package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.config.MqttConfig;
import com.pandatech.downloadcf.config.RabbitMQConfig;
import com.pandatech.downloadcf.dto.RefreshDto;
import com.pandatech.downloadcf.dto.TemplateDto;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMQListener {

    @Qualifier("mqttOutboundChannel")
    private final MessageChannel mqttOutboundChannel;
    private final ObjectMapper objectMapper;
    private final MqttService mqttService; // 注入MqttService以调用转换逻辑

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
            
            // 将消息发送到MQTT
            Message<String> mqttMessage = MessageBuilder.withPayload(message)
                    .setHeader("mqtt_topic", mqttTopic)
                    .build();
            mqttOutboundChannel.send(mqttMessage);
            log.info("模板消息已发送到MQTT主题 {}: {}", mqttTopic, message);
        } catch (Exception e) {
            log.error("处理模板消息并推送到MQTT失败", e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.REFRESH_QUEUE)
    public void receiveRefreshMessage(String message) {
        try {
            log.info("从RabbitMQ接收到刷新消息: {}", message);
            RefreshDto refreshDto = objectMapper.readValue(message, RefreshDto.class);
            
            // TODO: 根据新数据库结构重新实现价签刷新逻辑
            log.warn("价签刷新功能暂时禁用，需要根据新数据库结构重新实现。价签ID: {}", refreshDto.getEslId());
            
            // 暂时直接发送刷新消息到MQTT（可选）
            // 构造刷新消息格式
            Map<String, Object> refreshMessage = new HashMap<>();
            refreshMessage.put("command", "refresh");
            refreshMessage.put("eslId", refreshDto.getEslId());
            refreshMessage.put("timestamp", System.currentTimeMillis() / 1000);
            
            String refreshJson = objectMapper.writeValueAsString(refreshMessage);
            
            // 发送到MQTT（需要确定正确的主题）
            String mqttTopic = "esl/server/refresh/" + refreshDto.getEslId();
            Message<String> mqttMessage = MessageBuilder.withPayload(refreshJson)
                    .setHeader("mqtt_topic", mqttTopic)
                    .build();
            mqttOutboundChannel.send(mqttMessage);
            log.info("刷新消息已发送到MQTT主题 {}: {}", mqttTopic, refreshJson);
            
        } catch (Exception e) {
            log.error("处理刷新消息并推送到MQTT失败", e);
        }
    }
}