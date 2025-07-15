package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.config.RabbitMQConfig;
import com.pandatech.downloadcf.dto.LoadTemplateRequest;
import com.pandatech.downloadcf.dto.RefreshDto;
import com.pandatech.downloadcf.dto.TemplateDto;
import com.pandatech.downloadcf.entity.ActExtTemplatePrintWithBLOBs;
import com.pandatech.downloadcf.mapper.ActExtTemplatePrintMapper;
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
            
            // 构造MQTT消息格式
            Map<String, Object> mqttMessage = new HashMap<>();
            mqttMessage.put("command", "tmpllist");
            
            Map<String, Object> data = new HashMap<>();
            data.put("url", baseUrl);
            data.put("tid", "396a5189-53d8-4354-bcfa-27d57d9d69ad"); // 多租户ID，可以配置化
            
            // 构造模板列表
             List<Map<String, Object>> tmpls = new ArrayList<>();
             Map<String, Object> tmpl = new HashMap<>();
             tmpl.put("name", template.getName() + ".json");
             tmpl.put("md5", calculateMd5(template.getContent()));
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
        ActExtTemplatePrintWithBLOBs template;
        if (request.getName() != null && !request.getName().isEmpty()) {
            template = actExtTemplatePrintMapper.findByName(request.getName());
        } else {
            template = actExtTemplatePrintMapper.findById(request.getId());
        }

        if (template == null || template.getContent() == null) {
            return null;
        }

        try {
            // 将数据库中的模板内容（JSON字符串）转换为官方模板格式
            String officialTemplateJson = mqttService.convertToOfficialTemplate(template.getContent());
            return officialTemplateJson.getBytes();
        } catch (JsonProcessingException e) {
            log.error("Error converting template to official format for ID: {} or Name: {}", request.getId(), request.getName(), e);
            return null;
        }
    }
}