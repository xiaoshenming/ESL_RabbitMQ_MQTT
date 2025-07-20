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
import com.pandatech.downloadcf.util.ScreenTypeConverter;
import com.pandatech.downloadcf.util.TemplateValidator;
import com.pandatech.downloadcf.util.ScreenTypeMapper;
import com.pandatech.downloadcf.service.FieldMappingService;
import com.pandatech.downloadcf.config.TemplateConfig;
import com.pandatech.downloadcf.exception.TemplateException;
import com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs;
import com.pandatech.downloadcf.entity.EslBrand;
import com.pandatech.downloadcf.entity.ProductEslBinding;
import com.pandatech.downloadcf.mapper.PrintTemplateDesignMapper;
import com.pandatech.downloadcf.mapper.EslBrandMapper;
import com.pandatech.downloadcf.mapper.ProductEslBindingMapper;
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

    @Value("${app.template.base-url}")
    private String templateBaseUrl;

    private final PrintTemplateDesignMapper templateDesignMapper;
    private final EslBrandMapper eslBrandMapper;
    private final ProductEslBindingMapper productEslBindingMapper;
    private final ObjectMapper objectMapper;
    private final ScreenTypeConverter screenTypeConverter;
    private final TemplateValidator templateValidator;
    private final TemplateConfig templateConfig;
    private final FieldMappingService fieldMappingService;

    @ServiceActivator(inputChannel = "mqttInputChannel")
    @SuppressWarnings("unchecked")
    public void handleMessage(@Header(MqttHeaders.RECEIVED_TOPIC) String topic, String payload) {
        log.info("接收到MQTT消息 - 主题: {}, 内容: {}", topic, payload);
        // TODO: 根据新的数据库结构重新实现
        log.warn("MQTT消息处理功能暂时禁用，需要根据新数据库结构重新实现");
        /*
        // 假设 /templ/loadtemple 是一个特定的主题或包含在payload中用于区分
        if (topic.contains("loadtemple")) {
            try {
                // 这里模拟根据payload（可能是价签ID）加载模板和产品数据
                // 实际场景中，payload的格式需要根据业务确定
                @SuppressWarnings("unchecked")
                Map<String, Object> request = objectMapper.readValue(payload, HashMap.class);
                String eslId = (String) request.get("eslId");

                // TODO: 根据新的数据库结构重新实现
                // 处理模板下发
                processTemplateDownload(eslId);
                log.info("成功为价签 {} 处理模板下发", eslId);

            } catch (Exception e) {
                log.error("处理MQTT消息失败", e);
            }
        }
        */
    }

    @Qualifier("mqttOutboundChannel")
    private final MessageChannel mqttOutboundChannel;

    /*
    // TODO: 根据新的数据库结构重新实现这些方法
    public void processTemplateDownload(String eslId) {
        // 暂时注释掉，需要根据新的数据库结构重新实现
    }

    public void processRefresh(String eslId) {
        // 暂时注释掉，需要根据新的数据库结构重新实现
    }
    */

    private String getCachedChecksum(String templateId) {
        // TODO: 实现实际缓存逻辑
        // 实现从缓存或数据库获取checksum的逻辑
        return null;  // 示例，返回null表示未缓存
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
                // 确保FontFamily都是Zfull-GB
                String result = ensureCorrectFontFamily(jsonToParse);
                 // 验证模板
                 TemplateValidator.ValidationResult validation = templateValidator.validateTemplateContent(result);
                 if (validation.hasWarnings()) {
                     log.warn("模板验证警告: {}", validation.getWarnings());
                 }
                 return result;
            }
            
            // 检查是否是panels格式（自定义格式）
            if (rootNode.has("panels")) {
                log.debug("检测到panels格式模板，开始转换");
                String result = convertPanelsToOfficialFormat(rootNode, template);
                 // 验证模板
                 TemplateValidator.ValidationResult validation = templateValidator.validateTemplateContent(result);
                 if (validation.hasWarnings()) {
                     log.warn("模板验证警告: {}", validation.getWarnings());
                 }
                 return result;
            }
            
            // 检查是否包含designConfig字段
            if (rootNode.has("designConfig")) {
                log.info("检测到designConfig格式模板，开始转换");
                String result = convertPanelsToOfficialFormat(rootNode, template);
                 // 验证模板
                 TemplateValidator.ValidationResult validation = templateValidator.validateTemplateContent(result);
                 if (validation.hasWarnings()) {
                     log.warn("模板验证警告: {}", validation.getWarnings());
                 }
                 return result;
            }
            
            // 其他格式，尝试提取基本信息
            log.warn("未识别的模板格式，根节点包含字段: {}, 使用默认转换", rootNode.fieldNames());
            String result = createDefaultOfficialTemplate();
             // 验证模板
             TemplateValidator.ValidationResult validation = templateValidator.validateTemplateContent(result);
             if (validation.hasWarnings()) {
                 log.warn("默认模板验证警告: {}", validation.getWarnings());
             }
             return result;
            
        } catch (Exception e) {
            log.error("转换模板格式时发生错误，使用默认模板", e);
            String result = createDefaultOfficialTemplate();
             // 验证默认模板
             try {
                 TemplateValidator.ValidationResult validation = templateValidator.validateTemplateContent(result);
                 if (validation.hasErrors()) {
                     log.error("默认模板验证错误: {}", validation.getErrors());
                 }
                 if (validation.hasWarnings()) {
                     log.warn("默认模板验证警告: {}", validation.getWarnings());
                 }
             } catch (Exception validationException) {
                 log.error("默认模板验证失败", validationException);
             }
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
        
        String configuredFontFamily = templateConfig.getDefaultTemplate().getFontFamily();
        
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
        
        // 从模板对象中获取基本信息，如果为空则使用默认值
        TemplateConfig.DefaultTemplate defaultConfig = templateConfig.getDefaultTemplate();
        String templateName = template.getName() != null ? template.getName() : defaultConfig.getName();
        String screenType = extractScreenTypeFromTemplate(template);
        String tagType = ScreenTypeMapper.getTagType(screenType);
        official.put("Name", templateName); // 只使用基础模板名称，不包含后缀
        official.put("Version", defaultConfig.getVersion());
        official.put("hext", String.valueOf(defaultConfig.getHext()));
        official.put("rgb", String.valueOf(defaultConfig.getRgb()));
        official.put("wext", String.valueOf(defaultConfig.getWext()));
        
        // 默认尺寸，会被面板信息覆盖
        official.put("Size", defaultConfig.getSize());
        official.put("height", String.valueOf(defaultConfig.getHeight()));
        official.put("width", String.valueOf(defaultConfig.getWidth()));
        
        official.put("TagType", tagType);
        
        log.info("模板转换 - 屏幕类型: {}, TagType: {}", screenType, tagType);
        
        JsonNode designConfig = rootNode.get("designConfig");
        if (designConfig == null) {
            log.warn("模板JSON中缺少 'designConfig' 字段");
            official.put("Items", items);
            return objectMapper.writeValueAsString(official);
        }

        JsonNode panels = designConfig.get("panels");
        if (panels.isArray()) {
            for (JsonNode panel : panels) {
                // 提取面板的基本信息
                if (panel.has("width") && panel.has("height")) {
                    int width = panel.get("width").asInt();
                    int height = panel.get("height").asInt();
                    official.put("Size", width + ", " + height);
                    official.put("width", String.valueOf(width));
                    official.put("height", String.valueOf(height));
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
        
        log.info("模板转换完成，共生成{}个Items", items.size());
        official.put("Items", items);
        return objectMapper.writeValueAsString(official);
    }
    
    /**
     * 从模板中提取屏幕类型
     */
    private String extractScreenTypeFromTemplate(PrintTemplateDesignWithBLOBs template) {
        // 首先尝试从EXT_JSON中提取
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

        // 基本属性（使用配置中的默认值）
        TemplateConfig.DefaultTemplate defaultConfig = templateConfig.getDefaultTemplate();

        // 根据 printElementType 设置 Type
        String elementType = "text"; // 默认类型
        if (element.has("printElementType") && element.get("printElementType").has("type")) {
            elementType = element.get("printElementType").get("type").asText("text");
        }
        item.put("Type", elementType);
        
        // 设置默认属性
        item.put("FontFamily", defaultConfig.getFontFamily());
        item.put("FontColor", defaultConfig.getFontColor());
        item.put("Background", defaultConfig.getBackground());
        item.put("BorderColor", defaultConfig.getBorderColor());
        item.put("BorderStyle", defaultConfig.getBorderStyle());
        item.put("FontStyle", defaultConfig.getFontStyle());
        item.put("FontSpace", defaultConfig.getFontSpace());
        item.put("TextAlign", defaultConfig.getTextAlign());
        item.put("DataKeyStyle", defaultConfig.getDataKeyStyle());
        
        // 获取画布尺寸
        int canvasWidth = defaultConfig.getWidth();  // 250px
        int canvasHeight = defaultConfig.getHeight(); // 122px
        
        // 从原始数据分析，设计画布似乎是更大的尺寸（约720x360pt）
        // 需要按比例缩放到目标画布（250x122px）
        double originalCanvasWidth = 720.0;  // 推测的原始设计画布宽度
        double originalCanvasHeight = 360.0; // 推测的原始设计画布高度
        
        // 计算缩放比例
        double scaleX = (double) canvasWidth / originalCanvasWidth;   // 250/720 ≈ 0.347
        double scaleY = (double) canvasHeight / originalCanvasHeight; // 122/360 ≈ 0.339
        
        // 使用较小的缩放比例以确保内容不会超出边界
        double scale = Math.min(scaleX, scaleY);
        
        log.debug("缩放比例计算 - scaleX: {}, scaleY: {}, 使用scale: {}", scaleX, scaleY, scale);
        
        // 获取原始坐标和尺寸
        double leftPt = options.has("left") ? options.get("left").asDouble() : 0;
        double topPt = options.has("top") ? options.get("top").asDouble() : 0;
        double widthPt = options.has("width") ? options.get("width").asDouble() : 50;
        double heightPt = options.has("height") ? options.get("height").asDouble() : 20;
        
        // 应用缩放转换
        int x = (int) Math.round(leftPt * scale);
        int y = (int) Math.round(topPt * scale);
        int width = (int) Math.round(widthPt * scale);
        int height = (int) Math.round(heightPt * scale);
        
        // 边界检查和调整
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        
        // 确保元素不超出画布边界
        if (x >= canvasWidth) x = canvasWidth - 1;
        if (y >= canvasHeight) y = canvasHeight - 1;
        
        if (x + width > canvasWidth) {
            width = canvasWidth - x;
        }
        if (y + height > canvasHeight) {
            height = canvasHeight - y;
        }
        
        // 确保最小尺寸
        if (width < 1) width = 1;
        if (height < 1) height = 1;
        
        // 针对不同元素类型进行特殊处理
        if ("rect".equals(elementType) || "oval".equals(elementType)) {
            // 图形元素设置边框样式
            item.put("BorderStyle", 1); // 显示边框
            item.put("BorderColor", "Black");
            item.put("Background", "Transparent");
        }
        
        log.debug("坐标转换 - 原始: left={}, top={}, width={}, height={}", leftPt, topPt, widthPt, heightPt);
        log.debug("转换后: x={}, y={}, width={}, height={}, 画布: {}x{}", x, y, width, height, canvasWidth, canvasHeight);
        
        item.put("x", x);
        item.put("y", y);
        item.put("width", width);
        item.put("height", height);
        item.put("Location", x + ", " + y);
        item.put("Size", width + ", " + height);
        
        // 字体属性
        if (options.has("fontSize")) {
            item.put("FontSize", (int) options.get("fontSize").asDouble());
        } else {
            item.put("FontSize", defaultConfig.getFontSize());
        }
        
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
        
        // 数据绑定 - 使用字段映射服务
        String templateField = null;
        if (options.has("templateField")) {
            templateField = options.get("templateField").asText();
            log.debug("找到字段: {}", templateField);
        } else {
            log.debug("未找到field字段");
        }
        
        // 尝试获取品牌编码进行字段映射
        String brandCode = extractBrandCodeFromTemplate(template);
        String mappedField = templateField;
        
        if (templateField != null && !templateField.isEmpty() && brandCode != null) {
            try {
                String systemField = fieldMappingService.getSystemField(brandCode, templateField);
                if (systemField != null && !systemField.isEmpty()) {
                    mappedField = systemField;
                    log.debug("字段映射: {} -> {} (品牌: {})", templateField, systemField, brandCode);
                }
            } catch (Exception e) {
                log.warn("字段映射失败，使用原始字段: {} (品牌: {})", templateField, brandCode, e);
            }
        }
        
        item.put("DataKey", mappedField != null ? mappedField : "");
        
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
     * 从模板中提取品牌编码
     */
    private String extractBrandCodeFromTemplate(PrintTemplateDesignWithBLOBs template) {
        // 尝试从模板名称中提取品牌编码
        // 假设模板名称格式为: brandCode_templateName 或者直接是品牌编码
        String templateName = template.getName();
        if (templateName != null && !templateName.trim().isEmpty()) {
            // 如果包含下划线，取第一部分作为品牌编码
            if (templateName.contains("_")) {
                return templateName.split("_")[0];
            }
            // 否则直接返回模板名称作为品牌编码
            return templateName;
        }
        
        // 默认品牌编码
        return "DEFAULT";
    }

    /**
     * 创建默认的官方格式模板
     */
    private String createDefaultOfficialTemplate() throws JsonProcessingException {
        TemplateConfig.DefaultTemplate defaultConfig = templateConfig.getDefaultTemplate();
        
        Map<String, Object> official = new HashMap<>();
        official.put("Items", new ArrayList<>());
        official.put("Name", defaultConfig.getName());
        official.put("Size", defaultConfig.getSize());
        official.put("TagType", defaultConfig.getTagType());
        official.put("Version", defaultConfig.getVersion());
        official.put("height", defaultConfig.getHeight());
        official.put("hext", defaultConfig.getHext());
        official.put("rgb", defaultConfig.getRgb());
        official.put("wext", defaultConfig.getWext());
        official.put("width", defaultConfig.getWidth());
        
        return objectMapper.writeValueAsString(official);
    }

    private String calculateMD5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes("UTF-8"));
            byte[] digest = md.digest();
            return new BigInteger(1, digest).toString(16);
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            log.error("计算MD5失败", e);
            return "";
        }
    }

    private String generateTmpllistJson(String templateId, String templateName, String md5, String tid, String shop, String tagType) {
        try {
            Map<String, Object> json = new HashMap<>();
            json.put("command", "tmpllist");
            Map<String, Object> data = new HashMap<>();
            data.put("url", templateBaseUrl);
            List<Map<String, String>> tmpls = new ArrayList<>();
            Map<String, String> tmpl = new HashMap<>();
            // 根据templateName和tagType生成文件名，例如 "2_06.json"
            String fileName = templateName + "_" + tagType + ".json";
            tmpl.put("name", fileName);
            tmpl.put("md5", md5);
            tmpl.put("id", templateId);
            tmpls.add(tmpl);
            data.put("tmpls", tmpls);
            data.put("tid", tid);
            json.put("data", data);
            json.put("id", "uuid");
            json.put("timestamp", System.currentTimeMillis() / 1000);
            json.put("shop", shop);
            return objectMapper.writeValueAsString(json);
        } catch (JsonProcessingException e) {
            log.error("生成tmpllist JSON失败", e);
            return "{}";
        }
    }

    public void sendTemplateToMqtt(String topic, PrintTemplateDesignWithBLOBs template, String screenType) {
        if (!templateConfig.getMqtt().isEnabled()) {
            log.debug("MQTT发送已禁用，跳过发送模板: {}", template.getName());
            return;
        }
        
        try {
            // 验证输入参数
            if (template == null) {
                throw new TemplateException.TemplateNotFoundException("模板对象为空");
            }
            
            if (topic == null || topic.trim().isEmpty()) {
                topic = templateConfig.getMqtt().getTopicPrefix() + "default";
            }
            
            // 转换模板格式
            String officialTemplate = convertToOfficialTemplate(template);
            
            // 从转换后的模板中提取Name和TagType以生成正确的文件名
            JsonNode rootNode = objectMapper.readTree(officialTemplate);
            String name = rootNode.has("Name") ? rootNode.get("Name").asText() : template.getName();
            String tagType = rootNode.has("TagType") ? rootNode.get("TagType").asText() : ScreenTypeMapper.getTagType(screenType);

            String templateFileName = name + "_" + tagType + ".json";

            // 创建MQTT消息
            Map<String, Object> message = new HashMap<>();
            if (templateConfig.getMqtt().isIncludeTemplateContent()) {
                message.put("template", officialTemplate);
            }
            message.put("name", templateFileName);
            message.put("id", template.getId());
            message.put("screenType", screenType != null ? screenType : "1C"); // 保留原始screenType以供参考
            message.put("timestamp", System.currentTimeMillis());
            
            String jsonMessage = objectMapper.writeValueAsString(message);
            
            // 发送MQTT消息（带重试机制）
            sendMqttMessageWithRetry(topic, jsonMessage.getBytes());
            log.info("模板已发送到MQTT主题: {}, 模板名称: {}, 屏幕类型: {}", topic, templateFileName, screenType);
            
        } catch (TemplateException e) {
            log.error("模板处理失败: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("发送模板到MQTT失败: 模板={}, 主题={}", template != null ? template.getName() : "unknown", topic, e);
            throw new TemplateException.MqttSendException("发送模板到MQTT失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 带重试机制的MQTT消息发送
     */
    private void sendMqttMessageWithRetry(String topic, byte[] message) {
        int retryCount = templateConfig.getMqtt().getRetryCount();
        Exception lastException = null;
        
        for (int i = 0; i <= retryCount; i++) {
            try {
                sendMqttMessage(topic, message);
                return; // 发送成功，退出重试循环
            } catch (Exception e) {
                lastException = e;
                if (i < retryCount) {
                    log.warn("MQTT消息发送失败，第{}次重试: {}", i + 1, e.getMessage());
                    try {
                        Thread.sleep(1000 * (i + 1)); // 递增延迟
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new TemplateException.MqttSendException("发送被中断", ie);
                    }
                }
            }
        }
        
        throw new TemplateException.MqttSendException("MQTT消息发送失败，已重试" + retryCount + "次", lastException);
    }
    


    /*
    // TODO: 需要根据新数据库结构重新实现此方法
    // 新结构使用 ProductEslBinding 来管理产品和价签的绑定关系
    // 使用 EslModel 来管理价签型号信息
    private String generateWtagJson(PandaEsl esl, PandaProduct product, PrintTemplateDesignWithBLOBs template, String md5) {
        try {
            Map<String, Object> json = new HashMap<>();
            json.put("command", "wtag");
            List<Map<String, Object>> dataList = new ArrayList<>();
            Map<String, Object> data = new HashMap<>();
            data.put("tag", esl.getEslId());
            data.put("tmpl", template.getId());
            data.put("model", convertModel(esl.getEslModel()));
            data.put("checksum", md5);
            data.put("forcefrash", 1);
            Map<String, Object> value = new HashMap<>();
            value.put("GOODS_NAME", product.getProductName());
            value.put("GOODS_PRICE", product.getProductSalePrice());
            value.put("GOODS_SPEC", product.getGoodsSpec() != null ? product.getGoodsSpec() : "");
            value.put("GOODS_UNIT", product.getGoodsUnit() != null ? product.getGoodsUnit() : "");
            value.put("GOODS_ORIGIN", product.getGoodsOrigin() != null ? product.getGoodsOrigin() : "");
            value.put("GOODS_PROMOTION", product.getGoodsPromotion() != null ? product.getGoodsPromotion() : "");
            // 添加所有必要的字段映射，根据PandaProduct实体
            data.put("value", value);
            data.put("taskid", UUID.randomUUID().toString());
            data.put("token", UUID.randomUUID().toString());
            dataList.add(data);
            json.put("data", dataList);
            json.put("id", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis() / 1000);
            json.put("shop", product.getStoreCode());
            return objectMapper.writeValueAsString(json);
        } catch (JsonProcessingException e) {
            log.error("生成wtag JSON失败", e);
            return "{}";
        }
    }
    */

    private String convertModel(String model) {
        Map<String, String> modelconvert = new HashMap<>();
        modelconvert.put("30", "01");
        modelconvert.put("42", "02");
        modelconvert.put("58", "03");
        modelconvert.put("74", "04");
        modelconvert.put("75", "05");
        modelconvert.put("79", "06");
        modelconvert.put("80", "07");
        modelconvert.put("81", "08");
        modelconvert.put("82", "09");
        modelconvert.put("83", "0A");
        modelconvert.put("84", "0B");
        modelconvert.put("85", "0C");
        modelconvert.put("86", "0D");
        modelconvert.put("87", "0E");
        modelconvert.put("88", "0F");
        modelconvert.put("89", "10");
        modelconvert.put("90", "11");
        modelconvert.put("91", "12");
        modelconvert.put("92", "13");
        modelconvert.put("93", "14");
        modelconvert.put("94", "15");
        modelconvert.put("95", "16");
        modelconvert.put("96", "17");
        modelconvert.put("97", "18");
        modelconvert.put("98", "19");
        modelconvert.put("99", "1A");
        modelconvert.put("100", "1B");
        modelconvert.put("101", "1C");
        modelconvert.put("102", "1D");
        modelconvert.put("103", "1E");
        modelconvert.put("104", "1F");
        modelconvert.put("105", "20");
        modelconvert.put("106", "21");
        modelconvert.put("107", "22");
        modelconvert.put("108", "23");
        modelconvert.put("109", "24");
        modelconvert.put("110", "25");
        modelconvert.put("111", "26");
        modelconvert.put("112", "27");
        modelconvert.put("113", "28");
        modelconvert.put("114", "29");
        modelconvert.put("115", "2A");
        modelconvert.put("116", "2B");
        modelconvert.put("117", "2C");
        modelconvert.put("118", "2D");
        modelconvert.put("119", "2E");
        modelconvert.put("120", "2F");
        modelconvert.put("121", "30");
        modelconvert.put("122", "31");
        modelconvert.put("123", "32");
        modelconvert.put("124", "33");
        modelconvert.put("125", "34");
        modelconvert.put("126", "35");
        modelconvert.put("127", "36");
        modelconvert.put("128", "37");
        modelconvert.put("129", "38");
        modelconvert.put("130", "39");
        modelconvert.put("131", "3A");
        modelconvert.put("132", "3B");
        modelconvert.put("133", "3C");
        modelconvert.put("134", "3D");
        modelconvert.put("135", "3E");
        modelconvert.put("136", "3F");
        // 根据log.md添加所有映射
        return modelconvert.getOrDefault(model, model);
    }

    private void sendMqttMessage(String topic, byte[] payload) {
        try {
            org.springframework.messaging.Message<byte[]> message = MessageBuilder
                    .withPayload(payload)
                    .setHeader("mqtt_topic", topic)
                    .build();
            mqttOutboundChannel.send(message);
            log.info("MQTT消息发送成功 - 主题: {}, 数据长度: {}", topic, payload.length);
        } catch (Exception e) {
            log.error("MQTT消息发送失败 - 主题: {}", topic, e);
        }
    }
}