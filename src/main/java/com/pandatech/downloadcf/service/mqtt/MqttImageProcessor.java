package com.pandatech.downloadcf.service.mqtt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import javax.imageio.ImageIO;

/**
 * MQTT图像处理器
 * 专门负责图像处理和转换
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MqttImageProcessor {

    private final ObjectMapper objectMapper;

    /**
     * 为2.13F生成简化的dval - 修复关键问题
     * 基于成功案例的分析，2.13F需要更简洁的图片处理
     */
    public String generateSimplifiedDval(String base64Image, String rgbMode, int width, int height) {
        if (base64Image == null || base64Image.trim().isEmpty()) {
            log.debug("图片数据为空，返回简化的空dval");
            return "";
        }

        try {
            // 简化的图片处理，专门针对2.13F优化
            log.debug("为2.13F生成简化dval - 尺寸: {}x{}, 颜色模式: {}", width, height, rgbMode);
            
            // 解码Base64图片
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
            
            if (originalImage == null) {
                log.warn("无法解码图片数据");
                return "";
            }
            
            // 调整图片尺寸
            BufferedImage resizedImage = resizeImage(originalImage, width, height);
            
            // 转换为字节数组
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "PNG", baos);
            byte[] processedImageBytes = baos.toByteArray();
            
            // 返回Base64编码的结果
            return Base64.getEncoder().encodeToString(processedImageBytes);
            
        } catch (Exception e) {
            log.error("生成简化dval失败: {}", e.getMessage(), e);
            return "";
        }
    }

    /**
     * 调整图片尺寸
     */
    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();
        
        // 设置高质量渲染
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制调整后的图片
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        
        return resizedImage;
    }

    /**
     * 智能边框宽度解析器 - 支持多种格式
     * 能够解析数字、字符串、带单位的宽度值
     */
    public double parseBorderWidth(JsonNode borderWidthNode) {
        if (borderWidthNode == null || borderWidthNode.isNull()) {
            return 0.75; // 默认宽度
        }
        
        try {
            // 如果是数字类型，直接返回
            if (borderWidthNode.isNumber()) {
                return borderWidthNode.asDouble();
            }
            
            // 如果是字符串类型，进行智能解析
            String borderWidthStr = borderWidthNode.asText().trim();
            if (borderWidthStr.isEmpty()) {
                return 0.75;
            }
            
            // 移除单位后缀（pt、px等）并解析数字
            String numericPart = borderWidthStr.replaceAll("[a-zA-Z%]+$", "");
            
            // 解析数字
            double width = Double.parseDouble(numericPart);
            
            // 如果原始字符串包含px单位，需要转换为pt（1px ≈ 0.75pt）
            if (borderWidthStr.toLowerCase().contains("px")) {
                width = width * 0.75; // px转pt
                log.debug("🔄 边框宽度单位转换: {}px -> {}pt", width / 0.75, width);
            }
            
            return width;
            
        } catch (Exception e) {
            log.warn("⚠️ 解析边框宽度失败: {}, 使用默认值0.75pt", borderWidthNode.asText(), e);
            return 0.75;
        }
    }

    /**
     * 完美的边框宽度转换系统 - 根据映射关系动态转换
     * 实现pt宽度到BorderStyle值的精确映射
     */
    public int convertBorderWidthToStyle(double borderWidthPt, String borderType) {
        // 如果是无边框类型，直接返回0
        if ("none".equals(borderType) || "transparent".equals(borderType)) {
            return 0;
        }
        
        // 根据映射关系进行转换
        // 0.75pt-1, 1.5pt-2, 2.25pt-3, 3pt-4, 3.75pt-5, 4.5pt-6, 5.25pt-7, 6pt-8, 6.75pt-9
        if (borderWidthPt <= 0) {
            return 0; // 无边框
        } else if (borderWidthPt <= 0.75) {
            return 1; // 0.75pt
        } else if (borderWidthPt <= 1.5) {
            return 2; // 1.5pt
        } else if (borderWidthPt <= 2.25) {
            return 3; // 2.25pt
        } else if (borderWidthPt <= 3.0) {
            return 4; // 3pt
        } else if (borderWidthPt <= 3.75) {
            return 5; // 3.75pt
        } else if (borderWidthPt <= 4.5) {
            return 6; // 4.5pt
        } else if (borderWidthPt <= 5.25) {
            return 7; // 5.25pt
        } else if (borderWidthPt <= 6.0) {
            return 8; // 6pt
        } else if (borderWidthPt <= 6.75) {
            return 9; // 6.75pt
        } else {
            // 超过6.75pt的边框，使用最大值9
            log.debug("⚠️ 边框宽度{}pt超出映射范围，使用最大值9", borderWidthPt);
            return 9;
        }
    }

    /**
     * 根据TagType获取正确的hext值 - 修复2.13F关键问题
     * 基于成功案例的分析结果
     */
    public String getHextValue(String tagType) {
        if (tagType == null) return "6";
        
        switch (tagType) {
            case "06": // 2.13T
                return "6";
            case "07": // 2.13F
                return "6";
            case "0B": // 2.66T
                return "0";
            case "0C": // 2.66F
                return "0";
            case "02": // 1.54T
                return "0";
            case "0A": // 2.9T
                return "0";
            case "1C": // 4.2T - 修复：应该返回"0"而不是"6"
                return "0";
            case "1D": // 4.2F - 修复：应该返回"0"而不是"6"
                return "0";
            case "1E": // 7.5T
                return "0";
            default:
                log.debug("未知TagType: {}, 使用默认hext值: 6", tagType);
                return "6";
        }
    }

    /**
     * 根据屏幕类型获取TagType
     */
    public String getTagType(String screenType) {
        log.info("开始转换屏幕类型到TagType，输入屏幕类型: {}", screenType);
        
        if (screenType == null || screenType.trim().isEmpty()) {
            log.warn("屏幕类型为空，使用默认TagType: 06");
            return "06";
        }
        
        // 从模板名称中提取屏幕类型
        String extractedType = extractScreenTypeFromTemplateName(screenType);
        String normalizedType = extractedType.toLowerCase().trim();
        log.info("标准化后的屏幕类型: {}", normalizedType);
        
        String result;
        switch (normalizedType) {
            case "2.13t":
            case "2.13":
                result = "06";
                break;
            case "2.13f":
                result = "07";
                break;
            case "2.66t":
                result = "0B";
                break;
            case "2.66f":
                result = "0C";
                break;
            case "4.2t":
            case "4.20t":
            case "4.20":
                result = "1C";
                break;
            case "4.2f":
            case "4.20f":
                result = "1D";
                break;
            case "2.9t":
            case "2.90t":
            case "2.90":
                result = "0A";
                break;
            case "1.54t":
            case "1.54":
                result = "02";
                break;
            case "7.5t":
            case "7.50t":
            case "7.50":
                result = "1E";
                break;
            default:
                log.warn("未知的屏幕类型: {}, 使用默认TagType: 06", screenType);
                result = "06";
                break;
        }
        
        log.info("屏幕类型转换完成: {} -> {}", screenType, result);
        return result;
    }
    
    /**
     * 从模板名称中提取屏幕类型
     */
    private String extractScreenTypeFromTemplateName(String templateName) {
        if (templateName == null) return "";
        
        // 匹配模式：AES模板4.2T_1C.json -> 4.2T
        // 注意：需要先匹配更具体的模式，避免4.20F被4.2F匹配
        if (templateName.contains("4.20T")) {
            return "4.20T";
        }
        if (templateName.contains("4.20F")) {
            return "4.20F";
        }
        if (templateName.contains("4.2T")) {
            return "4.2T";
        }
        if (templateName.contains("4.2F")) {
            return "4.2F";
        }
        if (templateName.contains("2.13T")) {
            return "2.13T";
        }
        if (templateName.contains("2.13F")) {
            return "2.13F";
        }
        if (templateName.contains("2.66T")) {
            return "2.66T";
        }
        if (templateName.contains("2.66F")) {
            return "2.66F";
        }
        if (templateName.contains("1.54T")) {
            return "1.54T";
        }
        if (templateName.contains("2.9T")) {
            return "2.9T";
        }
        if (templateName.contains("7.5T")) {
            return "7.5T";
        }
        
        // 如果没有匹配到，返回原始名称
        return templateName;
    }
}