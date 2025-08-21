package com.pandatech.downloadcf.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.dto.BrandOutputData;
import com.pandatech.downloadcf.dto.EslCompleteData;
import com.pandatech.downloadcf.entity.EslBrandFieldMapping;
import com.pandatech.downloadcf.entity.PandaProductWithBLOBs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 雅量科技品牌适配器 - 处理雅量科技品牌的数据转换
 * 支持品牌编码 YALIANG001
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class YaliangBrandAdapter implements BrandAdapter {
    
    private final ObjectMapper objectMapper;
    
    @Override
    public String getSupportedBrandCode() {
        return "YALIANG001";
    }
    
    @Override
    public BrandOutputData transform(EslCompleteData completeData) {
        log.info("开始转换雅量科技品牌数据: eslId={}", completeData.getEsl().getId());
        
        BrandOutputData outputData = new BrandOutputData();
        outputData.setBrandCode(getSupportedBrandCode());
        outputData.setEslId(completeData.getEsl().getId());
        outputData.setStoreCode(completeData.getStoreCode());
        
        // 设置真正的ESL设备ID（十六进制）
        if (completeData.getEsl().getEslId() != null) {
            outputData.setActualEslId(completeData.getEsl().getEslId());
        } else {
            log.warn("ESL设备ID为空，使用默认值: eslId={}", completeData.getEsl().getId());
            outputData.setActualEslId("06000000195B"); // 雅量科技默认值
        }
        
        // 1. 构建数据映射
        Map<String, Object> dataMap = buildDataMap(completeData);
        outputData.setDataMap(dataMap);
        
        // 2. 处理模板内容
        String templateContent = processTemplate(completeData);
        outputData.setTemplateContent(templateContent);
        outputData.setTemplateId(completeData.getTemplate() != null ? 
                completeData.getTemplate().getId() : "default");
        
        // 3. 计算校验码
        String checksum = calculateChecksum(templateContent);
        outputData.setChecksum(checksum);
        
        log.info("雅量科技品牌数据转换完成: eslId={}, actualEslId={}, checksum={}", 
                completeData.getEsl().getId(), outputData.getActualEslId(), checksum);
        
        return outputData;
    }
    
    @Override
    public boolean validate(EslCompleteData completeData) {
        if (completeData == null || completeData.getEsl() == null) {
            log.warn("价签数据为空");
            return false;
        }
        
        if (completeData.getProduct() == null) {
            log.warn("商品数据为空: eslId={}", completeData.getEsl().getId());
            return false;
        }
        
        if (completeData.getTemplate() == null) {
            log.warn("模板数据为空: eslId={}", completeData.getEsl().getId());
            return false;
        }
        
        return true;
    }
    
    @Override
    public String calculateChecksum(String templateContent) {
        if (templateContent == null || templateContent.isEmpty()) {
            return "";
        }
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(templateContent.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (Exception e) {
            log.error("计算校验码失败", e);
            return "";
        }
    }
    
    /**
     * 构建数据映射 - 雅量科技MQTT格式
     */
    private Map<String, Object> buildDataMap(EslCompleteData completeData) {
        Map<String, Object> dataMap = new LinkedHashMap<>();
        PandaProductWithBLOBs product = completeData.getProduct();
        
        if (product == null) {
            log.warn("商品信息为空，返回空的数据映射");
            return dataMap;
        }
        
        log.info("开始构建雅量科技数据映射，商品ID: {}, 商品名称: {}", product.getProductId(), product.getProductName());
        
        // 根据字段映射配置进行精确映射
        if (completeData.getFieldMappings() != null && !completeData.getFieldMappings().isEmpty()) {
            log.info("使用字段映射配置，映射数量: {}", completeData.getFieldMappings().size());
            
            for (EslBrandFieldMapping mapping : completeData.getFieldMappings()) {
                String templateField = mapping.getTemplateField();
                String fieldCode = mapping.getFieldCode();
                
                log.debug("处理字段映射: {} -> {}", fieldCode, templateField);
                
                if (templateField != null && fieldCode != null) {
                    Object value = getProductFieldValue(product, fieldCode);
                    log.debug("获取字段值: {} = {}", fieldCode, value);
                    
                    String targetField = normalizeFieldName(templateField);
                    Object formattedValue = formatValueByFieldType(targetField, value);
                    
                    if (formattedValue != null || isStringField(targetField)) {
                        if (formattedValue == null) {
                            formattedValue = "";
                        }
                        dataMap.put(targetField, formattedValue);
                        log.debug("字段映射成功: {} -> {} = {}", fieldCode, targetField, formattedValue);
                    }
                }
            }
        } else {
            // 默认映射
            log.warn("没有找到字段映射配置，使用默认映射");
            dataMap.put("GOODS_CODE", formatStringField(product.getProductId()));
            dataMap.put("GOODS_NAME", formatStringField(product.getProductName()));
            dataMap.put("QRCODE", formatStringField(product.getProductQrcode()));
            dataMap.put("F_1", formatPriceAsNumber(product.getProductRetailPrice()));
        }
        
        log.debug("构建雅量科技数据映射完成，字段数量: {}", dataMap.size());
        return dataMap;
    }
    
    private boolean isStringField(String fieldName) {
        if (fieldName == null) {
            return false;
        }
        
        String upperField = fieldName.toUpperCase();
        return upperField.contains("NAME") || upperField.contains("CODE") || 
               upperField.contains("DESC") || upperField.contains("UNIT") ||
               upperField.contains("BRAND") || upperField.contains("CATEGORY") ||
               upperField.contains("QRCODE") || upperField.contains("BARCODE");
    }
    
    private String normalizeFieldName(String fieldName) {
        if (fieldName == null || fieldName.trim().isEmpty()) {
            return "";
        }
        
        String normalized = fieldName.trim();
        
        // 标准化常见字段名
        switch (normalized.toLowerCase()) {
            case "code":
            case "goods_code":
            case "product_code":
                return "GOODS_CODE";
            case "name":
            case "goods_name":
            case "product_name":
                return "GOODS_NAME";
            case "price":
            case "retail_price":
                return "F_1";
            case "qrcode":
            case "qr_code":
                return "QRCODE";
            default:
                return normalized.toUpperCase();
        }
    }
    
    private String formatStringField(Object value) {
        if (value == null) {
            return "";
        }
        return value.toString().trim();
    }
    
    private Double formatPriceAsNumber(Object price) {
        if (price == null) {
            return 0.0;
        }
        
        try {
            if (price instanceof Number) {
                return ((Number) price).doubleValue();
            }
            
            String priceStr = price.toString().trim();
            if (priceStr.isEmpty()) {
                return 0.0;
            }
            
            return Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            log.warn("价格格式转换失败: {}", price);
            return 0.0;
        }
    }
    
    private Object formatValueByFieldType(String fieldName, Object value) {
        if (value == null) {
            return null;
        }
        
        String upperField = fieldName.toUpperCase();
        
        // 价格字段
        if (upperField.contains("PRICE") || upperField.matches("F_\\d+")) {
            return formatPriceAsNumber(value);
        }
        
        // 数量字段
        if (upperField.contains("STOCK") || upperField.contains("QUANTITY") || upperField.contains("COUNT")) {
            try {
                if (value instanceof Number) {
                    return ((Number) value).intValue();
                }
                return Integer.parseInt(value.toString().trim());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        
        // 字符串字段
        return formatStringField(value);
    }
    
    private String processTemplate(EslCompleteData completeData) {
        if (completeData.getTemplate() == null || completeData.getTemplate().getContent() == null) {
            log.warn("模板内容为空，使用默认模板");
            return createDefaultTemplate();
        }
        
        String templateContent = completeData.getTemplate().getContent();
        log.debug("原始模板内容: {}", templateContent);
        
        // 雅量科技可能需要特殊的模板处理逻辑
        // 这里可以根据实际需求进行定制
        
        return templateContent;
    }
    
    private Object getProductFieldValue(PandaProductWithBLOBs product, String fieldName) {
        if (product == null || fieldName == null) {
            return null;
        }
        
        switch (fieldName.toUpperCase()) {
            case "PRODUCT_ID":
            case "GOODS_CODE":
                return product.getProductId();
            case "PRODUCT_NAME":
            case "GOODS_NAME":
                return product.getProductName();
            case "PRODUCT_RETAIL_PRICE":
            case "RETAIL_PRICE":
                return product.getProductRetailPrice();
            case "PRODUCT_QRCODE":
            case "QRCODE":
                return product.getProductQrcode();
            case "PRODUCT_BARCODE":
            case "BARCODE":
                return product.getProductBarcode();
            case "PRODUCT_UNIT":
            case "UNIT":
                return product.getProductUnit();
            case "PRODUCT_BRAND":
            case "BRAND":
                return product.getProductBrand();
            case "PRODUCT_CATEGORY":
            case "CATEGORY":
                return product.getProductCategory();
            case "PRODUCT_DESCRIPTION":
            case "DESCRIPTION":
                return product.getProductDescription();
            case "PRODUCT_STOCK":
            case "STOCK":
                return product.getProductStock();
            case "PRODUCT_WEIGHT":
            case "WEIGHT":
                return product.getProductWeight();
            case "PRODUCT_DISCOUNT":
            case "DISCOUNT":
                return product.getProductDiscount();
            default:
                log.warn("未知的产品字段: {}", fieldName);
                return null;
        }
    }
    
    private String createDefaultTemplate() {
        return "{\"goods_code\":\"{{GOODS_CODE}}\",\"goods_name\":\"{{GOODS_NAME}}\",\"price\":{{F_1}}}";
    }
}