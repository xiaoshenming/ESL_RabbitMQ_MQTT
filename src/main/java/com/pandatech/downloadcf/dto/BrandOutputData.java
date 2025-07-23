package com.pandatech.downloadcf.dto;

import lombok.Data;
import java.util.Map;

/**
 * 品牌输出数据DTO - 经过品牌适配器转换后的数据
 */
@Data
public class BrandOutputData {
    
    /**
     * 品牌编码
     */
    private String brandCode;
    
    /**
     * 转换后的数据映射
     */
    private Map<String, Object> dataMap;
    
    /**
     * 模板内容
     */
    private String templateContent;
    
    /**
     * 校验码
     */
    private String checksum;
    
    /**
     * 价签ID（数据库主键）
     */
    private String eslId;
    
    /**
     * 真正的ESL设备ID（十六进制，如06000000195A）
     */
    private String actualEslId;
    
    /**
     * 模板ID
     */
    private String templateId;
    
    /**
     * 门店编码
     */
    private String storeCode;
}