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
     * 构建数据映射 - 严格按照PANDA标准MQTT格式
     */
    private Map<String, Object> buildDataMap(EslCompleteData completeData) {
        Map<String, Object> dataMap = new LinkedHashMap<>(); // 使用LinkedHashMap保持字段顺序
        PandaProductWithBLOBs product = completeData.getProduct();
        
        if (product == null) {
            log.warn("商品信息为空，返回空的数据映射");
            return dataMap;
        }
        
        // 严格按照PANDA标准格式构建value字段内容
        // 按照err.md中定义的标准字段顺序和格式
        
        // F_1: 商品名称 (字符串)
        dataMap.put("F_1", formatStringField(product.getProductName()));
        
        // F_2: 商品编码 (字符串)
        dataMap.put("F_2", formatStringField(product.getProductId()));
        
        // F_3: 零售价 (数字)
        dataMap.put("F_3", formatPriceAsNumber(product.getProductRetailPrice()));
        
        // F_4: 会员价 (数字)
        dataMap.put("F_4", formatPriceAsNumber(product.getProductMembershipPrice()));
        
        // F_5: 折扣价 (数字)
        dataMap.put("F_5", formatPriceAsNumber(product.getProductDiscountPrice()));
        
        // F_6: 成本价 (数字)
        dataMap.put("F_6", formatPriceAsNumber(product.getProductCostPrice()));
        
        // F_7: 批发价 (数字)
        dataMap.put("F_7", formatPriceAsNumber(product.getProductWholesalePrice()));
        
        // F_8: 商品分类 (字符串)
        dataMap.put("F_8", formatStringField(product.getProductCategory()));
        
        // F_9: 商品品牌 (字符串)
        dataMap.put("F_9", formatStringField(product.getProductBrand()));
        
        // F_10: 商品规格 (字符串)
        dataMap.put("F_10", formatStringField(product.getProductSpecification()));
        
        // F_11: 商品单位 (字符串)
        dataMap.put("F_11", formatStringField(product.getProductUnit()));
        
        // F_12: 商品重量 (数字)
        dataMap.put("F_12", formatNumberField(product.getProductWeight()));
        
        // F_13: 商品库存 (数字)
        dataMap.put("F_13", formatIntegerField(product.getProductStock()));
        
        // F_14: 商品状态 (字符串)
        dataMap.put("F_14", formatStringField(product.getProductStatus()));
        
        // F_15: 商品材质 (字符串)
        dataMap.put("F_15", formatStringField(product.getProductMaterial()));
        
        // F_16: 商品产地 (字符串)
        dataMap.put("F_16", formatStringField(product.getProductOrigin()));
        
        // F_17: 商品条形码 (字符串)
        dataMap.put("F_17", formatStringField(product.getProductBarcode()));
        
        // F_18: 商品二维码 (字符串)
        dataMap.put("F_18", formatStringField(product.getProductQrcode()));
        
        // F_19: 商品图片 (字符串)
        dataMap.put("F_19", formatStringField(product.getProductImage()));
        
        // F_20: 商品描述 (字符串)
        dataMap.put("F_20", formatStringField(product.getProductDescription()));
        
        // F_21到F_32: 预留字段，根据字段映射配置填充
        for (int i = 21; i <= 32; i++) {
            dataMap.put("F_" + i, null);
        }
        
        // 根据字段映射配置进行额外转换
        if (completeData.getFieldMappings() != null) {
            for (EslBrandFieldMapping mapping : completeData.getFieldMappings()) {
                String sourceField = mapping.getSourceField();
                String targetField = mapping.getTargetField();
                
                if (sourceField != null && targetField != null && targetField.startsWith("F_")) {
                    Object value = getProductFieldValue(product, sourceField);
                    if (value != null) {
                        // 根据目标字段类型进行格式化
                        Object formattedValue = formatValueByFieldType(targetField, value);
                        dataMap.put(targetField, formattedValue);
                    }
                }
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
     * 格式化字符串字段 - 按照PANDA标准格式
     */
    private String formatStringField(Object value) {
        if (value == null) {
            return "";
        }
        return value.toString().trim();
    }
    
    /**
     * 格式化价格为数字类型 - 按照PANDA标准格式
     */
    private Double formatPriceAsNumber(Object price) {
        if (price == null) {
            return 0.0;
        }
        
        try {
            return Double.parseDouble(price.toString());
        } catch (NumberFormatException e) {
            log.warn("价格格式错误: {}", price);
            return 0.0;
        }
    }
    
    /**
     * 格式化数字字段 - 按照PANDA标准格式
     */
    private Double formatNumberField(Object value) {
        if (value == null) {
            return null;
        }
        
        try {
            return Double.parseDouble(value.toString());
        } catch (NumberFormatException e) {
            log.warn("数字格式错误: {}", value);
            return null;
        }
    }
    
    /**
     * 格式化整数字段 - 按照PANDA标准格式
     */
    private Integer formatIntegerField(Object value) {
        if (value == null) {
            return null;
        }
        
        try {
            return Integer.parseInt(value.toString());
        } catch (NumberFormatException e) {
            log.warn("整数格式错误: {}", value);
            return null;
        }
    }
    
    /**
     * 根据字段类型格式化值 - 按照PANDA标准格式
     */
    private Object formatValueByFieldType(String fieldName, Object value) {
        if (value == null) {
            return null;
        }
        
        // 根据字段名判断数据类型
        // F_3到F_7: 价格字段，应为数字类型
        if (fieldName.matches("F_[3-7]")) {
            return formatPriceAsNumber(value);
        }
        // F_12: 重量字段，应为数字类型
        else if ("F_12".equals(fieldName)) {
            return formatNumberField(value);
        }
        // F_13: 库存字段，应为整数类型
        else if ("F_13".equals(fieldName)) {
            return formatIntegerField(value);
        }
        // 其他字段默认为字符串类型
        else {
            return formatStringField(value);
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