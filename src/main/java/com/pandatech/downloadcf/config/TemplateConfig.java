package com.pandatech.downloadcf.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 模板配置类
 * 用于管理模板相关的配置参数
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "app.template")
public class TemplateConfig {
    
    /**
     * 模板下载基础URL
     */
    private String baseUrl = "http://localhost:8999/api/res/templ/loadtemple";
    
    /**
     * 默认模板配置
     */
    private DefaultTemplate defaultTemplate = new DefaultTemplate();
    
    /**
     * 屏幕类型映射配置
     */
    private ScreenTypeMapping screenTypeMapping = new ScreenTypeMapping();
    
    /**
     * MQTT配置
     */
    private MqttConfig mqtt = new MqttConfig();
    
    /**
     * 验证配置
     */
    private ValidationConfig validation = new ValidationConfig();

    /**
     * 根据硬件型号获取屏幕类型
     */
    public String getScreenTypeByModel(String model) {
        return screenTypeMapping.getModelToScreenType().getOrDefault(model, "1C");
    }

    /**
     * 根据屏幕类型获取标签类型
     */
    public String getTagTypeByScreenType(String screenType) {
        if (screenType == null || screenType.trim().isEmpty()) {
            return defaultTemplate.getTagType();
        }
        
        // 检查是否在映射表中存在
        if (screenTypeMapping.getTagTypeToScreenType().containsKey(screenType)) {
            return screenType;
        }
        
        return defaultTemplate.getTagType();
    }

    /**
     * 默认模板配置
     */
    @Data
    public static class DefaultTemplate {
        private String name = "U";
        private String size = "250, 122";
        private String tagType = "06";
        private int version = 10;
        private int height = 122;
        private int hext = 6;
        private int rgb = 3;
        private int wext = 0;
        private int width = 250;
        private String fontFamily = "阿里普惠";
        private String fontColor = "Black";
        private String background = "Transparent";
        private String borderColor = "Transparent";
        private int borderStyle = 0;
        private int fontStyle = 0;
        private int fontSpace = 0;
        private int textAlign = 0;
        private int dataKeyStyle = 0;
        private int fontSize = 12;
    }

    /**
     * 屏幕类型映射配置
     */
    @Data
    public static class ScreenTypeMapping {
        /**
         * 硬件型号到屏幕类型的映射
         */
        private Map<String, String> modelToScreenType = new HashMap<String, String>() {{
            put("ESP32_BW_2.13", "1C");
            put("ESP32_BWR_2.13", "2C");
            put("ESP32_BWY_2.13", "3C");
            put("ESP32_BW_2.9", "1C");
            put("ESP32_BWR_2.9", "2C");
            put("ESP32_BWY_2.9", "3C");
            put("ESP32_BW_4.2", "1C");
            put("ESP32_BWR_4.2", "2C");
            put("ESP32_BWY_4.2", "3C");
            put("ESP32_BW_7.5", "1C");
            put("ESP32_BWR_7.5", "2C");
            put("ESP32_BWY_7.5", "3C");
        }};

        /**
         * 标签类型到屏幕类型的映射
         */
        private Map<String, String> tagTypeToScreenType = new HashMap<String, String>() {{
            put("01", "1C");
            put("02", "2C");
            put("03", "3C");
            put("04", "1C");
            put("05", "2C");
            put("06", "3C");
            put("07", "1C");
            put("08", "2C");
            put("09", "3C");
            put("0A", "1C");
            put("0B", "2C");
            put("0C", "3C");
        }};
    }

    /**
     * MQTT配置
     */
    @Data
    public static class MqttConfig {
        /**
         * 是否启用MQTT消息发送
         */
        private boolean enabled = true;
        
        /**
         * 默认MQTT主题前缀
         */
        private String topicPrefix = "esl/template/";
        
        /**
         * 消息重试次数
         */
        private int retryCount = 3;
        
        /**
         * 消息超时时间（毫秒）
         */
        private long timeoutMs = 5000;
        
        /**
         * 是否包含模板内容
         */
        private boolean includeContent = true;
        
        /**
         * 是否压缩消息
         */
        private boolean compressMessage = false;
    }

    /**
     * 验证配置
     */
    @Data
    public static class ValidationConfig {
        /**
         * 是否启用模板验证
         */
        private boolean enabled = true;
        
        /**
         * 是否在验证失败时抛出异常
         */
        private boolean throwOnError = false;
        
        /**
         * 是否记录验证警告
         */
        private boolean logWarnings = true;
        
        /**
         * 是否记录验证错误
         */
        private boolean logErrors = true;
        
        /**
         * 最大模板大小（字节）
         */
        private long maxTemplateSize = 1024 * 1024; // 1MB
        
        /**
         * 允许的模板格式
         */
        private String[] allowedFormats = {"json", "xml", "html"};
    }
}