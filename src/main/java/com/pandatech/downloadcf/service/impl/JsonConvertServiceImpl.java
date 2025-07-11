package com.pandatech.downloadcf.service.impl;

import com.pandatech.downloadcf.entity.CFJson;
import com.pandatech.downloadcf.entity.PlatformJson;
import com.pandatech.downloadcf.service.JsonConvertService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JsonConvertServiceImpl implements JsonConvertService {
    
    @Override
    public CFJson convertPlatformToCF(PlatformJson platformJson) {
        if (platformJson == null || CollectionUtils.isEmpty(platformJson.getPanels())) {
            return null;
        }
        
        PlatformJson.Panel panel = platformJson.getPanels().get(0);
        CFJson cfJson = new CFJson();
        
        // 设置基本信息
        cfJson.setVersion(10);
        cfJson.setTagType("06");
        cfJson.setName("ConvertedTemplate");
        cfJson.setWidth(panel.getWidth());
        cfJson.setHeight(panel.getHeight());
        cfJson.setWext(0);
        cfJson.setHext(6);
        cfJson.setRgb(3);
        cfJson.setSize(panel.getWidth() + ", " + panel.getHeight());
        
        // 转换打印元素
        List<CFJson.Item> items = new ArrayList<>();
        if (!CollectionUtils.isEmpty(panel.getPrintElements())) {
            // 按zIndex排序，zIndex越大越靠前
            List<PlatformJson.PrintElement> sortedElements = panel.getPrintElements().stream()
                    .sorted((e1, e2) -> {
                        Integer z1 = e1.getOptions().getZIndex();
                        Integer z2 = e2.getOptions().getZIndex();
                        if (z1 == null) z1 = 0;
                        if (z2 == null) z2 = 0;
                        return z2.compareTo(z1); // 降序排列，zIndex大的在前
                    })
                    .collect(Collectors.toList());
            
            for (PlatformJson.PrintElement element : sortedElements) {
                CFJson.Item item = convertPrintElementToItem(element);
                if (item != null) {
                    items.add(item);
                }
            }
        }
        
        cfJson.setItems(items);
        return cfJson;
    }
    
    @Override
    public PlatformJson convertCFToPlatform(CFJson cfJson) {
        if (cfJson == null) {
            return null;
        }
        
        PlatformJson platformJson = new PlatformJson();
        List<PlatformJson.Panel> panels = new ArrayList<>();
        
        PlatformJson.Panel panel = new PlatformJson.Panel();
        panel.setIndex(0);
        panel.setName(1);
        panel.setPaperType("A4");
        panel.setHeight(cfJson.getHeight());
        panel.setWidth(cfJson.getWidth());
        panel.setPaperHeader(0);
        panel.setPaperFooter(841.8897637795277);
        panel.setPaperNumberContinue(true);
        panel.setWatermarkOptions(new PlatformJson.WatermarkOptions());
        
        // 转换Items
        List<PlatformJson.PrintElement> printElements = new ArrayList<>();
        if (!CollectionUtils.isEmpty(cfJson.getItems())) {
            for (int i = 0; i < cfJson.getItems().size(); i++) {
                CFJson.Item item = cfJson.getItems().get(i);
                PlatformJson.PrintElement element = convertItemToPrintElement(item, cfJson.getItems().size() - i);
                if (element != null) {
                    printElements.add(element);
                }
            }
        }
        
        panel.setPrintElements(printElements);
        panels.add(panel);
        platformJson.setPanels(panels);
        
        return platformJson;
    }
    
    private CFJson.Item convertPrintElementToItem(PlatformJson.PrintElement element) {
        if (element == null || element.getOptions() == null) {
            return null;
        }
        
        CFJson.Item item = new CFJson.Item();
        PlatformJson.Options options = element.getOptions();
        
        // 坐标转换 - 更正字段映射
        item.setX(options.getLeft() != null ? options.getLeft().intValue() : 0);  // x=left
        item.setY(options.getTop() != null ? options.getTop().intValue() : 0);   // y=top
        item.setWidth(options.getWidth() != null ? options.getWidth().intValue() : 80);
        item.setHeight(options.getHeight() != null ? options.getHeight().intValue() : 40);
        item.setSize(item.getWidth() + ", " + item.getHeight());
        item.setLocation(item.getX() + ", " + item.getY());
        
        // 类型转换
        String elementType = element.getPrintElementType().getType();
        switch (elementType) {
            case "text":
                if ("barcode".equals(options.getTextType())) {
                    item.setType("barcode");
                    item.setBartype(options.getBarcodeMode() != null ? options.getBarcodeMode().toLowerCase() : "code128");
                    item.setBarheight(20);
                    item.setBarwidth(1);
                    item.setShowtext(1);
                    item.setFontinval(1);
                } else {
                    item.setType("text");
                }
                break;
            case "image":
                item.setType("qrcode");
                break;
            case "html":
                item.setType("text");
                break;
            default:
                item.setType("text");
        }
        
        // 数据和样式 - 更正字段映射
        item.setDataDefault(options.getTestData() != null ? options.getTestData() : "text");
        // background=backgroundColor
        item.setBackground(options.getBackgroundColor() != null ? options.getBackgroundColor() : "Transparent");
        item.setFontFamily(options.getFontFamily() != null ? options.getFontFamily() : "微软雅黑");
        item.setFontStyle(0);
        item.setFontSize(options.getFontSize() != null ? options.getFontSize().intValue() : 12);
        item.setFontColor(options.getColor() != null ? options.getColor() : "Black");
        item.setFontSpace(options.getLetterSpacing() != null ? options.getLetterSpacing() : 0);
        
        // 对齐方式转换
        if ("center".equals(options.getTextAlign())) {
            item.setTextAlign(1);
        } else if ("right".equals(options.getTextAlign())) {
            item.setTextAlign(2);
        } else {
            item.setTextAlign(0);
        }
        
        // BorderStyle=borderWidth
        item.setBorderStyle(options.getBorderWidth() != null ? 
            Integer.parseInt(options.getBorderWidth().replaceAll("[^0-9]", "")) : 0);
        item.setBorderColor(options.getBorderColor() != null ? options.getBorderColor() : "Transparent");
        // DataKey=field
        item.setDataKey(options.getField() != null ? options.getField() : "");
        item.setDataKeyStyle(0);
        
        return item;
    }
    
    private PlatformJson.PrintElement convertItemToPrintElement(CFJson.Item item, int zIndex) {
        if (item == null) {
            return null;
        }
        
        PlatformJson.PrintElement element = new PlatformJson.PrintElement();
        PlatformJson.Options options = new PlatformJson.Options();
        
        // 坐标转换 - 更正字段映射
        options.setLeft(item.getX().doubleValue());  // left=x
        options.setTop(item.getY().doubleValue());   // top=y
        options.setWidth(item.getWidth().doubleValue());
        options.setHeight(item.getHeight().doubleValue());
        options.setZIndex(zIndex); // CF中的顺序转换为Platform的zIndex
        
        // 基本属性
        options.setTitle(item.getDataDefault());
        options.setTestData(item.getDataDefault());
        // field=DataKey
        options.setField(item.getDataKey());
        options.setCoordinateSync(false);
        options.setWidthHeightSync(false);
        options.setHideTitle(true);
        
        // 字体样式
        options.setFontFamily(item.getFontFamily());
        options.setFontSize(item.getFontSize().doubleValue());
        options.setColor(item.getFontColor());
        // backgroundColor=background
        options.setBackgroundColor(item.getBackground());
        
        // 对齐方式转换
        if (item.getTextAlign() != null) {
            switch (item.getTextAlign()) {
                case 1:
                    options.setTextAlign("center");
                    break;
                case 2:
                    options.setTextAlign("right");
                    break;
                default:
                    options.setTextAlign("left");
            }
        }
        
        options.setTextContentVerticalAlign("middle");
        options.setQrCodeLevel(0);
        // borderWidth=BorderStyle
        options.setBorderWidth(item.getBorderStyle() != null ? item.getBorderStyle().toString() + "px" : "0px");
        options.setBorderColor(item.getBorderColor());
        
        // 类型转换
        PlatformJson.PrintElementType elementType = new PlatformJson.PrintElementType();
        switch (item.getType()) {
            case "barcode":
                elementType.setType("text");
                elementType.setTitle("文本");
                options.setTextType("barcode");
                options.setBarcodeMode(item.getBartype() != null ? item.getBartype().toUpperCase() : "CODE128");
                break;
            case "qrcode":
                elementType.setType("image");
                elementType.setTitle("图片");
                break;
            case "price":
            case "text":
            default:
                elementType.setType("text");
                elementType.setTitle("文本");
        }
        
        element.setOptions(options);
        element.setPrintElementType(elementType);
        
        return element;
    }
}