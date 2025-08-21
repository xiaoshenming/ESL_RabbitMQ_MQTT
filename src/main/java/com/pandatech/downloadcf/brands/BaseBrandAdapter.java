package com.pandatech.downloadcf.brands;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.adapter.BrandAdapter;
import com.pandatech.downloadcf.dto.BrandOutputData;
import com.pandatech.downloadcf.dto.EslCompleteData;
import com.pandatech.downloadcf.entity.EslBrandFieldMapping;
import com.pandatech.downloadcf.entity.PandaProductWithBLOBs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 品牌适配器基础抽象类
 * 提供所有品牌适配器的通用功能和模板方法
 */
@Slf4j
@RequiredArgsConstructor
public abstract class BaseBrandAdapter implements BrandAdapter {
    
    protected final ObjectMapper objectMapper;
    
    @Override
    public BrandOutputData transform(EslCompleteData completeData) {
        log.info("开始转换{}品牌数据: eslId={}", getBrandName(), completeData.getEsl().getId());
        
        BrandOutputData outputData = new BrandOutputData();
        outputData.setBrandCode(getSupportedBrandCode());
        outputData.setEslId(completeData.getEsl().getId());
        outputData.setStoreCode(completeData.getStoreCode());
        
        // 设置真正的ESL设备ID（十六进制）
        if (completeData.getEsl().getEslId() != null) {
            outputData.setActualEslId(completeData.getEsl().getEslId());
        } else {
            log.warn("ESL设备ID为空，使用默认值: eslId={}", completeData.getEsl().getId());
            outputData.setActualEslId(getDefaultEslId());
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
        
        log.info("{}品牌数据转换完成: eslId={}, actualEslId={}, checksum={}", 
                getBrandName(), completeData.getEsl().getId(), outputData.getActualEslId(), checksum);
        
        return outputData;
    }
    
    @Override
    public boolean validate(EslCompleteData completeData) {
        if (completeData == null) {
            log.error("价签数据为空");
            return false;
        }
        
        if (completeData.getEsl() == null) {
            log.error("价签信息为空");
            return false;
        }
        
        if (completeData.getProduct() == null) {
            log.error("商品信息为空");
            return false;
        }
        
        // 品牌特定验证
        return validateBrandSpecific(completeData);
    }
    
    @Override
    public String calculateChecksum(String templateContent) {
        if (!StringUtils.hasText(templateContent)) {
            return "";
        }
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(templateContent.getBytes("UTF-8"));
            BigInteger bigInt = new BigInteger(1, digest);
            return bigInt.toString(16).toUpperCase();
        } catch (Exception e) {
            log.error("计算校验码失败", e);
            return "";
        }
    }
    
    /**
     * 构建数据映射 - 通用逻辑
     */
    protected Map<String, Object> buildDataMap(EslCompleteData completeData) {
        Map<String, Object> dataMap = new LinkedHashMap<>();
        PandaProductWithBLOBs product = completeData.getProduct();
        
        if (product == null) {
            log.warn("商品信息为空，返回空的数据映射");
            return dataMap;
        }
        
        log.info("开始构建{}数据映射，商品ID: {}, 商品名称: {}", 
                getBrandName(), product.getProductId(), product.getProductName());
        
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
            // 使用默认映射
            log.warn("没有找到字段映射配置，使用默认映射");
            buildDefaultDataMap(dataMap, product);
        }
        
