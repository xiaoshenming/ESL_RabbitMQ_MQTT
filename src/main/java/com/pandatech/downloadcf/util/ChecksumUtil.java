package com.pandatech.downloadcf.util;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

/**
 * 校验码工具类
 */
@Slf4j
public class ChecksumUtil {
    
    /**
     * 计算MD5校验码
     */
    public static String calculateMd5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("MD5算法不可用", e);
            throw new RuntimeException("MD5算法不可用", e);
        }
    }
    
    /**
     * 计算CRC32校验码
     */
    public static String calculateCrc32(String data) {
        CRC32 crc32 = new CRC32();
        crc32.update(data.getBytes(StandardCharsets.UTF_8));
        return Long.toHexString(crc32.getValue());
    }
    
    /**
     * 根据算法类型计算校验码
     */
    public static String calculateChecksum(String data, String algorithm) {
        if (data == null || data.isEmpty()) {
            return "";
        }
        
        switch (algorithm.toLowerCase()) {
            case "md5":
                return calculateMd5(data);
            case "crc32":
                return calculateCrc32(data);
            default:
                log.warn("不支持的校验算法: {}, 使用MD5", algorithm);
                return calculateMd5(data);
        }
    }
}