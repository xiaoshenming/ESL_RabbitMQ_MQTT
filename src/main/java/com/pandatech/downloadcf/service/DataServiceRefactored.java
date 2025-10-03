package com.pandatech.downloadcf.service;

import com.pandatech.downloadcf.dto.EslCompleteData;
import com.pandatech.downloadcf.entity.EslBrandFieldMapping;
import com.pandatech.downloadcf.entity.PandaEsl;
import com.pandatech.downloadcf.entity.PandaProductWithBLOBs;
import com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs;
import com.pandatech.downloadcf.service.data.EslDataAccessor;
import com.pandatech.downloadcf.service.data.FieldMappingDataAccessor;
import com.pandatech.downloadcf.service.data.ProductDataAccessor;
import com.pandatech.downloadcf.service.data.TemplateDataAccessor;
import com.pandatech.downloadcf.util.BrandCodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 重构后的统一数据访问服务
 * 使用专门的数据访问组件，遵循单一职责原则
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DataServiceRefactored {
    
    private final EslDataAccessor eslDataAccessor;
    private final ProductDataAccessor productDataAccessor;
    private final TemplateDataAccessor templateDataAccessor;
    private final FieldMappingDataAccessor fieldMappingDataAccessor;
    
    /**
     * 根据价签ID获取完整的价签数据
     * 优化版本：使用专门的数据访问组件，避免重复数据库查询
     */
    public EslCompleteData getEslCompleteData(String eslId) {
        log.info("获取价签完整数据: {}", eslId);
        
        // 1. 获取价签基本信息
        PandaEsl esl = eslDataAccessor.getEslById(eslId);
        if (esl == null) {
            log.warn("未找到价签信息: {}", eslId);
            return null;
        }
        
        EslCompleteData completeData = new EslCompleteData();
        completeData.setEsl(esl);
        completeData.setStoreCode(esl.getStoreCode());
        
        // 2. 获取绑定的商品信息
        PandaProductWithBLOBs product = null;
        if (esl.getBoundProduct() != null) {
            product = productDataAccessor.getProductById(esl.getBoundProduct());
            if (product == null) {
                log.warn("价签绑定的商品不存在: eslId={}, productId={}", eslId, esl.getBoundProduct());
            }
        } else {
            log.warn("价签未绑定商品: {}", eslId);
        }
        completeData.setProduct(product);
        
        // 3. 获取模板信息
        PrintTemplateDesignWithBLOBs template = getTemplateForProduct(product);
        completeData.setTemplate(template);
        
        // 4. 获取字段映射配置
        String adapterBrandCode = BrandCodeUtil.getDefaultAdapterBrandCode();
        List<EslBrandFieldMapping> fieldMappings = fieldMappingDataAccessor.getFieldMappingsByBrand(adapterBrandCode);
        completeData.setFieldMappings(fieldMappings);
        completeData.setBrandCode(BrandCodeUtil.getDefaultBrandCode());
        
        log.info("字段映射配置查询结果: brandCode={}, mappingCount={}", adapterBrandCode, fieldMappings.size());
        
        log.info("成功获取价签完整数据: eslId={}, productId={}, templateId={}", 
                eslId, product != null ? product.getId() : null, 
                template != null ? template.getId() : null);
        
        return completeData;
    }
    
    /**
     * 为商品获取合适的模板
     */
    private PrintTemplateDesignWithBLOBs getTemplateForProduct(PandaProductWithBLOBs product) {
        PrintTemplateDesignWithBLOBs template = null;
        
        if (product != null && product.getEslTemplateCode() != null) {
            // 商品的ESL_TEMPLATE_CODE实际上是模板的ID，直接通过ID查询
            log.info("查询模板，模板ID: {}", product.getEslTemplateCode());
            template = templateDataAccessor.getTemplateById(product.getEslTemplateCode());
            
            if (template != null) {
                log.debug("成功找到模板: templateId={}, templateName={}", template.getId(), template.getName());
            } else {
                log.warn("通过模板ID未找到模板: templateId={}", product.getEslTemplateCode());
                // 如果没有找到模板，使用默认模板
                template = templateDataAccessor.getDefaultTemplate();
            }
        } else {
            // 如果没有商品或商品没有模板代码，使用默认模板
            log.warn("未找到商品对应的模板，使用默认模板");
            template = templateDataAccessor.getDefaultTemplate();
        }
        
        return template;
    }
    
    /**
     * 根据价签ID获取商品信息
     */
    public PandaProductWithBLOBs getProductByEslId(String eslId) {
        log.debug("通过价签ID获取商品信息: {}", eslId);
        
        PandaEsl esl = eslDataAccessor.getEslById(eslId);
        if (esl == null || esl.getBoundProduct() == null) {
            log.warn("价签不存在或未绑定商品: {}", eslId);
            return null;
        }
        
        return productDataAccessor.getProductById(esl.getBoundProduct());
    }
    
    /**
     * 根据价签ID获取模板信息
     */
    public PrintTemplateDesignWithBLOBs getTemplateByEslId(String eslId) {
        log.debug("通过价签ID获取模板信息: {}", eslId);
        
        PandaProductWithBLOBs product = getProductByEslId(eslId);
        return getTemplateForProduct(product);
    }
    
    /**
     * 获取默认模板
     */
    public PrintTemplateDesignWithBLOBs getDefaultTemplate() {
        return templateDataAccessor.getDefaultTemplate();
    }
    
    /**
     * 根据品牌代码获取字段映射配置
     */
    public List<EslBrandFieldMapping> getFieldMappingsByBrand(String brandCode) {
        return fieldMappingDataAccessor.getFieldMappingsByBrand(brandCode);
    }
    
    /**
     * 根据门店代码获取价签列表
     */
    public List<PandaEsl> getEslsByStore(String storeCode) {
        return eslDataAccessor.getEslsByStore(storeCode);
    }
    
    /**
     * 根据商品ID获取价签列表
     */
    public List<PandaEsl> getEslsByProduct(String productId) {
        return eslDataAccessor.getEslsByProduct(productId);
    }
    
    /**
     * 根据模板ID获取模板信息
     */
    public PrintTemplateDesignWithBLOBs getTemplateById(String templateId) {
        return templateDataAccessor.getTemplateById(templateId);
    }
    
    /**
     * 根据模板名称获取模板信息
     */
    public PrintTemplateDesignWithBLOBs getTemplateByName(String templateName) {
        return templateDataAccessor.getTemplateByName(templateName);
    }
    
    /**
     * 检查价签是否存在
     */
    public boolean existsEsl(String eslId) {
        return eslDataAccessor.existsEsl(eslId);
    }
    
    /**
     * 检查商品是否存在
     */
    public boolean existsProduct(String productId) {
        return productDataAccessor.existsProduct(productId);
    }
    
    /**
     * 检查模板是否存在
     */
    public boolean existsTemplate(String templateId) {
        return templateDataAccessor.existsTemplate(templateId);
    }
    
    /**
     * 检查品牌是否有字段映射配置
     */
    public boolean hasBrandFieldMappings(String brandCode) {
        return fieldMappingDataAccessor.hasBrandFieldMappings(brandCode);
    }
}