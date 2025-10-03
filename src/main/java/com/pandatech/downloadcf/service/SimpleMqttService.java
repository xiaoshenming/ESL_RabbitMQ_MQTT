package com.pandatech.downloadcf.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * 简单MQTT服务
 * 替代已删除的MqttServiceRefactored，提供基本的MQTT消息发送功能
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleMqttService {

    @Qualifier("mqttOutboundChannel")
    private final MessageChannel mqttOutboundChannel;

    /**
     * 发送MQTT消息
     * @param topic MQTT主题
     * @param payload 消息内容
     * @return 发送是否成功
     */
    public boolean sendMqttMessage(String topic, String payload) {
        try {
            log.debug("发送MQTT消息 - 主题: {}, 内容长度: {} bytes", topic, payload.length());
            
            mqttOutboundChannel.send(MessageBuilder.withPayload(payload.getBytes())
                    .setHeader("mqtt_topic", topic)
                    .build());
            
            log.debug("MQTT消息发送成功 - 主题: {}", topic);
            return true;
        } catch (Exception e) {
            log.error("发送MQTT消息失败 - 主题: {}, 错误: {}", topic, e.getMessage(), e);
            return false;
        }
    }

    /**
     * 发送MQTT消息（字节数组）
     * @param topic MQTT主题
     * @param payload 消息内容（字节数组）
     * @return 发送是否成功
     */
    public boolean sendMqttMessage(String topic, byte[] payload) {
        try {
            log.debug("发送MQTT消息 - 主题: {}, 负载大小: {} bytes", topic, payload.length);
            
            mqttOutboundChannel.send(MessageBuilder.withPayload(payload)
                    .setHeader("mqtt_topic", topic)
                    .build());
            
            log.debug("MQTT消息发送成功 - 主题: {}", topic);
            return true;
        } catch (Exception e) {
            log.error("发送MQTT消息失败 - 主题: {}, 错误: {}", topic, e.getMessage(), e);
            return false;
        }
    }
}