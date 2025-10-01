package com.pandatech.downloadcf.brands.yaliang.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.brands.BaseBrandAdapter;
import com.pandatech.downloadcf.dto.EslCompleteData;
import com.pandatech.downloadcf.entity.PandaProductWithBLOBs;
import com.pandatech.downloadcf.brands.yaliang.config.YaliangBrandConfig;
import com.pandatech.downloadcf.brands.yaliang.dto.YaliangRefreshMessage;
import com.pandatech.downloadcf.brands.yaliang.dto.YaliangRefreshRequest;
import com.pandatech.downloadcf.brands.yaliang.exception.YaliangException;
import com.pandatech.downloadcf.brands.yaliang.util.YaliangImageProcessor;
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
    
    /**
     * 创建雅量刷新消息
     *
     * @param request 刷新请求
     * @return 雅量刷新消息
     */
    public YaliangRefreshMessage createRefreshMessage(YaliangRefreshRequest request) {
        log.info("创建雅量刷新消息: deviceCode={}, deviceMac={}", request.getDeviceCode(), request.getDeviceMac());
        
        // 验证请求参数
        validateRefreshRequest(request);
        
        // 获取设备规格
        YaliangBrandConfig.DeviceSpec deviceSpec = getDeviceSpec(request.getDeviceSize());
        
        // 处理图片
        String processedImageBase64 = imageProcessor.processImage(request.getImageBase64(), deviceSpec, config);
        
        // 构建刷新消息
        YaliangRefreshMessage message = new YaliangRefreshMessage();
        message.setQueueId(request.getQueueId() != null ? request.getQueueId() : generateQueueId());
        message.setDeviceType(request.getDeviceType() != null ? request.getDeviceType() : config.getDefaultDeviceType());
        message.setDeviceCode(request.getDeviceCode());
        message.setDeviceMac(request.getDeviceMac());
        message.setDeviceVersion(request.getDeviceVersion() != null ? request.getDeviceVersion() : "4.0.0");
        message.setRefreshAction(request.getRefreshAction() != null ? request.getRefreshAction() : config.getDefaultRefreshAction());
        message.setRefreshArea(request.getRefreshArea() != null ? request.getRefreshArea() : config.getDefaultRefreshArea());
        
        // 构建内容列表
        List<YaliangRefreshMessage.ContentItem> contentList = new ArrayList<>();
        YaliangRefreshMessage.ContentItem contentItem = new YaliangRefreshMessage.ContentItem();
        contentItem.setDataType(config.getDataFormat().getImageDataType());
        contentItem.setDataRef(processedImageBase64);
        contentItem.setLayerEnd(config.getDataFormat().isLayerEnd());
        contentList.add(contentItem);
        
        message.setContent(contentList);
        
        log.info("雅量刷新消息创建完成: queueId={}, 图片大小={}KB", 
                message.getQueueId(), processedImageBase64.length() / 1024);
        
        return message;
    }
    
    /**
     * 获取MQTT主题
     *
     * @param deviceCode 设备编码
     * @return MQTT主题
     */
    public String getMqttTopic(String deviceCode) {
        return config.getMqttTopicPrefix() + "/" + deviceCode + "/refresh/queue";
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