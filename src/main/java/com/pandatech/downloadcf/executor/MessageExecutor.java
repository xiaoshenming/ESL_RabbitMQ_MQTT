package com.pandatech.downloadcf.executor;

import com.pandatech.downloadcf.dto.MessageExecutionData;

/**
 * 消息执行器接口 - 支持不同的消息执行方式
 */
public interface MessageExecutor {
    
    /**
     * 获取执行器类型
     */
    String getExecutorType();
    
    /**
     * 执行消息发送
     * 
     * @param executionData 消息执行数据
     * @return 执行结果
     */
    boolean execute(MessageExecutionData executionData);
    
    /**
     * 检查执行器是否可用
     */
    boolean isAvailable();
    
    /**
     * 获取执行器描述
     */
    String getDescription();
}