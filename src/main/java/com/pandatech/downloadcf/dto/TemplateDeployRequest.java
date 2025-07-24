package com.pandatech.downloadcf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 模板下发请求DTO
 */
@Data
@Schema(description = "模板下发请求")
public class TemplateDeployRequest {

    @Schema(description = "模板ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1945045387689762818")
    @NotBlank(message = "模板ID不能为空")
    private String templateId;
    
    @Schema(description = "门店编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "009")
    @NotBlank(message = "门店编码不能为空")
    private String storeCode;
    
    @Schema(description = "品牌编码", example = "攀攀")
    private String brandCode = "攀攀";
    
    @Schema(description = "是否强制下发", example = "true")
    private Boolean forceDeploy = true;
}