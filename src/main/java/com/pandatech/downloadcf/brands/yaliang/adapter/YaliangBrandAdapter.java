package com.pandatech.downloadcf.brands.yaliang.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.brands.BaseBrandAdapter;
import com.pandatech.downloadcf.dto.BrandOutputData;
import com.pandatech.downloadcf.dto.EslCompleteData;
import com.pandatech.downloadcf.entity.PandaProductWithBLOBs;
import com.pandatech.downloadcf.brands.yaliang.config.YaliangBrandConfig;
import com.pandatech.downloadcf.brands.yaliang.dto.YaliangRefreshMessage;
import com.pandatech.downloadcf.brands.yaliang.dto.YaliangRefreshRequest;
import com.pandatech.downloadcf.brands.yaliang.exception.YaliangException;
import com.pandatech.downloadcf.brands.yaliang.util.YaliangImageProcessor;
import com.pandatech.downloadcf.service.DataService;
import com.pandatech.downloadcf.service.TemplateRenderingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * YALIANG (雅量科技) 品牌适配器
 * 支持品牌编码 YALIANG001
 * 支持混合数字和字符串格式
 * 支持电子价签图片刷新功能
 * 注意：此类通过 BrandAdapterConfig 进行Bean注册，不使用 @Component 注解
 */
@Slf4j
@Component
public class YaliangBrandAdapter extends BaseBrandAdapter {
    
    private static final DecimalFormat PRICE_FORMAT = new DecimalFormat("0.00");
    private static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("0.##");
    
    @Autowired
    private YaliangBrandConfig config;
    
    @Autowired
    private YaliangImageProcessor imageProcessor;
    
    @Autowired
    private DataService dataService;
    
    @Autowired
    private TemplateRenderingService templateRenderingService;
    
    // 当前处理的设备尺寸
    private String currentDeviceSize;
    
    public YaliangBrandAdapter(ObjectMapper objectMapper) {
        super(objectMapper);
    }
    
    @Override
    public String getSupportedBrandCode() {
        return "YALIANG001";
    }
    
    @Override
    protected String getBrandName() {
        return "雅量科技";
    }
    
    @Override
    protected String getDefaultEslId() {
        return "06000000195B";
    }
    
    @Override
    protected boolean validateBrandSpecific(EslCompleteData completeData) {
        // YALIANG品牌特定验证逻辑
        if (!StringUtils.hasText(completeData.getProduct().getProductId())) {
            log.error("YALIANG品牌要求商品ID不能为空");
            return false;
        }
        
        return true;
    }
    
    @Override
    protected void buildDefaultDataMap(Map<String, Object> dataMap, PandaProductWithBLOBs product) {
        // YALIANG品牌默认映射
        dataMap.put("GOODS_CODE", formatStringField(product.getProductId()));
        dataMap.put("GOODS_NAME", formatStringField(product.getProductName()));
        dataMap.put("QRCODE", formatStringField(product.getProductQrcode()));
        dataMap.put("F_1", formatPriceAsNumber(product.getProductRetailPrice()));
    }
    
    @Override
    protected Object formatValueByFieldType(String fieldName, Object value) {
        if (value == null) {
            return isStringField(fieldName) ? "" : null;
        }
        
        // YALIANG品牌支持混合格式
        if (isPriceField(fieldName)) {
            return formatPriceAsNumber(value);
        } else if (isNumberField(fieldName)) {
            return formatAsNumber(value);
        } else if (isStockField(fieldName)) {
            return formatAsInteger(value);
        } else {
            // 字符串字段
            return formatStringField(value);
        }
    }
    
    @Override
    protected boolean isStringField(String fieldName) {
        // YALIANG品牌的字符串字段
        return fieldName != null && (
            fieldName.equals("F_02") || // 商品名称
            fieldName.equals("F_04") || // 品牌
            fieldName.equals("F_09") || // 分类
            fieldName.equals("F_10") || // 二维码
            fieldName.equals("F_11") || // 条形码
            fieldName.equals("F_12") || // 单位
            fieldName.equals("F_13") || // 状态
            fieldName.equals("F_15") || // 描述
            fieldName.equals("F_17") || // 扩展字段1
            fieldName.equals("F_18") || // 扩展字段2
            fieldName.equals("GOODS_CODE") ||
            fieldName.equals("GOODS_NAME") ||
            fieldName.equals("QRCODE")
        );
    }
    
