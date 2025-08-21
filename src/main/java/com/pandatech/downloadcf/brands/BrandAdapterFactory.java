package com.pandatech.downloadcf.brands;

import com.pandatech.downloadcf.adapter.BrandAdapter;
import com.pandatech.downloadcf.util.BrandCodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 品牌适配器工厂类
 * 负责管理和提供品牌适配器实例
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BrandAdapterFactory {
    
    private final List<BrandAdapter> brandAdapters;
    private final Map<String, BrandAdapter> adapterCache = new HashMap<>();
    
    /**
     * 根据品牌编码获取适配器
     */
    public BrandAdapter getAdapter(String brandCode) {
        if (brandCode == null) {
            brandCode = BrandCodeUtil.getDefaultAdapterBrandCode();
        }
        
        // 标准化品牌编码
        String adapterBrandCode = BrandCodeUtil.toAdapterBrandCode(brandCode);
        
        // 先从缓存中查找
        BrandAdapter adapter = adapterCache.get(adapterBrandCode);
        if (adapter != null) {
            return adapter;
        }
        
        // 从注册的适配器中查找
        adapter = findAdapterByBrandCode(adapterBrandCode);
        if (adapter != null) {
            // 缓存适配器
            adapterCache.put(adapterBrandCode, adapter);
            log.info("找到品牌适配器: {} -> {}", adapterBrandCode, adapter.getClass().getSimpleName());
        } else {
            log.error("未找到品牌适配器: {}", adapterBrandCode);
        }
        
        return adapter;
    }
    
    /**
     * 根据品牌编码查找适配器
     */
    private BrandAdapter findAdapterByBrandCode(String brandCode) {
        return brandAdapters.stream()
                .filter(adapter -> brandCode.equals(adapter.getSupportedBrandCode()))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * 获取所有支持的品牌
     */
    public List<Map<String, Object>> getSupportedBrands() {
        return brandAdapters.stream()
                .map(adapter -> {
                    Map<String, Object> brand = new HashMap<>();
                    brand.put("brandCode", adapter.getSupportedBrandCode());
                    brand.put("brandName", getBrandName(adapter.getSupportedBrandCode()));
                    brand.put("adapterClass", adapter.getClass().getSimpleName());
                    brand.put("enabled", true);
                    return brand;
                })
                .toList();
    }
    
    /**
     * 获取品牌名称
     */
    private String getBrandName(String brandCode) {
        switch (brandCode) {
            case "AES001":
                return "攀攀科技";
            case "YALIANG001":
                return "雅量科技";
            case "攀攀": // 向后兼容
                return "攀攀科技";
            default:
                return brandCode + "品牌";
        }
    }
    
    /**
     * 检查品牌是否支持
     */
    public boolean isBrandSupported(String brandCode) {
        String adapterBrandCode = BrandCodeUtil.toAdapterBrandCode(brandCode);
        return findAdapterByBrandCode(adapterBrandCode) != null;
    }
    
    /**
     * 获取适配器数量
     */
    public int getAdapterCount() {
        return brandAdapters.size();
    }
    
    /**
     * 清空缓存
     */
    public void clearCache() {
        adapterCache.clear();
        log.info("品牌适配器缓存已清空");
    }
    
    /**
     * 获取缓存信息
     */
    public Map<String, String> getCacheInfo() {
        Map<String, String> cacheInfo = new HashMap<>();
        adapterCache.forEach((brandCode, adapter) -> 
            cacheInfo.put(brandCode, adapter.getClass().getSimpleName()));
        return cacheInfo;
    }
}