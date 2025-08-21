package com.pandatech.downloadcf.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * 品牌代码兼容性处理工具类
 * 支持新旧品牌编码的统一处理：
 * - AES001 -> 攀攀科技 (原panda/PANDA/攀攀)
 * - YALIANG001 -> 雅量科技
 * - 保持对旧格式的向后兼容
 */
@Slf4j
public class BrandCodeUtil {
    
    /**
     * 标准化品牌代码 - 将各种输入格式统一转换为标准格式
     * 
     * @param brandCode 输入的品牌代码（可能是panda、PANDA、攀攀、AES001、YALIANG001等）
     * @return 标准化后的品牌代码
     */
    public static String normalizeBrandCode(String brandCode) {
        if (!StringUtils.hasText(brandCode)) {
            log.debug("品牌代码为空，使用默认值: aes001");
            return "aes001";
        }
        
        String trimmed = brandCode.trim();
        
        // 处理攀攀科技相关的各种格式 (新旧兼容)
        if ("panda".equalsIgnoreCase(trimmed) || "攀攀".equals(trimmed) || "攀攀科技".equals(trimmed) || "AES001".equalsIgnoreCase(trimmed)) {
            log.debug("品牌代码标准化: {} -> aes001", brandCode);
            return "aes001";
        }
        
        // 处理雅量科技相关的格式
        if ("雅量科技".equals(trimmed) || "YALIANG001".equalsIgnoreCase(trimmed)) {
            log.debug("品牌代码标准化: {} -> yaliang001", brandCode);
            return "yaliang001";
        }
        
        // 如果是其他品牌代码，保持原样但转为小写
        String normalized = trimmed.toLowerCase();
        log.debug("品牌代码标准化: {} -> {}", brandCode, normalized);
        return normalized;
    }
    
    /**
     * 转换为适配器使用的品牌代码
     * 适配器内部使用特定标识符以保持向后兼容
     * 
     * @param brandCode 标准化的品牌代码
     * @return 适配器使用的品牌代码
     */
    public static String toAdapterBrandCode(String brandCode) {
        String normalized = normalizeBrandCode(brandCode);
        
        if ("aes001".equals(normalized)) {
            log.debug("转换为适配器品牌代码: {} -> AES001", brandCode);
            return "AES001";
        }
        
        if ("yaliang001".equals(normalized)) {
            log.debug("转换为适配器品牌代码: {} -> YALIANG001", brandCode);
            return "YALIANG001";
        }
        
        // 其他品牌保持原样
        log.debug("转换为适配器品牌代码: {} -> {}", brandCode, normalized);
        return normalized;
    }
    
    /**
     * 转换为API显示的品牌代码
     * API和Swagger文档中统一显示标准化格式
     * 
     * @param brandCode 任意格式的品牌代码
     * @return API显示的品牌代码
     */
    public static String toApiDisplayBrandCode(String brandCode) {
        String normalized = normalizeBrandCode(brandCode);
        
        if ("aes001".equals(normalized)) {
            log.debug("转换为API显示品牌代码: {} -> AES001", brandCode);
            return "AES001";
        }
        
        if ("yaliang001".equals(normalized)) {
            log.debug("转换为API显示品牌代码: {} -> YALIANG001", brandCode);
            return "YALIANG001";
        }
        
        // 其他品牌保持标准化格式
        log.debug("转换为API显示品牌代码: {} -> {}", brandCode, normalized);
        return normalized;
    }
    
    /**
     * 检查是否为攀攀科技品牌（兼容所有格式）
     * 
     * @param brandCode 品牌代码
     * @return 是否为攀攀科技品牌
     */
    public static boolean isAesBrand(String brandCode) {
        if (!StringUtils.hasText(brandCode)) {
            return true; // 默认为攀攀科技品牌
        }
        
        String normalized = normalizeBrandCode(brandCode);
        boolean isAes = "aes001".equals(normalized);
        log.debug("检查是否为攀攀科技品牌: {} -> {}", brandCode, isAes);
        return isAes;
    }
    
    /**
     * 检查是否为雅量科技品牌
     * 
     * @param brandCode 品牌代码
     * @return 是否为雅量科技品牌
     */
    public static boolean isYaliangBrand(String brandCode) {
        if (!StringUtils.hasText(brandCode)) {
            return false;
        }
        
        String normalized = normalizeBrandCode(brandCode);
        boolean isYaliang = "yaliang001".equals(normalized);
        log.debug("检查是否为雅量科技品牌: {} -> {}", brandCode, isYaliang);
        return isYaliang;
    }
    
    /**
     * 检查是否为panda品牌（向后兼容方法）
     * 
     * @param brandCode 品牌代码
     * @return 是否为panda品牌（现在映射到攀攀科技）
     * @deprecated 使用 isAesBrand 替代
     */
    @Deprecated
    public static boolean isPandaBrand(String brandCode) {
        return isAesBrand(brandCode);
    }
    
    /**
     * 获取默认品牌代码
     * 
     * @return 默认品牌代码
     */
    public static String getDefaultBrandCode() {
        return "aes001";
    }
    
    /**
     * 获取适配器默认品牌代码
     * 
     * @return 适配器默认品牌代码
     */
    public static String getDefaultAdapterBrandCode() {
        return "AES001";
    }
}