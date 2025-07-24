package com.pandatech.downloadcf.controller;

import com.pandatech.downloadcf.common.ApiResponse;
import com.pandatech.downloadcf.dto.BatchTemplateDeployRequest;
import com.pandatech.downloadcf.dto.TemplateDeployRequest;
import com.pandatech.downloadcf.service.TemplateDeployService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模板管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/template")
@RequiredArgsConstructor
@Tag(name = "模板管理", description = "模板下发和管理相关接口")
public class TemplateManagementController {
    
    private final TemplateDeployService templateDeployService;
    
    @GetMapping("/brands")
    @Operation(summary = "获取支持的品牌", description = "获取系统支持的所有模板品牌列表")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getSupportedBrands() {
        
        log.info("接收到获取支持品牌请求");
        
        try {
            List<Map<String, Object>> brands = templateDeployService.getSupportedBrands();
            
            return ResponseEntity.ok(ApiResponse.success("获取支持品牌成功", brands));
            
        } catch (Exception e) {
            log.error("获取支持品牌异常", e);
            return ResponseEntity.internalServerError().body(
                ApiResponse.error("获取支持品牌异常: " + e.getMessage(), null));
        }
    }
    
    @PostMapping("/deploy")
    @Operation(summary = "下发单个模板", description = "下发指定的模板到指定门店")
    public ResponseEntity<ApiResponse<Map<String, Object>>> deployTemplate(
            @Valid @RequestBody TemplateDeployRequest request) {
        
        log.info("接收到模板下发请求: templateId={}, storeCode={}, brandCode={}", 
                request.getTemplateId(), request.getStoreCode(), request.getBrandCode());
        
        try {
            boolean success = templateDeployService.deployTemplate(request);
            
            Map<String, Object> data = new HashMap<>();
            data.put("templateId", request.getTemplateId());
            data.put("storeCode", request.getStoreCode());
            data.put("brandCode", request.getBrandCode());
            data.put("success", success);
            data.put("timestamp", System.currentTimeMillis());
            
            if (success) {
                return ResponseEntity.ok(ApiResponse.success("模板下发处理完成", data));
            } else {
                return ResponseEntity.ok(ApiResponse.error("模板下发失败", data));
            }
            
        } catch (Exception e) {
            log.error("模板下发异常", e);
            Map<String, Object> data = new HashMap<>();
            data.put("templateId", request.getTemplateId());
            data.put("storeCode", request.getStoreCode());
            data.put("success", false);
            
            return ResponseEntity.internalServerError().body(
                ApiResponse.error("模板下发异常: " + e.getMessage(), data));
        }
    }
    
    @PostMapping("/deploy/batch")
    @Operation(summary = "批量下发模板", description = "批量下发多个模板到指定门店")
    public ResponseEntity<ApiResponse<Map<String, Object>>> batchDeployTemplate(
            @Valid @RequestBody BatchTemplateDeployRequest request) {
        
        log.info("接收到批量模板下发请求: count={}, parallel={}", 
                request.getTemplates().size(), request.getParallel());
        
        try {
            Map<String, Object> result = templateDeployService.batchDeployTemplate(request);
            
            int totalCount = (Integer) result.get("totalCount");
            int successCount = (Integer) result.get("successCount");
            int failedCount = (Integer) result.get("failedCount");
            
            if (failedCount == 0) {
                return ResponseEntity.ok(ApiResponse.success("批量模板下发处理完成", result));
            } else if (successCount > 0) {
                return ResponseEntity.ok(ApiResponse.partialSuccess("批量模板下发处理完成，部分失败", result));
            } else {
                return ResponseEntity.ok(ApiResponse.error("批量模板下发全部失败", result));
            }
            
        } catch (Exception e) {
            log.error("批量模板下发异常", e);
            Map<String, Object> data = new HashMap<>();
            data.put("totalCount", request.getTemplates().size());
            data.put("successCount", 0);
            data.put("failedCount", request.getTemplates().size());
            
            return ResponseEntity.internalServerError().body(
                ApiResponse.error("批量模板下发异常: " + e.getMessage(), data));
        }
    }
}