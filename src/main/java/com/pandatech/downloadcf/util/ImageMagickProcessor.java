package com.pandatech.downloadcf.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

/**
 * ImageMagick图像处理工具类
 * 专门用于电子墨水屏图像的高质量处理
 * 完美替代Java原生图像处理，获得最佳显示效果
 */
@Component
public class ImageMagickProcessor {
    
    private static final Logger log = LoggerFactory.getLogger(ImageMagickProcessor.class);
    
    // 临时文件目录
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir") + File.separator + "eink_processing";
    
    // ImageMagick命令超时时间（秒）
    private static final int COMMAND_TIMEOUT = 30;
    
    static {
        // 确保临时目录存在
        try {
            Files.createDirectories(Paths.get(TEMP_DIR));
            log.info("ImageMagick临时处理目录已创建: {}", TEMP_DIR);
        } catch (IOException e) {
            log.error("创建临时目录失败: {}", e.getMessage());
        }
    }
    
    /**
     * 电子墨水屏颜色模式定义
     */
    public enum EInkColorMode {
        BLACK_WHITE("2", new String[]{"#000000", "#FFFFFF"}),
        BLACK_WHITE_RED("3", new String[]{"#000000", "#FFFFFF", "#FF0000"}),
        BLACK_WHITE_RED_YELLOW("4", new String[]{"#000000", "#FFFFFF", "#FF0000", "#FFFF00"}),
        FULL_COLOR("7", new String[]{"#000000", "#FFFFFF", "#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FF00FF", "#00FFFF"});
        
        private final String code;
        private final String[] colors;
        
        EInkColorMode(String code, String[] colors) {
            this.code = code;
            this.colors = colors;
        }
        
        public String getCode() { return code; }
        public String[] getColors() { return colors; }
        
        public static EInkColorMode fromCode(String code) {
            for (EInkColorMode mode : values()) {
                if (mode.code.equals(code)) {
                    return mode;
                }
            }
            return BLACK_WHITE_RED; // 默认三色模式
        }
    }
    
    /**
     * 使用ImageMagick处理图像，生成完美的电子墨水屏显示效果
     * 
     * @param base64Image Base64编码的原始图像
     * @param rgbMode 颜色模式代码
     * @param targetWidth 目标宽度
     * @param targetHeight 目标高度
     * @return 处理后的Base64编码图像
     */
    public String processImageForEInk(String base64Image, String rgbMode, int targetWidth, int targetHeight) {
        if (base64Image == null || base64Image.trim().isEmpty()) {
            log.warn("输入图像数据为空");
            return "";
        }
        
        String inputFile = null;
        String paletteFile = null;
        String outputFile = null;
        
        try {
            // 解析Base64数据
            String imageData = extractBase64Data(base64Image);
            byte[] imgBytes = Base64.getDecoder().decode(imageData);
            
            // 创建临时文件
            long timestamp = System.currentTimeMillis();
            inputFile = TEMP_DIR + File.separator + "input_" + timestamp + ".png";
            paletteFile = TEMP_DIR + File.separator + "palette_" + timestamp + ".png";
            outputFile = TEMP_DIR + File.separator + "output_" + timestamp + ".png";
            
            // 保存输入图像
            Files.write(Paths.get(inputFile), imgBytes);
            log.debug("输入图像已保存: {}", inputFile);
            
            // 获取颜色模式
            EInkColorMode colorMode = EInkColorMode.fromCode(rgbMode);
            
            // 生成调色板文件
            createPaletteFile(paletteFile, colorMode);
            log.debug("调色板文件已创建: {}", paletteFile);
            
            // 执行ImageMagick处理命令
            boolean success = executeImageMagickCommand(inputFile, paletteFile, outputFile, targetWidth, targetHeight, colorMode);
            
            if (!success) {
                log.error("ImageMagick处理失败");
                return "";
            }
            
            // 读取处理后的图像并转换为Base64
            byte[] processedBytes = Files.readAllBytes(Paths.get(outputFile));
            String result = Base64.getEncoder().encodeToString(processedBytes);
            
            log.info("ImageMagick处理成功 - 原始: {} bytes, 处理后: {} bytes, 尺寸: {}x{}, 模式: {}", 
                    imgBytes.length, processedBytes.length, targetWidth, targetHeight, colorMode.name());
            
            return result;
            
        } catch (Exception e) {
            log.error("ImageMagick图像处理失败: {}", e.getMessage(), e);
            return "";
        } finally {
            // 清理临时文件
            cleanupTempFiles(inputFile, paletteFile, outputFile);
        }
    }
    
    /**
     * 提取Base64数据部分
     */
    private String extractBase64Data(String base64Image) {
        if (base64Image.contains(",")) {
            return base64Image.split(",", 2)[1];
        }
        return base64Image;
    }
    
    /**
     * 创建ImageMagick调色板文件
     * 这是获得完美电子墨水屏效果的关键
     */
    private void createPaletteFile(String paletteFile, EInkColorMode colorMode) throws IOException {
        // 创建1x1像素的调色板图像，每个颜色占一个像素
        String[] colors = colorMode.getColors();
        int paletteWidth = colors.length;
        int paletteHeight = 1;
        
        // 构建ImageMagick命令创建调色板
        StringBuilder paletteCommand = new StringBuilder();
        paletteCommand.append("magick -size ").append(paletteWidth).append("x").append(paletteHeight).append(" xc:none");
        
        for (int i = 0; i < colors.length; i++) {
            paletteCommand.append(" -fill \"").append(colors[i]).append("\"");
            paletteCommand.append(" -draw \"point ").append(i).append(",0\"");
        }
        
        paletteCommand.append(" \"").append(paletteFile).append("\"");
        
        try {
            executeCommand(paletteCommand.toString());
            log.debug("调色板创建命令执行成功: {}", paletteCommand.toString());
        } catch (Exception e) {
            log.error("创建调色板文件失败: {}", e.getMessage());
            throw new IOException("调色板创建失败", e);
        }
    }
    
