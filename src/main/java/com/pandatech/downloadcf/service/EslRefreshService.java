package com.pandatech.downloadcf.service;

import com.pandatech.downloadcf.dto.RefreshDto;
import com.pandatech.downloadcf.dto.MqttMessageDto;

/**
 * 价签刷新服务接口
 */
public interface EslRefreshService {
    
    /**
     * 刷新价签 - 根据ESL ID构造完整的MQTT消息
     * @param refreshDto 刷新请求
     * @return MQTT消息
     */
    MqttMessageDto buildRefreshMessage(RefreshDto refreshDto);
    
    /**
     * 发送价签刷新消息到RabbitMQ
     * @param refreshDto 刷新请求
     */
    void sendRefreshMessage(RefreshDto refreshDto);
}