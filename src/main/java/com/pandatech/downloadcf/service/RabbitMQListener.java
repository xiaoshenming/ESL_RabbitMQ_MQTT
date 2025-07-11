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
            TemplateDto templateDto = objectMapper.readValue(message, TemplateDto.class);

            // 模拟从数据库加载数据并转换为二进制流
            // 这里的逻辑应该和MqttService中的相似，但由HTTP触发
            // 为了简化，我们直接将收到的templateData作为推送内容
            byte[] binaryData = templateDto.getTemplateData().getBytes();

            // 动态设置topic，例如，推送到特定价签的topic
            String topic = "esl/" + templateDto.getEslId() + "/template";
            Message<byte[]> mqttMessage = MessageBuilder.withPayload(binaryData)
                    .setHeader("mqtt_topic", topic)
                    .build();

            mqttOutboundChannel.send(mqttMessage);
            log.info("通过MQTT推送模板到主题: {}", topic);
        } catch (Exception e) {
            log.error("处理模板消息并推送到MQTT失败", e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.REFRESH_QUEUE)
    public void receiveRefreshMessage(String message) {
        try {
            log.info("从RabbitMQ接收到刷新消息: {}", message);
            RefreshDto refreshDto = objectMapper.readValue(message, RefreshDto.class);

            // 刷新操作通常是发送一个简单的指令
            String refreshCommand = "{\"action\": \"refresh\"}";
            byte[] binaryData = refreshCommand.getBytes();

            String topic = "esl/" + refreshDto.getEslId() + "/refresh";
            Message<byte[]> mqttMessage = MessageBuilder.withPayload(binaryData)
                    .setHeader("mqtt_topic", topic)
                    .build();

            mqttOutboundChannel.send(mqttMessage);
            log.info("通过MQTT推送刷新指令到主题: {}", topic);
        } catch (Exception e) {
            log.error("处理刷新消息并推送到MQTT失败", e);
        }
    }
}