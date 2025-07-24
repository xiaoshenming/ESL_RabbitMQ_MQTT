package com.pandatech.downloadcf.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 门店价签刷新请求DTO
 */
@Data
@Schema(description = "门店价签刷新请求")
public class EslStoreRefreshRequest {
    
    @Schema(description = "门店编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "STORE001")
    private String storeCode;
    
    @Schema(description = "品牌编码", example = "panda")
    private String brandCode = "panda";
    
    @Schema(description = "是否强制刷新", example = "true")
    private Boolean forceRefresh = true;
}