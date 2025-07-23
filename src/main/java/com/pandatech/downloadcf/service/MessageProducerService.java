package com.pandatech.downloadcf.service;

import com.pandatech.downloadcf.dto.BrandOutputData;
import com.pandatech.downloadcf.dto.MessageExecutionData;
import com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs;
import com.pandatech.downloadcf.executor.MessageExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
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
    private final DataService dataService;
    
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
        Map<String, Object> payload = new LinkedHashMap<>(); // 使用LinkedHashMap保持字段顺序
        
        // 严格按照标准格式的字段顺序：command, data, id, timestamp, shop
        payload.put("command", "wtag");
        payload.put("data", buildDataArray(outputData));
        payload.put("id", outputData.getEslId());
        payload.put("timestamp", System.currentTimeMillis() / 1000.0); // 秒级时间戳，保持小数格式
        payload.put("shop", outputData.getStoreCode());
        
        return payload;
    }
    
    /**
     * 构建data数组 - 严格按照PANDA标准格式
     */
    private List<Map<String, Object>> buildDataArray(BrandOutputData outputData) {
        Map<String, Object> dataItem = new LinkedHashMap<>(); // 使用LinkedHashMap保持字段顺序
        
        // 严格按照标准格式的字段顺序：tag, tmpl, model, checksum, forcefrash, value, taskid, token
        dataItem.put("tag", convertEslIdToDecimal(outputData.getActualEslId())); // 使用真正的ESL设备ID进行转换
        dataItem.put("tmpl", getTemplateName(outputData.getTemplateId())); // 模板名称（从数据库获取）
        dataItem.put("model", "6"); // 按照标准格式，固定为"6"
        dataItem.put("checksum", outputData.getChecksum() != null ? outputData.getChecksum() : "");
        dataItem.put("forcefrash", 1); // 保持原有拼写（按照标准格式）
        dataItem.put("value", outputData.getDataMap()); // 直接使用已经格式化的数据映射
        dataItem.put("taskid", generateTaskId()); // 生成任务ID
        dataItem.put("token", generateToken()); // 生成令牌
        
        return List.of(dataItem);
    }
    
    /**
     * 将十六进制ESL ID转换为十进制 - 按照标准格式要求
     * 例如：06000000195A → 6597069773146
     */
    private long convertEslIdToDecimal(String eslId) {
        if (eslId == null || eslId.isEmpty()) {
            log.warn("ESL ID为空，使用默认值");
            return 0L;
        }
        
        try {
            // 清理ESL ID，移除所有非十六进制字符
            String cleanEslId = eslId.trim().toUpperCase();
            
            // 移除0x前缀（如果存在）
            if (cleanEslId.startsWith("0X")) {
                cleanEslId = cleanEslId.substring(2);
            }
            
            // 移除所有非十六进制字符（保留0-9, A-F）
            cleanEslId = cleanEslId.replaceAll("[^0-9A-F]", "");
            
            if (cleanEslId.isEmpty()) {
                log.warn("ESL ID格式错误，清理后为空: {}", eslId);
                return 0L;
            }
            
            // 如果是纯数字且长度较短，可能已经是十进制
            if (cleanEslId.matches("\\d+") && cleanEslId.length() <= 10) {
                long decimalValue = Long.parseLong(cleanEslId);
                log.debug("ESL ID已是十进制格式: {} → {}", eslId, decimalValue);
                return decimalValue;
            }
            
            // 转换十六进制为十进制
            long decimalValue = Long.parseUnsignedLong(cleanEslId, 16);
            log.debug("ESL ID十六进制转十进制: {} → {} → {}", eslId, cleanEslId, decimalValue);
            return decimalValue;
            
        } catch (NumberFormatException e) {
            log.error("ESL ID转换失败: {}", eslId, e);
            return 0L;
        }
    }
    
    /**
     * 获取模板名称 - 从数据库获取模板的name字段
     * 根据err.md的要求，tmpl字段应该是模板的name（如"2"），而不是"panda"
     */
    private String getTemplateName(String templateId) {
        if (templateId == null || templateId.isEmpty()) {
            return "2"; // 默认模板名称，按照标准格式示例
        }
        
        try {
            // 通过DataService获取模板信息
            PrintTemplateDesignWithBLOBs template = dataService.getTemplateById(templateId);
            if (template != null && template.getName() != null) {
                return template.getName(); // 返回模板的name字段
            }
            
            // 如果没有找到模板，返回默认值
            log.warn("未找到模板信息: {}", templateId);
            return "2"; // 默认模板名称
            
        } catch (Exception e) {
            log.error("获取模板名称失败: {}", templateId, e);
            return "2"; // 默认模板名称
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