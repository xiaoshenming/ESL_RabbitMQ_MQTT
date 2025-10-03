package com.pandatech.downloadcf.brands.yaliang.service;

import com.pandatech.downloadcf.entity.PandaProductWithBLOBs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * 雅量品牌数据格式化服务
 * 负责处理雅量品牌特有的数据格式化逻辑
 */
@Slf4j
@Service
public class YaliangDataFormatter {
    
    private static final DecimalFormat PRICE_FORMAT = new DecimalFormat("0.00");
    private static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("0.##");
    
    /**
     * 构建默认数据映射
     */
    public void buildDefaultDataMap(Map<String, Object> dataMap, PandaProductWithBLOBs product) {
        // YALIANG品牌默认映射
        dataMap.put("GOODS_CODE", formatStringField(product.getProductId()));
        dataMap.put("GOODS_NAME", formatStringField(product.getProductName()));
        dataMap.put("QRCODE", formatStringField(product.getProductQrcode()));
        dataMap.put("F_1", formatPriceAsNumber(product.getProductRetailPrice()));
    }
    
    /**
     * 根据字段类型格式化值
     */
    public Object formatValueByFieldType(String fieldName, Object value) {
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
    
    /**
     * 判断是否为字符串字段
     */
    public boolean isStringField(String fieldName) {
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
}