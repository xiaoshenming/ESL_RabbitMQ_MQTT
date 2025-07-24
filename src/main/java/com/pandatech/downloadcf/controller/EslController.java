package com.pandatech.downloadcf.controller;

import com.pandatech.downloadcf.common.ApiResponse;
import com.pandatech.downloadcf.dto.EslRefreshRequest;
import com.pandatech.downloadcf.dto.EslStoreRefreshRequest;
import com.pandatech.downloadcf.dto.EslProductRefreshRequest;
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
    public ResponseEntity<ApiResponse<Map<String, Object>>> refreshEsl(
            @RequestBody EslRefreshRequest request) {
        
        log.info("接收到价签刷新请求: {}", request);
        
        try {
            boolean success = eslRefreshService.refreshEsl(request);
            
            if (success) {
                Map<String, Object> data = new HashMap<>();
                data.put("eslId", request.getEslId());
                data.put("timestamp", System.currentTimeMillis());
                
                return ResponseEntity.ok(ApiResponse.success("价签刷新请求已提交", data));
            } else {
                Map<String, Object> data = new HashMap<>();
                data.put("eslId", request.getEslId());
                
                return ResponseEntity.badRequest().body(
                    ApiResponse.badRequest("价签不存在或未绑定商品", data));
            }
            
        } catch (Exception e) {
            log.error("价签刷新异常", e);
            Map<String, Object> data = new HashMap<>();
            data.put("eslId", request.getEslId());
            
            return ResponseEntity.internalServerError().body(
                ApiResponse.error("价签刷新异常: " + e.getMessage(), data));
        }
    }
    
    @PostMapping("/refresh/batch")
    @Operation(summary = "批量刷新价签", description = "批量刷新多个价签的显示内容")
    public ResponseEntity<ApiResponse<Map<String, Object>>> batchRefreshEsl(
            @RequestBody List<EslRefreshRequest> requests) {
        
        log.info("接收到批量价签刷新请求: count={}", requests.size());
        
        try {
            int successCount = eslRefreshService.batchRefreshEsl(requests);
            int failedCount = requests.size() - successCount;
            
            Map<String, Object> data = new HashMap<>();
            data.put("totalCount", requests.size());
            data.put("successCount", successCount);
            data.put("failedCount", failedCount);
            
            // 这里可以添加详细的结果列表，暂时简化处理
            // TODO: 如果需要详细的每个价签的处理结果，需要修改service层返回更详细的信息
            
            if (failedCount == 0) {
                return ResponseEntity.ok(ApiResponse.success("批量刷新处理完成", data));
            } else {
                return ResponseEntity.ok(ApiResponse.partialSuccess("批量刷新处理完成，部分失败", data));
            }
            
        } catch (Exception e) {
            log.error("批量价签刷新异常", e);
            Map<String, Object> data = new HashMap<>();
            data.put("totalCount", requests.size());
            data.put("successCount", 0);
            data.put("failedCount", requests.size());
            
            return ResponseEntity.internalServerError().body(
                ApiResponse.error("批量刷新异常: " + e.getMessage(), data));
        }
    }
    
    @PostMapping("/refresh/store")
    @Operation(summary = "按门店刷新价签", description = "刷新指定门店的所有价签")
    public ResponseEntity<ApiResponse<Map<String, Object>>> refreshEslByStore(
            @RequestBody EslStoreRefreshRequest request) {
        
        log.info("接收到门店价签刷新请求: storeCode={}, brandCode={}", 
                request.getStoreCode(), request.getBrandCode());
        
        try {
            int refreshCount = eslRefreshService.refreshEslByStore(request);
            
            Map<String, Object> data = new HashMap<>();
            data.put("storeCode", request.getStoreCode());
            data.put("brandCode", request.getBrandCode());
            data.put("refreshCount", refreshCount);
            data.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(ApiResponse.success("门店价签刷新处理完成", data));
            
        } catch (Exception e) {
            log.error("门店价签刷新异常", e);
            Map<String, Object> data = new HashMap<>();
            data.put("storeCode", request.getStoreCode());
            data.put("brandCode", request.getBrandCode());
            data.put("refreshCount", 0);
            
            return ResponseEntity.internalServerError().body(
                ApiResponse.error("门店价签刷新异常: " + e.getMessage(), data));
        }
    }
    
    @PostMapping("/refresh/product")
    @Operation(summary = "按商品刷新价签", description = "刷新指定商品的所有价签")
    public ResponseEntity<ApiResponse<Map<String, Object>>> refreshEslByProduct(
            @RequestBody EslProductRefreshRequest request) {
        
        log.info("接收到商品价签刷新请求: productCode={}, brandCode={}", 
                request.getProductCode(), request.getBrandCode());
        
        try {
            int refreshCount = eslRefreshService.refreshEslByProduct(request);
            
            Map<String, Object> data = new HashMap<>();
            data.put("productCode", request.getProductCode());
            data.put("brandCode", request.getBrandCode());
            data.put("refreshCount", refreshCount);
            data.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(ApiResponse.success("商品价签刷新处理完成", data));
            
        } catch (Exception e) {
            log.error("商品价签刷新异常", e);
            Map<String, Object> data = new HashMap<>();
            data.put("productCode", request.getProductCode());
            data.put("brandCode", request.getBrandCode());
            data.put("refreshCount", 0);
            
            return ResponseEntity.internalServerError().body(
                ApiResponse.error("商品价签刷新异常: " + e.getMessage(), data));
        }
    }
    
    @GetMapping("/brands")
    @Operation(summary = "获取支持的品牌", description = "获取系统支持的所有价签品牌列表")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getSupportedBrands() {
        
        log.info("接收到获取支持品牌请求");
        
        try {
            List<Map<String, Object>> brands = eslRefreshService.getSupportedBrands();
            
            return ResponseEntity.ok(ApiResponse.success("获取支持品牌成功", brands));
            
        } catch (Exception e) {
            log.error("获取支持品牌异常", e);
            return ResponseEntity.internalServerError().body(
                ApiResponse.error("获取支持品牌异常: " + e.getMessage(), null));
        }
    }
}