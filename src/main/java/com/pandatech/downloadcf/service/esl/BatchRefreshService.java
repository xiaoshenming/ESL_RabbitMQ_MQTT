package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.dto.EslRefreshRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 批量刷新服务
 * 支持批量价签刷新和负载控制
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BatchRefreshService {
    
    private final EslRefreshService eslRefreshService;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final RabbitMQListener rabbitMQListener;
    
    // 批量处理线程池
    private final ExecutorService batchExecutor = Executors.newFixedThreadPool(5);
    
    /**
     * 批量刷新价签
     */
    public CompletableFuture<BatchRefreshResult> batchRefresh(List<EslRefreshRequest> requests) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("开始批量刷新价签，总数量: {}", requests.size());
            
            AtomicInteger successCount = new AtomicInteger(0);
            AtomicInteger failureCount = new AtomicInteger(0);
            
            // 按门店分组处理
            Map<String, List<EslRefreshRequest>> storeGroups = groupByStore(requests);
            
            for (Map.Entry<String, List<EslRefreshRequest>> entry : storeGroups.entrySet()) {
                String storeCode = entry.getKey();
                List<EslRefreshRequest> storeRequests = entry.getValue();
                
                log.info("处理门店 {} 的价签刷新，数量: {}", storeCode, storeRequests.size());
                
                // 检查系统负载
                while (!canProcessBatch()) {
                    log.warn("系统负载过高，等待处理批量刷新: 门店={}", storeCode);
                    try {
                        Thread.sleep(500); // 等待500ms
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
                
                // 处理该门店的所有请求
                for (EslRefreshRequest request : storeRequests) {
                    try {
                        boolean success = eslRefreshService.refreshEsl(request);
                        if (success) {
                            successCount.incrementAndGet();
                        } else {
                            failureCount.incrementAndGet();
                        }
                        
                        // 控制处理速度，避免过快发送
                        Thread.sleep(50); // 每个请求间隔50ms
                        
                    } catch (Exception e) {
                        log.error("批量刷新失败: eslId={}, 错误: {}", request.getEslId(), e.getMessage(), e);
                        failureCount.incrementAndGet();
                    }
                }
            }
            
            BatchRefreshResult result = new BatchRefreshResult();
            result.setTotalCount(requests.size());
            result.setSuccessCount(successCount.get());
            result.setFailureCount(failureCount.get());
            
            log.info("批量刷新完成: 总数={}, 成功={}, 失败={}", 
                    result.getTotalCount(), result.getSuccessCount(), result.getFailureCount());
            
            return result;
            
        }, batchExecutor);
    }
    
    /**
     * 按门店分组
     */
    private Map<String, List<EslRefreshRequest>> groupByStore(List<EslRefreshRequest> requests) {
        Map<String, List<EslRefreshRequest>> groups = new HashMap<>();
        
        for (EslRefreshRequest request : requests) {
            String storeCode = request.getStoreCode();
            if (storeCode == null || storeCode.isEmpty()) {
                storeCode = "0002"; // 默认门店
            }
            
            groups.computeIfAbsent(storeCode, k -> new java.util.ArrayList<>()).add(request);
        }
        
        return groups;
    }
    
    /**
     * 检查是否可以处理批量操作
     */
    private boolean canProcessBatch() {
        try {
            String loadInfo = rabbitMQListener.getLoadInfo();
            log.debug("当前系统负载: {}", loadInfo);
            
            // 简单的负载检查，可以根据实际情况调整
            return !loadInfo.contains("10/10"); // 如果负载未满，可以处理
            
        } catch (Exception e) {
            log.warn("获取负载信息失败: {}", e.getMessage());
            return true; // 默认允许处理
        }
    }
    
    /**
     * 发送批量刷新消息到队列
     */
    public boolean sendBatchRefreshToQueue(List<EslRefreshRequest> requests, int priority) {
        try {
            // 构建批量刷新队列消息
            Map<String, Object> queueMessage = new HashMap<>();
            queueMessage.put("messageType", "batchRefresh");
            queueMessage.put("requests", requests);
            queueMessage.put("timestamp", System.currentTimeMillis());
            queueMessage.put("priority", priority);
            queueMessage.put("batchSize", requests.size());
            
            // 发送到刷新队列
            String jsonMessage = objectMapper.writeValueAsString(queueMessage);
            rabbitTemplate.convertAndSend("refresh.queue", jsonMessage);
            
            log.info("批量刷新消息已发送到RabbitMQ队列: 数量={}, 优先级={}", requests.size(), priority);
            return true;
            
        } catch (Exception e) {
            log.error("发送批量刷新消息到队列失败: 数量={}, 错误: {}", requests.size(), e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * 批量刷新结果
     */
    public static class BatchRefreshResult {
        private int totalCount;
        private int successCount;
        private int failureCount;
        
        public int getTotalCount() { return totalCount; }
        public void setTotalCount(int totalCount) { this.totalCount = totalCount; }
        
        public int getSuccessCount() { return successCount; }
        public void setSuccessCount(int successCount) { this.successCount = successCount; }
        
        public int getFailureCount() { return failureCount; }
        public void setFailureCount(int failureCount) { this.failureCount = failureCount; }
        
        public double getSuccessRate() {
            return totalCount > 0 ? (double) successCount / totalCount * 100 : 0;
        }
        
        @Override
        public String toString() {
            return String.format("BatchRefreshResult{总数=%d, 成功=%d, 失败=%d, 成功率=%.2f%%}", 
                    totalCount, successCount, failureCount, getSuccessRate());
        }
    }
}