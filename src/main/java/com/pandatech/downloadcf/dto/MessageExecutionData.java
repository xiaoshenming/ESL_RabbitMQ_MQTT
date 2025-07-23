package com.pandatech.downloadcf.dto;

import lombok.Data;
import java.util.Map;

/**
 * 消息执行数据DTO - 用于消息执行器的数据传输
 */
@Data
public class MessageExecutionData {
    
    /**
     * 执行器类型 (mqtt, http, etc.)
     */
    private String executorType;
    
    /**
     * 目标地址 (topic, url, etc.)
     */
    private String destination;
    
    /**
     * 消息载荷
     */
    private Object payload;
    
    /**
     * 消息头信息
     */
    private Map<String, String> headers;
    
    /**
     * 门店编码
     */
    private String storeCode;
    
    /**
     * 品牌编码
     */
    private String brandCode;
    
    /**
     * 价签ID
     */
    private String eslId;
}