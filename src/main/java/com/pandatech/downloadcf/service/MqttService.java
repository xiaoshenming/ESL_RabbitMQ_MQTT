package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import com.pandatech.downloadcf.entity.ActExtTemplatePrint;
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

    private final ActExtTemplatePrintMapper templatePrintMapper;
    private final PandaEslMapper eslMapper;
    private final PandaProductMapper productMapper;
    private final ObjectMapper objectMapper;

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(@Header(MqttHeaders.RECEIVED_TOPIC) String topic, String payload) {
        log.info("接收到MQTT消息 - 主题: {}, 内容: {}", topic, payload);
        // 假设 /templ/loadtemple 是一个特定的主题或包含在payload中用于区分
        if (topic.contains("loadtemple")) {
            try {
                // 这里模拟根据payload（可能是价签ID）加载模板和产品数据
                // 实际场景中，payload的格式需要根据业务确定
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

                ActExtTemplatePrint template = templatePrintMapper.selectByPrimaryKey(product.getEslTemplateId());
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

            ActExtTemplatePrint template = templatePrintMapper.selectByPrimaryKey(product.getEslTemplateId());
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

            ActExtTemplatePrint template = templatePrintMapper.selectByPrimaryKey(product.getEslTemplateId());
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

    

    private String convertToOfficialTemplate(String customJson) {
    // 假设 ActExtTemplatePrint 有 getContent() 方法，如果没有，根据实体调整
    // 这里简化实现

        // 实现转换逻辑：从自定义格式到官方格式
        // 假设customJson是自定义JSON，映射到官方格式
        // 这里简化，实际需解析并转换
        Map<String, Object> official = new HashMap<>();
        official.put("Items", new ArrayList<>());
        official.put("Name", "U");
        official.put("Size", "250, 122");
        official.put("TagType", "06");
        official.put("Version", 10);
        official.put("height", "122");
        official.put("hext", "6");
        official.put("rgb", "3");
        official.put("wext", "0");
        official.put("width", "250");
        try {
            return objectMapper.writeValueAsString(official);
        } catch (JsonProcessingException e) {
            log.error("转换模板失败", e);
            return "{}";
        }
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
            data.put("url", "http://esl.openesl.cn/api/res/templ/loadtemple");
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

    private String getTemplateFileName(String tagType) {
        // 根据tagType返回文件名，如 "06" -> "TAG_1C.json"
        return "TAG_" + Integer.toHexString(Integer.parseInt(tagType)).toUpperCase() + ".json";
    }

    private String generateWtagJson(PandaEsl esl, PandaProduct product, ActExtTemplatePrint template, String md5) {
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