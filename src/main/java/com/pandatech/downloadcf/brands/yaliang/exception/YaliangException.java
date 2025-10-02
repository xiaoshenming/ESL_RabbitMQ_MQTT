package com.pandatech.downloadcf.brands.yaliang.exception;

/**
 * 雅量品牌专用异常类
 * 用于处理雅量ESL操作中的各种异常情况
 */
public class YaliangException extends RuntimeException {
    
    private final String errorCode;
    private final String deviceCode;
    private final String deviceMac;
    
    public YaliangException(String message) {
        super(message);
        this.errorCode = "YALIANG_ERROR";
        this.deviceCode = null;
        this.deviceMac = null;
    }
    
    public YaliangException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "YALIANG_ERROR";
        this.deviceCode = null;
        this.deviceMac = null;
    }
    
    public YaliangException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.deviceCode = null;
        this.deviceMac = null;
    }
    
    public YaliangException(String errorCode, String message, String deviceCode, String deviceMac) {
        super(message);
        this.errorCode = errorCode;
        this.deviceCode = deviceCode;
        this.deviceMac = deviceMac;
    }
    
    public YaliangException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.deviceCode = null;
        this.deviceMac = null;
    }
    
    public YaliangException(String errorCode, String message, String deviceCode, String deviceMac, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.deviceCode = deviceCode;
        this.deviceMac = deviceMac;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    public String getDeviceCode() {
        return deviceCode;
    }
    
    public String getDeviceMac() {
        return deviceMac;
    }
    
    /**
     * 雅量异常错误码常量
     */
    public static class ErrorCodes {
        public static final String DEVICE_NOT_FOUND = "YALIANG_DEVICE_NOT_FOUND";
        public static final String INVALID_DEVICE_CODE = "YALIANG_INVALID_DEVICE_CODE";
        public static final String INVALID_DEVICE_MAC = "YALIANG_INVALID_DEVICE_MAC";
        public static final String INVALID_DEVICE_SIZE = "YALIANG_INVALID_DEVICE_SIZE";
        public static final String INVALID_IMAGE_FORMAT = "YALIANG_INVALID_IMAGE_FORMAT";
        public static final String IMAGE_TOO_LARGE = "YALIANG_IMAGE_TOO_LARGE";
        public static final String IMAGE_PROCESSING_ERROR = "YALIANG_IMAGE_PROCESSING_ERROR";
        public static final String MQTT_SEND_ERROR = "YALIANG_MQTT_SEND_ERROR";
        public static final String CONFIG_ERROR = "YALIANG_CONFIG_ERROR";
        public static final String VALIDATION_ERROR = "YALIANG_VALIDATION_ERROR";
        public static final String QUEUE_ID_GENERATION_ERROR = "YALIANG_QUEUE_ID_GENERATION_ERROR";
        public static final String UNSUPPORTED_OPERATION = "YALIANG_UNSUPPORTED_OPERATION";
        public static final String INVALID_ESL_ID = "YALIANG_INVALID_ESL_ID";
        public static final String DATA_TRANSFORMATION_ERROR = "YALIANG_DATA_TRANSFORMATION_ERROR";
        public static final String EXT_JSON_PARSING_ERROR = "YALIANG_EXT_JSON_PARSING_ERROR";
        public static final String MESSAGE_CREATION_ERROR = "YALIANG_MESSAGE_CREATION_ERROR";
    }
}