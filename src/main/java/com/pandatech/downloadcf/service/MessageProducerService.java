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
        dataItem.put("tmpl", getTemplateName(outputData.getEslId())); // 根据ESL ID获取模板名称
        dataItem.put("model", "6"); // 按照标准格式，固定为"6"
        dataItem.put("checksum", outputData.getChecksum() != null ? outputData.getChecksum() : "");
        dataItem.put("forcefrash", 1); // 保持原有拼写（按照标准格式）
        dataItem.put("value", outputData.getDataMap()); // 直接使用已经格式化的数据映射
        dataItem.put("taskid", generateTaskId()); // 生成任务ID
        dataItem.put("token", generateToken()); // 生成令牌
        
        return List.of(dataItem);
    }
    
    /**
     * 将十六进制ESL ID转换为十进制 - 严格按照标准格式要求
     * 例如：06000000195A → 6597069773146
     * 参考原始代码的convertHexEslIdToLong方法实现
     */
    private long convertEslIdToDecimal(String eslId) {
        if (eslId == null || eslId.trim().isEmpty()) {
            log.warn("ESL ID为空，使用默认值0");
            return 0L;
        }
        
        try {
            // 移除可能的前缀和空格
            String cleanHexId = eslId.trim().toUpperCase();
            
            // 如果包含0x前缀，移除它
            if (cleanHexId.startsWith("0X")) {
                cleanHexId = cleanHexId.substring(2);
            }
            
            // 将十六进制字符串转换为Long
            Long result = Long.parseUnsignedLong(cleanHexId, 16);
            log.debug("ESL ID转换成功: {} -> {}", eslId, result);
            return result;
            
        } catch (NumberFormatException e) {
            log.error("ESL ID转换失败，无效的十六进制格式: {}", eslId, e);
            // 如果转换失败，尝试提取数字部分
            try {
                String numericPart = eslId.replaceAll("[^0-9A-Fa-f]", "");
                if (!numericPart.isEmpty()) {
                    Long result = Long.parseUnsignedLong(numericPart, 16);
                    log.warn("使用提取的数字部分进行转换: {} -> {}", numericPart, result);
                    return result;
                }
            } catch (Exception ex) {
                log.error("提取数字部分转换也失败", ex);
            }
            
            // 最后的备用方案：返回hashCode的绝对值
            long fallback = Math.abs(eslId.hashCode());
            log.warn("使用hashCode作为备用方案: {} -> {}", eslId, fallback);
            return fallback;
        }
    }
    
    /**
     * 获取模板名称 - 严格按照标准格式要求
     * 根据ESL ID获取对应的模板名称，参考原始代码实现
     */
    private String getTemplateName(String eslId) {
        try {
            // 通过ESL ID获取模板信息
            PrintTemplateDesignWithBLOBs template = dataService.getTemplateByEslId(eslId);
            if (template != null && template.getName() != null && !template.getName().trim().isEmpty()) {
                String templateName = template.getName().trim();
                log.debug("获取到模板名称: ESL ID={}, 模板名称={}", eslId, templateName);
                return templateName;
            }
            
            log.warn("未找到ESL ID对应的模板或模板名称为空: {}, 使用默认模板名称: 2", eslId);
            return "2"; // 默认模板名称
            
        } catch (Exception e) {
            log.error("获取模板名称时发生异常: ESL ID={}", eslId, e);
            return "2"; // 异常时返回默认模板名称
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