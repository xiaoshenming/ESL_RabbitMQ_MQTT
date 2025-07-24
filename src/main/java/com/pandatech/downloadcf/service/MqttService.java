package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.Message;
import com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MqttService {

    @Value("${app.template.base-url:http://localhost:8999/api/res/templ/loadtemple}")
    private String templateBaseUrl;

    private final ObjectMapper objectMapper;
    private final DataService dataService;
    
    @Qualifier("mqttOutboundChannel")
    private final MessageChannel mqttOutboundChannel;

    @ServiceActivator(inputChannel = "mqttInputChannel")
    @SuppressWarnings("unchecked")
    public void handleMessage(@Header(MqttHeaders.RECEIVED_TOPIC) String topic, String payload) {
        log.info("接收到MQTT消息 - 主题: {}, 内容: {}", topic, payload);
        // 注意：MQTT消息处理功能需要根据新数据库结构重新实现
        log.warn("MQTT消息处理功能暂时禁用，需要根据新数据库结构重新实现");
    }

    /**
     * 优化和去重Items，解决重复元素和位置冲突问题
     */
    private List<Map<String, Object>> optimizeAndDeduplicateItems(List<Map<String, Object>> items) {
        if (items == null || items.isEmpty()) {
            return items;
        }
        
        List<Map<String, Object>> optimizedItems = new ArrayList<>();
        Set<String> processedKeys = new HashSet<>();
        
        log.debug("开始优化Items，原始数量: {}", items.size());
        
        for (int i = 0; i < items.size(); i++) {
            Map<String, Object> item = items.get(i);
            
            // 生成唯一标识符，用于去重
            String uniqueKey = generateItemUniqueKey(item);
            
            // 检查是否已经处理过相同的元素
            if (processedKeys.contains(uniqueKey)) {
                log.debug("发现重复元素，跳过: DataKey={}, Location={}", 
                         item.get("DataKey"), item.get("Location"));
                continue;
            }
            
            // 检查位置是否合理
            if (!isValidItemPosition(item)) {
                log.warn("发现位置异常的元素，尝试修复: DataKey={}, Location={}, Size={}", 
                        item.get("DataKey"), item.get("Location"), item.get("Size"));
                
                // 尝试修复位置
                item = fixItemPosition(item, i);
                if (item == null) {
                    log.warn("无法修复位置异常的元素，跳过");
                    continue;
                }
            }
            
            // 检查尺寸是否合理
            if (!isValidItemSize(item)) {
                log.warn("发现尺寸异常的元素，尝试修复: DataKey={}, Size={}", 
                        item.get("DataKey"), item.get("Size"));
                
                item = fixItemSize(item);
                if (item == null) {
                    log.warn("无法修复尺寸异常的元素，跳过");
                    continue;
                }
            }
            
            processedKeys.add(uniqueKey);
            optimizedItems.add(item);
            
            log.debug("保留元素: DataKey={}, Type={}, Location={}, Size={}", 
                     item.get("DataKey"), item.get("Type"), item.get("Location"), item.get("Size"));
        }
        
        log.info("Items优化完成，原始数量: {}, 优化后数量: {}", items.size(), optimizedItems.size());
        return optimizedItems;
    }
    
    /**
     * 生成元素的唯一标识符
     */
    private String generateItemUniqueKey(Map<String, Object> item) {
        String dataKey = String.valueOf(item.get("DataKey"));
        String type = String.valueOf(item.get("Type"));
        String location = String.valueOf(item.get("Location"));
        
        // 对于相同DataKey的元素，如果位置完全相同，认为是重复的
        return dataKey + "|" + type + "|" + location;
    }
    
    /**
     * 检查元素位置是否合理
     */
    private boolean isValidItemPosition(Map<String, Object> item) {
        try {
            int x = (Integer) item.get("x");
            int y = (Integer) item.get("y");
            
            // 位置不能为负数，且不能超出合理范围
            return x >= 0 && y >= 0 && x < 10000 && y < 10000;
        } catch (Exception e) {
            log.warn("检查元素位置时出错: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * 检查元素尺寸是否合理
     */
    private boolean isValidItemSize(Map<String, Object> item) {
        try {
            int width = (Integer) item.get("width");
            int height = (Integer) item.get("height");
            
            // 尺寸必须大于0，且不能过大
            return width > 0 && height > 0 && width <= 1000 && height <= 1000;
        } catch (Exception e) {
            log.warn("检查元素尺寸时出错: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * 修复元素位置
     */
    private Map<String, Object> fixItemPosition(Map<String, Object> item, int index) {
        try {
            int x = (Integer) item.get("x");
            int y = (Integer) item.get("y");
            
            // 如果位置异常，根据索引重新分配位置
            if (x < 0 || y < 0 || x >= 10000 || y >= 10000) {
                // 简单的网格布局：每行放4个元素
                int newX = (index % 4) * 100;
                int newY = (index / 4) * 30;
                
                item.put("x", newX);
                item.put("y", newY);
                item.put("Location", newX + ", " + newY);
                
                log.debug("修复元素位置: 原位置({}, {}), 新位置({}, {})", x, y, newX, newY);
            }
            
            return item;
        } catch (Exception e) {
            log.error("修复元素位置时出错: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * 修复元素尺寸
     */
    private Map<String, Object> fixItemSize(Map<String, Object> item) {
        try {
            int width = (Integer) item.get("width");
            int height = (Integer) item.get("height");
            
            // 如果尺寸异常，设置合理的默认值
            if (width <= 0 || width > 1000) {
                width = 80; // 默认宽度
            }
            if (height <= 0 || height > 1000) {
                height = 20; // 默认高度
            }
            
            // 特殊处理不同类型的元素
            String type = String.valueOf(item.get("Type"));
            if ("qrcode".equals(type)) {
                // 二维码应该是正方形，且有最小尺寸要求
                int size = Math.max(width, height);
                size = Math.max(size, 30); // 最小30像素
                width = height = size;
            } else if ("barcode".equals(type)) {
                // 条形码宽度应该大于高度
                width = Math.max(width, 60); // 最小宽度60像素
                height = Math.max(height, 15); // 最小高度15像素
            }
            
            item.put("width", width);
            item.put("height", height);
            item.put("Size", width + ", " + height);
            
            log.debug("修复元素尺寸: Type={}, 新尺寸={}x{}", type, width, height);
            
            return item;
        } catch (Exception e) {
            log.error("修复元素尺寸时出错: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 发送模板到MQTT - 优化版
     * 构建tmpllist格式的MQTT消息并发送
     */
    public void sendTemplateToMqtt(String shop, String templateId, String templateName) {
        try {
            log.info("开始发送模板到MQTT - 门店: {}, 模板ID: {}, 模板名称: {}", shop, templateId, templateName);
            
            // 验证参数
            if (shop == null || shop.trim().isEmpty()) {
                throw new IllegalArgumentException("门店编码不能为空");
            }
            if (templateId == null || templateId.trim().isEmpty()) {
                throw new IllegalArgumentException("模板ID不能为空");
            }
            
            // 从数据库获取真实的模板数据
            PrintTemplateDesignWithBLOBs template = dataService.getTemplateById(templateId);
            if (template == null) {
                log.error("未找到模板ID: {}", templateId);
                throw new RuntimeException("未找到指定的模板: " + templateId);
            }
            
            // 如果传入的模板名称为空，使用数据库中的名称
            String actualTemplateName = (templateName != null && !templateName.trim().isEmpty()) 
                ? templateName.trim() 
                : (template.getName() != null ? template.getName() : "2");
            
            log.info("使用模板名称: {}", actualTemplateName);

            String officialJson = convertToOfficialTemplate(template);
            String md5 = calculateMD5(officialJson);
            
            // 从官方模板JSON中提取TagType
            String tagType = extractTagTypeFromOfficialTemplate(officialJson);

            String topic = "esl/server/data/" + shop;
            String message = buildTmplListMessage(shop, templateId, actualTemplateName, md5, tagType);

            sendMqttMessage(topic, message.getBytes());

            log.info("模板消息发送成功 - 主题: {}, 模板ID: {}, 模板名称: {}, TagType: {}", topic, templateId, actualTemplateName, tagType);
        } catch (Exception e) {
            log.error("发送模板到MQTT失败: {}", e.getMessage(), e);
            throw new RuntimeException("MQTT发送失败: " + e.getMessage(), e);
        }
    }

    /**
     * 构建tmpllist格式消息 - 严格匹配用户指定结构
     */
    private String buildTmplListMessage(String shop, String templateId, String templateName, String md5, String tagType) throws JsonProcessingException {
        Map<String, Object> message = new HashMap<>();
        
        // 生成随机UUID作为消息ID（已经正确）
        String messageId = UUID.randomUUID().toString();
        // 生成完整的时间戳（避免科学计数法省略值）
        long currentTimeMillis = System.currentTimeMillis();
        double timestamp = currentTimeMillis / 1000.0;
        
        message.put("shop", shop);
        message.put("id", messageId);
        message.put("command", "tmpllist");
        message.put("timestamp", timestamp); // 使用完整时间戳

        Map<String, Object> data = new HashMap<>();
        data.put("url", templateBaseUrl);
        data.put("tid", UUID.randomUUID().toString()); // 使用随机UUID

        List<Map<String, String>> tmpls = new ArrayList<>();
        Map<String, String> tmpl = new HashMap<>();
        
        // 构建正确的文件名格式：{templateName}_{tagType}.json
        String fileName;
        if (tagType != null && !tagType.isEmpty()) {
            fileName = templateName + "_" + tagType + ".json";
        } else {
            // 如果TagType为空，使用默认值06
            fileName = templateName + "_06.json";
            log.warn("TagType为空，使用默认值06: templateId={}, templateName={}", templateId, templateName);
        }
        
        tmpl.put("name", fileName);
        tmpl.put("id", templateId);
        tmpl.put("md5", md5);
        tmpls.add(tmpl);
        data.put("tmpls", tmpls);

        message.put("data", data);
        
        log.debug("构建tmpllist消息: templateId={}, fileName={}, md5={}, 消息ID={}, 时间戳={}", 
                templateId, fileName, md5, messageId, timestamp);
        return objectMapper.writeValueAsString(message);
    }

    /**
     * 计算MD5值
     */
    private String calculateMD5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes("UTF-8"));
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            log.error("MD5计算失败", e);
            return UUID.randomUUID().toString();
        }
    }

    /**
     * 发送MQTT消息 (私有方法)
     */
    private void sendMqttMessage(String topic, byte[] payload) {
        Message<byte[]> message = MessageBuilder
                .withPayload(payload)
                .setHeader("mqtt_topic", topic)
                .build();
        mqttOutboundChannel.send(message);
    }
    
    /**
     * 发送MQTT消息 (公共方法)
     * @param topic MQTT主题
     * @param message 消息内容（String类型）
     * @return 发送是否成功
     */
    public boolean sendMqttMessage(String topic, String message) {
        try {
            if (topic == null || topic.trim().isEmpty()) {
                log.error("MQTT主题不能为空");
                return false;
            }
            if (message == null) {
                log.error("MQTT消息内容不能为null");
                return false;
            }
            
            log.debug("发送MQTT消息 - 主题: {}, 消息长度: {}", topic, message.length());
            sendMqttMessage(topic, message.getBytes("UTF-8"));
            return true;
            
        } catch (Exception e) {
            log.error("发送MQTT消息失败 - 主题: {}, 错误: {}", topic, e.getMessage(), e);
            return false;
        }
    }

    /**
     * 获取用户提供的模板内容（示例数据）
     */
    private String getUserProvidedTemplateContent() {
        // 用户提供的模板数据
        return "{\n  \"designConfig\": {\n    \"panels\": [\n      {\n        \"index\": 0,\n        \"name\": \"2\",\n        \"paperType\": \"CUSTOM\",\n        \"width\": 250,\n        \"height\": 122,\n        \"paperHeader\": 0,\n        \"paperFooter\": 345.82677165354335,\n        \"printElements\": [],\n        \"paperNumberContinue\": true,\n        \"eslConfig\": {\n          \"screenType\": \"2.13T\",\n          \"pixelWidth\": 250,\n          \"pixelHeight\": 122,\n          \"colorMode\": {\n            \"black\": true,\n            \"white\": true,\n            \"red\": true,\n            \"yellow\": false\n          },\n          \"orientation\": \"LANDSCAPE\"\n        }\n      }\n    ]\n  }\n}";
    }

    /**
     * 将自定义模板格式转换为官方格式
     * 支持两种输入格式：
     * 1. 已经是官方格式的JSON（如U_06.json）
     * 2. 自定义格式的JSON（如数据库中的panels格式）
     */
    public String convertToOfficialTemplate(PrintTemplateDesignWithBLOBs template) throws JsonProcessingException {
        // 添加详细的调试日志
        log.info("模板转换开始 - 模板ID: {}, 模板名称: {}", template.getId(), template.getName());
        
        String extJson = template.getExtJson();
        String content = template.getContent();
        
        log.info("EXT_JSON是否为null: {}", extJson == null);
        if (extJson != null) {
            log.info("EXT_JSON长度: {}", extJson.length());
            log.info("EXT_JSON前100个字符: {}", extJson.length() > 100 ? extJson.substring(0, 100) : extJson);
        }
        
        log.info("CONTENT是否为null: {}", content == null);
        if (content != null) {
            log.info("CONTENT长度: {}", content.length());
            log.info("CONTENT前100个字符: {}", content.length() > 100 ? content.substring(0, 100) : content);
        }
        
        String jsonToParse = extJson;

        // 如果EXT_JSON为空，则尝试使用CONTENT字段
        if (jsonToParse == null || jsonToParse.trim().isEmpty()) {
            log.info("EXT_JSON为空，尝试使用CONTENT字段");
            jsonToParse = content;
        }

        if (jsonToParse == null || jsonToParse.trim().isEmpty()) {
            log.warn("EXT_JSON和CONTENT都为空，使用默认模板");
            return createDefaultOfficialTemplate();
        }

        log.info("准备解析的JSON内容长度: {}", jsonToParse.length());
        log.info("准备解析的JSON内容前200个字符: {}", jsonToParse.substring(0, Math.min(200, jsonToParse.length())) + "...");

        try {
            JsonNode rootNode = objectMapper.readTree(jsonToParse);
            log.info("JSON解析成功，根节点字段: {}", rootNode.fieldNames());
            
            // 检查是否已经是官方格式（包含Items字段）
            if (rootNode.has("Items")) {
                log.debug("检测到官方格式模板，直接返回");
                // 确保FontFamily都是阿里普惠
                String result = ensureCorrectFontFamily(jsonToParse);
                return result;
            }
            
            // 检查是否是panels格式（自定义格式）
            if (rootNode.has("panels")) {
                log.debug("检测到panels格式模板，开始转换");
                String result = convertPanelsToOfficialFormat(rootNode, template);
                return result;
            }
            
            // 检查是否包含designConfig字段
            if (rootNode.has("designConfig")) {
                log.info("检测到designConfig格式模板，开始转换");
                String result = convertPanelsToOfficialFormat(rootNode, template);
                return result;
            }
            
            // 其他格式，尝试提取基本信息
            log.warn("未识别的模板格式，根节点包含字段: {}, 使用默认转换", rootNode.fieldNames());
            String result = createDefaultOfficialTemplate();
            return result;
            
        } catch (Exception e) {
            log.error("转换模板格式时发生错误，使用默认模板", e);
            String result = createDefaultOfficialTemplate();
            return result;
        }
    }

    /**
     * 确保官方格式模板中的FontFamily使用配置的字体
     */
    @SuppressWarnings("unchecked")
    private String ensureCorrectFontFamily(String officialJson) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(officialJson);
        Map<String, Object> result = objectMapper.convertValue(rootNode, Map.class);
        
        String configuredFontFamily = "阿里普惠";
        
        if (result.containsKey("Items") && result.get("Items") instanceof List) {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> items = (List<Map<String, Object>>) result.get("Items");
            
            for (Map<String, Object> item : items) {
                if (item.containsKey("FontFamily")) {
                    item.put("FontFamily", configuredFontFamily);
                }
            }
        }
        
        return objectMapper.writeValueAsString(result);
    }

    /**
     * 将panels格式转换为官方格式
     */
    private String convertPanelsToOfficialFormat(JsonNode rootNode, PrintTemplateDesignWithBLOBs template) throws JsonProcessingException {
        Map<String, Object> official = new HashMap<>();
        List<Map<String, Object>> items = new ArrayList<>();
        
        // 从CONTENT字段的JSON中提取模板名称，而不是直接使用NAME字段
        String templateName = extractTemplateNameFromContent(template);
        String screenType = extractScreenTypeFromTemplate(template);
        String tagType = getTagType(screenType);
        
        log.info("模板转换开始 - 模板名称: {}, 屏幕类型: {}, TagType: {}", templateName, screenType, tagType);
        
        official.put("Name", templateName); // 使用从CONTENT中解析的模板名称
        official.put("Version", 10);
        official.put("hext", "6");
        official.put("rgb", "3");
        official.put("wext", "0");
        
        // 默认尺寸，会被面板信息覆盖
        official.put("Size", "250, 122");
        official.put("height", "122");
        official.put("width", "250");
        
        official.put("TagType", tagType);
        
        log.info("模板转换 - 屏幕类型: {}, TagType: {}", screenType, tagType);
        
        // 检查是否是直接的panels格式
        JsonNode panels = null;
        if (rootNode.has("panels")) {
            panels = rootNode.get("panels");
            log.info("找到根级panels字段");
        } else if (rootNode.has("designConfig")) {
            JsonNode designConfig = rootNode.get("designConfig");
            if (designConfig.has("panels")) {
                panels = designConfig.get("panels");
                log.info("找到designConfig.panels字段");
            }
        }
        
        if (panels == null) {
            log.warn("模板JSON中缺少 'panels' 字段");
            official.put("Items", items);
            return objectMapper.writeValueAsString(official);
        }

        if (panels.isArray()) {
            for (JsonNode panel : panels) {
                // 提取面板的基本信息
                if (panel.has("width") && panel.has("height")) {
                    int width = panel.get("width").asInt();
                    int height = panel.get("height").asInt();
                    official.put("Size", width + ", " + height);
                    official.put("width", String.valueOf(width));
                    official.put("height", String.valueOf(height));
                    log.info("设置画布尺寸: {}x{}", width, height);
                }
                
                // 转换printElements
                if (panel.has("printElements")) {
                    JsonNode printElements = panel.get("printElements");
                    log.debug("找到printElements数组，元素数量: {}", printElements.size());
                    if (printElements.isArray()) {
                        for (int i = 0; i < printElements.size(); i++) {
                            JsonNode element = printElements.get(i);
                            log.debug("处理第{}个printElement: {}", i, element.toString());
                            Map<String, Object> item = convertPrintElementToItem(element, template);
                            if (item != null) {
                                items.add(item);
                                log.debug("成功转换第{}个printElement为Item", i);
                            } else {
                                log.warn("第{}个printElement转换失败", i);
                            }
                        }
                    }
                } else {
                    log.debug("面板中没有printElements字段");
                }
            }
        }
        
        log.info("模板转换完成，共生成{}个Items，最终TagType: {}", items.size(), tagType);
        
        // 优化和去重Items
        items = optimizeAndDeduplicateItems(items);
        log.info("优化后Items数量: {}", items.size());
        
        official.put("Items", items);
        return objectMapper.writeValueAsString(official);
    }
    
    /**
     * 从模板CONTENT字段的JSON中提取模板名称
     */
    @SuppressWarnings("unchecked")
    private String extractTemplateNameFromContent(PrintTemplateDesignWithBLOBs template) {
        if (template.getContent() == null || template.getContent().trim().isEmpty()) {
            log.warn("模板CONTENT字段为空，使用NAME字段作为模板名称: {}", template.getName());
            return template.getName() != null ? template.getName() : "template";
        }
        
        try {
            Map<String, Object> contentData = objectMapper.readValue(template.getContent(), Map.class);
            log.info("解析CONTENT字段成功，根级字段: {}", contentData.keySet());
            
            // 优先检查panels[0].name字段（这是实际的模板名称位置）
            if (contentData.containsKey("panels")) {
                Object panelsObj = contentData.get("panels");
                log.info("找到panels字段，类型: {}", panelsObj != null ? panelsObj.getClass().getSimpleName() : "null");
                if (panelsObj instanceof List) {
                    List<Map<String, Object>> panels = (List<Map<String, Object>>) panelsObj;
                    log.info("panels数组长度: {}", panels.size());
                    if (!panels.isEmpty()) {
                        Map<String, Object> firstPanel = panels.get(0);
                        log.info("第一个panel的字段: {}", firstPanel.keySet());
                        if (firstPanel.containsKey("name")) {
                            Object nameObj = firstPanel.get("name");
                            log.info("找到panels[0].name字段，值: {}, 类型: {}", nameObj, nameObj != null ? nameObj.getClass().getSimpleName() : "null");
                            if (nameObj instanceof String) {
                                String extractedName = (String) nameObj;
                                log.info("从CONTENT.panels[0].name字段提取模板名称: {}", extractedName);
                                return extractedName;
                            } else if (nameObj instanceof Number) {
                                String extractedName = nameObj.toString();
                                log.info("从CONTENT.panels[0].name字段提取模板名称（数字转字符串）: {}", extractedName);
                                return extractedName;
                            }
                        } else {
                            log.warn("panels[0]中没有name字段");
                        }
                    } else {
                        log.warn("panels数组为空");
                    }
                } else {
                    log.warn("panels字段不是数组类型");
                }
            } else {
                log.warn("CONTENT中没有panels字段");
            }
            
            // 检查根级别的name字段
            if (contentData.containsKey("name")) {
                Object nameObj = contentData.get("name");
                if (nameObj instanceof String) {
                    String extractedName = (String) nameObj;
                    log.info("从CONTENT根级name字段提取模板名称: {}", extractedName);
                    return extractedName;
                }
            }
            
            // 检查designConfig.name字段
            if (contentData.containsKey("designConfig")) {
                Object designConfigObj = contentData.get("designConfig");
                if (designConfigObj instanceof Map) {
                    Map<String, Object> designConfig = (Map<String, Object>) designConfigObj;
                    if (designConfig.containsKey("name")) {
                        Object nameObj = designConfig.get("name");
                        if (nameObj instanceof String) {
                            String extractedName = (String) nameObj;
                            log.info("从CONTENT.designConfig字段提取模板名称: {}", extractedName);
                            return extractedName;
                        }
                    }
                }
            }
            
            log.warn("CONTENT字段中未找到name字段，使用NAME字段作为模板名称: {}", template.getName());
            return template.getName() != null ? template.getName() : "template";
            
        } catch (Exception e) {
            log.warn("解析CONTENT字段失败: {}, 使用NAME字段作为模板名称: {}", e.getMessage(), template.getName());
            return template.getName() != null ? template.getName() : "template";
        }
    }
    
    /**
     * 从模板中提取屏幕类型
     */
    @SuppressWarnings("unchecked")
    private String extractScreenTypeFromTemplate(PrintTemplateDesignWithBLOBs template) {
        // 首先尝试从CONTENT字段的panels[0].eslConfig.screenType中提取
        if (template.getContent() != null && !template.getContent().trim().isEmpty()) {
            try {
                Map<String, Object> contentData = objectMapper.readValue(template.getContent(), Map.class);
                
                // 检查panels[0].eslConfig.screenType字段
                if (contentData.containsKey("panels")) {
                    Object panelsObj = contentData.get("panels");
                    if (panelsObj instanceof List) {
                        List<Map<String, Object>> panels = (List<Map<String, Object>>) panelsObj;
                        if (!panels.isEmpty()) {
                            Map<String, Object> firstPanel = panels.get(0);
                            if (firstPanel.containsKey("eslConfig")) {
                                Object eslConfigObj = firstPanel.get("eslConfig");
                                if (eslConfigObj instanceof Map) {
                                    Map<String, Object> eslConfig = (Map<String, Object>) eslConfigObj;
                                    if (eslConfig.containsKey("screenType")) {
                                        Object screenTypeObj = eslConfig.get("screenType");
                                        if (screenTypeObj instanceof String) {
                                            String screenType = (String) screenTypeObj;
                                            log.info("从CONTENT.panels[0].eslConfig.screenType字段提取屏幕类型: {}", screenType);
                                            return screenType;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.warn("解析CONTENT字段中的屏幕类型失败: {}", e.getMessage());
            }
        }
        
        // 其次尝试从EXT_JSON中提取
        String screenType = extractScreenTypeFromExtJson(template.getExtJson());
        if (screenType != null) {
            return screenType;
        }
        
        // 如果EXT_JSON中没有，尝试从CATEGORY字段获取
        if (template.getCategory() != null && !template.getCategory().trim().isEmpty()) {
            return template.getCategory();
        }
        
        // 默认返回2.13T
        return "2.13T";
    }
    
    /**
     * 从EXT_JSON中提取屏幕类型信息
     */
    @SuppressWarnings("unchecked")
    private String extractScreenTypeFromExtJson(String extJson) {
        if (extJson == null || extJson.trim().isEmpty()) {
            return null;
        }
        
        try {
            Map<String, Object> extData = objectMapper.readValue(extJson, Map.class);
            
            // 检查designConfig.panels[0].eslConfig.screenType
            if (extData.containsKey("designConfig")) {
                Object designConfigObj = extData.get("designConfig");
                if (designConfigObj instanceof Map) {
                    Map<String, Object> designConfig = (Map<String, Object>) designConfigObj;
                    if (designConfig.containsKey("panels")) {
                        Object panelsObj = designConfig.get("panels");
                        if (panelsObj instanceof List) {
                            List<Map<String, Object>> panels = (List<Map<String, Object>>) panelsObj;
                            if (!panels.isEmpty()) {
                                Map<String, Object> firstPanel = panels.get(0);
                                if (firstPanel.containsKey("eslConfig")) {
                                    Object eslConfigObj = firstPanel.get("eslConfig");
                                    if (eslConfigObj instanceof Map) {
                                        Map<String, Object> eslConfig = (Map<String, Object>) eslConfigObj;
                                        if (eslConfig.containsKey("screenType")) {
                                            Object screenTypeObj = eslConfig.get("screenType");
                                            if (screenTypeObj instanceof String) {
                                                return (String) screenTypeObj;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.warn("解析EXT_JSON失败: {}", e.getMessage());
        }
        
        return null;
    }

    /**
     * 将printElement转换为官方格式的Item
     */
    private Map<String, Object> convertPrintElementToItem(JsonNode element, PrintTemplateDesignWithBLOBs template) {
        log.debug("开始转换printElement: {}", element.toString());
        
        // 检查element是否为空
        if (element == null) {
            log.warn("printElement为null，跳过转换");
            return null;
        }
        
        // 元素必须包含options
        if (!element.has("options")) {
            log.warn("printElement缺少'options'字段，跳过转换: {}", element.toString());
            return null;
        }
        JsonNode options = element.get("options");
        log.debug("使用的options节点: {}", options.toString());

        Map<String, Object> item = new HashMap<>();

        // 根据 printElementType 设置 Type
        String elementType = "text"; // 默认类型
        if (element.has("printElementType") && element.get("printElementType").has("type")) {
            elementType = element.get("printElementType").get("type").asText("text");
        }
        item.put("Type", elementType);
        
        // 设置默认属性
        item.put("FontFamily", "阿里普惠");
        item.put("FontColor", "Black");
        item.put("Background", "Transparent");
        item.put("BorderColor", "Transparent");
        item.put("BorderStyle", 0);
        item.put("FontStyle", 0);
        item.put("FontSpace", 0);
        item.put("TextAlign", 0);
        item.put("DataKeyStyle", 0);
        
        // 从模板获取目标画布尺寸
        int canvasWidth = 400;  // 默认宽度
        int canvasHeight = 300; // 默认高度
        
        // 尝试从模板中获取实际画布尺寸
        if (template != null && template.getContent() != null) {
            try {
                JsonNode designConfig = objectMapper.readTree(template.getContent());
                if (designConfig.has("panels") && designConfig.get("panels").isArray() && 
                    designConfig.get("panels").size() > 0) {
                    JsonNode panel = designConfig.get("panels").get(0);
                    if (panel.has("width")) {
                        canvasWidth = panel.get("width").asInt(400);
                    }
                    if (panel.has("height")) {
                        canvasHeight = panel.get("height").asInt(300);
                    }
                }
            } catch (Exception e) {
                log.warn("无法解析模板设计配置，使用默认画布尺寸: {}x{}", canvasWidth, canvasHeight);
            }
        }
        
        log.debug("目标画布尺寸: {}x{}", canvasWidth, canvasHeight);
        
        // 分析原始设计画布尺寸
        // 从数据库数据可以看出，原始设计使用的是更大的坐标系统
        // 最大坐标约为: left=912, top=787.5, 加上元素尺寸后约为1032x813
        // 但实际设计画布应该是基于某个标准尺寸的坐标系统
        
        // 根据数据库中的实际坐标范围，推算原始设计画布尺寸
        // 观察到的坐标范围: left: 114-912, top: 265.5-787.5
        // 加上元素尺寸后的边界: right: 1032, bottom: 813
        // 考虑到设计时的边距，原始画布可能是 1200x900 或类似比例
        double originalCanvasWidth = 1200.0;   // 原始设计画布宽度
        double originalCanvasHeight = 900.0;   // 原始设计画布高度
        
        // 计算缩放比例 - 保持宽高比
        double scaleX = (double) canvasWidth / originalCanvasWidth;
        double scaleY = (double) canvasHeight / originalCanvasHeight;
        
        // 使用统一的缩放比例以保持元素比例
        double scale = Math.min(scaleX, scaleY);
        
        log.debug("坐标转换参数 - 原始画布: {}x{}, 目标画布: {}x{}, 缩放比例: {}", 
                 originalCanvasWidth, originalCanvasHeight, canvasWidth, canvasHeight, scale);
        
        // 获取原始坐标和尺寸
        double leftPt = options.has("left") ? options.get("left").asDouble() : 0;
        double topPt = options.has("top") ? options.get("top").asDouble() : 0;
        double widthPt = options.has("width") ? options.get("width").asDouble() : 50;
        double heightPt = options.has("height") ? options.get("height").asDouble() : 20;
        
        log.debug("原始坐标和尺寸 - left: {}, top: {}, width: {}, height: {}", 
                 leftPt, topPt, widthPt, heightPt);
        
        // 应用缩放转换
        int x = (int) Math.round(leftPt * scale);
        int y = (int) Math.round(topPt * scale);
        int width = (int) Math.round(widthPt * scale);
        int height = (int) Math.round(heightPt * scale);
        
        // 智能边界处理 - 避免过度压缩
        if (x < 0) {
            log.warn("元素X坐标小于0，调整为0。原始值: {}", x);
            x = 0;
        }
        if (y < 0) {
            log.warn("元素Y坐标小于0，调整为0。原始值: {}", y);
            y = 0;
        }
        
        // 如果元素完全超出画布，进行适当调整而不是强制压缩
        if (x >= canvasWidth) {
            log.warn("元素X坐标超出画布宽度，调整到边界内。原始值: {}, 画布宽度: {}", x, canvasWidth);
            x = Math.max(0, canvasWidth - width);
        }
        if (y >= canvasHeight) {
            log.warn("元素Y坐标超出画布高度，调整到边界内。原始值: {}, 画布高度: {}", y, canvasHeight);
            y = Math.max(0, canvasHeight - height);
        }
        
        // 如果元素尺寸超出画布，按比例缩小而不是截断
        if (x + width > canvasWidth) {
            int availableWidth = canvasWidth - x;
            if (availableWidth > 0) {
                width = availableWidth;
            } else {
                // 如果没有可用空间，重新计算位置和尺寸
                width = Math.min(width, canvasWidth);
                x = Math.max(0, canvasWidth - width);
            }
            log.debug("调整元素宽度以适应画布。新宽度: {}, X坐标: {}", width, x);
        }
        
        if (y + height > canvasHeight) {
            int availableHeight = canvasHeight - y;
            if (availableHeight > 0) {
                height = availableHeight;
            } else {
                // 如果没有可用空间，重新计算位置和尺寸
                height = Math.min(height, canvasHeight);
                y = Math.max(0, canvasHeight - height);
            }
            log.debug("调整元素高度以适应画布。新高度: {}, Y坐标: {}", height, y);
        }
        
        // 确保最小可见尺寸
        if (width < 1) {
            width = 1;
            log.debug("元素宽度过小，设置为最小值1");
        }
        if (height < 1) {
            height = 1;
            log.debug("元素高度过小，设置为最小值1");
        }
        
        // 检查是否为二维码或条形码（通过textType字段判断）
        String textType = null;
        if (options.has("textType")) {
            textType = options.get("textType").asText();
        }
        
        // 针对不同元素类型进行特殊处理
        if ("qrcode".equals(textType)) {
            // 二维码特殊处理
            item.put("Type", "qrcode");
            item.put("Background", "Transparent");
            item.put("BorderColor", "Transparent");
            item.put("BorderStyle", 0);
            log.debug("识别为二维码元素");
        } else if ("barcode".equals(textType)) {
            // 条形码特殊处理
            item.put("Type", "barcode");
            item.put("Background", "Transparent");
            item.put("BorderColor", "Transparent");
            item.put("BorderStyle", 0);
            
            // 条形码特有属性
            String barcodeType = "code128"; // 默认类型
            if (options.has("barcodeType")) {
                barcodeType = options.get("barcodeType").asText();
            } else if (options.has("barcodeMode")) {
                barcodeType = options.get("barcodeMode").asText();
            }
            item.put("Bartype", barcodeType);
            item.put("Barformat", 0);
            
            // 根据缩放后的高度动态设置条形码高度
            // 条形码高度应该是元素高度的一部分，留出空间给文字
            int barcodeHeight = Math.max(8, (int)(height * 0.7)); // 至少8像素，最多占元素高度的70%
            item.put("Barheight", barcodeHeight);
            
            item.put("Barwidth", 1);
            item.put("Showtext", 1);
            item.put("Fontinval", 1);
            log.debug("识别为条形码元素，类型: {}, 高度: {}", barcodeType, barcodeHeight);
        } else if ("rect".equals(elementType) || "oval".equals(elementType)) {
            // 图形元素设置边框样式
            item.put("BorderStyle", 1); // 显示边框
            item.put("BorderColor", "Black");
            item.put("Background", "Transparent");
        } else if ("line".equals(elementType) || "hline".equals(elementType) || "vline".equals(elementType)) {
            // 线条元素特殊处理
            item.put("Background", "Black"); // 线条背景设为黑色以显示线条
            item.put("BorderColor", "Transparent");
            item.put("BorderStyle", 0);
            
            // 根据线条类型调整尺寸
            if ("hline".equals(elementType)) {
                // 水平线：高度较小，宽度较大
                if (height > width / 10) {
                    height = Math.max(1, width / 20); // 高度设为宽度的1/20，最小为1
                }
            } else if ("vline".equals(elementType)) {
                // 垂直线：宽度较小，高度较大
                if (width > height / 10) {
                    width = Math.max(1, height / 20); // 宽度设为高度的1/20，最小为1
                }
            }
            // 对于普通line类型，保持原有尺寸比例
        }
        
        log.debug("坐标转换 - 原始: left={}, top={}, width={}, height={}", leftPt, topPt, widthPt, heightPt);
        log.debug("转换后: x={}, y={}, width={}, height={}, 画布: {}x{}", x, y, width, height, canvasWidth, canvasHeight);
        
        item.put("x", x);
        item.put("y", y);
        item.put("width", width);
        item.put("height", height);
        item.put("Location", x + ", " + y);
        item.put("Size", width + ", " + height);
        
        // 字体属性 - 根据缩放比例调整字体大小
        int fontSize = 12; // 默认字体大小
        if (options.has("fontSize")) {
            double originalFontSize = options.get("fontSize").asDouble();
            // 字体大小也需要根据缩放比例调整，但不要过小
            fontSize = Math.max(8, (int) Math.round(originalFontSize * scale));
        } else {
            // 如果没有指定字体大小，根据元素高度估算合适的字体大小
            fontSize = Math.max(8, Math.min(height - 4, 16)); // 字体大小不超过元素高度-4，最大16
        }
        item.put("FontSize", fontSize);
        
        log.debug("字体大小设置 - 原始: {}, 缩放后: {}, 元素高度: {}", 
                 options.has("fontSize") ? options.get("fontSize").asDouble() : "未指定", 
                 fontSize, height);
        
        if (options.has("fontWeight")) {
            String fontWeight = options.get("fontWeight").asText();
            item.put("FontStyle", "bold".equals(fontWeight) ? 1 : 0);
        }
        
        if (options.has("textAlign")) {
            String textAlign = options.get("textAlign").asText();
            int align = 0; // left
            if ("center".equals(textAlign)) align = 1;
            else if ("right".equals(textAlign)) align = 2;
            item.put("TextAlign", align);
        }
        
        // 数据绑定
        String templateField = null;
        if (options.has("templateField")) {
            templateField = options.get("templateField").asText();
            log.debug("找到字段: {}", templateField);
        } else {
            log.debug("未找到field字段");
        }
        
        item.put("DataKey", templateField != null ? templateField : "");
        
        if (options.has("testData")) {
            item.put("DataDefault", options.get("testData").asText());
        } else {
            item.put("DataDefault", "");
        }
        
        // 颜色属性
        if (options.has("color")) {
            item.put("FontColor", options.get("color").asText());
        }
        
        if (options.has("backgroundColor")) {
            item.put("Background", options.get("backgroundColor").asText());
        }
        
        log.debug("成功转换printElement为Item: {}", item);
        return item;
    }

    /**
     * 创建默认的官方格式模板
     */
    private String createDefaultOfficialTemplate() throws JsonProcessingException {
        Map<String, Object> template = new HashMap<>();
        template.put("Name", "template");
        template.put("Version", 10);
        template.put("hext", "6");
        template.put("rgb", "3");
        template.put("wext", "0");
        template.put("TagType", "06");
        template.put("Size", "250, 122");
        template.put("width", "250");
        template.put("height", "122");
        template.put("Items", new ArrayList<>());
        
        return objectMapper.writeValueAsString(template);
    }

    /**
     * 从官方模板JSON中提取TagType
     */
    private String extractTagTypeFromOfficialTemplate(String officialJson) {
        try {
            JsonNode rootNode = objectMapper.readTree(officialJson);
            if (rootNode.has("TagType")) {
                String tagType = rootNode.get("TagType").asText();
                log.debug("从官方模板中提取TagType: {}", tagType);
                return tagType;
            }
            log.warn("官方模板JSON中未找到TagType字段，使用默认值06");
            return "06";
        } catch (Exception e) {
            log.error("解析官方模板JSON失败，使用默认TagType: 06", e);
            return "06";
        }
    }

    /**
     * 根据屏幕类型获取TagType
     */
    private String getTagType(String screenType) {
        if (screenType == null) return "06";
        
        switch (screenType.toLowerCase()) {
            case "2.13t":
            case "2.13":
                return "06";
            case "1.54t":
            case "1.54":
                return "04";
            case "2.9t":
            case "2.9":
                return "08";
            case "4.2t":
            case "4.2":
                return "10";
            case "4.20t":
            case "4.20":
                return "1C";
            case "4.20f":
                return "1D";
            case "7.5t":
            case "7.5":
                return "1E";
            default:
                return "06";
        }
    }
}