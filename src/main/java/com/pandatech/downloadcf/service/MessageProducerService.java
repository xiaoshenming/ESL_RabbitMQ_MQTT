package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.dto.BrandOutputData;
import com.pandatech.downloadcf.dto.MessageExecutionData;
import com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs;
import com.pandatech.downloadcf.executor.MessageExecutor;
import com.pandatech.downloadcf.util.BrandCodeUtil;
import com.pandatech.downloadcf.brands.yaliang.util.YaliangImageProcessor;
import com.pandatech.downloadcf.brands.yaliang.config.YaliangBrandConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * 消息生产者服务 - 负责将数据发送到消息队列和执行器
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducerService {
    
    private final List<MessageExecutor> messageExecutors;
    private final DataService dataService;
    private final YaliangImageProcessor yaliangImageProcessor;
    private final YaliangBrandConfig yaliangBrandConfig;
    
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
            // 根据品牌选择不同的MQTT主题格式
            if ("YALIANG001".equals(outputData.getBrandCode())) {
                executionData.setDestination(buildYaliangMqttTopic(outputData.getStoreCode()));
            } else {
                executionData.setDestination(buildMqttTopic(outputData.getStoreCode()));
            }
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
     * 构建MQTT主题 - 按照品牌特定格式
     */
    private String buildMqttTopic(String storeCode) {
        return String.format("esl/server/data/%s", storeCode);
    }
    
    /**
     * 为YALIANG品牌构建特定的MQTT主题
     */
    private String buildYaliangMqttTopic(String storeCode) {
        // YALIANG品牌使用特定的MQTT主题格式
        return String.format("yl-esl/%s/refresh/queue", storeCode);
    }
    
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
        payload.put("refreshArea", 1);
        
        // 构建content数组
        List<Map<String, Object>> contentList = new ArrayList<>();
        Map<String, Object> contentItem = new HashMap<>();
        contentItem.put("dataType", 3);
        
        // 从EXT_JSON中提取templateBase64并进行图片处理
        String templateBase64 = extractTemplateBase64FromOutputData(outputData);
        
        // 如果获取到了templateBase64，进行图片分辨率转换
        if (templateBase64 != null && !templateBase64.trim().isEmpty()) {
            try {
                // 根据设备规格获取目标分辨率
                YaliangBrandConfig.DeviceSpec deviceSpec = getDeviceSpecForEsl(deviceCode);
                if (deviceSpec != null) {
                    log.info("开始处理雅量图片: deviceCode={}, 目标分辨率={}x{}, 旋转角度={}", 
                            deviceCode, deviceSpec.getWidth(), deviceSpec.getHeight(), deviceSpec.getRotation());
                    
                    // 使用YaliangImageProcessor处理图片
                     String processedImage = yaliangImageProcessor.processImage(templateBase64, deviceSpec, yaliangBrandConfig);
                    templateBase64 = processedImage;
                    
                    log.info("雅量图片处理完成: deviceCode={}, 处理后大小={}KB", 
                            deviceCode, processedImage.length() / 1024);
                } else {
                    log.warn("未找到设备规格配置，使用原始图片: deviceCode={}", deviceCode);
                }
            } catch (Exception e) {
                log.error("雅量图片处理失败，使用原始图片: deviceCode={}, error={}", deviceCode, e.getMessage());
            }
        }
        
        contentItem.put("dataRef", templateBase64 != null ? templateBase64 : "iVxxxYII=");
        contentItem.put("layerEnd", true);
        contentList.add(contentItem);
        
        payload.put("content", contentList);
        
        log.info("雅量MQTT载荷构建完成: queueId={}, deviceCode={}, deviceMac={}", 
                payload.get("queueId"), deviceCode, deviceMac);
        
        return payload;
    }
    
    /**
     * 根据设备代码获取设备规格
     */
    private YaliangBrandConfig.DeviceSpec getDeviceSpecForEsl(String deviceCode) {
        try {
            // 从设备代码推断设备尺寸，这里需要根据实际的设备代码规则来实现
            // 暂时使用4.2寸作为默认规格
            String deviceSize = "4.2";
            
            // 从配置中获取设备规格
            YaliangBrandConfig.DeviceSpecs deviceSpecs = yaliangBrandConfig.getDeviceSpecs();
            if (deviceSpecs != null && deviceSpecs.getSpecs() != null) {
                return deviceSpecs.getSpecs().get(deviceSize);
            }
            
            log.warn("未找到设备规格配置: deviceCode={}, deviceSize={}", deviceCode, deviceSize);
            return null;
        } catch (Exception e) {
            log.error("获取设备规格失败: deviceCode={}, error={}", deviceCode, e.getMessage());
            return null;
        }
    }
     
     /**
      * 构建PANDA标准MQTT载荷
      */
     private Object buildPandaMqttPayload(BrandOutputData outputData) {
         log.info("构建PANDA标准MQTT载荷，ESL ID: {}, 门店代码: {}", 
                 outputData.getEslId(), outputData.getStoreCode());
        
        Map<String, Object> payload = new LinkedHashMap<>(); // 使用LinkedHashMap保持字段顺序
        
        // 生成随机UUID作为消息ID（必须每次都不同）
        String messageId = java.util.UUID.randomUUID().toString();
        // 生成完整的时间戳（避免科学计数法省略值）
        long currentTimeMillis = System.currentTimeMillis();
        double timestamp = currentTimeMillis / 1000.0;
        
        // 严格按照标准格式的字段顺序：command, data, id, timestamp, shop
        payload.put("command", "wtag");
        payload.put("data", buildDataArray(outputData));
        payload.put("id", messageId); // 使用随机UUID，确保每次都不同
        payload.put("timestamp", timestamp); // 使用完整时间戳
        payload.put("shop", outputData.getStoreCode());
        
        log.info("PANDA MQTT载荷构建完成，载荷大小: {} 字段，消息ID: {}, 时间戳: {}", 
                payload.size(), messageId, timestamp);
        return payload;
    }
    
    /**
     * 生成雅量品牌的queueId
     */
    private int generateYaliangQueueId() {
        // 使用当前时间戳的后4位作为queueId，确保唯一性
        return (int) (System.currentTimeMillis() % 10000);
    }
    
    /**
     * 从BrandOutputData中提取templateBase64
     */
    private String extractTemplateBase64FromOutputData(BrandOutputData outputData) {
        try {
            // 尝试从ESL数据中获取模板信息
            String eslId = outputData.getEslId();
            PrintTemplateDesignWithBLOBs template = dataService.getTemplateByEslId(eslId);
            
            if (template != null && template.getExtJson() != null) {
                String extJson = template.getExtJson();
                return extractTemplateBase64FromExtJson(extJson);
            }
            
            log.warn("无法从ESL数据中获取templateBase64: eslId={}", eslId);
            return null;
        } catch (Exception e) {
            log.error("提取templateBase64失败", e);
            return null;
        }
    }
    
    /**
     * 从EXT_JSON中提取templateBase64字段
     */
    private String extractTemplateBase64FromExtJson(String extJson) {
        if (extJson == null || extJson.trim().isEmpty()) {
            return null;
        }
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(extJson);
            
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
     * 攀攀格式：06000000195A → 6597069773146
     * 雅量格式：CG101F6D-00125414A7B9B046 → 使用MAC部分进行转换
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
     * 转换雅量ESL ID格式 (CG101F6D-00125414A7B9B046)
     * 使用MAC地址部分进行转换，不进行十六进制转换，保持原始格式
     */
    private long convertYaliangEslIdToDecimal(String yaliangEslId) {
        try {
            // 雅量格式：CG101F6D-00125414A7B9B046
            // 分割为 code 和 mac 两部分
            String[] parts = yaliangEslId.split("-");
            if (parts.length == 2) {
                String code = parts[0]; // CG101F6D
                String mac = parts[1];  // 00125414A7B9B046
                
                log.info("雅量ESL ID解析: 原始={}, 代码={}, MAC={}", yaliangEslId, code, mac);
                
                // 对于雅量，我们使用MAC地址的hashCode作为tag值
                // 这样可以确保每个设备有唯一的标识，同时避免十六进制转换问题
                long result = Math.abs(mac.hashCode());
                log.info("雅量ESL ID转换完成: {} -> {}", yaliangEslId, result);
                return result;
            } else {
                log.warn("雅量ESL ID格式不正确，期望格式: CODE-MAC，实际: {}", yaliangEslId);
                return Math.abs(yaliangEslId.hashCode());
            }
        } catch (Exception e) {
            log.error("雅量ESL ID转换失败: {}", yaliangEslId, e);
            return Math.abs(yaliangEslId.hashCode());
        }
    }
    
    /**
     * 转换攀攀ESL ID格式 (标准十六进制)
     */
    private long convertPandaEslIdToDecimal(String pandaEslId) {
        try {
            // 移除可能的前缀和空格
            String cleanHexId = pandaEslId;
            
            // 如果包含0x前缀，移除它
            if (cleanHexId.startsWith("0X")) {
                cleanHexId = cleanHexId.substring(2);
            }
            
            // 将十六进制字符串转换为Long
            Long result = Long.parseUnsignedLong(cleanHexId, 16);
            log.debug("攀攀ESL ID转换成功: {} -> {}", pandaEslId, result);
            return result;
            
        } catch (NumberFormatException e) {
            log.error("攀攀ESL ID转换失败，无效的十六进制格式: {}", pandaEslId, e);
            // 如果转换失败，尝试提取数字部分
            try {
                String numericPart = pandaEslId.replaceAll("[^0-9A-Fa-f]", "");
                if (!numericPart.isEmpty()) {
                    Long result = Long.parseUnsignedLong(numericPart, 16);
                    log.warn("使用提取的数字部分进行转换: {} -> {}", numericPart, result);
                    return result;
                }
            } catch (Exception ex) {
                log.error("提取数字部分转换也失败", ex);
            }
            
            // 最后的备用方案：返回hashCode的绝对值
            long fallback = Math.abs(pandaEslId.hashCode());
            log.warn("使用hashCode作为备用方案: {} -> {}", pandaEslId, fallback);
            return fallback;
        }
    }
    
    /**
     * 获取模板名称 - 严格按照标准格式要求
     * 根据ESL ID获取对应的模板名称，参考原始代码实现
     */
    private String getTemplateName(String eslId) {
        log.info("开始获取模板名称，ESL ID: {}", eslId);
        
        try {
            // 通过ESL ID获取模板信息
            PrintTemplateDesignWithBLOBs template = dataService.getTemplateByEslId(eslId);
            if (template != null && template.getContent() != null && !template.getContent().trim().isEmpty()) {
                log.info("找到模板数据，模板ID: {}, 模板名称: {}", 
                        template.getId(), template.getName());
                
                // 从CONTENT字段解析模板名称
                String templateName = extractTemplateNameFromContent(template.getContent());
                if (templateName != null && !templateName.trim().isEmpty()) {
                    log.info("从CONTENT字段解析到模板名称: {}", templateName);
                    return templateName;
                }
            }
            
            // 如果CONTENT解析失败，尝试使用NAME字段
            if (template != null && template.getName() != null && !template.getName().trim().isEmpty()) {
                String templateName = template.getName().trim();
                log.info("使用模板NAME字段作为模板名称: {}", templateName);
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
     * 根据ESL ID获取model值（屏幕类型对应的标识）
     */
    private String getModelByEslId(String eslId) {
        log.info("开始获取model值，ESL ID: {}", eslId);
        
        try {
            // 通过ESL ID获取模板信息
            PrintTemplateDesignWithBLOBs template = dataService.getTemplateByEslId(eslId);
            if (template != null) {
                log.info("找到模板数据，模板ID: {}, CATEGORY: {}", 
                        template.getId(), template.getCategory());
                
                // 从模板中提取屏幕类型
                String screenType = extractScreenTypeFromTemplate(template);
                if (screenType != null && !screenType.trim().isEmpty()) {
                    log.info("从模板中解析到屏幕类型: {}", screenType);
                    // 根据屏幕类型获取对应的TagType
                    String tagType = getTagType(screenType.trim().toLowerCase());
                    log.info("屏幕类型 {} 转换为model: {}", screenType, tagType);
                    return tagType;
                }
            } else {
                log.warn("未找到模板数据，ESL ID: {}", eslId);
            }
            
            log.warn("未找到ESL ID对应的屏幕类型: {}, 使用默认model值: 06", eslId);
            return "06"; // 默认model值
            
        } catch (Exception e) {
            log.error("获取model值时发生异常: ESL ID={}", eslId, e);
            return "06"; // 异常时返回默认model值
        }
    }
    
    /**
     * 从CONTENT字段提取模板名称
     */
    private String extractTemplateNameFromContent(String content) {
        log.info("开始从CONTENT字段解析模板名称");
        
        if (content == null || content.trim().isEmpty()) {
            log.warn("CONTENT字段为空");
            return null;
        }
        
        try {
            log.info("CONTENT字段内容长度: {} 字符", content.length());
            log.debug("CONTENT字段内容: {}", content.substring(0, Math.min(content.length(), 500)) + "...");
            
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode contentData = objectMapper.readTree(content);
            
            // 优先检查 panels[0].name
            JsonNode panels = contentData.get("panels");
            if (panels != null && panels.isArray() && panels.size() > 0) {
                JsonNode firstPanel = panels.get(0);
                JsonNode nameNode = firstPanel.get("name");
                if (nameNode != null && !nameNode.asText().trim().isEmpty()) {
                    String name = nameNode.asText().trim();
                    log.info("从panels[0].name提取到模板名称: {}", name);
                    return name;
                }
                log.info("panels[0].name字段为空或不存在");
            } else {
                log.info("panels数组为空或不存在");
            }
            
            // 其次检查根级别的name字段
            JsonNode nameNode = contentData.get("name");
            if (nameNode != null && !nameNode.asText().trim().isEmpty()) {
                String name = nameNode.asText().trim();
                log.info("从根级别name字段提取到模板名称: {}", name);
                return name;
            } else {
                log.info("根级别name字段为空或不存在");
            }
            
            // 最后检查designConfig.name
            JsonNode designConfig = contentData.get("designConfig");
            if (designConfig != null) {
                JsonNode designNameNode = designConfig.get("name");
                if (designNameNode != null && !designNameNode.asText().trim().isEmpty()) {
                    String name = designNameNode.asText().trim();
                    log.info("从designConfig.name提取到模板名称: {}", name);
                    return name;
                }
            } else {
                log.info("designConfig字段为空或不存在");
            }
            
            log.warn("CONTENT字段中未找到有效的模板名称");
            return null;
            
        } catch (Exception e) {
            log.error("解析CONTENT字段时发生异常，内容: {}", content, e);
            return null;
        }
    }
    
    /**
     * 从模板中提取屏幕类型
     */
    private String extractScreenTypeFromTemplate(PrintTemplateDesignWithBLOBs template) {
        // 优先从CONTENT字段的panels[0].eslConfig.screenType中提取
        if (template.getContent() != null && !template.getContent().trim().isEmpty()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode contentData = objectMapper.readTree(template.getContent());
                
                JsonNode panels = contentData.get("panels");
                if (panels != null && panels.isArray() && panels.size() > 0) {
                    JsonNode firstPanel = panels.get(0);
                    JsonNode eslConfig = firstPanel.get("eslConfig");
                    if (eslConfig != null) {
                        JsonNode screenTypeNode = eslConfig.get("screenType");
                        if (screenTypeNode != null && !screenTypeNode.asText().trim().isEmpty()) {
                            String screenType = screenTypeNode.asText().trim();
                            log.debug("从CONTENT的panels[0].eslConfig.screenType提取到屏幕类型: {}", screenType);
                            return screenType;
                        }
                    }
                }
            } catch (Exception e) {
                log.warn("从CONTENT字段提取屏幕类型失败", e);
            }
        }
        
        // 其次从EXT_JSON中提取
        if (template.getExtJson() != null && !template.getExtJson().trim().isEmpty()) {
            String screenType = extractScreenTypeFromExtJson(template.getExtJson());
            if (screenType != null && !screenType.trim().isEmpty()) {
                log.debug("从EXT_JSON提取到屏幕类型: {}", screenType);
                return screenType;
            }
        }
        
        // 最后从CATEGORY字段获取
        if (template.getCategory() != null && !template.getCategory().trim().isEmpty()) {
            String category = template.getCategory().trim();
            log.debug("从CATEGORY字段获取到屏幕类型: {}", category);
            return category;
        }
        
        log.warn("未能从模板中提取到屏幕类型，使用默认值: 2.13T");
        return "2.13T"; // 默认屏幕类型
    }
    
    /**
     * 从EXT_JSON中提取屏幕类型
     */
    private String extractScreenTypeFromExtJson(String extJson) {
        if (extJson == null || extJson.trim().isEmpty()) {
            return null;
        }
        
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode extData = objectMapper.readTree(extJson);
            
            JsonNode designConfig = extData.get("designConfig");
            if (designConfig != null) {
                JsonNode panels = designConfig.get("panels");
                if (panels != null && panels.isArray() && panels.size() > 0) {
                    JsonNode firstPanel = panels.get(0);
                    JsonNode eslConfig = firstPanel.get("eslConfig");
                    if (eslConfig != null) {
                        JsonNode screenTypeNode = eslConfig.get("screenType");
                        if (screenTypeNode != null && !screenTypeNode.asText().trim().isEmpty()) {
                            return screenTypeNode.asText().trim();
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.warn("从EXT_JSON提取屏幕类型失败", e);
        }
        
        return null;
    }
    
    /**
     * 根据屏幕类型获取对应的TagType
     */
    private String getTagType(String screenType) {
        log.info("开始转换屏幕类型到model，输入屏幕类型: {}", screenType);
        
        if (screenType == null || screenType.trim().isEmpty()) {
            log.warn("屏幕类型为空，使用默认model: 06");
            return "06"; // 默认返回2.13T对应的TagType
        }
        
        String normalizedType = screenType.toLowerCase().trim();
        log.info("标准化后的屏幕类型: {}", normalizedType);
        
        String result;
        switch (normalizedType) {
            case "2.13t":
            case "2.13":
                result = "06";
                break;
            case "2.13f":
                result = "07";
                break;
            case "2.66t":
                result = "0B";
                break;
            case "2.66f":
                result = "0C";
                break;
            case "4.20t":
            case "4.20":
                result = "1C";
                break;
            case "4.20f":
                result = "1D";
                break;
            case "2.90t":
            case "2.90":
                result = "0A";
                break;
            case "1.54t":
            case "1.54":
                result = "02";
                break;
            case "7.50t":
            case "7.50":
                result = "1E";
                break;
            default:
                log.warn("未知的屏幕类型: {}, 使用默认TagType: 06", screenType);
                result = "06";
                break;
        }
        
        log.info("屏幕类型转换完成: {} -> {}", screenType, result);
        return result;
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