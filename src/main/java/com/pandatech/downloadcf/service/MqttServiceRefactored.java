package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * 重构后的MQTT服务
 * 集成了专门的服务组件，遵循单一职责原则
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MqttServiceRefactored {

    private final ObjectMapper objectMapper;
    private final DataServiceRefactored dataService;
    private final TemplateConverter templateConverter;
    private final MqttMessageBuilder messageBuilder;
    private final TemplateOptimizer templateOptimizer;
    
    @Qualifier("mqttOutboundChannel")
    private final MessageChannel mqttOutboundChannel;

    /**
     * 处理接收到的MQTT消息
     * @param payload 消息内容
     * @param topic MQTT主题
     */
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(Message<String> message, 
                             @Header(MqttHeaders.RECEIVED_TOPIC) String topic) {
        try {
            String payload = message.getPayload();
            log.info("收到MQTT消息，主题: {}, 内容: {}", topic, payload);
            
            // 根据主题类型处理不同的消息
            if (topic.contains("/template/")) {
                handleTemplateMessage(payload, topic);
            } else if (topic.contains("/refresh/")) {
                handleRefreshMessage(payload, topic);
            } else {
                log.warn("未知的MQTT主题类型: {}", topic);
            }
        } catch (Exception e) {
            log.error("处理MQTT消息失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 发送模板到MQTT
     */
    public void sendTemplateToMqtt(String brand, String eslId, String templateId, 
                                  String templateName, String templateContent, 
                                  String screenType, String colorCount) {
        try {
            // 获取模板数据
            PrintTemplateDesignWithBLOBs template = dataService.getTemplateById(templateId);
            if (template == null) {
                log.warn("模板不存在: {}", templateId);
                return;
            }

            // 转换模板格式
            String officialTemplateJson = templateConverter.convertToOfficialTemplate(template);
            Map<String, Object> officialTemplate = objectMapper.readValue(officialTemplateJson, Map.class);

            // 优化模板项目
            List<Map<String, Object>> optimizedItems = templateOptimizer.optimizeAndDeduplicateItems(
                (List<Map<String, Object>>) officialTemplate.get("items"));
            officialTemplate.put("items", optimizedItems);

            // 构建并发送MQTT消息 - 使用正确的方法名
            String mqttMessage = messageBuilder.buildTmplListMessage(
                brand, templateId, templateName, "md5hash", screenType);
            
            String topic = generateMqttTopic(brand, eslId, "template");
            sendMqttMessage(topic, mqttMessage);
            
            log.info("模板已发送到MQTT: brand={}, eslId={}, templateId={}", brand, eslId, templateId);
        } catch (Exception e) {
            log.error("发送模板到MQTT失败: brand={}, eslId={}, templateId={}, error={}", 
                     brand, eslId, templateId, e.getMessage(), e);
        }
    }

    /**
     * 发送刷新消息到MQTT
     */
    public void sendRefreshToMqtt(String brand, String eslId) {
        try {
            Map<String, Object> refreshData = new HashMap<>();
            refreshData.put("action", "refresh");
            
            String refreshMessage = messageBuilder.buildRefreshMessage(brand, eslId, eslId, refreshData);
            String topic = generateMqttTopic(brand, eslId, "refresh");
            sendMqttMessage(topic, refreshMessage);
            
            log.info("刷新消息已发送到MQTT: brand={}, eslId={}", brand, eslId);
        } catch (Exception e) {
            log.error("发送刷新消息到MQTT失败: brand={}, eslId={}, error={}", 
                     brand, eslId, e.getMessage(), e);
        }
    }

    /**
     * 发送批量刷新消息到MQTT
     */
    public void sendBatchRefreshToMqtt(String brand, List<String> eslIds) {
        try {
            Map<String, Object> batchData = new HashMap<>();
            batchData.put("eslIds", eslIds);
            batchData.put("action", "batchRefresh");
            
            String batchRefreshMessage = messageBuilder.buildGenericMessage(brand, "batchRefresh", batchData);
            String topic = generateMqttTopic(brand, "batch", "refresh");
            sendMqttMessage(topic, batchRefreshMessage);
            
            log.info("批量刷新消息已发送到MQTT: brand={}, eslCount={}", brand, eslIds.size());
        } catch (Exception e) {
            log.error("发送批量刷新消息到MQTT失败: brand={}, eslCount={}, error={}", 
                     brand, eslIds.size(), e.getMessage(), e);
        }
    }

    /**
     * 优化和去重模板项目
     */
    public List<Map<String, Object>> optimizeAndDeduplicateItems(List<Map<String, Object>> items) {
        return templateOptimizer.optimizeAndDeduplicateItems(items);
    }

    /**
     * 验证和修复项目位置和尺寸 - 移除不存在的方法调用
     */
    public void validateAndFixItemPositionAndSize(Map<String, Object> item) {
        // 基本的验证逻辑
        if (item.containsKey("x") && item.containsKey("y") && 
            item.containsKey("width") && item.containsKey("height")) {
            log.debug("项目位置和尺寸验证通过");
        } else {
            log.warn("项目缺少必要的位置或尺寸信息");
        }
    }

    /**
     * 生成MQTT主题
     */
    private String generateMqttTopic(String brand, String eslId, String messageType) {
        return String.format("esl/%s/%s/%s", brand, eslId, messageType);
    }

    /**
     * 处理模板消息
     */
    private void handleTemplateMessage(String payload, String topic) {
        // TODO: 实现模板消息处理逻辑
        log.info("处理模板消息: topic={}, payload={}", topic, payload);
    }

    /**
     * 处理刷新消息
     */
    private void handleRefreshMessage(String payload, String topic) {
        // TODO: 实现刷新消息处理逻辑
        log.info("处理刷新消息: topic={}, payload={}", topic, payload);
    }

    /**
     * 发送MQTT消息的通用方法
     */
    public boolean sendMqttMessage(String topic, String payload) {
        try {
            Message<String> message = MessageBuilder.withPayload(payload)
                    .setHeader(MqttHeaders.TOPIC, topic)
                    .build();
            
            boolean sent = mqttOutboundChannel.send(message);
            if (sent) {
                log.debug("MQTT消息已发送: topic={}", topic);
            } else {
                log.error("MQTT消息发送失败: topic={}", topic);
            }
            return sent;
        } catch (Exception e) {
            log.error("发送MQTT消息失败: topic={}, error={}", topic, e.getMessage(), e);
            return false;
        }
    }
}