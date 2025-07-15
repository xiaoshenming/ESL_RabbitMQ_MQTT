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
import com.pandatech.downloadcf.config.TemplateConfig;
import com.pandatech.downloadcf.exception.TemplateException;
import com.pandatech.downloadcf.entity.ActExtTemplatePrintWithBLOBs;
import com.pandatech.downloadcf.entity.PandaEsl;
import com.pandatech.downloadcf.entity.PandaProduct;
import com.pandatech.downloadcf.mapper.ActExtTemplatePrintMapper;
import com.pandatech.downloadcf.mapper.PandaEslMapper;
import com.pandatech.downloadcf.mapper.PandaProductMapper;
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

    private final ActExtTemplatePrintMapper templatePrintMapper;
    private final PandaEslMapper eslMapper;
    private final PandaProductMapper productMapper;
    private final ObjectMapper objectMapper;
    private final ScreenTypeConverter screenTypeConverter;
    private final TemplateValidator templateValidator;
    private final TemplateConfig templateConfig;

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(@Header(MqttHeaders.RECEIVED_TOPIC) String topic, String payload) {
        log.info("接收到MQTT消息 - 主题: {}, 内容: {}", topic, payload);
        // 假设 /templ/loadtemple 是一个特定的主题或包含在payload中用于区分
        if (topic.contains("loadtemple")) {
            try {
                // 这里模拟根据payload（可能是价签ID）加载模板和产品数据
                // 实际场景中，payload的格式需要根据业务确定
                @SuppressWarnings("unchecked")
                Map<String, Object> request = objectMapper.readValue(payload, HashMap.class);
                String eslId = (String) request.get("eslId");

                PandaEsl esl = eslMapper.selectByPrimaryKey(eslId);
                if (esl == null) {
                    log.error("未找到价签: {}", eslId);
                    return;
                }

                PandaProduct product = productMapper.selectByPrimaryKey(esl.getBoundProductId());
                if (product == null) {
                    log.error("未找到产品: {}", esl.getBoundProductId());
                    return;
                }

                ActExtTemplatePrintWithBLOBs template = templatePrintMapper.selectByPrimaryKey(product.getEslTemplateId());
                if (template == null) {
                    log.error("未找到模板: {}", product.getEslTemplateId());
                    return;
                }

                // 处理模板下发
                processTemplateDownload(eslId);
                log.info("成功为价签 {} 处理模板下发", eslId);

            } catch (Exception e) {
                log.error("处理MQTT消息失败", e);
            }
        }
    }

    @Qualifier("mqttOutboundChannel")
    private final MessageChannel mqttOutboundChannel;

    public void processTemplateDownload(String eslId) {
        try {
            PandaEsl esl = eslMapper.selectByPrimaryKey(eslId);
            if (esl == null) {
                log.error("未找到价签: {}", eslId);
                return;
            }

            PandaProduct product = productMapper.selectByPrimaryKey(esl.getBoundProductId());
            if (product == null) {
                log.error("未找到产品: {}", esl.getBoundProductId());
                return;
            }

            ActExtTemplatePrintWithBLOBs template = templatePrintMapper.selectByPrimaryKey(product.getEslTemplateId());
            if (template == null) {
                log.error("未找到模板: {}", product.getEslTemplateId());
                return;
            }

            // 转换模板到官方格式
            String officialTemplateJson = convertToOfficialTemplate(template.getContent());

            // 计算MD5
            String md5 = calculateMD5(officialTemplateJson);

            // 生成tmpllist消息
            String tmpllistJson = generateTmpllistJson(template.getId(), md5, esl.getTenantId(), product.getStoreCode(), template.getTagType());
            sendMqttMessage("esl/server/data/" + product.getStoreCode(), tmpllistJson.getBytes());

            // 生成wtag消息
            String wtagJson = generateWtagJson(esl, product, template, md5);
            sendMqttMessage("esl/server/data/" + product.getStoreCode(), wtagJson.getBytes());

            log.info("模板下发成功 for eslId: {}", eslId);
        } catch (Exception e) {
            log.error("处理模板下发失败 for eslId: {}", eslId, e);
        }
    }

    public void processRefresh(String eslId) {
        try {
            PandaEsl esl = eslMapper.selectByPrimaryKey(eslId);
            if (esl == null) {
                log.error("未找到价签: {}", eslId);
                return;
            }

            PandaProduct product = productMapper.selectByPrimaryKey(esl.getBoundProductId());
            if (product == null) {
                log.error("未找到产品: {}", esl.getBoundProductId());
                return;
            }

            ActExtTemplatePrintWithBLOBs template = templatePrintMapper.selectByPrimaryKey(product.getEslTemplateId());
            if (template == null) {
                log.error("未找到模板: {}", product.getEslTemplateId());
                return;
            }

            // 对于刷新，不需要tmpllist，只需wtag
            // 假设checksum从数据库或缓存获取先前MD5
            String checksum = getCachedChecksum(template.getId());  // 实现获取缓存MD5的方法
            if (checksum == null) {
                String officialTemplateJson = convertToOfficialTemplate(template.getContent());
                checksum = calculateMD5(officialTemplateJson);
                // 可选：发送tmpllist如果需要
            }

            String wtagJson = generateWtagJson(esl, product, template, checksum);
            sendMqttMessage("esl/server/data/" + product.getStoreCode(), wtagJson.getBytes());

            log.info("价签刷新成功 for eslId: {}", eslId);
        } catch (Exception e) {
            log.error("处理价签刷新失败 for eslId: {}", eslId, e);
        }
    }

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
    public String convertToOfficialTemplate(String customJson) throws JsonProcessingException {
        if (customJson == null || customJson.trim().isEmpty()) {
            return createDefaultOfficialTemplate();
        }

        try {
            JsonNode rootNode = objectMapper.readTree(customJson);
            
            // 检查是否已经是官方格式（包含Items字段）
            if (rootNode.has("Items")) {
                log.debug("检测到官方格式模板，直接返回");
                // 确保FontFamily都是Zfull-GB
                String result = ensureCorrectFontFamily(customJson);
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
                String result = convertPanelsToOfficialFormat(rootNode);
                 // 验证模板
                 TemplateValidator.ValidationResult validation = templateValidator.validateTemplateContent(result);
                 if (validation.hasWarnings()) {
                     log.warn("模板验证警告: {}", validation.getWarnings());
                 }
                 return result;
            }
            
            // 其他格式，尝试提取基本信息
            log.warn("未识别的模板格式，使用默认转换");
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
    private String convertPanelsToOfficialFormat(JsonNode rootNode) throws JsonProcessingException {
        Map<String, Object> official = new HashMap<>();
        List<Map<String, Object>> items = new ArrayList<>();
        
        // 默认值
        official.put("Name", "U");
        official.put("Size", "250, 122");
        official.put("TagType", "06");
        official.put("Version", 10);
        official.put("height", "122");
        official.put("hext", "6");
        official.put("rgb", "3");
        official.put("wext", "0");
        official.put("width", "250");
        
        JsonNode panels = rootNode.get("panels");
        if (panels.isArray()) {
            for (JsonNode panel : panels) {
                // 提取面板的基本信息
                if (panel.has("width") && panel.has("height")) {
                    String width = panel.get("width").asText();
                    String height = panel.get("height").asText();
                    official.put("Size", width + ", " + height);
                    official.put("width", width);
                    official.put("height", height);
                }
                
                // 转换printElements
                if (panel.has("printElements")) {
                    JsonNode printElements = panel.get("printElements");
                    if (printElements.isArray()) {
                        for (JsonNode element : printElements) {
                            Map<String, Object> item = convertPrintElementToItem(element);
                            if (item != null) {
                                items.add(item);
                            }
                        }
                    }
                }
            }
        }
        
        official.put("Items", items);
        return objectMapper.writeValueAsString(official);
    }

    /**
     * 将printElement转换为官方格式的Item
     */
    private Map<String, Object> convertPrintElementToItem(JsonNode element) {
        if (!element.has("options")) {
            return null;
        }
        
        JsonNode options = element.get("options");
        Map<String, Object> item = new HashMap<>();
        
        // 基本属性（使用配置中的默认值）
        TemplateConfig.DefaultTemplate defaultConfig = templateConfig.getDefaultTemplate();
        item.put("Type", "text");
        item.put("FontFamily", defaultConfig.getFontFamily());
        item.put("FontColor", defaultConfig.getFontColor());
        item.put("Background", defaultConfig.getBackground());
        item.put("BorderColor", defaultConfig.getBorderColor());
        item.put("BorderStyle", defaultConfig.getBorderStyle());
        item.put("FontStyle", defaultConfig.getFontStyle());
        item.put("FontSpace", defaultConfig.getFontSpace());
        item.put("TextAlign", defaultConfig.getTextAlign());
        item.put("DataKeyStyle", defaultConfig.getDataKeyStyle());
        
        // 位置和尺寸
        int x = options.has("left") ? options.get("left").asInt() : 0;
        int y = options.has("top") ? options.get("top").asInt() : 0;
        int width = options.has("width") ? options.get("width").asInt() : 50;
        int height = options.has("height") ? options.get("height").asInt() : 20;
        
        item.put("x", x);
        item.put("y", y);
        item.put("width", width);
        item.put("height", height);
        item.put("Location", x + ", " + y);
        item.put("Size", width + ", " + height);
        
        // 字体属性
        if (options.has("fontSize")) {
            item.put("FontSize", options.get("fontSize").asInt());
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
        
        // 数据绑定
        if (options.has("field")) {
            item.put("DataKey", options.get("field").asText());
        } else {
            item.put("DataKey", "");
        }
        
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
        
        return item;
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

    private String generateTmpllistJson(String templateId, String md5, String tid, String shop, String tagType) {
        try {
            Map<String, Object> json = new HashMap<>();
            json.put("command", "tmpllist");
            Map<String, Object> data = new HashMap<>();
            data.put("url", templateBaseUrl);
            List<Map<String, String>> tmpls = new ArrayList<>();
            Map<String, String> tmpl = new HashMap<>();
            tmpl.put("name", getTemplateFileName(tagType));
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

    public void sendTemplateToMqtt(String topic, ActExtTemplatePrintWithBLOBs template, String screenType) {
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
            String officialTemplate = convertToOfficialTemplate(template.getContent());
            
            // 如果提供了screenType，需要更新模板中的TagType
            if (screenType != null && !screenType.isEmpty()) {
                officialTemplate = updateTagTypeInTemplate(officialTemplate, screenType);
            }
            
            // 生成带屏幕类型的模板文件名
            String templateFileName = screenTypeConverter.generateTemplateFileName(template.getName(), screenType);
            
            // 创建MQTT消息
            Map<String, Object> message = new HashMap<>();
            if (templateConfig.getMqtt().isIncludeTemplateContent()) {
                message.put("template", officialTemplate);
            }
            message.put("name", templateFileName);
            message.put("id", template.getId());
            message.put("screenType", screenType != null ? screenType : "1C");
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
    
    /**
     * 更新模板中的TagType字段
     */
    private String updateTagTypeInTemplate(String officialTemplate, String screenType) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(officialTemplate);
        Map<String, Object> result = objectMapper.convertValue(rootNode, Map.class);
        
        // 根据屏幕类型设置TagType
        String tagType = screenTypeConverter.convertScreenTypeToTagType(screenType);
        if (tagType != null) {
            result.put("TagType", tagType);
        }
        
        return objectMapper.writeValueAsString(result);
    }

    private String getTemplateFileName(String tagType) {
        // 根据tagType返回文件名，如 "06" -> "TAG_1C.json"
        return "TAG_" + Integer.toHexString(Integer.parseInt(tagType)).toUpperCase() + ".json";
    }

    private String generateWtagJson(PandaEsl esl, PandaProduct product, ActExtTemplatePrintWithBLOBs template, String md5) {
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