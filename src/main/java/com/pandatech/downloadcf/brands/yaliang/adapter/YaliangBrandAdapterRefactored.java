package com.pandatech.downloadcf.brands.yaliang.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.brands.BaseBrandAdapter;
import com.pandatech.downloadcf.brands.yaliang.dto.YaliangRefreshMessage;
import com.pandatech.downloadcf.brands.yaliang.dto.YaliangRefreshRequest;
import com.pandatech.downloadcf.brands.yaliang.exception.YaliangException;
import com.pandatech.downloadcf.brands.yaliang.service.*;
import com.pandatech.downloadcf.brands.yaliang.service.YaliangEslIdParser.YaliangEslIdInfo;
import com.pandatech.downloadcf.dto.BrandOutputData;
import com.pandatech.downloadcf.dto.EslCompleteData;
import com.pandatech.downloadcf.entity.PandaProductWithBLOBs;
import com.pandatech.downloadcf.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 重构后的雅量品牌适配器
 * 通过组合多个专门的服务组件来实现功能，遵循单一职责原则
 * 支持品牌编码 YALIANG001
 */
@Slf4j
public class YaliangBrandAdapterRefactored extends BaseBrandAdapter {
    
    @Autowired
    private YaliangDataFormatter dataFormatter;
    
    @Autowired
    private YaliangEslIdParser eslIdParser;
    
    @Autowired
    private YaliangTemplateExtractor templateExtractor;
    
    @Autowired
    private YaliangMessageBuilder messageBuilder;
    
    @Autowired
    private YaliangRequestValidator requestValidator;
    
    @Autowired
    private DataService dataService;
    
    public YaliangBrandAdapterRefactored(ObjectMapper objectMapper) {
        super(objectMapper);
    }
    
    @Override
    public String getSupportedBrandCode() {
        return "YALIANG001";
    }
    
    @Override
    protected String getBrandName() {
        return "雅量科技";
    }
    
    @Override
    protected String getDefaultEslId() {
        return "06000000195B";
    }
    
    @Override
    protected boolean validateBrandSpecific(EslCompleteData completeData) {
        return requestValidator.validateBrandSpecific(completeData);
    }
    
    @Override
    protected void buildDefaultDataMap(Map<String, Object> dataMap, PandaProductWithBLOBs product) {
        dataFormatter.buildDefaultDataMap(dataMap, product);
    }
    
    @Override
    protected Object formatValueByFieldType(String fieldName, Object value) {
        return dataFormatter.formatValueByFieldType(fieldName, value);
    }
    
    @Override
    protected boolean isStringField(String fieldName) {
        return dataFormatter.isStringField(fieldName);
    }
    