    /**
     * 执行核心的ImageMagick图像处理命令
     * 使用最优参数获得完美的电子墨水屏显示效果
     */
    private boolean executeImageMagickCommand(String inputFile, String paletteFile, String outputFile, 
                                            int targetWidth, int targetHeight, EInkColorMode colorMode) {
        try {
            // 构建完美的ImageMagick处理命令
            StringBuilder command = new StringBuilder();
            command.append("magick \"").append(inputFile).append("\"");
            
            // 第一步：调整图像尺寸，使用高质量的Lanczos滤镜
            command.append(" -resize ").append(targetWidth).append("x").append(targetHeight).append("!");
            command.append(" -filter Lanczos");
            
            // 第二步：颜色空间转换和优化
            command.append(" -colorspace sRGB");
            
            // 第三步：针对不同颜色模式的特殊优化
            switch (colorMode) {
                case BLACK_WHITE:
                    // 黑白模式：增强对比度
                    command.append(" -contrast-stretch 2%x1%");
                    break;
                case BLACK_WHITE_RED:
                    // 三色模式：保护红色通道，增强对比度
                    command.append(" -channel RGB -contrast-stretch 1%x1%");
                    command.append(" -modulate 100,120,100"); // 轻微增加饱和度
                    break;
                case BLACK_WHITE_RED_YELLOW:
                    // 四色模式：平衡处理
                    command.append(" -contrast-stretch 1%x1%");
                    command.append(" -modulate 100,110,100");
                    break;
                default:
                    command.append(" -contrast-stretch 1%x1%");
            }
            
            // 第四步：应用Floyd-Steinberg抖动算法到指定调色板
            // 这是获得完美效果的关键步骤
            command.append(" -dither FloydSteinberg");
            command.append(" -remap \"").append(paletteFile).append("\"");
            
            // 第五步：最终优化和输出
            command.append(" -quality 100");
            command.append(" -depth 8");
            command.append(" \"").append(outputFile).append("\"");
            
            log.info("执行ImageMagick命令: {}", command.toString());
            
            // 执行命令
            executeCommand(command.toString());
            
            // 验证输出文件是否存在
            if (Files.exists(Paths.get(outputFile))) {
                log.info("ImageMagick处理成功，输出文件大小: {} bytes", Files.size(Paths.get(outputFile)));
                return true;
            } else {
                log.error("ImageMagick处理失败，输出文件不存在");
                return false;
            }
            
        } catch (Exception e) {
            log.error("执行ImageMagick命令失败: {}", e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * 执行系统命令
     */
    private void executeCommand(String command) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder();
        
        // 根据操作系统选择命令执行方式
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            pb.command("cmd", "/c", command);
        } else {
            pb.command("bash", "-c", command);
        }
        
        pb.directory(new File(TEMP_DIR));
        pb.redirectErrorStream(true);
        
        Process process = pb.start();
        
        // 读取命令输出
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }
        
        // 等待命令完成
        boolean finished = process.waitFor(COMMAND_TIMEOUT, TimeUnit.SECONDS);
        
        if (!finished) {
            process.destroyForcibly();
            throw new IOException("ImageMagick命令执行超时");
        }
        
        int exitCode = process.exitValue();
        if (exitCode != 0) {
            log.error("ImageMagick命令执行失败，退出码: {}, 输出: {}", exitCode, output.toString());
            throw new IOException("ImageMagick命令执行失败，退出码: " + exitCode);
        }
        
        log.debug("ImageMagick命令执行成功，输出: {}", output.toString());
    }
    
    /**
     * 清理临时文件
     */
    private void cleanupTempFiles(String... files) {
        for (String file : files) {
            if (file != null) {
                try {
                    Files.deleteIfExists(Paths.get(file));
                    log.debug("临时文件已清理: {}", file);
                } catch (IOException e) {
                    log.warn("清理临时文件失败: {}, 错误: {}", file, e.getMessage());
                }
            }
        }
    }
    
    /**
     * 检查ImageMagick是否可用
     */
    public boolean isImageMagickAvailable() {
        try {
            executeCommand("magick -version");
            log.info("ImageMagick可用性检查通过");
            return true;
        } catch (Exception e) {
            log.warn("ImageMagick不可用: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * 获取ImageMagick版本信息
     */
    public String getImageMagickVersion() {
        try {
            ProcessBuilder pb = new ProcessBuilder();
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                pb.command("cmd", "/c", "magick -version");
            } else {
                pb.command("bash", "-c", "magick -version");
            }
            
            Process process = pb.start();
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                    if (line.contains("Version:")) {
                        return line.trim();
                    }
                }
            }
            
            process.waitFor(5, TimeUnit.SECONDS);
            return output.toString().split("\n")[0];
            
        } catch (Exception e) {
            return "ImageMagick版本检测失败: " + e.getMessage();
        }
    }
}