package com.pandatech.downloadcf.brands.yaliang.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 雅量电子价签刷新消息
 */
@Data
public class YaliangRefreshMessage {
    
    /**
     * 队列ID
     */
    @JsonProperty("queueId")
    private Integer queueId;
    
    /**
     * 设备类型
     */
    @JsonProperty("deviceType")
    private Integer deviceType;
    
    /**
     * 设备编码
     */
    @JsonProperty("deviceCode")
    private String deviceCode;
    
    /**
     * 设备MAC地址
     */
    @JsonProperty("deviceMac")
    private String deviceMac;
    
    /**
     * 设备版本
     */
    @JsonProperty("deviceVersion")
    private String deviceVersion;
    
    /**
     * 刷新动作
     */
    @JsonProperty("refreshAction")
    private Integer refreshAction;
    
    /**
     * 刷新区域
     */
    @JsonProperty("refreshArea")
    private Integer refreshArea;
    
    /**
     * 内容列表
     */
    @JsonProperty("content")
    private List<ContentItem> content;
    
    /**
     * 内容项
     */
    @Data
    public static class ContentItem {
        /**
         * 数据类型
         */
        @JsonProperty("dataType")
        private Integer dataType;
        
        /**
         * 数据引用（Base64编码的图片数据）
         */
        @JsonProperty("dataRef")
        private String dataRef;
        
        /**
         * 是否为最后一层
         */
        @JsonProperty("layerEnd")
        private Boolean layerEnd;
    }
}