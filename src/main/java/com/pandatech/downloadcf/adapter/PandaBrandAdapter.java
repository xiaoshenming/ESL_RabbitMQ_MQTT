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
 * 攀攀科技品牌适配器 - 处理攀攀科技品牌的数据转换
 * 支持新的品牌编码 AES001
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PandaBrandAdapter implements BrandAdapter {
    
    private final ObjectMapper objectMapper;
    
    @Override
    public String getSupportedBrandCode() {
        return "AES001";
    }
    
    @Override
    public BrandOutputData transform(EslCompleteData completeData) {
        log.info("开始转换攀攀品牌数据: eslId={}", completeData.getEsl().getId());
        
        BrandOutputData outputData = new BrandOutputData();
        outputData.setBrandCode(getSupportedBrandCode());
        outputData.setEslId(completeData.getEsl().getId());
        outputData.setStoreCode(completeData.getStoreCode());
        
        // 设置真正的ESL设备ID（十六进制）
        if (completeData.getEsl().getEslId() != null) {
            outputData.setActualEslId(completeData.getEsl().getEslId());
        } else {
            log.warn("ESL设备ID为空，使用默认值: eslId={}", completeData.getEsl().getId());
            outputData.setActualEslId("06000000195A"); // 默认值，应该从数据库获取
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
        
        log.info("攀攀品牌数据转换完成: eslId={}, actualEslId={}, checksum={}", 
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
     * 构建数据映射 - 严格按照PANDA标准MQTT格式
     */
    private Map<String, Object> buildDataMap(EslCompleteData completeData) {
        Map<String, Object> dataMap = new LinkedHashMap<>(); // 使用LinkedHashMap保持字段顺序
        PandaProductWithBLOBs product = completeData.getProduct();
        
        if (product == null) {
            log.warn("商品信息为空，返回空的数据映射");
            return dataMap;
        }
        
        log.info("开始构建数据映射，商品ID: {}, 商品名称: {}", product.getProductId(), product.getProductName());
        
        // 根据字段映射配置进行精确映射（这是核心逻辑）
        if (completeData.getFieldMappings() != null && !completeData.getFieldMappings().isEmpty()) {
            log.info("使用字段映射配置，映射数量: {}", completeData.getFieldMappings().size());
            
            // 打印所有字段映射配置
            for (EslBrandFieldMapping mapping : completeData.getFieldMappings()) {
                log.info("字段映射配置: {} -> {} (格式: {})", 
                    mapping.getFieldCode(), mapping.getTemplateField(), mapping.getFormatRule());
            }
            
            for (EslBrandFieldMapping mapping : completeData.getFieldMappings()) {
                String templateField = mapping.getTemplateField(); // 目标字段 (如 F_01, code, name等)
                String fieldCode = mapping.getFieldCode(); // 源字段 (如 PRODUCT_RETAIL_PRICE等)
                
                log.debug("处理字段映射: {} -> {}", fieldCode, templateField);
                
                if (templateField != null && fieldCode != null) {
                    Object value = getProductFieldValue(product, fieldCode);
                    log.debug("获取字段值: {} = {}", fieldCode, value);
                    
                    // 即使值为null也要处理，某些字段可能需要默认值
                    // 标准化目标字段名
                    String targetField = normalizeFieldName(templateField);
                    log.debug("标准化字段名: {} -> {}", templateField, targetField);
                    
                    // 根据目标字段类型进行格式化
                    Object formattedValue = formatValueByFieldType(targetField, value);
                    log.debug("格式化字段值: {} = {}", targetField, formattedValue);
                    
                    // 对于字符串字段，即使为空也要添加；对于数字字段，只有非null才添加
                    boolean shouldAdd = false;
                    if (formattedValue != null) {
                        shouldAdd = true;
                    } else if (isStringField(targetField)) {
                        // 字符串字段即使为null也要添加空字符串
                        formattedValue = "";
                        shouldAdd = true;
                    }
                    
                    if (shouldAdd) {
                        dataMap.put(targetField, formattedValue);
                        log.debug("字段映射成功: {} -> {} = {}", fieldCode, targetField, formattedValue);
                    } else {
                        log.debug("字段映射: {} -> {} = null (跳过)", fieldCode, targetField);
                    }
                } else {
                    log.warn("字段映射配置不完整: templateField={}, fieldCode={}", templateField, fieldCode);
                }
            }
            
            log.info("字段映射完成，实际映射字段数量: {}", dataMap.size());
            log.info("映射结果: {}", dataMap);
        } else {
            // 如果没有字段映射配置，使用最小化的默认映射
            log.warn("没有找到字段映射配置，使用最小化默认映射");
            
            // 只映射基本的必要字段，避免生成过多F_字段
            dataMap.put("GOODS_CODE", formatStringField(product.getProductId()));
            dataMap.put("GOODS_NAME", formatStringField(product.getProductName()));
            dataMap.put("QRCODE", formatStringField(product.getProductQrcode()));
            
            // 只添加最基本的价格字段
            dataMap.put("F_1", formatPriceAsNumber(product.getProductRetailPrice())); // 零售价
        }
        
        log.debug("构建PANDA标准数据映射完成，字段数量: {}", dataMap.size());
        return dataMap;
    }
    
    /**
     * 判断字段是否为字符串类型
     */
    private boolean isStringField(String fieldName) {
        if (fieldName == null) {
            return false;
        }
        
        switch (fieldName) {
            // 字符串字段
            case "F_2":  // 分类
            case "F_4":  // 规格
            case "F_9":  // 材质
            case "F_10": // 图片
            case "F_11": // 产地
            case "F_12": // 单位
            case "F_14": // 状态
            case "F_15": // 预留
            case "F_16": // 预留
            case "F_17": // 预留
            case "F_18": // 预留
            case "F_19": // 预留
            case "F_20": // 描述
            case "GOODS_CODE":
            case "GOODS_NAME":
            case "QRCODE":
                return true;
                
            // 数字字段
            case "F_1":  // 零售价
            case "F_3":  // 成本价
            case "F_5":  // 会员价
            case "F_6":  // 折扣价
            case "F_7":  // 折扣
            case "F_8":  // 批发价
            case "F_13": // 重量
            case "F_32": // 库存
                return false;
                
            // 其他F_字段的处理
            default:
                if (fieldName.startsWith("F_")) {
                    // 对于F_21到F_31等预留字段，认为是字符串
                    if (fieldName.matches("F_(2[1-9]|3[0-1])")) {
                        return true;
                    }
                    // 其他F_字段默认为字符串
                    return true;
                }
                // 非F_字段默认为字符串
                return true;
        }
    }
    
    /**
     * 获取简化的图片值 - 根据标准格式
     */
    private String getSimplifiedImageValue(String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            return "ge"; // 默认值，根据标准格式
        }
        // 简化处理，返回固定值或简化的标识
        return "ge";
    }
    
    /**
     * 标准化字段名 - 处理字段名的格式转换
     */
    private String normalizeFieldName(String fieldName) {
        if (fieldName == null) {
            return null;
        }
        
        // 处理 F_01 -> F_1 的转换
        if (fieldName.matches("F_0\\d")) {
            return "F_" + fieldName.substring(3); // 去掉前导0
        }
        
        // 处理其他特殊字段名
        switch (fieldName.toLowerCase()) {
            case "code":
                return "GOODS_CODE";
            case "name":
                return "GOODS_NAME";
            case "qrcode":
                return "QRCODE";
            default:
                return fieldName;
        }
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
     * 根据err.md中的标准格式示例进行精确格式化
     */
    private Object formatValueByFieldType(String fieldName, Object value) {
        log.debug("格式化字段值: fieldName={}, value={}, valueType={}", 
            fieldName, value, value != null ? value.getClass().getSimpleName() : "null");
        
        // 根据字段名判断数据类型，严格按照标准格式示例
        switch (fieldName) {
            // 价格字段 - 应为数字类型（Double）
            case "F_1":  // 零售价
            case "F_3":  // 成本价
            case "F_5":  // 会员价
            case "F_6":  // 折扣价
            case "F_8":  // 批发价
                Object priceResult = formatPriceAsNumber(value);
                log.debug("价格字段 {} 格式化结果: {}", fieldName, priceResult);
                return priceResult;
                
            // 数字字段 - 应为数字类型（Double）
            case "F_7":  // 折扣
            case "F_13": // 重量
                Object numberResult = formatNumberField(value);
                log.debug("数字字段 {} 格式化结果: {}", fieldName, numberResult);
                return numberResult;
                
            // 整数字段 - 应为整数类型（Integer）
            case "F_32": // 库存
                Object intResult = formatIntegerField(value);
                log.debug("整数字段 {} 格式化结果: {}", fieldName, intResult);
                return intResult;
                
            // 字符串字段 - 应为字符串类型，即使为null也返回空字符串
            case "F_2":  // 分类
            case "F_4":  // 规格
            case "F_9":  // 材质
            case "F_10": // 图片
            case "F_11": // 产地
            case "F_12": // 单位
            case "F_14": // 状态
            case "F_15": // 预留
            case "F_16": // 预留
            case "F_17": // 预留
            case "F_18": // 预留
            case "F_19": // 预留
            case "F_20": // 描述
            case "GOODS_CODE":
            case "GOODS_NAME":
            case "QRCODE":
                String stringResult = formatStringField(value);
                log.debug("字符串字段 {} 格式化结果: '{}'", fieldName, stringResult);
                return stringResult;
                
            // 其他F_字段的处理
            default:
                if (fieldName.startsWith("F_")) {
                    // 对于F_21到F_31等预留字段，返回null
                    if (fieldName.matches("F_(2[1-9]|3[0-1])")) {
                        log.debug("预留字段 {} 返回null", fieldName);
                        return null;
                    }
                    // 其他F_字段默认为字符串
                    String defaultStringResult = formatStringField(value);
                    log.debug("其他F_字段 {} 格式化为字符串: '{}'", fieldName, defaultStringResult);
                    return defaultStringResult;
                }
                // 非F_字段默认为字符串
                String nonFStringResult = formatStringField(value);
                log.debug("非F_字段 {} 格式化为字符串: '{}'", fieldName, nonFStringResult);
                return nonFStringResult;
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