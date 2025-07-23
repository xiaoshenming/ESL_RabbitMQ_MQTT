package com.pandatech.downloadcf.util;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 模板转换工具类
 */
@Slf4j
public class TemplateUtil {
    
    // 自定义格式的变量模式 ${变量名}
    private static final Pattern CUSTOM_VARIABLE_PATTERN = Pattern.compile("\\$\\{([^}]+)\\}");
    
    // 官方格式的变量模式 {{变量名}}
    private static final Pattern OFFICIAL_VARIABLE_PATTERN = Pattern.compile("\\{\\{([^}]+)\\}\\}");
    
    /**
     * 将自定义格式转换为官方格式
     * ${变量名} -> {{变量名}}
     */
    public static String convertCustomToOfficial(String customTemplate) {
        if (customTemplate == null || customTemplate.isEmpty()) {
            return customTemplate;
        }
        
        Matcher matcher = CUSTOM_VARIABLE_PATTERN.matcher(customTemplate);
        StringBuffer result = new StringBuffer();
        
        while (matcher.find()) {
            String variableName = matcher.group(1);
            matcher.appendReplacement(result, "{{" + variableName + "}}");
        }
        matcher.appendTail(result);
        
        return result.toString();
    }
    
    /**
     * 将官方格式转换为自定义格式
     * {{变量名}} -> ${变量名}
     */
    public static String convertOfficialToCustom(String officialTemplate) {
        if (officialTemplate == null || officialTemplate.isEmpty()) {
            return officialTemplate;
        }
        
        Matcher matcher = OFFICIAL_VARIABLE_PATTERN.matcher(officialTemplate);
        StringBuffer result = new StringBuffer();
        
        while (matcher.find()) {
            String variableName = matcher.group(1);
            matcher.appendReplacement(result, "${" + variableName + "}");
        }
        matcher.appendTail(result);
        
        return result.toString();
    }
    
    /**
     * 替换模板中的变量
     */
    public static String replaceVariables(String template, Map<String, Object> variables) {
        if (template == null || template.isEmpty() || variables == null || variables.isEmpty()) {
            return template;
        }
        
        String result = template;
        
        // 替换自定义格式变量
        Matcher customMatcher = CUSTOM_VARIABLE_PATTERN.matcher(result);
        StringBuffer customResult = new StringBuffer();
        while (customMatcher.find()) {
            String variableName = customMatcher.group(1);
            Object value = variables.get(variableName);
            String replacement = value != null ? value.toString() : "";
            customMatcher.appendReplacement(customResult, Matcher.quoteReplacement(replacement));
        }
        customMatcher.appendTail(customResult);
        result = customResult.toString();
        
        // 替换官方格式变量
        Matcher officialMatcher = OFFICIAL_VARIABLE_PATTERN.matcher(result);
        StringBuffer officialResult = new StringBuffer();
        while (officialMatcher.find()) {
            String variableName = officialMatcher.group(1);
            Object value = variables.get(variableName);
            String replacement = value != null ? value.toString() : "";
            officialMatcher.appendReplacement(officialResult, Matcher.quoteReplacement(replacement));
        }
        officialMatcher.appendTail(officialResult);
        
        return officialResult.toString();
    }
    
    /**
     * 提取模板中的变量名
     */
    public static Map<String, String> extractVariables(String template) {
        Map<String, String> variables = new HashMap<>();
        
        if (template == null || template.isEmpty()) {
            return variables;
        }
        
        // 提取自定义格式变量
        Matcher customMatcher = CUSTOM_VARIABLE_PATTERN.matcher(template);
        while (customMatcher.find()) {
            String variableName = customMatcher.group(1);
            variables.put(variableName, "CUSTOM");
        }
        
        // 提取官方格式变量
        Matcher officialMatcher = OFFICIAL_VARIABLE_PATTERN.matcher(template);
        while (officialMatcher.find()) {
            String variableName = officialMatcher.group(1);
            variables.put(variableName, "OFFICIAL");
        }
        
        return variables;
    }
    
    /**
     * 验证模板格式
     */
    public static boolean isValidTemplate(String template) {
        if (template == null || template.isEmpty()) {
            return true;
        }
        
        try {
            // 检查自定义格式变量是否匹配
            Matcher customMatcher = CUSTOM_VARIABLE_PATTERN.matcher(template);
            while (customMatcher.find()) {
                String variableName = customMatcher.group(1);
                if (variableName.trim().isEmpty()) {
                    return false;
                }
            }
            
            // 检查官方格式变量是否匹配
            Matcher officialMatcher = OFFICIAL_VARIABLE_PATTERN.matcher(template);
            while (officialMatcher.find()) {
                String variableName = officialMatcher.group(1);
                if (variableName.trim().isEmpty()) {
                    return false;
                }
            }
            
            return true;
        } catch (Exception e) {
            log.error("模板验证异常", e);
            return false;
        }
    }
}