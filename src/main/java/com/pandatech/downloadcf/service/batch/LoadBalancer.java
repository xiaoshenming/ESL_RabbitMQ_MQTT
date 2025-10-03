package com.pandatech.downloadcf.service.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 负载均衡器
 * 监控系统资源使用情况，控制处理速度
 */
@Slf4j
@Component
public class LoadBalancer {
    
    // 系统资源阈值配置
    private static final double CPU_THRESHOLD = 0.8; // CPU使用率阈值80%
    private static final double MEMORY_THRESHOLD = 0.85; // 内存使用率阈值85%
    private static final int MAX_CONCURRENT_TASKS = 10; // 最大并发任务数
    
    // 当前并发任务计数器
    private final AtomicInteger currentTasks = new AtomicInteger(0);
    
    // 系统监控Bean
    private final OperatingSystemMXBean osBean;
    private final MemoryMXBean memoryBean;
    
    public LoadBalancer() {
        this.osBean = ManagementFactory.getOperatingSystemMXBean();
        this.memoryBean = ManagementFactory.getMemoryMXBean();
    }
    
    /**
     * 检查系统是否可以处理新的任务
     * @return true表示可以处理，false表示需要等待
     */
    public boolean canProcess() {
        try {
            // 检查并发任务数
            if (currentTasks.get() >= MAX_CONCURRENT_TASKS) {
                log.debug("并发任务数已达上限: {}/{}", currentTasks.get(), MAX_CONCURRENT_TASKS);
                return false;
            }
            
            // 检查CPU使用率
            double cpuUsage = getCpuUsage();
            if (cpuUsage > CPU_THRESHOLD) {
                log.debug("CPU使用率过高: {:.2f}% > {:.2f}%", cpuUsage * 100, CPU_THRESHOLD * 100);
                return false;
            }
            
            // 检查内存使用率
            double memoryUsage = getMemoryUsage();
            if (memoryUsage > MEMORY_THRESHOLD) {
                log.debug("内存使用率过高: {:.2f}% > {:.2f}%", memoryUsage * 100, MEMORY_THRESHOLD * 100);
                return false;
            }
            
            return true;
            
        } catch (Exception e) {
            log.warn("检查系统负载时发生异常: {}", e.getMessage());
            // 发生异常时保守处理，允许继续执行
            return true;
        }
    }
    
    /**
     * 开始处理任务（增加计数器）
     */
    public void startTask() {
        currentTasks.incrementAndGet();
        log.debug("开始处理任务，当前并发数: {}", currentTasks.get());
    }
    
    /**
     * 完成处理任务（减少计数器）
     */
    public void finishTask() {
        int current = currentTasks.decrementAndGet();
        log.debug("完成处理任务，当前并发数: {}", current);
    }
    
    /**
     * 获取CPU使用率
     * @return CPU使用率（0.0-1.0）
     */
    private double getCpuUsage() {
        try {
            // 对于某些JVM实现，可能需要特殊处理
            if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
                com.sun.management.OperatingSystemMXBean sunOsBean = 
                    (com.sun.management.OperatingSystemMXBean) osBean;
                double cpuUsage = sunOsBean.getProcessCpuLoad();
                return cpuUsage >= 0 ? cpuUsage : 0.0;
            } else {
                // 如果无法获取CPU使用率，返回较低的值
                return 0.3;
            }
        } catch (Exception e) {
            log.debug("获取CPU使用率失败: {}", e.getMessage());
            return 0.3; // 默认返回30%
        }
    }
    
    /**
     * 获取内存使用率
     * @return 内存使用率（0.0-1.0）
     */
    private double getMemoryUsage() {
        try {
            long usedMemory = memoryBean.getHeapMemoryUsage().getUsed();
            long maxMemory = memoryBean.getHeapMemoryUsage().getMax();
            
            if (maxMemory <= 0) {
                return 0.5; // 如果无法获取最大内存，返回50%
            }
            
            return (double) usedMemory / maxMemory;
        } catch (Exception e) {
            log.debug("获取内存使用率失败: {}", e.getMessage());
            return 0.5; // 默认返回50%
        }
    }
    
    /**
     * 获取当前系统负载信息
     * @return 负载信息字符串
     */
    public String getLoadInfo() {
        try {
            double cpuUsage = getCpuUsage();
            double memoryUsage = getMemoryUsage();
            int currentTaskCount = currentTasks.get();
            
            return String.format("CPU: %.1f%%, Memory: %.1f%%, Tasks: %d/%d", 
                    cpuUsage * 100, memoryUsage * 100, currentTaskCount, MAX_CONCURRENT_TASKS);
        } catch (Exception e) {
            return "Load info unavailable: " + e.getMessage();
        }
    }
    
    /**
     * 获取当前并发任务数
     * @return 当前并发任务数
     */
    public int getCurrentTaskCount() {
        return currentTasks.get();
    }
    
    /**
     * 重置负载均衡器状态
     */
    public void reset() {
        currentTasks.set(0);
        log.info("负载均衡器状态已重置");
    }
}