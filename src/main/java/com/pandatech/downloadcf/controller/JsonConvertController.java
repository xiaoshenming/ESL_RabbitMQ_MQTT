package com.pandatech.downloadcf.controller;

import com.pandatech.downloadcf.entity.CFJson;
import com.pandatech.downloadcf.entity.PlatformJson;
import com.pandatech.downloadcf.service.JsonConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/convert")
public class JsonConvertController {
    
    @Autowired
    private JsonConvertService jsonConvertService;
    
    /**
     * 将PlatformJson转换为CFJson
     * @param platformJson 源数据
     * @return 转换后的CFJson
     */
    @PostMapping("/platform-to-cf")
    public ResponseEntity<CFJson> convertPlatformToCF(@RequestBody PlatformJson platformJson) {
        CFJson result = jsonConvertService.convertPlatformToCF(platformJson);
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }
    
    /**
     * 将CFJson转换为PlatformJson
     * @param cfJson 源数据
     * @return 转换后的PlatformJson
     */
    @PostMapping("/cf-to-platform")
    public ResponseEntity<PlatformJson> convertCFToPlatform(@RequestBody CFJson cfJson) {
        PlatformJson result = jsonConvertService.convertCFToPlatform(cfJson);
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }
}