package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;

/**
 * MQTT消息构建器
 * 负责构建各种类型的MQTT消息
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MqttMessageBuilder {

    @Value("${app.template.base-url:http://localhost:8999/api/res/templ/loadtemple}")
    private String templateBaseUrl;

    private final ObjectMapper objectMapper;

    /**
     * 构建tmpllist格式消息 - 严格匹配用户指定结构
     */
    public String buildTmplListMessage(String shop, String templateId, String templateName, String md5, String tagType) throws JsonProcessingException {
        Map<String, Object> message = new HashMap<>();
        
        // 生成随机UUID作为消息ID
        String messageId = UUID.randomUUID().toString();
        // 生成完整的时间戳（避免科学计数法省略值）
        long currentTimeMillis = System.currentTimeMillis();
        double timestamp = currentTimeMillis / 1000.0;
        
        message.put("shop", shop);
        message.put("id", messageId);
        message.put("command", "tmpllist");
        message.put("timestamp", timestamp);

        Map<String, Object> data = new HashMap<>();
        data.put("url", templateBaseUrl);
        data.put("tid", UUID.randomUUID().toString());

        List<Map<String, String>> tmpls = new ArrayList<>();
        Map<String, String> tmpl = new HashMap<>();
        
        // 构建正确的文件名格式：{templateName}_{tagType}.json
        String fileName;
        if (tagType != null && !tagType.isEmpty()) {
            fileName = templateName + "_" + tagType + ".json";
        } else {
            // 如果TagType为空，使用默认值06
            fileName = templateName + "_06.json";
            log.warn("TagType为空，使用默认值06: templateId={}, templateName={}", templateId, templateName);
        }
        
        tmpl.put("name", fileName);
        tmpl.put("id", templateId);
        tmpl.put("md5", md5);
        tmpls.add(tmpl);
        data.put("tmpls", tmpls);

        message.put("data", data);
        
        log.debug("构建tmpllist消息: templateId={}, fileName={}, md5={}, 消息ID={}, 时间戳={}", 
                templateId, fileName, md5, messageId, timestamp);
        return objectMapper.writeValueAsString(message);
    }

    /**
     * 构建刷新消息
     */
    public String buildRefreshMessage(String shop, String deviceCode, String deviceMac, Map<String, Object> refreshData) throws JsonProcessingException {
        Map<String, Object> message = new HashMap<>();
        
        String messageId = UUID.randomUUID().toString();
        long currentTimeMillis = System.currentTimeMillis();
        double timestamp = currentTimeMillis / 1000.0;
        
        message.put("shop", shop);
        message.put("id", messageId);
        message.put("command", "refresh");
        message.put("timestamp", timestamp);

        Map<String, Object> data = new HashMap<>();
        data.put("deviceCode", deviceCode);
        data.put("deviceMac", deviceMac);
        data.putAll(refreshData);

        message.put("data", data);
        
        log.debug("构建refresh消息: deviceCode={}, deviceMac={}, 消息ID={}", 
                deviceCode, deviceMac, messageId);
        return objectMapper.writeValueAsString(message);
    }

    /**
     * 构建批量刷新消息
     */
    public String buildBatchRefreshMessage(String shop, List<Map<String, Object>> devices) throws JsonProcessingException {
        Map<String, Object> message = new HashMap<>();
        
        String messageId = UUID.randomUUID().toString();
        long currentTimeMillis = System.currentTimeMillis();
        double timestamp = currentTimeMillis / 1000.0;
        
        message.put("shop", shop);
        message.put("id", messageId);
        message.put("command", "batchRefresh");
        message.put("timestamp", timestamp);

        Map<String, Object> data = new HashMap<>();
        data.put("devices", devices);
        data.put("count", devices.size());

        message.put("data", data);
        
        log.debug("构建batchRefresh消息: 设备数量={}, 消息ID={}", devices.size(), messageId);
        return objectMapper.writeValueAsString(message);
    }

    /**
     * 构建通用MQTT消息
     */
    public String buildGenericMessage(String shop, String command, Map<String, Object> data) throws JsonProcessingException {
        Map<String, Object> message = new HashMap<>();
        
        String messageId = UUID.randomUUID().toString();
        long currentTimeMillis = System.currentTimeMillis();
        double timestamp = currentTimeMillis / 1000.0;
        
        message.put("shop", shop);
        message.put("id", messageId);
        message.put("command", command);
        message.put("timestamp", timestamp);
        message.put("data", data != null ? data : new HashMap<>());
        
        log.debug("构建{}消息: 消息ID={}", command, messageId);
        return objectMapper.writeValueAsString(message);
    }

    /**
     * 计算MD5值
     */
    public String calculateMD5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes("UTF-8"));
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            log.error("MD5计算失败", e);
            return UUID.randomUUID().toString();
        }
    }

    /**
     * 生成MQTT主题
     */
    public String generateTopic(String shop, String type) {
        switch (type.toLowerCase()) {
            case "data":
                return "esl/server/data/" + shop;
            case "refresh":
                return "esl/server/refresh/" + shop;
            case "batch":
                return "esl/server/batch/" + shop;
            default:
                return "esl/server/" + type + "/" + shop;
        }
    }

    /**
     * 验证消息参数
     */
    public void validateMessageParams(String shop, String templateId, String templateName) {
        if (shop == null || shop.trim().isEmpty()) {
            throw new IllegalArgumentException("门店编码不能为空");
        }
        if (templateId == null || templateId.trim().isEmpty()) {
            throw new IllegalArgumentException("模板ID不能为空");
        }
        // templateName可以为空，会使用默认值
    }
}