package com.pandatech.downloadcf.exception;

/**
 * 模板相关异常类
 * 用于处理模板操作中的各种异常情况
 */
public class TemplateException extends RuntimeException {
    
    private final String errorCode;
    private final Object[] args;
    
    public TemplateException(String message) {
        super(message);
        this.errorCode = "TEMPLATE_ERROR";
        this.args = null;
    }
    
    public TemplateException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "TEMPLATE_ERROR";
        this.args = null;
    }
    
    public TemplateException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.args = null;
    }
    
    public TemplateException(String errorCode, String message, Object... args) {
        super(message);
        this.errorCode = errorCode;
        this.args = args;
    }
    
    public TemplateException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.args = null;
    }
    
    public TemplateException(String errorCode, String message, Throwable cause, Object... args) {
        super(message, cause);
        this.errorCode = errorCode;
        this.args = args;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    public Object[] getArgs() {
        return args;
    }
    
    /**
     * 模板不存在异常
     */
    public static class TemplateNotFoundException extends TemplateException {
        public TemplateNotFoundException(String templateId) {
            super("TEMPLATE_NOT_FOUND", "模板不存在: " + templateId, templateId);
        }
    }
    
    /**
     * 模板格式无效异常
     */
    public static class InvalidTemplateFormatException extends TemplateException {
        public InvalidTemplateFormatException(String message) {
            super("INVALID_TEMPLATE_FORMAT", "模板格式无效: " + message);
        }
        
        public InvalidTemplateFormatException(String message, Throwable cause) {
            super("INVALID_TEMPLATE_FORMAT", "模板格式无效: " + message, cause);
        }
    }
    
    /**
     * 模板转换异常
     */
    public static class TemplateConversionException extends TemplateException {
        public TemplateConversionException(String message) {
            super("TEMPLATE_CONVERSION_ERROR", "模板转换失败: " + message);
        }
        
        public TemplateConversionException(String message, Throwable cause) {
            super("TEMPLATE_CONVERSION_ERROR", "模板转换失败: " + message, cause);
        }
    }
    
    /**
     * 模板验证异常
     */
    public static class TemplateValidationException extends TemplateException {
        public TemplateValidationException(String message) {
            super("TEMPLATE_VALIDATION_ERROR", "模板验证失败: " + message);
        }
        
        public TemplateValidationException(String message, Throwable cause) {
            super("TEMPLATE_VALIDATION_ERROR", "模板验证失败: " + message, cause);
        }
    }
    
    /**
     * MQTT发送异常
     */
    public static class MqttSendException extends TemplateException {
        public MqttSendException(String message) {
            super("MQTT_SEND_ERROR", "MQTT消息发送失败: " + message);
        }
        
        public MqttSendException(String message, Throwable cause) {
            super("MQTT_SEND_ERROR", "MQTT消息发送失败: " + message, cause);
        }
    }
    
    /**
     * 屏幕类型转换异常
     */
    public static class ScreenTypeConversionException extends TemplateException {
        public ScreenTypeConversionException(String message) {
            super("SCREEN_TYPE_CONVERSION_ERROR", "屏幕类型转换失败: " + message);
        }
        
        public ScreenTypeConversionException(String message, Throwable cause) {
            super("SCREEN_TYPE_CONVERSION_ERROR", "屏幕类型转换失败: " + message, cause);
        }
    }
}