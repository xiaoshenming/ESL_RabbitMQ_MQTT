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
            
            // 直接将完整的MQTT消息发送到MQTT
            // 解析消息获取门店编码
            @SuppressWarnings("unchecked")
            Map<String, Object> messageMap = objectMapper.readValue(message, Map.class);
            String storeCode = (String) messageMap.get("shop");
            
            if (storeCode == null) {
                log.error("刷新消息中缺少shop字段，无法确定MQTT主题");
                return;
            }
            
            // 构造正确的MQTT主题：esl/server/data/{storeCode}
            String mqttTopic = "esl/server/data/" + storeCode;
            
            // 将完整的MQTT消息发送到MQTT
            Message<String> mqttMessage = MessageBuilder.withPayload(message)
                    .setHeader("mqtt_topic", mqttTopic)
                    .build();
            mqttOutboundChannel.send(mqttMessage);
            log.info("刷新消息已发送到MQTT主题 {}: {}", mqttTopic, message);
            
        } catch (Exception e) {
            log.error("处理刷新消息并推送到MQTT失败", e);
        }
    }
}