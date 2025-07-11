package com.pandatech.downloadcf.controller;

import com.pandatech.downloadcf.entity.TemplateExchange;
import com.pandatech.downloadcf.service.TemplateExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/res")
public class TemplateExchangeController {
    
    @Autowired
    private TemplateExchangeService templateExchangeService;
    
    /**
     * 根据名称查找模板信息
     * @param name 模板名称
     * @return 模板对象
     */
    @GetMapping("/find/{name}")
    public ResponseEntity<TemplateExchange> findByName(@PathVariable String name) {
        TemplateExchange template = templateExchangeService.findByName(name);
        if (template == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(template);
    }
    
    /**
     * 根据名称获取JSON内容
     * @param name 模板名称
     * @return JSON字符串
     */
    @GetMapping("/json/{name}")
    public ResponseEntity<String> getJsonContent(@PathVariable String name) {
        String jsonContent = templateExchangeService.getJsonContentByName(name);
        if (jsonContent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonContent);
    }
    
    /**
     * 下载JSON文件（二进制流）
     * @param name 模板名称
     * @return 文件下载响应
     */
    @GetMapping("/download/{name}")
    public ResponseEntity<byte[]> downloadJsonFile(@PathVariable String name) {
        String jsonContent = templateExchangeService.getJsonContentByName(name);
        if (jsonContent == null) {
            return ResponseEntity.notFound().build();
        }
        
        // 转换为字节数组
        byte[] jsonBytes = jsonContent.getBytes(StandardCharsets.UTF_8);
        
        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", name + ".json");
        headers.setContentLength(jsonBytes.length);
        
        return new ResponseEntity<>(jsonBytes, headers, HttpStatus.OK);
    }
    
    /**
     * 直接下载为JSON文件（带正确的Content-Type）
     * @param name 模板名称
     * @return JSON文件下载响应
     */
    @GetMapping("/download-json/{name}")
    public ResponseEntity<byte[]> downloadAsJsonFile(@PathVariable String name) {
        String jsonContent = templateExchangeService.getJsonContentByName(name);
        if (jsonContent == null) {
            return ResponseEntity.notFound().build();
        }
        
        // 转换为字节数组
        byte[] jsonBytes = jsonContent.getBytes(StandardCharsets.UTF_8);
        
        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
        headers.setContentDispositionFormData("attachment", name + ".json");
        headers.setContentLength(jsonBytes.length);
        
        return new ResponseEntity<>(jsonBytes, headers, HttpStatus.OK);
    }
    
    /**
     * 下载JSON文件（处理带.json后缀的文件名）
     * @param filename 带.json后缀的文件名
     * @return 文件下载响应
     */
    /**
 * 根据POST请求数据下载模板文件（二进制流）
 * @param request 包含id和name的请求对象
 * @return 文件下载响应
 */
@PostMapping("/templ/loadtemple")
public ResponseEntity<byte[]> loadTemplateName(@RequestBody TemplateLoadRequest request) {
    // 优先使用name查找，如果name为空则使用id
    String searchKey = request.getName() != null ? request.getName() : request.getId();
    
    if (searchKey == null || searchKey.trim().isEmpty()) {
        return ResponseEntity.badRequest().build();
    }
    
    // 处理带.json后缀的文件名（参考downloadJsonFileByFilename的逻辑）
    String templateName = searchKey;
    String downloadFileName = searchKey;
    
    if (searchKey.toLowerCase().endsWith(".json")) {
        // 移除.json后缀用于数据库查找
        templateName = searchKey.substring(0, searchKey.length() - 5);
        // 保留原始文件名用于下载
        downloadFileName = searchKey;
    } else {
        // 如果没有.json后缀，下载时自动添加
        downloadFileName = searchKey + ".json";
    }
    
    String jsonContent = templateExchangeService.getJsonContentByName(templateName);
    if (jsonContent == null) {
        return ResponseEntity.notFound().build();
    }
    
    // 转换为字节数组
    byte[] jsonBytes = jsonContent.getBytes(StandardCharsets.UTF_8);
    
    // 设置响应头，使用处理后的文件名
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers.setContentDispositionFormData("attachment", downloadFileName);
    headers.setContentLength(jsonBytes.length);
    
    return new ResponseEntity<>(jsonBytes, headers, HttpStatus.OK);
}
    
    /**
     * 根据POST请求数据下载模板文件（二进制流）
     * @param request 包含id和name的请求对象
     * @return 文件下载响应
     */
    @PostMapping("/loadtemple")
    public ResponseEntity<byte[]> loadTemplate(@RequestBody TemplateLoadRequest request) {
        // 优先使用name查找，如果name为空则使用id
        String searchKey = request.getName() != null ? request.getName() : request.getId();
        
        if (searchKey == null || searchKey.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        String jsonContent = templateExchangeService.getJsonContentByName(searchKey);
        if (jsonContent == null) {
            return ResponseEntity.notFound().build();
        }
        
        // 转换为字节数组
        byte[] jsonBytes = jsonContent.getBytes(StandardCharsets.UTF_8);
        
        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", searchKey + ".json");
        headers.setContentLength(jsonBytes.length);
        
        return new ResponseEntity<>(jsonBytes, headers, HttpStatus.OK);
    }
    
    /**
     * 模板加载请求对象
     */
    public static class TemplateLoadRequest {
        private String id;
        private String name;
        
        public String getId() {
            return id;
        }
        
        public void setId(String id) {
            this.id = id;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
    }
}