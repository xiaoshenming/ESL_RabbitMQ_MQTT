package com.pandatech.downloadcf.brands.yaliang.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 雅量模板提取服务
 * 负责从EXT_JSON中提取templateBase64等模板相关数据
 */
@Slf4j
@Service
public class YaliangTemplateExtractor {
    
    @Autowired
    private ObjectMapper objectMapper;
    
    /**
     * 从EXT_JSON中提取templateBase64
     */
    public String extractTemplateBase64FromExtJson(String extJson) {
        // 添加详细的日志输出来调试EXT_JSON内容
        log.info("=== EXT_JSON调试信息开始 ===");
        log.info("EXT_JSON是否为null: {}", extJson == null);
        if (extJson != null) {
            log.info("EXT_JSON长度: {}", extJson.length());
            log.info("EXT_JSON前200字符: {}", extJson.length() > 200 ? extJson.substring(0, 200) + "..." : extJson);
            log.info("EXT_JSON是否为空字符串: {}", extJson.trim().isEmpty());
        }
        log.info("=== EXT_JSON调试信息结束 ===");
        
        if (extJson == null || extJson.trim().isEmpty()) {
            log.warn("EXT_JSON为空，无法提取templateBase64");
            return generateDefaultImageBase64();
        }
        
        try {
            Map<String, Object> extData = objectMapper.readValue(extJson, Map.class);
            log.info("EXT_JSON解析成功，包含的键: {}", extData.keySet());
            
            String templateBase64 = (String) extData.get("templateBase64");
            log.info("templateBase64字段是否存在: {}", extData.containsKey("templateBase64"));
            log.info("templateBase64字段值是否为null: {}", templateBase64 == null);
            
            if (templateBase64 != null) {
                log.info("templateBase64原始长度: {}", templateBase64.length());
                log.info("templateBase64前100字符: {}", templateBase64.length() > 100 ? templateBase64.substring(0, 100) + "..." : templateBase64);
            }
            
            if (templateBase64 != null && !templateBase64.trim().isEmpty()) {
                // 移除data:image/png;base64,前缀（如果存在）
                if (templateBase64.startsWith("data:image/")) {
                    int commaIndex = templateBase64.indexOf(",");
                    if (commaIndex > 0) {
                        log.info("检测到data:image前缀，移除前缀，原长度: {}, 前缀长度: {}", templateBase64.length(), commaIndex + 1);
                        templateBase64 = templateBase64.substring(commaIndex + 1);
                        log.info("移除前缀后长度: {}", templateBase64.length());
                    }
                }
                
                log.info("从EXT_JSON中成功提取templateBase64，最终大小: {}KB", templateBase64.length() / 1024.0);
                return templateBase64;
            } else {
                log.warn("EXT_JSON中未找到templateBase64字段或字段为空，使用默认图片");
                return generateDefaultImageBase64();
            }
            
        } catch (Exception e) {
            log.error("解析EXT_JSON失败: {}", e.getMessage(), e);
            log.error("EXT_JSON内容: {}", extJson);
            return generateDefaultImageBase64();
        }
    }
    
    /**
     * 生成默认的Base64图片数据
     * 这是一个简单的1x1像素透明PNG图片
     */
    public String generateDefaultImageBase64() {
        // 这是一个1x1像素的透明PNG图片的Base64编码
        String defaultBase64 = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNkYPhfDwAChAI9jU77yQAAAABJRU5ErkJggg==";
        log.info("使用默认图片Base64数据，大小: {}KB", defaultBase64.length() / 1024);
        return defaultBase64;
    }
    
    /**
     * 验证Base64图片数据格式
     */
    public boolean isValidBase64Image(String base64Data) {
        if (base64Data == null || base64Data.trim().isEmpty()) {
            return false;
        }
        
        try {
            // 简单的Base64格式验证
            java.util.Base64.getDecoder().decode(base64Data);
            return true;
        } catch (Exception e) {
            log.warn("Base64数据格式验证失败: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * 清理Base64数据前缀
     */
    public String cleanBase64Prefix(String base64Data) {
        if (base64Data == null || base64Data.trim().isEmpty()) {
            return base64Data;
        }
        
        // 移除data:image/xxx;base64,前缀（如果存在）
        if (base64Data.startsWith("data:image/")) {
            int commaIndex = base64Data.indexOf(",");
            if (commaIndex > 0) {
                return base64Data.substring(commaIndex + 1);
            }
        }
        
        return base64Data;
    }
}