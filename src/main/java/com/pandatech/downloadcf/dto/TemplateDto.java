package com.pandatech.downloadcf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "模板下发请求体")
public class TemplateDto {

    @Schema(description = "价签ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String eslId;

    @Schema(description = "模板内容", requiredMode = Schema.RequiredMode.REQUIRED, example = "{\"template\": \"U_06\"}")
    private String templateData;
}