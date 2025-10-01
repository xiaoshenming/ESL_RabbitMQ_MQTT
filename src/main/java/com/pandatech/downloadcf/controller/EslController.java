package com.pandatech.downloadcf.controller;

import com.pandatech.downloadcf.common.ApiResponse;
import com.pandatech.downloadcf.dto.EslRefreshRequest;
import com.pandatech.downloadcf.dto.EslStoreRefreshRequest;
import com.pandatech.downloadcf.dto.EslProductRefreshRequest;
import com.pandatech.downloadcf.service.EslRefreshService;
import com.pandatech.downloadcf.brands.yaliang.dto.YaliangRefreshRequest;
import com.pandatech.downloadcf.brands.yaliang.service.YaliangEslService;
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
    private final YaliangEslService yaliangEslService;
    
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
    
    @PostMapping("/yaliang/refresh")
    @Operation(summary = "雅量价签刷新", description = "专用于雅量品牌的价签刷新接口，支持图片Base64直接刷新")
    public ResponseEntity<ApiResponse<Map<String, Object>>> refreshYaliangEsl(
            @RequestBody YaliangRefreshRequest request) {
        
        log.info("接收到雅量价签刷新请求: deviceCode={}, deviceMac={}", 
                request.getDeviceCode(), request.getDeviceMac());
        
        try {
            Map<String, Object> result = yaliangEslService.refreshEsl(request);
            
            Map<String, Object> data = new HashMap<>();
            data.put("deviceCode", request.getDeviceCode());
            data.put("deviceMac", request.getDeviceMac());
            data.put("queueId", result.get("queueId"));
            data.put("mqttTopic", result.get("mqttTopic"));
            data.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(ApiResponse.success("雅量价签刷新请求已提交", data));
            
        } catch (IllegalArgumentException e) {
            log.error("雅量价签刷新参数错误: {}", e.getMessage());
            Map<String, Object> data = new HashMap<>();
            data.put("deviceCode", request.getDeviceCode());
            data.put("deviceMac", request.getDeviceMac());
            
            return ResponseEntity.badRequest().body(
                ApiResponse.badRequest("参数错误: " + e.getMessage(), data));
                
        } catch (Exception e) {
            log.error("雅量价签刷新异常", e);
            Map<String, Object> data = new HashMap<>();
            data.put("deviceCode", request.getDeviceCode());
            data.put("deviceMac", request.getDeviceMac());
            
            return ResponseEntity.internalServerError().body(
                ApiResponse.error("雅量价签刷新异常: " + e.getMessage(), data));
        }
    }
    
    @PostMapping("/yaliang/refresh/batch")
    @Operation(summary = "雅量价签批量刷新", description = "批量刷新多个雅量品牌价签")
    public ResponseEntity<ApiResponse<Map<String, Object>>> batchRefreshYaliangEsl(
            @RequestBody List<YaliangRefreshRequest> requests) {
        
        log.info("接收到雅量价签批量刷新请求: count={}", requests.size());
        
        try {
            List<Map<String, Object>> results = yaliangEslService.batchRefreshEsl(requests);
            
            int successCount = (int) results.stream().filter(r -> (Boolean) r.get("success")).count();
            int failedCount = requests.size() - successCount;
            
            Map<String, Object> data = new HashMap<>();
            data.put("totalCount", requests.size());
            data.put("successCount", successCount);
            data.put("failedCount", failedCount);
            data.put("results", results);
            data.put("timestamp", System.currentTimeMillis());
            
            if (failedCount == 0) {
                return ResponseEntity.ok(ApiResponse.success("雅量价签批量刷新处理完成", data));
            } else {
                return ResponseEntity.ok(ApiResponse.partialSuccess("雅量价签批量刷新处理完成，部分失败", data));
            }
            
        } catch (Exception e) {
            log.error("雅量价签批量刷新异常", e);
            Map<String, Object> data = new HashMap<>();
            data.put("totalCount", requests.size());
            data.put("successCount", 0);
            data.put("failedCount", requests.size());
            
            return ResponseEntity.internalServerError().body(
                ApiResponse.error("雅量价签批量刷新异常: " + e.getMessage(), data));
        }
    }
    
    @GetMapping("/yaliang/device-specs")
    @Operation(summary = "获取雅量设备规格", description = "获取雅量品牌支持的所有设备规格信息")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getYaliangDeviceSpecs() {
        
        log.info("接收到获取雅量设备规格请求");
        
        try {
            Map<String, Object> deviceSpecs = yaliangEslService.getDeviceSpecs();
            
            return ResponseEntity.ok(ApiResponse.success("获取雅量设备规格成功", deviceSpecs));
            
        } catch (Exception e) {
            log.error("获取雅量设备规格异常", e);
            return ResponseEntity.internalServerError().body(
                ApiResponse.error("获取雅量设备规格异常: " + e.getMessage(), null));
        }
    }
    
    @GetMapping("/yaliang/device-specs/{deviceSize}")
    @Operation(summary = "获取指定雅量设备规格", description = "获取指定尺寸的雅量设备规格详细信息")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getYaliangDeviceSpec(
            @Parameter(description = "设备尺寸", example = "2.13") 
            @PathVariable String deviceSize) {
        
        log.info("接收到获取雅量设备规格请求: deviceSize={}", deviceSize);
        
        try {
            Map<String, Object> deviceSpec = yaliangEslService.getDeviceSpec(deviceSize);
            
            if (deviceSpec != null) {
                return ResponseEntity.ok(ApiResponse.success("获取雅量设备规格成功", deviceSpec));
            } else {
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            log.error("获取雅量设备规格异常", e);
            return ResponseEntity.internalServerError().body(
                ApiResponse.error("获取雅量设备规格异常: " + e.getMessage(), null));
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