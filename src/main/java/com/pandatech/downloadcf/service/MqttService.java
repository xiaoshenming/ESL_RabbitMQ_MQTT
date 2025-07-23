package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * MQTT服务类
 * 处理MQTT消息的发送和接收
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MqttService {

    @Value("${app.template.base-url}")
    private String templateBaseUrl;

    @Qualifier("mqttOutboundChannel")
    private final MessageChannel mqttOutboundChannel;
    private final ObjectMapper objectMapper;

    /**
     * 处理接收到的MQTT消息
     */
    @ServiceActivator(inputChannel = "mqttInputChannel")
    @SuppressWarnings("unchecked")
    public void handleMessage(@Header(MqttHeaders.RECEIVED_TOPIC) String topic, String payload) {
        log.info("接收到MQTT消息 - 主题: {}, 内容: {}", topic, payload);
        // 注意：MQTT消息处理功能需要根据新数据库结构重新实现
        log.warn("MQTT消息处理功能暂时禁用，需要根据新数据库结构重新实现");
    }

    /**
     * 发送模板到MQTT
     */
    public void sendTemplateToMqtt(String topic, PrintTemplateDesignWithBLOBs template, String screenType) {
        try {
            // 验证输入参数
            if (template == null) {
                log.error("模板对象为空");
                return;
            }

            if (topic == null || topic.trim().isEmpty()) {
                topic = "esl/template/default";
            }

            // 构造模板消息
            String templateMessage = generateTemplateMessage(template, screenType);
            
            // 发送MQTT消息
            sendMqttMessage(topic, templateMessage.getBytes());
            
        } catch (Exception e) {
            log.error("发送模板到MQTT失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 生成模板消息
     */
    private String generateTemplateMessage(PrintTemplateDesignWithBLOBs template, String screenType) {
        try {
            Map<String, Object> message = new HashMap<>();
            message.put("command", "tmpllist");
            
            Map<String, Object> data = new HashMap<>();
            data.put("url", templateBaseUrl);
            
            List<Map<String, String>> tmpls = new ArrayList<>();
            Map<String, String> tmpl = new HashMap<>();
            
            // 生成文件名
            String fileName = template.getName() + "_" + screenType + ".json";
            tmpl.put("name", fileName);
            tmpl.put("md5", calculateMD5(template.getContent()));
            
            tmpls.add(tmpl);
            data.put("tmpls", tmpls);
            message.put("data", data);
            
            return objectMapper.writeValueAsString(message);
            
        } catch (Exception e) {
            log.error("生成模板消息失败: {}", e.getMessage(), e);
            return "{}";
        }
    }

    /**
     * 发送MQTT消息
     */
    private void sendMqttMessage(String topic, byte[] payload) {
        try {
            Message<byte[]> message = MessageBuilder
                    .withPayload(payload)
                    .setHeader("mqtt_topic", topic)
                    .build();
            
            mqttOutboundChannel.send(message);
            log.info("MQTT消息发送成功 - 主题: {}, 数据长度: {}", topic, payload.length);
            
        } catch (Exception e) {
            log.error("MQTT消息发送失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 计算MD5校验码
     */
    private String calculateMD5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes("UTF-8"));
            byte[] digest = md.digest();
            return new BigInteger(1, digest).toString(16);
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            log.error("计算MD5失败", e);
            return "";
        }
    }

    /**
     * 构建MQTT主题
     */
    public String buildMqttTopic(String storeCode) {
        return String.format("esl/%s/data", storeCode);
    }
}