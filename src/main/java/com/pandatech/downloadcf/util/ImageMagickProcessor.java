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
     * 使用ImageMagick处理图像，完全按照Python标准实现
     * 与原始Python代码_deal_draw_pic_data方法100%一致
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
        String outputFile = null;
        
        try {
            // 步骤1: 解析Base64数据（与Python完全一致）
            String imageData = extractBase64Data(base64Image);
            byte[] imgBytes = Base64.getDecoder().decode(imageData);
            
            // 步骤2: 创建临时文件（与Python路径结构一致）
            long timestamp = System.currentTimeMillis();
            inputFile = TEMP_DIR + File.separator + "raw_temp.png";  // 与Python一致的文件名
            outputFile = TEMP_DIR + File.separator + "out_temp.png"; // 与Python一致的文件名
            
            // 步骤3: 保存输入图像到临时文件（与Python完全一致）
            Files.write(Paths.get(inputFile), imgBytes);
            log.debug("输入图像已保存: {}", inputFile);
            
            // 步骤4: 根据rgb参数选择颜色映射文件（与Python完全一致）
            String paletteFile = getPaletteFilePath(rgbMode);
            log.debug("使用调色板文件: {}", paletteFile);
            
            // 步骤5: 执行ImageMagick处理命令（与Python完全一致）
            boolean success = executeImageMagickCommand(inputFile, paletteFile, outputFile, targetWidth, targetHeight);
            
            if (!success) {
                log.error("ImageMagick处理失败");
                return "";
            }
            
            // 步骤6: 读取处理后的图像并转换为Base64（与Python完全一致）
            byte[] processedBytes = Files.readAllBytes(Paths.get(outputFile));
            String result = Base64.getEncoder().encodeToString(processedBytes);
            
            log.info("ImageMagick处理成功 - 原始: {} bytes, 处理后: {} bytes, 尺寸: {}x{}, 模式: {}", 
                    imgBytes.length, processedBytes.length, targetWidth, targetHeight, rgbMode);
            
            return result;
            
        } catch (Exception e) {
            log.error("ImageMagick图像处理失败: {}", e.getMessage(), e);
            return "";
        } finally {
            // 清理临时文件
            cleanupTempFiles(inputFile, null, outputFile);
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
     * 根据rgb参数选择颜色映射文件（与Python完全一致）
     * Python原始逻辑:
     * if rgb == "2": ftcolor = "2color.png"
     * elif rgb == "3": ftcolor = "3color.png" 
     * elif rgb == "4": ftcolor = "4color.png"
     * else: ftcolor = "3color.png"  # 默认3色
     */
    private String getPaletteFilePath(String rgbMode) {
        // 获取项目根目录下的color-palettes文件夹
        String projectRoot = System.getProperty("user.dir");
        String paletteDir = projectRoot + File.separator + "color-palettes";
        
        String paletteFileName;
        
        // 完全按照Python逻辑选择调色板文件
        if ("2".equals(rgbMode)) {
            paletteFileName = "2color.png";
        } else if ("3".equals(rgbMode)) {
            paletteFileName = "3color.png";
        } else if ("4".equals(rgbMode)) {
            paletteFileName = "4color.png";
        } else {
            // 默认使用3色调色板（与Python一致）
            paletteFileName = "3color.png";
            log.debug("未知的rgb模式: {}, 使用默认3色调色板", rgbMode);
        }
        
        String paletteFilePath = paletteDir + File.separator + paletteFileName;
        
        // 验证调色板文件是否存在
        if (!Files.exists(Paths.get(paletteFilePath))) {
            log.error("调色板文件不存在: {}", paletteFilePath);
            // 如果指定文件不存在，尝试使用默认的3color.png
            String defaultPalette = paletteDir + File.separator + "3color.png";
            if (Files.exists(Paths.get(defaultPalette))) {
                log.warn("使用默认调色板文件: {}", defaultPalette);
                return defaultPalette;
            } else {
                throw new RuntimeException("找不到任何可用的调色板文件，请确保color-palettes目录存在且包含调色板文件");
            }
        }
        
        log.debug("选择调色板文件: {} (rgb模式: {})", paletteFilePath, rgbMode);
        return paletteFilePath;
    }
    
    /**
     * 执行核心的ImageMagick图像处理命令
     * 完全按照原始Python标准实现，确保100%一致的效果
     * 原始Python命令: convert input -resize WxH! -dither FloydSteinberg -define dither:diffusion-amount=85% -remap palette output
     */
    private boolean executeImageMagickCommand(String inputFile, String paletteFile, String outputFile, 
                                            int targetWidth, int targetHeight) {
        try {
            // 构建与Python完全一致的ImageMagick命令
            // Python原始命令格式: convert {} -resize {}X{}! -dither FloydSteinberg -define dither:diffusion-amount={}% -remap {} {}
            StringBuilder command = new StringBuilder();
            command.append("convert \"").append(inputFile).append("\"");
            
            // 步骤1: 精确调整尺寸（与Python完全一致）
            command.append(" -resize ").append(targetWidth).append("X").append(targetHeight).append("!");
            
            // 步骤2: Floyd-Steinberg抖动算法（与Python完全一致）
            command.append(" -dither FloydSteinberg");
            
            // 步骤3: 抖动强度85%（与Python完全一致）
            command.append(" -define dither:diffusion-amount=85%");
            
            // 步骤4: 颜色重映射到调色板（与Python完全一致）
            command.append(" -remap \"").append(paletteFile).append("\"");
            
            // 步骤5: 输出文件（与Python完全一致）
            command.append(" \"").append(outputFile).append("\"");
            
            log.info("执行标准ImageMagick命令（Python兼容）: {}", command.toString());
            
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
     * 首先尝试convert命令（与Python一致），如果失败则尝试magick命令
     */
    public boolean isImageMagickAvailable() {
        // 首先尝试convert命令（与Python实现一致）
        try {
            executeCommand("convert -version");
            log.info("ImageMagick可用性检查通过（convert命令）");
            return true;
        } catch (Exception e) {
            log.debug("convert命令不可用，尝试magick命令: {}", e.getMessage());
        }
        
        // 如果convert不可用，尝试magick命令
        try {
            executeCommand("magick -version");
            log.info("ImageMagick可用性检查通过（magick命令）");
            return true;
        } catch (Exception e) {
            log.warn("ImageMagick不可用（convert和magick命令都失败）: {}", e.getMessage());
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