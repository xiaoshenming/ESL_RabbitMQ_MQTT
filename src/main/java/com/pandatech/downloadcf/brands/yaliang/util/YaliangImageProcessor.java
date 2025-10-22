package com.pandatech.downloadcf.brands.yaliang.util;

import com.pandatech.downloadcf.brands.yaliang.config.YaliangBrandConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * 雅量图片处理工具类
 */
@Slf4j
@Component
public class YaliangImageProcessor {
    
    /**
     * 处理图片：缩放、旋转、转换为Base64
     *
     * @param imageBase64 原始图片Base64数据
     * @param deviceSpec 设备规格
     * @return 处理后的Base64数据
     */
    public String processImage(String imageBase64, YaliangBrandConfig.DeviceSpec deviceSpec) {
        try {
            // 验证Base64数据
            if (imageBase64 == null || imageBase64.trim().isEmpty()) {
                throw new IllegalArgumentException("图片Base64数据为空");
            }
            
            // 清理并验证Base64数据
            String cleanedBase64 = cleanBase64(imageBase64);
            if (!isValidBase64(cleanedBase64)) {
                throw new IllegalArgumentException("无效的Base64数据格式");
            }
            
            // 解码Base64图片
            byte[] imageBytes;
            try {
                imageBytes = Base64.getDecoder().decode(cleanedBase64);
            } catch (IllegalArgumentException e) {
                log.error("Base64解码失败: {}", e.getMessage());
                throw new IllegalArgumentException("Base64解码失败: " + e.getMessage(), e);
            }
            
            if (imageBytes.length == 0) {
                throw new IllegalArgumentException("解码后的图片数据为空");
            }
            
            // 验证图片数据头部
            if (!isValidImageData(imageBytes)) {
                throw new IllegalArgumentException("无效的图片数据格式");
            }
            
            BufferedImage originalImage;
            try {
                originalImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
            } catch (IOException e) {
                log.error("图片读取失败: {}", e.getMessage());
                throw new IllegalArgumentException("图片数据损坏或格式不支持: " + e.getMessage(), e);
            }
            
            if (originalImage == null) {
                throw new IllegalArgumentException("无法解析图片数据，可能是不支持的图片格式");
            }
            
            log.info("原始图片尺寸: {}x{}, 目标尺寸: {}x{}, 旋转角度: {}°", 
                    originalImage.getWidth(), originalImage.getHeight(),
                    deviceSpec.getWidth(), deviceSpec.getHeight(), deviceSpec.getRotation());
            
            // 缩放图片到目标尺寸
            BufferedImage scaledImage = scaleImage(originalImage, deviceSpec.getWidth(), deviceSpec.getHeight());
            
            // 旋转图片
            BufferedImage rotatedImage = rotateImage(scaledImage, deviceSpec.getRotation());
            
            // 转换为Base64
            return imageToBase64(rotatedImage, "PNG", false);
            
        } catch (Exception e) {
            log.error("图片处理失败: {}", e.getMessage(), e);
            throw new RuntimeException("图片处理失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 处理图片：缩放、旋转、转换为Base64
     *
     * @param imageBase64 原始图片Base64数据
     * @param deviceSpec 设备规格
     * @param config 品牌配置
     * @return 处理后的Base64数据
     */
    public String processImage(String imageBase64, YaliangBrandConfig.DeviceSpec deviceSpec, 
                              YaliangBrandConfig config) {
        try {
            // 验证Base64数据
            if (imageBase64 == null || imageBase64.trim().isEmpty()) {
                throw new IllegalArgumentException("图片Base64数据为空");
            }
            
            // 清理并验证Base64数据
            String cleanedBase64 = cleanBase64(imageBase64);
            if (!isValidBase64(cleanedBase64)) {
                throw new IllegalArgumentException("无效的Base64数据格式");
            }
            
            // 解码Base64图片
            byte[] imageBytes;
            try {
                imageBytes = Base64.getDecoder().decode(cleanedBase64);
            } catch (IllegalArgumentException e) {
                log.error("Base64解码失败: {}", e.getMessage());
                throw new IllegalArgumentException("Base64解码失败: " + e.getMessage(), e);
            }
            
            if (imageBytes.length == 0) {
                throw new IllegalArgumentException("解码后的图片数据为空");
            }
            
            // 验证图片数据头部
            if (!isValidImageData(imageBytes)) {
                throw new IllegalArgumentException("无效的图片数据格式");
            }
            
            BufferedImage originalImage;
            try {
                originalImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
            } catch (IOException e) {
                log.error("图片读取失败: {}", e.getMessage());
                throw new IllegalArgumentException("图片数据损坏或格式不支持: " + e.getMessage(), e);
            }
            
            if (originalImage == null) {
                throw new IllegalArgumentException("无法解析图片数据，可能是不支持的图片格式");
            }
            
            log.info("原始图片尺寸: {}x{}, 目标尺寸: {}x{}, 旋转角度: {}°", 
                    originalImage.getWidth(), originalImage.getHeight(),
                    deviceSpec.getWidth(), deviceSpec.getHeight(), deviceSpec.getRotation());
            
            // 缩放图片到目标尺寸
            BufferedImage scaledImage = scaleImage(originalImage, deviceSpec.getWidth(), deviceSpec.getHeight());
            
            // 旋转图片
            BufferedImage rotatedImage = rotateImage(scaledImage, deviceSpec.getRotation());
            
            // 转换为Base64
            return imageToBase64(rotatedImage, config.getDataFormat().getImageFormat(), 
                               config.getDataFormat().isIncludeBase64Header());
            
        } catch (Exception e) {
            log.error("图片处理失败: {}", e.getMessage(), e);
            throw new RuntimeException("图片处理失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 缩放图片到指定尺寸
     */
    private BufferedImage scaleImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage scaledImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = scaledImage.createGraphics();
        
        // 设置高质量渲染
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 填充白色背景
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, targetWidth, targetHeight);
        
        // 计算缩放比例，保持宽高比
        double scaleX = (double) targetWidth / originalImage.getWidth();
        double scaleY = (double) targetHeight / originalImage.getHeight();
        double scale = Math.min(scaleX, scaleY);
        
        int scaledWidth = (int) (originalImage.getWidth() * scale);
        int scaledHeight = (int) (originalImage.getHeight() * scale);
        
        // 居中绘制
        int x = (targetWidth - scaledWidth) / 2;
        int y = (targetHeight - scaledHeight) / 2;
        
        g2d.drawImage(originalImage, x, y, scaledWidth, scaledHeight, null);
        g2d.dispose();
        
        return scaledImage;
    }
    
    /**
     * 旋转图片
     */
    private BufferedImage rotateImage(BufferedImage originalImage, int rotation) {
        if (rotation == 0) {
            return originalImage;
        }
        
        double radians = Math.toRadians(rotation);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        
        // 计算旋转后的尺寸
        int newWidth = (int) Math.floor(originalWidth * cos + originalHeight * sin);
        int newHeight = (int) Math.floor(originalHeight * cos + originalWidth * sin);
        
        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = rotatedImage.createGraphics();
        
        // 设置高质量渲染
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 填充白色背景
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, newWidth, newHeight);
        
        // 设置旋转变换
        AffineTransform transform = new AffineTransform();
        transform.translate(newWidth / 2.0, newHeight / 2.0);
        transform.rotate(radians);
        transform.translate(-originalWidth / 2.0, -originalHeight / 2.0);
        
        g2d.setTransform(transform);
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.dispose();
        
        return rotatedImage;
    }
    
    /**
     * 图片转Base64
     */
    private String imageToBase64(BufferedImage image, String format, boolean includeHeader) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, format, baos);
        byte[] imageBytes = baos.toByteArray();
        String base64 = Base64.getEncoder().encodeToString(imageBytes);
        
        if (includeHeader) {
            return "data:image/" + format.toLowerCase() + ";base64," + base64;
        }
        return base64;
    }
    
    /**
     * 清理Base64数据，移除头部信息
     */
    private String cleanBase64(String base64) {
        if (base64.contains(",")) {
            return base64.substring(base64.indexOf(",") + 1);
        }
        return base64.trim().replaceAll("\\s", ""); // 移除所有空白字符
    }
    
    /**
     * 验证Base64数据格式
     */
    private boolean isValidBase64(String base64) {
        if (base64 == null || base64.isEmpty()) {
            return false;
        }
        
        // Base64字符集验证
        if (!base64.matches("^[A-Za-z0-9+/]*={0,2}$")) {
            return false;
        }
        
        // 长度验证（Base64编码后的长度必须是4的倍数）
        if (base64.length() % 4 != 0) {
            return false;
        }
        
        return true;
    }
    
    /**
     * 验证图片数据头部
     */
    private boolean isValidImageData(byte[] imageBytes) {
        if (imageBytes == null || imageBytes.length < 8) {
            return false;
        }
        
        // PNG文件头: 89 50 4E 47 0D 0A 1A 0A
        if (imageBytes.length >= 8 && 
            imageBytes[0] == (byte) 0x89 && imageBytes[1] == 0x50 && 
            imageBytes[2] == 0x4E && imageBytes[3] == 0x47 &&
            imageBytes[4] == 0x0D && imageBytes[5] == 0x0A && 
            imageBytes[6] == 0x1A && imageBytes[7] == 0x0A) {
            return true;
        }
        
        // JPEG文件头: FF D8 FF
        if (imageBytes.length >= 3 && 
            imageBytes[0] == (byte) 0xFF && imageBytes[1] == (byte) 0xD8 && 
            imageBytes[2] == (byte) 0xFF) {
            return true;
        }
        
        // GIF文件头: GIF87a 或 GIF89a
        if (imageBytes.length >= 6) {
            String header = new String(imageBytes, 0, 6);
            if ("GIF87a".equals(header) || "GIF89a".equals(header)) {
                return true;
            }
        }
        
        // BMP文件头: BM
        if (imageBytes.length >= 2 && 
            imageBytes[0] == 0x42 && imageBytes[1] == 0x4D) {
            return true;
        }
        
        return false;
    }
    
    /**
     * 验证图片格式和大小
     */
    public boolean validateImage(String imageBase64, YaliangBrandConfig.Validation validation) {
        try {
            String cleanBase64 = cleanBase64(imageBase64);
            byte[] imageBytes = Base64.getDecoder().decode(cleanBase64);
            
            // 检查文件大小
            if (imageBytes.length > validation.getMaxImageSize()) {
                log.warn("图片大小超出限制: {} bytes > {} bytes", imageBytes.length, validation.getMaxImageSize());
                return false;
            }
            
            // 检查图片格式
            if (validation.isValidateImageFormat()) {
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
                if (image == null) {
                    log.warn("无法解析图片数据");
                    return false;
                }
            }
            
            return true;
        } catch (Exception e) {
            log.error("图片验证失败: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * 验证图片格式
     *
     * @param imageBase64 图片Base64数据
     * @throws IllegalArgumentException 如果图片格式无效
     */
    public void validateImageFormat(String imageBase64) {
        try {
            String cleanBase64 = cleanBase64(imageBase64);
            byte[] imageBytes = Base64.getDecoder().decode(cleanBase64);
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
            
            if (image == null) {
                throw new IllegalArgumentException("无法解析图片数据，可能是不支持的图片格式");
            }
            
            log.debug("图片格式验证通过，尺寸: {}x{}", image.getWidth(), image.getHeight());
        } catch (Exception e) {
            log.error("图片格式验证失败: {}", e.getMessage());
            throw new IllegalArgumentException("图片格式验证失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 验证图片大小
     *
     * @param imageBase64 图片Base64数据
     * @throws IllegalArgumentException 如果图片大小超出限制
     */
    public void validateImageSize(String imageBase64) {
        validateImageSize(imageBase64, 5 * 1024 * 1024); // 默认5MB限制
    }
    
    /**
     * 验证图片大小
     *
     * @param imageBase64 图片Base64数据
     * @param maxSizeBytes 最大文件大小（字节）
     * @throws IllegalArgumentException 如果图片大小超出限制
     */
    public void validateImageSize(String imageBase64, long maxSizeBytes) {
        try {
            String cleanBase64 = cleanBase64(imageBase64);
            byte[] imageBytes = Base64.getDecoder().decode(cleanBase64);
            
            if (imageBytes.length > maxSizeBytes) {
                throw new IllegalArgumentException(String.format("图片大小超出限制: %d bytes > %d bytes", 
                    imageBytes.length, maxSizeBytes));
            }
            
            log.debug("图片大小验证通过: {} bytes", imageBytes.length);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.error("图片大小验证失败: {}", e.getMessage());
            throw new IllegalArgumentException("图片大小验证失败: " + e.getMessage(), e);
        }
    }
}