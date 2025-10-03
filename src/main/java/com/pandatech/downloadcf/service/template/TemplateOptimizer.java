package com.pandatech.downloadcf.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 模板优化器
 * 负责优化和去重模板Items，解决重复元素和位置冲突问题
 */
@Slf4j
@Component
public class TemplateOptimizer {

    /**
     * 优化和去重Items，解决重复元素和位置冲突问题
     */
    public List<Map<String, Object>> optimizeAndDeduplicateItems(List<Map<String, Object>> items) {
        if (items == null || items.isEmpty()) {
            return items;
        }
        
        List<Map<String, Object>> optimizedItems = new ArrayList<>();
        Set<String> processedKeys = new HashSet<>();
        
        log.debug("开始优化Items，原始数量: {}", items.size());
        
        for (int i = 0; i < items.size(); i++) {
            Map<String, Object> item = items.get(i);
            
            // 生成唯一标识符，用于去重
            String uniqueKey = generateItemUniqueKey(item);
            
            // 检查是否已经处理过相同的元素
            if (processedKeys.contains(uniqueKey)) {
                log.debug("发现重复元素，跳过: DataKey={}, Location={}", 
                         item.get("DataKey"), item.get("Location"));
                continue;
            }
            
            // 检查位置是否合理
            if (!isValidItemPosition(item)) {
                log.warn("发现位置异常的元素，尝试修复: DataKey={}, Location={}, Size={}", 
                        item.get("DataKey"), item.get("Location"), item.get("Size"));
                
                // 尝试修复位置
                item = fixItemPosition(item, i);
                if (item == null) {
                    log.warn("无法修复位置异常的元素，跳过");
                    continue;
                }
            }
            
            // 检查尺寸是否合理
            if (!isValidItemSize(item)) {
                log.warn("发现尺寸异常的元素，尝试修复: DataKey={}, Size={}", 
                        item.get("DataKey"), item.get("Size"));
                
                item = fixItemSize(item);
                if (item == null) {
                    log.warn("无法修复尺寸异常的元素，跳过");
                    continue;
                }
            }
            
            processedKeys.add(uniqueKey);
            optimizedItems.add(item);
            
            log.debug("保留元素: DataKey={}, Type={}, Location={}, Size={}", 
                     item.get("DataKey"), item.get("Type"), item.get("Location"), item.get("Size"));
        }
        
        log.info("Items优化完成，原始数量: {}, 优化后数量: {}", items.size(), optimizedItems.size());
        return optimizedItems;
    }
    
    /**
     * 生成元素的唯一标识符
     */
    private String generateItemUniqueKey(Map<String, Object> item) {
        String dataKey = String.valueOf(item.get("DataKey"));
        String type = String.valueOf(item.get("Type"));
        String location = String.valueOf(item.get("Location"));
        
        // 对于相同DataKey的元素，如果位置完全相同，认为是重复的
        return dataKey + "|" + type + "|" + location;
    }
    
    /**
     * 检查元素位置是否合理
     */
    private boolean isValidItemPosition(Map<String, Object> item) {
        try {
            int x = (Integer) item.get("x");
            int y = (Integer) item.get("y");
            
            // 位置不能为负数，且不能超出合理范围
            return x >= 0 && y >= 0 && x < 10000 && y < 10000;
        } catch (Exception e) {
            log.warn("检查元素位置时出错: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * 检查元素尺寸是否合理
     */
    private boolean isValidItemSize(Map<String, Object> item) {
        try {
            int width = (Integer) item.get("width");
            int height = (Integer) item.get("height");
            
            // 尺寸必须大于0，且不能过大
            return width > 0 && height > 0 && width <= 1000 && height <= 1000;
        } catch (Exception e) {
            log.warn("检查元素尺寸时出错: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * 修复元素位置
     */
    private Map<String, Object> fixItemPosition(Map<String, Object> item, int index) {
        try {
            int x = (Integer) item.get("x");
            int y = (Integer) item.get("y");
            
            // 如果位置异常，根据索引重新分配位置
            if (x < 0 || y < 0 || x >= 10000 || y >= 10000) {
                // 简单的网格布局：每行放4个元素
                int newX = (index % 4) * 100;
                int newY = (index / 4) * 30;
                
                item.put("x", newX);
                item.put("y", newY);
                item.put("Location", newX + ", " + newY);
                
                log.debug("修复元素位置: 原位置({}, {}), 新位置({}, {})", x, y, newX, newY);
            }
            
            return item;
        } catch (Exception e) {
            log.error("修复元素位置时出错: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * 修复元素尺寸
     */
    private Map<String, Object> fixItemSize(Map<String, Object> item) {
        try {
            int width = (Integer) item.get("width");
            int height = (Integer) item.get("height");
            
            // 如果尺寸异常，设置合理的默认值
            if (width <= 0 || width > 1000) {
                width = 80; // 默认宽度
            }
            if (height <= 0 || height > 1000) {
                height = 20; // 默认高度
            }
            
            // 特殊处理不同类型的元素
            String type = String.valueOf(item.get("Type"));
            if ("qrcode".equals(type)) {
                // 二维码应该是正方形，且有最小尺寸要求
                int size = Math.max(width, height);
                size = Math.max(size, 30); // 最小30像素
                width = height = size;
            } else if ("barcode".equals(type)) {
                // 条形码宽度应该大于高度
                width = Math.max(width, 60); // 最小宽度60像素
                height = Math.max(height, 15); // 最小高度15像素
            }
            
            item.put("width", width);
            item.put("height", height);
            item.put("Size", width + ", " + height);
            
            log.debug("修复元素尺寸: Type={}, 新尺寸={}x{}", type, width, height);
            
            return item;
        } catch (Exception e) {
            log.error("修复元素尺寸时出错: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 验证模板Items的完整性
     */
    public boolean validateTemplateItems(List<Map<String, Object>> items) {
        if (items == null || items.isEmpty()) {
            log.warn("模板Items为空");
            return false;
        }

        int validItems = 0;
        for (Map<String, Object> item : items) {
            if (isValidItem(item)) {
                validItems++;
            }
        }

        double validRatio = (double) validItems / items.size();
        log.debug("模板Items验证结果: 总数={}, 有效数={}, 有效率={:.2f}", 
                items.size(), validItems, validRatio);

        return validRatio >= 0.8; // 至少80%的Items有效
    }

    /**
     * 检查单个Item是否有效
     */
    private boolean isValidItem(Map<String, Object> item) {
        if (item == null) {
            return false;
        }

        // 检查必要字段
        String[] requiredFields = {"DataKey", "Type", "x", "y", "width", "height"};
        for (String field : requiredFields) {
            if (!item.containsKey(field) || item.get(field) == null) {
                return false;
            }
        }

        // 检查位置和尺寸
        return isValidItemPosition(item) && isValidItemSize(item);
    }
}