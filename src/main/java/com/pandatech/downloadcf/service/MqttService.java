package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs;
import com.pandatech.downloadcf.exception.TemplateException;
import com.pandatech.downloadcf.util.TemplateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.UUID;

/**
 * MQTT服务类 - 优化版
 * 处理MQTT消息的发送和接收，支持模板转换和消息格式优化
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MqttService {

    @Value("${app.template.base-url:http://localhost:8999/api/res/templ/loadtemple}")
    private String templateBaseUrl;

    @Qualifier("mqttOutboundChannel")
    private final MessageChannel mqttOutboundChannel;
    private final ObjectMapper objectMapper;

    // 默认配置值
    private static final String DEFAULT_FONT_FAMILY = "阿里普惠";
    private static final int DEFAULT_VERSION = 10;
    private static final int DEFAULT_HEXT = 6;
    private static final int DEFAULT_RGB = 3;
    private static final int DEFAULT_WEXT = 0;
    private static final String DEFAULT_TAG_TYPE = "06";
    private static final String DEFAULT_SIZE = "250, 122";
    private static final int DEFAULT_WIDTH = 250;
    private static final int DEFAULT_HEIGHT = 122;

    /**
     * 处理接收到的MQTT消息
     */
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(@Header(MqttHeaders.RECEIVED_TOPIC) String topic, String payload) {
        log.info("接收到MQTT消息 - 主题: {}, 内容: {}", topic, payload);
        // TODO: 根据新数据库结构实现消息处理
    }

    /**
     * 发送模板到MQTT - 优化版
     */
    public void sendTemplateToMqtt(String shop, String templateId, String templateName) {
        try {
            // 假设从数据库获取模板，这里使用用户提供的示例数据
            PrintTemplateDesignWithBLOBs template = new PrintTemplateDesignWithBLOBs();
            template.setId(templateId);
            template.setName(templateName);
            template.setContent(getUserProvidedTemplateContent()); // 使用用户提供的模板数据

            String officialJson = convertToOfficialTemplate(template);
            String md5 = calculateMD5(officialJson);

            String topic = "esl/server/data/" + shop;
            String message = buildTmplListMessage(shop, templateId, templateName, md5);

            sendMqttMessage(topic, message.getBytes());

            log.info("模板消息发送成功 - 主题: {}, 模板ID: {}", topic, templateId);
        } catch (Exception e) {
            log.error("发送模板到MQTT失败: {}", e.getMessage(), e);
            throw new TemplateException.MqttSendException("MQTT发送失败", e);
        }
    }

    /**
     * 构建tmpllist格式消息 - 严格匹配用户指定结构
     */
    private String buildTmplListMessage(String shop, String templateId, String templateName, String md5) throws JsonProcessingException {
        Map<String, Object> message = new HashMap<>();
        message.put("shop", shop);
        message.put("id", UUID.randomUUID().toString());
        message.put("command", "tmpllist");
        message.put("timestamp", System.currentTimeMillis() / 1000);

        Map<String, Object> data = new HashMap<>();
        data.put("url", templateBaseUrl);
        data.put("tid", "396a5189-53d8-4354-bcfa-27d57d9d69ad");

        List<Map<String, String>> tmpls = new ArrayList<>();
        Map<String, String> tmpl = new HashMap<>();
        tmpl.put("name", templateName + "_06.json");
        tmpl.put("id", templateId);
        tmpl.put("md5", md5);
        tmpls.add(tmpl);
        data.put("tmpls", tmpls);

        message.put("data", data);
        return objectMapper.writeValueAsString(message);
    }

    /**
     * 将自定义模板转换为官方格式 - 优化版
     */
    public String convertToOfficialTemplate(PrintTemplateDesignWithBLOBs template) {
        try {
            String content = template.getContent();
            if (content == null || content.isEmpty()) {
                throw new TemplateException.TemplateConversionException("模板内容为空");
            }

            JsonNode rootNode = objectMapper.readTree(content);
            if (rootNode.has("Items")) {
                return ensureCorrectFontFamily(content);
            }

            if (rootNode.has("panels")) {
                return convertPanelsToOfficial(rootNode, template);
            }

            throw new TemplateException.InvalidTemplateFormatException("未知模板格式");
        } catch (Exception e) {
            log.error("模板转换失败: {}", e.getMessage());
            throw new TemplateException.TemplateConversionException("转换失败", e);
        }
    }

    private String convertPanelsToOfficial(JsonNode rootNode, PrintTemplateDesignWithBLOBs template) throws JsonProcessingException {
        Map<String, Object> official = new HashMap<>();
        List<Map<String, Object>> items = new ArrayList<>();

        // 基本信息
        official.put("Name", template.getName());
        official.put("Version", DEFAULT_VERSION);
        official.put("hext", String.valueOf(DEFAULT_HEXT));
        official.put("rgb", String.valueOf(DEFAULT_RGB));
        official.put("wext", String.valueOf(DEFAULT_WEXT));
        official.put("TagType", DEFAULT_TAG_TYPE);
        official.put("Size", DEFAULT_SIZE);
        official.put("width", String.valueOf(DEFAULT_WIDTH));
        official.put("height", String.valueOf(DEFAULT_HEIGHT));

        // 转换panels
        JsonNode panels = rootNode.get("panels");
        if (panels != null && panels.isArray() && panels.size() > 0) {
            JsonNode firstPanel = panels.get(0);
            int width = firstPanel.get("width").asInt(DEFAULT_WIDTH);
            int height = firstPanel.get("height").asInt(DEFAULT_HEIGHT);
            official.put("Size", width + ", " + height);
            official.put("width", String.valueOf(width));
            official.put("height", String.valueOf(height));

            // 转换printElements到Items
            JsonNode printElements = firstPanel.get("printElements");
            if (printElements != null && printElements.isArray()) {
                for (JsonNode element : printElements) {
                    Map<String, Object> item = convertElementToItem(element);
                    if (item != null) {
                        items.add(item);
                    }
                }
            }
        }

        official.put("Items", items);
        return objectMapper.writeValueAsString(official);
    }

    private Map<String, Object> convertElementToItem(JsonNode element) {
        Map<String, Object> item = new HashMap<>();
        String type = element.get("elementType").asText().toLowerCase();
        item.put("Type", type);

        // Location and Size
        item.put("Location", element.get("x").asText() + "," + element.get("y").asText());
        item.put("Size", element.get("width").asText() + "," + element.get("height").asText());

        // Common properties
        if (element.has("fontFamily")) {
            item.put("FontFamily", element.get("fontFamily").asText());
        } else {
            item.put("FontFamily", DEFAULT_FONT_FAMILY);
        }
        if (element.has("fontSize")) {
            item.put("FontSize", element.get("fontSize").asText());
        }
        if (element.has("bold")) {
            item.put("Bold", element.get("bold").asBoolean() ? "1" : "0");
        }
        if (element.has("italic")) {
            item.put("Italic", element.get("italic").asBoolean() ? "1" : "0");
        }
        if (element.has("underline")) {
            item.put("Underline", element.get("underline").asBoolean() ? "1" : "0");
        }
        if (element.has("align")) {
            item.put("Align", getAlignValue(element.get("align").asText()));
        }

        // Type-specific properties
        if ("text".equals(type)) {
            item.put("Content", element.get("content").asText());
        } else if ("barcode".equals(type) || "qrcode".equals(type)) {
            item.put("Content", element.get("content").asText());
            item.put("Thickness", element.has("thickness") ? element.get("thickness").asText() : "1");
        } else if ("image".equals(type)) {
            item.put("Image", element.get("image").asText());
        } else if ("line".equals(type)) {
            item.put("Thickness", element.get("thickness").asText());
            item.put("Direction", element.get("direction").asText());
        }

        // Add more fields as per official format
        item.put("Color", "0"); // Default black
        item.put("Rotation", "0");

        return item;
    }

    private String getAlignValue(String align) {
        switch (align.toLowerCase()) {
            case "left": return "0";
            case "center": return "1";
            case "right": return "2";
            default: return "0";
        }
    }

    private String ensureCorrectFontFamily(String json) throws JsonProcessingException {
        JsonNode root = objectMapper.readTree(json);
        if (root.has("Items")) {
            for (JsonNode item : root.get("Items")) {
                if (item.has("FontFamily")) {
                    ((com.fasterxml.jackson.databind.node.ObjectNode) item).put("FontFamily", DEFAULT_FONT_FAMILY);
                }
            }
        }
        return objectMapper.writeValueAsString(root);
    }

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

    private void sendMqttMessage(String topic, byte[] payload) {
        Message<byte[]> message = MessageBuilder
                .withPayload(payload)
                .setHeader("mqtt_topic", topic)
                .build();
        mqttOutboundChannel.send(message);
    }

    private String getUserProvidedTemplateContent() {
        // 用户提供的模板数据
        return "{\n  \"panels\": [\n    {\n      \"index\": 0,\n      \"name\": \"2\",\n      \"paperType\": \"CUSTOM\",\n      \"width\": 250,\n      \"height\": 122,\n      \"paperHeader\": 0,\n      \"paperFooter\": 345.82677165354335,\n      \"printElements\": [],\n      \"paperNumberContinue\": true,\n      \"eslConfig\": {\n        \"screenType\": \"2.13T\",\n        \"pixelWidth\": 250,\n        \"pixelHeight\": 122,\n        \"colorMode\": {\n          \"black\": true,\n          \"white\": true,\n          \"red\": true,\n          \"yellow\": false\n        },\n        \"orientation\": \"LANDSCAPE\"\n      }\n    }\n  ]\n}";
    }
}