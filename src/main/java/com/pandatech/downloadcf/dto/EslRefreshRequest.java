package com.pandatech.downloadcf.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 价签刷新请求DTO
 */
@Data
@Schema(description = "价签刷新请求")
public class EslRefreshRequest {
    
    @Schema(description = "价签ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "ESL001")
    private String eslId;
    
    @Schema(description = "品牌编码", example = "PANDA")
    private String brandCode;
    
    @Schema(description = "是否强制刷新", example = "true")
    private Boolean forceRefresh = true;
    
    @Schema(description = "门店编码", example = "STORE001")
    private String storeCode;
}