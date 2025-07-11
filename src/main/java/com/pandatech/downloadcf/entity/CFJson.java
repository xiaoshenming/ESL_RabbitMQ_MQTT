package com.pandatech.downloadcf.entity;

import lombok.Data;
import java.util.List;

@Data
public class CFJson {
    private Integer Version;
    private String TagType;
    private String Name;
    private Integer width;
    private Integer height;
    private Integer wext;
    private Integer hext;
    private Integer rgb;
    private String Size;
    private List<Item> Items;
    
    @Data
    public static class Item {
        private Integer x;
        private Integer y;
        private Integer width;
        private Integer height;
        private String Size;
        private String Location;
        private String Type;
        private String DataDefault;
        private String Background;
        private String FontFamily;
        private Integer FontStyle;
        private Integer FontSize;
        private String FontColor;
        private Integer FontSpace;
        private Integer TextAlign;
        private Integer BorderStyle;
        private String BorderColor;
        private String DataKey;
        private Integer DataKeyStyle;
        private String Spacer;
        private Integer DecimalsStyle;
        private String Bartype;
        private Integer Barheight;
        private Integer Barwidth;
        private Integer Showtext;
        private Integer Fontinval;
    }
}