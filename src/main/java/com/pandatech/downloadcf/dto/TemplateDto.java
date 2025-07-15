package com.pandatech.downloadcf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "模板下发请求体")
public class TemplateDto {

    @Schema(description = "模板ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1945045387689762818")
    private String templateId;
    
    @Schema(description = "门店编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "009")
    private String storeCode;
}