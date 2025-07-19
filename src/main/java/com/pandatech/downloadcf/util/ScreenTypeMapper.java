package com.pandatech.downloadcf.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 屏幕类型映射工具类
 * 用于将屏幕类型转换为TagType
 */
public class ScreenTypeMapper {
    
    private static final Map<String, String> SCREEN_TYPE_TO_TAG_TYPE = new HashMap<>();
    private static final String DEFAULT_TAG_TYPE = "06";
    
    static {
        // 根据需求文档配置映射关系
        SCREEN_TYPE_TO_TAG_TYPE.put("2.13T", "06");
        SCREEN_TYPE_TO_TAG_TYPE.put("2.13", "06");
        SCREEN_TYPE_TO_TAG_TYPE.put("2.9T", "07");
        SCREEN_TYPE_TO_TAG_TYPE.put("2.9", "07");
        SCREEN_TYPE_TO_TAG_TYPE.put("4.2T", "08");
        SCREEN_TYPE_TO_TAG_TYPE.put("4.2", "08");
        SCREEN_TYPE_TO_TAG_TYPE.put("4.20T", "1C");
        SCREEN_TYPE_TO_TAG_TYPE.put("4.20F", "1D");
        // 可以根据需要添加更多映射
    }
    
    /**
     * 将屏幕类型转换为TagType
     * @param screenType 屏幕类型（如：2.13T）
     * @return TagType（如：06）
     */
    public static String getTagType(String screenType) {
        if (screenType == null || screenType.trim().isEmpty()) {
            return DEFAULT_TAG_TYPE;
        }
        return SCREEN_TYPE_TO_TAG_TYPE.getOrDefault(screenType.trim(), DEFAULT_TAG_TYPE);
    }
    
    /**
     * 获取默认的TagType
     * @return 默认TagType
     */
    public static String getDefaultTagType() {
        return DEFAULT_TAG_TYPE;
    }
    
    /**
     * 检查屏幕类型是否支持
     * @param screenType 屏幕类型
     * @return 是否支持
     */
    public static boolean isSupported(String screenType) {
        return screenType != null && SCREEN_TYPE_TO_TAG_TYPE.containsKey(screenType.trim());
    }
    
    /**
     * 获取所有支持的屏幕类型
     * @return 支持的屏幕类型集合
     */
    public static Map<String, String> getAllMappings() {
        return new HashMap<>(SCREEN_TYPE_TO_TAG_TYPE);
    }
}