package com.pandatech.downloadcf.entity;

import lombok.Data;
import java.util.List;

@Data
public class PlatformJson {
    private List<Panel> panels;
    
    @Data
    public static class Panel {
        private Integer index;
        private Object name;
        private String paperType;
        private Integer height;
        private Integer width;
        private Integer paperHeader;
        private Double paperFooter;
        private List<PrintElement> printElements;
        private Boolean paperNumberContinue;
        private WatermarkOptions watermarkOptions;
    }
    
    @Data
    public static class PrintElement {
        private Options options;
        private PrintElementType printElementType;
    }
    
    @Data
    public static class Options {
        private Double left;
        private Double top;
        private Double height;
        private Double width;
        private Integer transform;
        private String title;
        private String field;
        private String testData;
        private Double right;
        private Double bottom;
        private Double vCenter;
        private Double hCenter;
        private Boolean coordinateSync;
        private Boolean widthHeightSync;
        private String fontFamily;
        private Double fontSize;
        private String fontWeight;
        private String color;
        private String backgroundColor;
        private String textContentVerticalAlign;
        private String textContentWrap;
        private Integer lineHeight;
        private Integer zIndex;
        private String borderLeft;
        private String borderTop;
        private String borderRight;
        private String borderBottom;
        private String borderWidth;
        private String borderColor;
        private Double contentPaddingLeft;
        private Double contentPaddingTop;
        private Double contentPaddingRight;
        private Double contentPaddingBottom;
        private Integer qrCodeLevel;
        private String textAlign;
        private Boolean hideTitle;
        private Boolean fixed;
        private Integer letterSpacing;
        private String textDecoration;
        private String textType;
        private String barcodeMode;
        private String src;
        private String formatter;
    }
    
    @Data
    public static class PrintElementType {
        private String title;
        private String type;
    }
    
    @Data
    public static class WatermarkOptions {
        // 根据需要添加水印相关字段
    }
}