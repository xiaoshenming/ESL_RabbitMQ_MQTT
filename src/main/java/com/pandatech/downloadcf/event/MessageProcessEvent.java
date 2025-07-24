package com.pandatech.downloadcf.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 消息处理事件
 * 用于通知消息处理的成功或失败
 */
@Getter
public class MessageProcessEvent extends ApplicationEvent {
    
    private final String messageType;
    private final boolean success;
    private final String errorMessage;
    
    public MessageProcessEvent(Object source, String messageType, boolean success) {
        this(source, messageType, success, null);
    }
    
    public MessageProcessEvent(Object source, String messageType, boolean success, String errorMessage) {
        super(source);
        this.messageType = messageType;
        this.success = success;
        this.errorMessage = errorMessage;
    }
}