    @Override
    public BrandOutputData transform(EslCompleteData completeData) {
        log.info("开始转换雅量品牌数据: ESL_ID={}", completeData.getEsl().getEslId());
        
        try {
            // 调用父类的基础转换逻辑
            BrandOutputData outputData = super.transform(completeData);
            
            // 解析雅量ESL ID格式
            YaliangEslIdInfo eslIdInfo = eslIdParser.parseYaliangEslId(completeData.getEsl().getEslId());
            
            // 处理模板EXT_JSON中的templateBase64
            String templateBase64 = templateExtractor.extractTemplateBase64FromExtJson(
                completeData.getTemplate() != null ? completeData.getTemplate().getExtJson() : null);
            
            // 创建雅量专用的MQTT消息
            Map<String, Object> yaliangMessage = messageBuilder.createYaliangMqttMessage(eslIdInfo, templateBase64);
            
            // 添加雅量特有的数据到dataMap
            Map<String, Object> dataMap = outputData.getDataMap();
            if (dataMap == null) {
                dataMap = new HashMap<>();
                outputData.setDataMap(dataMap);
            }
            
            dataMap.put("mqttTopic", messageBuilder.getMqttTopic(eslIdInfo.getDeviceCode()));
            dataMap.put("mqttPayload", yaliangMessage);
            dataMap.put("deviceCode", eslIdInfo.getDeviceCode());
            dataMap.put("deviceMac", eslIdInfo.getDeviceMac());
            
            log.info("雅量品牌数据转换完成: topic={}, deviceCode={}, deviceMac={}", 
                    dataMap.get("mqttTopic"), eslIdInfo.getDeviceCode(), eslIdInfo.getDeviceMac());
            
            return outputData;
            
        } catch (Exception e) {
            log.error("雅量品牌数据转换失败: ESL_ID={}", completeData.getEsl().getEslId(), e);
            throw new YaliangException(YaliangException.ErrorCodes.DATA_TRANSFORMATION_ERROR, 
                "数据转换失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 创建刷新消息
     */
    public YaliangRefreshMessage createRefreshMessage(YaliangRefreshRequest request) {
        log.info("开始创建雅量刷新消息: deviceCode={}, deviceMac={}", 
                request.getDeviceCode(), request.getDeviceMac());
        
        try {
            // 验证请求参数
            requestValidator.validateRefreshRequest(request);
            
            // 获取完整的价签数据，包括模板信息
            String eslId = request.getDeviceCode() + "_" + request.getDeviceMac();
            EslCompleteData completeData = dataService.getEslCompleteData(eslId);
            
            String templateBase64 = null;
            
            // 尝试从模板的EXT_JSON中提取templateBase64
            if (completeData != null && completeData.getTemplate() != null) {
                String extJson = completeData.getTemplate().getExtJson();
                log.info("模板EXT_JSON内容: {}", extJson != null ? extJson.substring(0, Math.min(100, extJson.length())) + "..." : "null");
                
                if (extJson != null && !extJson.trim().isEmpty()) {
                    templateBase64 = templateExtractor.extractTemplateBase64FromExtJson(extJson);
                    log.info("从EXT_JSON中提取templateBase64成功，大小: {}KB", 
                            templateBase64 != null ? templateBase64.length() / 1024 : 0);
                } else {
                    log.warn("模板EXT_JSON为空，无法提取templateBase64");
                }
            } else {
                log.warn("未找到价签对应的模板数据: eslId={}", eslId);
            }
            
            // 如果从模板中无法获取templateBase64，使用请求中的imageBase64作为备选
            if (templateBase64 == null || templateBase64.trim().isEmpty()) {
                templateBase64 = request.getImageBase64();
                log.info("使用请求中的imageBase64作为templateBase64");
            }
            
            // 使用消息构建器创建刷新消息
            return messageBuilder.createRefreshMessage(request, templateBase64);
            
        } catch (Exception e) {
            log.error("创建雅量刷新消息失败: deviceCode={}, error={}", 
                    request.getDeviceCode(), e.getMessage(), e);
            throw new YaliangException(YaliangException.ErrorCodes.MESSAGE_CREATION_ERROR, 
                "创建刷新消息失败: " + e.getMessage(), 
                request.getDeviceCode(), request.getDeviceMac(), e);
        }
    }
    
    /**
     * 验证刷新请求参数
     */
    public void validateRefreshRequest(YaliangRefreshRequest request) {
        requestValidator.validateRefreshRequest(request);
    }
    
    /**
     * 获取MQTT主题
     */
    public String getMqttTopic(String deviceCode) {
        return messageBuilder.getMqttTopic(deviceCode);
    }
    
    /**
     * 生成队列ID
     */
    public int generateQueueId() {
        return messageBuilder.generateQueueId();
    }
    
    /**
     * 获取支持的设备规格列表
     */
    public java.util.List<String> getSupportedDeviceSizes() {
        return requestValidator.getSupportedDeviceSizes();
    }
    
    /**
     * 获取设备规格信息
     */
    public com.pandatech.downloadcf.brands.yaliang.config.YaliangBrandConfig.DeviceSpec getDeviceSpecInfo(String deviceSize) {
        return requestValidator.getDeviceSpecInfo(deviceSize);
    }
}