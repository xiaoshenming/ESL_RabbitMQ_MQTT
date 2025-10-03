package com.pandatech.downloadcf.brands.yaliang.service;

import com.pandatech.downloadcf.brands.yaliang.config.YaliangBrandConfig;
import com.pandatech.downloadcf.brands.yaliang.dto.YaliangRefreshRequest;
import com.pandatech.downloadcf.brands.yaliang.exception.YaliangException;
import com.pandatech.downloadcf.brands.yaliang.util.YaliangImageProcessor;
import com.pandatech.downloadcf.dto.EslCompleteData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 雅量请求验证服务
 * 负责验证雅量品牌相关的请求参数和数据
 */
@Slf4j
@Service
public class YaliangRequestValidator {
    
    @Autowired
    private YaliangBrandConfig config;
    
    @Autowired
    private YaliangImageProcessor imageProcessor;
    
    /**
     * 验证品牌特定数据
     */
    public boolean validateBrandSpecific(EslCompleteData completeData) {
        // YALIANG品牌特定验证逻辑
        if (!StringUtils.hasText(completeData.getProduct().getProductId())) {
            log.error("YALIANG品牌要求商品ID不能为空");
            return false;
        }
        
        return true;
    }
    
    /**
     * 验证刷新请求参数
     */
    public void validateRefreshRequest(YaliangRefreshRequest request) {
        if (request == null) {
            throw new YaliangException(YaliangException.ErrorCodes.VALIDATION_ERROR, "刷新请求不能为空");
        }
        
        // 验证设备编码
        if (config.getValidation().isRequireDeviceCode()) {
            if (request.getDeviceCode() == null || request.getDeviceCode().trim().isEmpty()) {
                throw new YaliangException(YaliangException.ErrorCodes.INVALID_DEVICE_CODE, 
                    "设备编码不能为空", request.getDeviceCode(), request.getDeviceMac());
            }
        }
        
        // 验证设备MAC地址
        if (config.getValidation().isRequireDeviceMac()) {
            if (request.getDeviceMac() == null || request.getDeviceMac().trim().isEmpty()) {
                throw new YaliangException(YaliangException.ErrorCodes.INVALID_DEVICE_MAC, 
                    "设备MAC地址不能为空", request.getDeviceCode(), request.getDeviceMac());
            }
        }
        
        // 验证设备尺寸
        if (request.getDeviceSize() != null && !request.getDeviceSize().trim().isEmpty()) {
            YaliangBrandConfig.DeviceSpec spec = getDeviceSpec(request.getDeviceSize());
            if (spec == null) {
                throw new YaliangException(YaliangException.ErrorCodes.INVALID_DEVICE_SIZE, 
                    "不支持的设备尺寸: " + request.getDeviceSize(), 
                    request.getDeviceCode(), request.getDeviceMac());
            }
        }
        
        // 验证图片格式和大小
        if (request.getImageBase64() != null && !request.getImageBase64().trim().isEmpty()) {
            try {
                imageProcessor.validateImageFormat(request.getImageBase64());
                imageProcessor.validateImageSize(request.getImageBase64());
            } catch (Exception e) {
                throw new YaliangException(YaliangException.ErrorCodes.IMAGE_PROCESSING_ERROR, 
                    "图片验证失败: " + e.getMessage(), 
                    request.getDeviceCode(), request.getDeviceMac(), e);
            }
        }
    }
    
    /**
     * 获取设备规格信息（带异常处理）
     */
    public YaliangBrandConfig.DeviceSpec getDeviceSpec(String deviceSize) {
        try {
            if (deviceSize == null || deviceSize.trim().isEmpty()) {
                deviceSize = config.getDeviceSpecs().getDefaultSpec();
            }
            
            YaliangBrandConfig.DeviceSpec spec = config.getDeviceSpecs().getSpecs().get(deviceSize);
            if (spec == null) {
                log.warn("未找到设备规格: {}, 使用默认规格: {}", deviceSize, config.getDeviceSpecs().getDefaultSpec());
                spec = config.getDeviceSpecs().getSpecs().get(config.getDeviceSpecs().getDefaultSpec());
            }
            
            if (spec == null) {
                throw new YaliangException(YaliangException.ErrorCodes.CONFIG_ERROR, 
                    "配置错误：无法获取默认设备规格");
            }
            
            return spec;
        } catch (Exception e) {
            if (e instanceof YaliangException) {
                throw e;
            }
            throw new YaliangException(YaliangException.ErrorCodes.CONFIG_ERROR, 
                "获取设备规格失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 获取支持的设备规格列表
     */
    public List<String> getSupportedDeviceSizes() {
        return new ArrayList<>(config.getDeviceSpecs().getSpecs().keySet());
    }
    
    /**
     * 获取设备规格信息
     */
    public YaliangBrandConfig.DeviceSpec getDeviceSpecInfo(String deviceSize) {
        return config.getDeviceSpecs().getSpecs().get(deviceSize);
    }
    
    /**
     * 验证设备编码格式
     */
    public boolean isValidDeviceCode(String deviceCode) {
        if (deviceCode == null || deviceCode.trim().isEmpty()) {
            return false;
        }
        
        // 雅量设备编码通常是8位字符，如CG101F6D
        String cleanCode = deviceCode.trim().toUpperCase();
        return cleanCode.length() >= 6 && cleanCode.length() <= 10 && cleanCode.matches("[A-Z0-9]+");
    }
    
    /**
     * 验证MAC地址格式
     */
    public boolean isValidDeviceMac(String deviceMac) {
        if (deviceMac == null || deviceMac.trim().isEmpty()) {
            return false;
        }
        
        // 雅量MAC地址通常是12位或14位十六进制字符
        String cleanMac = deviceMac.trim().toUpperCase();
        return (cleanMac.length() == 12 || cleanMac.length() == 14) && cleanMac.matches("[A-F0-9]+");
    }
    
    /**
     * 验证队列ID范围
     */
    public boolean isValidQueueId(int queueId) {
        int minQueueId = config.getValidation().getMinQueueId();
        int maxQueueId = config.getValidation().getMaxQueueId();
        
        if (minQueueId > 0 && queueId < minQueueId) {
            return false;
        }
        
        if (maxQueueId > 0 && queueId > maxQueueId) {
            return false;
        }
        
        return true;
    }
}