package com.pandatech.downloadcf.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 商品价签刷新请求DTO
 */
@Data
@Schema(description = "商品价签刷新请求")
public class EslProductRefreshRequest {
    
    @Schema(description = "商品编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "PRODUCT001")
    private String productCode;
    
    @Schema(description = "品牌编码", example = "panda")
    private String brandCode = "panda";
    
    @Schema(description = "是否强制刷新", example = "true")
    private Boolean forceRefresh = true;
}