        log.debug("构建{}数据映射完成，字段数量: {}", getBrandName(), dataMap.size());
        return dataMap;
    }
    
    /**
     * 处理模板内容 - 通用逻辑
     */
    protected String processTemplate(EslCompleteData completeData) {
        if (completeData.getTemplate() == null || 
            !StringUtils.hasText(completeData.getTemplate().getContent())) {
            log.warn("模板内容为空");
            return "";
        }
        
        String templateContent = completeData.getTemplate().getContent();
        log.debug("原始模板内容长度: {}", templateContent.length());
        
        try {
            JsonNode templateJson = objectMapper.readTree(templateContent);
            String processedContent = objectMapper.writeValueAsString(templateJson);
            log.debug("处理后模板内容长度: {}", processedContent.length());
            return processedContent;
        } catch (Exception e) {
            log.error("处理模板内容失败", e);
            return templateContent;
        }
    }
    
    /**
     * 标准化字段名
     */
    protected String normalizeFieldName(String fieldName) {
        if (!StringUtils.hasText(fieldName)) {
            return fieldName;
        }
        
        String normalized = fieldName.trim().toUpperCase();
        
        // 确保F_字段保持两位数字格式
        if (normalized.startsWith("F_") && normalized.length() == 4) {
            String number = normalized.substring(2);
            if (number.matches("\\d")) {
                normalized = "F_0" + number;
            }
        }
        
        return normalized;
    }
    
    /**
     * 获取商品字段值 - 通用逻辑
     */
    protected Object getProductFieldValue(PandaProductWithBLOBs product, String fieldName) {
        if (product == null || !StringUtils.hasText(fieldName)) {
            return null;
        }
        
        switch (fieldName.toUpperCase()) {
            case "PRODUCT_ID":
                return product.getProductId();
            case "PRODUCT_NAME":
                return product.getProductName();
            case "PRODUCT_RETAIL_PRICE":
                return product.getProductRetailPrice();
            case "PRODUCT_WHOLESALE_PRICE":
                return product.getProductWholesalePrice();
            case "PRODUCT_COST_PRICE":
                return product.getProductCostPrice();
            case "PRODUCT_DISCOUNT_PRICE":
                return product.getProductDiscountPrice();
            case "PRODUCT_MEMBERSHIP_PRICE":
                return product.getProductMembershipPrice();
            case "PRODUCT_MEMBER_PRICE":
                return product.getProductMembershipPrice();
            case "PRODUCT_VIP_PRICE":
                return product.getProductDiscountPrice();
            case "PRODUCT_BRAND":
                return product.getProductBrand();
            case "PRODUCT_CATEGORY":
                return product.getProductCategory();
            case "PRODUCT_SPECIFICATION":
                return product.getProductSpecification();
            case "PRODUCT_DISCOUNT":
                return product.getProductDiscount();
            case "PRODUCT_MATERIAL":
                return product.getProductMaterial();
            case "PRODUCT_IMAGE":
                return product.getProductImage();
            case "PRODUCT_ORIGIN":
                return product.getProductOrigin();
            case "PRODUCT_QRCODE":
                return product.getProductQrcode();
            case "PRODUCT_BARCODE":
                return product.getProductBarcode();
            case "PRODUCT_UNIT":
                return product.getProductUnit();
            case "PRODUCT_WEIGHT":
                return product.getProductWeight();
            case "PRODUCT_STATUS":
                return product.getProductStatus();
            case "PRODUCT_STOCK":
                return product.getProductStock();
            case "PRODUCT_DESCRIPTION":
                return product.getProductDescription();
            default:
                log.warn("未知的商品字段: {}", fieldName);
                return null;
        }
    }
    
    // ========== 抽象方法，由子类实现 ==========
    
    /**
     * 获取品牌名称
     */
    protected abstract String getBrandName();
    
    /**
     * 获取默认ESL设备ID
     */
    protected abstract String getDefaultEslId();
    
    /**
     * 品牌特定验证
     */
    protected abstract boolean validateBrandSpecific(EslCompleteData completeData);
    
    /**
     * 构建默认数据映射
     */
    protected abstract void buildDefaultDataMap(Map<String, Object> dataMap, PandaProductWithBLOBs product);
    
    /**
     * 根据字段类型格式化值
     */
    protected abstract Object formatValueByFieldType(String fieldName, Object value);
    
    /**
     * 判断是否为字符串字段
     */
    protected abstract boolean isStringField(String fieldName);
}