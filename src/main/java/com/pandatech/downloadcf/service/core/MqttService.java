package com.pandatech.downloadcf.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 原始MQTT服务 - 已废弃
 * 
 * 注意：此类已被重构为多个专门的服务组件：
 * - TemplateConverter: 处理模板格式转换
 * - MqttMessageBuilder: 处理MQTT消息构建
 * - TemplateOptimizer: 处理模板优化和去重
 * - MqttServiceRefactored: 统一的MQTT服务接口
 * 
 * 请使用 MqttServiceRefactored 替代此类
 * 
 * @deprecated 使用 {@link MqttServiceRefactored} 替代
 */
@Slf4j
@Service
@Deprecated
public class MqttService {

    public MqttService() {
        log.warn("MqttService已被废弃，请使用MqttServiceRefactored替代");
    }
}