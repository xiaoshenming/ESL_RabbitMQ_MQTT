package com.pandatech.downloadcf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * MQTT消息DTO - 对应价签刷新的完整消息格式
 */
@Data
public class MqttMessageDto {
    
    @JsonProperty("command")
    private String command = "wtag";
    
    @JsonProperty("data")
    private List<MqttDataDto> data;
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("timestamp")
    private Double timestamp;
    
    @JsonProperty("shop")
    private String shop;
}