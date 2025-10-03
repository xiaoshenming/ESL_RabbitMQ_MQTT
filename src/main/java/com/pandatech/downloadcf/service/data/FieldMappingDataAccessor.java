package com.pandatech.downloadcf.service.data;

import com.pandatech.downloadcf.entity.EslBrandFieldMapping;
import com.pandatech.downloadcf.entity.EslBrandFieldMappingCriteria;
import com.pandatech.downloadcf.mapper.EslBrandFieldMappingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 字段映射数据访问器
 * 专门负责品牌字段映射相关的数据库操作
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FieldMappingDataAccessor {
    
    private final EslBrandFieldMappingMapper fieldMappingMapper;
    
    /**
     * 根据品牌代码获取字段映射配置
     */
    public List<EslBrandFieldMapping> getFieldMappingsByBrand(String brandCode) {
        log.debug("查询品牌字段映射配置: {}", brandCode);
        
        if (brandCode == null || brandCode.trim().isEmpty()) {
            log.warn("品牌代码为空");
            return List.of();
        }
        
        EslBrandFieldMappingCriteria criteria = new EslBrandFieldMappingCriteria();
        criteria.createCriteria().andBrandCodeEqualTo(brandCode);
        criteria.setOrderByClause("field_order ASC, id ASC");
        
        List<EslBrandFieldMapping> mappings = fieldMappingMapper.selectByExample(criteria);
        log.debug("品牌 {} 的字段映射配置数量: {}", brandCode, mappings.size());
        
        return mappings;
    }
    
    /**
     * 根据品牌代码和字段名获取特定的字段映射
     */
    public EslBrandFieldMapping getFieldMappingByBrandAndField(String brandCode, String fieldName) {
        log.debug("查询特定字段映射: brandCode={}, fieldName={}", brandCode, fieldName);
        
        if (brandCode == null || brandCode.trim().isEmpty() || 
            fieldName == null || fieldName.trim().isEmpty()) {
            log.warn("品牌代码或字段名为空");
            return null;
        }
        
        EslBrandFieldMappingCriteria criteria = new EslBrandFieldMappingCriteria();
        criteria.createCriteria()
                .andBrandCodeEqualTo(brandCode)
                .andTemplateFieldEqualTo(fieldName);
        
        List<EslBrandFieldMapping> mappings = fieldMappingMapper.selectByExample(criteria);
        
        if (mappings.isEmpty()) {
            log.debug("未找到字段映射: brandCode={}, fieldName={}", brandCode, fieldName);
            return null;
        }
        
        if (mappings.size() > 1) {
            log.warn("找到多个相同的字段映射，返回第一个: brandCode={}, fieldName={}", brandCode, fieldName);
        }
        
        EslBrandFieldMapping mapping = mappings.get(0);
        log.debug("成功获取字段映射: brandCode={}, fieldName={}, mappingId={}", 
                brandCode, fieldName, mapping.getId());
        
        return mapping;
    }
    
    /**
     * 获取所有品牌的字段映射配置
     */
    public List<EslBrandFieldMapping> getAllFieldMappings() {
        log.debug("查询所有字段映射配置");
        
        EslBrandFieldMappingCriteria criteria = new EslBrandFieldMappingCriteria();
        criteria.setOrderByClause("brand_code ASC, field_order ASC, id ASC");
        
        List<EslBrandFieldMapping> mappings = fieldMappingMapper.selectByExample(criteria);
        log.debug("共找到 {} 个字段映射配置", mappings.size());
        
        return mappings;
    }
    
    /**
     * 获取指定品牌的所有字段名列表
     */
    public List<String> getFieldNamesByBrand(String brandCode) {
        log.debug("获取品牌字段名列表: {}", brandCode);
        
        List<EslBrandFieldMapping> mappings = getFieldMappingsByBrand(brandCode);
        List<String> fieldNames = mappings.stream()
                .map(EslBrandFieldMapping::getTemplateField)
                .distinct()
                .toList();
        
        log.debug("品牌 {} 的字段名列表: {}", brandCode, fieldNames);
        return fieldNames;
    }
    
    /**
     * 检查品牌是否有字段映射配置
     */
    public boolean hasBrandFieldMappings(String brandCode) {
        if (brandCode == null || brandCode.trim().isEmpty()) {
            return false;
        }
        
        EslBrandFieldMappingCriteria criteria = new EslBrandFieldMappingCriteria();
        criteria.createCriteria().andBrandCodeEqualTo(brandCode);
        
        long count = fieldMappingMapper.countByExample(criteria);
        log.debug("品牌 {} 的字段映射配置数量: {}", brandCode, count);
        
        return count > 0;
    }
    
    /**
     * 获取所有已配置的品牌代码列表
     */
    public List<String> getAllConfiguredBrandCodes() {
        log.debug("获取所有已配置的品牌代码");
        
        List<EslBrandFieldMapping> mappings = getAllFieldMappings();
        List<String> brandCodes = mappings.stream()
                .map(EslBrandFieldMapping::getBrandCode)
                .distinct()
                .toList();
        
        log.debug("已配置字段映射的品牌代码: {}", brandCodes);
        return brandCodes;
    }
}