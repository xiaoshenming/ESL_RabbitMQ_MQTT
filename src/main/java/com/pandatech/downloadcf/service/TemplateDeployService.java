package com.pandatech.downloadcf.service;

import com.pandatech.downloadcf.adapter.BrandAdapter;
import com.pandatech.downloadcf.dto.BatchTemplateDeployRequest;
import com.pandatech.downloadcf.dto.TemplateDeployRequest;
import com.pandatech.downloadcf.dto.TemplateDto;
import com.pandatech.downloadcf.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 模板下发服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateDeployService {
    
    private final TemplateService templateService;
    private final List<BrandAdapter> brandAdapters;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    
    /**
     * 单个模板下发
     */
    public boolean deployTemplate(TemplateDeployRequest request) {
        log.info("开始下发模板: templateId={}, storeCode={}, brandCode={}", 
                request.getTemplateId(), request.getStoreCode(), request.getBrandCode());
        
        try {
            // 转换为原有的TemplateDto格式
            TemplateDto templateDto = new TemplateDto();
            templateDto.setTemplateId(request.getTemplateId());
            templateDto.setStoreCode(request.getStoreCode());
            templateDto.setBrandCode(request.getBrandCode());
            
            // 调用原有的模板下发服务
            templateService.sendTemplate(templateDto);
            
            log.info("模板下发成功: templateId={}, storeCode={}", 
                    request.getTemplateId(), request.getStoreCode());
            return true;
            
        } catch (BusinessException e) {
            log.error("模板下发业务异常: templateId={}, storeCode={}, 错误代码: {}, 错误信息: {}", 
                    request.getTemplateId(), request.getStoreCode(), e.getCode(), e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("模板下发失败: templateId={}, storeCode={}, 错误: {}", 
                    request.getTemplateId(), request.getStoreCode(), e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * 批量模板下发
     */
    public Map<String, Object> batchDeployTemplate(BatchTemplateDeployRequest request) {
        log.info("开始批量下发模板: count={}, parallel={}", 
                request.getTemplates().size(), request.getParallel());
        
        Map<String, Object> result = new HashMap<>();
        int totalCount = request.getTemplates().size();
        int successCount = 0;
        int failedCount = 0;
        
        if (request.getParallel()) {
            // 并行处理
            List<CompletableFuture<Boolean>> futures = request.getTemplates().stream()
                    .map(templateRequest -> CompletableFuture.supplyAsync(() -> 
                            deployTemplate(templateRequest), executorService))
                    .toList();
            
            // 等待所有任务完成并统计结果
            for (CompletableFuture<Boolean> future : futures) {
                try {
                    if (future.get()) {
                        successCount++;
                    } else {
                        failedCount++;
                    }
                } catch (Exception e) {
                    log.error("批量下发任务执行异常", e);
                    failedCount++;
                }
            }
        } else {
            // 串行处理
            for (TemplateDeployRequest templateRequest : request.getTemplates()) {
                try {
                    if (deployTemplate(templateRequest)) {
                        successCount++;
                    } else {
                        failedCount++;
                    }
                } catch (Exception e) {
                    log.error("批量下发模板异常: templateId={}", templateRequest.getTemplateId(), e);
                    failedCount++;
                }
            }
        }
        
        result.put("totalCount", totalCount);
        result.put("successCount", successCount);
        result.put("failedCount", failedCount);
        result.put("batchDescription", request.getBatchDescription());
        result.put("timestamp", System.currentTimeMillis());
        
        log.info("批量模板下发完成: total={}, success={}, failed={}", 
                totalCount, successCount, failedCount);
        
        return result;
    }
    
    /**
     * 获取支持的品牌列表
     */
    public List<Map<String, Object>> getSupportedBrands() {
        return brandAdapters.stream()
                .map(adapter -> {
                    Map<String, Object> brand = new HashMap<>();
                    brand.put("brandCode", adapter.getSupportedBrandCode());
                    brand.put("brandName", getBrandName(adapter.getSupportedBrandCode()));
                    brand.put("enabled", true);
                    return brand;
                })
                .toList();
    }
    
    /**
     * 根据品牌编码获取品牌名称
     */
    private String getBrandName(String brandCode) {
        switch (brandCode) {
            case "攀攀":
                return "攀攀品牌";
            default:
                return brandCode + "品牌";
        }
    }
}