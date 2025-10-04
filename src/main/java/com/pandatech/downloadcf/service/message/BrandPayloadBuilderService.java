package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.dto.BrandOutputData;
import com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs;
import com.pandatech.downloadcf.brands.yaliang.config.YaliangBrandConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 品牌载荷构建服务
 * 专门处理不同品牌的MQTT消息载荷构建
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BrandPayloadBuilderService {
    
    private final DataService dataService;
    private final YaliangBrandConfig yaliangBrandConfig;
    private final ObjectMapper objectMapper;
    
    /**
     * 构建MQTT载荷 - 根据品牌选择不同格式
     */
    public Object buildMqttPayload(BrandOutputData outputData) {
        log.info("开始构建MQTT载荷，品牌: {}, ESL ID: {}, 门店代码: {}", 
                outputData.getBrandCode(), outputData.getEslId(), outputData.getStoreCode());
        
        // 根据品牌选择不同的载荷格式
        if ("YALIANG001".equals(outputData.getBrandCode())) {
            return buildYaliangMqttPayload(outputData);
        } else {
            return buildPandaMqttPayload(outputData);
        }
    }
    
    /**
     * 构建MQTT载荷（带模板数据，避免重复查询）
     */
    public Object buildMqttPayload(BrandOutputData outputData, PrintTemplateDesignWithBLOBs template) {
        log.info("开始构建MQTT载荷（使用已有模板数据），品牌: {}, ESL ID: {}, 门店代码: {}", 
                outputData.getBrandCode(), outputData.getEslId(), outputData.getStoreCode());
        
        // 根据品牌选择不同的载荷格式
        if ("YALIANG001".equals(outputData.getBrandCode())) {
            return buildYaliangMqttPayload(outputData, template);
        } else {
            return buildPandaMqttPayload(outputData);
        }
    }
    
    /**
     * 构建雅量品牌MQTT载荷
     */
    private Object buildYaliangMqttPayload(BrandOutputData outputData) {
        log.info("构建雅量品牌MQTT载荷，ESL ID: {}", outputData.getEslId());
        
        Map<String, Object> payload = new LinkedHashMap<>();
        
        // 解析雅量ESL ID格式：CG101F6D-00125414A7B9B046
        String actualEslId = outputData.getActualEslId();
        String[] parts = actualEslId.split("-");
        if (parts.length != 2) {
            log.error("雅量ESL ID格式错误: {}", actualEslId);
            throw new RuntimeException("雅量ESL ID格式错误: " + actualEslId);
        }
        
        String deviceCode = parts[0];
        String deviceMac = parts[1];
        
        // 构建雅量格式的消息
        payload.put("queueId", generateYaliangQueueId());
        payload.put("deviceType", 1);
        payload.put("deviceCode", deviceCode);
        payload.put("deviceMac", deviceMac);
        payload.put("deviceVersion", "4.0.0");
        payload.put("refreshAction", 3);
        
        // 获取设备规格
        YaliangBrandConfig.DeviceSpec deviceSpec = getDeviceSpecForEsl(deviceCode);
        if (deviceSpec != null) {
            // 从输出数据中提取模板Base64
            String templateBase64 = extractTemplateBase64FromOutputData(outputData);
            if (templateBase64 != null && !templateBase64.trim().isEmpty()) {
                payload.put("refreshData", templateBase64);
                log.info("雅量MQTT载荷构建完成，包含模板数据");
            } else {
                log.warn("未找到模板Base64数据，ESL ID: {}", outputData.getEslId());
            }
        }
        
        return payload;
    }
    
    /**
     * 构建雅量品牌MQTT载荷（使用已有模板数据）
     */
    private Object buildYaliangMqttPayload(BrandOutputData outputData, PrintTemplateDesignWithBLOBs template) {
        log.info("构建雅量品牌MQTT载荷（使用已有模板），ESL ID: {}", outputData.getEslId());
        
        Map<String, Object> payload = new LinkedHashMap<>();
        
        // 解析雅量ESL ID格式：CG101F6D-00125414A7B9B046
        String actualEslId = outputData.getActualEslId();
        String[] parts = actualEslId.split("-");
        if (parts.length != 2) {
            log.error("雅量ESL ID格式错误: {}", actualEslId);
            throw new RuntimeException("雅量ESL ID格式错误: " + actualEslId);
        }
        
        String deviceCode = parts[0];
        String deviceMac = parts[1];
        
        // 构建雅量格式的消息
        payload.put("queueId", generateYaliangQueueId());
        payload.put("deviceType", 1);
        payload.put("deviceCode", deviceCode);
        payload.put("deviceMac", deviceMac);
        payload.put("deviceVersion", "4.0.0");
        payload.put("refreshAction", 3);
        
        // 获取设备规格
        YaliangBrandConfig.DeviceSpec deviceSpec = getDeviceSpecForEsl(deviceCode);
        if (deviceSpec != null && template != null) {
            // 从模板的EXT_JSON中提取templateBase64
            String templateBase64 = extractTemplateBase64FromExtJson(template.getExtJson());
            if (templateBase64 != null && !templateBase64.trim().isEmpty()) {
                payload.put("refreshData", templateBase64);
                log.info("雅量MQTT载荷构建完成，使用模板数据");
            } else {
                log.warn("模板中未找到Base64数据，ESL ID: {}", outputData.getEslId());
            }
        }
        
        return payload;
    }
    
    /**
     * 获取雅量设备规格
     */
    private YaliangBrandConfig.DeviceSpec getDeviceSpecForEsl(String deviceCode) {
        if (yaliangBrandConfig != null && yaliangBrandConfig.getDeviceSpecs() != null) {
            String deviceSize = "2.13"; // 默认规格
            
            // 根据设备代码前缀匹配设备尺寸
            if (deviceCode != null) {
                // 雅量设备代码规则匹配
                if (deviceCode.startsWith("DC100")) {
                    // DC100开头的设备通常是1.54寸
                    deviceSize = "1.54";
                    log.info("根据设备代码识别为1.54寸设备: deviceCode={}", deviceCode);
                } else if (deviceCode.startsWith("CG101")) {
                    // CG101开头的设备通常是2.13寸
                    deviceSize = "2.13";
                    log.info("根据设备代码识别为2.13寸设备: deviceCode={}", deviceCode);
                } else if (deviceCode.startsWith("CG420")) {
                    // CG420开头的设备通常是4.2寸
                    deviceSize = "4.2";
                    log.info("根据设备代码识别为4.2寸设备: deviceCode={}", deviceCode);
                } else {
                    log.warn("未知设备代码前缀，使用默认规格: deviceCode={}, defaultSize={}", deviceCode, deviceSize);
                }
            }
            
            // 遍历所有规格，查找匹配的设备代码前缀
            Map<String, YaliangBrandConfig.DeviceSpec> specs = yaliangBrandConfig.getDeviceSpecs().getSpecs();
            if (specs != null) {
                YaliangBrandConfig.DeviceSpec spec = specs.get(deviceSize);
                if (spec != null) {
                    log.info("成功获取设备规格: deviceCode={}, deviceSize={}, resolution={}x{}, rotation={}", 
                            deviceCode, deviceSize, spec.getWidth(), spec.getHeight(), spec.getRotation());
                    return spec;
                } else {
                    log.warn("未找到指定尺寸的设备规格，使用默认规格: deviceCode={}, requestedSize={}, defaultSize={}", 
                            deviceCode, deviceSize, yaliangBrandConfig.getDeviceSpecs().getDefaultSpec());
                    return specs.get(yaliangBrandConfig.getDeviceSpecs().getDefaultSpec());
                }
            }
        }
        log.warn("未找到匹配的设备规格: {}", deviceCode);
        return null;
    }
    
    /**
     * 构建攀攀品牌MQTT载荷
     */
    private Object buildPandaMqttPayload(BrandOutputData outputData) {
        log.info("构建攀攀品牌MQTT载荷，ESL ID: {}", outputData.getEslId());
        
        Map<String, Object> payload = new LinkedHashMap<>();
        
        // 构建攀攀格式的消息
        payload.put("cmd", "data");
        payload.put("data", buildDataArray(outputData));
        
        log.info("攀攀MQTT载荷构建完成");
        return payload;
    }
    
    /**
     * 生成雅量队列ID
     */
    private int generateYaliangQueueId() {
        return (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
    }
    
    /**
     * 从输出数据中提取模板Base64
     */
    private String extractTemplateBase64FromOutputData(BrandOutputData outputData) {
        if (outputData.getExtJson() != null && !outputData.getExtJson().trim().isEmpty()) {
            return extractTemplateBase64FromExtJson(outputData.getExtJson());
        }
        return null;
    }
    
    /**
     * 从EXT_JSON中提取templateBase64字段
     */
    private String extractTemplateBase64FromExtJson(String extJson) {
        if (extJson == null || extJson.trim().isEmpty()) {
            return null;
        }
        
        try {
            JsonNode rootNode = objectMapper.readTree(extJson);
            
            // 查找templateBase64字段
            JsonNode templateBase64Node = rootNode.get("templateBase64");
            if (templateBase64Node != null && !templateBase64Node.isNull()) {
                String templateBase64 = templateBase64Node.asText();
                // 如果是data:image格式，提取base64部分
                if (templateBase64.startsWith("data:image/")) {
                    int commaIndex = templateBase64.indexOf(",");
                    if (commaIndex > 0 && commaIndex < templateBase64.length() - 1) {
                        return templateBase64.substring(commaIndex + 1);
                    }
                }
                return templateBase64;
            }
            
            log.warn("EXT_JSON中未找到templateBase64字段");
            return null;
        } catch (Exception e) {
            log.error("解析EXT_JSON失败", e);
            return null;
        }
    }
    
    /**
     * 构建data数组 - 严格按照PANDA标准格式
     */
    private List<Map<String, Object>> buildDataArray(BrandOutputData outputData) {
        log.info("开始构建data数组，ESL ID: {}", outputData.getEslId());
        
        Map<String, Object> dataItem = new LinkedHashMap<>(); // 使用LinkedHashMap保持字段顺序
        
        // 严格按照标准格式的字段顺序：tag, tmpl, model, checksum, forcefrash, value, taskid, token
        long tagValue = convertEslIdToDecimal(outputData.getActualEslId());
        String templateName = getTemplateName(outputData.getEslId());
        String modelValue = getModelByEslId(outputData.getEslId());
        int taskId = generateTaskId();
        int token = generateToken();
        
        dataItem.put("tag", tagValue); // 使用真正的ESL设备ID进行转换
        dataItem.put("tmpl", templateName); // 根据ESL ID获取模板名称
        dataItem.put("model", modelValue); // 根据ESL ID获取屏幕类型对应的model
        dataItem.put("checksum", outputData.getChecksum() != null ? outputData.getChecksum() : "");
        dataItem.put("forcefrash", 1); // 保持原有拼写（按照标准格式）
        dataItem.put("value", outputData.getDataMap()); // 直接使用已经格式化的数据映射
        dataItem.put("taskid", taskId); // 生成任务ID
        dataItem.put("token", token); // 生成令牌
        
        log.info("data数组构建完成，tag: {}, tmpl: {}, model: {}, taskid: {}, token: {}", 
                tagValue, templateName, modelValue, taskId, token);
        
        return List.of(dataItem);
    }
    
    /**
     * 将ESL ID转换为十进制 - 支持多种格式
     */
    private long convertEslIdToDecimal(String eslId) {
        if (eslId == null || eslId.trim().isEmpty()) {
            log.warn("ESL ID为空，使用默认值0");
            return 0L;
        }
        
        String cleanEslId = eslId.trim().toUpperCase();
        
        // 检查是否为雅量格式 (包含连字符的code-mac格式)
        if (cleanEslId.contains("-")) {
            return convertYaliangEslIdToDecimal(cleanEslId);
        }
        
        // 处理攀攀标准十六进制格式
        return convertPandaEslIdToDecimal(cleanEslId);
    }
    
    /**
     * 转换雅量ESL ID格式
     */
    private long convertYaliangEslIdToDecimal(String yaliangEslId) {
        try {
            String[] parts = yaliangEslId.split("-");
            if (parts.length == 2) {
                String mac = parts[1];
                long result = Math.abs(mac.hashCode());
                log.info("雅量ESL ID转换完成: {} -> {}", yaliangEslId, result);
                return result;
            } else {
                log.warn("雅量ESL ID格式不正确: {}", yaliangEslId);
                return Math.abs(yaliangEslId.hashCode());
            }
        } catch (Exception e) {
            log.error("雅量ESL ID转换失败: {}", yaliangEslId, e);
            return Math.abs(yaliangEslId.hashCode());
        }
    }
    
    /**
     * 转换攀攀ESL ID格式
     */
    private long convertPandaEslIdToDecimal(String pandaEslId) {
        try {
            String cleanHexId = pandaEslId;
            if (cleanHexId.startsWith("0X")) {
                cleanHexId = cleanHexId.substring(2);
            }
            
            Long result = Long.parseUnsignedLong(cleanHexId, 16);
            log.debug("攀攀ESL ID转换成功: {} -> {}", pandaEslId, result);
            return result;
            
        } catch (NumberFormatException e) {
            log.error("攀攀ESL ID转换失败: {}", pandaEslId, e);
            long fallback = Math.abs(pandaEslId.hashCode());
            log.warn("使用hashCode作为备用方案: {} -> {}", pandaEslId, fallback);
            return fallback;
        }
    }
    
    /**
     * 获取模板名称
     */
    private String getTemplateName(String eslId) {
        try {
            PrintTemplateDesignWithBLOBs template = dataService.getTemplateByEslId(eslId);
            if (template != null && template.getName() != null && !template.getName().trim().isEmpty()) {
                return template.getName().trim();
            }
            return "2"; // 默认模板名称
        } catch (Exception e) {
            log.error("获取模板名称失败: {}", eslId, e);
            return "2";
        }
    }
    
    /**
     * 根据ESL ID获取model值
     */
    private String getModelByEslId(String eslId) {
        try {
            PrintTemplateDesignWithBLOBs template = dataService.getTemplateByEslId(eslId);
            if (template != null) {
                String screenType = extractScreenTypeFromTemplate(template);
                return getTagType(screenType);
            }
            return "1"; // 默认model值
        } catch (Exception e) {
            log.error("获取model值失败: {}", eslId, e);
            return "1";
        }
    }
    
    /**
     * 从模板中提取屏幕类型
     */
    private String extractScreenTypeFromTemplate(PrintTemplateDesignWithBLOBs template) {
        if (template.getExtJson() != null && !template.getExtJson().trim().isEmpty()) {
            return extractScreenTypeFromExtJson(template.getExtJson());
        }
        return "2.13";
    }
    
    /**
     * 从EXT_JSON中提取屏幕类型
     */
    private String extractScreenTypeFromExtJson(String extJson) {
        try {
            JsonNode rootNode = objectMapper.readTree(extJson);
            JsonNode screenTypeNode = rootNode.get("screenType");
            if (screenTypeNode != null && !screenTypeNode.isNull()) {
                return screenTypeNode.asText();
            }
        } catch (Exception e) {
            log.error("解析屏幕类型失败", e);
        }
        return "2.13";
    }
    
    /**
     * 根据屏幕类型获取TagType
     */
    private String getTagType(String screenType) {
        if (screenType == null || screenType.trim().isEmpty()) {
            return "1";
        }
        
        switch (screenType.trim()) {
            case "1.54":
                return "1";
            case "2.13":
                return "2";
            case "2.9":
                return "3";
            case "4.2":
                return "4";
            case "7.5":
                return "5";
            default:
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
}