    /**
     * 判断是否为价格字段
     */
    private boolean isPriceField(String fieldName) {
        return fieldName != null && (
            fieldName.equals("F_01") || // 零售价
            fieldName.equals("F_03") || // 批发价
            fieldName.equals("F_05") || // 成本价
            fieldName.equals("F_06") || // 折扣价
            fieldName.equals("F_08")    // 会员价
        );
    }
    
    /**
     * 判断是否为数字字段
     */
    private boolean isNumberField(String fieldName) {
        return fieldName != null && (
            fieldName.equals("F_07") || // 重量
            fieldName.equals("F_14")    // VIP价格
        );
    }
    
    /**
     * 判断是否为库存字段
     */
    private boolean isStockField(String fieldName) {
        return fieldName != null && fieldName.equals("F_16"); // 库存
    }
    
    /**
     * 格式化价格为数字
     */
    private BigDecimal formatPriceAsNumber(Object value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        
        try {
            if (value instanceof BigDecimal) {
                return (BigDecimal) value;
            } else if (value instanceof Number) {
                return new BigDecimal(value.toString());
            } else {
                return new BigDecimal(value.toString());
            }
        } catch (Exception e) {
            log.warn("价格格式化失败: {}", value, e);
            return BigDecimal.ZERO;
        }
    }
    
    /**
     * 格式化为数字
     */
    private BigDecimal formatAsNumber(Object value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        
        try {
            if (value instanceof BigDecimal) {
                return (BigDecimal) value;
            } else if (value instanceof Number) {
                return new BigDecimal(value.toString());
            } else {
                return new BigDecimal(value.toString());
            }
        } catch (Exception e) {
            log.warn("数字格式化失败: {}", value, e);
            return BigDecimal.ZERO;
        }
    }
    
    /**
     * 格式化为整数
     */
    private Integer formatAsInteger(Object value) {
        if (value == null) {
            return 0;
        }
        
        try {
            if (value instanceof Integer) {
                return (Integer) value;
            } else if (value instanceof Number) {
                return ((Number) value).intValue();
            } else {
                return Integer.parseInt(value.toString());
            }
        } catch (Exception e) {
            log.warn("整数格式化失败: {}", value, e);
            return 0;
        }
    }
    
    /**
     * 格式化字符串字段
     */
    private String formatStringField(Object value) {
        if (value == null) {
            return "";
        }
        return value.toString().trim();
    }
    
