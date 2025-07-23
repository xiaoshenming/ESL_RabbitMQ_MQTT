package com.pandatech.downloadcf.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.config.TemplateConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 屏幕类型转换工具类
 * 用于从模板数据中提取和转换屏幕类型信息
 */
@Slf4j
@Component
public class ScreenTypeConverter {
    
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    @Autowired
    private TemplateConfig templateConfig;

    /**
     * 硬件型号到屏幕类型的映射字典
     * 基于err.md文档中的modelconvert映射关系
     */
    private static final Map<String, String> MODEL_CONVERT = new HashMap<String, String>() {{
        put("30", "01");  // 对应 ESL213R - 2.13寸红黑白屏幕
        put("32", "32");
        put("33", "29");
        put("36", "04");
        put("39", "39");
        put("3A", "07");  // 对应 ESL420R - 4.2寸红黑白屏幕
        put("5B", "5B");
        put("3D", "08");
        put("3F", "3F");
        put("54", "54");
        put("40", "0A");
        put("43", "0A");
        put("44", "0F");
    }};

    /**
     * TagType到屏幕类型的映射
     * 基于U_06.json示例中的TagType字段
     */
    private static final Map<String, String> TAG_TYPE_CONVERT = new HashMap<String, String>() {{
        put("06", "06");  // 默认类型
        put("01", "01");  // 2.13寸
        put("07", "07");  // 4.2寸
        put("09", "09");  // 7.5寸
    }};

    /**
     * 从模板数据中提取屏幕类型
     * 优先级：extJson中的配置 > content中的TagType > 默认值
     * 
     * @param content 模板内容JSON字符串
     * @param extJson 扩展配置JSON字符串
     * @param tagType 数据库中的tagType字段
     * @return 屏幕类型代码（如"06", "01", "07"等）
     */
    public String extractScreenType(String content, String extJson, String tagType) {
        try {
            // 1. 优先从extJson中提取屏幕类型信息
            String screenTypeFromExt = extractFromExtJson(extJson);
            if (screenTypeFromExt != null) {
                log.debug("从extJson中提取到屏幕类型: {}", screenTypeFromExt);
                return screenTypeFromExt;
            }

            // 2. 从content中的TagType字段提取
            String screenTypeFromContent = extractFromContent(content);
            if (screenTypeFromContent != null) {
                log.debug("从content中提取到屏幕类型: {}", screenTypeFromContent);
                return screenTypeFromContent;
            }

            // 3. 使用数据库中的tagType字段
            if (tagType != null && !tagType.trim().isEmpty()) {
                String convertedType = TAG_TYPE_CONVERT.get(tagType.trim());
                if (convertedType != null) {
                    log.debug("从数据库tagType字段转换得到屏幕类型: {}", convertedType);
                    return convertedType;
                }
                log.debug("直接使用数据库tagType作为屏幕类型: {}", tagType.trim());
                return tagType.trim();
            }

            // 4. 默认返回"06"
            log.warn("无法提取屏幕类型，使用默认值: 06");
            return "06";

        } catch (Exception e) {
            log.error("提取屏幕类型时发生错误，使用默认值06", e);
            return "06";
        }
    }

    /**
     * 从extJson中提取屏幕类型信息
     */
    private String extractFromExtJson(String extJson) {
        if (extJson == null || extJson.trim().isEmpty()) {
            return null;
        }

        try {
            JsonNode extNode = objectMapper.readTree(extJson);
            
            // 检查是否有直接的screenType字段
            if (extNode.has("screenType")) {
                return extNode.get("screenType").asText();
            }

            // 检查是否有tagType字段
            if (extNode.has("tagType")) {
                return extNode.get("tagType").asText();
            }

            // 检查hiprintConfig中的配置
            if (extNode.has("hiprintConfig")) {
                JsonNode configNode = objectMapper.readTree(extNode.get("hiprintConfig").asText());
                if (configNode.has("tagType")) {
                    return configNode.get("tagType").asText();
                }
            }

        } catch (Exception e) {
            log.warn("解析extJson时发生错误: {}", e.getMessage());
        }

        return null;
    }

    /**
     * 从content中提取TagType字段
     */
    private String extractFromContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            return null;
        }

        try {
            JsonNode contentNode = objectMapper.readTree(content);
            
            // 检查是否有TagType字段（如U_06.json示例）
            if (contentNode.has("TagType")) {
                return contentNode.get("TagType").asText();
            }

            // 检查panels数组中的配置
            if (contentNode.has("panels") && contentNode.get("panels").isArray()) {
                JsonNode panels = contentNode.get("panels");
                for (JsonNode panel : panels) {
                    if (panel.has("tagType")) {
                        return panel.get("tagType").asText();
                    }
                }
            }

        } catch (Exception e) {
            log.warn("解析content时发生错误: {}", e.getMessage());
        }

        return null;
    }

    /**
     * 根据硬件型号转换屏幕类型
     * 
     * @param hardwareModel 硬件型号（如"30", "3A"等）
     * @return 屏幕类型代码
     */
    public String convertHardwareModelToScreenType(String hardwareModel) {
        if (hardwareModel == null || hardwareModel.trim().isEmpty()) {
            return "06";
        }

        return templateConfig.getScreenTypeByModel(hardwareModel);
    }

    /**
     * 生成带屏幕类型的模板文件名
     * @param templateName 原始模板名称
     * @param screenType 屏幕类型
     * @return 带屏幕类型后缀的文件名
     */
    public String generateTemplateFileName(String templateName, String screenType) {
        if (templateName == null || templateName.trim().isEmpty()) {
            return "template_" + (screenType != null ? screenType : "1C") + ".json";
        }
        
        String baseName = templateName.trim();
        
        // 移除已存在的.json后缀
        if (baseName.toLowerCase().endsWith(".json")) {
            baseName = baseName.substring(0, baseName.length() - 5);
        }
        
        // 移除已存在的屏幕类型后缀
        Pattern pattern = Pattern.compile("_[0-9]+[A-Z]$");
        Matcher matcher = pattern.matcher(baseName);
        if (matcher.find()) {
            baseName = baseName.substring(0, matcher.start());
        }
        
        // 添加屏幕类型后缀
        if (screenType != null && !screenType.trim().isEmpty() && isValidScreenType(screenType)) {
            baseName += "_" + screenType.trim();
        } else {
            baseName += "_1C"; // 默认屏幕类型
        }
        
        return baseName + ".json";
    }

    /**
     * 验证屏幕类型是否有效
     */
    public boolean isValidScreenType(String screenType) {
        if (screenType == null || screenType.trim().isEmpty()) {
            return false;
        }
        
        // 检查是否在配置的屏幕类型映射中
        return templateConfig.getScreenTypeMapping().getTagTypeToScreenType().containsValue(screenType) ||
               templateConfig.getScreenTypeMapping().getModelToScreenType().containsValue(screenType);
    }
    
    /**
     * 将屏幕类型转换为TagType
     * @param screenType 屏幕类型（如1C, 2C, 3C等）
     * @return TagType（如06, 07, 08等）
     */
    public String convertScreenTypeToTagType(String screenType) {
        if (screenType == null || screenType.trim().isEmpty()) {
            return templateConfig.getDefaultTemplate().getTagType();
        }
        
        return templateConfig.getTagTypeByScreenType(screenType);
    }
}