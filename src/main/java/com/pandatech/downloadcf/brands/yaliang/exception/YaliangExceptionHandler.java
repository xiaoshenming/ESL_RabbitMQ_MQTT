package com.pandatech.downloadcf.brands.yaliang.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 雅量品牌专用异常处理器
 * 统一处理雅量ESL操作中的异常情况
 */
@Slf4j
@RestControllerAdvice
public class YaliangExceptionHandler {
    
    /**
     * 处理雅量专用异常
     *
     * @param ex 雅量异常
     * @return 错误响应
     */
    @ExceptionHandler(YaliangException.class)
    public ResponseEntity<Map<String, Object>> handleYaliangException(YaliangException ex) {
        log.error("雅量ESL操作异常: errorCode={}, deviceCode={}, deviceMac={}, message={}", 
                ex.getErrorCode(), ex.getDeviceCode(), ex.getDeviceMac(), ex.getMessage(), ex);
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("errorCode", ex.getErrorCode());
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("brandCode", "YALIANG001");
        
        // 添加设备相关信息（如果有）
        if (ex.getDeviceCode() != null) {
            errorResponse.put("deviceCode", ex.getDeviceCode());
        }
        if (ex.getDeviceMac() != null) {
            errorResponse.put("deviceMac", ex.getDeviceMac());
        }
        
        // 根据错误类型返回不同的HTTP状态码
        HttpStatus status = getHttpStatusByErrorCode(ex.getErrorCode());
        
        return ResponseEntity.status(status).body(errorResponse);
    }
    
    /**
     * 根据错误码确定HTTP状态码
     *
     * @param errorCode 错误码
     * @return HTTP状态码
     */
    private HttpStatus getHttpStatusByErrorCode(String errorCode) {
        switch (errorCode) {
            case YaliangException.ErrorCodes.DEVICE_NOT_FOUND:
                return HttpStatus.NOT_FOUND;
            
            case YaliangException.ErrorCodes.INVALID_DEVICE_CODE:
            case YaliangException.ErrorCodes.INVALID_DEVICE_MAC:
            case YaliangException.ErrorCodes.INVALID_DEVICE_SIZE:
            case YaliangException.ErrorCodes.INVALID_IMAGE_FORMAT:
            case YaliangException.ErrorCodes.IMAGE_TOO_LARGE:
            case YaliangException.ErrorCodes.VALIDATION_ERROR:
                return HttpStatus.BAD_REQUEST;
            
            case YaliangException.ErrorCodes.IMAGE_PROCESSING_ERROR:
            case YaliangException.ErrorCodes.MQTT_SEND_ERROR:
            case YaliangException.ErrorCodes.QUEUE_ID_GENERATION_ERROR:
                return HttpStatus.INTERNAL_SERVER_ERROR;
            
            case YaliangException.ErrorCodes.CONFIG_ERROR:
                return HttpStatus.SERVICE_UNAVAILABLE;
            
            case YaliangException.ErrorCodes.UNSUPPORTED_OPERATION:
                return HttpStatus.NOT_IMPLEMENTED;
            
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
    
    /**
     * 处理参数验证异常
     *
     * @param ex 参数异常
     * @return 错误响应
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("雅量ESL参数验证异常: {}", ex.getMessage(), ex);
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("errorCode", YaliangException.ErrorCodes.VALIDATION_ERROR);
        errorResponse.put("message", "参数验证失败: " + ex.getMessage());
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("brandCode", "YALIANG001");
        
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    /**
     * 处理空指针异常
     *
     * @param ex 空指针异常
     * @return 错误响应
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, Object>> handleNullPointerException(NullPointerException ex) {
        log.error("雅量ESL空指针异常: {}", ex.getMessage(), ex);
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("errorCode", "YALIANG_NULL_POINTER_ERROR");
        errorResponse.put("message", "系统内部错误，请联系管理员");
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("brandCode", "YALIANG001");
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    
    /**
     * 处理通用运行时异常
     *
     * @param ex 运行时异常
     * @return 错误响应
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        log.error("雅量ESL运行时异常: {}", ex.getMessage(), ex);
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("errorCode", "YALIANG_RUNTIME_ERROR");
        errorResponse.put("message", "系统运行时错误: " + ex.getMessage());
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("brandCode", "YALIANG001");
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}