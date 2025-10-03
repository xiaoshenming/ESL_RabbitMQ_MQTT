package com.pandatech.downloadcf.brands.yaliang.service;

import com.pandatech.downloadcf.brands.yaliang.config.YaliangBrandConfig;
import com.pandatech.downloadcf.brands.yaliang.dto.YaliangRefreshMessage;
import com.pandatech.downloadcf.brands.yaliang.dto.YaliangRefreshRequest;
import com.pandatech.downloadcf.brands.yaliang.exception.YaliangException;
import com.pandatech.downloadcf.brands.yaliang.service.YaliangEslIdParser.YaliangEslIdInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 雅量MQTT消息构建服务
 * 负责构建雅量品牌特有的MQTT消息格式
 */
@Slf4j
@Service
public class YaliangMessageBuilder {
    
    @Autowired
    private YaliangBrandConfig config;
    
    /**
     * 创建雅量专用的MQTT消息
     */
    public Map<String, Object> createYaliangMqttMessage(YaliangEslIdInfo eslIdInfo, String templateBase64) {
        Map<String, Object> message = new HashMap<>();
        
        // 基本信息
        message.put("queueId", generateQueueId());
        message.put("deviceType", config.getDefaultDeviceType());
        message.put("deviceCode", eslIdInfo.getDeviceCode());
        message.put("deviceMac", eslIdInfo.getDeviceMac());
        message.put("deviceVersion", "4.0.0");
        message.put("refreshAction", config.getDefaultRefreshAction());
        message.put("refreshArea", config.getDefaultRefreshArea());
        
        // 内容列表
        List<Map<String, Object>> contentList = new ArrayList<>();
        Map<String, Object> contentItem = new HashMap<>();
        contentItem.put("dataType", config.getDataFormat().getImageDataType());
        contentItem.put("dataRef", templateBase64 != null ? templateBase64 : "");
        contentItem.put("layerEnd", config.getDataFormat().isLayerEnd());
        contentList.add(contentItem);
        
        message.put("content", contentList);
        
        log.info("雅量MQTT消息创建完成: queueId={}, deviceCode={}, deviceMac={}, 图片大小={}KB", 
                message.get("queueId"), eslIdInfo.getDeviceCode(), eslIdInfo.getDeviceMac(), 
                templateBase64 != null ? templateBase64.length() / 1024 : 0);
        
        return message;
    }
    
    /**
     * 创建刷新消息
     */
    public YaliangRefreshMessage createRefreshMessage(YaliangRefreshRequest request, String templateBase64) {
        log.info("开始创建雅量刷新消息: deviceCode={}, deviceMac={}", 
                request.getDeviceCode(), request.getDeviceMac());
        
        try {
            // 创建刷新消息
            YaliangRefreshMessage message = new YaliangRefreshMessage();
            
            // 设置基本信息
            message.setQueueId(generateQueueId());
            message.setDeviceType(config.getDefaultDeviceType());
            message.setDeviceCode(request.getDeviceCode());
            message.setDeviceMac(request.getDeviceMac());
            message.setDeviceVersion("4.0.0");
            message.setRefreshAction(config.getDefaultRefreshAction());
            message.setRefreshArea(config.getDefaultRefreshArea());
            
            // 创建内容项
            List<YaliangRefreshMessage.ContentItem> contentList = new ArrayList<>();
            YaliangRefreshMessage.ContentItem contentItem = new YaliangRefreshMessage.ContentItem();
            contentItem.setDataType(config.getDataFormat().getImageDataType());
            contentItem.setDataRef(templateBase64 != null ? templateBase64 : "");
            contentItem.setLayerEnd(config.getDataFormat().isLayerEnd());
            contentList.add(contentItem);
            
            message.setContent(contentList);
            
            log.info("雅量刷新消息创建完成: queueId={}, deviceCode={}, deviceMac={}, templateBase64Size={}KB", 
                    message.getQueueId(), request.getDeviceCode(), request.getDeviceMac(),
                    templateBase64 != null ? templateBase64.length() / 1024 : 0);
            
            return message;
            
        } catch (Exception e) {
            log.error("创建雅量刷新消息失败: deviceCode={}, error={}", 
                    request.getDeviceCode(), e.getMessage(), e);
            throw new YaliangException(YaliangException.ErrorCodes.MESSAGE_CREATION_ERROR, 
                "创建刷新消息失败: " + e.getMessage(), 
                request.getDeviceCode(), request.getDeviceMac(), e);
        }
    }
    
    /**
     * 获取MQTT主题
     * 格式: yl-esl/XD010012/refresh/queue
     * 注意：这里使用固定的XD010012作为主题中间部分，而不是deviceCode
     */
    public String getMqttTopic(String deviceCode) {
        return config.getMqttTopicPrefix() + "/XD010012/refresh/queue";
    }
    
    /**
     * 生成队列ID（带异常处理）
     */
    public int generateQueueId() {
        try {
            int queueId = (int) (System.currentTimeMillis() % 100000);
            
            // 验证队列ID范围
            if (config.getValidation().getMinQueueId() > 0 && queueId < config.getValidation().getMinQueueId()) {
                queueId = config.getValidation().getMinQueueId();
            }
            if (config.getValidation().getMaxQueueId() > 0 && queueId > config.getValidation().getMaxQueueId()) {
                queueId = config.getValidation().getMaxQueueId();
            }
            
            return queueId;
        } catch (Exception e) {
            throw new YaliangException(YaliangException.ErrorCodes.QUEUE_ID_GENERATION_ERROR, 
                "生成队列ID失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 验证消息内容
     */
    public boolean validateMessage(Map<String, Object> message) {
        if (message == null) {
            return false;
        }
        
        // 检查必要字段
        String[] requiredFields = {"queueId", "deviceType", "deviceCode", "deviceMac", "content"};
        for (String field : requiredFields) {
            if (!message.containsKey(field) || message.get(field) == null) {
                log.warn("消息缺少必要字段: {}", field);
                return false;
            }
        }
        
        return true;
    }
}