package com.pandatech.downloadcf.brands.yaliang.service;

import com.pandatech.downloadcf.brands.yaliang.exception.YaliangException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 雅量ESL ID解析服务
 * 负责解析雅量品牌特有的ESL ID格式
 */
@Slf4j
@Service
public class YaliangEslIdParser {
    
    /**
     * 解析雅量ESL ID格式
     * 格式: CG101F6D-5414A7B9B046 (code-mac，MAC地址需要补充前缀00)
     */
    public YaliangEslIdInfo parseYaliangEslId(String eslId) {
        if (eslId == null || eslId.trim().isEmpty()) {
            throw new YaliangException(YaliangException.ErrorCodes.INVALID_ESL_ID, "ESL ID不能为空");
        }
        
        String cleanEslId = eslId.trim().toUpperCase();
        
        // 检查是否包含连字符
        if (!cleanEslId.contains("-")) {
            throw new YaliangException(YaliangException.ErrorCodes.INVALID_ESL_ID, 
                "雅量ESL ID格式错误，期望格式: CODE-MAC，实际: " + eslId);
        }
        
        String[] parts = cleanEslId.split("-");
        if (parts.length != 2) {
            throw new YaliangException(YaliangException.ErrorCodes.INVALID_ESL_ID, 
                "雅量ESL ID格式错误，期望格式: CODE-MAC，实际: " + eslId);
        }
        
        String deviceCode = parts[0]; // CG101F6D
        String rawMac = parts[1];     // 5414A7B9B046
        
        // MAC地址需要补充前缀"00"，确保格式为16位
        String deviceMac = rawMac;
        if (rawMac.length() == 12) {
            deviceMac = "00" + rawMac; // 补充前缀00，变为00125414A7B9B046
        } else if (rawMac.length() != 14) {
            log.warn("MAC地址长度异常: {}, 长度: {}", rawMac, rawMac.length());
        }
        
        // 验证格式
        if (deviceCode.isEmpty() || deviceMac.isEmpty()) {
            throw new YaliangException(YaliangException.ErrorCodes.INVALID_ESL_ID, 
                "雅量ESL ID的code或mac部分不能为空: " + eslId);
        }
        
        log.info("雅量ESL ID解析成功: 原始={}, 设备代码={}, 原始MAC={}, 处理后MAC={}", 
                eslId, deviceCode, rawMac, deviceMac);
        
        return new YaliangEslIdInfo(deviceCode, deviceMac);
    }
    
    /**
     * 雅量ESL ID信息类
     */
    public static class YaliangEslIdInfo {
        private final String deviceCode;
        private final String deviceMac;
        
        public YaliangEslIdInfo(String deviceCode, String deviceMac) {
            this.deviceCode = deviceCode;
            this.deviceMac = deviceMac;
        }
        
        public String getDeviceCode() {
            return deviceCode;
        }
        
        public String getDeviceMac() {
            return deviceMac;
        }
    }
}