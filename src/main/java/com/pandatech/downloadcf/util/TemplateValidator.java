package com.pandatech.downloadcf.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 模板验证工具类
 * 用于验证模板数据的完整性和正确性
 */
@Slf4j
@Component
public class TemplateValidator {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * 验证模板内容是否有效
     * @param content 模板内容JSON字符串
     * @return 验证结果
     */
    public ValidationResult validateTemplateContent(String content) {
        ValidationResult result = new ValidationResult();
        
        if (content == null || content.trim().isEmpty()) {
            result.addError("模板内容不能为空");
            return result;
        }
        
        try {
            JsonNode rootNode = objectMapper.readTree(content);
            
            // 检查是否是官方格式
            if (rootNode.has("Items")) {
                validateOfficialFormat(rootNode, result);
            }
            // 检查是否是panels格式
            else if (rootNode.has("panels")) {
                validatePanelsFormat(rootNode, result);
            }
            else {
                result.addWarning("未识别的模板格式，将使用默认转换");
            }
            
        } catch (Exception e) {
            result.addError("模板内容JSON格式错误: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 验证官方格式模板
     */
    private void validateOfficialFormat(JsonNode rootNode, ValidationResult result) {
        // 检查必需字段
        String[] requiredFields = {"Items", "Name", "Size", "TagType", "Version", "width", "height"};
        for (String field : requiredFields) {
            if (!rootNode.has(field)) {
                result.addWarning("缺少字段: " + field);
            }
        }
        
        // 验证Items数组
        JsonNode items = rootNode.get("Items");
        if (items != null && items.isArray()) {
            for (int i = 0; i < items.size(); i++) {
                validateItem(items.get(i), i, result);
            }
        }
        
        // 验证TagType
        JsonNode tagType = rootNode.get("TagType");
        if (tagType != null) {
            String tagTypeValue = tagType.asText();
            if (!isValidTagType(tagTypeValue)) {
                result.addWarning("TagType值可能无效: " + tagTypeValue);
            }
        }
    }
    
    /**
     * 验证panels格式模板
     */
    private void validatePanelsFormat(JsonNode rootNode, ValidationResult result) {
        JsonNode panels = rootNode.get("panels");
        if (panels == null || !panels.isArray()) {
            result.addError("panels字段必须是数组");
            return;
        }
        
        for (int i = 0; i < panels.size(); i++) {
            JsonNode panel = panels.get(i);
            validatePanel(panel, i, result);
        }
    }
    
    /**
     * 验证单个面板
     */
    private void validatePanel(JsonNode panel, int index, ValidationResult result) {
        String prefix = "面板[" + index + "]";
        
        // 检查基本属性
        if (!panel.has("width") || !panel.has("height")) {
            result.addWarning(prefix + "缺少width或height属性");
        }
        
        // 验证printElements
        JsonNode printElements = panel.get("printElements");
        if (printElements != null && printElements.isArray()) {
            for (int i = 0; i < printElements.size(); i++) {
                validatePrintElement(printElements.get(i), prefix + ".printElements[" + i + "]", result);
            }
        }
    }
    
    /**
     * 验证打印元素
     */
    private void validatePrintElement(JsonNode element, String prefix, ValidationResult result) {
        if (!element.has("options")) {
            result.addWarning(prefix + "缺少options属性");
            return;
        }
        
        JsonNode options = element.get("options");
        
        // 检查位置属性
        String[] positionFields = {"left", "top", "width", "height"};
        for (String field : positionFields) {
            if (!options.has(field)) {
                result.addWarning(prefix + ".options缺少" + field + "属性");
            }
        }
    }
    
    /**
     * 验证单个Item
     */
    private void validateItem(JsonNode item, int index, ValidationResult result) {
        String prefix = "Items[" + index + "]";
        
        // 检查必需字段
        String[] requiredFields = {"Type", "FontFamily", "x", "y", "width", "height"};
        for (String field : requiredFields) {
            if (!item.has(field)) {
                result.addWarning(prefix + "缺少字段: " + field);
            }
        }
        
        // 验证FontFamily
        JsonNode fontFamily = item.get("FontFamily");
        if (fontFamily != null && !"Zfull-GB".equals(fontFamily.asText())) {
            result.addWarning(prefix + "FontFamily建议使用Zfull-GB");
        }
    }
    
    /**
     * 检查TagType是否有效
     */
    private boolean isValidTagType(String tagType) {
        // 常见的TagType值
        String[] validTagTypes = {"06", "07", "08", "09", "10", "11", "12", "13", "14", "15"};
        for (String valid : validTagTypes) {
            if (valid.equals(tagType)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 验证结果类
     */
    public static class ValidationResult {
        private final List<String> errors = new ArrayList<>();
        private final List<String> warnings = new ArrayList<>();
        
        public void addError(String error) {
            errors.add(error);
        }
        
        public void addWarning(String warning) {
            warnings.add(warning);
        }
        
        public boolean hasErrors() {
            return !errors.isEmpty();
        }
        
        public boolean hasWarnings() {
            return !warnings.isEmpty();
        }
        
        public List<String> getErrors() {
            return new ArrayList<>(errors);
        }
        
        public List<String> getWarnings() {
            return new ArrayList<>(warnings);
        }
        
        public boolean isValid() {
            return errors.isEmpty();
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (!errors.isEmpty()) {
                sb.append("错误: ").append(errors).append("; ");
            }
            if (!warnings.isEmpty()) {
                sb.append("警告: ").append(warnings);
            }
            return sb.toString();
        }
    }
}