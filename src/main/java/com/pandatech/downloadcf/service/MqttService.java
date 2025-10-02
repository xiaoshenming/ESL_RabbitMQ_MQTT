package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.Message;
import com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs;
import com.pandatech.downloadcf.util.ImageMagickProcessor;
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
    private final ImageMagickProcessor imageMagickProcessor;
    
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
                // 注意：不在这里设置Barheight，因为已经在convertPrintElementToItem中正确设置
                // 避免覆盖已经计算好的缩放后高度值
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
                .setHeader("mqtt_qos", 0)
                .setHeader("mqtt_retained", false)
                .build();
        
        boolean sent = mqttOutboundChannel.send(message);
        if (sent) {
            log.info("MQTT消息发送成功: topic={}, 消息大小={}字节", topic, payload.length);
        } else {
            log.error("MQTT消息发送失败: topic={}", topic);
        }
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
        // 使用LinkedHashMap保持字段顺序
        Map<String, Object> official = new LinkedHashMap<>();
        List<Map<String, Object>> items = new ArrayList<>();
        
        // 从CONTENT字段的JSON中提取模板名称，而不是直接使用NAME字段
        String templateName = extractTemplateNameFromContent(template);
        String screenType = extractScreenTypeFromTemplate(template);
        String tagType = getTagType(screenType);
        
        log.info("模板转换开始 - 模板名称: {}, 屏幕类型: {}, TagType: {}", templateName, screenType, tagType);
        
        // 🎯 根据TagType设置正确的hext值 - 修复2.13F问题
        String hextValue = getHextValue(tagType);
        
        // 从CONTENT字段中提取颜色模式
        String rgbMode = extractColorModeFromTemplate(template);
        
        // 按字母顺序添加字段（Items字段除外，它需要在最后处理）
        // 默认尺寸，会被面板信息覆盖
        official.put("Name", templateName); // 使用从CONTENT中解析的模板名称
        official.put("Size", "250, 122");
        official.put("TagType", tagType);
        official.put("Version", 10);
        official.put("height", "122");
        official.put("hext", hextValue);
        official.put("rgb", rgbMode);
        official.put("wext", "0");
        official.put("width", "250");
        
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
                int width = 250;  // 默认宽度
                int height = 122; // 默认高度
                if (panel.has("width") && panel.has("height")) {
                    width = panel.get("width").asInt();
                    height = panel.get("height").asInt();
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
                        // 🎯 智能动态尺寸调整算法 - 完美版本
                        // 第一步：精确分析所有元素的边界
                        double minLeft = Double.MAX_VALUE;
                        double minTop = Double.MAX_VALUE;
                        double maxRight = Double.MIN_VALUE;
                        double maxBottom = Double.MIN_VALUE;
                        
                        // 收集所有有效元素的边界信息
                        for (JsonNode elem : printElements) {
                            JsonNode opts = elem.get("options");
                            if (opts != null) {
                                double l = opts.has("left") ? opts.get("left").asDouble() : 0;
                                double t = opts.has("top") ? opts.get("top").asDouble() : 0;
                                double w = opts.has("width") ? opts.get("width").asDouble() : 0;
                                double h = opts.has("height") ? opts.get("height").asDouble() : 0;
                                
                                // 只考虑有实际尺寸的元素
                                if (w > 0 && h > 0) {
                                    minLeft = Math.min(minLeft, l);
                                    minTop = Math.min(minTop, t);
                                    maxRight = Math.max(maxRight, l + w);
                                    maxBottom = Math.max(maxBottom, t + h);
                                }
                            }
                        }
                        
                        // 如果没有找到有效元素，使用默认值
                        if (minLeft == Double.MAX_VALUE) {
                            minLeft = 0;
                            minTop = 0;
                            maxRight = width;
                            maxBottom = height;
                        }
                        
                        // 第二步：计算实际内容区域
                        double contentWidth = maxRight - minLeft;
                        double contentHeight = maxBottom - minTop;
                        
                        log.debug("📊 元素边界分析 - minLeft: {}, minTop: {}, maxRight: {}, maxBottom: {}", 
                                 minLeft, minTop, maxRight, maxBottom);
                        log.debug("📏 实际内容尺寸 - contentWidth: {}, contentHeight: {}", contentWidth, contentHeight);
                        log.debug("🎨 目标画布尺寸 - targetWidth: {}, targetHeight: {}", width, height);
                        
                        // 第三步：智能确定原始设计画布尺寸和坐标偏移
                        double originalCanvasWidth, originalCanvasHeight;
                        double offsetX = 0, offsetY = 0;
                        
                        // 检查内容是否超出声明的画布尺寸（允许10%的容差）
                        boolean contentExceedsCanvas = (maxRight > width * 1.1) || (maxBottom > height * 1.1);
                        
                        if (contentExceedsCanvas) {
                            // 🔥 情况1：内容超出画布 - 使用实际内容边界作为设计尺寸
                            originalCanvasWidth = contentWidth;
                            originalCanvasHeight = contentHeight;
                            // 设置偏移量，将内容左上角对齐到原点
                            offsetX = -minLeft;
                            offsetY = -minTop;
                            log.debug("🚀 检测到内容超出画布，使用实际内容尺寸作为原始画布");
                            log.debug("🎯 设置坐标偏移 - offsetX: {}, offsetY: {}", offsetX, offsetY);
                        } else {
                            // ✨ 情况2：内容在画布内 - 使用声明的画布尺寸，保持原有布局
                            originalCanvasWidth = width;
                            originalCanvasHeight = height;
                            offsetX = 0;
                            offsetY = 0;
                            log.debug("✅ 内容在画布范围内，使用声明的画布尺寸，保持原有布局");
                        }
                        
                        // 第四步：安全性检查，确保尺寸合理
                        if (originalCanvasWidth < 50) {
                            originalCanvasWidth = Math.max(contentWidth, 100);
                            log.debug("⚠️ 原始画布宽度过小，调整为: {}", originalCanvasWidth);
                        }
                        if (originalCanvasHeight < 30) {
                            originalCanvasHeight = Math.max(contentHeight, 60);
                            log.debug("⚠️ 原始画布高度过小，调整为: {}", originalCanvasHeight);
                        }
                        
                        log.debug("🎪 最终转换参数 - 原始画布: {}x{}, 目标画布: {}x{}, 坐标偏移: ({}, {})", 
                                 originalCanvasWidth, originalCanvasHeight, width, height, offsetX, offsetY);
                        // 现在转换元素
                        for (int i = 0; i < printElements.size(); i++) {
                            JsonNode element = printElements.get(i);
                            log.debug("处理第{}个printElement: {}", i, element.toString());
                            Map<String, Object> item = convertPrintElementToItem(element, template, originalCanvasWidth, originalCanvasHeight, width, height, offsetX, offsetY);
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
        
        // 🎯 为图片元素生成dval字段 - 针对2.13F优化
        // 使用之前从模板中提取的rgbMode值
        for (Map<String, Object> item : items) {
            if ("pic".equals(item.get("Type"))) {
                String dataDefault = (String) item.get("DataDefault");
                if (dataDefault != null && !dataDefault.trim().isEmpty()) {
                    int width = (Integer) item.get("width");
                    int height = (Integer) item.get("height");
                    
                    // 针对2.13F模板，使用简化的图片处理
                    if ("07".equals(tagType)) {
                        String dval = generateSimplifiedDval(dataDefault, rgbMode, width, height);
                        item.put("dval", dval);
                        log.debug("为2.13F图片元素生成简化dval，尺寸: {}x{}, dval长度: {}", width, height, dval.length());
                    } else {
                        String dval = generateDval(dataDefault, rgbMode, width, height);
                        item.put("dval", dval);
                        log.debug("为图片元素生成标准dval，尺寸: {}x{}, dval长度: {}", width, height, dval.length());
                    }
                } else {
                    // 如果没有图片数据，设置空的dval
                    item.put("dval", "");
                    log.debug("图片元素无数据，设置空dval");
                }
            }
        }
        
        // 按字母顺序插入Items字段（在height之前）
        Map<String, Object> finalOfficial = new LinkedHashMap<>();
        finalOfficial.put("Items", items);
        finalOfficial.put("Name", official.get("Name"));
        finalOfficial.put("Size", official.get("Size"));
        finalOfficial.put("TagType", official.get("TagType"));
        finalOfficial.put("Version", official.get("Version"));
        finalOfficial.put("height", official.get("height"));
        finalOfficial.put("hext", official.get("hext"));
        finalOfficial.put("rgb", official.get("rgb"));
        finalOfficial.put("wext", official.get("wext"));
        finalOfficial.put("width", official.get("width"));
        
        return objectMapper.writeValueAsString(finalOfficial);
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
     * 从模板中提取颜色模式并返回对应的rgb值
     */
    @SuppressWarnings("unchecked")
    private String extractColorModeFromTemplate(PrintTemplateDesignWithBLOBs template) {
        // 首先尝试从CONTENT字段的panels[0].eslConfig.colorMode中提取
        if (template.getContent() != null && !template.getContent().trim().isEmpty()) {
            try {
                Map<String, Object> contentData = objectMapper.readValue(template.getContent(), Map.class);
                
                // 检查panels[0].eslConfig.colorMode字段
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
                                    if (eslConfig.containsKey("colorMode")) {
                                        Object colorModeObj = eslConfig.get("colorMode");
                                        if (colorModeObj instanceof Map) {
                                            Map<String, Object> colorMode = (Map<String, Object>) colorModeObj;
                                            
                                            // 统计启用的颜色数量
                                            int colorCount = 0;
                                            
                                            // 检查各种颜色是否启用
                                            if (colorMode.containsKey("black") && Boolean.TRUE.equals(colorMode.get("black"))) {
                                                colorCount++;
                                            }
                                            if (colorMode.containsKey("white") && Boolean.TRUE.equals(colorMode.get("white"))) {
                                                colorCount++;
                                            }
                                            if (colorMode.containsKey("red") && Boolean.TRUE.equals(colorMode.get("red"))) {
                                                colorCount++;
                                            }
                                            if (colorMode.containsKey("yellow") && Boolean.TRUE.equals(colorMode.get("yellow"))) {
                                                colorCount++;
                                            }
                                            
                                            // 根据颜色数量返回对应的rgb值
                                            String rgbValue;
                                            if (colorCount >= 4) {
                                                rgbValue = "4";
                                            } else if (colorCount >= 3) {
                                                rgbValue = "3";
                                            } else if (colorCount >= 2) {
                                                rgbValue = "2";
                                            } else {
                                                rgbValue = "3"; // 默认值
                                            }
                                            
                                            log.info("从CONTENT.panels[0].eslConfig.colorMode字段提取颜色模式: 启用颜色数={}, rgb值={}", colorCount, rgbValue);
                                            return rgbValue;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.warn("解析CONTENT字段中的颜色模式失败: {}", e.getMessage());
            }
        }
        
        log.info("未找到颜色模式配置，使用默认rgb值: 3");
        return "3"; // 默认值为3色
    }

    /**
     * 🎯 将printElement转换为官方格式的Item - 完美版本
     * 支持智能动态尺寸调整和坐标偏移
     */
    private Map<String, Object> convertPrintElementToItem(JsonNode element, PrintTemplateDesignWithBLOBs template, 
            double originalCanvasWidth, double originalCanvasHeight, int canvasWidth, int canvasHeight, 
            double offsetX, double offsetY) {
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
        
        // 设置通用默认属性
        item.put("Background", "Transparent");
        item.put("BorderColor", "Transparent");
        item.put("BorderStyle", 0);
        item.put("DataKeyStyle", 0);
        
        // 只有非图片元素才设置文本相关属性
        if (!"image".equals(elementType)) {
            item.put("FontFamily", "阿里普惠");
            item.put("FontColor", "Black");
            item.put("FontStyle", 0);
            item.put("FontSpace", 0);
            item.put("TextAlign", 0);
        }
        
        // 🎯 完美的动态坐标转换算法
        log.debug("🎨 转换参数 - 原始画布: {}x{}, 目标画布: {}x{}, 坐标偏移: ({}, {})", 
                 originalCanvasWidth, originalCanvasHeight, canvasWidth, canvasHeight, offsetX, offsetY);
        
        // 第一步：计算缩放比例
        double scaleX = (double) canvasWidth / originalCanvasWidth;
        double scaleY = (double) canvasHeight / originalCanvasHeight;
        
        // 使用等比例缩放以保持元素比例，选择较小的缩放比例确保内容完全适应画布
        double scale = Math.min(scaleX, scaleY);
        
        // 第二步：计算缩放后的画布尺寸和居中偏移
        double scaledWidth = originalCanvasWidth * scale;
        double scaledHeight = originalCanvasHeight * scale;
        double centerOffsetX = (canvasWidth - scaledWidth) / 2.0;
        double centerOffsetY = (canvasHeight - scaledHeight) / 2.0;
        
        log.debug("📐 缩放参数 - scaleX: {}, scaleY: {}, 最终scale: {}", scaleX, scaleY, scale);
        log.debug("🎪 居中参数 - 缩放后尺寸: {}x{}, 居中偏移: ({}, {})", 
                 scaledWidth, scaledHeight, centerOffsetX, centerOffsetY);
        
        // 第三步：获取原始元素坐标和尺寸
        double leftPt = options.has("left") ? options.get("left").asDouble() : 0;
        double topPt = options.has("top") ? options.get("top").asDouble() : 0;
        double widthPt = options.has("width") ? options.get("width").asDouble() : 50;
        double heightPt = options.has("height") ? options.get("height").asDouble() : 20;
        
        log.debug("📍 原始元素 - left: {}, top: {}, width: {}, height: {}", 
                 leftPt, topPt, widthPt, heightPt);
        
        // 第四步：应用完整的坐标变换
        // 变换顺序：1.坐标偏移 -> 2.缩放 -> 3.居中偏移
        int x = (int) Math.round((leftPt + offsetX) * scale + centerOffsetX);
        int y = (int) Math.round((topPt + offsetY) * scale + centerOffsetY);
        int width = (int) Math.round(widthPt * scale);
        int height = (int) Math.round(heightPt * scale);
        
        // 🛡️ 智能边界处理 - 保持比例，避免过度压缩
        log.debug("🔍 变换后坐标 - x: {}, y: {}, width: {}, height: {}", x, y, width, height);
        
        // 第五步：温和的边界调整，优先保持元素可读性
        boolean needsAdjustment = false;
        
        // 检查是否需要调整
        if (x < 0 || y < 0 || x + width > canvasWidth || y + height > canvasHeight) {
            needsAdjustment = true;
            log.debug("⚠️ 元素超出画布边界，需要调整");
        }
        
        if (needsAdjustment) {
            // 保守的边界调整策略
            if (x < 0) {
                log.debug("🔧 X坐标调整: {} -> 0", x);
                x = 0;
            }
            if (y < 0) {
                log.debug("🔧 Y坐标调整: {} -> 0", y);
                y = 0;
            }
            
            // 智能尺寸调整 - 保持最小可读尺寸
            int minWidth = Math.max(1, (int)(widthPt * scale * 0.3)); // 保持原尺寸的30%
            int minHeight = Math.max(1, (int)(heightPt * scale * 0.3)); // 保持原尺寸的30%
            
            if (x + width > canvasWidth) {
                int newWidth = canvasWidth - x;
                width = Math.max(newWidth, minWidth);
                if (width > newWidth) {
                    // 如果最小宽度仍然超出，重新计算位置
                    x = Math.max(0, canvasWidth - width);
                }
                log.debug("🔧 宽度调整: 新宽度={}, X坐标={}", width, x);
            }
            
            if (y + height > canvasHeight) {
                int newHeight = canvasHeight - y;
                height = Math.max(newHeight, minHeight);
                if (height > newHeight) {
                    // 如果最小高度仍然超出，重新计算位置
                    y = Math.max(0, canvasHeight - height);
                }
                log.debug("🔧 高度调整: 新高度={}, Y坐标={}", height, y);
            }
        }
        
        // 最终安全检查 - 确保元素在画布范围内
        x = Math.max(0, Math.min(x, canvasWidth - 1));
        y = Math.max(0, Math.min(y, canvasHeight - 1));
        width = Math.max(1, Math.min(width, canvasWidth - x));
        height = Math.max(1, Math.min(height, canvasHeight - y));
        
        log.debug("✅ 最终坐标 - x: {}, y: {}, width: {}, height: {}", x, y, width, height);
        
        // 检查是否为二维码或条形码（通过textType字段判断）
        String textType = null;
        if (options.has("textType")) {
            textType = options.get("textType").asText();
        }
        
        // 针对不同元素类型进行特殊处理
        if ("image".equals(elementType)) {
            // 图片元素特殊处理 - 根据AP手动修复后的正确格式
            item.put("Type", "pic");
            
            // 图片特有属性
            item.put("Imgdeal", 0);
            item.put("Imgfill", 0);
            item.put("Imgtype", "png");
            
            // 生成dval字段 - 将在后续处理DataDefault时生成
            item.put("dval", "");
            
            log.debug("识别为图片元素，使用pic类型");
        } else if ("qrcode".equals(textType)) {
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
            
            // 🎯 修复条形码高度计算 - 基于成功案例的分析
            // 条形码高度应该是元素高度的50%左右，以确保文本和条形码都能正确显示
            double barcodeHeight = height * 0.5; // 使用元素高度的50%作为条形码高度
            item.put("Barheight", barcodeHeight);
            
            // 使用模板中定义的条形码宽度，而不是硬编码
            item.put("Barwidth", width);
            item.put("Showtext", 1);
            item.put("Fontinval", 1);
            log.debug("识别为条形码元素，类型: {}, 高度: {}", barcodeType, height);
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
        
        // 字体属性处理
        int fontSize = 12; // 默认字体大小
        if (options.has("fontSize")) {
            double originalFontSize = options.get("fontSize").asDouble();
            // 保持原始字体大小，不进行缩放，让AP自己处理显示
            fontSize = Math.max(8, (int) Math.round(originalFontSize));
        } else {
            // 如果没有指定字体大小，根据元素高度估算合适的字体大小
            fontSize = Math.max(8, Math.min(height - 4, 16));
        }
        
        // 字符间距处理
        int letterSpacing = 0; // 默认字符间距
        if (options.has("letterSpacing")) {
            double originalLetterSpacing = options.get("letterSpacing").asDouble();
            letterSpacing = Math.max(0, (int) Math.round(originalLetterSpacing));
        }
        
        log.debug("字体属性设置 - 原始字体大小: {}, 设置字体大小: {}, 原始字符间距: {}, 设置字符间距: {}", 
                 options.has("fontSize") ? options.get("fontSize").asDouble() : "未指定", 
                 fontSize, 
                 options.has("letterSpacing") ? options.get("letterSpacing").asDouble() : "未指定",
                 letterSpacing);
        
        // 字体样式处理 - 支持粗体、斜体、粗斜体
        int fontStyle = 0; // 默认正常
        if (options.has("fontWeight")) {
            String fontWeight = options.get("fontWeight").asText();
            if ("bold".equals(fontWeight) || "900".equals(fontWeight)) {
                fontStyle = 1; // 粗体
            }
        }
        
        // 文本对齐处理 - 支持左对齐、居中、右对齐、两端对齐
        int textAlign = 0; // 默认左对齐
        if (options.has("textAlign")) {
            String align = options.get("textAlign").asText();
            switch (align) {
                case "left":
                    textAlign = 0;
                    break;
                case "center":
                    textAlign = 1;
                    break;
                case "right":
                    textAlign = 2;
                    break;
                case "justify":
                    textAlign = 0; // AP中justify映射为左对齐
                    break;
                default:
                    textAlign = 0;
            }
        }
        
        // 只有非图片元素才设置字体相关属性
        if (!"image".equals(elementType)) {
            item.put("FontStyle", fontStyle);
            item.put("FontSpace", letterSpacing);
            item.put("TextAlign", textAlign);
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
        
        // 图片元素的DataDefault处理
        if ("pic".equals(item.get("Type"))) {
            // 对于单一图片组件（没有绑定字段但有src属性），使用src作为默认值
            if ((templateField == null || templateField.trim().isEmpty()) && options.has("src")) {
                String srcValue = options.get("src").asText();
                item.put("DataDefault", srcValue);
                log.debug("单一图片组件，使用src作为DataDefault: {}", srcValue);
            } else {
                // 有绑定字段的图片组件，DataDefault为空字符串
                item.put("DataDefault", "");
            }
        } else if (options.has("testData")) {
            item.put("DataDefault", options.get("testData").asText());
        } else {
            item.put("DataDefault", "");
        }
        
        // 颜色属性 - 图片元素不需要FontColor
        if (options.has("color") && !"pic".equals(item.get("Type"))) {
            item.put("FontColor", options.get("color").asText());
        }
        
        if (options.has("backgroundColor")) {
            item.put("Background", options.get("backgroundColor").asText());
        }
        
        // 边框处理 - 根据前端的边框设置转换为AP格式
        if (options.has("borderColor")) {
            item.put("BorderColor", options.get("borderColor").asText());
        }
        
        // 🎯 完美的边框样式处理 - 根据边框宽度动态转换
        int borderStyle = 0; // 默认无边框
        if (options.has("borderLeft") || options.has("borderTop") || 
            options.has("borderRight") || options.has("borderBottom")) {
            
            String borderType = "solid"; // 默认实线
            double borderWidth = 0.75; // 默认边框宽度(pt)
            
            // 获取边框类型
            if (options.has("borderLeft")) {
                borderType = options.get("borderLeft").asText();
            } else if (options.has("borderTop")) {
                borderType = options.get("borderTop").asText();
            }
            
            // 🎯 智能获取边框宽度 - 支持多种字段名和格式
            if (options.has("borderWidth")) {
                borderWidth = parseBorderWidth(options.get("borderWidth"));
            } else if (options.has("borderLeftWidth")) {
                borderWidth = parseBorderWidth(options.get("borderLeftWidth"));
            } else if (options.has("borderTopWidth")) {
                borderWidth = parseBorderWidth(options.get("borderTopWidth"));
            } else if (options.has("strokeWidth")) {
                borderWidth = parseBorderWidth(options.get("strokeWidth"));
            } else if (options.has("lineWidth")) {
                borderWidth = parseBorderWidth(options.get("lineWidth"));
            }
            
            // 🎯 根据边框宽度映射到BorderStyle值
            borderStyle = convertBorderWidthToStyle(borderWidth, borderType);
            
            log.debug("🖼️ 边框转换 - 类型: {}, 宽度: {}pt -> BorderStyle: {}", borderType, borderWidth, borderStyle);
        }
        
        if (borderStyle > 0) {
            item.put("BorderStyle", borderStyle);
        }
        
        // 处理字段替换样式 - DataKeyStyle
        // 0: 纯替换, 1: 前缀, 2: 后缀
        int dataKeyStyle = 0; // 默认纯替换
        if (options.has("dataKeyStyle")) {
            String style = options.get("dataKeyStyle").asText();
            switch (style) {
                case "prefix":
                    dataKeyStyle = 1;
                    break;
                case "suffix":
                    dataKeyStyle = 2;
                    break;
                default:
                    dataKeyStyle = 0;
            }
        }
        item.put("DataKeyStyle", dataKeyStyle);
        
        // 处理价格类型元素的特殊属性
        if ("price".equals(elementType)) {
            item.put("Type", "price");
            
            // 价格分隔符
            String spacer = ".";
            if (options.has("priceSeparator")) {
                spacer = options.get("priceSeparator").asText();
            }
            item.put("Spacer", spacer);
            
            // 小数样式: 0-中间对齐, 1-顶部对齐, 2-底部对齐
            int decimalsStyle = 0;
            if (options.has("textContentVerticalAlign")) {
                String align = options.get("textContentVerticalAlign").asText();
                switch (align) {
                    case "top":
                        decimalsStyle = 1;
                        break;
                    case "bottom":
                        decimalsStyle = 2;
                        break;
                    default:
                        decimalsStyle = 0; // middle
                }
            }
            item.put("DecimalsStyle", decimalsStyle);
        }
        
        log.debug("成功转换printElement为Item: {}", item);
        
        // 重新组织字段顺序，按字母顺序排列（与AP自动修复后的格式一致）
        return createOrderedItem(item, x, y, width, height, fontSize);
    }
    
    /**
     * 创建字段按字母顺序排列的Item对象
     */
    private Map<String, Object> createOrderedItem(Map<String, Object> originalItem, int x, int y, int width, int height, int fontSize) {
        Map<String, Object> orderedItem = new LinkedHashMap<>();
        
        // 按字母顺序添加字段
        if (originalItem.containsKey("Background")) {
            orderedItem.put("Background", originalItem.get("Background"));
        }
        if (originalItem.containsKey("Barformat")) {
            orderedItem.put("Barformat", originalItem.get("Barformat"));
        }
        if (originalItem.containsKey("Barheight")) {
            orderedItem.put("Barheight", originalItem.get("Barheight"));
        }
        if (originalItem.containsKey("Bartype")) {
            orderedItem.put("Bartype", originalItem.get("Bartype"));
        }
        if (originalItem.containsKey("Barwidth")) {
            orderedItem.put("Barwidth", originalItem.get("Barwidth"));
        }
        if (originalItem.containsKey("BorderColor")) {
            orderedItem.put("BorderColor", originalItem.get("BorderColor"));
        }
        if (originalItem.containsKey("BorderStyle")) {
            orderedItem.put("BorderStyle", originalItem.get("BorderStyle"));
        }
        if (originalItem.containsKey("DataDefault")) {
            orderedItem.put("DataDefault", originalItem.get("DataDefault"));
        }
        if (originalItem.containsKey("DataKey")) {
            orderedItem.put("DataKey", originalItem.get("DataKey"));
        }
        if (originalItem.containsKey("DataKeyStyle")) {
            orderedItem.put("DataKeyStyle", originalItem.get("DataKeyStyle"));
        }
        // 添加价格元素的DecimalsStyle属性
        if (originalItem.containsKey("DecimalsStyle")) {
            orderedItem.put("DecimalsStyle", originalItem.get("DecimalsStyle"));
        }
        // 只有非图片元素才添加文本相关属性
        if (!"pic".equals(originalItem.get("Type"))) {
            if (originalItem.containsKey("FontColor")) {
                orderedItem.put("FontColor", originalItem.get("FontColor"));
            }
            if (originalItem.containsKey("FontFamily")) {
                orderedItem.put("FontFamily", originalItem.get("FontFamily"));
            }
            orderedItem.put("FontSize", fontSize);
            if (originalItem.containsKey("FontSpace")) {
                orderedItem.put("FontSpace", originalItem.get("FontSpace"));
            }
            if (originalItem.containsKey("FontStyle")) {
                orderedItem.put("FontStyle", originalItem.get("FontStyle"));
            }
        }
        if (originalItem.containsKey("Fontinval")) {
            orderedItem.put("Fontinval", originalItem.get("Fontinval"));
        }
        // 添加图片特有属性支持
        if (originalItem.containsKey("Imgdeal")) {
            orderedItem.put("Imgdeal", originalItem.get("Imgdeal"));
        }
        if (originalItem.containsKey("Imgfill")) {
            orderedItem.put("Imgfill", originalItem.get("Imgfill"));
        }
        if (originalItem.containsKey("Imgtype")) {
            orderedItem.put("Imgtype", originalItem.get("Imgtype"));
        }
        orderedItem.put("Location", x + ", " + y);
        if (originalItem.containsKey("Showtext")) {
            orderedItem.put("Showtext", originalItem.get("Showtext"));
        }
        orderedItem.put("Size", width + ", " + height);
        // 添加价格元素的Spacer属性
        if (originalItem.containsKey("Spacer")) {
            orderedItem.put("Spacer", originalItem.get("Spacer"));
        }
        // 只有非图片元素才添加TextAlign属性
        if (!"pic".equals(originalItem.get("Type")) && originalItem.containsKey("TextAlign")) {
            orderedItem.put("TextAlign", originalItem.get("TextAlign"));
        }
        if (originalItem.containsKey("Type")) {
            orderedItem.put("Type", originalItem.get("Type"));
        }
        orderedItem.put("height", height);
        orderedItem.put("width", width);
        orderedItem.put("x", x);
        orderedItem.put("y", y);
        
        // 添加图片元素的dval属性支持 - 放在最后以匹配成功版本的字段顺序
        if (originalItem.containsKey("dval")) {
            orderedItem.put("dval", originalItem.get("dval"));
        }
        
        return orderedItem;
    }

    /**
     * 创建默认的官方格式模板
     */
    private String createDefaultOfficialTemplate() throws JsonProcessingException {
        Map<String, Object> template = new HashMap<>();
        template.put("Name", "template");
        template.put("Version", 10);
        template.put("hext", "0");
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
            case "2.13f":
                return "07";
            case "2.66t":
                return "0B";
            case "2.66f":
                return "0C";
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

    /**
     * 🎯 根据TagType获取正确的hext值 - 修复2.13F关键问题
     * 基于成功案例的分析结果
     */
    private String getHextValue(String tagType) {
        if (tagType == null) return "6";
        
        switch (tagType) {
            case "06": // 2.13T
                return "6";
            case "07": // 2.13F - 关键修复！
                return "6"; // 之前错误设置为"0"，现在修正为"6"
            case "0B": // 2.66T
                return "0";
            case "0C": // 2.66F
                return "0";
            case "04": // 1.54T
                return "0";
            case "08": // 2.9T
                return "0";
            case "10": // 4.2T
                return "0";
            case "1C": // 4.20T
                return "0";
            case "1D": // 4.20F
                return "0";
            case "1E": // 7.5T
                return "0";
            default:
                log.debug("未知TagType: {}, 使用默认hext值: 6", tagType);
                return "6";
        }
    }

    /**
     * 🎯 智能边框宽度解析器 - 支持多种格式
     * 能够解析数字、字符串、带单位的宽度值
     */
    private double parseBorderWidth(JsonNode borderWidthNode) {
        if (borderWidthNode == null || borderWidthNode.isNull()) {
            return 0.75; // 默认宽度
        }
        
        try {
            // 如果是数字类型，直接返回
            if (borderWidthNode.isNumber()) {
                return borderWidthNode.asDouble();
            }
            
            // 如果是字符串类型，进行智能解析
            String borderWidthStr = borderWidthNode.asText().trim();
            if (borderWidthStr.isEmpty()) {
                return 0.75;
            }
            
            // 移除单位后缀（pt、px等）并解析数字
            String numericPart = borderWidthStr.replaceAll("[a-zA-Z%]+$", "");
            
            // 解析数字
            double width = Double.parseDouble(numericPart);
            
            // 如果原始字符串包含px单位，需要转换为pt（1px ≈ 0.75pt）
            if (borderWidthStr.toLowerCase().contains("px")) {
                width = width * 0.75; // px转pt
                log.debug("🔄 边框宽度单位转换: {}px -> {}pt", width / 0.75, width);
            }
            
            return width;
            
        } catch (Exception e) {
            log.warn("⚠️ 解析边框宽度失败: {}, 使用默认值0.75pt", borderWidthNode.asText(), e);
            return 0.75;
        }
    }

    /**
     * 🎯 完美的边框宽度转换系统 - 根据映射关系动态转换
     * 实现pt宽度到BorderStyle值的精确映射
     */
    private int convertBorderWidthToStyle(double borderWidthPt, String borderType) {
        // 如果是无边框类型，直接返回0
        if ("none".equals(borderType) || "transparent".equals(borderType)) {
            return 0;
        }
        
        // 🎯 根据你提供的完美映射关系进行转换
        // 0.75pt-1, 1.5pt-2, 2.25pt-3, 3pt-4, 3.75pt-5, 4.5pt-6, 5.25pt-7, 6pt-8, 6.75pt-9
        if (borderWidthPt <= 0) {
            return 0; // 无边框
        } else if (borderWidthPt <= 0.75) {
            return 1; // 0.75pt
        } else if (borderWidthPt <= 1.5) {
            return 2; // 1.5pt
        } else if (borderWidthPt <= 2.25) {
            return 3; // 2.25pt
        } else if (borderWidthPt <= 3.0) {
            return 4; // 3pt
        } else if (borderWidthPt <= 3.75) {
            return 5; // 3.75pt
        } else if (borderWidthPt <= 4.5) {
            return 6; // 4.5pt
        } else if (borderWidthPt <= 5.25) {
            return 7; // 5.25pt
        } else if (borderWidthPt <= 6.0) {
            return 8; // 6pt
        } else if (borderWidthPt <= 6.75) {
            return 9; // 6.75pt
        } else {
            // 超过6.75pt的边框，使用最大值9
            log.debug("⚠️ 边框宽度{}pt超出映射范围，使用最大值9", borderWidthPt);
            return 9;
        }
    }

    /**
     * 🎯 为2.13F生成简化的dval - 修复关键问题
     * 基于成功案例的分析，2.13F需要更简洁的图片处理
     */
    private String generateSimplifiedDval(String base64Image, String rgbMode, int width, int height) {
        if (base64Image == null || base64Image.trim().isEmpty()) {
            log.debug("图片数据为空，返回简化的空dval");
            return "";
        }

        try {
            // 简化的图片处理，专门针对2.13F优化
            log.debug("为2.13F生成简化dval - 尺寸: {}x{}, 颜色模式: {}", width, height, rgbMode);
            
            // 解析Base64数据
            String imageData;
            if (base64Image.contains(",")) {
                String[] parts = base64Image.split(",", 2);
                imageData = parts[1];
            } else {
                imageData = base64Image;
            }
            
            // 解码Base64
            byte[] imageBytes = Base64.getDecoder().decode(imageData);
            
            // 使用Java原生处理，生成简化的图像数据
            BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
            if (originalImage == null) {
                log.warn("无法解析图片数据，返回空dval");
                return "";
            }
            
            // 简化处理：直接调整尺寸并转换为简单的黑白图像
            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(originalImage, 0, 0, width, height, null);
            g2d.dispose();
            
            // 转换为Base64
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "PNG", baos);
            String result = Base64.getEncoder().encodeToString(baos.toByteArray());
            
            log.debug("2.13F简化dval生成成功，长度: {} 字符", result.length());
            return result;
            
        } catch (Exception e) {
            log.error("生成2.13F简化dval时出错: {}", e.getMessage(), e);
            return "";
        }
    }

    /**
     * 生成图片元素的dval字段
     * 使用ImageMagick外部工具，获得完美的电子墨水屏显示效果
     * 这是经过精心优化的图像处理方案，能够产生最佳的颜色分布和抖动效果
     */
    private String generateDval(String base64Image, String rgbMode, int width, int height) {
        if (base64Image == null || base64Image.trim().isEmpty()) {
            log.warn("图片数据为空，返回空dval");
            return "";
        }

        try {
            // 首先检查ImageMagick是否可用
            if (!imageMagickProcessor.isImageMagickAvailable()) {
                log.error("ImageMagick不可用，无法处理图像");
                // 如果ImageMagick不可用，回退到Java原生处理
                return generateDvalWithJava(base64Image, rgbMode, width, height);
            }
            
            log.info("使用ImageMagick处理图像 - 尺寸: {}x{}, 颜色模式: {}", width, height, rgbMode);
            log.debug("ImageMagick版本: {}", imageMagickProcessor.getImageMagickVersion());
            
            // 使用ImageMagick处理器生成完美的电子墨水屏图像
            String result = imageMagickProcessor.processImageForEInk(base64Image, rgbMode, width, height);
            
            if (result != null && !result.isEmpty()) {
                log.info("ImageMagick处理成功，生成dval长度: {} 字符", result.length());
                return result;
            } else {
                log.warn("ImageMagick处理失败，回退到Java原生处理");
                return generateDvalWithJava(base64Image, rgbMode, width, height);
            }
            
        } catch (Exception e) {
            log.error("ImageMagick处理异常: {}, 回退到Java原生处理", e.getMessage(), e);
            return generateDvalWithJava(base64Image, rgbMode, width, height);
        }
    }
    
    /**
     * Java原生图像处理方法（作为ImageMagick的备用方案）
     * 保留原有逻辑以确保系统稳定性
     */
    private String generateDvalWithJava(String base64Image, String rgbMode, int width, int height) {
        log.info("使用Java原生方法处理图像（备用方案）");
        
        try {
            // 解析Base64数据
            String imageData;
            if (base64Image.contains(",")) {
                String[] parts = base64Image.split(",", 2);
                imageData = parts[1];
            } else {
                imageData = base64Image;
            }

            // 解码图像数据
            byte[] imgBytes = Base64.getDecoder().decode(imageData);
            java.io.ByteArrayInputStream bis = new java.io.ByteArrayInputStream(imgBytes);
            java.awt.image.BufferedImage originalImage = javax.imageio.ImageIO.read(bis);
            
            if (originalImage == null) {
                log.error("无法解析图像数据");
                return "";
            }

            // 调整图像尺寸
            java.awt.image.BufferedImage resizedImage = new java.awt.image.BufferedImage(
                width, height, java.awt.image.BufferedImage.TYPE_INT_RGB
            );
            java.awt.Graphics2D g2d = resizedImage.createGraphics();
            g2d.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, 
                               java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(originalImage, 0, 0, width, height, null);
            g2d.dispose();

            // 获取调色板
            int[][] palette = getColorPalette(rgbMode);
            
            // 直接应用Floyd-Steinberg抖动算法
            java.awt.image.BufferedImage ditheredImage = applyFloydSteinbergDithering(resizedImage, palette);
            
            // 转换为PNG格式的Base64
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            javax.imageio.ImageIO.write(ditheredImage, "PNG", baos);
            byte[] processedBytes = baos.toByteArray();
            String result = Base64.getEncoder().encodeToString(processedBytes);
            
            log.debug("Java原生处理完成，原始图片大小: {} bytes, 处理后大小: {} bytes", 
                     imgBytes.length, processedBytes.length);
            
            return result;

        } catch (Exception e) {
            log.error("Java原生图像处理失败: {}", e.getMessage(), e);
            return "";
        }
    }
    
    /**
     * 针对电子墨水屏的颜色增强预处理
     * 更温和的颜色处理，保持自然的颜色分布
     */
    private java.awt.image.BufferedImage enhanceColorsForEInk(java.awt.image.BufferedImage image, String rgbMode) {
        int width = image.getWidth();
        int height = image.getHeight();
        
        java.awt.image.BufferedImage enhanced = new java.awt.image.BufferedImage(
            width, height, java.awt.image.BufferedImage.TYPE_INT_RGB
        );
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                
                // 简化的颜色预处理，接近ImageMagick的标准处理
                // 轻微的对比度增强
                r = enhanceContrast(r);
                g = enhanceContrast(g);
                b = enhanceContrast(b);
                
                int newRgb = (r << 16) | (g << 8) | b;
                enhanced.setRGB(x, y, newRgb);
            }
        }
        
        return enhanced;
    }
    
    /**
     * 增强单个颜色分量的对比度
     */
    private int enhanceContrast(int colorValue) {
        // 使用S曲线增强对比度
        double normalized = colorValue / 255.0;
        double enhanced;
        
        if (normalized < 0.5) {
            enhanced = 2 * normalized * normalized;
        } else {
            enhanced = 1 - 2 * (1 - normalized) * (1 - normalized);
        }
        
        return Math.max(0, Math.min(255, (int)(enhanced * 255)));
    }
    
    /**
     * 获取指定rgb模式的调色板
     * 针对电子墨水屏优化的颜色定义
     */
    private int[][] getColorPalette(String rgbMode) {
        switch (rgbMode) {
            case "2":
                return new int[][]{
                    {0, 0, 0},       // 纯黑色
                    {255, 255, 255}  // 纯白色
                };
            case "3":
                return new int[][]{
                    {0, 0, 0},       // 纯黑色
                    {255, 255, 255}, // 纯白色
                    {255, 0, 0}      // 纯红色 - 电子墨水屏标准红色
                };
            case "4":
                return new int[][]{
                    {0, 0, 0},       // 纯黑色
                    {255, 255, 255}, // 纯白色
                    {255, 0, 0},     // 纯红色
                    {255, 255, 0}    // 纯黄色
                };
            default:
                return new int[][]{
                    {0, 0, 0},       // 纯黑色
                    {255, 255, 255}, // 纯白色
                    {255, 0, 0}      // 纯红色
                };
        }
    }
    
    /**
     * 应用Floyd-Steinberg抖动算法
     */
    private java.awt.image.BufferedImage applyFloydSteinbergDithering(
            java.awt.image.BufferedImage image, int[][] palette) {
        
        int width = image.getWidth();
        int height = image.getHeight();
        
        // 创建输出图像
        java.awt.image.BufferedImage result = new java.awt.image.BufferedImage(
            width, height, java.awt.image.BufferedImage.TYPE_INT_RGB
        );
        
        // 创建误差数组
        double[][][] errors = new double[height][width][3];
        
        // ImageMagick标准扩散强度：85%
        final double DIFFUSION_AMOUNT = 0.85;
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // 获取原始像素颜色
                int rgb = image.getRGB(x, y);
                double[] oldPixel = {
                    ((rgb >> 16) & 0xFF) + errors[y][x][0],
                    ((rgb >> 8) & 0xFF) + errors[y][x][1],
                    (rgb & 0xFF) + errors[y][x][2]
                };
                
                // 限制颜色值范围
                for (int i = 0; i < 3; i++) {
                    oldPixel[i] = Math.max(0, Math.min(255, oldPixel[i]));
                }
                
                // 找到最接近的调色板颜色
                int[] newPixel = findClosestColor(oldPixel, palette);
                
                // 设置新像素
                int newRgb = (newPixel[0] << 16) | (newPixel[1] << 8) | newPixel[2];
                result.setRGB(x, y, newRgb);
                
                // 计算误差
                double[] error = {
                    oldPixel[0] - newPixel[0],
                    oldPixel[1] - newPixel[1],
                    oldPixel[2] - newPixel[2]
                };
                
                // 标准Floyd-Steinberg误差扩散（ImageMagick标准权重分布）
                // 扩散到相邻像素：右(7/16)、左下(3/16)、下(5/16)、右下(1/16)
                if (x + 1 < width) {
                    // 右像素：7/16
                    errors[y][x + 1][0] += error[0] * (7.0 / 16.0) * DIFFUSION_AMOUNT;
                    errors[y][x + 1][1] += error[1] * (7.0 / 16.0) * DIFFUSION_AMOUNT;
                    errors[y][x + 1][2] += error[2] * (7.0 / 16.0) * DIFFUSION_AMOUNT;
                }
                if (y + 1 < height) {
                    if (x > 0) {
                        // 左下像素：3/16
                        errors[y + 1][x - 1][0] += error[0] * (3.0 / 16.0) * DIFFUSION_AMOUNT;
                        errors[y + 1][x - 1][1] += error[1] * (3.0 / 16.0) * DIFFUSION_AMOUNT;
                        errors[y + 1][x - 1][2] += error[2] * (3.0 / 16.0) * DIFFUSION_AMOUNT;
                    }
                    // 下像素：5/16
                    errors[y + 1][x][0] += error[0] * (5.0 / 16.0) * DIFFUSION_AMOUNT;
                    errors[y + 1][x][1] += error[1] * (5.0 / 16.0) * DIFFUSION_AMOUNT;
                    errors[y + 1][x][2] += error[2] * (5.0 / 16.0) * DIFFUSION_AMOUNT;
                    if (x + 1 < width) {
                        // 右下像素：1/16
                        errors[y + 1][x + 1][0] += error[0] * (1.0 / 16.0) * DIFFUSION_AMOUNT;
                        errors[y + 1][x + 1][1] += error[1] * (1.0 / 16.0) * DIFFUSION_AMOUNT;
                        errors[y + 1][x + 1][2] += error[2] * (1.0 / 16.0) * DIFFUSION_AMOUNT;
                    }
                }
            }
        }
        
        return result;
    }
    
    /**
     * 找到调色板中最接近的颜色
     * 针对电子墨水屏优化的颜色匹配算法，支持背景淡红色点效果
     */
    private int[] findClosestColor(double[] pixel, int[][] palette) {
        double r = pixel[0];
        double g = pixel[1];
        double b = pixel[2];
        
        // 标准欧几里得距离计算（ImageMagick标准实现）
        double minDistance = Double.MAX_VALUE;
        int[] closestColor = palette[0];
        
        for (int[] color : palette) {
            // 计算标准欧几里得距离
            double distance = Math.sqrt(
                Math.pow(r - color[0], 2) +
                Math.pow(g - color[1], 2) +
                Math.pow(b - color[2], 2)
            );
            
            if (distance < minDistance) {
                minDistance = distance;
                closestColor = color;
            }
        }
        
        return closestColor.clone();
    }

    }