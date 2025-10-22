package com.pandatech.downloadcf.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 前端渲染服务配置类
 * 用于管理前端渲染API的配置参数
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "app.template.rendering")
public class TemplateRenderingConfig {
    
    /**
     * API配置
     */
    private ApiConfig api = new ApiConfig();
    
    /**
     * API配置内部类
     */
    @Data
    public static class ApiConfig {
        /**
         * 前端渲染服务URL
         */
        private String url = "http://localhost:3000/api/render";
        
        /**
         * API密钥
         */
        private String key = "your-api-key-here";
        
        /**
         * 是否启用前端渲染服务
         */
        private boolean enabled = true;
        
        /**
         * 请求超时时间（毫秒）
         */
        private int timeout = 30000;
        
        /**
         * 重试次数
         */
        private int retryCount = 3;
        
        /**
         * 连接超时时间（毫秒）
         */
        private int connectTimeout = 10000;
        
        /**
         * 读取超时时间（毫秒）
         */
        private int readTimeout = 30000;
    }
}