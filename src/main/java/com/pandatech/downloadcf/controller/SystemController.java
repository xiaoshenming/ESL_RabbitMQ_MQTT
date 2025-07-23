package com.pandatech.downloadcf.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统健康检查控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/system")
@Tag(name = "系统管理", description = "系统健康检查和状态监控")
public class SystemController {
    
    @GetMapping("/health")
    @Operation(summary = "健康检查", description = "检查系统运行状态")
    public Map<String, Object> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now());
        response.put("service", "价签模板下发系统");
        response.put("version", "1.0.0");
        
        log.info("系统健康检查: {}", response);
        return response;
    }
    
    @GetMapping("/info")
    @Operation(summary = "系统信息", description = "获取系统基本信息")
    public Map<String, Object> info() {
        Map<String, Object> response = new HashMap<>();
        response.put("name", "价签模板下发系统");
        response.put("description", "企业级价签模板下发和管理系统");
        response.put("version", "1.0.0");
        response.put("author", "PandaTech");
        response.put("buildTime", LocalDateTime.now());
        
        return response;
    }
}