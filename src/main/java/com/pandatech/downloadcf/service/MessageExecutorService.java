package com.pandatech.downloadcf.service;

import com.pandatech.downloadcf.dto.MessageExecutionData;
import com.pandatech.downloadcf.executor.MessageExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 消息执行器管理服务
 * 负责管理和调度各种类型的消息执行器
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageExecutorService {
    
    private final List<MessageExecutor> messageExecutors;
    
    /**
     * 执行消息发送
     */
    public boolean executeMessage(MessageExecutionData executionData) {
        log.info("开始执行消息: executorType={}, brandCode={}, eslId={}", 
                executionData.getExecutorType(), executionData.getBrandCode(), executionData.getEslId());
        
        // 查找对应的执行器
        MessageExecutor executor = findExecutor(executionData.getExecutorType());
        if (executor == null) {
            log.error("未找到执行器: type={}", executionData.getExecutorType());
            return false;
        }
        
        // 检查执行器是否可用
        if (!executor.isAvailable()) {
            log.error("执行器不可用: type={}", executionData.getExecutorType());
            return false;
        }
        
        // 执行消息发送
        boolean success = executor.execute(executionData);
        
        if (success) {
            log.info("消息执行成功: executorType={}, brandCode={}, eslId={}", 
                    executionData.getExecutorType(), executionData.getBrandCode(), executionData.getEslId());
        } else {
            log.error("消息执行失败: executorType={}, brandCode={}, eslId={}", 
                    executionData.getExecutorType(), executionData.getBrandCode(), executionData.getEslId());
        }
        
        return success;
    }
    
    /**
     * 查找指定类型的执行器
     */
    public MessageExecutor findExecutor(String executorType) {
        if (executorType == null || executorType.trim().isEmpty()) {
            log.warn("执行器类型为空");
            return null;
        }
        
        for (MessageExecutor executor : messageExecutors) {
            if (executorType.equalsIgnoreCase(executor.getExecutorType())) {
                log.debug("找到执行器: type={}, class={}", executorType, executor.getClass().getSimpleName());
                return executor;
            }
        }
        
        log.warn("未找到匹配的执行器: type={}", executorType);
        return null;
    }
    
    /**
     * 获取所有可用的执行器
     */
    public List<MessageExecutor> getAvailableExecutors() {
        return messageExecutors.stream()
                .filter(MessageExecutor::isAvailable)
                .toList();
    }
    
    /**
     * 获取所有执行器
     */
    public List<MessageExecutor> getAllExecutors() {
        return messageExecutors;
    }
    
    /**
     * 检查指定类型的执行器是否可用
     */
    public boolean isExecutorAvailable(String executorType) {
        MessageExecutor executor = findExecutor(executorType);
        return executor != null && executor.isAvailable();
    }
}