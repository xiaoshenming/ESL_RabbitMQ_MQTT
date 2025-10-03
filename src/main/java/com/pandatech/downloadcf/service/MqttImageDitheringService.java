package com.pandatech.downloadcf.service;

import org.springframework.stereotype.Service;
import java.awt.image.BufferedImage;

/**
 * MQTT图像抖动处理服务
 * 专门处理电子墨水屏的图像抖动算法
 */
@Service
public class MqttImageDitheringService {

    /**
     * 获取电子墨水屏调色板
     * 根据颜色数量返回对应的标准调色板
     */
    public int[][] getEInkPalette(String colorCount) {
        switch (colorCount) {
            case "2":
                return new int[][]{
                    {0, 0, 0},       // 纯黑色
                    {255, 255, 255}  // 纯白色
                };
            case "3":
                return new int[][]{
                    {0, 0, 0},       // 纯黑色
                    {255, 255, 255}, // 纯白色
                    {255, 0, 0}      // 纯红色 - 电子墨水屏标准红色
                };
            case "4":
                return new int[][]{
                    {0, 0, 0},       // 纯黑色
                    {255, 255, 255}, // 纯白色
                    {255, 0, 0},     // 纯红色
                    {255, 255, 0}    // 纯黄色
                };
            default:
                return new int[][]{
                    {0, 0, 0},       // 纯黑色
                    {255, 255, 255}, // 纯白色
                    {255, 0, 0}      // 纯红色
                };
        }
    }
    
    /**
     * 应用Floyd-Steinberg抖动算法
     * 针对电子墨水屏优化的抖动处理
     */
    public BufferedImage applyFloydSteinbergDithering(BufferedImage image, int[][] palette) {
        int width = image.getWidth();
        int height = image.getHeight();
        
        // 创建输出图像
        BufferedImage result = new BufferedImage(
            width, height, BufferedImage.TYPE_INT_RGB
        );
        
        // 创建误差数组
        double[][][] errors = new double[height][width][3];
        
        // ImageMagick标准扩散强度：85%
        final double DIFFUSION_AMOUNT = 0.85;
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // 获取原始像素颜色
                int rgb = image.getRGB(x, y);
                double[] oldPixel = {
                    ((rgb >> 16) & 0xFF) + errors[y][x][0],
                    ((rgb >> 8) & 0xFF) + errors[y][x][1],
                    (rgb & 0xFF) + errors[y][x][2]
                };
                
                // 限制颜色值范围
                for (int i = 0; i < 3; i++) {
                    oldPixel[i] = Math.max(0, Math.min(255, oldPixel[i]));
                }
                
                // 找到最接近的调色板颜色
                int[] newPixel = findClosestColor(oldPixel, palette);
                
                // 设置新像素
                int newRgb = (newPixel[0] << 16) | (newPixel[1] << 8) | newPixel[2];
                result.setRGB(x, y, newRgb);
                
                // 计算误差
                double[] error = {
                    oldPixel[0] - newPixel[0],
                    oldPixel[1] - newPixel[1],
                    oldPixel[2] - newPixel[2]
                };
                
                // 标准Floyd-Steinberg误差扩散（ImageMagick标准权重分布）
                // 扩散到相邻像素：右(7/16)、左下(3/16)、下(5/16)、右下(1/16)
                distributeError(errors, error, x, y, width, height, DIFFUSION_AMOUNT);
            }
        }
        
        return result;
    }
    
    /**
     * 分发误差到相邻像素
     */
    private void distributeError(double[][][] errors, double[] error, int x, int y, 
                               int width, int height, double diffusionAmount) {
        if (x + 1 < width) {
            // 右像素：7/16
            errors[y][x + 1][0] += error[0] * (7.0 / 16.0) * diffusionAmount;
            errors[y][x + 1][1] += error[1] * (7.0 / 16.0) * diffusionAmount;
            errors[y][x + 1][2] += error[2] * (7.0 / 16.0) * diffusionAmount;
        }
        if (y + 1 < height) {
            if (x > 0) {
                // 左下像素：3/16
                errors[y + 1][x - 1][0] += error[0] * (3.0 / 16.0) * diffusionAmount;
                errors[y + 1][x - 1][1] += error[1] * (3.0 / 16.0) * diffusionAmount;
                errors[y + 1][x - 1][2] += error[2] * (3.0 / 16.0) * diffusionAmount;
            }
            // 下像素：5/16
            errors[y + 1][x][0] += error[0] * (5.0 / 16.0) * diffusionAmount;
            errors[y + 1][x][1] += error[1] * (5.0 / 16.0) * diffusionAmount;
            errors[y + 1][x][2] += error[2] * (5.0 / 16.0) * diffusionAmount;
            if (x + 1 < width) {
                // 右下像素：1/16
                errors[y + 1][x + 1][0] += error[0] * (1.0 / 16.0) * diffusionAmount;
                errors[y + 1][x + 1][1] += error[1] * (1.0 / 16.0) * diffusionAmount;
                errors[y + 1][x + 1][2] += error[2] * (1.0 / 16.0) * diffusionAmount;
            }
        }
    }
    
    /**
     * 找到调色板中最接近的颜色
     * 针对电子墨水屏优化的颜色匹配算法
     */
    private int[] findClosestColor(double[] pixel, int[][] palette) {
        double r = pixel[0];
        double g = pixel[1];
        double b = pixel[2];
        
        // 标准欧几里得距离计算（ImageMagick标准实现）
        double minDistance = Double.MAX_VALUE;
        int[] closestColor = palette[0];
        
        for (int[] color : palette) {
            // 计算标准欧几里得距离
            double distance = Math.sqrt(
                Math.pow(r - color[0], 2) +
                Math.pow(g - color[1], 2) +
                Math.pow(b - color[2], 2)
            );
            
            if (distance < minDistance) {
                minDistance = distance;
                closestColor = color;
            }
        }
        
        return closestColor.clone();
    }
}