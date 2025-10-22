package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.config.TemplateRenderingConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前端图片渲染API服务
 * 负责调用前端渲染服务生成模板图片
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateRenderingService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final TemplateRenderingConfig config;

    /**
     * 渲染单个模板
     * @param templateId 模板ID (数据库主键)
     * @param productId 商品ID (数据库主键)
     * @return Base64编码的PNG图片数据 (已移除data:image/前缀)
     */
    public String renderTemplate(String templateId, String productId) {
        if (!config.getApi().isEnabled()) {
            log.warn("前端渲染API已禁用，返回null");
            return null;
        }

        log.info("开始调用前端渲染API: templateId={}, productId={}", templateId, productId);

        try {
            // 构建请求参数 - 关键修复：同时在请求体中传递API密钥
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("templateId", templateId);
            requestBody.put("productId", productId);
            requestBody.put("apiKey", config.getApi().getKey()); // 添加API密钥到请求体

            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-api-key", config.getApi().getKey());

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            // 发送POST请求 - 使用完整的API路径
            String apiUrl = config.getApi().getUrl();
            if (!apiUrl.endsWith("/api/render")) {
                // 如果配置的URL不包含完整路径，则添加
                if (apiUrl.endsWith("/")) {
                    apiUrl = apiUrl + "api/render";
                } else {
                    apiUrl = apiUrl + "/api/render";
                }
            }
            
            log.info("调用前端渲染API URL: {}", apiUrl);
            ResponseEntity<Map> response = restTemplate.postForEntity(
                apiUrl,
                request,
                Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();
                if (responseBody != null && Boolean.TRUE.equals(responseBody.get("success"))) {
                    // 修复：正确处理嵌套的数据结构
                    Object dataObj = responseBody.get("data");
                    if (dataObj instanceof Map) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> data = (Map<String, Object>) dataObj;
                        String base64Data = (String) data.get("base64");
                        if (base64Data != null && !base64Data.trim().isEmpty()) {
                            // 移除data:image/png;base64,前缀（如果存在）
                            if (base64Data.startsWith("data:image/")) {
                                int commaIndex = base64Data.indexOf(",");
                                if (commaIndex > 0) {
                                    base64Data = base64Data.substring(commaIndex + 1);
                                }
                            }
                            
                            // 验证Base64数据完整性
                            if (isValidBase64(base64Data)) {
                                log.info("前端渲染API成功，图片大小: {}KB", base64Data.length() / 1024.0);
                                return base64Data;
                            } else {
                                log.error("前端渲染API返回的Base64数据格式无效");
                            }
                        } else {
                            log.error("前端渲染API返回的base64字段为空");
                        }
                    }
                }
                log.error("前端渲染API返回格式异常: {}", responseBody);
            } else {
                log.error("前端渲染API请求失败，状态码: {}", response.getStatusCode());
            }

        } catch (Exception e) {
            log.error("调用前端渲染API异常", e);
        }

        return null;
    }

    /**
     * 验证Base64数据格式
     */
    private boolean isValidBase64(String base64) {
        if (base64 == null || base64.trim().isEmpty()) {
            return false;
        }
        
        try {
            // 清理Base64数据
            String cleanBase64 = base64.trim().replaceAll("\\s", "");
            
            // 检查Base64字符集
            if (!cleanBase64.matches("^[A-Za-z0-9+/]*={0,2}$")) {
                return false;
            }
            
            // 检查长度是否为4的倍数
            if (cleanBase64.length() % 4 != 0) {
                return false;
            }
            
            // 尝试解码验证
            byte[] decoded = Base64.getDecoder().decode(cleanBase64);
            
            // 检查解码后的数据长度
            if (decoded.length == 0) {
                return false;
            }
            
            // 验证是否为有效的图片数据头部
            return isValidImageHeader(decoded);
            
        } catch (Exception e) {
            log.debug("Base64验证失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 验证图片数据头部
     */
    private boolean isValidImageHeader(byte[] imageBytes) {
        if (imageBytes == null || imageBytes.length < 8) {
            return false;
        }
        
        // PNG文件头: 89 50 4E 47 0D 0A 1A 0A
        if (imageBytes.length >= 8 && 
            imageBytes[0] == (byte) 0x89 && imageBytes[1] == 0x50 && 
            imageBytes[2] == 0x4E && imageBytes[3] == 0x47 &&
            imageBytes[4] == 0x0D && imageBytes[5] == 0x0A && 
            imageBytes[6] == 0x1A && imageBytes[7] == 0x0A) {
            return true;
        }
        
        // JPEG文件头: FF D8 FF
        if (imageBytes.length >= 3 && 
            imageBytes[0] == (byte) 0xFF && imageBytes[1] == (byte) 0xD8 && 
            imageBytes[2] == (byte) 0xFF) {
            return true;
        }
        
        // GIF文件头: GIF87a 或 GIF89a
        if (imageBytes.length >= 6) {
            String header = new String(imageBytes, 0, 6);
            if ("GIF87a".equals(header) || "GIF89a".equals(header)) {
                return true;
            }
        }
        
        // BMP文件头: BM
        if (imageBytes.length >= 2 && 
            imageBytes[0] == 0x42 && imageBytes[1] == 0x4D) {
            return true;
        }
        
        return false;
    }

    /**
     * 批量渲染模板
     * @param renderItems 渲染项目列表
     * @return 渲染结果映射 (templateId_productId -> base64)
     */
    public Map<String, String> batchRenderTemplates(List<RenderItem> renderItems) {
        if (!config.getApi().isEnabled()) {
            log.warn("前端渲染API已禁用，返回空结果");
            return new HashMap<>();
        }

        if (renderItems == null || renderItems.isEmpty()) {
            log.warn("批量渲染请求为空");
            return new HashMap<>();
        }

        log.info("开始批量调用前端渲染API，数量: {}", renderItems.size());

        try {
            // 构建请求参数 - 关键修复：同时在请求体中传递API密钥
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("items", renderItems);
            requestBody.put("apiKey", config.getApi().getKey()); // 添加API密钥到请求体

            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-api-key", config.getApi().getKey());

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            // 发送POST请求 - 使用完整的API路径
            String apiUrl = config.getApi().getUrl();
            if (!apiUrl.endsWith("/api/render/batch")) {
                // 如果配置的URL不包含完整路径，则添加
                if (apiUrl.endsWith("/")) {
                    apiUrl = apiUrl + "api/render/batch";
                } else {
                    apiUrl = apiUrl + "/api/render/batch";
                }
            }
            
            log.info("调用批量前端渲染API URL: {}", apiUrl);
            ResponseEntity<Map> response = restTemplate.postForEntity(
                apiUrl,
                request,
                Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();
                if (responseBody != null && Boolean.TRUE.equals(responseBody.get("success"))) {
                    // 修复：正确处理嵌套的数据结构
                    Object dataObj = responseBody.get("data");
                    if (dataObj instanceof Map) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> data = (Map<String, Object>) dataObj;
                        Map<String, String> results = new HashMap<>();
                        for (Map.Entry<String, Object> entry : data.entrySet()) {
                            if (entry.getValue() instanceof String) {
                                String base64Data = (String) entry.getValue();
                                // 移除data:image/png;base64,前缀（如果存在）
                                if (base64Data.startsWith("data:image/")) {
                                    int commaIndex = base64Data.indexOf(",");
                                    if (commaIndex > 0) {
                                        base64Data = base64Data.substring(commaIndex + 1);
                                    }
                                }
                                
                                // 验证Base64数据完整性
                                if (isValidBase64(base64Data)) {
                                    results.put(entry.getKey(), base64Data);
                                } else {
                                    log.error("批量渲染中发现无效的Base64数据: key={}", entry.getKey());
                                }
                            }
                        }
                        log.info("批量前端渲染API成功，结果数量: {}", results.size());
                        return results;
                    }
                }
                log.error("批量前端渲染API返回格式异常: {}", responseBody);
            } else {
                log.error("批量前端渲染API请求失败，状态码: {}", response.getStatusCode());
            }

        } catch (Exception e) {
            log.error("批量调用前端渲染API异常", e);
        }

        return new HashMap<>();
    }

    /**
     * 检查服务状态
     */
    public boolean checkServiceHealth() {
        if (!config.getApi().isEnabled()) {
            return false;
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-api-key", config.getApi().getKey());

            HttpEntity<Void> request = new HttpEntity<>(headers);

            // 使用完整的API路径
            String apiUrl = config.getApi().getUrl();
            if (!apiUrl.endsWith("/api/status")) {
                // 如果配置的URL不包含完整路径，则添加
                if (apiUrl.endsWith("/")) {
                    apiUrl = apiUrl + "api/status";
                } else {
                    apiUrl = apiUrl + "/api/status";
                }
            }
            
            log.info("检查前端渲染服务状态 URL: {}", apiUrl);
            ResponseEntity<Map> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                request,
                Map.class
            );

            return response.getStatusCode() == HttpStatus.OK;

        } catch (Exception e) {
            log.error("检查前端渲染服务状态失败", e);
            return false;
        }
    }

    /**
     * 渲染项目类
     */
    public static class RenderItem {
        private String templateId;
        private String productId;

        public RenderItem() {}

        public RenderItem(String templateId, String productId) {
            this.templateId = templateId;
            this.productId = productId;
        }

        public String getTemplateId() {
            return templateId;
        }

        public void setTemplateId(String templateId) {
            this.templateId = templateId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }
    }
}