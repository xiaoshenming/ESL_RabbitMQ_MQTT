package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.config.MqttConfig;
import com.pandatech.downloadcf.config.RabbitMQConfig;
import com.pandatech.downloadcf.dto.RefreshDto;
import com.pandatech.downloadcf.dto.TemplateDto;
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
            
            // 直接将消息发送到MQTT
            Message<String> mqttMessage = MessageBuilder.withPayload(message)
                    .setHeader("mqtt_topic", "esl/template/download")
                    .build();
            mqttOutboundChannel.send(mqttMessage);
            log.info("模板消息已发送到MQTT: {}", message);
        } catch (Exception e) {
            log.error("处理模板消息并推送到MQTT失败", e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.REFRESH_QUEUE)
    public void receiveRefreshMessage(String message) {
        try {
            log.info("从RabbitMQ接收到刷新消息: {}", message);
            RefreshDto refreshDto = objectMapper.readValue(message, RefreshDto.class);
            
            // 使用MqttService处理价签刷新
            mqttService.processRefresh(refreshDto.getEslId());
        } catch (Exception e) {
            log.error("处理刷新消息并推送到MQTT失败", e);
        }
    }
}