package com.pandatech.downloadcf.service.data;

import com.pandatech.downloadcf.entity.PrintTemplateDesignCriteria;
import com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs;
import com.pandatech.downloadcf.mapper.PrintTemplateDesignMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 模板数据访问器
 * 专门负责模板相关的数据库操作
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TemplateDataAccessor {
    
    private final PrintTemplateDesignMapper templateMapper;
    
    // 默认模板相关常量
    private static final String DEFAULT_TEMPLATE_NAME = "默认模板";
    private static final String FALLBACK_TEMPLATE_ID = "default_template_001";
    
    /**
     * 根据模板ID获取模板信息
     */
    public PrintTemplateDesignWithBLOBs getTemplateById(String templateId) {
        log.debug("查询模板信息: {}", templateId);
        
        if (templateId == null || templateId.trim().isEmpty()) {
            log.warn("模板ID为空");
            return null;
        }
        
        PrintTemplateDesignWithBLOBs template = templateMapper.selectByPrimaryKey(templateId);
        if (template == null) {
            log.warn("未找到模板信息: {}", templateId);
        } else {
            log.debug("成功获取模板信息: templateId={}, templateName={}", 
                    templateId, template.getName());
        }
        
        return template;
    }
    
    /**
     * 根据模板名称获取模板信息
     */
    public PrintTemplateDesignWithBLOBs getTemplateByName(String templateName) {
        log.debug("根据名称查询模板: {}", templateName);
        
        if (templateName == null || templateName.trim().isEmpty()) {
            log.warn("模板名称为空");
            return null;
        }
        
        PrintTemplateDesignCriteria criteria = new PrintTemplateDesignCriteria();
        criteria.createCriteria().andNameEqualTo(templateName);
        
        List<PrintTemplateDesignWithBLOBs> templates = templateMapper.selectByExampleWithBLOBs(criteria);
        
        if (templates.isEmpty()) {
            log.warn("未找到名称为 {} 的模板", templateName);
            return null;
        }
        
        if (templates.size() > 1) {
            log.warn("找到多个名称为 {} 的模板，返回第一个", templateName);
        }
        
        PrintTemplateDesignWithBLOBs template = templates.get(0);
        log.debug("成功获取模板: templateName={}, templateId={}", templateName, template.getId());
        
        return template;
    }
    
    /**
     * 获取默认模板
     * 优先查找名称为"默认模板"的模板，如果没有则返回第一个可用模板
     */
    public PrintTemplateDesignWithBLOBs getDefaultTemplate() {
        log.debug("获取默认模板");
        
        // 1. 首先尝试根据默认模板名称查找
        PrintTemplateDesignWithBLOBs defaultTemplate = getTemplateByName(DEFAULT_TEMPLATE_NAME);
        if (defaultTemplate != null) {
            log.info("使用默认模板: {}", DEFAULT_TEMPLATE_NAME);
            return defaultTemplate;
        }
        
        // 2. 如果没有找到默认模板，尝试查找第一个可用模板
        PrintTemplateDesignCriteria criteria = new PrintTemplateDesignCriteria();
        criteria.setOrderByClause("id ASC");
        criteria.setLimit(1);
        
        List<PrintTemplateDesignWithBLOBs> templates = templateMapper.selectByExampleWithBLOBs(criteria);
        
        if (!templates.isEmpty()) {
            PrintTemplateDesignWithBLOBs template = templates.get(0);
            log.info("使用第一个可用模板作为默认模板: templateId={}, templateName={}", 
                    template.getId(), template.getName());
            return template;
        }
        
        // 3. 如果数据库中没有任何模板，创建一个临时的默认模板对象
        log.warn("数据库中没有任何模板，创建临时默认模板");
        return createFallbackTemplate();
    }
    
    /**
     * 创建一个临时的默认模板对象
     * 当数据库中没有任何模板时使用
     */
    private PrintTemplateDesignWithBLOBs createFallbackTemplate() {
        PrintTemplateDesignWithBLOBs fallbackTemplate = new PrintTemplateDesignWithBLOBs();
        fallbackTemplate.setId(FALLBACK_TEMPLATE_ID);
        fallbackTemplate.setName(DEFAULT_TEMPLATE_NAME);
        fallbackTemplate.setCategory("系统生成的临时默认模板"); // 使用category字段代替description
        fallbackTemplate.setContent("{}"); // 使用content字段代替templateJson
        fallbackTemplate.setExtJson(""); // 使用extJson字段代替templateBase64
        
        log.warn("创建了临时默认模板: {}", FALLBACK_TEMPLATE_ID);
        return fallbackTemplate;
    }
    
    /**
     * 检查模板是否存在
     */
    public boolean existsTemplate(String templateId) {
        if (templateId == null || templateId.trim().isEmpty()) {
            return false;
        }
        
        PrintTemplateDesignWithBLOBs template = templateMapper.selectByPrimaryKey(templateId);
        return template != null;
    }
    
    /**
     * 获取所有模板列表
     */
    public List<PrintTemplateDesignWithBLOBs> getAllTemplates() {
        log.debug("查询所有模板列表");
        
        PrintTemplateDesignCriteria criteria = new PrintTemplateDesignCriteria();
        criteria.setOrderByClause("name ASC");
        
        List<PrintTemplateDesignWithBLOBs> templates = templateMapper.selectByExampleWithBLOBs(criteria);
        log.debug("共找到 {} 个模板", templates.size());
        
        return templates;
    }
    
    /**
     * 根据模板ID列表批量获取模板
     */
    public List<PrintTemplateDesignWithBLOBs> getTemplatesByIds(List<String> templateIds) {
        log.debug("批量查询模板: {}", templateIds);
        
        if (templateIds == null || templateIds.isEmpty()) {
            return List.of();
        }
        
        PrintTemplateDesignCriteria criteria = new PrintTemplateDesignCriteria();
        criteria.createCriteria().andIdIn(templateIds);
        
        List<PrintTemplateDesignWithBLOBs> templates = templateMapper.selectByExampleWithBLOBs(criteria);
        log.debug("批量查询结果: 请求{}个，找到{}个", templateIds.size(), templates.size());
        
        return templates;
    }
}