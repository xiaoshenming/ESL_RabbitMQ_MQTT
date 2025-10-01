package com.pandatech.downloadcf.brands.yaliang.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.brands.yaliang.adapter.YaliangBrandAdapter;
import com.pandatech.downloadcf.brands.yaliang.config.YaliangBrandConfig;
import com.pandatech.downloadcf.brands.yaliang.dto.YaliangRefreshMessage;
import com.pandatech.downloadcf.brands.yaliang.dto.YaliangRefreshRequest;
import com.pandatech.downloadcf.brands.yaliang.exception.YaliangException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 雅量电子价签服务
 * 专门处理雅量品牌的价签刷新业务逻辑
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class YaliangEslService {
    
    private final YaliangBrandAdapter yaliangBrandAdapter;
    private final YaliangBrandConfig yaliangBrandConfig;
    @Qualifier("mqttOutboundChannel")
    private final MessageChannel mqttOutboundChannel;
    private final ObjectMapper objectMapper;
    
    // 线程池用于批量处理
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    
    /**
     * 刷新单个雅量价签
     *
     * @param request 刷新请求
     * @return 刷新结果
     */
    public Map<String, Object> refreshEsl(YaliangRefreshRequest request) {
        log.info("开始处理雅量价签刷新: deviceCode={}, deviceMac={}", 
                request.getDeviceCode(), request.getDeviceMac());
        
        try {
            // 1. 验证请求参数
            yaliangBrandAdapter.validateRefreshRequest(request);
            
            // 2. 创建刷新消息
            YaliangRefreshMessage message = yaliangBrandAdapter.createRefreshMessage(request);
            
            // 3. 获取MQTT主题
            String mqttTopic = yaliangBrandAdapter.getMqttTopic(request.getDeviceCode());
            
            // 4. 发送MQTT消息
            sendMqttMessage(mqttTopic, message);
            
            // 5. 构建返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("queueId", message.getQueueId());
            result.put("mqttTopic", mqttTopic);
            result.put("deviceCode", request.getDeviceCode());
            result.put("deviceMac", request.getDeviceMac());
            result.put("timestamp", System.currentTimeMillis());
            
            log.info("雅量价签刷新完成: deviceCode={}, queueId={}, topic={}", 
                    request.getDeviceCode(), message.getQueueId(), mqttTopic);
            
            return result;
            
        } catch (YaliangException e) {
            log.error("雅量价签刷新失败: deviceCode={}, errorCode={}, message={}", 
                    request.getDeviceCode(), e.getErrorCode(), e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("雅量价签刷新异常: deviceCode={}, error={}", 
                    request.getDeviceCode(), e.getMessage(), e);
            throw new YaliangException(YaliangException.ErrorCodes.VALIDATION_ERROR, 
                "价签刷新失败: " + e.getMessage(), 
                request.getDeviceCode(), request.getDeviceMac(), e);
        }
    }
    
    /**
     * 批量刷新雅量价签
     *
     * @param requests 刷新请求列表
     * @return 刷新结果列表
     */
    public List<Map<String, Object>> batchRefreshEsl(List<YaliangRefreshRequest> requests) {
        log.info("开始批量处理雅量价签刷新: count={}", requests.size());
        
        if (requests == null || requests.isEmpty()) {
            throw new YaliangException(YaliangException.ErrorCodes.VALIDATION_ERROR, "批量刷新请求列表不能为空");
        }
        
        List<CompletableFuture<Map<String, Object>>> futures = new ArrayList<>();
        
        // 并行处理每个请求
        for (YaliangRefreshRequest request : requests) {
            CompletableFuture<Map<String, Object>> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return refreshEsl(request);
                } catch (YaliangException e) {
                    log.error("批量刷新中单个请求失败: deviceCode={}, errorCode={}, message={}", 
                            request.getDeviceCode(), e.getErrorCode(), e.getMessage());
                    
                    Map<String, Object> errorResult = new HashMap<>();
                    errorResult.put("success", false);
                    errorResult.put("deviceCode", request.getDeviceCode());
                    errorResult.put("deviceMac", request.getDeviceMac());
                    errorResult.put("errorCode", e.getErrorCode());
                    errorResult.put("error", e.getMessage());
                    errorResult.put("timestamp", System.currentTimeMillis());
                    
                    return errorResult;
                } catch (Exception e) {
                    log.error("批量刷新中单个请求异常: deviceCode={}, error={}", 
                            request.getDeviceCode(), e.getMessage(), e);
                    
                    Map<String, Object> errorResult = new HashMap<>();
                    errorResult.put("success", false);
                    errorResult.put("deviceCode", request.getDeviceCode());
                    errorResult.put("deviceMac", request.getDeviceMac());
                    errorResult.put("errorCode", YaliangException.ErrorCodes.VALIDATION_ERROR);
                    errorResult.put("error", "处理异常: " + e.getMessage());
                    errorResult.put("timestamp", System.currentTimeMillis());
                    
                    return errorResult;
                }
            }, executorService);
            
            futures.add(future);
        }
        
        // 等待所有请求完成
        List<Map<String, Object>> results = new ArrayList<>();
        for (CompletableFuture<Map<String, Object>> future : futures) {
            try {
                results.add(future.get());
            } catch (Exception e) {
                log.error("获取批量刷新结果失败", e);
                Map<String, Object> errorResult = new HashMap<>();
                errorResult.put("success", false);
                errorResult.put("errorCode", YaliangException.ErrorCodes.VALIDATION_ERROR);
                errorResult.put("error", "获取结果失败: " + e.getMessage());
                errorResult.put("timestamp", System.currentTimeMillis());
                results.add(errorResult);
            }
        }
        
        int successCount = (int) results.stream().filter(r -> (Boolean) r.get("success")).count();
        log.info("批量雅量价签刷新完成: total={}, success={}, failed={}", 
                requests.size(), successCount, requests.size() - successCount);
        
        return results;
    }
    
    /**
     * 获取设备规格列表
     *
     * @return 设备规格信息
     */
    public Map<String, Object> getDeviceSpecs() {
        log.info("获取雅量设备规格列表");
        
        try {
            Map<String, Object> result = new HashMap<>();
            result.put("brandName", yaliangBrandConfig.getName());
            result.put("brandCode", yaliangBrandConfig.getCode());
            result.put("supportedSizes", yaliangBrandAdapter.getSupportedDeviceSizes());
            result.put("defaultSize", yaliangBrandConfig.getDeviceSpecs().getDefaultSpec());
            
            Map<String, Object> specsDetail = new HashMap<>();
            yaliangBrandConfig.getDeviceSpecs().getSpecs().forEach((size, spec) -> {
                Map<String, Object> specInfo = new HashMap<>();
                specInfo.put("width", spec.getWidth());
                specInfo.put("height", spec.getHeight());
                specInfo.put("rotation", spec.getRotation());
                specInfo.put("isNewVersion", spec.isNewVersion());
                specInfo.put("resolution", spec.getResolution());
                specsDetail.put(size, specInfo);
            });
            result.put("specifications", specsDetail);
            
            return result;
        } catch (Exception e) {
            log.error("获取设备规格列表失败: {}", e.getMessage(), e);
            throw new YaliangException(YaliangException.ErrorCodes.CONFIG_ERROR, 
                "获取设备规格列表失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 获取指定设备规格
     *
     * @param deviceSize 设备尺寸
     * @return 设备规格信息
     */
    public Map<String, Object> getDeviceSpec(String deviceSize) {
        log.info("获取雅量设备规格: deviceSize={}", deviceSize);
        
        try {
            YaliangBrandConfig.DeviceSpec spec = yaliangBrandAdapter.getDeviceSpecInfo(deviceSize);
            if (spec == null) {
                log.warn("未找到设备规格: deviceSize={}", deviceSize);
                throw new YaliangException(YaliangException.ErrorCodes.INVALID_DEVICE_SIZE, 
                    "未找到设备规格: " + deviceSize);
            }
            
            Map<String, Object> result = new HashMap<>();
            result.put("deviceSize", deviceSize);
            result.put("width", spec.getWidth());
            result.put("height", spec.getHeight());
            result.put("rotation", spec.getRotation());
            result.put("isNewVersion", spec.isNewVersion());
            result.put("resolution", spec.getResolution());
            result.put("brandName", yaliangBrandConfig.getName());
            result.put("brandCode", yaliangBrandConfig.getCode());
            
            return result;
        } catch (YaliangException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取设备规格失败: deviceSize={}, error={}", deviceSize, e.getMessage(), e);
            throw new YaliangException(YaliangException.ErrorCodes.CONFIG_ERROR, 
                "获取设备规格失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 发送MQTT消息
     *
     * @param topic   MQTT主题
     * @param message 消息内容
     */
    private void sendMqttMessage(String topic, YaliangRefreshMessage message) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            
            org.springframework.messaging.Message<String> mqttMessage = MessageBuilder
                    .withPayload(jsonMessage)
                    .setHeader("mqtt_topic", topic)
                    .setHeader("mqtt_qos", 1)
                    .setHeader("mqtt_retained", false)
                    .build();
            
            boolean sent = mqttOutboundChannel.send(mqttMessage);
            if (sent) {
                log.info("MQTT消息发送成功: topic={}, queueId={}", topic, message.getQueueId());
            } else {
                log.error("MQTT消息发送失败: topic={}, queueId={}", topic, message.getQueueId());
                throw new YaliangException(YaliangException.ErrorCodes.MQTT_SEND_ERROR, 
                    "MQTT消息发送失败", message.getDeviceCode(), message.getDeviceMac());
            }
            
        } catch (YaliangException e) {
            throw e;
        } catch (Exception e) {
            log.error("发送MQTT消息异常: topic={}, error={}", topic, e.getMessage(), e);
            throw new YaliangException(YaliangException.ErrorCodes.MQTT_SEND_ERROR, 
                "发送MQTT消息异常: " + e.getMessage(), 
                message.getDeviceCode(), message.getDeviceMac(), e);
        }
    }
    
    /**
     * 获取雅量品牌配置信息
     *
     * @return 配置信息
     */
    public Map<String, Object> getBrandConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("brandName", yaliangBrandConfig.getName());
        config.put("brandCode", yaliangBrandConfig.getCode());
        config.put("enabled", yaliangBrandConfig.isEnabled());
        config.put("executorType", yaliangBrandConfig.getExecutorType());
        config.put("mqttTopicPrefix", yaliangBrandConfig.getMqttTopicPrefix());
        config.put("defaultDeviceType", yaliangBrandConfig.getDefaultDeviceType());
        config.put("defaultRefreshAction", yaliangBrandConfig.getDefaultRefreshAction());
        config.put("defaultRefreshArea", yaliangBrandConfig.getDefaultRefreshArea());
        
        return config;
    }
    
    /**
     * 验证设备编码格式
     *
     * @param deviceCode 设备编码
     * @return 是否有效
     */
    public boolean validateDeviceCode(String deviceCode) {
        return yaliangBrandConfig.getValidation().isRequireDeviceCode() && 
               deviceCode != null && !deviceCode.trim().isEmpty();
    }
    
    /**
     * 验证设备MAC地址格式
     *
     * @param deviceMac 设备MAC地址
     * @return 是否有效
     */
    public boolean validateDeviceMac(String deviceMac) {
        return yaliangBrandConfig.getValidation().isRequireDeviceMac() && 
               deviceMac != null && !deviceMac.trim().isEmpty();
    }
}