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
    
    @Data
    public static class DefaultTemplate {
        private String name = "U";
        private String size = "250, 122";
        private String tagType = "06";
        private int version = 10;
        private String height = "122";
        private String hext = "6";
        private String rgb = "3";
        private String wext = "0";
        private String width = "250";
        private String fontFamily = "Zfull-GB";
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
         * TagType到屏幕类型的映射
         */
        private Map<String, String> tagTypeToScreenType = new HashMap<String, String>() {{
            put("06", "1C");
            put("07", "2C");
            put("08", "3C");
            put("09", "1C");
            put("10", "2C");
            put("11", "3C");
            put("12", "1C");
            put("13", "2C");
            put("14", "3C");
            put("15", "1C");
        }};
    }
    
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
         * 是否包含模板内容在消息中
         */
        private boolean includeTemplateContent = true;
        
        /**
         * 是否压缩模板内容
         */
        private boolean compressTemplate = false;
    }
    
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
         * 最大允许的Items数量
         */
        private int maxItemsCount = 100;
        
        /**
         * 最大模板尺寸（像素）
         */
        private int maxTemplateWidth = 1000;
        private int maxTemplateHeight = 1000;
    }
    
    /**
     * 获取屏幕类型对应的TagType
     */
    public String getTagTypeByScreenType(String screenType) {
        for (Map.Entry<String, String> entry : screenTypeMapping.getTagTypeToScreenType().entrySet()) {
            if (entry.getValue().equals(screenType)) {
                return entry.getKey();
            }
        }
        return defaultTemplate.getTagType();
    }
    
    /**
     * 获取TagType对应的屏幕类型
     */
    public String getScreenTypeByTagType(String tagType) {
        return screenTypeMapping.getTagTypeToScreenType().getOrDefault(tagType, "1C");
    }
    
    /**
     * 获取硬件型号对应的屏幕类型
     */
    public String getScreenTypeByModel(String model) {
        return screenTypeMapping.getModelToScreenType().getOrDefault(model, "1C");
    }
}