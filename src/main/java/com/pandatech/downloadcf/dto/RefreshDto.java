package com.pandatech.downloadcf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 价签刷新请求DTO
 * 用于价签刷新接口
 */
@Data
@Schema(description = "价签刷新请求体")
public class RefreshDto {

    @Schema(description = "价签ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1945045387689762819")
    @NotBlank(message = "价签ID不能为空")
    private String eslId;
    
    @Schema(description = "门店编码", example = "009")
    private String storeCode;
    
    @Schema(description = "品牌编码", example = "PANDA")
    private String brandCode;
    
    @Schema(description = "是否强制刷新", example = "false")
    private Boolean forceRefresh = false;
}