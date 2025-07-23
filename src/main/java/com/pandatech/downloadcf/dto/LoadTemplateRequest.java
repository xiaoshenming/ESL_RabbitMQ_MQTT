package com.pandatech.downloadcf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 模板加载请求DTO
 * 用于模板文件下载接口
 */
@Data
@Schema(description = "模板加载请求")
public class LoadTemplateRequest {
    
    @Schema(description = "模板ID", example = "1945045387689762818")
    private String id;
    
    @Schema(description = "模板名称", example = "U")
    private String name;
    
    @Schema(description = "门店编码", example = "009")
    private String storeCode;
}