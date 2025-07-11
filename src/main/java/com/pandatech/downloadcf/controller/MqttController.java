package com.pandatech.downloadcf.controller;

import com.pandatech.downloadcf.service.MqttService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mqtt")
@Tag(name = "MQTT 模拟接口", description = "用于模拟MQTT消息推送，方便测试")
@RequiredArgsConstructor
public class MqttController {

    private final MqttService mqttService;

    @PostMapping("/loadtemplate")
    @Operation(
        summary = "模拟MQTT推送加载模板", 
        description = "此接口用于模拟真实的MQTT消息，触发/templ/loadtemple逻辑，处理模板和产品数据。主要用于开发和测试环境。",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200", 
                description = "MQTT消息模拟处理成功",
                content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "text/plain",
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                        value = "模拟MQTT消息处理成功"
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400", 
                description = "请求参数错误或消息格式不正确"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "500", 
                description = "服务器内部错误"
            )
        }
    )
    public ResponseEntity<String> loadTemplate(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "MQTT消息载荷，通常为JSON格式的字符串",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "示例消息",
                    value = "{\"eslId\": \"1\", \"templateData\": \"{\\\"template\\\": \\\"U_06\\\"}\", \"action\": \"loadtemplate\"}"
                )
            )
        )
        @RequestBody String payload) {
        // 模拟从MQTT主题接收消息
        mqttService.handleMessage("/templ/loadtemple", payload);
        return ResponseEntity.ok("模拟MQTT消息处理成功");
    }
}