    @Override
    public BrandOutputData transform(EslCompleteData completeData) {
        log.info("开始转换雅量品牌数据: ESL_ID={}", completeData.getEsl().getEslId());
        
        try {
            // 从产品名称中提取设备尺寸信息
            String productName = completeData.getProduct().getProductName();
            if (productName != null && productName.contains("YALIANG")) {
                String deviceSize = extractDeviceSizeFromProductName(productName);
                if (deviceSize != null) {
                    log.info("从产品名称中提取到设备尺寸: {} -> {}", productName, deviceSize);
                    // 设置当前处理的设备尺寸，供后续图片处理使用
                    setCurrentDeviceSize(deviceSize);
                }
            }
            
            // 调用父类的基础转换逻辑
            BrandOutputData outputData = super.transform(completeData);
            
            // 解析雅量ESL ID格式
            YaliangEslIdInfo eslIdInfo = parseYaliangEslId(completeData.getEsl().getEslId());
            
            // 优化：只调用一次前端渲染API生成模板图片
            String templateBase64 = renderTemplateWithProductData(completeData);
            
            // 如果图片生成失败，使用默认图片
            if (templateBase64 == null || templateBase64.trim().isEmpty()) {
                templateBase64 = generateDefaultImageBase64();
                log.warn("前端渲染失败，使用默认图片");
            }
            
            // 创建雅量专用的MQTT消息
            Map<String, Object> yaliangMessage = createYaliangMqttMessage(eslIdInfo, templateBase64);
            
            // 将完整的雅量MQTT载荷数据存储到BrandOutputData.extJson中
            if (templateBase64 != null && !templateBase64.isEmpty()) {
                // 构建完整的雅量MQTT载荷数据，包含所有必要字段
                Map<String, Object> extJsonData = new HashMap<>();
                extJsonData.put("queueId", yaliangMessage.get("queueId"));
                extJsonData.put("deviceType", yaliangMessage.get("deviceType"));
                extJsonData.put("deviceCode", eslIdInfo.getDeviceCode());
                extJsonData.put("deviceMac", eslIdInfo.getDeviceMac());
                extJsonData.put("deviceVersion", yaliangMessage.get("deviceVersion"));
                extJsonData.put("refreshAction", yaliangMessage.get("refreshAction"));
                extJsonData.put("refreshArea", yaliangMessage.get("refreshArea"));
                extJsonData.put("content", yaliangMessage.get("content"));
                extJsonData.put("timestamp", yaliangMessage.get("timestamp"));
                
                // 只保存dataRef字段，符合MQTT消息格式要求
                extJsonData.put("dataRef", templateBase64);
                extJsonData.put("imageProcessed", true);
                
                try {
                    String extJsonString = objectMapper.writeValueAsString(extJsonData);
                    outputData.setExtJson(extJsonString);
                    log.info("已将完整的雅量MQTT载荷数据存储到BrandOutputData.extJson: deviceCode={}, 数据大小={}KB", 
                            eslIdInfo.getDeviceCode(), templateBase64.length() / 1024);
                } catch (Exception e) {
                    log.error("序列化extJson数据失败", e);
                }
            }
            
            // 添加雅量特有的数据到dataMap
            Map<String, Object> dataMap = outputData.getDataMap();
            if (dataMap == null) {
                dataMap = new HashMap<>();
                outputData.setDataMap(dataMap);
            }
            
            dataMap.put("mqttTopic", getMqttTopic(eslIdInfo.getDeviceCode()));
            dataMap.put("mqttPayload", yaliangMessage);
            dataMap.put("deviceCode", eslIdInfo.getDeviceCode());
            dataMap.put("deviceMac", eslIdInfo.getDeviceMac());
            
            log.info("雅量品牌数据转换完成: topic={}, deviceCode={}, deviceMac={}", 
                    dataMap.get("mqttTopic"), eslIdInfo.getDeviceCode(), eslIdInfo.getDeviceMac());
            
            return outputData;
            
        } catch (Exception e) {
            log.error("雅量品牌数据转换失败: ESL_ID={}", completeData.getEsl().getEslId(), e);
            throw new YaliangException(YaliangException.ErrorCodes.DATA_TRANSFORMATION_ERROR, 
                "数据转换失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 解析雅量ESL ID格式
     * 格式: CG101F6D-5414A7B9B046 (code-mac，MAC地址需要补充前缀00)
     */
    private YaliangEslIdInfo parseYaliangEslId(String eslId) {
        if (eslId == null || eslId.trim().isEmpty()) {
            throw new YaliangException(YaliangException.ErrorCodes.INVALID_ESL_ID, "ESL ID不能为空");
        }
        
        String cleanEslId = eslId.trim().toUpperCase();
        
        // 检查是否包含连字符
        if (!cleanEslId.contains("-")) {
            throw new YaliangException(YaliangException.ErrorCodes.INVALID_ESL_ID, 
                "雅量ESL ID格式错误，期望格式: CODE-MAC，实际: " + eslId);
        }
        
        String[] parts = cleanEslId.split("-");
        if (parts.length != 2) {
            throw new YaliangException(YaliangException.ErrorCodes.INVALID_ESL_ID, 
                "雅量ESL ID格式错误，期望格式: CODE-MAC，实际: " + eslId);
        }
        
        String deviceCode = parts[0]; // CG101F6D
        String rawMac = parts[1];     // 5414A7B9B046
        
        // MAC地址需要补充前缀"00"，确保格式为16位
        String deviceMac = rawMac;
        if (rawMac.length() == 12) {
            deviceMac = "00" + rawMac; // 补充前缀00，变为00125414A7B9B046
        } else if (rawMac.length() != 14) {
            log.warn("MAC地址长度异常: {}, 长度: {}", rawMac, rawMac.length());
        }
        
        // 验证格式
        if (deviceCode.isEmpty() || deviceMac.isEmpty()) {
            throw new YaliangException(YaliangException.ErrorCodes.INVALID_ESL_ID, 
                "雅量ESL ID的code或mac部分不能为空: " + eslId);
        }
        
        log.info("雅量ESL ID解析成功: 原始={}, 设备代码={}, 原始MAC={}, 处理后MAC={}", 
                eslId, deviceCode, rawMac, deviceMac);
        
        return new YaliangEslIdInfo(deviceCode, deviceMac);
    }
    
    /**
     * 从EXT_JSON中提取图片数据
     * 修复：只从已存储的dataRef或templateBase64字段获取图片数据，不再重复调用前端渲染API
     */
    private String extractTemplateBase64FromExtJson(String extJson) {
        // 添加详细的日志输出来调试EXT_JSON内容
        log.info("=== EXT_JSON调试信息开始 ===");
        log.info("EXT_JSON是否为null: {}", extJson == null);
        if (extJson != null) {
            log.info("EXT_JSON长度: {}", extJson.length());
            log.info("EXT_JSON前200字符: {}", extJson.length() > 200 ? extJson.substring(0, 200) + "..." : extJson);
            log.info("EXT_JSON是否为空字符串: {}", extJson.trim().isEmpty());
        }
        log.info("=== EXT_JSON调试信息结束 ===");
        
        if (extJson == null || extJson.trim().isEmpty()) {
            log.warn("EXT_JSON为空，无法提取图片数据");
            return null; // 返回null而不是默认图片，让调用方决定如何处理
        }
        
        try {
            Map<String, Object> extData = objectMapper.readValue(extJson, Map.class);
            log.info("EXT_JSON解析成功，包含的键: {}", extData.keySet());
            
            // 首先尝试从EXT_JSON中获取dataRef（新格式）
            String imageBase64 = (String) extData.get("dataRef");
            log.info("dataRef字段是否存在: {}", extData.containsKey("dataRef"));
            log.info("dataRef字段值是否为null: {}", imageBase64 == null);
            
            // 如果没有dataRef，尝试获取templateBase64（旧格式兼容）
            if (imageBase64 == null || imageBase64.trim().isEmpty()) {
                imageBase64 = (String) extData.get("templateBase64");
                log.info("templateBase64字段是否存在: {}", extData.containsKey("templateBase64"));
                log.info("templateBase64字段值是否为null: {}", imageBase64 == null);
            }
            
            if (imageBase64 != null && !imageBase64.trim().isEmpty()) {
                log.info("图片数据原始长度: {}", imageBase64.length());
                log.info("图片数据前100字符: {}", imageBase64.length() > 100 ? imageBase64.substring(0, 100) + "..." : imageBase64);
                
                // 移除data:image/png;base64,前缀（如果存在）
                if (imageBase64.startsWith("data:image/")) {
                    int commaIndex = imageBase64.indexOf(",");
                    if (commaIndex > 0) {
                        log.info("检测到data:image前缀，移除前缀，原长度: {}, 前缀长度: {}", imageBase64.length(), commaIndex + 1);
                        imageBase64 = imageBase64.substring(commaIndex + 1);
                        log.info("移除前缀后长度: {}", imageBase64.length());
                    }
                }
                
                log.info("成功从EXT_JSON获取图片数据，最终大小: {}KB", imageBase64.length() / 1024.0);
                return imageBase64;
            } else {
                log.warn("EXT_JSON中未找到图片数据");
                return null; // 返回null而不是默认图片，让调用方决定如何处理
            }
            
        } catch (Exception e) {
            log.error("解析EXT_JSON失败: {}", e.getMessage(), e);
            log.error("EXT_JSON内容: {}", extJson);
            return null; // 返回null而不是默认图片，让调用方决定如何处理
        }
    }
    
    /**
     * 生成默认的Base64图片数据
     * 这是一个简单的1x1像素透明PNG图片
     */
    private String generateDefaultImageBase64() {
        // 这是一个1x1像素的透明PNG图片的Base64编码
        String defaultBase64 = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNkYPhfDwAChAI9jU77yQAAAABJRU5ErkJggg==";
        log.info("使用默认图片Base64数据，大小: {}KB", defaultBase64.length() / 1024);
        return defaultBase64;
    }
    
    /**
     * 创建雅量专用的MQTT消息
     */
    private Map<String, Object> createYaliangMqttMessage(YaliangEslIdInfo eslIdInfo, String templateBase64) {
        Map<String, Object> message = new HashMap<>();
        
        // 基本信息
        message.put("queueId", generateQueueId());
        message.put("deviceType", config.getDefaultDeviceType());
        message.put("deviceCode", eslIdInfo.getDeviceCode());
        message.put("deviceMac", eslIdInfo.getDeviceMac());
        message.put("deviceVersion", "4.0.0");
        message.put("refreshAction", config.getDefaultRefreshAction());
        message.put("refreshArea", config.getDefaultRefreshArea());
        message.put("timestamp", System.currentTimeMillis()); // 添加时间戳字段
        
        // 内容列表
        List<Map<String, Object>> contentList = new ArrayList<>();
        Map<String, Object> contentItem = new HashMap<>();
        contentItem.put("dataType", config.getDataFormat().getImageDataType());
        contentItem.put("dataRef", templateBase64 != null ? templateBase64 : "");
        contentItem.put("layerEnd", config.getDataFormat().isLayerEnd());
        contentList.add(contentItem);
        
        message.put("content", contentList);
        
        log.info("雅量MQTT消息创建完成: queueId={}, deviceCode={}, deviceMac={}, timestamp={}, 图片大小={}KB", 
                message.get("queueId"), eslIdInfo.getDeviceCode(), eslIdInfo.getDeviceMac(), 
                message.get("timestamp"), templateBase64 != null ? templateBase64.length() / 1024 : 0);
        
        return message;
    }
    
    /**
     * 获取MQTT主题
     * 格式: yl-esl/XD010012/refresh/queue
     * 注意：这里使用固定的XD010012作为主题中间部分，而不是deviceCode
     */
    public String getMqttTopic(String deviceCode) {
        return config.getMqttTopicPrefix() + "/XD010012/refresh/queue";
    }
    
    /**
     * 雅量ESL ID信息类
     */
    private static class YaliangEslIdInfo {
        private final String deviceCode;
        private final String deviceMac;
        
        public YaliangEslIdInfo(String deviceCode, String deviceMac) {
            this.deviceCode = deviceCode;
            this.deviceMac = deviceMac;
        }
        
        public String getDeviceCode() {
            return deviceCode;
        }
        
        public String getDeviceMac() {
            return deviceMac;
        }
    }
    
    /**
     * 创建刷新消息
     *
     * @param request 刷新请求
     * @return 雅量刷新消息
     */
    public YaliangRefreshMessage createRefreshMessage(YaliangRefreshRequest request) {
        log.info("开始创建雅量刷新消息: deviceCode={}, deviceMac={}", 
                request.getDeviceCode(), request.getDeviceMac());
        
        try {
            // 验证请求参数
            validateRefreshRequest(request);
            
            // 获取完整的价签数据，包括模板信息
            String eslId = request.getDeviceCode() + "_" + request.getDeviceMac();
            EslCompleteData completeData = dataService.getEslCompleteData(eslId);
            
            String templateBase64 = null;
            
            // 尝试从模板的EXT_JSON中提取templateBase64
            if (completeData != null && completeData.getTemplate() != null) {
                String extJson = completeData.getTemplate().getExtJson();
                log.info("模板EXT_JSON内容: {}", extJson != null ? extJson.substring(0, Math.min(100, extJson.length())) + "..." : "null");
                
                if (extJson != null && !extJson.trim().isEmpty()) {
                    templateBase64 = extractTemplateBase64FromExtJson(extJson);
                    log.info("从EXT_JSON中提取templateBase64成功，大小: {}KB", 
                            templateBase64 != null ? templateBase64.length() / 1024 : 0);
                } else {
                    log.warn("模板EXT_JSON为空，无法提取templateBase64");
                }
            } else {
                log.warn("未找到价签对应的模板数据: eslId={}", eslId);
            }
            
            // 如果从模板中无法获取templateBase64，使用请求中的imageBase64作为备选
            if (templateBase64 == null || templateBase64.trim().isEmpty()) {
                templateBase64 = request.getImageBase64();
                log.info("使用请求中的imageBase64作为templateBase64");
            }
            
            // 创建刷新消息
            YaliangRefreshMessage message = new YaliangRefreshMessage();
            
            // 设置基本信息
            message.setQueueId(generateQueueId());
            message.setDeviceType(config.getDefaultDeviceType());
            message.setDeviceCode(request.getDeviceCode());
            message.setDeviceMac(request.getDeviceMac());
            message.setDeviceVersion("4.0.0");
            message.setRefreshAction(config.getDefaultRefreshAction());
            message.setRefreshArea(config.getDefaultRefreshArea());
            
            // 创建内容项
            List<YaliangRefreshMessage.ContentItem> contentList = new ArrayList<>();
            YaliangRefreshMessage.ContentItem contentItem = new YaliangRefreshMessage.ContentItem();
            contentItem.setDataType(config.getDataFormat().getImageDataType());
            contentItem.setDataRef(templateBase64 != null ? templateBase64 : "");
            contentItem.setLayerEnd(config.getDataFormat().isLayerEnd());
            contentList.add(contentItem);
            
            message.setContent(contentList);
            
            log.info("雅量刷新消息创建完成: queueId={}, deviceCode={}, deviceMac={}, templateBase64Size={}KB", 
                    message.getQueueId(), request.getDeviceCode(), request.getDeviceMac(),
                    templateBase64 != null ? templateBase64.length() / 1024 : 0);
            
            return message;
            
        } catch (Exception e) {
            log.error("创建雅量刷新消息失败: deviceCode={}, error={}", 
                    request.getDeviceCode(), e.getMessage(), e);
            throw new YaliangException(YaliangException.ErrorCodes.MESSAGE_CREATION_ERROR, 
                "创建刷新消息失败: " + e.getMessage(), 
                request.getDeviceCode(), request.getDeviceMac(), e);
        }
    }

    /**
     * 验证刷新请求参数
     *
     * @param request 刷新请求
     */
    public void validateRefreshRequest(YaliangRefreshRequest request) {
        if (request == null) {
            throw new YaliangException(YaliangException.ErrorCodes.VALIDATION_ERROR, "刷新请求不能为空");
        }
        
        // 验证设备编码
        if (config.getValidation().isRequireDeviceCode()) {
            if (request.getDeviceCode() == null || request.getDeviceCode().trim().isEmpty()) {
                throw new YaliangException(YaliangException.ErrorCodes.INVALID_DEVICE_CODE, 
                    "设备编码不能为空", request.getDeviceCode(), request.getDeviceMac());
            }
        }
        
        // 验证设备MAC地址
        if (config.getValidation().isRequireDeviceMac()) {
            if (request.getDeviceMac() == null || request.getDeviceMac().trim().isEmpty()) {
                throw new YaliangException(YaliangException.ErrorCodes.INVALID_DEVICE_MAC, 
                    "设备MAC地址不能为空", request.getDeviceCode(), request.getDeviceMac());
            }
        }
        
        // 验证设备尺寸
        if (request.getDeviceSize() != null && !request.getDeviceSize().trim().isEmpty()) {
            YaliangBrandConfig.DeviceSpec spec = getDeviceSpec(request.getDeviceSize());
            if (spec == null) {
                throw new YaliangException(YaliangException.ErrorCodes.INVALID_DEVICE_SIZE, 
                    "不支持的设备尺寸: " + request.getDeviceSize(), 
                    request.getDeviceCode(), request.getDeviceMac());
            }
        }
        
        // 验证图片格式和大小
        if (request.getImageBase64() != null && !request.getImageBase64().trim().isEmpty()) {
            try {
                imageProcessor.validateImageFormat(request.getImageBase64());
                imageProcessor.validateImageSize(request.getImageBase64());
            } catch (Exception e) {
                throw new YaliangException(YaliangException.ErrorCodes.IMAGE_PROCESSING_ERROR, 
                    "图片验证失败: " + e.getMessage(), 
                    request.getDeviceCode(), request.getDeviceMac(), e);
            }
        }
    }
    
    /**
     * 获取设备规格信息（带异常处理）
     *
     * @param deviceSize 设备尺寸
     * @return 设备规格
     */
    public YaliangBrandConfig.DeviceSpec getDeviceSpec(String deviceSize) {
        try {
            if (deviceSize == null || deviceSize.trim().isEmpty()) {
                deviceSize = config.getDeviceSpecs().getDefaultSpec();
            }
            
            YaliangBrandConfig.DeviceSpec spec = config.getDeviceSpecs().getSpecs().get(deviceSize);
            if (spec == null) {
                log.warn("未找到设备规格: {}, 使用默认规格: {}", deviceSize, config.getDeviceSpecs().getDefaultSpec());
                spec = config.getDeviceSpecs().getSpecs().get(config.getDeviceSpecs().getDefaultSpec());
            }
            
            if (spec == null) {
                throw new YaliangException(YaliangException.ErrorCodes.CONFIG_ERROR, 
                    "配置错误：无法获取默认设备规格");
            }
            
            return spec;
        } catch (Exception e) {
            if (e instanceof YaliangException) {
                throw e;
            }
            throw new YaliangException(YaliangException.ErrorCodes.CONFIG_ERROR, 
                "获取设备规格失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 生成队列ID（带异常处理）
     * 使用时间戳和随机数结合，确保在有效范围内生成唯一的队列ID
     *
     * @return 队列ID
     */
    public int generateQueueId() {
        try {
            int minQueueId = config.getValidation().getMinQueueId();
            int maxQueueId = config.getValidation().getMaxQueueId();
            
            // 确保配置的范围有效
            if (minQueueId <= 0) {
                minQueueId = 1000;
            }
            if (maxQueueId <= minQueueId) {
                maxQueueId = 9999;
            }
            
            // 使用时间戳的低位和随机数结合生成队列ID
            long timestamp = System.currentTimeMillis();
            int timeBasedPart = (int) (timestamp % 1000); // 取时间戳的低3位
            int randomPart = ThreadLocalRandom.current().nextInt(1000); // 生成0-999的随机数
            
            // 组合时间戳和随机数，然后映射到有效范围内
            int combinedValue = (timeBasedPart + randomPart) % (maxQueueId - minQueueId + 1);
            int queueId = minQueueId + combinedValue;
            
            log.debug("生成队列ID: {} (范围: {}-{}, 时间戳部分: {}, 随机部分: {})", 
                     queueId, minQueueId, maxQueueId, timeBasedPart, randomPart);
            
            return queueId;
        } catch (Exception e) {
            throw new YaliangException(YaliangException.ErrorCodes.QUEUE_ID_GENERATION_ERROR, 
                "生成队列ID失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 获取支持的设备规格列表
     */
    public List<String> getSupportedDeviceSizes() {
        return new ArrayList<>(config.getDeviceSpecs().getSpecs().keySet());
    }
    
    /**
     * 获取设备规格信息
     */
    public YaliangBrandConfig.DeviceSpec getDeviceSpecInfo(String deviceSize) {
        return config.getDeviceSpecs().getSpecs().get(deviceSize);
    }
    
    /**
     * 从产品名称中提取设备尺寸
     * 例如: YALIANG2.9T -> 2.9
     */
    private String extractDeviceSizeFromProductName(String productName) {
        if (productName == null || !productName.contains("YALIANG")) {
            return null;
        }
        
        // 匹配YALIANG后面的数字和小数点
        String pattern = "YALIANG([0-9]+\\.?[0-9]*)";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(productName);
        
        if (m.find()) {
            return m.group(1);
        }
        
        return null;
    }
    
    /**
     * 设置当前处理的设备尺寸
     */
    private void setCurrentDeviceSize(String deviceSize) {
        this.currentDeviceSize = deviceSize;
        log.info("设置当前处理的设备尺寸: {}", deviceSize);
    }
    
    /**
     * 使用前端渲染API生成图片
     */
    private String renderTemplateWithProductData(EslCompleteData completeData) {
        log.info("准备调用前端渲染服务: templateId={}, productId={}", 
                completeData.getTemplate().getId(), completeData.getProduct().getId());
        
        try {
            // 调用前端渲染API生成图片
            String templateBase64 = templateRenderingService.renderTemplate(
                completeData.getTemplate().getId(), 
                completeData.getProduct().getId()
            );
            
            if (templateBase64 != null && !templateBase64.trim().isEmpty()) {
                log.info("前端渲染API调用成功，原始图片大小: {}KB", templateBase64.length() / 1024.0);
                
                // 解析雅量ESL ID格式以获取设备信息
                YaliangEslIdInfo eslIdInfo = parseYaliangEslId(completeData.getEsl().getEslId());
                
                // 获取设备规格进行图片处理
                String deviceSize = currentDeviceSize != null ? currentDeviceSize : mapDeviceCodeToSize(eslIdInfo.getDeviceCode());
                if (deviceSize != null) {
                    YaliangBrandConfig.DeviceSpec deviceSpec = getDeviceSpec(deviceSize);
                    if (deviceSpec != null) {
                        log.info("找到设备规格: deviceCode={}, deviceSize={}, resolution={}x{}, rotation={}°", 
                            eslIdInfo.getDeviceCode(), deviceSize, 
                            deviceSpec.getWidth(), deviceSpec.getHeight(), deviceSpec.getRotation());
                        
                        // 处理图片 - 关键修复：确保异常被正确处理
                        try {
                            log.info("开始处理图片: 原始大小={}KB, 目标分辨率={}x{}, 旋转角度={}°", 
                                templateBase64.length() / 1024.0,
                                deviceSpec.getWidth(), deviceSpec.getHeight(), deviceSpec.getRotation());
                            
                            String processedBase64 = imageProcessor.processImage(templateBase64, deviceSpec, config);
                            
                            if (processedBase64 != null && !processedBase64.trim().isEmpty()) {
                                log.info("图片处理完成，处理后大小: {}KB，目标分辨率: {}x{}，旋转: {}°", 
                                    processedBase64.length() / 1024.0, 
                                    deviceSpec.getWidth(), deviceSpec.getHeight(), deviceSpec.getRotation());
                                
                                return processedBase64;
                            } else {
                                log.error("图片处理返回空结果，使用原始图片");
                                return templateBase64;
                            }
                        } catch (Exception e) {
                            log.error("图片处理失败，错误详情: {}", e.getMessage(), e);
                            log.error("图片处理失败，使用原始图片: deviceCode={}, deviceSize={}", 
                                eslIdInfo.getDeviceCode(), deviceSize);
                            // 不要返回原始图片，而是生成默认图片，确保数据格式正确
                            return generateDefaultImageBase64();
                        }
                    } else {
                        log.warn("不支持的设备规格: {}", deviceSize);
                        return generateDefaultImageBase64();
                    }
                } else {
                    log.warn("无法确定设备尺寸: deviceCode={}", eslIdInfo.getDeviceCode());
                    return generateDefaultImageBase64();
                }
            } else {
                log.warn("前端渲染API返回空图片");
                return generateDefaultImageBase64();
            }
            
        } catch (Exception e) {
            log.error("前端渲染API调用失败: {}", e.getMessage(), e);
            return generateDefaultImageBase64();
        }
    }
    
    /**
     * 根据设备代码映射到设备尺寸
     */
    private String mapDeviceCodeToSize(String deviceCode) {
        if (deviceCode == null) return null;
        
        if (deviceCode.startsWith("CG101") || deviceCode.startsWith("LE2000KC")) return "2.13";
        if (deviceCode.startsWith("CG102")) return "2.9";
        if (deviceCode.startsWith("CG103")) return "4.2";
        if (deviceCode.startsWith("CG104")) return "1.54";
        if (deviceCode.startsWith("CG105")) return "2.66";
        if (deviceCode.startsWith("CG106")) return "3.5";
        if (deviceCode.startsWith("CG107")) return "5.83";
        if (deviceCode.startsWith("CG108")) return "7.5";
        if (deviceCode.startsWith("CG109")) return "10.2";
        
        return "2.13"; // 默认规格
    }
}