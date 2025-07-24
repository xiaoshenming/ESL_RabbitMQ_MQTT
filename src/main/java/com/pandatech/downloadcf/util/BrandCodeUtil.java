package com.pandatech.downloadcf.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * 品牌代码兼容性处理工具类
 * 支持panda/PANDA/攀攀三种输入格式的统一处理
 */
@Slf4j
public class BrandCodeUtil {
    
    /**
     * 标准化品牌代码 - 将各种输入格式统一转换为标准格式
     * 
     * @param brandCode 输入的品牌代码（可能是panda、PANDA、攀攀等）
     * @return 标准化后的品牌代码
     */
    public static String normalizeBrandCode(String brandCode) {
        if (!StringUtils.hasText(brandCode)) {
            log.debug("品牌代码为空，使用默认值: panda");
            return "panda";
        }
        
        String trimmed = brandCode.trim();
        
        // 处理panda相关的各种格式
        if ("panda".equalsIgnoreCase(trimmed) || "攀攀".equals(trimmed)) {
            log.debug("品牌代码标准化: {} -> panda", brandCode);
            return "panda";
        }
        
        // 如果是其他品牌代码，保持原样但转为小写
        String normalized = trimmed.toLowerCase();
        log.debug("品牌代码标准化: {} -> {}", brandCode, normalized);
        return normalized;
    }
    
    /**
     * 转换为适配器使用的品牌代码
     * 适配器内部仍使用"攀攀"作为标识符以保持向后兼容
     * 
     * @param brandCode 标准化的品牌代码
     * @return 适配器使用的品牌代码
     */
    public static String toAdapterBrandCode(String brandCode) {
        String normalized = normalizeBrandCode(brandCode);
        
        if ("panda".equals(normalized)) {
            log.debug("转换为适配器品牌代码: {} -> 攀攀", brandCode);
            return "攀攀";
        }
        
        // 其他品牌保持原样
        log.debug("转换为适配器品牌代码: {} -> {}", brandCode, normalized);
        return normalized;
    }
    
    /**
     * 转换为API显示的品牌代码
     * API和Swagger文档中统一显示为"panda"
     * 
     * @param brandCode 任意格式的品牌代码
     * @return API显示的品牌代码
     */
    public static String toApiDisplayBrandCode(String brandCode) {
        String normalized = normalizeBrandCode(brandCode);
        
        if ("panda".equals(normalized)) {
            log.debug("转换为API显示品牌代码: {} -> panda", brandCode);
            return "panda";
        }
        
        // 其他品牌保持标准化格式
        log.debug("转换为API显示品牌代码: {} -> {}", brandCode, normalized);
        return normalized;
    }
    
    /**
     * 检查是否为panda品牌（兼容所有格式）
     * 
     * @param brandCode 品牌代码
     * @return 是否为panda品牌
     */
    public static boolean isPandaBrand(String brandCode) {
        if (!StringUtils.hasText(brandCode)) {
            return true; // 默认为panda品牌
        }
        
        String trimmed = brandCode.trim();
        boolean isPanda = "panda".equalsIgnoreCase(trimmed) || "攀攀".equals(trimmed);
        log.debug("检查是否为panda品牌: {} -> {}", brandCode, isPanda);
        return isPanda;
    }
    
    /**
     * 获取默认品牌代码
     * 
     * @return 默认品牌代码
     */
    public static String getDefaultBrandCode() {
        return "panda";
    }
    
    /**
     * 获取适配器默认品牌代码
     * 
     * @return 适配器默认品牌代码
     */
    public static String getDefaultAdapterBrandCode() {
        return "攀攀";
    }
}