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
            // 在转换开始时，先从产品和模板信息中提取设备尺寸
            String extractedDeviceSize = extractDeviceSizeFromProductAndTemplate(completeData);
            if (extractedDeviceSize != null) {
                setCurrentProcessingDeviceSize(extractedDeviceSize);
                log.info("设置当前处理的设备尺寸: {}", extractedDeviceSize);
            }
            
            // 调用父类的基础转换逻辑
            BrandOutputData outputData = super.transform(completeData);
            
            // 解析雅量ESL ID格式 (CG101F6D-00125414A7B9B046)
            YaliangEslIdInfo eslIdInfo = parseYaliangEslId(completeData.getEsl().getEslId());
            
            // 使用前端渲染API生成模板图片
            String templateBase64 = renderTemplateWithProductData(completeData);
            
            // 创建雅量专用的MQTT消息
            Map<String, Object> yaliangMessage = createYaliangMqttMessage(eslIdInfo, templateBase64);
            
            // 将图片数据存储到BrandOutputData.extJson中，供MessageProducerService使用
            if (templateBase64 != null) {
                try {
                    Map<String, Object> extData = new HashMap<>();
                    extData.put("dataRef", templateBase64);
                    ObjectMapper mapper = new ObjectMapper();
                    outputData.setExtJson(mapper.writeValueAsString(extData));
                    log.info("已将图片数据存储到BrandOutputData.extJson: deviceCode={}, 数据大小={}KB", 
                            eslIdInfo.getDeviceCode(), templateBase64.length() / 1024);
                } catch (Exception e) {
                    log.error("存储图片数据到BrandOutputData.extJson失败: deviceCode={}, error={}", 
                            eslIdInfo.getDeviceCode(), e.getMessage());
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
        } finally {
            // 清理ThreadLocal，避免内存泄漏
            clearCurrentProcessingDeviceSize();
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
     * 使用前端渲染API生成模板图片并进行设备适配处理
     * @param completeData 完整的价签数据
     * @return 经过设备适配处理的base64编码图片数据
     */
    private String renderTemplateWithProductData(EslCompleteData completeData) {
        try {
            if (completeData == null || completeData.getTemplate() == null) {
                log.warn("价签数据或模板为空，无法渲染");
                return generateDefaultImageBase64();
            }

            // 调用前端渲染API获取原始图片
            String templateId = completeData.getTemplate() != null ? completeData.getTemplate().getId() : "default";
            String productId = completeData.getProduct() != null ? completeData.getProduct().getId() : "default";
            
            String rawBase64Image = templateRenderingService.renderTemplate(templateId, productId);
            
            if (!StringUtils.hasText(rawBase64Image)) {
                log.warn("前端渲染API返回空结果，使用默认图片");
                rawBase64Image = generateDefaultImageBase64();
            } else {
                log.info("前端渲染API调用成功，原始图片大小: {}KB", rawBase64Image.length() / 1024);
            }
            
            // 获取设备规格进行图片处理
            YaliangEslIdInfo eslIdInfo = parseYaliangEslId(completeData.getEsl().getEslId());
            YaliangBrandConfig.DeviceSpec deviceSpec = getDeviceSpecByCode(eslIdInfo.getDeviceCode());
            
            if (deviceSpec != null) {
                // 使用YaliangImageProcessor进行图片处理（分辨率调整、旋转等）
                String processedBase64 = imageProcessor.processImage(rawBase64Image, deviceSpec, config);
                log.info("图片处理完成，处理后大小: {}KB，目标分辨率: {}x{}，旋转: {}°", 
                        processedBase64.length() / 1024, deviceSpec.getWidth(), deviceSpec.getHeight(), deviceSpec.getRotation());
                return processedBase64;
            } else {
                log.warn("未找到设备规格，返回原始图片: deviceCode={}", eslIdInfo.getDeviceCode());
                return rawBase64Image;
            }
            
        } catch (Exception e) {
            log.error("渲染和处理图片失败: {}", e.getMessage(), e);
            return generateDefaultImageBase64();
        }
    }
    
    /**
     * 根据设备代码获取设备规格
     * @param deviceCode 设备代码
     * @return 设备规格，如果未找到则返回null
     */
    private YaliangBrandConfig.DeviceSpec getDeviceSpecByCode(String deviceCode) {
        try {
            if (config == null || config.getDeviceSpecs() == null) {
                log.warn("雅量品牌配置或设备规格配置为空");
                return null;
            }
            
            Map<String, YaliangBrandConfig.DeviceSpec> specs = config.getDeviceSpecs().getSpecs();
            if (specs == null || specs.isEmpty()) {
                log.warn("设备规格映射为空");
                return null;
            }
            
            // 根据设备代码查找对应的规格
            String deviceSize = mapDeviceCodeToSize(deviceCode);
            YaliangBrandConfig.DeviceSpec spec = specs.get(deviceSize);
            
            if (spec != null) {
                log.info("找到设备规格: deviceCode={}, deviceSize={}, resolution={}x{}, rotation={}°", 
                        deviceCode, deviceSize, spec.getWidth(), spec.getHeight(), spec.getRotation());
                return spec;
            } else {
                // 使用默认规格
                String defaultSpec = config.getDeviceSpecs().getDefaultSpec();
                spec = specs.get(defaultSpec);
                if (spec != null) {
                    log.info("使用默认设备规格: deviceCode={}, defaultSize={}, resolution={}x{}, rotation={}°", 
                            deviceCode, defaultSpec, spec.getWidth(), spec.getHeight(), spec.getRotation());
                    return spec;
                }
            }
            
            log.warn("未找到匹配的设备规格: deviceCode={}", deviceCode);
            return null;
            
        } catch (Exception e) {
            log.error("获取设备规格失败: deviceCode={}", deviceCode, e);
            return null;
        }
    }
    
    /**
     * 将设备代码映射到设备尺寸
     * 优先从模板类别中提取设备规格，然后根据设备代码进行映射
     * @param deviceCode 设备代码
     * @return 设备尺寸标识
     */
    private String mapDeviceCodeToSize(String deviceCode) {
        try {
            // 首先尝试从当前处理的数据中获取模板类别信息
            String deviceSizeFromTemplate = extractDeviceSizeFromCurrentContext();
            if (deviceSizeFromTemplate != null) {
                log.info("从模板类别中提取到设备规格: {}", deviceSizeFromTemplate);
                return deviceSizeFromTemplate;
            }
            
            if (deviceCode == null) {
                return config.getDeviceSpecs() != null ? config.getDeviceSpecs().getDefaultSpec() : "2.13";
            }
            
            // 根据设备代码的特征来判断尺寸 - 修正映射键名
            if (deviceCode.startsWith("CG101")) {
                return "2.13"; // 2.13寸屏幕 - 使用配置中的正确键名
            } else if (deviceCode.startsWith("CG102")) {
                return "2.9";  // 2.9寸屏幕
            } else if (deviceCode.startsWith("CG103")) {
                return "4.2";  // 4.2寸屏幕
            } else if (deviceCode.startsWith("CG104")) {
                return "1.54"; // 1.54寸屏幕
            } else if (deviceCode.startsWith("CG105")) {
                return "2.66"; // 2.66寸屏幕
            } else if (deviceCode.startsWith("CG106")) {
                return "3.5";  // 3.5寸屏幕
            } else if (deviceCode.startsWith("CG107")) {
                return "5.83"; // 5.83寸屏幕
            } else if (deviceCode.startsWith("CG108")) {
                return "7.5";  // 7.5寸屏幕
            } else if (deviceCode.startsWith("CG109")) {
                return "10.2"; // 10.2寸屏幕
            }
            
            log.warn("未识别的设备代码: {}，使用默认规格", deviceCode);
            return config.getDeviceSpecs() != null ? config.getDeviceSpecs().getDefaultSpec() : "2.13";
            
        } catch (Exception e) {
            log.error("设备代码映射失败: deviceCode={}", deviceCode, e);
            return config.getDeviceSpecs() != null ? config.getDeviceSpecs().getDefaultSpec() : "2.13";
        }
    }
    
    /**
     * 从当前处理上下文中提取设备尺寸信息
     * 通过分析产品名称、模板类别等信息来确定设备规格
     */
    private String extractDeviceSizeFromCurrentContext() {
        try {
            // 这里可以通过ThreadLocal或其他方式获取当前处理的数据
            // 由于架构限制，我们需要在transform方法中设置上下文信息
            String contextDeviceSize = getCurrentProcessingDeviceSize();
            if (contextDeviceSize != null) {
                return contextDeviceSize;
            }
            
            return null;
        } catch (Exception e) {
            log.debug("从上下文提取设备尺寸失败: {}", e.getMessage());
            return null;
        }
    }
    
    // ThreadLocal用于在处理过程中传递设备尺寸信息
    private static final ThreadLocal<String> CURRENT_DEVICE_SIZE = new ThreadLocal<>();
    
    /**
     * 设置当前处理的设备尺寸
     */
    private void setCurrentProcessingDeviceSize(String deviceSize) {
        CURRENT_DEVICE_SIZE.set(deviceSize);
    }
    
    /**
     * 获取当前处理的设备尺寸
     */
    private String getCurrentProcessingDeviceSize() {
        return CURRENT_DEVICE_SIZE.get();
    }
    
    /**
     * 清理当前处理的设备尺寸
     */
    private void clearCurrentProcessingDeviceSize() {
        CURRENT_DEVICE_SIZE.remove();
    }
    
    /**
     * 从产品信息和模板信息中智能提取设备尺寸
     */
    private String extractDeviceSizeFromProductAndTemplate(EslCompleteData completeData) {
        try {
            // 1. 从产品名称中提取尺寸信息
            if (completeData.getProduct() != null && completeData.getProduct().getProductName() != null) {
                String productName = completeData.getProduct().getProductName();
                String sizeFromProductName = extractSizeFromProductName(productName);
                if (sizeFromProductName != null) {
                    log.info("从产品名称中提取到设备尺寸: {} -> {}", productName, sizeFromProductName);
                    return sizeFromProductName;
                }
            }
            
            // 2. 从模板类别中提取尺寸信息
            if (completeData.getTemplate() != null && completeData.getTemplate().getCategory() != null) {
                String category = completeData.getTemplate().getCategory();
                String sizeFromCategory = extractSizeFromCategory(category);
                if (sizeFromCategory != null) {
                    log.info("从模板类别中提取到设备尺寸: {} -> {}", category, sizeFromCategory);
                    return sizeFromCategory;
                }
            }
            
            return null;
        } catch (Exception e) {
            log.error("从产品和模板信息中提取设备尺寸失败", e);
            return null;
        }
    }
    
    /**
     * 从产品名称中提取尺寸信息
     */
    private String extractSizeFromProductName(String productName) {
        if (productName == null) {
            return null;
        }
        
        String name = productName.toUpperCase();
        
        // 匹配各种尺寸格式
        if (name.contains("4.2T") || name.contains("4.2寸") || name.contains("4.2INCH")) {
            return "4.2";
        } else if (name.contains("2.13T") || name.contains("2.13寸") || name.contains("2.13INCH")) {
            return "2.13";
        } else if (name.contains("2.9T") || name.contains("2.9寸") || name.contains("2.9INCH")) {
            return "2.9";
        } else if (name.contains("1.54T") || name.contains("1.54寸") || name.contains("1.54INCH")) {
            return "1.54";
        } else if (name.contains("2.66T") || name.contains("2.66寸") || name.contains("2.66INCH")) {
            return "2.66";
        } else if (name.contains("3.5T") || name.contains("3.5寸") || name.contains("3.5INCH")) {
            return "3.5";
        } else if (name.contains("5.83T") || name.contains("5.83寸") || name.contains("5.83INCH")) {
            return "5.83";
        } else if (name.contains("7.5T") || name.contains("7.5寸") || name.contains("7.5INCH")) {
            return "7.5";
        } else if (name.contains("10.2T") || name.contains("10.2寸") || name.contains("10.2INCH")) {
            return "10.2";
        }
        
        return null;
    }
    
    /**
     * 从模板类别中提取尺寸信息
     */
    private String extractSizeFromCategory(String category) {
        if (category == null) {
            return null;
        }
        
        String cat = category.toUpperCase();
        
        // 匹配模板类别中的尺寸信息
        if (cat.contains("4.2") || cat.contains("4_2")) {
            return "4.2";
        } else if (cat.contains("2.13") || cat.contains("2_13")) {
            return "2.13";
        } else if (cat.contains("2.9") || cat.contains("2_9")) {
            return "2.9";
        } else if (cat.contains("1.54") || cat.contains("1_54")) {
            return "1.54";
        } else if (cat.contains("2.66") || cat.contains("2_66")) {
            return "2.66";
        } else if (cat.contains("3.5") || cat.contains("3_5")) {
            return "3.5";
        } else if (cat.contains("5.83") || cat.contains("5_83")) {
            return "5.83";
        } else if (cat.contains("7.5") || cat.contains("7_5")) {
            return "7.5";
        } else if (cat.contains("10.2") || cat.contains("10_2")) {
            return "10.2";
        }
        
        return null;
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
        
        // 内容列表
        List<Map<String, Object>> contentList = new ArrayList<>();
        Map<String, Object> contentItem = new HashMap<>();
        contentItem.put("dataType", config.getDataFormat().getImageDataType());
        contentItem.put("dataRef", templateBase64 != null ? templateBase64 : "");
        contentItem.put("layerEnd", config.getDataFormat().isLayerEnd());
        contentList.add(contentItem);
        
        message.put("content", contentList);
        
        log.info("雅量MQTT消息创建完成: queueId={}, deviceCode={}, deviceMac={}, 图片大小={}KB", 
                message.get("queueId"), eslIdInfo.getDeviceCode(), eslIdInfo.getDeviceMac(), 
                templateBase64 != null ? templateBase64.length() / 1024 : 0);
        
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
            
            // 使用前端渲染API生成模板图片
            if (completeData != null && completeData.getTemplate() != null) {
                templateBase64 = renderTemplateWithProductData(completeData);
                log.info("从前端渲染API获取templateBase64成功，大小: {}KB", 
                        templateBase64 != null ? templateBase64.length() / 1024 : 0);
            } else {
                log.warn("价签数据不完整，无法调用前端渲染API: eslId={}", eslId);
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
     *
     * @return 队列ID
     */
    public int generateQueueId() {
        try {
            int queueId = (int) (System.currentTimeMillis() % 100000);
            
            // 验证队列ID范围
            if (config.getValidation().getMinQueueId() > 0 && queueId < config.getValidation().getMinQueueId()) {
                queueId = config.getValidation().getMinQueueId();
            }
            if (config.getValidation().getMaxQueueId() > 0 && queueId > config.getValidation().getMaxQueueId()) {
                queueId = config.getValidation().getMaxQueueId();
            }
            
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
}