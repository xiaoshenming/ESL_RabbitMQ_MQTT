package com.pandatech.downloadcf.service;

import com.pandatech.downloadcf.dto.EslCompleteData;
import com.pandatech.downloadcf.entity.EslBrandFieldMapping;
import com.pandatech.downloadcf.entity.EslBrandFieldMappingCriteria;
import com.pandatech.downloadcf.entity.PandaEsl;
import com.pandatech.downloadcf.entity.PandaEslCriteria;
import com.pandatech.downloadcf.entity.PandaProductWithBLOBs;
import com.pandatech.downloadcf.entity.PrintTemplateDesignCriteria;
import com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs;
import com.pandatech.downloadcf.mapper.EslBrandFieldMappingMapper;
import com.pandatech.downloadcf.mapper.PandaEslMapper;
import com.pandatech.downloadcf.mapper.PandaProductMapper;
import com.pandatech.downloadcf.mapper.PrintTemplateDesignMapper;
import com.pandatech.downloadcf.mapper.ProductEslBindingMapper;
import com.pandatech.downloadcf.util.BrandCodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 统一数据访问服务 - 提供便捷的数据获取方法
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DataService {
    
    private final PandaEslMapper pandaEslMapper;
    private final PandaProductMapper pandaProductMapper;
    private final PrintTemplateDesignMapper templateMapper;
    private final EslBrandFieldMappingMapper fieldMappingMapper;
    private final ProductEslBindingMapper bindingMapper;
    
    /**
     * 根据价签ID获取完整的价签数据
     * 优化版本：避免重复数据库查询
     */
    public EslCompleteData getEslCompleteData(String eslId) {
        log.info("获取价签完整数据: {}", eslId);
        
        // 1. 获取价签基本信息
        PandaEsl esl = pandaEslMapper.selectByPrimaryKey(eslId);
        if (esl == null) {
            log.warn("未找到价签信息: {}", eslId);
            return null;
        }
        
        EslCompleteData completeData = new EslCompleteData();
        completeData.setEsl(esl);
        completeData.setStoreCode(esl.getStoreCode());
        
        // 2. 获取绑定的商品信息 - 直接使用已获取的esl对象，避免重复查询
        PandaProductWithBLOBs product = null;
        if (esl.getBoundProduct() != null) {
            product = pandaProductMapper.selectByPrimaryKey(esl.getBoundProduct());
            if (product == null) {
                log.warn("价签绑定的商品不存在: eslId={}, productId={}", eslId, esl.getBoundProduct());
            }
        } else {
            log.warn("价签未绑定商品: {}", eslId);
        }
        completeData.setProduct(product);
        
        // 3. 获取模板信息 - 直接使用已获取的product对象，避免重复查询
        PrintTemplateDesignWithBLOBs template = null;
        if (product != null && product.getEslTemplateCode() != null) {
            // 商品的ESL_TEMPLATE_CODE实际上是模板的ID，直接通过ID查询
            log.info("查询模板，模板ID: {}", product.getEslTemplateCode());
            template = templateMapper.selectByPrimaryKey(product.getEslTemplateCode());
            if (template != null) {
                log.debug("成功找到模板: templateId={}, templateName={}", template.getId(), template.getName());
            } else {
                log.warn("通过模板ID未找到模板: templateId={}", product.getEslTemplateCode());
                // 如果没有找到模板，使用默认模板
                template = getDefaultTemplate();
            }
        } else {
            // 如果没有商品或商品没有模板代码，使用默认模板
            log.warn("未找到商品对应的模板，使用默认模板: eslId={}", eslId);
            template = getDefaultTemplate();
        }
        completeData.setTemplate(template);
        
        // 4. 获取字段映射配置 - 使用BrandCodeUtil处理品牌代码
        String adapterBrandCode = BrandCodeUtil.getDefaultAdapterBrandCode();
        List<EslBrandFieldMapping> fieldMappings = getFieldMappingsByBrand(adapterBrandCode);
        completeData.setFieldMappings(fieldMappings);
        completeData.setBrandCode(BrandCodeUtil.getDefaultBrandCode()); // 设置为标准化的品牌代码
        
        log.info("字段映射配置查询结果: brandCode={}, mappingCount={}", adapterBrandCode, fieldMappings.size());
        
        log.info("成功获取价签完整数据: eslId={}, 商品主键ID={}, 商品业务编号={}, templateId={}", 
                eslId, product != null ? product.getId() : null, 
                product != null ? product.getProductId() : null,
                template != null ? template.getId() : null);
        
        return completeData;
    }
    
    /**
     * 根据价签ID获取绑定的商品信息
     */
    public PandaProductWithBLOBs getProductByEslId(String eslId) {
        // 通过价签的绑定商品字段获取商品ID
        PandaEsl esl = pandaEslMapper.selectByPrimaryKey(eslId);
        if (esl == null || esl.getBoundProduct() == null) {
            log.warn("价签未绑定商品: {}", eslId);
            return null;
        }
        
        return pandaProductMapper.selectByPrimaryKey(esl.getBoundProduct());
    }
    
    /**
     * 根据价签ID获取对应的模板
     */
    public PrintTemplateDesignWithBLOBs getTemplateByEslId(String eslId) {
        // 通过价签获取绑定的商品
        PandaProductWithBLOBs product = getProductByEslId(eslId);
        if (product != null && product.getEslTemplateCode() != null) {
            // 商品的ESL_TEMPLATE_CODE实际上是模板的ID，直接通过ID查询
            log.info("查询模板，模板ID: {}", product.getEslTemplateCode());
            PrintTemplateDesignWithBLOBs template = templateMapper.selectByPrimaryKey(product.getEslTemplateCode());
            if (template != null) {
                log.debug("成功找到模板: templateId={}, templateName={}", template.getId(), template.getName());
                return template;
            } else {
                log.warn("通过模板ID未找到模板: templateId={}", product.getEslTemplateCode());
            }
        }
        
        // 如果没有找到模板，使用默认模板
        log.warn("未找到商品对应的模板，使用默认模板: eslId={}", eslId);
        return getDefaultTemplate();
    }
    
    /**
     * 获取默认模板
     */
    public PrintTemplateDesignWithBLOBs getDefaultTemplate() {
        // 查找默认模板（可以通过名称或特定ID）
        PrintTemplateDesignCriteria criteria = new PrintTemplateDesignCriteria();
        criteria.createCriteria().andNameEqualTo("默认模板");
        
        List<PrintTemplateDesignWithBLOBs> templates = templateMapper.selectByExampleWithBLOBs(criteria);
        if (!templates.isEmpty()) {
            return templates.get(0);
        }
        
        // 如果没有找到默认模板，返回第一个可用模板
        criteria.clear();
        templates = templateMapper.selectByExampleWithBLOBs(criteria);
        return templates.isEmpty() ? null : templates.get(0);
    }
    
    /**
     * 根据品牌获取字段映射配置
     */
    public List<EslBrandFieldMapping> getFieldMappingsByBrand(String brandCode) {
        EslBrandFieldMappingCriteria criteria = new EslBrandFieldMappingCriteria();
        criteria.createCriteria().andBrandCodeEqualTo(brandCode);
        return fieldMappingMapper.selectByExample(criteria);
    }
    
    /**
     * 根据门店编码获取该门店的所有价签
     */
    public List<PandaEsl> getEslsByStore(String storeCode) {
        PandaEslCriteria criteria = new PandaEslCriteria();
        criteria.createCriteria().andStoreCodeEqualTo(storeCode);
        return pandaEslMapper.selectByExample(criteria);
    }
    
    /**
     * 根据商品ID获取绑定的所有价签
     */
    public List<PandaEsl> getEslsByProduct(String productId) {
        PandaEslCriteria criteria = new PandaEslCriteria();
        criteria.createCriteria().andBoundProductEqualTo(productId);
        return pandaEslMapper.selectByExample(criteria);
    }
    
    /**
     * 根据模板ID获取模板信息
     */
    public PrintTemplateDesignWithBLOBs getTemplateById(String templateId) {
        return templateMapper.selectByPrimaryKey(templateId);
    }
    
    /**
     * 根据模板名称获取模板信息
     */
    public PrintTemplateDesignWithBLOBs getTemplateByName(String templateName) {
        PrintTemplateDesignCriteria criteria = new PrintTemplateDesignCriteria();
        criteria.createCriteria().andNameEqualTo(templateName);
        
        List<PrintTemplateDesignWithBLOBs> templates = templateMapper.selectByExampleWithBLOBs(criteria);
        return templates.isEmpty() ? null : templates.get(0);
    }
}