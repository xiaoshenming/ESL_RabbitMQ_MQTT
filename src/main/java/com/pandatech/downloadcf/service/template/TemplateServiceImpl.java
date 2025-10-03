package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.dto.EslRefreshRequest;
import com.pandatech.downloadcf.dto.LoadTemplateRequest;
import com.pandatech.downloadcf.dto.RefreshDto;
import com.pandatech.downloadcf.dto.TemplateDto;
import com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs;
import com.pandatech.downloadcf.exception.BusinessException;
import com.pandatech.downloadcf.util.JsonUtil;
import com.pandatech.downloadcf.util.BrandCodeUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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
    private final TemplateConverter templateConverter;
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

            // 构建新格式的队列消息
            Map<String, Object> queueMessage = new HashMap<>();
            queueMessage.put("messageType", "template");
            queueMessage.put("storeCode", templateDto.getStoreCode());
            queueMessage.put("templateId", templateDto.getTemplateId());
            queueMessage.put("brandCode", templateDto.getBrandCode());
            queueMessage.put("mqttTopic", "esl/server/data/" + templateDto.getStoreCode());
            queueMessage.put("timestamp", System.currentTimeMillis());
            queueMessage.put("priority", 2); // 模板消息优先级为2
            
            // 构建MQTT载荷
            Map<String, Object> mqttPayload = buildTemplateMessage(template, templateDto.getStoreCode(), templateDto.getBrandCode());
            queueMessage.put("mqttPayload", mqttPayload);
            
            // 将消息转换为JSON字符串后发送到RabbitMQ队列
            String jsonMessage = objectMapper.writeValueAsString(queueMessage);
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
            
            // 使用BrandCodeUtil进行品牌代码兼容性处理
            String normalizedBrandCode = BrandCodeUtil.normalizeBrandCode(refreshDto.getBrandCode());
            
            // 转换为新架构的请求格式
            EslRefreshRequest request = new EslRefreshRequest();
            request.setEslId(refreshDto.getEslId());
            request.setStoreCode(refreshDto.getStoreCode());
            request.setBrandCode(normalizedBrandCode); // 使用标准化的品牌代码
            request.setForceRefresh(refreshDto.getForceRefresh() != null ? refreshDto.getForceRefresh() : false);
            
            log.info("品牌代码标准化: {} -> {}", refreshDto.getBrandCode(), normalizedBrandCode);
            
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
            String officialTemplateJson = templateConverter.convertToOfficialTemplate(template);
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
            String officialTemplateJson = templateConverter.convertToOfficialTemplate(template);
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
            Map<String, Object> templateData = objectMapper.readValue(content, new TypeReference<Map<String, Object>>() {});
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
            Map<String, Object> templateData = objectMapper.readValue(officialJson, new TypeReference<Map<String, Object>>() {});
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
     * 构建模板下发消息 - 按照标准格式
     */
    private Map<String, Object> buildTemplateMessage(PrintTemplateDesignWithBLOBs template, String storeCode, String brandCode) {
        try {
            // 构建标准的模板下发MQTT消息格式
            Map<String, Object> message = new HashMap<>();
            
            // 生成随机UUID作为消息ID（必须每次都不同）
            String messageId = java.util.UUID.randomUUID().toString();
            // 生成完整的时间戳（避免科学计数法省略值）
            long currentTimeMillis = System.currentTimeMillis();
            double timestamp = currentTimeMillis / 1000.0;
            
            // 按照标准格式的字段顺序
            message.put("command", "tmpllist");
            message.put("id", messageId); // 使用随机UUID，确保每次都不同
            message.put("timestamp", timestamp); // 使用完整时间戳
            message.put("shop", storeCode);
            
            // 构建data字段
            Map<String, Object> data = new HashMap<>();
            data.put("url", baseUrl); // 模板下载URL
            data.put("tid", java.util.UUID.randomUUID().toString()); // 事务ID
            
            // 获取模板的官方格式并提取TagType
            String officialTemplateJson = templateConverter.convertToOfficialTemplate(template);
            String tagType = extractTagTypeFromTemplate(officialTemplateJson);
            
            // 构建正确的文件名格式：{templateName}_{tagType}.json
            String templateName = template.getName() != null ? template.getName() : "2";
            String fileName;
            if (tagType != null && !tagType.isEmpty()) {
                fileName = templateName + "_" + tagType + ".json";
            } else {
                // 如果无法获取TagType，使用默认值06
                fileName = templateName + "_06.json";
                log.warn("无法获取模板TagType，使用默认值06: templateId={}", template.getId());
            }
            
            // 构建tmpls数组
            Map<String, Object> tmplItem = new HashMap<>();
            tmplItem.put("id", template.getId());
            tmplItem.put("name", fileName); // 使用正确的文件名格式
            tmplItem.put("md5", calculateTemplateMd5(template));
            
            data.put("tmpls", List.of(tmplItem));
            message.put("data", data);
            
            log.debug("构建模板下发消息完成: templateId={}, fileName={}, storeCode={}, brandCode={}, 消息ID={}, 时间戳={}", 
                    template.getId(), fileName, storeCode, brandCode, messageId, timestamp);
            
            return message;
            
        } catch (Exception e) {
            log.error("构建模板消息失败，模板ID: {}, 错误: {}", template.getId(), e.getMessage(), e);
            throw new BusinessException("BUILD_MESSAGE_ERROR", "构建模板消息失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 计算模板MD5校验码
     */
    private String calculateTemplateMd5(PrintTemplateDesignWithBLOBs template) {
        try {
            String content = template.getContent();
            if (content == null || content.isEmpty()) {
                content = template.getExtJson();
            }
            if (content == null) {
                content = "";
            }
            
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(content.getBytes());
            java.math.BigInteger no = new java.math.BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (Exception e) {
            log.error("计算模板MD5失败", e);
            return "";
        }
    }
}