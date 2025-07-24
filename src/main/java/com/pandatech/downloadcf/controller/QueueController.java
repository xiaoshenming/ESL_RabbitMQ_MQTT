package com.pandatech.downloadcf.controller;

import com.pandatech.downloadcf.dto.EslRefreshRequest;
import com.pandatech.downloadcf.service.BatchRefreshService;
import com.pandatech.downloadcf.service.QueueMonitorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 队列管理控制器
 * 提供批量操作和队列监控功能
 */
@Slf4j
@RestController
@RequestMapping("/api/queue")
@RequiredArgsConstructor
public class QueueController {
    
    private final BatchRefreshService batchRefreshService;
    private final QueueMonitorService queueMonitorService;
    
    /**
     * 批量刷新价签
     */
    @PostMapping("/batch-refresh")
    public ResponseEntity<Map<String, Object>> batchRefresh(@RequestBody List<EslRefreshRequest> requests) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            log.info("收到批量刷新请求，数量: {}", requests.size());
            
            if (requests.isEmpty()) {
                response.put("success", false);
                response.put("message", "请求列表不能为空");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (requests.size() > 1000) {
                response.put("success", false);
                response.put("message", "单次批量刷新不能超过1000个价签");
                return ResponseEntity.badRequest().body(response);
            }
            
            // 异步处理批量刷新
            CompletableFuture<BatchRefreshService.BatchRefreshResult> future = 
                    batchRefreshService.batchRefresh(requests);
            
            // 立即返回响应，不等待处理完成
            response.put("success", true);
            response.put("message", "批量刷新任务已提交");
            response.put("batchSize", requests.size());
            response.put("status", "processing");
            
            // 异步记录结果
            future.thenAccept(result -> {
                log.info("批量刷新完成: {}", result);
            }).exceptionally(throwable -> {
                log.error("批量刷新异常: {}", throwable.getMessage(), throwable);
                return null;
            });
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("批量刷新请求处理失败: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("message", "批量刷新请求处理失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * 批量刷新（发送到队列）
     */
    @PostMapping("/batch-refresh-queue")
    public ResponseEntity<Map<String, Object>> batchRefreshQueue(
            @RequestBody List<EslRefreshRequest> requests,
            @RequestParam(defaultValue = "1") int priority) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            log.info("收到批量刷新队列请求，数量: {}, 优先级: {}", requests.size(), priority);
            
            if (requests.isEmpty()) {
                response.put("success", false);
                response.put("message", "请求列表不能为空");
                return ResponseEntity.badRequest().body(response);
            }
            
            // 发送到队列
            boolean success = batchRefreshService.sendBatchRefreshToQueue(requests, priority);
            
            if (success) {
                response.put("success", true);
                response.put("message", "批量刷新消息已发送到队列");
                response.put("batchSize", requests.size());
                response.put("priority", priority);
            } else {
                response.put("success", false);
                response.put("message", "发送批量刷新消息到队列失败");
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("批量刷新队列请求处理失败: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("message", "批量刷新队列请求处理失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * 获取队列状态
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getQueueStatus() {
        try {
            Map<String, Object> status = queueMonitorService.getQueueStatus();
            status.put("success", true);
            return ResponseEntity.ok(status);
            
        } catch (Exception e) {
            log.error("获取队列状态失败: {}", e.getMessage(), e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "获取队列状态失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * 重置统计信息
     */
    @PostMapping("/reset-statistics")
    public ResponseEntity<Map<String, Object>> resetStatistics() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            queueMonitorService.resetStatistics();
            response.put("success", true);
            response.put("message", "统计信息已重置");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("重置统计信息失败: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("message", "重置统计信息失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * 检查系统健康状态
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> checkHealth() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean healthy = queueMonitorService.isSystemHealthy();
            response.put("healthy", healthy);
            response.put("status", healthy ? "UP" : "DOWN");
            
            if (healthy) {
                response.put("message", "系统运行正常");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "系统存在问题，请检查队列状态");
                return ResponseEntity.status(503).body(response);
            }
            
        } catch (Exception e) {
            log.error("检查系统健康状态失败: {}", e.getMessage(), e);
            response.put("healthy", false);
            response.put("status", "ERROR");
            response.put("message", "健康检查失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * 启动队列监控
     */
    @PostMapping("/start-monitoring")
    public ResponseEntity<Map<String, Object>> startMonitoring() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            queueMonitorService.startMonitoring();
            response.put("success", true);
            response.put("message", "队列监控已启动");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("启动队列监控失败: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("message", "启动队列监控失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * 停止队列监控
     */
    @PostMapping("/stop-monitoring")
    public ResponseEntity<Map<String, Object>> stopMonitoring() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            queueMonitorService.stopMonitoring();
            response.put("success", true);
            response.put("message", "队列监控已停止");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("停止队列监控失败: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("message", "停止队列监控失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}