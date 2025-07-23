package com.pandatech.downloadcf.controller;

import com.pandatech.downloadcf.dto.EslRefreshRequest;
import com.pandatech.downloadcf.service.EslRefreshService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 价签控制器 - 提供价签刷新相关的API接口
 */
@Slf4j
@RestController
@RequestMapping("/api/esl")
@RequiredArgsConstructor
@Tag(name = "价签管理", description = "价签刷新和管理相关接口")
public class EslController {
    
    private final EslRefreshService eslRefreshService;
    
    @PostMapping("/refresh")
    @Operation(summary = "刷新单个价签", description = "根据价签ID刷新指定的价签显示内容")
    public ResponseEntity<Map<String, Object>> refreshEsl(
            @RequestBody EslRefreshRequest request) {
        
        log.info("接收到价签刷新请求: {}", request);
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean success = eslRefreshService.refreshEsl(request);
            
            if (success) {
                response.put("success", true);
                response.put("message", "价签刷新成功");
                response.put("eslId", request.getEslId());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "价签刷新失败");
                response.put("eslId", request.getEslId());
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            log.error("价签刷新异常", e);
            response.put("success", false);
            response.put("message", "价签刷新异常: " + e.getMessage());
            response.put("eslId", request.getEslId());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    @PostMapping("/refresh/batch")
    @Operation(summary = "批量刷新价签", description = "批量刷新多个价签的显示内容")
    public ResponseEntity<Map<String, Object>> batchRefreshEsl(
            @RequestBody List<EslRefreshRequest> requests) {
        
        log.info("接收到批量价签刷新请求: count={}", requests.size());
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            int successCount = eslRefreshService.batchRefreshEsl(requests);
            
            response.put("success", true);
            response.put("message", "批量刷新完成");
            response.put("total", requests.size());
            response.put("successCount", successCount);
            response.put("failedCount", requests.size() - successCount);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("批量价签刷新异常", e);
            response.put("success", false);
            response.put("message", "批量刷新异常: " + e.getMessage());
            response.put("total", requests.size());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    @PostMapping("/refresh/store/{storeCode}")
    @Operation(summary = "按门店刷新价签", description = "刷新指定门店的所有价签")
    public ResponseEntity<Map<String, Object>> refreshEslByStore(
            @Parameter(description = "门店编码") @PathVariable String storeCode,
            @Parameter(description = "品牌编码") @RequestParam(defaultValue = "PANDA") String brandCode) {
        
        log.info("接收到门店价签刷新请求: storeCode={}, brandCode={}", storeCode, brandCode);
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            int successCount = eslRefreshService.refreshEslByStore(storeCode, brandCode);
            
            response.put("success", true);
            response.put("message", "门店价签刷新完成");
            response.put("storeCode", storeCode);
            response.put("brandCode", brandCode);
            response.put("successCount", successCount);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("门店价签刷新异常", e);
            response.put("success", false);
            response.put("message", "门店价签刷新异常: " + e.getMessage());
            response.put("storeCode", storeCode);
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    @PostMapping("/refresh/product/{productId}")
    @Operation(summary = "按商品刷新价签", description = "刷新指定商品绑定的所有价签")
    public ResponseEntity<Map<String, Object>> refreshEslByProduct(
            @Parameter(description = "商品ID") @PathVariable String productId,
            @Parameter(description = "品牌编码") @RequestParam(defaultValue = "PANDA") String brandCode) {
        
        log.info("接收到商品价签刷新请求: productId={}, brandCode={}", productId, brandCode);
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            int successCount = eslRefreshService.refreshEslByProduct(productId, brandCode);
            
            response.put("success", true);
            response.put("message", "商品价签刷新完成");
            response.put("productId", productId);
            response.put("brandCode", brandCode);
            response.put("successCount", successCount);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("商品价签刷新异常", e);
            response.put("success", false);
            response.put("message", "商品价签刷新异常: " + e.getMessage());
            response.put("productId", productId);
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    @GetMapping("/brands")
    @Operation(summary = "获取支持的品牌", description = "获取系统支持的所有品牌列表")
    public ResponseEntity<Map<String, Object>> getSupportedBrands() {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<String> brands = eslRefreshService.getSupportedBrands();
            
            response.put("success", true);
            response.put("message", "获取品牌列表成功");
            response.put("brands", brands);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("获取品牌列表异常", e);
            response.put("success", false);
            response.put("message", "获取品牌列表异常: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}