package com.pandatech.downloadcf.service;

import com.pandatech.downloadcf.event.MessageProcessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.QueueInformation;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 队列监控服务
 * 监控RabbitMQ队列状态和系统负载
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QueueMonitorService {
    
    private final RabbitAdmin rabbitAdmin;
    private final LoadInfoService loadInfoService;
    
    // 统计信息
    private final AtomicLong totalProcessedMessages = new AtomicLong(0);
    private final AtomicLong totalFailedMessages = new AtomicLong(0);
    private final AtomicLong templateMessages = new AtomicLong(0);
    private final AtomicLong refreshMessages = new AtomicLong(0);
    
    // 定时监控
    private final ScheduledExecutorService monitorExecutor = Executors.newScheduledThreadPool(2);
    
    /**
     * 监听消息处理事件
     */
    @EventListener
    public void handleMessageProcessEvent(MessageProcessEvent event) {
        if (event.isSuccess()) {
            recordMessageSuccess(event.getMessageType());
        } else {
            recordMessageFailure(event.getMessageType());
        }
    }
    
    /**
     * 启动监控
     */
    public void startMonitoring() {
        // 每30秒监控一次队列状态
        monitorExecutor.scheduleAtFixedRate(this::monitorQueues, 0, 30, TimeUnit.SECONDS);
        
        // 每5分钟输出统计信息
        monitorExecutor.scheduleAtFixedRate(this::logStatistics, 0, 5, TimeUnit.MINUTES);
        
        log.info("队列监控服务已启动");
    }
    
    /**
     * 停止监控
     */
    public void stopMonitoring() {
        monitorExecutor.shutdown();
        log.info("队列监控服务已停止");
    }
    
    /**
     * 监控队列状态
     */
    private void monitorQueues() {
        try {
            // 监控模板队列
            QueueInformation templateQueueInfo = rabbitAdmin.getQueueInfo("template.queue");
            if (templateQueueInfo != null) {
                int templateQueueSize = templateQueueInfo.getMessageCount();
                if (templateQueueSize > 0) {
                    log.info("模板队列状态: 待处理消息数={}", templateQueueSize);
                    
                    // 如果队列积压过多，发出警告
                    if (templateQueueSize > 100) {
                        log.warn("模板队列积压严重: 待处理消息数={}", templateQueueSize);
                    }
                }
            }
            
            // 监控刷新队列
            QueueInformation refreshQueueInfo = rabbitAdmin.getQueueInfo("refresh.queue");
            if (refreshQueueInfo != null) {
                int refreshQueueSize = refreshQueueInfo.getMessageCount();
                if (refreshQueueSize > 0) {
                    log.info("刷新队列状态: 待处理消息数={}", refreshQueueSize);
                    
                    // 如果队列积压过多，发出警告
                    if (refreshQueueSize > 200) {
                        log.warn("刷新队列积压严重: 待处理消息数={}", refreshQueueSize);
                    }
                }
            }
            
            // 监控系统负载
            String loadInfo = loadInfoService.getLoadInfo();
            log.debug("系统负载信息: {}", loadInfo);
            
        } catch (Exception e) {
            log.error("监控队列状态失败: {}", e.getMessage(), e);
        }
    }
    
    /**
     * 输出统计信息
     */
    private void logStatistics() {
        log.info("队列处理统计: 总处理={}, 总失败={}, 模板消息={}, 刷新消息={}, 成功率={:.2f}%",
                totalProcessedMessages.get(),
                totalFailedMessages.get(),
                templateMessages.get(),
                refreshMessages.get(),
                getSuccessRate());
    }
    
    /**
     * 获取队列状态信息
     */
    public Map<String, Object> getQueueStatus() {
        Map<String, Object> status = new HashMap<>();
        
        try {
            // 获取队列信息
            QueueInformation templateQueueInfo = rabbitAdmin.getQueueInfo("template.queue");
            QueueInformation refreshQueueInfo = rabbitAdmin.getQueueInfo("refresh.queue");
            
            status.put("templateQueueSize", templateQueueInfo != null ? templateQueueInfo.getMessageCount() : 0);
            status.put("refreshQueueSize", refreshQueueInfo != null ? refreshQueueInfo.getMessageCount() : 0);
            
            // 获取处理统计
            status.put("totalProcessed", totalProcessedMessages.get());
            status.put("totalFailed", totalFailedMessages.get());
            status.put("templateMessages", templateMessages.get());
            status.put("refreshMessages", refreshMessages.get());
            status.put("successRate", getSuccessRate());
            
            // 获取系统负载
            status.put("loadInfo", loadInfoService.getLoadInfo());
            
        } catch (Exception e) {
            log.error("获取队列状态失败: {}", e.getMessage(), e);
            status.put("error", e.getMessage());
        }
        
        return status;
    }
    
    /**
     * 记录消息处理成功
     */
    public void recordMessageSuccess(String messageType) {
        totalProcessedMessages.incrementAndGet();
        
        if ("template".equals(messageType)) {
            templateMessages.incrementAndGet();
        } else if ("refresh".equals(messageType)) {
            refreshMessages.incrementAndGet();
        }
    }
    
    /**
     * 记录消息处理失败
     */
    public void recordMessageFailure(String messageType) {
        totalFailedMessages.incrementAndGet();
    }
    
    /**
     * 获取成功率
     */
    private double getSuccessRate() {
        long total = totalProcessedMessages.get() + totalFailedMessages.get();
        return total > 0 ? (double) totalProcessedMessages.get() / total * 100 : 100.0;
    }
    
    /**
     * 重置统计信息
     */
    public void resetStatistics() {
        totalProcessedMessages.set(0);
        totalFailedMessages.set(0);
        templateMessages.set(0);
        refreshMessages.set(0);
        log.info("统计信息已重置");
    }
    
    /**
     * 检查系统是否健康
     */
    public boolean isSystemHealthy() {
        try {
            Map<String, Object> status = getQueueStatus();
            
            // 检查队列积压
            int templateQueueSize = (Integer) status.getOrDefault("templateQueueSize", 0);
            int refreshQueueSize = (Integer) status.getOrDefault("refreshQueueSize", 0);
            
            // 检查成功率
            double successRate = (Double) status.getOrDefault("successRate", 100.0);
            
            // 健康标准：队列积压不超过阈值，成功率不低于95%
            boolean queueHealthy = templateQueueSize < 500 && refreshQueueSize < 1000;
            boolean successRateHealthy = successRate >= 95.0;
            
            return queueHealthy && successRateHealthy;
            
        } catch (Exception e) {
            log.error("检查系统健康状态失败: {}", e.getMessage(), e);
            return false;
        }
    }
}