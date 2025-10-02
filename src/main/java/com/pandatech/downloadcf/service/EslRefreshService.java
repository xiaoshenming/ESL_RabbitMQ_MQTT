package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.adapter.BrandAdapter;
import com.pandatech.downloadcf.brands.BrandAdapterFactory;
import com.pandatech.downloadcf.brands.BaseBrandAdapter;
import com.pandatech.downloadcf.dto.BrandOutputData;
import com.pandatech.downloadcf.dto.EslCompleteData;
import com.pandatech.downloadcf.dto.EslRefreshRequest;
import com.pandatech.downloadcf.dto.EslStoreRefreshRequest;
import com.pandatech.downloadcf.dto.EslProductRefreshRequest;
import com.pandatech.downloadcf.util.BrandCodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 价签刷新服务 - 核心业务逻辑
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EslRefreshService {
    
    private final DataService dataService;
    private final MessageProducerService messageProducerService;
    private final BrandAdapterFactory brandAdapterFactory;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    
    /**
     * 刷新价签
     */
    public boolean refreshEsl(EslRefreshRequest request) {
        log.info("开始刷新价签: {}", request);
        log.info("请求中的品牌编码: {}", request.getBrandCode());
        
        try {
            // 1. 获取完整的价签数据
            EslCompleteData completeData = dataService.getEslCompleteData(request.getEslId());
            if (completeData == null) {
                log.error("获取价签数据失败: eslId={}", request.getEslId());
                return false;
            }
            log.info("获取到价签数据，商品品牌: {}", completeData.getProduct() != null ? completeData.getProduct().getProductBrand() : "null");
            
            // 2. 设置请求参数 - 使用BrandCodeUtil进行品牌代码兼容性处理
            String requestBrandCode = BrandCodeUtil.normalizeBrandCode(request.getBrandCode());
            String productBrand = completeData.getProduct().getProductBrand();
            String normalizedProductBrand = BrandCodeUtil.normalizeBrandCode(productBrand);
            
            // 优先使用商品数据中的品牌字段，如果为空则使用请求中的品牌代码
            String actualBrandCode = StringUtils.hasText(normalizedProductBrand) ? normalizedProductBrand : requestBrandCode;
            
            // 转换为适配器使用的品牌代码
            String adapterBrandCode = BrandCodeUtil.toAdapterBrandCode(actualBrandCode);
            
            log.info("请求参数中的品牌编码: {} -> 标准化: {}", request.getBrandCode(), requestBrandCode);
            log.info("商品数据中的品牌字段: {} -> 标准化: {}", productBrand, normalizedProductBrand);
            log.info("实际使用的品牌编码: {} -> 适配器品牌编码: {}", actualBrandCode, adapterBrandCode);
            
            completeData.setBrandCode(adapterBrandCode);
            
            if (request.getForceRefresh() != null) {
                completeData.setForceRefresh(request.getForceRefresh());
            }
            if (request.getStoreCode() != null) {
                completeData.setStoreCode(request.getStoreCode());
            }
            
            // 3. 查找对应的品牌适配器
            BrandAdapter adapter = brandAdapterFactory.getAdapter(adapterBrandCode);
            if (adapter == null) {
                log.error("未找到品牌适配器: brandCode={}", completeData.getBrandCode());
                return false;
            }
            log.info("获取到品牌适配器: {}", adapter.getClass().getSimpleName());
            log.info("品牌适配器支持的品牌编码: {}", adapter.getSupportedBrandCode());
            
            // 4. 验证数据
            if (!adapter.validate(completeData)) {
                log.error("数据验证失败: eslId={}, brandCode={}", 
                        request.getEslId(), completeData.getBrandCode());
                return false;
            }
            log.info("数据验证通过");
            
            // 5. 转换数据
            BrandOutputData outputData = adapter.transform(completeData);
            if (outputData == null) {
                log.error("数据转换失败: eslId={}, brandCode={}", 
                        request.getEslId(), completeData.getBrandCode());
                return false;
            }
            log.info("数据转换完成，输出数据类型: {}", outputData.getClass().getSimpleName());
            
            // 6. 发送消息到RabbitMQ队列
            log.info("准备发送消息到RabbitMQ队列，使用品牌编码: {}", adapterBrandCode);
            boolean success = sendToRefreshQueue(outputData);
            
            if (success) {
                log.info("价签刷新消息已发送到队列: eslId={}, brandCode={}", 
                        request.getEslId(), completeData.getBrandCode());
            } else {
                log.error("价签刷新消息发送到队列失败: eslId={}, brandCode={}", 
                        request.getEslId(), completeData.getBrandCode());
            }
            
            return success;
            
        } catch (Exception e) {
            log.error("价签刷新异常: eslId={}", request.getEslId(), e);
            return false;
        }
    }
    
    /**
     * 批量刷新价签
     */
    public int batchRefreshEsl(List<EslRefreshRequest> requests) {
        log.info("开始批量刷新价签: count={}", requests.size());
        
        int successCount = 0;
        for (EslRefreshRequest request : requests) {
            try {
                if (refreshEsl(request)) {
                    successCount++;
                }
            } catch (Exception e) {
                log.error("批量刷新价签异常: eslId={}", request.getEslId(), e);
            }
        }
        
        log.info("批量刷新价签完成: total={}, success={}, failed={}", 
                requests.size(), successCount, requests.size() - successCount);
        
        return successCount;
    }
    
    /**
     * 根据门店刷新所有价签
     */
    public int refreshEslByStore(EslStoreRefreshRequest request) {
        log.info("开始按门店刷新价签: storeCode={}, brandCode={}", 
                request.getStoreCode(), request.getBrandCode());
        
        // 获取门店的所有价签
        var esls = dataService.getEslsByStore(request.getStoreCode());
        if (esls.isEmpty()) {
            log.warn("门店没有价签: storeCode={}", request.getStoreCode());
            return 0;
        }
        
        // 构建刷新请求
        List<EslRefreshRequest> requests = esls.stream()
                .map(esl -> {
                    EslRefreshRequest eslRequest = new EslRefreshRequest();
                    eslRequest.setEslId(esl.getId());
                    eslRequest.setBrandCode(request.getBrandCode());
                    eslRequest.setStoreCode(request.getStoreCode());
                    eslRequest.setForceRefresh(request.getForceRefresh());
                    return eslRequest;
                })
                .toList();
        
        return batchRefreshEsl(requests);
    }
    
    /**
     * 根据商品刷新相关价签
     */
    public int refreshEslByProduct(EslProductRefreshRequest request) {
        log.info("开始按商品刷新价签: productCode={}, brandCode={}", 
                request.getProductCode(), request.getBrandCode());
        
        // 获取商品绑定的所有价签
        var esls = dataService.getEslsByProduct(request.getProductCode());
        if (esls.isEmpty()) {
            log.warn("商品没有绑定价签: productCode={}", request.getProductCode());
            return 0;
        }
        
        // 构建刷新请求
        List<EslRefreshRequest> requests = esls.stream()
                .map(esl -> {
                    EslRefreshRequest eslRequest = new EslRefreshRequest();
                    eslRequest.setEslId(esl.getId());
                    eslRequest.setBrandCode(request.getBrandCode());
                    eslRequest.setStoreCode(esl.getStoreCode());
                    eslRequest.setForceRefresh(request.getForceRefresh());
                    return eslRequest;
                })
                .toList();
        
        return batchRefreshEsl(requests);
    }
    
    /**
     * 查找品牌适配器
     */

    
    /**
     * 获取支持的品牌列表
     */
    public List<Map<String, Object>> getSupportedBrands() {
        return brandAdapterFactory.getSupportedBrands();
    }
    
    /**
     * 发送消息到RabbitMQ刷新队列
     * 注意：YALIANG品牌直接发送MQTT消息，不通过RabbitMQ队列，避免重复发送
     */
    private boolean sendToRefreshQueue(BrandOutputData outputData) {
        try {
            // 根据品牌确定MQTT主题和载荷
            String mqttTopic;
            Object mqttPayload;
            
            if ("YALIANG001".equals(outputData.getBrandCode())) {
                // 雅量品牌直接发送MQTT消息，不通过RabbitMQ队列
                // 使用MessageProducerService的sendMessage方法，它会自动处理MQTT发送
                boolean success = messageProducerService.sendMessage(outputData);
                
                if (success) {
                    log.info("YALIANG品牌MQTT消息直接发送成功: eslId={}", outputData.getEslId());
                } else {
                    log.error("YALIANG品牌MQTT消息直接发送失败: eslId={}", outputData.getEslId());
                }
                
                return success;
            } else {
                // 其他品牌使用原有格式，通过RabbitMQ队列
                mqttTopic = "esl/server/data/" + outputData.getStoreCode();
                mqttPayload = messageProducerService.buildMqttPayload(outputData);
                
                // 构建队列消息
                Map<String, Object> queueMessage = new HashMap<>();
                queueMessage.put("messageType", "refresh");
                queueMessage.put("brandCode", outputData.getBrandCode());
                queueMessage.put("eslId", outputData.getEslId());
                queueMessage.put("storeCode", outputData.getStoreCode());
                queueMessage.put("mqttTopic", mqttTopic);
                queueMessage.put("mqttPayload", mqttPayload);
                queueMessage.put("timestamp", System.currentTimeMillis());
                queueMessage.put("priority", 1); // 刷新消息优先级为1
                
                // 发送到刷新队列
                String jsonMessage = objectMapper.writeValueAsString(queueMessage);
                rabbitTemplate.convertAndSend("refresh.queue", jsonMessage);
                
                log.info("价签刷新消息已发送到RabbitMQ队列: eslId={}, storeCode={}, mqttTopic={}", 
                        outputData.getEslId(), outputData.getStoreCode(), mqttTopic);
                return true;
            }
            
        } catch (Exception e) {
            log.error("发送消息失败: eslId={}, 错误: {}", 
                    outputData.getEslId(), e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * 直接刷新价签（用于批量处理）
     * 不经过队列，直接发送MQTT消息
     */
    public void refreshEslDirect(String eslId, String brandCode, String storeCode) {
        try {
            log.info("直接刷新价签: eslId={}, brandCode={}, storeCode={}", eslId, brandCode, storeCode);
            
            // 使用BrandCodeUtil进行品牌代码兼容性处理
            String normalizedBrandCode = BrandCodeUtil.normalizeBrandCode(brandCode);
            String adapterBrandCode = BrandCodeUtil.toAdapterBrandCode(normalizedBrandCode);
            
            log.info("品牌代码转换: {} -> 标准化: {} -> 适配器: {}", brandCode, normalizedBrandCode, adapterBrandCode);
            
            // 构建BrandOutputData对象
            BrandOutputData outputData = new BrandOutputData();
            outputData.setBrandCode(adapterBrandCode);
            outputData.setEslId(eslId);
            outputData.setStoreCode(storeCode);
            outputData.setActualEslId(eslId); // 使用eslId作为actualEslId的默认值
            
            // 构建数据映射
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("command", "refresh");
            dataMap.put("id", eslId);
            dataMap.put("timestamp", System.currentTimeMillis());
            dataMap.put("shop", storeCode);
            dataMap.put("brandCode", adapterBrandCode);
            outputData.setDataMap(dataMap);
            
            // 直接发送MQTT消息
            messageProducerService.sendMessage(outputData);
            
            log.info("直接刷新价签完成: eslId={}", eslId);
            
        } catch (Exception e) {
            log.error("直接刷新价签失败: eslId={}, 错误: {}", eslId, e.getMessage(), e);
            throw new RuntimeException("直接刷新价签失败: " + e.getMessage(), e);
        }
    }
}