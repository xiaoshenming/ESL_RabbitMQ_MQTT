package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * RabbitMQ消息监听器
 * 处理来自RabbitMQ的消息并转发到MQTT
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMQListener {

    @Qualifier("mqttOutboundChannel")
    private final MessageChannel mqttOutboundChannel;
    private final ObjectMapper objectMapper;
    private final MqttService mqttService;

    /**
     * 监听模板消息队列
     */
    @RabbitListener(queues = RabbitMQConfig.TEMPLATE_QUEUE)
    public void receiveTemplateMessage(String message) {
        try {
            log.info("从RabbitMQ接收到模板消息: {}", message);
            
            // 解析消息
            @SuppressWarnings("unchecked")
            Map<String, Object> messageMap = objectMapper.readValue(message, Map.class);
            log.debug("解析后的消息Map: {}", messageMap);
            
            String shop = (String) messageMap.get("shop");
            Object idObj = messageMap.get("id");
            String templateId = idObj != null ? idObj.toString() : null; // 确保转换为字符串
            log.debug("提取的字段 - shop: {}, id对象: {}, templateId: {}", shop, idObj, templateId);
            
            // 从data.tmpls[0].name中提取模板名称（去掉后缀）
            String templateName = null;
            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> data = (Map<String, Object>) messageMap.get("data");
                log.debug("data字段: {}", data);
                if (data != null) {
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> tmpls = (List<Map<String, Object>>) data.get("tmpls");
                    log.debug("tmpls字段: {}", tmpls);
                    if (tmpls != null && !tmpls.isEmpty()) {
                        String fileName = (String) tmpls.get(0).get("name");
                        log.debug("文件名: {}", fileName);
                        if (fileName != null) {
                            // 从文件名中提取模板名称（去掉_XX.json后缀）
                            int underscoreIndex = fileName.lastIndexOf('_');
                            if (underscoreIndex > 0) {
                                templateName = fileName.substring(0, underscoreIndex);
                            } else {
                                // 如果没有下划线，去掉.json后缀
                                templateName = fileName.replace(".json", "");
                            }
                            log.debug("提取的模板名称: {}", templateName);
                        }
                    }
                }
            } catch (Exception e) {
                log.warn("解析模板名称失败: {}", e.getMessage());
            }
            
            // 验证必要参数
            if (shop == null || shop.trim().isEmpty()) {
                log.error("门店编码为空，消息: {}", message);
                throw new IllegalArgumentException("门店编码不能为空");
            }
            
            if (templateId == null || templateId.trim().isEmpty()) {
                log.error("模板ID为空，消息: {}", message);
                throw new IllegalArgumentException("模板ID不能为空");
            }
            
            // 模板名称可以为空，MqttService会处理
            log.info("解析消息成功 - 门店: {}, 模板ID: {}, 模板名称: {}", shop, templateId, templateName);
            
            // 调用MqttService发送优化后的tmpllist消息
            mqttService.sendTemplateToMqtt(shop, templateId, templateName);
            log.info("模板消息已通过MqttService发送到MQTT");
            
        } catch (Exception e) {
            log.error("处理模板消息失败: {}", e.getMessage(), e);
            // 可以考虑将失败的消息发送到死信队列或重试队列
        }
    }

    /**
     * 监听刷新消息队列
     */
    @RabbitListener(queues = RabbitMQConfig.REFRESH_QUEUE)
    public void receiveRefreshMessage(String message) {
        try {
            log.info("从RabbitMQ接收到刷新消息: {}", message);
            
            // 直接将完整的MQTT消息发送到MQTT
            // 解析消息获取门店编码
            @SuppressWarnings("unchecked")
            Map<String, Object> messageMap = objectMapper.readValue(message, Map.class);
            String storeCode = (String) messageMap.get("shop");
            
            // 构造正确的MQTT主题：esl/server/data/{storeCode}
            String mqttTopic = "esl/server/data/" + storeCode;
            
            // 发送到MQTT
            Message<String> mqttMessage = MessageBuilder
                    .withPayload(message)
                    .setHeader("mqtt_topic", mqttTopic)
                    .build();
            
            mqttOutboundChannel.send(mqttMessage);
            log.info("刷新消息已发送到MQTT主题: {}", mqttTopic);
            
        } catch (Exception e) {
            log.error("处理刷新消息失败: {}", e.getMessage(), e);
        }
    }
}