package com.pandatech.downloadcf.service.mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs;
import com.pandatech.downloadcf.util.ImageMagickProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;
import javax.imageio.ImageIO;

/**
 * MQTT模板转换器
 * 专门负责模板格式转换和优化
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MqttTemplateConverter {

    private final ObjectMapper objectMapper;
    private final ImageMagickProcessor imageMagickProcessor;

    /**
     * 优化和去重Items，解决重复元素和位置冲突问题
     */
    public List<Map<String, Object>> optimizeAndDeduplicateItems(List<Map<String, Object>> items) {
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
     * 转换为官方模板格式
     */
    public String convertToOfficialTemplate(PrintTemplateDesignWithBLOBs template) {
        log.info("MQTT模板转换开始 - 模板ID: {}, 模板名称: {}", template.getId(), template.getName());
        
        try {
            String extJson = template.getExtJson();
            String content = template.getContent();
            
            String jsonToParse = extJson;
            if (jsonToParse == null || jsonToParse.trim().isEmpty()) {
                jsonToParse = content;
            }
            
            if (jsonToParse == null || jsonToParse.trim().isEmpty()) {
                log.warn("模板数据为空，返回空字符串");
                return "";
            }
            
            JsonNode rootNode = objectMapper.readTree(jsonToParse);
            
            // 检查是否已经是官方格式
            if (rootNode.has("Items")) {
                log.debug("检测到官方格式模板，直接返回");
                return jsonToParse;
            }
            
            // 检查是否包含designConfig字段
            if (rootNode.has("designConfig")) {
                log.info("检测到designConfig格式模板，开始转换");
                return convertDesignConfigToOfficialFormat(rootNode);
            }
            
            // 检查是否是panels格式
            if (rootNode.has("panels")) {
                log.debug("检测到panels格式模板，开始转换");
                return convertPanelsToOfficialFormat(rootNode);
            }
            
            log.warn("未识别的模板格式，返回空字符串");
            return "";
            
        } catch (Exception e) {
            log.error("MQTT模板转换失败", e);
            return "";
        }
    }
    
    /**
     * 转换designConfig格式到官方格式
     */
    private String convertDesignConfigToOfficialFormat(JsonNode rootNode) throws JsonProcessingException {
        JsonNode designConfig = rootNode.get("designConfig");
        return convertPanelsToOfficialFormat(designConfig);
    }
    
    /**
     * 转换panels格式到官方格式
     */
    private String convertPanelsToOfficialFormat(JsonNode rootNode) throws JsonProcessingException {
        Map<String, Object> officialTemplate = new HashMap<>();
        officialTemplate.put("Version", "1.0");
        officialTemplate.put("TagType", "06");
        officialTemplate.put("Hext", "0x0006");
        officialTemplate.put("Rotate", 0);
        
        List<Map<String, Object>> items = new ArrayList<>();
        
        JsonNode panelsNode = rootNode.get("panels");
        if (panelsNode != null && panelsNode.isArray() && panelsNode.size() > 0) {
            JsonNode firstPanel = panelsNode.get(0);
            
            // 设置模板尺寸
            if (firstPanel.has("width")) {
                officialTemplate.put("Width", firstPanel.get("width").asInt());
            }
            if (firstPanel.has("height")) {
                officialTemplate.put("Height", firstPanel.get("height").asInt());
            }
            
            // 转换printElements为Items
            if (firstPanel.has("printElements")) {
                JsonNode printElements = firstPanel.get("printElements");
                log.info("找到printElements，元素数量: {}", printElements.size());
                
                for (JsonNode element : printElements) {
                    Map<String, Object> item = convertPrintElementToItem(element);
                    if (item != null) {
                        items.add(item);
                    }
                }
            }
        }
        
        // 优化和去重Items
        items = optimizeAndDeduplicateItems(items);
        officialTemplate.put("Items", items);
        
        log.info("MQTT模板转换完成，生成Items数量: {}", items.size());
        return objectMapper.writeValueAsString(officialTemplate);
    }
    
    /**
     * 将printElement转换为Item格式
     */
    private Map<String, Object> convertPrintElementToItem(JsonNode element) {
        try {
            if (!element.has("options") || !element.has("printElementType")) {
                return null;
            }
            
            JsonNode options = element.get("options");
            JsonNode elementType = element.get("printElementType");
            
            Map<String, Object> item = new HashMap<>();
            
            // 基础属性
            item.put("Type", elementType.get("type").asText("text"));
            
            // 位置和尺寸转换
            double left = options.has("left") ? options.get("left").asDouble() : 0;
            double top = options.has("top") ? options.get("top").asDouble() : 0;
            double width = options.has("width") ? options.get("width").asDouble() : 50;
            double height = options.has("height") ? options.get("height").asDouble() : 20;
            
            // 坐标转换
            int x = (int) Math.round(left * 250.0 / 750.0);
            int y = (int) Math.round(top * 122.0 / 345.0);
            int w = (int) Math.round(width * 250.0 / 750.0);
            int h = (int) Math.round(height * 122.0 / 345.0);
            
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
            String type = elementType.get("type").asText("text");
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
                    item.put("Barheight", (double) h);
                    item.put("Bartype", "code128");
                    item.put("Barwidth", w);
                    item.put("Fontinval", 1);
                    item.put("Showtext", 1);
                } else if ("qrcode".equals(textType)) {
                    item.put("Type", "qrcode");
                    // 二维码使用默认字体大小16
                    item.put("FontSize", 16);
                }
            }
            
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
            return 0;
        }
        
        String textAlign = textAlignNode.asText("left");
        switch (textAlign.toLowerCase()) {
            case "center":
                return 1;
            case "right":
                return 2;
            default:
                return 0;
        }
    }

    /**
     * 从官方模板JSON中提取TagType
     */
    public String extractTagTypeFromOfficialTemplate(String officialJson) {
        try {
            JsonNode rootNode = objectMapper.readTree(officialJson);
            JsonNode tagTypeNode = rootNode.path("tagtype");
            return tagTypeNode.asText("2"); // 默认返回"2"
        } catch (Exception e) {
            log.warn("提取TagType失败，使用默认值: {}", e.getMessage());
            return "2";
        }
    }
}