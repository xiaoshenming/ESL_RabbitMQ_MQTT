package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.config.RabbitMQConfig;
import com.pandatech.downloadcf.dto.LoadTemplateRequest;
import com.pandatech.downloadcf.dto.RefreshDto;
import com.pandatech.downloadcf.dto.TemplateDto;
import com.pandatech.downloadcf.entity.ActExtTemplatePrintWithBLOBs;
import com.pandatech.downloadcf.mapper.ActExtTemplatePrintMapper;
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
    private final ActExtTemplatePrintMapper actExtTemplatePrintMapper;
    private final ScreenTypeConverter screenTypeConverter;
    
    @Value("${app.template.base-url}")
    private String baseUrl;

    @Override
    public void sendTemplate(TemplateDto templateDto) {
        try {
            // 根据templateId从数据库获取模板信息
            ActExtTemplatePrintWithBLOBs template = actExtTemplatePrintMapper.findById(templateDto.getTemplateId());
            if (template == null) {
                log.error("未找到模板ID: {}", templateDto.getTemplateId());
                throw new RuntimeException("未找到指定的模板");
            }
            
            // 提取屏幕类型
            String screenType = screenTypeConverter.extractScreenType(
                template.getContent(), 
                template.getExtJson(), 
                template.getTagType()
            );
            log.info("模板ID: {} 提取到屏幕类型: {}", template.getId(), screenType);
            
            // 生成带屏幕类型的模板文件名
            String templateFileName = screenTypeConverter.generateTemplateFileName(
                template.getName(), 
                screenType
            );
            log.info("生成的模板文件名: {}", templateFileName);
            
            // 计算模板内容的MD5（用于转换后的官方格式）
            String officialTemplateJson = mqttService.convertToOfficialTemplate(template.getContent(), template);
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
            tmpl.put("name", templateFileName); // 使用带屏幕类型的文件名
            tmpl.put("md5", templateMd5); // 使用转换后内容的MD5
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
        try {
            String message = objectMapper.writeValueAsString(refreshDto);
            rabbitTemplate.convertAndSend(RabbitMQConfig.REFRESH_QUEUE, message);
            log.info("发送刷新消息到队列: {}", message);
        } catch (Exception e) {
            log.error("发送刷新消息失败", e);
        }
    }

    @Override
    public byte[] loadTemple(LoadTemplateRequest request) {
        ActExtTemplatePrintWithBLOBs template = null;
        
        // 优先使用name查找，如果name为空则使用id
        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            String searchName = request.getName();
            
            // 处理带.json后缀的文件名
            if (searchName.toLowerCase().endsWith(".json")) {
                searchName = searchName.substring(0, searchName.length() - 5);
            }
            
            // 处理带屏幕类型后缀的模板名称（如 6666_1C -> 6666）
            String baseName = extractBaseName(searchName);
            
            // 首先尝试精确匹配原始名称
            template = actExtTemplatePrintMapper.findByName(searchName);
            
            // 如果精确匹配失败，尝试使用基础名称进行精确匹配
            if (template == null && !baseName.equals(searchName)) {
                template = actExtTemplatePrintMapper.findByName(baseName);
            }
            
            // 如果仍然失败，使用基础名称进行模糊搜索
            if (template == null) {
                template = actExtTemplatePrintMapper.findByNameLike(baseName);
            }
        } else if (request.getId() != null && !request.getId().trim().isEmpty()) {
            template = actExtTemplatePrintMapper.findById(request.getId());
        }

        if (template == null || template.getContent() == null) {
            return null;
        }

        try {
            // 将数据库中的模板内容（JSON字符串）转换为官方模板格式
            String officialTemplateJson = mqttService.convertToOfficialTemplate(template.getContent(), template);
            return officialTemplateJson.getBytes();
        } catch (JsonProcessingException e) {
            log.error("Error converting template to official format for ID: {} or Name: {}", request.getId(), request.getName(), e);
            return null;
        }
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