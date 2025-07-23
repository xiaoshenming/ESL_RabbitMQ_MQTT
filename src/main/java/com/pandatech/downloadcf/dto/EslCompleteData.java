package com.pandatech.downloadcf.dto;

import com.pandatech.downloadcf.entity.*;
import lombok.Data;
import java.util.List;

/**
 * 价签完整数据DTO - 包含价签相关的所有数据
 */
@Data
public class EslCompleteData {
    
    /**
     * 价签基本信息
     */
    private PandaEsl esl;
    
    /**
     * 绑定的商品信息
     */
    private PandaProductWithBLOBs product;
    
    /**
     * 使用的模板信息
     */
    private PrintTemplateDesignWithBLOBs template;
    
    /**
     * 字段映射配置
     */
    private List<EslBrandFieldMapping> fieldMappings;
    
    /**
     * 门店编码
     */
    private String storeCode;
    
    /**
     * 品牌编码
     */
    private String brandCode;
    
    /**
     * 是否强制刷新
     */
    private Boolean forceRefresh;
}