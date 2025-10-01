package com.pandatech.downloadcf.brands.yaliang.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 雅量电子价签刷新请求
 */
@Data
@Schema(description = "雅量电子价签刷新请求")
public class YaliangRefreshRequest {
    
    @Schema(description = "设备编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "CF100S18")
    private String deviceCode;
    
    @Schema(description = "设备MAC地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "00125414A7109A90")
    private String deviceMac;
    
    @Schema(description = "设备版本", example = "4.0.0")
    private String deviceVersion = "4.0.0";
    
    @Schema(description = "队列ID", example = "1008")
    private Integer queueId = 1008;
    
    @Schema(description = "设备类型", example = "1")
    private Integer deviceType = 1;
    
    @Schema(description = "刷新动作", example = "3")
    private Integer refreshAction = 3;
    
    @Schema(description = "刷新区域", example = "1")
    private Integer refreshArea = 1;
    
    @Schema(description = "图片Base64数据", requiredMode = Schema.RequiredMode.REQUIRED)
    private String imageBase64;
    
    @Schema(description = "设备尺寸规格", example = "2.13")
    private String deviceSize;
    
    @Schema(description = "是否强制使用新版本分辨率", example = "true")
    private Boolean useNewVersion = true;
    
    @Schema(description = "模板ID", example = "template_001")
    private String templateId;
    
    @Schema(description = "门店编码", example = "STORE001")
    private String storeCode;
}