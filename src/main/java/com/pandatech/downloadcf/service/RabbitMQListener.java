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
            log.info("模板消息已发送到MQTT主题: {}", mqttTopic);
            
        } catch (Exception e) {
            log.error("处理模板消息失败: {}", e.getMessage(), e);
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