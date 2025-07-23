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
     * 构建数据映射 - 按照PANDA标准格式
     */
    private Map<String, Object> buildDataMap(EslCompleteData completeData) {
        Map<String, Object> dataMap = new HashMap<>();
        PandaProductWithBLOBs product = completeData.getProduct();
        
        if (product == null) {
            log.warn("商品信息为空，返回空的数据映射");
            return dataMap;
        }
        
        // 按照PANDA标准字段映射格式构建数据
        // 基础商品信息 - 使用标准字段编码
        putIfNotNull(dataMap, "GOODS_CODE", product.getProductId());
        putIfNotNull(dataMap, "GOODS_NAME", product.getProductName());
        putIfNotNull(dataMap, "QRCODE", product.getProductBarcode() != null ? product.getProductBarcode() : "www.baidu.com");
        
        // 按照字段映射表进行标准化映射
        putIfNotNull(dataMap, "F_1", formatPrice(product.getProductRetailPrice())); // 零售价
        putIfNotNull(dataMap, "F_2", product.getProductCategory()); // 分类
        putIfNotNull(dataMap, "F_3", formatPrice(product.getProductCostPrice())); // 成本价
        putIfNotNull(dataMap, "F_4", product.getProductSpecification()); // 规格
        putIfNotNull(dataMap, "F_5", formatPrice(product.getProductMembershipPrice())); // 会员价
        putIfNotNull(dataMap, "F_6", product.getProductBrand()); // 品牌
        putIfNotNull(dataMap, "F_7", formatDiscount(product.getProductDiscount())); // 折扣
        putIfNotNull(dataMap, "F_8", formatPrice(product.getProductWholesalePrice())); // 批发价
        putIfNotNull(dataMap, "F_9", product.getProductMaterial()); // 材质
        putIfNotNull(dataMap, "F_10", product.getProductImage()); // 图片
        putIfNotNull(dataMap, "F_11", product.getProductOrigin()); // 产地
        putIfNotNull(dataMap, "F_12", product.getProductUnit()); // 单位
        putIfNotNull(dataMap, "F_13", formatWeight(product.getProductWeight())); // 重量
        putIfNotNull(dataMap, "F_14", product.getProductStatus()); // 状态
        putIfNotNull(dataMap, "F_20", product.getProductDescription()); // 描述
        putIfNotNull(dataMap, "F_32", formatStock(product.getProductStock())); // 库存
        
        // 根据字段映射配置进行额外转换
        if (completeData.getFieldMappings() != null) {
            for (EslBrandFieldMapping mapping : completeData.getFieldMappings()) {
                String sourceField = mapping.getSourceField();
                String targetField = mapping.getTargetField();
                
                if (sourceField != null && targetField != null) {
                    Object value = getProductFieldValue(product, sourceField);
                    if (value != null) {
                        dataMap.put(targetField, formatFieldValue(value));
                    }
                }
            }
        }
        
        // 确保所有F_字段都有值，空值设为null（按照标准格式）
        for (int i = 15; i <= 31; i++) {
            String fieldKey = "F_" + i;
            if (!dataMap.containsKey(fieldKey)) {
                dataMap.put(fieldKey, null);
            }
        }
        
        log.debug("构建PANDA标准数据映射完成: {}", dataMap);
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
     * 格式化折扣
     */
    private Object formatDiscount(Object discount) {
        if (discount == null) {
            return null;
        }
        
        try {
            double discountValue = Double.parseDouble(discount.toString());
            return discountValue;
        } catch (NumberFormatException e) {
            log.warn("折扣格式错误: {}", discount);
            return null;
        }
    }
    
    /**
     * 格式化重量
     */
    private Object formatWeight(Object weight) {
        if (weight == null) {
            return null;
        }
        
        try {
            double weightValue = Double.parseDouble(weight.toString());
            return weightValue;
        } catch (NumberFormatException e) {
            log.warn("重量格式错误: {}", weight);
            return null;
        }
    }
    
    /**
     * 格式化库存
     */
    private Object formatStock(Object stock) {
        if (stock == null) {
            return null;
        }
        
        try {
            int stockValue = Integer.parseInt(stock.toString());
            return stockValue;
        } catch (NumberFormatException e) {
            log.warn("库存格式错误: {}", stock);
            return null;
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
            case "PRODUCT_RETAIL_PRICE":
                return product.getProductRetailPrice();
            case "PRODUCT_MEMBERSHIP_PRICE":
                return product.getProductMembershipPrice();
            case "PRODUCT_DISCOUNT_PRICE":
                return product.getProductDiscountPrice();
            case "PRODUCT_DISCOUNT":
                return product.getProductDiscount();
            case "PRODUCT_WHOLESALE_PRICE":
                return product.getProductWholesalePrice();
            case "PRODUCT_CATEGORY":
                return product.getProductCategory();
            case "PRODUCT_BRAND":
                return product.getProductBrand();
            case "PRODUCT_SPECIFICATION":
                return product.getProductSpecification();
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