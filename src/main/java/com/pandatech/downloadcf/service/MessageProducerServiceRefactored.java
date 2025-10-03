package com.pandatech.downloadcf.service;

import com.pandatech.downloadcf.dto.BrandOutputData;
import com.pandatech.downloadcf.dto.MessageExecutionData;
import com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs;
import com.pandatech.downloadcf.util.BrandCodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 重构后的消息生产者服务
 * 整合各个专门的服务组件，提供统一的消息发送接口
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducerServiceRefactored {
    
    private final MessageExecutorService messageExecutorService;
    private final BrandPayloadBuilderService brandPayloadBuilderService;
    private final MessageDestinationService messageDestinationService;
    
    /**
     * 发送品牌输出数据到对应的执行器
     */
    public boolean sendMessage(BrandOutputData outputData) {
        log.info("开始发送消息: brandCode={}, eslId={}", 
                outputData.getBrandCode(), outputData.getEslId());
        
        try {
            // 根据品牌确定执行器类型
            String executorType = getExecutorTypeByBrand(outputData.getBrandCode());
            
            // 构建消息执行数据
            MessageExecutionData executionData = buildExecutionData(outputData, executorType);
            
            // 执行消息发送
            boolean success = messageExecutorService.executeMessage(executionData);
            
            if (success) {
                log.info("消息发送成功: brandCode={}, eslId={}, executor={}", 
                        outputData.getBrandCode(), outputData.getEslId(), executorType);
            } else {
                log.error("消息发送失败: brandCode={}, eslId={}, executor={}", 
                        outputData.getBrandCode(), outputData.getEslId(), executorType);
            }
            
            return success;
            
        } catch (Exception e) {
            log.error("消息发送异常: brandCode={}, eslId={}", 
                    outputData.getBrandCode(), outputData.getEslId(), e);
            return false;
        }
    }
    
    /**
     * 发送消息（带模板数据，避免重复查询）
     */
    public boolean sendMessage(BrandOutputData outputData, PrintTemplateDesignWithBLOBs template) {
        log.info("开始发送消息（使用已有模板数据）: brandCode={}, eslId={}", 
                outputData.getBrandCode(), outputData.getEslId());
        
        try {
            // 根据品牌确定执行器类型
            String executorType = getExecutorTypeByBrand(outputData.getBrandCode());
            
            // 构建消息执行数据（使用已有模板）
            MessageExecutionData executionData = buildExecutionData(outputData, executorType, template);
            
            // 执行消息发送
            boolean success = messageExecutorService.executeMessage(executionData);
            
            if (success) {
                log.info("消息发送成功（使用已有模板）: brandCode={}, eslId={}, executor={}", 
                        outputData.getBrandCode(), outputData.getEslId(), executorType);
            } else {
                log.error("消息发送失败（使用已有模板）: brandCode={}, eslId={}, executor={}", 
                        outputData.getBrandCode(), outputData.getEslId(), executorType);
            }
            
            return success;
            
        } catch (Exception e) {
            log.error("消息发送异常（使用已有模板）: brandCode={}, eslId={}", 
                    outputData.getBrandCode(), outputData.getEslId(), e);
            return false;
        }
    }
    
    /**
     * 根据品牌确定执行器类型
     */
    private String getExecutorTypeByBrand(String brandCode) {
        // 使用BrandCodeUtil进行品牌代码兼容性处理
        String adapterBrandCode = BrandCodeUtil.toAdapterBrandCode(brandCode);
        
        // 根据品牌配置确定执行器类型
        switch (adapterBrandCode) {
            case "AES001":
                return "mqtt";
            case "YALIANG001":
                return "mqtt";
            case "攀攀": // 向后兼容
                return "mqtt";
            default:
                log.warn("未知品牌编码: {} (原始: {}), 使用默认执行器类型: mqtt", adapterBrandCode, brandCode);
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
            executionData.setDestination(messageDestinationService.buildMqttTopic(outputData));
            executionData.setPayload(brandPayloadBuilderService.buildMqttPayload(outputData));
        } else if ("http".equals(executorType)) {
            executionData.setDestination(messageDestinationService.buildHttpUrl(outputData));
            executionData.setPayload(brandPayloadBuilderService.buildMqttPayload(outputData)); // 可以扩展为HTTP载荷
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
     * 构建消息执行数据（使用已有模板）
     */
    private MessageExecutionData buildExecutionData(BrandOutputData outputData, String executorType, 
                                                   PrintTemplateDesignWithBLOBs template) {
        MessageExecutionData executionData = new MessageExecutionData();
        executionData.setExecutorType(executorType);
        executionData.setBrandCode(outputData.getBrandCode());
        executionData.setEslId(outputData.getEslId());
        executionData.setStoreCode(outputData.getStoreCode());
        
        // 根据执行器类型构建不同的载荷和目标地址
        if ("mqtt".equals(executorType)) {
            executionData.setDestination(messageDestinationService.buildMqttTopic(outputData));
            executionData.setPayload(brandPayloadBuilderService.buildMqttPayload(outputData, template));
        } else if ("http".equals(executorType)) {
            executionData.setDestination(messageDestinationService.buildHttpUrl(outputData));
            executionData.setPayload(brandPayloadBuilderService.buildMqttPayload(outputData, template));
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
     * 检查指定品牌的执行器是否可用
     */
    public boolean isExecutorAvailable(String brandCode) {
        String executorType = getExecutorTypeByBrand(brandCode);
        return messageExecutorService.isExecutorAvailable(executorType);
    }
}