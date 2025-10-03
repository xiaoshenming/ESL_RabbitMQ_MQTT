package com.pandatech.downloadcf.service;

import com.pandatech.downloadcf.dto.BrandOutputData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 消息目标地址服务
 * 专门处理不同品牌和协议的消息目标地址构建
 */
@Slf4j
@Service
public class MessageDestinationService {
    
    /**
     * 构建MQTT主题
     */
    public String buildMqttTopic(BrandOutputData outputData) {
        // 根据品牌选择不同的MQTT主题格式
        if ("YALIANG001".equals(outputData.getBrandCode())) {
            return buildYaliangMqttTopic(outputData.getStoreCode());
        } else {
            return buildPandaMqttTopic(outputData.getStoreCode());
        }
    }
    
    /**
     * 构建攀攀品牌MQTT主题
     */
    private String buildPandaMqttTopic(String storeCode) {
        String topic = String.format("esl/server/data/%s", storeCode);
        log.debug("构建攀攀MQTT主题: {}", topic);
        return topic;
    }
    
    /**
     * 为YALIANG品牌构建特定的MQTT主题
     */
    private String buildYaliangMqttTopic(String storeCode) {
        // YALIANG品牌使用固定的MQTT主题格式: yl-esl/XD010012/refresh/queue
        String topic = "yl-esl/XD010012/refresh/queue";
        log.debug("构建雅量MQTT主题: {}", topic);
        return topic;
    }
    
    /**
     * 构建HTTP URL
     */
    public String buildHttpUrl(BrandOutputData outputData) {
        // 根据品牌选择不同的HTTP URL格式
        if ("YALIANG001".equals(outputData.getBrandCode())) {
            return buildYaliangHttpUrl(outputData.getStoreCode());
        } else {
            return buildPandaHttpUrl(outputData.getStoreCode());
        }
    }
    
    /**
     * 构建攀攀品牌HTTP URL
     */
    private String buildPandaHttpUrl(String storeCode) {
        String url = String.format("http://api.panda.com/esl/data/%s", storeCode);
        log.debug("构建攀攀HTTP URL: {}", url);
        return url;
    }
    
    /**
     * 构建雅量品牌HTTP URL
     */
    private String buildYaliangHttpUrl(String storeCode) {
        String url = String.format("http://api.yaliang.com/esl/refresh/%s", storeCode);
        log.debug("构建雅量HTTP URL: {}", url);
        return url;
    }
    
    /**
     * 根据品牌和协议类型构建目标地址
     */
    public String buildDestination(BrandOutputData outputData, String protocol) {
        switch (protocol.toLowerCase()) {
            case "mqtt":
                return buildMqttTopic(outputData);
            case "http":
            case "https":
                return buildHttpUrl(outputData);
            default:
                log.warn("不支持的协议类型: {}, 使用默认MQTT", protocol);
                return buildMqttTopic(outputData);
        }
    }
    
    /**
     * 验证目标地址格式
     */
    public boolean isValidDestination(String destination, String protocol) {
        if (destination == null || destination.trim().isEmpty()) {
            return false;
        }
        
        switch (protocol.toLowerCase()) {
            case "mqtt":
                return isValidMqttTopic(destination);
            case "http":
            case "https":
                return isValidHttpUrl(destination);
            default:
                return false;
        }
    }
    
    /**
     * 验证MQTT主题格式
     */
    private boolean isValidMqttTopic(String topic) {
        // MQTT主题不能为空，不能包含通配符（在发布时）
        return topic != null && !topic.trim().isEmpty() && 
               !topic.contains("+") && !topic.contains("#");
    }
    
    /**
     * 验证HTTP URL格式
     */
    private boolean isValidHttpUrl(String url) {
        return url != null && (url.startsWith("http://") || url.startsWith("https://"));
    }
}