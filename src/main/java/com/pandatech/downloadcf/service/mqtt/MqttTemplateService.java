package com.pandatech.downloadcf.service.mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs;
import com.pandatech.downloadcf.service.DataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * MQTT模板服务
 * 专门负责模板相关的MQTT操作
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MqttTemplateService {

    @Value("${app.template.base-url:http://localhost:8999/api/res/templ/loadtemple}")
    private String templateBaseUrl;

    private final ObjectMapper objectMapper;
    private final DataService dataService;
    private final MqttTemplateConverter templateConverter;
    
    @Qualifier("mqttOutboundChannel")
    private final MessageChannel mqttOutboundChannel;

    /**
     * 发送模板到MQTT - 优化版
     * 构建tmpllist格式的MQTT消息并发送
     */
    public void sendTemplateToMqtt(String shop, String templateId, String templateName) {
        try {
            log.info("开始发送模板到MQTT - 门店: {}, 模板ID: {}, 模板名称: {}", shop, templateId, templateName);
            
            // 验证参数
            validateParameters(shop, templateId);
            
            // 从数据库获取真实的模板数据
            PrintTemplateDesignWithBLOBs template = dataService.getTemplateById(templateId);
            if (template == null) {
                log.error("未找到模板ID: {}", templateId);
                throw new RuntimeException("未找到指定的模板: " + templateId);
            }
            
            // 如果传入的模板名称为空，使用数据库中的名称
            String actualTemplateName = determineTemplateName(templateName, template);
            
            log.info("使用模板名称: {}", actualTemplateName);

            String officialJson = templateConverter.convertToOfficialTemplate(template);
            String md5 = calculateMD5(officialJson);
            
            // 从官方模板JSON中提取TagType
            String tagType = templateConverter.extractTagTypeFromOfficialTemplate(officialJson);

            String topic = "esl/server/data/" + shop;
            String message = buildTmplListMessage(shop, templateId, actualTemplateName, md5, tagType);

            sendMqttMessage(topic, message.getBytes());

            log.info("模板消息发送成功 - 主题: {}, 模板ID: {}, 模板名称: {}, TagType: {}", topic, templateId, actualTemplateName, tagType);
        } catch (Exception e) {
            log.error("发送模板到MQTT失败: {}", e.getMessage(), e);
            throw new RuntimeException("MQTT发送失败: " + e.getMessage(), e);
        }
    }

    /**
     * 验证参数
     */
    private void validateParameters(String shop, String templateId) {
        if (shop == null || shop.trim().isEmpty()) {
            throw new IllegalArgumentException("门店编码不能为空");
        }
        if (templateId == null || templateId.trim().isEmpty()) {
            throw new IllegalArgumentException("模板ID不能为空");
        }
    }

    /**
     * 确定模板名称
     */
    private String determineTemplateName(String templateName, PrintTemplateDesignWithBLOBs template) {
        return (templateName != null && !templateName.trim().isEmpty()) 
            ? templateName.trim() 
            : (template.getName() != null ? template.getName() : "2");
    }

    /**
     * 构建tmpllist格式消息 - 严格匹配用户指定结构
     */
    private String buildTmplListMessage(String shop, String templateId, String templateName, String md5, String tagType) throws JsonProcessingException {
        Map<String, Object> message = new HashMap<>();
        
        // 生成随机UUID作为消息ID
        String messageId = UUID.randomUUID().toString();
        // 生成完整的时间戳
        long currentTimeMillis = System.currentTimeMillis();
        double timestamp = currentTimeMillis / 1000.0;
        
        message.put("shop", shop);
        message.put("id", messageId);
        message.put("command", "tmpllist");
        message.put("timestamp", timestamp);

        Map<String, Object> data = new HashMap<>();
        data.put("name", templateName);
        data.put("md5", md5);
        data.put("url", templateBaseUrl + "?id=" + templateId);
        data.put("tagtype", tagType);

        message.put("data", data);

        return objectMapper.writeValueAsString(message);
    }

    /**
     * 发送MQTT消息
     */
    private void sendMqttMessage(String topic, byte[] payload) {
        try {
            mqttOutboundChannel.send(MessageBuilder.withPayload(payload)
                    .setHeader("mqtt_topic", topic)
                    .build());
            log.debug("MQTT消息发送成功 - 主题: {}, 负载大小: {} bytes", topic, payload.length);
        } catch (Exception e) {
            log.error("发送MQTT消息失败 - 主题: {}, 错误: {}", topic, e.getMessage(), e);
            throw new RuntimeException("MQTT消息发送失败", e);
        }
    }

    /**
     * 计算MD5哈希值
     */
    private String calculateMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5算法不可用", e);
        }
    }
}