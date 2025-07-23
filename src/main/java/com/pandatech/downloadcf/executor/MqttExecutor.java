package com.pandatech.downloadcf.executor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.dto.MessageExecutionData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * MQTT消息执行器 - 通过MQTT协议发送消息
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MqttExecutor implements MessageExecutor {
    
    @Qualifier("mqttOutboundChannel")
    private final MessageChannel mqttOutboundChannel;
    
    private final ObjectMapper objectMapper;
    
    @Override
    public String getExecutorType() {
        return "mqtt";
    }
    
    @Override
    public boolean execute(MessageExecutionData executionData) {
        try {
            log.info("执行MQTT消息发送: destination={}, eslId={}", 
                    executionData.getDestination(), executionData.getEslId());
            
            // 构建MQTT消息
            String jsonPayload = objectMapper.writeValueAsString(executionData.getPayload());
            
            // 发送消息
            mqttOutboundChannel.send(MessageBuilder
                    .withPayload(jsonPayload)
                    .setHeader("mqtt_topic", executionData.getDestination())
                    .build());
            
            log.info("MQTT消息发送成功: eslId={}", executionData.getEslId());
            return true;
            
        } catch (Exception e) {
            log.error("MQTT消息发送失败: eslId={}", executionData.getEslId(), e);
            return false;
        }
    }
    
    @Override
    public boolean isAvailable() {
        return mqttOutboundChannel != null;
    }
    
    @Override
    public String getDescription() {
        return "MQTT消息执行器 - 通过MQTT协议发送价签刷新消息";
    }
}