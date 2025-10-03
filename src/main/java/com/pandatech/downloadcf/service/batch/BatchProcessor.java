package com.pandatech.downloadcf.service.batch;

import com.pandatech.downloadcf.dto.EslRefreshRequest;
import com.pandatech.downloadcf.service.EslRefreshService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 批量处理器
 * 专门负责批量操作的核心逻辑
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BatchProcessor {
    
    private final EslRefreshService eslRefreshService;
    private final LoadBalancer loadBalancer;
    
    // 批量处理线程池
    private final ExecutorService batchExecutor = Executors.newFixedThreadPool(5);
    
    // 处理速度控制（毫秒）
    private static final int PROCESSING_DELAY_MS = 50;
    
    /**
     * 批量处理价签刷新请求
     */
    public CompletableFuture<BatchProcessResult> processBatch(List<EslRefreshRequest> requests) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("开始批量处理价签刷新，总数量: {}", requests.size());
            
            AtomicInteger successCount = new AtomicInteger(0);
            AtomicInteger failureCount = new AtomicInteger(0);
            
            for (EslRefreshRequest request : requests) {
                try {
                    // 检查系统负载，如果负载过高则等待
                    waitForSystemLoad();
                    
                    // 处理单个请求
                    boolean success = eslRefreshService.refreshEsl(request);
                    if (success) {
                        successCount.incrementAndGet();
                        log.debug("价签刷新成功: {}", request.getEslId());
                    } else {
                        failureCount.incrementAndGet();
                        log.warn("价签刷新失败: {}", request.getEslId());
                    }
                    
                    // 控制处理速度，避免过快发送
                    Thread.sleep(PROCESSING_DELAY_MS);
                    
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.warn("批量处理被中断: {}", request.getEslId());
                    failureCount.incrementAndGet();
                    break;
                } catch (Exception e) {
                    log.error("批量处理失败: eslId={}, 错误: {}", request.getEslId(), e.getMessage(), e);
                    failureCount.incrementAndGet();
                }
            }
            
            BatchProcessResult result = new BatchProcessResult();
            result.setTotalCount(requests.size());
            result.setSuccessCount(successCount.get());
            result.setFailureCount(failureCount.get());
            
            log.info("批量处理完成: {}", result);
            return result;
            
        }, batchExecutor);
    }
    
    /**
     * 等待系统负载降低
     */
    private void waitForSystemLoad() {
        while (!loadBalancer.canProcess()) {
            log.debug("系统负载过高，等待处理");
            try {
                Thread.sleep(500); // 等待500ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    /**
     * 批量处理单个门店的请求
     */
    public BatchProcessResult processStoreRequests(String storeCode, List<EslRefreshRequest> storeRequests) {
        log.info("处理门店 {} 的价签刷新，数量: {}", storeCode, storeRequests.size());
        
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);
        
        for (EslRefreshRequest request : storeRequests) {
            try {
                // 检查系统负载
                waitForSystemLoad();
                
                // 处理请求
                boolean success = eslRefreshService.refreshEsl(request);
                if (success) {
                    successCount.incrementAndGet();
                } else {
                    failureCount.incrementAndGet();
                }
                
                // 控制处理速度
                Thread.sleep(PROCESSING_DELAY_MS);
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                failureCount.incrementAndGet();
                break;
            } catch (Exception e) {
                log.error("门店批量处理失败: storeCode={}, eslId={}, 错误: {}", 
                        storeCode, request.getEslId(), e.getMessage(), e);
                failureCount.incrementAndGet();
            }
        }
        
        BatchProcessResult result = new BatchProcessResult();
        result.setTotalCount(storeRequests.size());
        result.setSuccessCount(successCount.get());
        result.setFailureCount(failureCount.get());
        
        log.info("门店 {} 批量处理完成: {}", storeCode, result);
        return result;
    }
    
    /**
     * 设置处理延迟时间
     */
    public void setProcessingDelay(int delayMs) {
        // 这里可以动态调整处理延迟，但为了简化，暂时使用常量
        log.info("处理延迟设置请求: {}ms (当前使用固定值: {}ms)", delayMs, PROCESSING_DELAY_MS);
    }
    
    /**
     * 获取当前处理器状态
     */
    public ProcessorStatus getStatus() {
        ProcessorStatus status = new ProcessorStatus();
        status.setThreadPoolSize(5);
        status.setProcessingDelayMs(PROCESSING_DELAY_MS);
        status.setCanProcess(loadBalancer.canProcess());
        
        return status;
    }
    
    /**
     * 处理器状态信息
     */
    public static class ProcessorStatus {
        private int threadPoolSize;
        private int processingDelayMs;
        private boolean canProcess;
        
        // Getters and Setters
        public int getThreadPoolSize() { return threadPoolSize; }
        public void setThreadPoolSize(int threadPoolSize) { this.threadPoolSize = threadPoolSize; }
        
        public int getProcessingDelayMs() { return processingDelayMs; }
        public void setProcessingDelayMs(int processingDelayMs) { this.processingDelayMs = processingDelayMs; }
        
        public boolean isCanProcess() { return canProcess; }
        public void setCanProcess(boolean canProcess) { this.canProcess = canProcess; }
        
        @Override
        public String toString() {
            return String.format("ProcessorStatus{线程池大小=%d, 处理延迟=%dms, 可处理=%s}", 
                    threadPoolSize, processingDelayMs, canProcess);
        }
    }
}