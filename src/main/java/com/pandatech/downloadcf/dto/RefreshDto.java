package com.pandatech.downloadcf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "价签刷新请求体")
public class RefreshDto {

    @Schema(description = "价签ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String eslId;
}