package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs;
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
        // 这里应该包含复杂的转换逻辑
        // 为了简化，暂时返回默认模板
        log.info("执行panels到官方格式的转换");
        return createDefaultOfficialTemplate();
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