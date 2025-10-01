package com.pandatech.downloadcf.brands.yaliang.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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
    private String mqttTopicPrefix = "yl-esl";
    
    /**
     * 默认设备类型
     */
    private int defaultDeviceType = 1;
    
    /**
     * 默认刷新动作
     */
    private int defaultRefreshAction = 3;
    
    /**
     * 默认刷新区域
     */
    private int defaultRefreshArea = 1;
    
    /**
     * 设备规格配置
     */
    private DeviceSpecs deviceSpecs = new DeviceSpecs();
    
    /**
     * 数据格式配置
     */
    private DataFormat dataFormat = new DataFormat();
    
    /**
     * 验证配置
     */
    private Validation validation = new Validation();
    
    /**
     * 设备规格配置类
     */
    @Data
    public static class DeviceSpecs {
        /**
         * 设备规格映射 - 尺寸 -> 规格信息
         */
        private Map<String, DeviceSpec> specs = new HashMap<String, DeviceSpec>() {{
            // 1.54寸屏幕规格
            put("1.54", new DeviceSpec(200, 200, 90, true));
            put("1.54_old", new DeviceSpec(152, 152, 90, false));
            
            // 2.13寸屏幕规格
            put("2.13", new DeviceSpec(250, 122, 90, true));
            put("2.13_old", new DeviceSpec(212, 104, 90, false));
            
            // 2.66寸屏幕规格
            put("2.66", new DeviceSpec(296, 152, 90, true));
            put("2.66_old", new DeviceSpec(296, 160, 90, false));
            
            // 2.9寸屏幕规格
            put("2.9", new DeviceSpec(296, 128, 90, true));
            
            // 3.5寸屏幕规格
            put("3.5", new DeviceSpec(384, 184, 90, true));
            
            // 4.2寸屏幕规格
            put("4.2", new DeviceSpec(400, 300, 0, true));
            
            // 5.83寸屏幕规格
            put("5.83", new DeviceSpec(648, 480, 0, true));
            
            // 7.5寸屏幕规格
            put("7.5", new DeviceSpec(800, 480, 0, true));
            put("7.5_old", new DeviceSpec(640, 384, 0, false));
            
            // 10.2寸屏幕规格
            put("10.2", new DeviceSpec(960, 640, 0, true));
        }};
        
        /**
         * 默认规格（当无法识别设备时使用）
         */
        private String defaultSpec = "2.13";
    }
    
    /**
     * 设备规格信息
     */
    @Data
    public static class DeviceSpec {
        /**
         * 宽度
         */
        private int width;
        
        /**
         * 高度
         */
        private int height;
        
        /**
         * 旋转角度
         */
        private int rotation;
        
        /**
         * 是否为新版本（带*标记）
         */
        private boolean isNewVersion;
        
        public DeviceSpec() {}
        
        public DeviceSpec(int width, int height, int rotation, boolean isNewVersion) {
            this.width = width;
            this.height = height;
            this.rotation = rotation;
            this.isNewVersion = isNewVersion;
        }
        
        /**
         * 获取分辨率字符串
         */
        public String getResolution() {
            return width + "×" + height + (isNewVersion ? "*" : "");
        }
    }
    
    @Data
    public static class DataFormat {
        /**
         * 图片格式
         */
        private String imageFormat = "PNG";
        
        /**
         * Base64编码时是否包含头部
         */
        private boolean includeBase64Header = false;
        
        /**
         * 数据类型 - 图片
         */
        private int imageDataType = 3;
        
        /**
         * 是否为最后一层
         */
        private boolean layerEnd = true;
        
        /**
         * 图片质量（0-100）
         */
        private int imageQuality = 90;
        
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
         * 是否要求设备编码不为空
         */
        private boolean requireDeviceCode = true;
        
        /**
         * 是否要求设备MAC不为空
         */
        private boolean requireDeviceMac = true;
        
        /**
         * 是否验证图片格式
         */
        private boolean validateImageFormat = true;
        
        /**
         * 最大图片大小（字节）
         */
        private long maxImageSize = 1024 * 1024; // 1MB
        
        /**
         * 队列ID范围
         */
        private int minQueueId = 1000;
        private int maxQueueId = 9999;
        
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