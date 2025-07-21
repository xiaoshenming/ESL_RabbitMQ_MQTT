package com.pandatech.downloadcf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * MQTT数据DTO - 对应data数组中的单个元素
 */
@Data
public class MqttDataDto {
    
    @JsonProperty("tag")
    private Long tag;
    
    @JsonProperty("tmpl")
    private String tmpl;
    
    @JsonProperty("model")
    private Integer model;
    
    @JsonProperty("checksum")
    private String checksum;
    
    @JsonProperty("forcefrash")
    private Integer forcefrash = 1;
    
    @JsonProperty("value")
    private Map<String, Object> value;
    
    @JsonProperty("taskid")
    private Integer taskid;
    
    @JsonProperty("token")
    private Integer token;
}