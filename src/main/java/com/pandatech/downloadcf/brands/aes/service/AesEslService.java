package com.pandatech.downloadcf.brands.aes.service;

import com.pandatech.downloadcf.brands.aes.config.AesBrandConfig;
import com.pandatech.downloadcf.dto.BrandOutputData;
import com.pandatech.downloadcf.dto.EslCompleteData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * AES品牌专用服务类
 * 处理AES品牌特有的业务逻辑
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AesEslService {
    
    private final AesBrandConfig config;
    
    /**
     * 验证AES品牌特定的数据
     */
    public boolean validateAesData(EslCompleteData completeData) {
        // AES品牌特定验证逻辑
        if (completeData.getProduct() == null) {
            log.error("AES品牌要求商品数据不能为空");
            return false;
        }
        
        if (completeData.getProduct().getProductId() == null || 
            completeData.getProduct().getProductId().trim().isEmpty()) {
            log.error("AES品牌要求商品ID不能为空");
            return false;
        }
        
        return true;
    }
    
    /**
     * 构建AES品牌的MQTT主题
     */
    public String buildMqttTopic(String storeCode) {
        return String.format("%s/%s", config.getMqttTopicPrefix(), storeCode);
    }
    
    /**
     * 获取AES品牌配置
     */
    public AesBrandConfig getConfig() {
        return config;
    }
    
    /**
     * 处理AES品牌特有的数据转换
     */
    public void processAesSpecificData(BrandOutputData outputData) {
        // AES品牌特有的数据处理逻辑
        log.debug("处理AES品牌特有数据: {}", outputData.getBrandCode());
        
        // 可以在这里添加AES品牌特有的数据处理逻辑
        // 例如：特殊的价格格式化、特定的字段映射等
    }
}