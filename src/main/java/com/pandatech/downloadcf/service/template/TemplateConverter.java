package com.pandatech.downloadcf.service.template;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs;
import com.pandatech.downloadcf.service.mqtt.MqttImageProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 模板转换器
 * 负责将自定义模板格式转换为官方格式
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TemplateConverter {

    private final ObjectMapper objectMapper;
    private final MqttImageProcessor mqttImageProcessor;

    /**
     * 将自定义模板格式转换为官方格式
     * 支持两种输入格式：
     * 1. 已经是官方格式的JSON（如U_06.json）
     * 2. 自定义格式的JSON（如数据库中的panels格式）
     */
    public String convertToOfficialTemplate(PrintTemplateDesignWithBLOBs template) throws JsonProcessingException {
        log.info("模板转换开始 - 模板ID: {}, 模板名称: {}", template.getId(), template.getName());
        
        String extJson = template.getExtJson();
        String content = template.getContent();
        
        log.info("EXT_JSON是否为null: {}", extJson == null);
        if (extJson != null) {
            log.info("EXT_JSON长度: {}", extJson.length());
        }
        
        log.info("CONTENT是否为null: {}", content == null);
        if (content != null) {
            log.info("CONTENT长度: {}", content.length());
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

        try {
            JsonNode rootNode = objectMapper.readTree(jsonToParse);
            log.info("JSON解析成功，根节点字段: {}", rootNode.fieldNames());
            
            // 检查是否已经是官方格式（包含Items字段）
            if (rootNode.has("Items")) {
                log.debug("检测到官方格式模板，直接返回");
                return ensureCorrectFontFamily(jsonToParse);
            }
            
            // 检查是否是panels格式（自定义格式）
            if (rootNode.has("panels")) {
                log.debug("检测到panels格式模板，开始转换");
                return convertPanelsToOfficialFormat(rootNode, template);
            }
            
            // 检查是否包含designConfig字段
            if (rootNode.has("designConfig")) {
                log.info("检测到designConfig格式模板，开始转换");
                return convertPanelsToOfficialFormat(rootNode, template);
            }
            
            // 其他格式，使用默认模板
            log.warn("未识别的模板格式，根节点包含字段: {}, 使用默认转换", rootNode.fieldNames());
            return createDefaultOfficialTemplate();
            
        } catch (Exception e) {
            log.error("转换模板格式时发生错误，使用默认模板", e);
            return createDefaultOfficialTemplate();
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
            List<Map<String, Object>> items = (List<Map<String, Object>>) result.get("Items");
            
            for (Map<String, Object> item : items) {
                if (item.containsKey("FontFamily")) {
                    item.put("FontFamily", configuredFontFamily);
                    log.debug("更新FontFamily为: {}", configuredFontFamily);
                }
            }
        }
        
        return objectMapper.writeValueAsString(result);
    }

    /**
     * 将panels格式转换为官方格式
     */
    private String convertPanelsToOfficialFormat(JsonNode rootNode, PrintTemplateDesignWithBLOBs template) throws JsonProcessingException {
        log.info("执行panels到官方格式的转换");
        
        try {
            // 创建基础模板结构
            Map<String, Object> officialTemplate = new HashMap<>();
            
            List<Map<String, Object>> items = new ArrayList<>();
            
            // 查找panels数据
            JsonNode panelsNode = null;
            if (rootNode.has("designConfig") && rootNode.get("designConfig").has("panels")) {
                panelsNode = rootNode.get("designConfig").get("panels");
                log.info("从designConfig.panels中提取数据");
            } else if (rootNode.has("panels")) {
                panelsNode = rootNode.get("panels");
                log.info("从panels中提取数据");
            }
            
            int templateWidth = 250;
            int templateHeight = 122;
            String templateName = template.getName() != null ? template.getName() : "Template";
            
            if (panelsNode != null && panelsNode.isArray() && panelsNode.size() > 0) {
                JsonNode firstPanel = panelsNode.get(0);
                
                // 获取模板尺寸
                if (firstPanel.has("width")) {
                    templateWidth = firstPanel.get("width").asInt();
                }
                if (firstPanel.has("height")) {
                    templateHeight = firstPanel.get("height").asInt();
                }
                
                // 获取模板名称
                if (firstPanel.has("name")) {
                    templateName = firstPanel.get("name").asText();
                }
                
                // 转换printElements为Items
                if (firstPanel.has("printElements")) {
                    JsonNode printElements = firstPanel.get("printElements");
                    log.info("找到printElements，元素数量: {}", printElements.size());
                    
                    for (JsonNode element : printElements) {
                        Map<String, Object> item = convertPrintElementToItem(element, template);
                        if (item != null) {
                            items.add(item);
                        }
                    }
                }
            }
            
            // 按照期望的格式设置所有必要字段
            officialTemplate.put("Items", items);
            officialTemplate.put("Name", templateName);
            officialTemplate.put("Size", templateWidth + ", " + templateHeight);
            
            // 使用MqttImageProcessor获取正确的TagType
            String tagType = mqttImageProcessor.getTagType(templateName);
            officialTemplate.put("TagType", tagType);
            
            // 根据TagType设置正确的hext值
            String hextValue = mqttImageProcessor.getHextValue(tagType);
            
            // 根据TagType设置正确的rgb值
            String rgbValue = getRgbValueByTagType(tagType);
            
            officialTemplate.put("Version", 10);
            officialTemplate.put("height", String.valueOf(templateHeight));
            officialTemplate.put("hext", hextValue);
            officialTemplate.put("rgb", rgbValue);
            officialTemplate.put("wext", "0");
            officialTemplate.put("width", String.valueOf(templateWidth));
            
            log.info("转换完成，生成Items数量: {}, 模板尺寸: {}x{}", items.size(), templateWidth, templateHeight);
            
            return objectMapper.writeValueAsString(officialTemplate);
            
        } catch (Exception e) {
            log.error("转换panels格式时发生错误，使用默认模板", e);
            return createDefaultOfficialTemplate();
        }
    }

    /**
     * 将printElement转换为Item格式
     */
    private Map<String, Object> convertPrintElementToItem(JsonNode element, PrintTemplateDesignWithBLOBs template) {
        try {
            if (!element.has("options") || !element.has("printElementType")) {
                log.warn("printElement缺少必要字段，跳过");
                return null;
            }
            
            JsonNode options = element.get("options");
            JsonNode elementType = element.get("printElementType");
            
            Map<String, Object> item = new HashMap<>();
            
            // 基础属性
            String type = elementType.get("type").asText("text");
            item.put("Type", type);
            
            // 位置和尺寸 - 直接使用设计器坐标转换为像素坐标
            double left = options.has("left") ? options.get("left").asDouble() : 0;
            double top = options.has("top") ? options.get("top").asDouble() : 0;
            double width = options.has("width") ? options.get("width").asDouble() : 50;
            double height = options.has("height") ? options.get("height").asDouble() : 20;
            
            // 坐标转换：设计器坐标(750x345) -> 像素坐标
            // 根据模板名称确定目标尺寸
            String templateName = template.getName();
            double targetWidth = 250.0; // 默认2.13T尺寸
            double targetHeight = 122.0;
            
            if (templateName != null && templateName.contains("4.2")) {
                targetWidth = 400.0;
                targetHeight = 300.0;
            }
            
            int x = (int) Math.round(left * targetWidth / 750.0);
            int y = (int) Math.round(top * targetHeight / 345.0);
            int w = (int) Math.round(width * targetWidth / 750.0);
            int h = (int) Math.round(height * targetHeight / 345.0);
            
            item.put("x", x);
            item.put("y", y);
            item.put("width", w);
            item.put("height", h);
            item.put("Location", x + ", " + y);
            item.put("Size", w + ", " + h);
            
            // 数据字段
            if (options.has("templateField")) {
                item.put("DataKey", options.get("templateField").asText());
            }
            if (options.has("testData")) {
                item.put("DataDefault", options.get("testData").asText());
            }
            
            // 样式属性
            item.put("FontFamily", "阿里普惠");
            item.put("FontSize", options.has("fontSize") ? options.get("fontSize").asInt() : 8);
            item.put("FontStyle", 0);
            item.put("FontSpace", 0);
            item.put("TextAlign", getTextAlign(options.get("textAlign")));
            item.put("DataKeyStyle", 0);
            
            // 颜色
            item.put("FontColor", options.has("color") ? options.get("color").asText() : "Black");
            item.put("Background", options.has("backgroundColor") ? options.get("backgroundColor").asText() : "Transparent");
            
            // 边框
            item.put("BorderColor", "Transparent");
            item.put("BorderStyle", 0);
            
            // 处理特殊类型
            if ("image".equals(type)) {
                // 图片类型的特殊处理
                item.put("Type", "pic");
                if (options.has("src")) {
                    String src = options.get("src").asText();
                    item.put("DataDefault", src);
                    // 添加图片特有的字段
                    item.put("Imgdeal", 0);
                    item.put("Imgfill", 0);
                    item.put("Imgtype", "png");
                    // 如果是base64图片，提取纯base64数据
                    if (src.startsWith("data:image/")) {
                        int commaIndex = src.indexOf(",");
                        if (commaIndex > 0 && commaIndex < src.length() - 1) {
                            String base64Data = src.substring(commaIndex + 1);
                            item.put("dval", base64Data);
                        }
                    }
                }
                // 清除不适用于图片的字段
                item.remove("FontFamily");
                item.remove("FontSize");
                item.remove("FontStyle");
                item.remove("FontSpace");
                item.remove("TextAlign");
                item.remove("FontColor");
                item.put("DataKey", "");
            } else if ("text".equals(type) && options.has("textType")) {
                String textType = options.get("textType").asText();
                if ("barcode".equals(textType)) {
                    item.put("Type", "barcode");
                    // 添加条形码特有的字段
                    item.put("Barformat", 0);
                    // 修复条形码高度：根据模板类型设置正确的高度
                    if (templateName != null && templateName.contains("4.2")) {
                        // 4.2寸屏幕条形码高度为17
                        item.put("Barheight", 17);
                    } else {
                        // 2.13寸屏幕条形码高度为19
                        item.put("Barheight", 19);
                    }
                    item.put("Bartype", "code128");
                    item.put("Barwidth", w);
                    item.put("Fontinval", 1);
                    item.put("Showtext", 1);
                } else if ("qrcode".equals(textType)) {
                    item.put("Type", "qrcode");
                    // 二维码使用默认字体大小16
                    item.put("FontSize", 16);
                    
                    // 修复二维码尺寸：根据模板类型设置正确的尺寸
                    if (templateName != null && templateName.contains("4.2")) {
                        // 4.2寸屏幕二维码尺寸为99x99
                        item.put("width", 99);
                        item.put("height", 99);
                        item.put("Size", "99, 99");
                    } else {
                        // 2.13寸屏幕保持原有尺寸
                        // 不修改w和h，保持原有计算结果
                    }
                }
            }
            
            log.debug("转换printElement成功: Type={}, DataKey={}, Location={}, Size={}", 
                     item.get("Type"), item.get("DataKey"), item.get("Location"), item.get("Size"));
            
            return item;
            
        } catch (Exception e) {
            log.error("转换printElement时发生错误", e);
            return null;
        }
    }
    
    /**
     * 转换文本对齐方式
     */
    private int getTextAlign(JsonNode textAlignNode) {
        if (textAlignNode == null) {
            return 0; // 默认左对齐
        }
        
        String textAlign = textAlignNode.asText("left");
        switch (textAlign.toLowerCase()) {
            case "center":
                return 1;
            case "right":
                return 2;
            default:
                return 0; // 左对齐
        }
    }

    /**
     * 创建默认的官方格式模板
     */
    private String createDefaultOfficialTemplate() throws JsonProcessingException {
        Map<String, Object> template = new HashMap<>();
        
        // 基本信息
        template.put("Version", "1.0");
        template.put("TagType", "06");
        template.put("Hext", "0x0006");
        template.put("Width", 250);
        template.put("Height", 122);
        template.put("Rotate", 0);
        template.put("Items", new ArrayList<>());
        
        log.info("创建默认官方格式模板");
        return objectMapper.writeValueAsString(template);
    }

    /**
     * 从官方模板JSON中提取TagType
     */
    public String extractTagTypeFromOfficialTemplate(String officialJson) {
        try {
            JsonNode rootNode = objectMapper.readTree(officialJson);
            
            if (rootNode.has("TagType")) {
                String tagType = rootNode.get("TagType").asText();
                log.debug("从模板中提取TagType: {}", tagType);
                return tagType;
            }
            
            // 如果没有TagType，尝试从Hext推导
            if (rootNode.has("Hext")) {
                String hext = rootNode.get("Hext").asText();
                String tagType = getTagTypeFromHext(hext);
                log.debug("从Hext推导TagType: {} -> {}", hext, tagType);
                return tagType;
            }
            
            log.warn("未找到TagType，使用默认值06");
            return "06";
            
        } catch (Exception e) {
            log.error("提取TagType时出错，使用默认值06", e);
            return "06";
        }
    }

    /**
     * 根据TagType获取正确的rgb值
     */
    private String getRgbValueByTagType(String tagType) {
        if (tagType == null) return "3";
        
        switch (tagType) {
            case "06": // 2.13T
                return "3";
            case "07": // 2.13F
                return "3";
            case "0B": // 2.66T
                return "3";
            case "0C": // 2.66F
                return "3";
            case "02": // 1.54T
                return "3";
            case "0A": // 2.9T
                return "3";
            case "1C": // 4.2T
                return "3";
            case "1D": // 4.2F
                return "4";
            case "1E": // 7.5T
                return "3";
            default:
                log.debug("未知TagType: {}, 使用默认rgb值: 3", tagType);
                return "3";
        }
    }

    /**
     * 从Hext值推导TagType
     */
    private String getTagTypeFromHext(String hext) {
        if (hext == null || hext.isEmpty()) {
            return "06";
        }
        
        // 移除0x前缀并转换为大写
        String cleanHext = hext.replace("0x", "").replace("0X", "").toUpperCase();
        
        // 根据Hext值映射TagType
        switch (cleanHext) {
            case "0006":
                return "06";
            case "0007":
                return "07";
            case "0008":
                return "08";
            default:
                return "06";
        }
    }
}