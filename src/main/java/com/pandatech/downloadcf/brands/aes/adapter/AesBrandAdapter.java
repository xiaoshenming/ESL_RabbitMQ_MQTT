package com.pandatech.downloadcf.brands.aes.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.brands.BaseBrandAdapter;
import com.pandatech.downloadcf.dto.EslCompleteData;
import com.pandatech.downloadcf.entity.PandaProductWithBLOBs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * AES (攀攀科技) 品牌适配器
 * 支持品牌编码 AES001
 * 所有字段统一返回字符串格式，符合MQTT协议要求
 * 注意：此类通过 BrandAdapterConfig 进行Bean注册，不使用 @Component 注解
 */
@Slf4j
public class AesBrandAdapter extends BaseBrandAdapter {
    
    private static final DecimalFormat PRICE_FORMAT = new DecimalFormat("0.00");
    private static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("0.##");
    private static final DecimalFormat INTEGER_FORMAT = new DecimalFormat("0");
    
    public AesBrandAdapter(ObjectMapper objectMapper) {
        super(objectMapper);
    }
    
    @Override
    public String getSupportedBrandCode() {
        return "AES001";
    }
    
    @Override
    protected String getBrandName() {
        return "攀攀科技";
    }
    
    @Override
    protected String getDefaultEslId() {
        return "06000000195A";
    }
    
    @Override
    protected boolean validateBrandSpecific(EslCompleteData completeData) {
        // AES品牌特定验证逻辑
        if (!StringUtils.hasText(completeData.getProduct().getProductId())) {
            log.error("AES品牌要求商品ID不能为空");
            return false;
        }
        
        if (!StringUtils.hasText(completeData.getProduct().getProductName())) {
            log.error("AES品牌要求商品名称不能为空");
            return false;
        }
        
        return true;
    }
    
    @Override
    protected void buildDefaultDataMap(Map<String, Object> dataMap, PandaProductWithBLOBs product) {
        // AES品牌默认映射
        dataMap.put("GOODS_CODE", formatStringField(product.getProductId()));
        dataMap.put("GOODS_NAME", formatStringField(product.getProductName()));
        dataMap.put("QRCODE", formatStringField(product.getProductQrcode()));
        dataMap.put("F_01", formatPriceAsString(product.getProductRetailPrice()));
    }
    
    @Override
    protected Object formatValueByFieldType(String fieldName, Object value) {
        if (value == null) {
            return "";
        }
        
        // 根据用户提供的字段映射信息进行分类
        if (isPriceField(fieldName)) {
            return formatPriceAsString(value);
        } else if (isNumberField(fieldName)) {
            return formatNumberAsString(value);
        } else if (isStockField(fieldName)) {
            return formatIntegerAsString(value);
        } else {
            // 字符串字段
            return formatStringField(value);
        }
    }
    
    @Override
    protected boolean isStringField(String fieldName) {
        // AES品牌所有字段都返回字符串
        return true;
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
            fieldName.equals("F_07") || // 会员价 (根据数据库映射PRODUCT_MEMBERSHIP_PRICE)
            fieldName.equals("F_08")    // 会员价
        );
    }
    
    /**
     * 判断是否为数字字段
     */
    private boolean isNumberField(String fieldName) {
        return fieldName != null && (
            fieldName.equals("F_14")    // 重量 (根据数据库映射PRODUCT_WEIGHT)
        );
    }
    
    /**
     * 判断是否为库存字段
     */
    private boolean isStockField(String fieldName) {
        return fieldName != null && fieldName.equals("F_16"); // 库存
    }
    
    /**
     * 格式化价格为字符串 - 按照err.md标准格式
     */
    private String formatPriceAsString(Object value) {
        if (value == null) {
            return "0.00";
        }
        
        try {
            BigDecimal price;
            if (value instanceof BigDecimal) {
                price = (BigDecimal) value;
            } else if (value instanceof Number) {
                price = new BigDecimal(value.toString());
            } else {
                price = new BigDecimal(value.toString());
            }
            return PRICE_FORMAT.format(price);
        } catch (Exception e) {
            log.warn("价格格式化失败: {}", value, e);
            return "0.00";
        }
    }
    
    /**
     * 格式化数字为字符串 - 按照err.md标准格式
     */
    private String formatNumberAsString(Object value) {
        if (value == null) {
            return "0";
        }
        
        try {
            BigDecimal number;
            if (value instanceof BigDecimal) {
                number = (BigDecimal) value;
            } else if (value instanceof Number) {
                number = new BigDecimal(value.toString());
            } else {
                number = new BigDecimal(value.toString());
            }
            return NUMBER_FORMAT.format(number);
        } catch (Exception e) {
            log.warn("数字格式化失败: {}", value, e);
            return "0";
        }
    }
    
    /**
     * 格式化整数为字符串 - 按照err.md标准格式
     */
    private String formatIntegerAsString(Object value) {
        if (value == null) {
            return "0";
        }
        
        try {
            BigDecimal number;
            if (value instanceof BigDecimal) {
                number = (BigDecimal) value;
            } else if (value instanceof Number) {
                number = new BigDecimal(value.toString());
            } else {
                number = new BigDecimal(value.toString());
            }
            return INTEGER_FORMAT.format(number);
        } catch (Exception e) {
            log.warn("整数格式化失败: {}", value, e);
            return "0";
        }
    }
    
    /**
     * 格式化字符串字段 - 按照err.md标准格式
     */
    private String formatStringField(Object value) {
        if (value == null) {
            return "";
        }
        return value.toString().trim();
    }
}