package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @Value("${template.rendering.api.url:http://localhost:3001}")
    private String apiBaseUrl;

    @Value("${template.rendering.api.key:snowy-print-api-2024}")
    private String apiKey;

    @Value("${template.rendering.api.enabled:true}")
    private boolean apiEnabled;

    /**
     * 渲染单个模板
     * @param templateId 模板ID (数据库主键)
     * @param productId 商品ID (数据库主键)
     * @return Base64编码的PNG图片数据 (已移除data:image/前缀)
     */
    public String renderTemplate(String templateId, String productId) {
        if (!apiEnabled) {
            log.warn("前端渲染API已禁用，返回null");
            return null;
        }

        log.info("开始调用前端渲染API: templateId={}, productId={}", templateId, productId);

        try {
            // 构建请求参数
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("templateId", templateId);
            requestBody.put("productId", productId);
            requestBody.put("apiKey", apiKey);

            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            // 发送请求
            ResponseEntity<Map> response = restTemplate.exchange(
                apiBaseUrl + "/api/render",
                HttpMethod.POST,
                request,
                Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();
                if (responseBody != null && Boolean.TRUE.equals(responseBody.get("success"))) {
                    Map<String, Object> data = (Map<String, Object>) responseBody.get("data");
                    if (data != null) {
                        String base64 = (String) data.get("base64");
                        if (base64 != null && !base64.trim().isEmpty()) {
                            log.info("前端渲染API成功生成图片，大小: {}KB", base64.length() / 1024.0);
                            return base64;
                        }
                    }
                }
                log.error("前端渲染API返回格式异常: {}", responseBody);
            } else {
                log.error("前端渲染API请求失败，状态码: {}", response.getStatusCode());
            }

        } catch (Exception e) {
            log.error("调用前端渲染API异常: templateId={}, productId={}", templateId, productId, e);
        }

        return null;
    }

    /**
     * 批量渲染模板
     * @param items 渲染项目列表
     * @return 渲染结果映射，key为templateId，value为base64数据
     */
    public Map<String, String> batchRenderTemplates(List<RenderItem> items) {
        if (!apiEnabled) {
            log.warn("前端渲染API已禁用，返回空结果");
            return new HashMap<>();
        }

        log.info("开始批量调用前端渲染API，项目数量: {}", items.size());

        try {
            // 构建请求参数
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("items", items);
            requestBody.put("apiKey", apiKey);

            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            // 发送请求
            ResponseEntity<Map> response = restTemplate.exchange(
                apiBaseUrl + "/api/batch-render",
                HttpMethod.POST,
                request,
                Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();
                if (responseBody != null && Boolean.TRUE.equals(responseBody.get("success"))) {
                    Map<String, Object> data = (Map<String, Object>) responseBody.get("data");
                    if (data != null) {
                        List<Map<String, Object>> results = (List<Map<String, Object>>) data.get("results");
                        if (results != null) {
                            Map<String, String> resultMap = new HashMap<>();
                            for (Map<String, Object> result : results) {
                                String templateId = (String) result.get("templateId");
                                String base64 = (String) result.get("base64");
                                Boolean success = (Boolean) result.get("success");
                                
                                if (Boolean.TRUE.equals(success) && base64 != null) {
                                    resultMap.put(templateId, base64);
                                }
                            }
                            log.info("批量渲染完成，成功: {}/{}", resultMap.size(), items.size());
                            return resultMap;
                        }
                    }
                }
                log.error("前端渲染API批量请求返回格式异常: {}", responseBody);
            } else {
                log.error("前端渲染API批量请求失败，状态码: {}", response.getStatusCode());
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
        if (!apiEnabled) {
            return false;
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-api-key", apiKey);

            HttpEntity<Void> request = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                apiBaseUrl + "/api/status",
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