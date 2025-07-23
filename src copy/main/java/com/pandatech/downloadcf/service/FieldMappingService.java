package com.pandatech.downloadcf.service;

import com.pandatech.downloadcf.entity.EslBrandFieldMapping;
import com.pandatech.downloadcf.mapper.EslBrandFieldMappingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字段映射服务
 * 用于处理模板字段与系统字段的映射关系
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FieldMappingService {
    
    private final EslBrandFieldMappingMapper fieldMappingMapper;
    
    // 缓存映射关系，避免频繁查询数据库
    private Map<String, Map<String, String>> brandMappingCache = new HashMap<>();
    
    /**
     * 获取指定品牌的字段映射关系
     * @param brandCode 品牌编码
     * @return 模板字段 -> 系统字段的映射
     */
    public Map<String, String> getFieldMapping(String brandCode) {
        if (brandCode == null || brandCode.trim().isEmpty()) {
            brandCode = "攀攀"; // 默认品牌
        }
        
        // 先从缓存获取
        if (brandMappingCache.containsKey(brandCode)) {
            return brandMappingCache.get(brandCode);
        }
        
        // 从数据库查询
        Map<String, String> mapping = loadFieldMappingFromDB(brandCode);
        brandMappingCache.put(brandCode, mapping);
        
        return mapping;
    }
    
    /**
     * 从数据库加载字段映射
     */
    private Map<String, String> loadFieldMappingFromDB(String brandCode) {
        Map<String, String> mapping = new HashMap<>();
        
        try {
            List<EslBrandFieldMapping> mappings = fieldMappingMapper.findByBrandCode(brandCode);
            
            for (EslBrandFieldMapping fieldMapping : mappings) {
                if (fieldMapping.getTemplateField() != null && fieldMapping.getFieldCode() != null) {
                    mapping.put(fieldMapping.getTemplateField(), fieldMapping.getFieldCode());
                }
            }
            
            log.debug("加载品牌 {} 的字段映射，共 {} 个映射关系", brandCode, mapping.size());
            
        } catch (Exception e) {
            log.error("加载品牌 {} 的字段映射失败", brandCode, e);
        }
        
        return mapping;
    }
    
    /**
     * 根据模板字段获取系统字段
     * @param brandCode 品牌编码
     * @param templateField 模板字段
     * @return 系统字段，如果没有映射则返回原字段
     */
    public String getSystemField(String brandCode, String templateField) {
        if (templateField == null || templateField.trim().isEmpty()) {
            return templateField;
        }
        
        Map<String, String> mapping = getFieldMapping(brandCode);
        return mapping.getOrDefault(templateField, templateField);
    }
    
    /**
     * 清除缓存
     */
    public void clearCache() {
        brandMappingCache.clear();
        log.info("字段映射缓存已清除");
    }
    
    /**
     * 清除指定品牌的缓存
     */
    public void clearCache(String brandCode) {
        brandMappingCache.remove(brandCode);
        log.info("品牌 {} 的字段映射缓存已清除", brandCode);
    }
}