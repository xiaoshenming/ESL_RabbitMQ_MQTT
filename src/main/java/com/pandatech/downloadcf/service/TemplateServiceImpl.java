package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.dto.EslRefreshRequest;
import com.pandatech.downloadcf.dto.LoadTemplateRequest;
import com.pandatech.downloadcf.dto.RefreshDto;
import com.pandatech.downloadcf.dto.TemplateDto;
import com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs;
import com.pandatech.downloadcf.exception.BusinessException;
import com.pandatech.downloadcf.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 模板服务实现类
 * 整合新架构的模板下发、价签刷新和模板加载功能
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final DataService dataService;
    private final EslRefreshService eslRefreshService;
    private final MessageProducerService messageProducerService;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final MqttService mqttService;

    @Value("${app.template.base-url:http://localhost:8999/api/res/templ/loadtemple}")
    private String baseUrl;

    @Override
    public void sendTemplate(TemplateDto templateDto) {
        try {
            log.info("开始下发模板，模板ID: {}, 门店编码: {}, 品牌编码: {}", 
                    templateDto.getTemplateId(), templateDto.getStoreCode(), templateDto.getBrandCode());
            
            // 获取模板信息
            PrintTemplateDesignWithBLOBs template = dataService.getTemplateById(templateDto.getTemplateId());
            if (template == null) {
                log.error("未找到模板ID: {}", templateDto.getTemplateId());
                throw new BusinessException("TEMPLATE_NOT_FOUND", "未找到指定的模板: " + templateDto.getTemplateId());
            }

            // 构建模板下发消息
            Map<String, Object> templateMessage = buildTemplateMessage(template, templateDto.getStoreCode(), templateDto.getBrandCode());
            
            // 将消息转换为JSON字符串后发送到RabbitMQ队列
            String jsonMessage = objectMapper.writeValueAsString(templateMessage);
            rabbitTemplate.convertAndSend("template.queue", jsonMessage);
            
            log.info("模板下发消息已发送到队列，模板ID: {}, 门店编码: {}, 品牌编码: {}", 
                    templateDto.getTemplateId(), templateDto.getStoreCode(), templateDto.getBrandCode());
                    
        } catch (Exception e) {
            log.error("模板下发失败，模板ID: {}, 门店编码: {}, 品牌编码: {}, 错误: {}", 
                    templateDto.getTemplateId(), templateDto.getStoreCode(), templateDto.getBrandCode(), e.getMessage(), e);
            throw new BusinessException("TEMPLATE_SEND_ERROR", "模板下发失败: " + e.getMessage(), e);
        }
    }

    @Override
    public void refreshEsl(RefreshDto refreshDto) {
        try {
            log.info("开始刷新价签，价签ID: {}, 门店编码: {}, 品牌编码: {}", 
                    refreshDto.getEslId(), refreshDto.getStoreCode(), refreshDto.getBrandCode());
            
            // 转换为新架构的请求格式
            EslRefreshRequest request = new EslRefreshRequest();
            request.setEslId(refreshDto.getEslId());
            request.setStoreCode(refreshDto.getStoreCode());
            request.setBrandCode(refreshDto.getBrandCode() != null ? refreshDto.getBrandCode() : "PANDA");
            request.setForceRefresh(refreshDto.getForceRefresh() != null ? refreshDto.getForceRefresh() : false);
            
            // 使用新架构的价签刷新服务
            eslRefreshService.refreshEsl(request);
            
            log.info("价签刷新请求已处理，价签ID: {}", refreshDto.getEslId());
            
        } catch (Exception e) {
            log.error("价签刷新失败，价签ID: {}, 错误: {}", refreshDto.getEslId(), e.getMessage(), e);
            throw new BusinessException("ESL_REFRESH_ERROR", "价签刷新失败: " + e.getMessage(), e);
        }
    }

    @Override
    public byte[] loadTemple(LoadTemplateRequest request) {
        PrintTemplateDesignWithBLOBs template = findTemplate(request);

        if (template == null || template.getContent() == null) {
            return null;
        }

        try {
            // 将数据库中的模板内容（JSON字符串）转换为官方模板格式
            String officialTemplateJson = mqttService.convertToOfficialTemplate(template);
            return officialTemplateJson.getBytes();
        } catch (JsonProcessingException e) {
            log.error("Error converting template to official format for ID: {} or Name: {}", request.getId(), request.getName(), e);
            return null;
        }
    }

    @Override
    public String getTemplateFileName(LoadTemplateRequest request) {
        try {
            PrintTemplateDesignWithBLOBs template = findTemplate(request);
            
            if (template == null) {
                // 如果找不到模板，返回原始请求的名称或ID
                return request.getName() != null ? request.getName() : request.getId();
            }
            
            // 从模板内容中提取屏幕类型
            String officialTemplateJson = mqttService.convertToOfficialTemplate(template);
            String tagType = extractTagTypeFromTemplate(officialTemplateJson);
            
            // 构建文件名：{模板名称}_{屏幕类型}
            String baseName = extractBaseName(template.getName());
            if (tagType != null && !tagType.isEmpty()) {
                return baseName + "_" + tagType;
            } else {
                return baseName;
            }
            
        } catch (JsonProcessingException e) {
            log.warn("获取模板文件名失败，使用默认名称，错误: {}", e.getMessage());
            return request.getName() != null ? request.getName() : request.getId();
        }
    }

    /**
     * 查找模板
     */
    private PrintTemplateDesignWithBLOBs findTemplate(LoadTemplateRequest request) {
        PrintTemplateDesignWithBLOBs template = null;
        
        // 优先使用id查找
        if (request.getId() != null && !request.getId().trim().isEmpty()) {
            String templateId = request.getId().trim();
            log.debug("通过ID查找模板: {}", templateId);
            template = dataService.getTemplateById(templateId);
            
            if (template != null) {
                log.debug("通过ID找到模板: {}, 名称: {}", templateId, template.getName());
                return template;
            } else {
                log.debug("通过ID未找到模板: {}", templateId);
            }
        }
        
        // 如果通过ID未找到，且提供了name，则尝试通过name查找
        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            String templateName = request.getName().trim();
            log.debug("通过名称查找模板: {}", templateName);
            template = dataService.getTemplateByName(templateName);
            
            if (template != null) {
                log.debug("通过名称找到模板: {}, ID: {}", templateName, template.getId());
            } else {
                log.debug("通过名称未找到模板: {}", templateName);
            }
        }
        
        return template;
    }



    /**
     * 判断是否为官方格式
     */
    private boolean isOfficialFormat(String content) {
        try {
            Map<String, Object> templateData = JsonUtil.parseObject(content, Map.class);
            return templateData.containsKey("TagType") && templateData.containsKey("Items");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从模板中提取TagType
     */
    private String extractTagTypeFromTemplate(String officialJson) {
        try {
            Map<String, Object> templateData = JsonUtil.parseObject(officialJson, Map.class);
            return (String) templateData.get("TagType");
        } catch (Exception e) {
            log.warn("从模板JSON解析TagType失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 提取模板基础名称
     */
    private String extractBaseName(String templateName) {
        if (templateName == null || templateName.trim().isEmpty()) {
            return "template";
        }
        
        // 匹配模式：数字_字母数字组合，例如：6666_1C, 7777_2A 等
        if (templateName.matches(".*_[A-Za-z0-9]+$")) {
            int lastUnderscoreIndex = templateName.lastIndexOf('_');
            if (lastUnderscoreIndex > 0) {
                return templateName.substring(0, lastUnderscoreIndex);
            }
        }
        
        return templateName;
    }

    /**
     * 构建模板下发消息
     */
    private Map<String, Object> buildTemplateMessage(PrintTemplateDesignWithBLOBs template, String storeCode, String brandCode) {
        try {
            // 转换为官方格式
            String officialTemplate = mqttService.convertToOfficialTemplate(template);
            
            // 构建MQTT消息格式
            Map<String, Object> message = objectMapper.readValue(officialTemplate, Map.class);
            message.put("shop", storeCode);  // 使用"shop"字段以匹配RabbitMQListener
            message.put("templateId", template.getId());
            message.put("templateName", template.getName());
            message.put("brandCode", brandCode != null ? brandCode : "PANDA"); // 添加品牌编码，默认为PANDA
            message.put("timestamp", System.currentTimeMillis());
            
            return message;
            
        } catch (Exception e) {
            log.error("构建模板消息失败，模板ID: {}, 错误: {}", template.getId(), e.getMessage(), e);
            throw new BusinessException("BUILD_MESSAGE_ERROR", "构建模板消息失败: " + e.getMessage(), e);
        }
    }
}