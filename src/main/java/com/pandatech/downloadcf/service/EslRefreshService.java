package com.pandatech.downloadcf.service;

import com.pandatech.downloadcf.adapter.BrandAdapter;
import com.pandatech.downloadcf.dto.BrandOutputData;
import com.pandatech.downloadcf.dto.EslCompleteData;
import com.pandatech.downloadcf.dto.EslRefreshRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 价签刷新服务 - 核心业务逻辑
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EslRefreshService {
    
    private final DataService dataService;
    private final MessageProducerService messageProducerService;
    private final List<BrandAdapter> brandAdapters;
    
    /**
     * 刷新单个价签
     */
    public boolean refreshEsl(EslRefreshRequest request) {
        log.info("开始刷新价签: {}", request);
        log.info("请求中的品牌编码: {}", request.getBrandCode());
        
        try {
            // 1. 获取完整的价签数据
            EslCompleteData completeData = dataService.getEslCompleteData(request.getEslId());
            if (completeData == null) {
                log.error("获取价签数据失败: eslId={}", request.getEslId());
                return false;
            }
            log.info("获取到价签数据，商品品牌: {}", completeData.getProduct() != null ? completeData.getProduct().getProductBrand() : "null");
            
            // 2. 设置请求参数
            String actualBrandCode = null;
            // 获取实际的品牌编码（应该使用商品数据中的品牌字段）
            String productBrand = completeData.getProduct().getProductBrand();
            actualBrandCode = StringUtils.hasText(productBrand) ? productBrand : "攀攀";
            
            log.info("请求参数中的品牌编码: {} (用于选择解析器)", request.getBrandCode());
            log.info("商品数据中的品牌字段: {}", productBrand);
            log.info("实际使用的品牌编码: {} (用于查找品牌适配器)", actualBrandCode);
            
            completeData.setBrandCode(actualBrandCode);
            
            if (request.getForceRefresh() != null) {
                completeData.setForceRefresh(request.getForceRefresh());
            }
            if (request.getStoreCode() != null) {
                completeData.setStoreCode(request.getStoreCode());
            }
            
            // 3. 查找对应的品牌适配器（使用商品数据中的品牌字段）
            BrandAdapter adapter = findBrandAdapter(actualBrandCode);
            if (adapter == null) {
                log.error("未找到品牌适配器: brandCode={}", completeData.getBrandCode());
                return false;
            }
            log.info("获取到品牌适配器: {}", adapter.getClass().getSimpleName());
            log.info("品牌适配器支持的品牌编码: {}", adapter.getSupportedBrandCode());
            
            // 4. 验证数据
            if (!adapter.validate(completeData)) {
                log.error("数据验证失败: eslId={}, brandCode={}", 
                        request.getEslId(), completeData.getBrandCode());
                return false;
            }
            log.info("数据验证通过");
            
            // 5. 转换数据
            BrandOutputData outputData = adapter.transform(completeData);
            if (outputData == null) {
                log.error("数据转换失败: eslId={}, brandCode={}", 
                        request.getEslId(), completeData.getBrandCode());
                return false;
            }
            log.info("数据转换完成，输出数据类型: {}", outputData.getClass().getSimpleName());
            
            // 6. 发送消息
            log.info("准备发送消息，使用品牌编码: {}", actualBrandCode);
            boolean success = messageProducerService.sendMessage(outputData);
            
            if (success) {
                log.info("价签刷新成功: eslId={}, brandCode={}", 
                        request.getEslId(), completeData.getBrandCode());
            } else {
                log.error("价签刷新失败: eslId={}, brandCode={}", 
                        request.getEslId(), completeData.getBrandCode());
            }
            
            return success;
            
        } catch (Exception e) {
            log.error("价签刷新异常: eslId={}", request.getEslId(), e);
            return false;
        }
    }
    
    /**
     * 批量刷新价签
     */
    public int batchRefreshEsl(List<EslRefreshRequest> requests) {
        log.info("开始批量刷新价签: count={}", requests.size());
        
        int successCount = 0;
        for (EslRefreshRequest request : requests) {
            try {
                if (refreshEsl(request)) {
                    successCount++;
                }
            } catch (Exception e) {
                log.error("批量刷新价签异常: eslId={}", request.getEslId(), e);
            }
        }
        
        log.info("批量刷新价签完成: total={}, success={}, failed={}", 
                requests.size(), successCount, requests.size() - successCount);
        
        return successCount;
    }
    
    /**
     * 根据门店刷新所有价签
     */
    public int refreshEslByStore(String storeCode, String brandCode) {
        log.info("开始按门店刷新价签: storeCode={}, brandCode={}", storeCode, brandCode);
        
        // 获取门店的所有价签
        var esls = dataService.getEslsByStore(storeCode);
        if (esls.isEmpty()) {
            log.warn("门店没有价签: storeCode={}", storeCode);
            return 0;
        }
        
        // 构建刷新请求
        List<EslRefreshRequest> requests = esls.stream()
                .map(esl -> {
                    EslRefreshRequest request = new EslRefreshRequest();
                    request.setEslId(esl.getId());
                    request.setBrandCode(brandCode);
                    request.setStoreCode(storeCode);
                    request.setForceRefresh(true);
                    return request;
                })
                .toList();
        
        return batchRefreshEsl(requests);
    }
    
    /**
     * 根据商品刷新相关价签
     */
    public int refreshEslByProduct(String productId, String brandCode) {
        log.info("开始按商品刷新价签: productId={}, brandCode={}", productId, brandCode);
        
        // 获取商品绑定的所有价签
        var esls = dataService.getEslsByProduct(productId);
        if (esls.isEmpty()) {
            log.warn("商品没有绑定价签: productId={}", productId);
            return 0;
        }
        
        // 构建刷新请求
        List<EslRefreshRequest> requests = esls.stream()
                .map(esl -> {
                    EslRefreshRequest request = new EslRefreshRequest();
                    request.setEslId(esl.getId());
                    request.setBrandCode(brandCode);
                    request.setStoreCode(esl.getStoreCode());
                    request.setForceRefresh(true);
                    return request;
                })
                .toList();
        
        return batchRefreshEsl(requests);
    }
    
    /**
     * 查找品牌适配器
     */
    private BrandAdapter findBrandAdapter(String brandCode) {
        if (brandCode == null) {
            brandCode = "攀攀"; // 默认品牌
        }
        
        final String finalBrandCode = brandCode;
        return brandAdapters.stream()
                .filter(adapter -> finalBrandCode.equals(adapter.getSupportedBrandCode()))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * 获取支持的品牌列表
     */
    public List<String> getSupportedBrands() {
        return brandAdapters.stream()
                .map(BrandAdapter::getSupportedBrandCode)
                .toList();
    }
}