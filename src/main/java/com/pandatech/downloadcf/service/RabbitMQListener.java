package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.config.RabbitMQConfig;
import com.pandatech.downloadcf.event.MessageProcessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

/**
 * RabbitMQ消息监听器
 * 负责监听队列消息并转发到MQTT
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMQListener {

    @Qualifier("mqttOutboundChannel")
    private final MessageChannel mqttOutboundChannel;
    private final ObjectMapper objectMapper;
    private final SimpleMqttService mqttService;
    private final EslRefreshService eslRefreshService;
    private final ApplicationEventPublisher eventPublisher;
    private final LoadInfoService loadInfoService;

    // 负载控制参数 - 已移至LoadInfoService
    // private final AtomicInteger currentLoad = new AtomicInteger(0);
    // private final AtomicLong lastProcessTime = new AtomicLong(System.currentTimeMillis());
    // private static final int MAX_CONCURRENT_MESSAGES = 10; // 最大并发处理数
    // private static final long MIN_INTERVAL_MS = 100; // 最小处理间隔（毫秒）

    /**
     * 监听模板消息队列
     */
    @RabbitListener(queues = RabbitMQConfig.TEMPLATE_QUEUE)
    public void receiveTemplateMessage(String message) {
        if (!loadInfoService.canProcessMessage()) {
            log.warn("系统负载过高，延迟处理模板消息");
            try {
                Thread.sleep(loadInfoService.getMinIntervalMs());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        loadInfoService.incrementLoad();
        try {
            log.info("从RabbitMQ接收到模板消息: {}", message);
            
            JsonNode messageNode = objectMapper.readTree(message);
            String messageType = messageNode.path("messageType").asText();
            
            if ("template".equals(messageType)) {
                processNewTemplateMessage(messageNode);
                eventPublisher.publishEvent(new MessageProcessEvent(this, "template", true));
            } else if ("batchRefresh".equals(messageType)) {
                processBatchRefreshMessage(messageNode);
                eventPublisher.publishEvent(new MessageProcessEvent(this, "batchRefresh", true));
            } else {
                // 兼容旧格式
                processLegacyTemplateMessage(message);
                eventPublisher.publishEvent(new MessageProcessEvent(this, "template", true));
            }
            
        } catch (Exception e) {
            log.error("处理模板消息失败: {}", e.getMessage(), e);
            eventPublisher.publishEvent(new MessageProcessEvent(this, "template", false, e.getMessage()));
        } finally {
            loadInfoService.decrementLoad();
        }
    }

    /**
     * 监听刷新消息队列
     */
    @RabbitListener(queues = RabbitMQConfig.REFRESH_QUEUE)
    public void receiveRefreshMessage(String message) {
        if (!loadInfoService.canProcessMessage()) {
            log.warn("系统负载过高，延迟处理刷新消息");
            try {
                Thread.sleep(loadInfoService.getMinIntervalMs());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        loadInfoService.incrementLoad();
        try {
            log.info("从RabbitMQ接收到刷新消息: {}", message);
            
            JsonNode messageNode = objectMapper.readTree(message);
            String messageType = messageNode.path("messageType").asText();
            
            if ("refresh".equals(messageType)) {
                processNewRefreshMessage(messageNode);
                eventPublisher.publishEvent(new MessageProcessEvent(this, "refresh", true));
            } else if ("batchRefresh".equals(messageType)) {
                processBatchRefreshMessage(messageNode);
                eventPublisher.publishEvent(new MessageProcessEvent(this, "batchRefresh", true));
            } else {
                // 兼容旧格式
                processLegacyRefreshMessage(message);
                eventPublisher.publishEvent(new MessageProcessEvent(this, "refresh", true));
            }
            
        } catch (Exception e) {
            log.error("处理刷新消息失败: {}", e.getMessage(), e);
            eventPublisher.publishEvent(new MessageProcessEvent(this, "refresh", false, e.getMessage()));
        } finally {
            loadInfoService.decrementLoad();
        }
    }
    
    /**
     * 处理新格式的模板消息
     */
    private void processNewTemplateMessage(JsonNode messageNode) {
        try {
            String storeCode = messageNode.path("storeCode").asText();
            String mqttTopic = messageNode.path("mqttTopic").asText();
            JsonNode mqttPayload = messageNode.path("mqttPayload");
            int priority = messageNode.path("priority").asInt(0);
            
            log.info("处理新格式模板消息: storeCode={}, topic={}, priority={}", storeCode, mqttTopic, priority);
            
            // 发送MQTT消息
            boolean success = mqttService.sendMqttMessage(mqttTopic, mqttPayload.toString());
            
            if (success) {
                log.info("新格式模板消息MQTT发送成功: topic={}", mqttTopic);
            } else {
                log.error("新格式模板消息MQTT发送失败: topic={}", mqttTopic);
            }
            
        } catch (Exception e) {
            log.error("处理新格式模板消息失败: {}", e.getMessage(), e);
        }
    }
    
    /**
     * 处理新格式的刷新消息
     * RabbitMQListener的职责是接收队列消息并发送MQTT消息
     */
    private void processNewRefreshMessage(JsonNode messageNode) {
        try {
            String brandCode = messageNode.path("brandCode").asText();
            String eslId = messageNode.path("eslId").asText();
            String storeCode = messageNode.path("storeCode").asText();
            String mqttTopic = messageNode.path("mqttTopic").asText();
            JsonNode mqttPayload = messageNode.path("mqttPayload");
            int priority = messageNode.path("priority").asInt(0);
            
            log.info("处理新格式刷新消息: eslId={}, brandCode={}, storeCode={}, topic={}, priority={}", 
                    eslId, brandCode, storeCode, mqttTopic, priority);
            
            // 发送MQTT消息
            boolean success = mqttService.sendMqttMessage(mqttTopic, mqttPayload.toString());
            
            if (success) {
                log.info("新格式刷新消息MQTT发送成功: eslId={}, topic={}", eslId, mqttTopic);
            } else {
                log.error("新格式刷新消息MQTT发送失败: eslId={}, topic={}", eslId, mqttTopic);
            }
            
        } catch (Exception e) {
            log.error("处理新格式刷新消息失败: {}", e.getMessage(), e);
        }
    }
    
    /**
     * 处理批量刷新消息
     */
    private void processBatchRefreshMessage(JsonNode messageNode) {
        try {
            String storeCode = messageNode.get("storeCode").asText();
            JsonNode refreshRequests = messageNode.get("refreshRequests");
            int priority = messageNode.has("priority") ? messageNode.get("priority").asInt() : 5;
            
            log.info("开始处理批量刷新消息，门店: {}, 数量: {}, 优先级: {}", 
                    storeCode, refreshRequests.size(), priority);
            
            // 逐个处理刷新请求
            for (JsonNode request : refreshRequests) {
                try {
                    // 负载控制
                    if (!loadInfoService.canProcessMessage()) {
                        log.warn("系统负载过高，延迟处理批量刷新消息");
                        Thread.sleep(loadInfoService.getMinIntervalMs());
                    }
                    
                    // 构建单个刷新请求
                    String eslId = request.get("eslId").asText();
                    String brandCode = request.get("brandCode").asText();
                    
                    // 直接刷新价签，避免重复进入队列造成循环
                    eslRefreshService.refreshEslDirect(eslId, brandCode, storeCode);
                    
                    // 处理间隔控制
                    Thread.sleep(loadInfoService.getMinIntervalMs());
                    
                } catch (Exception e) {
                    log.error("处理批量刷新中的单个请求失败，ESL ID: {}, 错误: {}", 
                            request.has("eslId") ? request.get("eslId").asText() : "unknown", 
                            e.getMessage());
                }
            }
            
            log.info("批量刷新消息处理完成，门店: {}", storeCode);
            
        } catch (Exception e) {
            log.error("处理批量刷新消息失败: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 处理旧格式的模板消息（兼容性）
     */
    private void processLegacyTemplateMessage(String message) {
        try {
            log.info("处理旧格式模板消息: {}", message);
            
            JsonNode messageNode = objectMapper.readTree(message);
            String storeCode = messageNode.path("shop").asText();
            String topic = "esl/server/data/" + storeCode;
            
            // 发送MQTT消息
            boolean success = mqttService.sendMqttMessage(topic, message);
            
            if (success) {
                log.info("旧格式模板消息MQTT发送成功: topic={}", topic);
            } else {
                log.error("旧格式模板消息MQTT发送失败: topic={}", topic);
            }
            
        } catch (Exception e) {
            log.error("处理旧格式模板消息失败: {}", e.getMessage(), e);
        }
    }
    
    /**
     * 处理旧格式的刷新消息（兼容性）
     */
    private void processLegacyRefreshMessage(String message) {
        try {
            log.info("处理旧格式刷新消息: {}", message);
            
            JsonNode messageNode = objectMapper.readTree(message);
            String storeCode = messageNode.path("shop").asText();
            String topic = "esl/server/data/" + storeCode;
            
            // 发送MQTT消息
            boolean success = mqttService.sendMqttMessage(topic, message);
            
            if (success) {
                log.info("旧格式刷新消息MQTT发送成功: topic={}", topic);
            } else {
                log.error("旧格式刷新消息MQTT发送失败: topic={}", topic);
            }
            
        } catch (Exception e) {
            log.error("处理旧格式刷新消息失败: {}", e.getMessage(), e);
        }
    }
    
    /**
     * 获取系统负载信息
     */
    public String getLoadInfo() {
        return loadInfoService.getLoadInfo();
    }
}