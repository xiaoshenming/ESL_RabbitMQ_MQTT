package com.pandatech.downloadcf.controller;

import com.pandatech.downloadcf.dto.LoadTemplateRequest;
import com.pandatech.downloadcf.dto.RefreshDto;
import com.pandatech.downloadcf.dto.TemplateDto;
import com.pandatech.downloadcf.service.TemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/template")
@Tag(name = "模板与价签操作", description = "提供模板下发和价签刷新的接口")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    @PostMapping("/send")
    @Operation(
        summary = "下发模板", 
        description = "根据模板ID从数据库获取模板信息，构造MQTT消息格式并发送到RabbitMQ队列，最终通过MQTT推送到价签设备。消息格式符合tmpllist命令规范。",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200", 
                description = "模板下发请求成功接收",
                content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "text/plain",
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                        value = "模板下发请求已接收"
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400", 
                description = "请求参数错误或模板ID不存在"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "500", 
                description = "服务器内部错误"
            )
        }
    )
    public ResponseEntity<String> sendTemplate(@RequestBody TemplateDto templateDto) {
        templateService.sendTemplate(templateDto);
        return ResponseEntity.ok("模板下发请求已接收");
    }

    @PostMapping("/refresh")
    @Operation(
        summary = "刷新价签", 
        description = "向指定的价签发送刷新指令。此操作会将消息放入RabbitMQ队列，由后端服务处理后通过MQTT推送。",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200", 
                description = "价签刷新请求成功接收",
                content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "text/plain",
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                        value = "价签刷新请求已接收"
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400", 
                description = "请求参数错误"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "500", 
                description = "服务器内部错误"
            )
        }
    )
    public ResponseEntity<String> refreshEsl(@RequestBody RefreshDto refreshDto) {
        templateService.refreshEsl(refreshDto);
        return ResponseEntity.ok("价签刷新请求已接收");
    }

    @PostMapping("/loadtemple")
    @Operation(
        summary = "下载模板文件",
        description = "根据模板ID或名称下载模板文件（二进制流格式）",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "模板文件二进制流",
                content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/octet-stream")
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "请求参数无效（id和name都为空）"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "找不到对应的模板"
            )
        }
    )
    public ResponseEntity<byte[]> loadTemple(@RequestBody LoadTemplateRequest request) {
        if ((request.getId() == null || request.getId().isEmpty()) && (request.getName() == null || request.getName().isEmpty())) {
            return ResponseEntity.badRequest().build();
        }

        byte[] templateContent = templateService.loadTemple(request);

        if (templateContent == null) {
            return ResponseEntity.notFound().build();
        }

        String fileName = (request.getName() != null && !request.getName().isEmpty()) ? request.getName() : request.getId();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName + ".json");

        return new ResponseEntity<>(templateContent, headers, HttpStatus.OK);
    }
}