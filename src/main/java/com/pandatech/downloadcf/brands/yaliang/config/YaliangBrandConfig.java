package com.pandatech.downloadcf.brands.yaliang.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * YALIANG (雅量科技) 品牌配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "app.esl.brands.yaliang001")
public class YaliangBrandConfig {
    
    /**
     * 品牌名称
     */
    private String name = "雅量科技";
    
    /**
     * 品牌编码
     */
    private String code = "YALIANG001";
    
    /**
     * 是否启用
     */
    private boolean enabled = true;
    
    /**
     * 执行器类型
     */
    private String executorType = "mqtt";
    
    /**
     * 模板格式
     */
    private String templateFormat = "json";
    
    /**
     * 默认ESL设备ID
     */
    private String defaultEslId = "06000000195B";
    
    /**
     * MQTT主题前缀
     */
    private String mqttTopicPrefix = "esl/yaliang";
    
    /**
     * 数据格式配置
     */
    private DataFormat dataFormat = new DataFormat();
    
    /**
     * 验证配置
     */
    private Validation validation = new Validation();
    
    @Data
    public static class DataFormat {
        /**
         * 价格格式
         */
        private String priceFormat = "0.00";
        
        /**
         * 数字格式
         */
        private String numberFormat = "0.##";
        
        /**
         * 整数格式
         */
        private String integerFormat = "0";
        
        /**
         * 字符串最大长度
         */
        private int maxStringLength = 255;
        
        /**
         * 是否支持混合类型输出
         */
        private boolean mixedTypeOutput = true;
        
        /**
         * 价格字段是否返回数字类型
         */
        private boolean priceAsNumber = true;
    }
    
    @Data
    public static class Validation {
        /**
         * 是否要求商品ID不为空
         */
        private boolean requireProductId = true;
        
        /**
         * 是否要求商品名称不为空
         */
        private boolean requireProductName = false;
        
        /**
         * 是否要求价格大于0
         */
        private boolean requirePositivePrice = false;
        
        /**
         * 最大字段数量
         */
        private int maxFieldCount = 50;
        
        /**
         * 是否允许空值
         */
        private boolean allowNullValues = true;
    }
}