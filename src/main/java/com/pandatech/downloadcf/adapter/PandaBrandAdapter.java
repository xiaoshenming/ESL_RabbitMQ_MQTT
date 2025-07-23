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
import java.util.Map;

/**
 * 攀攀品牌适配器 - 处理攀攀品牌的数据转换
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PandaBrandAdapter implements BrandAdapter {
    
    private final ObjectMapper objectMapper;
    
    @Override
    public String getSupportedBrandCode() {
        return "PANDA";
    }
    
    @Override
    public BrandOutputData transform(EslCompleteData completeData) {
        log.info("开始转换攀攀品牌数据: eslId={}", completeData.getEsl().getId());
        
        BrandOutputData outputData = new BrandOutputData();
        outputData.setBrandCode(getSupportedBrandCode());
        outputData.setEslId(completeData.getEsl().getId());
        outputData.setStoreCode(completeData.getStoreCode());
        
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
        
        log.info("攀攀品牌数据转换完成: eslId={}, checksum={}", 
                completeData.getEsl().getId(), checksum);
        
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
     * 构建数据映射 - 按照标准格式
     */
    private Map<String, Object> buildDataMap(EslCompleteData completeData) {
        Map<String, Object> dataMap = new HashMap<>();
        PandaProductWithBLOBs product = completeData.getProduct();
        
        if (product == null) {
            log.warn("商品信息为空，返回空的数据映射");
            return dataMap;
        }
        
        // 基础商品信息映射 - 使用标准字段名称
        putIfNotNull(dataMap, "GOODS_NAME", product.getProductName());
        putIfNotNull(dataMap, "GOODS_CODE", product.getProductId());
        putIfNotNull(dataMap, "PRICE", formatPrice(product.getProductCostPrice()));
        putIfNotNull(dataMap, "CATEGORY", product.getProductCategory());
        putIfNotNull(dataMap, "BRAND", product.getProductBrand());
        putIfNotNull(dataMap, "SPECIFICATION", product.getProductSpecification());
        
        // 添加常用的标准字段
        putIfNotNull(dataMap, "PRODUCT_NAME", product.getProductName());
        putIfNotNull(dataMap, "PRODUCT_CODE", product.getProductId());
        putIfNotNull(dataMap, "UNIT_PRICE", formatPrice(product.getProductCostPrice()));
        
        // 根据字段映射配置进行转换
        if (completeData.getFieldMappings() != null) {
            for (EslBrandFieldMapping mapping : completeData.getFieldMappings()) {
                String sourceField = mapping.getSourceField();
                String targetField = mapping.getTargetField();
                
                if (sourceField != null && targetField != null) {
                    Object value = getProductFieldValue(product, sourceField);
                    if (value != null) {
                        // 确保值是字符串格式
                        dataMap.put(targetField, formatFieldValue(value));
                    }
                }
            }
        }
        
        log.debug("构建数据映射完成: {}", dataMap);
        return dataMap;
    }
    
    /**
     * 安全地添加非空值到映射中
     */
    private void putIfNotNull(Map<String, Object> map, String key, Object value) {
        if (value != null) {
            map.put(key, formatFieldValue(value));
        }
    }
    
    /**
     * 格式化字段值为字符串
     */
    private String formatFieldValue(Object value) {
        if (value == null) {
            return "";
        }
        return value.toString().trim();
    }
    
    /**
     * 格式化价格
     */
    private String formatPrice(Object price) {
        if (price == null) {
            return "0.00";
        }
        
        try {
            double priceValue = Double.parseDouble(price.toString());
            return String.format("%.2f", priceValue);
        } catch (NumberFormatException e) {
            log.warn("价格格式错误: {}", price);
            return "0.00";
        }
    }
    
    /**
     * 处理模板内容
     */
    private String processTemplate(EslCompleteData completeData) {
        if (completeData.getTemplate() == null) {
            log.warn("模板为空，返回默认模板内容");
            return createDefaultTemplate();
        }
        
        String templateContent = completeData.getTemplate().getExtJson();
        if (templateContent == null || templateContent.trim().isEmpty()) {
            templateContent = completeData.getTemplate().getContent();
        }
        
        if (templateContent == null || templateContent.trim().isEmpty()) {
            log.warn("模板内容为空，使用默认模板");
            return createDefaultTemplate();
        }
        
        try {
            // 验证并处理模板内容
            JsonNode templateNode = objectMapper.readTree(templateContent);
            
            // 如果是官方格式，直接返回
            if (templateNode.has("Items")) {
                return templateContent;
            }
            
            // 如果是自定义格式，进行转换
            return convertToOfficialFormat(templateNode);
            
        } catch (Exception e) {
            log.error("处理模板内容失败", e);
            return createDefaultTemplate();
        }
    }
    
    /**
     * 获取商品字段值
     */
    private Object getProductFieldValue(PandaProductWithBLOBs product, String fieldName) {
        switch (fieldName.toUpperCase()) {
            case "PRODUCT_NAME":
                return product.getProductName();
            case "PRODUCT_ID":
                return product.getProductId();
            case "PRODUCT_COST_PRICE":
                return product.getProductCostPrice();
            case "PRODUCT_CATEGORY":
                return product.getProductCategory();
            case "PRODUCT_BRAND":
                return product.getProductBrand();
            case "PRODUCT_SPECIFICATION":
                return product.getProductSpecification();
            default:
                log.warn("未知的商品字段: {}", fieldName);
                return null;
        }
    }
    
    /**
     * 转换为官方格式
     */
    private String convertToOfficialFormat(JsonNode customFormat) {
        // 这里实现自定义格式到官方格式的转换逻辑
        // 简化实现，实际项目中需要根据具体格式进行转换
        return createDefaultTemplate();
    }
    
    /**
     * 创建默认模板
     */
    private String createDefaultTemplate() {
        return "{\n" +
                "  \"Items\": [\n" +
                "    {\n" +
                "      \"Type\": \"Text\",\n" +
                "      \"X\": 10,\n" +
                "      \"Y\": 10,\n" +
                "      \"Width\": 200,\n" +
                "      \"Height\": 30,\n" +
                "      \"FontFamily\": \"阿里普惠\",\n" +
                "      \"FontSize\": 16,\n" +
                "      \"Text\": \"{{GOODS_NAME}}\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"Type\": \"Text\",\n" +
                "      \"X\": 10,\n" +
                "      \"Y\": 50,\n" +
                "      \"Width\": 100,\n" +
                "      \"Height\": 25,\n" +
                "      \"FontFamily\": \"阿里普惠\",\n" +
                "      \"FontSize\": 14,\n" +
                "      \"Text\": \"￥{{PRICE}}\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }
}