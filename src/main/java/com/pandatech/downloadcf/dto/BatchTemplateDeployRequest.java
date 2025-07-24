package com.pandatech.downloadcf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 批量模板下发请求DTO
 */
@Data
@Schema(description = "批量模板下发请求")
public class BatchTemplateDeployRequest {

    @Schema(description = "模板下发请求列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "模板下发请求列表不能为空")
    @Valid
    private List<TemplateDeployRequest> templates;
    
    @Schema(description = "是否并行处理", example = "true")
    private Boolean parallel = true;
    
    @Schema(description = "批次描述", example = "门店模板批量更新")
    private String batchDescription;
}