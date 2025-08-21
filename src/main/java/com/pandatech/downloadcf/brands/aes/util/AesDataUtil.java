package com.pandatech.downloadcf.brands.aes.util;

import com.pandatech.downloadcf.brands.aes.config.AesBrandConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * AES品牌数据处理工具类
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AesDataUtil {
    
    private final AesBrandConfig config;
    
    /**
     * 字段类型映射
     */
    private static final Map<String, String> FIELD_TYPE_MAP = new HashMap<>();
    
    static {
        // 价格字段
        FIELD_TYPE_MAP.put("F_01", "price"); // 零售价
        FIELD_TYPE_MAP.put("F_03", "price"); // 批发价
        FIELD_TYPE_MAP.put("F_05", "price"); // 成本价
        FIELD_TYPE_MAP.put("F_06", "price"); // 折扣价
        FIELD_TYPE_MAP.put("F_08", "price"); // 会员价
        
        // 数字字段
        FIELD_TYPE_MAP.put("F_07", "number"); // 重量
        FIELD_TYPE_MAP.put("F_14", "number"); // VIP价格
        
        // 库存字段
        FIELD_TYPE_MAP.put("F_16", "stock"); // 库存
        
        // 字符串字段
        FIELD_TYPE_MAP.put("F_02", "string"); // 商品名称
        FIELD_TYPE_MAP.put("F_04", "string"); // 品牌
        FIELD_TYPE_MAP.put("F_09", "string"); // 分类
        FIELD_TYPE_MAP.put("F_10", "string"); // 二维码
        FIELD_TYPE_MAP.put("F_11", "string"); // 条形码
        FIELD_TYPE_MAP.put("F_12", "string"); // 单位
        FIELD_TYPE_MAP.put("F_13", "string"); // 状态
        FIELD_TYPE_MAP.put("F_15", "string"); // 描述
        FIELD_TYPE_MAP.put("F_17", "string"); // 扩展字段1
        FIELD_TYPE_MAP.put("F_18", "string"); // 扩展字段2
        FIELD_TYPE_MAP.put("GOODS_CODE", "string"); // 商品编码
        FIELD_TYPE_MAP.put("GOODS_NAME", "string"); // 商品名称
        FIELD_TYPE_MAP.put("QRCODE", "string"); // 二维码
    }
    
    /**
     * 获取字段类型
     */
    public String getFieldType(String fieldName) {
        return FIELD_TYPE_MAP.getOrDefault(fieldName, "string");
    }
    
    /**
     * 格式化字段值
     */
    public String formatFieldValue(String fieldName, Object value) {
        if (value == null) {
            return getDefaultValue(fieldName);
        }
        
        String fieldType = getFieldType(fieldName);
        
        switch (fieldType) {
            case "price":
                return formatPrice(value);
            case "number":
                return formatNumber(value);
            case "stock":
                return formatInteger(value);
            case "string":
            default:
                return formatString(value);
        }
    }
    
    /**
     * 格式化价格
     */
    public String formatPrice(Object value) {
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
            
            DecimalFormat formatter = new DecimalFormat(config.getDataFormat().getPriceFormat());
            return formatter.format(price);
        } catch (Exception e) {
            log.warn("价格格式化失败: {}", value, e);
            return "0.00";
        }
    }
    
    /**
     * 格式化数字
     */
    public String formatNumber(Object value) {
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
            
            DecimalFormat formatter = new DecimalFormat(config.getDataFormat().getNumberFormat());
            return formatter.format(number);
        } catch (Exception e) {
            log.warn("数字格式化失败: {}", value, e);
            return "0";
        }
    }
    
    /**
     * 格式化整数
     */
    public String formatInteger(Object value) {
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
            
            DecimalFormat formatter = new DecimalFormat(config.getDataFormat().getIntegerFormat());
            return formatter.format(number);
        } catch (Exception e) {
            log.warn("整数格式化失败: {}", value, e);
            return "0";
        }
    }
    
    /**
     * 格式化字符串
     */
    public String formatString(Object value) {
        if (value == null) {
            return "";
        }
        
        String str = value.toString().trim();
        
        // 检查长度限制
        int maxLength = config.getDataFormat().getMaxStringLength();
        if (str.length() > maxLength) {
            log.warn("字符串长度超过限制，截断: {} -> {}", str.length(), maxLength);
            str = str.substring(0, maxLength);
        }
        
        return str;
    }
    
    /**
     * 获取字段默认值
     */
    public String getDefaultValue(String fieldName) {
        String fieldType = getFieldType(fieldName);
        
        switch (fieldType) {
            case "price":
                return "0.00";
            case "number":
            case "stock":
                return "0";
            case "string":
            default:
                return "";
        }
    }
    
    /**
     * 验证字段值
     */
    public boolean validateFieldValue(String fieldName, Object value) {
        if (value == null) {
            return true; // null值由格式化方法处理
        }
        
        String fieldType = getFieldType(fieldName);
        
        switch (fieldType) {
            case "price":
                return validatePrice(value);
            case "number":
            case "stock":
                return validateNumber(value);
            case "string":
                return validateString(value);
            default:
                return true;
        }
    }
    
    /**
     * 验证价格
     */
    private boolean validatePrice(Object value) {
        try {
            BigDecimal price = new BigDecimal(value.toString());
            if (config.getValidation().isRequirePositivePrice()) {
                return price.compareTo(BigDecimal.ZERO) > 0;
            }
            return price.compareTo(BigDecimal.ZERO) >= 0;
        } catch (Exception e) {
            log.warn("价格验证失败: {}", value, e);
            return false;
        }
    }
    
    /**
     * 验证数字
     */
    private boolean validateNumber(Object value) {
        try {
            new BigDecimal(value.toString());
            return true;
        } catch (Exception e) {
            log.warn("数字验证失败: {}", value, e);
            return false;
        }
    }
    
    /**
     * 验证字符串
     */
    private boolean validateString(Object value) {
        String str = value.toString();
        return str.length() <= config.getDataFormat().getMaxStringLength();
    }
    
    /**
     * 标准化字段名
     */
    public String normalizeFieldName(String fieldName) {
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
}