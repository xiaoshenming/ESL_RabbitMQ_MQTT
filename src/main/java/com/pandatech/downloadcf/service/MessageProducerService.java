package com.pandatech.downloadcf.service;

import com.pandatech.downloadcf.dto.BrandOutputData;
import com.pandatech.downloadcf.dto.MessageExecutionData;
import com.pandatech.downloadcf.executor.MessageExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息生产者服务 - 负责将数据发送到消息队列和执行器
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducerService {
    
    private final List<MessageExecutor> messageExecutors;
    
    /**
     * 发送品牌输出数据到对应的执行器
     */
    public boolean sendMessage(BrandOutputData outputData) {
        log.info("开始发送消息: brandCode={}, eslId={}", 
                outputData.getBrandCode(), outputData.getEslId());
        
        // 根据品牌确定执行器类型
        String executorType = getExecutorTypeByBrand(outputData.getBrandCode());
        
        // 构建消息执行数据
        MessageExecutionData executionData = buildExecutionData(outputData, executorType);
        
        // 查找对应的执行器
        MessageExecutor executor = findExecutor(executorType);
        if (executor == null) {
            log.error("未找到执行器: type={}", executorType);
            return false;
        }
        
        // 检查执行器是否可用
        if (!executor.isAvailable()) {
            log.error("执行器不可用: type={}", executorType);
            return false;
        }
        
        // 执行消息发送
        boolean success = executor.execute(executionData);
        
        if (success) {
            log.info("消息发送成功: brandCode={}, eslId={}, executor={}", 
                    outputData.getBrandCode(), outputData.getEslId(), executorType);
        } else {
            log.error("消息发送失败: brandCode={}, eslId={}, executor={}", 
                    outputData.getBrandCode(), outputData.getEslId(), executorType);
        }
        
        return success;
    }
    
    /**
     * 根据品牌确定执行器类型
     */
    private String getExecutorTypeByBrand(String brandCode) {
        // 根据品牌配置确定执行器类型
        switch (brandCode.toUpperCase()) {
            case "PANDA":
                return "mqtt";
            case "YALIANG":
                return "http"; // 预留给雅量品牌
            default:
                return "mqtt"; // 默认使用MQTT
        }
    }
    
    /**
     * 构建消息执行数据
     */
    private MessageExecutionData buildExecutionData(BrandOutputData outputData, String executorType) {
        MessageExecutionData executionData = new MessageExecutionData();
        executionData.setExecutorType(executorType);
        executionData.setBrandCode(outputData.getBrandCode());
        executionData.setEslId(outputData.getEslId());
        executionData.setStoreCode(outputData.getStoreCode());
        
        // 根据执行器类型构建不同的载荷和目标地址
        if ("mqtt".equals(executorType)) {
            executionData.setDestination(buildMqttTopic(outputData.getStoreCode()));
            executionData.setPayload(buildMqttPayload(outputData));
        } else if ("http".equals(executorType)) {
            executionData.setDestination(buildHttpUrl(outputData.getStoreCode()));
            executionData.setPayload(buildHttpPayload(outputData));
        }
        
        // 设置消息头
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Brand-Code", outputData.getBrandCode());
        headers.put("Store-Code", outputData.getStoreCode());
        executionData.setHeaders(headers);
        
        return executionData;
    }
    
    /**
     * 构建MQTT主题 - 按照标准格式
     */
    private String buildMqttTopic(String storeCode) {
        return String.format("esl/server/data/%s", storeCode);
    }
    
    /**
     * 构建MQTT载荷 - 严格按照PANDA标准格式
     */
    private Object buildMqttPayload(BrandOutputData outputData) {
        Map<String, Object> payload = new HashMap<>();
        
        // 按照标准格式的字段顺序
        payload.put("command", "wtag");
        payload.put("data", buildDataArray(outputData));
        payload.put("id", outputData.getEslId());
        payload.put("timestamp", (double) (System.currentTimeMillis() / 1000.0)); // 科学计数法格式
        payload.put("shop", outputData.getStoreCode());
        
        return payload;
    }
    
    /**
     * 构建data数组 - 严格按照PANDA标准格式
     */
    private List<Map<String, Object>> buildDataArray(BrandOutputData outputData) {
        Map<String, Object> dataItem = new HashMap<>();
        
        // 严格按照标准格式的字段顺序：tag, tmpl, model, checksum, forcefrash, value, taskid, token
        dataItem.put("tag", Long.parseLong(outputData.getEslId())); // 转换为数字格式
        dataItem.put("tmpl", getTemplateNumber(outputData.getTemplateId())); // 模板编号（字符串格式，如"2"）
        dataItem.put("model", "6"); // 按照标准格式，应该是"6"而不是"213"
        dataItem.put("checksum", outputData.getChecksum() != null ? outputData.getChecksum() : "");
        dataItem.put("forcefrash", 1); // 保持原有拼写（按照标准格式）
        dataItem.put("value", outputData.getDataMap()); // 直接使用已经格式化的数据映射
        dataItem.put("taskid", generateTaskId()); // 生成任务ID
        dataItem.put("token", generateToken()); // 生成令牌
        
        return List.of(dataItem);
    }
    
    /**
     * 获取模板编号 - 将模板ID转换为简单的编号格式
     */
    private String getTemplateNumber(String templateId) {
        if (templateId == null || templateId.isEmpty()) {
            return "1"; // 默认模板编号
        }
        
        // 简化模板ID为编号（实际项目中可能需要查询数据库获取对应的编号）
        try {
            // 如果是长ID，取后几位作为编号
            if (templateId.length() > 10) {
                return String.valueOf(Math.abs(templateId.hashCode()) % 100 + 1);
            } else {
                return templateId;
            }
        } catch (Exception e) {
            log.warn("模板ID格式错误: {}", templateId);
            return "1";
        }
    }
    
    /**
     * 生成任务ID
     */
    private int generateTaskId() {
        return (int) (System.currentTimeMillis() % 100000);
    }
    
    /**
     * 生成令牌
     */
    private int generateToken() {
        return (int) (Math.random() * 1000000);
    }
    
    /**
     * 构建标准的value映射格式
     */
    private Map<String, Object> buildStandardValueMapping(Map<String, Object> dataMap) {
        Map<String, Object> standardValue = new HashMap<>();
        
        if (dataMap != null) {
            // 确保所有值都是字符串格式
            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                Object value = entry.getValue();
                if (value != null) {
                    standardValue.put(entry.getKey(), value.toString());
                } else {
                    standardValue.put(entry.getKey(), "");
                }
            }
        }
        
        return standardValue;
    }
    
    /**
     * 构建HTTP URL
     */
    private String buildHttpUrl(String storeCode) {
        return String.format("http://api.example.com/esl/%s/refresh", storeCode);
    }
    
    /**
     * 构建HTTP载荷
     */
    private Object buildHttpPayload(BrandOutputData outputData) {
        // HTTP载荷格式（预留给其他品牌）
        Map<String, Object> payload = new HashMap<>();
        payload.put("eslId", outputData.getEslId());
        payload.put("storeCode", outputData.getStoreCode());
        payload.put("templateContent", outputData.getTemplateContent());
        payload.put("dataMap", outputData.getDataMap());
        payload.put("checksum", outputData.getChecksum());
        return payload;
    }
    
    /**
     * 查找执行器
     */
    private MessageExecutor findExecutor(String executorType) {
        return messageExecutors.stream()
                .filter(executor -> executorType.equals(executor.getExecutorType()))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * 获取所有可用的执行器
     */
    public List<MessageExecutor> getAvailableExecutors() {
        return messageExecutors.stream()
                .filter(MessageExecutor::isAvailable)
                .toList();
    }
}