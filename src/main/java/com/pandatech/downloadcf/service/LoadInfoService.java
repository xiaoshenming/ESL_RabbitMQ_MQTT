package com.pandatech.downloadcf.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 系统负载信息服务
 * 提供系统负载相关信息，避免循环依赖
 */
@Slf4j
@Service
public class LoadInfoService {
    
    // 负载控制参数
    private final AtomicInteger currentLoad = new AtomicInteger(0);
    private final AtomicLong lastProcessTime = new AtomicLong(System.currentTimeMillis());
    private static final int MAX_CONCURRENT_MESSAGES = 10; // 最大并发处理数
    private static final long MIN_INTERVAL_MS = 100; // 最小处理间隔（毫秒）
    
    /**
     * 增加当前负载
     */
    public void incrementLoad() {
        currentLoad.incrementAndGet();
    }
    
    /**
     * 减少当前负载
     */
    public void decrementLoad() {
        currentLoad.decrementAndGet();
        lastProcessTime.set(System.currentTimeMillis());
    }
    
    /**
     * 检查是否可以处理消息（负载控制）
     */
    public boolean canProcessMessage() {
        int load = currentLoad.get();
        long timeSinceLastProcess = System.currentTimeMillis() - lastProcessTime.get();
        
        boolean canProcess = load < MAX_CONCURRENT_MESSAGES && timeSinceLastProcess >= MIN_INTERVAL_MS;
        
        if (!canProcess) {
            log.debug("负载控制: 当前负载={}, 最大负载={}, 距离上次处理={}ms, 最小间隔={}ms", 
                    load, MAX_CONCURRENT_MESSAGES, timeSinceLastProcess, MIN_INTERVAL_MS);
        }
        
        return canProcess;
    }
    
    /**
     * 获取负载信息
     */
    public String getLoadInfo() {
        int load = currentLoad.get();
        long timeSinceLastProcess = System.currentTimeMillis() - lastProcessTime.get();
        return String.format("当前负载: %d/%d, 距离上次处理: %dms", 
                load, MAX_CONCURRENT_MESSAGES, timeSinceLastProcess);
    }
    
    /**
     * 获取当前负载数
     */
    public int getCurrentLoad() {
        return currentLoad.get();
    }
    
    /**
     * 获取最大并发数
     */
    public int getMaxConcurrentMessages() {
        return MAX_CONCURRENT_MESSAGES;
    }
    
    /**
     * 获取最小处理间隔
     */
    public long getMinIntervalMs() {
        return MIN_INTERVAL_MS;
    }
}