package com.pandatech.downloadcf.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 统一API响应格式
 * 
 * @param <T> 数据类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    
    /**
     * 响应状态码
     */
    private Integer code;
    
    /**
     * 响应消息
     */
    private String msg;
    
    /**
     * 响应数据
     */
    private T data;
    
    /**
     * 成功响应（带数据）
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, data);
    }
    
    /**
     * 成功响应（无数据）
     */
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(200, message, null);
    }
    
    /**
     * 部分成功响应（用于批量操作）
     */
    public static <T> ApiResponse<T> partialSuccess(String message, T data) {
        return new ApiResponse<>(207, message, data);
    }
    
    /**
     * 客户端错误响应
     */
    public static <T> ApiResponse<T> badRequest(String message, T data) {
        return new ApiResponse<>(400, message, data);
    }
    
    /**
     * 客户端错误响应（无数据）
     */
    public static <T> ApiResponse<T> badRequest(String message) {
        return new ApiResponse<>(400, message, null);
    }
    
    /**
     * 资源不存在响应
     */
    public static <T> ApiResponse<T> notFound(String message, T data) {
        return new ApiResponse<>(404, message, data);
    }
    
    /**
     * 资源不存在响应（无数据）
     */
    public static <T> ApiResponse<T> notFound(String message) {
        return new ApiResponse<>(404, message, null);
    }
    
    /**
     * 服务器内部错误响应
     */
    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(500, message, data);
    }
    
    /**
     * 服务器内部错误响应（无数据）
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(500, message, null);
    }
    
    /**
     * 服务不可用响应
     */
    public static <T> ApiResponse<T> serviceUnavailable(String message) {
        return new ApiResponse<>(503, message, null);
    }
}