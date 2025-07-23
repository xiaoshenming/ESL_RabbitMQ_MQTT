package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.config.RabbitMQConfig;
import com.pandatech.downloadcf.dto.LoadTemplateRequest;
import com.pandatech.downloadcf.dto.RefreshDto;
import com.pandatech.downloadcf.dto.TemplateDto;
import com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs;
import com.pandatech.downloadcf.mapper.PrintTemplateDesignMapper;
import com.pandatech.downloadcf.util.ScreenTypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final MqttService mqttService;
    private final PrintTemplateDesignMapper printTemplateDesignMapper;
    private final ScreenTypeConverter screenTypeConverter;
    private final EslRefreshService eslRefreshService;
    
    @Value("${app.template.base-url}")
    private String baseUrl;

    @Override
    public void sendTemplate(TemplateDto templateDto) {
        try {
            // 根据templateId从数据库获取模板信息
            PrintTemplateDesignWithBLOBs template = printTemplateDesignMapper.selectByPrimaryKey(templateDto.getTemplateId());
            if (template == null) {
                log.error("未找到模板ID: {}", templateDto.getTemplateId());
                throw new RuntimeException("未找到指定的模板");
            }
            
            // 从EXT_JSON中提取屏幕类型信息
            String screenType = extractScreenTypeFromExtJson(template.getExtJson());
            if (screenType == null) {
                // 如果EXT_JSON中没有，尝试从CATEGORY字段获取
                screenType = template.getCategory();
            }
            
            log.info("模板ID: {} 提取到屏幕类型: {}", template.getId(), screenType);
            
            // 转换模板为官方格式
            String officialTemplateJson = mqttService.convertToOfficialTemplate(template);
            if (officialTemplateJson == null) {
                log.error("模板转换失败，ID: {}", template.getId());
                throw new RuntimeException("模板转换失败");
            }

            // 从转换后的JSON中提取TagType
            String tagType = extractTagTypeFromOfficialTemplate(officialTemplateJson);
            if (tagType == null) {
                log.warn("无法从转换后的模板中提取TagType, 模板ID: {}", template.getId());
                // 兜底逻辑：继续使用从EXT_JSON或CATEGORY中获取的screenType
                tagType = screenType;
            }

            // 使用模板名称和提取到的TagType生成最终的文件名
            String finalTemplateName = template.getName() + "_" + tagType + ".json";
            log.info("生成的最终模板文件名: {}", finalTemplateName);

            // 计算模板内容的MD5
            String templateMd5 = calculateMd5(officialTemplateJson);

            // 构造MQTT消息格式
            Map<String, Object> mqttMessage = new HashMap<>();
            mqttMessage.put("command", "tmpllist");

            Map<String, Object> data = new HashMap<>();
            data.put("url", baseUrl);
            data.put("tid", "396a5189-53d8-4354-bcfa-27d57d9d69ad"); // 多租户ID，可以配置化

            // 构造模板列表
            List<Map<String, Object>> tmpls = new ArrayList<>();
            Map<String, Object> tmpl = new HashMap<>();
            tmpl.put("name", finalTemplateName); // 使用最终生成的文件名
            tmpl.put("md5", templateMd5);
            tmpl.put("id", template.getId());
            tmpls.add(tmpl);
            data.put("tmpls", tmpls);
            
            mqttMessage.put("data", data);
            mqttMessage.put("id", UUID.randomUUID().toString());
            mqttMessage.put("timestamp", System.currentTimeMillis() / 1000);
            mqttMessage.put("shop", templateDto.getStoreCode()); // 使用传入的门店编码
            
            String message = objectMapper.writeValueAsString(mqttMessage);
            rabbitTemplate.convertAndSend(RabbitMQConfig.TEMPLATE_QUEUE, message);
            log.info("模板消息已发送到RabbitMQ队列: {}", message);
            
        } catch (JsonProcessingException e) {
            log.error("序列化模板消息失败", e);
            throw new RuntimeException("发送模板消息失败", e);
        } catch (Exception e) {
            log.error("发送模板消息时发生未知错误", e);
            throw new RuntimeException("发送模板消息失败", e);
        }
    }
    
    /**
     * 从EXT_JSON中提取屏幕类型信息
     */
    @SuppressWarnings("unchecked")
    private String extractScreenTypeFromExtJson(String extJson) {
        if (extJson == null || extJson.trim().isEmpty()) {
            return null;
        }
        
        try {
            Map<String, Object> extData = objectMapper.readValue(extJson, Map.class);
            
            // 检查designConfig.panels[0].eslConfig.screenType
            if (extData.containsKey("designConfig")) {
                Object designConfigObj = extData.get("designConfig");
                if (designConfigObj instanceof Map) {
                    Map<String, Object> designConfig = (Map<String, Object>) designConfigObj;
                    if (designConfig.containsKey("panels")) {
                        Object panelsObj = designConfig.get("panels");
                        if (panelsObj instanceof List) {
                            List<Map<String, Object>> panels = (List<Map<String, Object>>) panelsObj;
                            if (!panels.isEmpty()) {
                                Map<String, Object> firstPanel = panels.get(0);
                                if (firstPanel.containsKey("eslConfig")) {
                                    Object eslConfigObj = firstPanel.get("eslConfig");
                                    if (eslConfigObj instanceof Map) {
                                        Map<String, Object> eslConfig = (Map<String, Object>) eslConfigObj;
                                        if (eslConfig.containsKey("screenType")) {
                                            Object screenTypeObj = eslConfig.get("screenType");
                                            if (screenTypeObj instanceof String) {
                                                return (String) screenTypeObj;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.warn("解析EXT_JSON失败: {}", e.getMessage());
        }
        
        return null;
    }

    /**
     * 从官方模板JSON中提取TagType
     */
    @SuppressWarnings("unchecked")
    private String extractTagTypeFromOfficialTemplate(String officialJson) {
        if (officialJson == null || officialJson.trim().isEmpty()) {
            return null;
        }
        try {
            Map<String, Object> templateData = objectMapper.readValue(officialJson, Map.class);
            return (String) templateData.get("TagType");
        } catch (Exception e) {
            log.warn("从官方模板JSON解析TagType失败: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * 计算字符串的MD5值
     */
    private String calculateMd5(String content) {
        if (content == null || content.isEmpty()) {
            return "";
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(content.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("MD5算法不可用", e);
            return "";
        }
    }

    @Override
    public void refreshEsl(RefreshDto refreshDto) {
        eslRefreshService.sendRefreshMessage(refreshDto);
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
        PrintTemplateDesignWithBLOBs template = findTemplate(request);
        
        if (template == null) {
            // 如果找不到模板，返回原始请求的名称或ID
            return request.getName() != null ? request.getName() : request.getId();
        }
        
        try {
            // 从模板内容中提取屏幕类型
            String officialTemplateJson = mqttService.convertToOfficialTemplate(template);
            String tagType = extractTagTypeFromOfficialTemplate(officialTemplateJson);
            
            if (tagType == null) {
                // 如果无法从官方模板中提取TagType，尝试从EXT_JSON中提取
                tagType = extractScreenTypeFromExtJson(template.getExtJson());
            }
            
            // 如果仍然无法获取屏幕类型，使用默认值
            if (tagType == null) {
                tagType = "06"; // 默认屏幕类型
            }
            
            // 返回格式化的文件名：{模板名称}_{屏幕类型}
            return template.getName() + "_" + tagType;
            
        } catch (JsonProcessingException e) {
            log.error("Error generating template file name for ID: {} or Name: {}", request.getId(), request.getName(), e);
            // 出错时返回原始模板名称
            return template.getName();
        }
    }
    
    /**
     * 根据请求查找模板
     */
    private PrintTemplateDesignWithBLOBs findTemplate(LoadTemplateRequest request) {
        PrintTemplateDesignWithBLOBs template = null;
        
        // 优先使用id查找，如果id为空则使用name
        if (request.getId() != null && !request.getId().trim().isEmpty()) {
            String templateId = request.getId().trim();
            log.info("通过ID查找模板: {}", templateId);
            
            // 直接通过ID查找模板
            template = printTemplateDesignMapper.selectByPrimaryKey(templateId);
            
            if (template != null) {
                log.info("通过ID找到模板: {}, 名称: {}", templateId, template.getName());
            } else {
                log.warn("通过ID未找到模板: {}", templateId);
            }
        }
        
        // 如果通过ID未找到，且提供了name，则尝试通过name查找
        if (template == null && request.getName() != null && !request.getName().trim().isEmpty()) {
            String searchName = request.getName();
            log.info("通过name查找模板: {}", searchName);
            
            // 处理带.json后缀的文件名
            if (searchName.toLowerCase().endsWith(".json")) {
                searchName = searchName.substring(0, searchName.length() - 5);
            }
            
            // 处理带屏幕类型后缀的模板名称（如 6666_1C -> 6666）
            String baseName = extractBaseName(searchName);
            
            // 首先尝试精确匹配原始名称
            template = findTemplateByName(searchName);
            
            // 如果精确匹配失败，尝试使用基础名称进行精确匹配
            if (template == null && !baseName.equals(searchName)) {
                template = findTemplateByName(baseName);
            }
            
            // 如果仍然失败，使用基础名称进行模糊搜索
            if (template == null) {
                template = findTemplateByNameLike(baseName);
            }
        }
        
        return template;
    }
    
    /**
     * 根据名称查找模板（精确匹配）
     */
    private PrintTemplateDesignWithBLOBs findTemplateByName(String name) {
        return printTemplateDesignMapper.findByName(name);
    }
    
    /**
     * 根据名称查找模板（模糊匹配）
     */
    private PrintTemplateDesignWithBLOBs findTemplateByNameLike(String name) {
        return printTemplateDesignMapper.findByNameLike(name);
    }
    
    /**
     * 提取模板名称的基础部分，去除屏幕类型后缀
     * 例如：6666_1C -> 6666, 7777_2A -> 7777
     */
    private String extractBaseName(String templateName) {
        if (templateName == null || templateName.trim().isEmpty()) {
            return templateName;
        }
        
        // 匹配模式：数字_字母数字组合
        // 例如：6666_1C, 7777_2A 等
        if (templateName.matches(".*_[A-Za-z0-9]+$")) {
            int lastUnderscoreIndex = templateName.lastIndexOf('_');
            if (lastUnderscoreIndex > 0) {
                return templateName.substring(0, lastUnderscoreIndex);
            }
        }
        
        return templateName;
    }
}