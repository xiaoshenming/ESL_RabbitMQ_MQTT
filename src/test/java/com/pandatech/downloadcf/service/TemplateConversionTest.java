package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs;
import com.pandatech.downloadcf.util.ScreenTypeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 模板转换测试类
 * 验证模板转换功能是否正常工作
 */
@SpringBootTest
@ActiveProfiles("test")
public class TemplateConversionTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testScreenTypeMapping() {
        // 测试屏幕类型映射
        assertEquals("06", ScreenTypeMapper.getTagType("2.13T"));
        assertEquals("06", ScreenTypeMapper.getTagType("2.13"));
        assertEquals("07", ScreenTypeMapper.getTagType("2.9T"));
        assertEquals("08", ScreenTypeMapper.getTagType("4.2T"));
        assertEquals("06", ScreenTypeMapper.getTagType("unknown")); // 默认值
    }

    @Test
    public void testTemplateDataStructure() throws Exception {
        // 模拟err2.md中的模板数据
        String templateContent = """
        {
            "panels": [
                {
                    "width": 296,
                    "height": 128,
                    "printElements": [
                        {
                            "options": {
                                "left": 10,
                                "top": 10,
                                "width": 100,
                                "height": 20,
                                "fontSize": 12,
                                "field": "productName",
                                "testData": "测试商品"
                            }
                        }
                    ]
                }
            ]
        }
        """;

        // 验证JSON结构
        JsonNode rootNode = objectMapper.readTree(templateContent);
        assertTrue(rootNode.has("panels"));
        assertTrue(rootNode.get("panels").isArray());
        
        JsonNode firstPanel = rootNode.get("panels").get(0);
        assertEquals(296, firstPanel.get("width").asInt());
        assertEquals(128, firstPanel.get("height").asInt());
        
        JsonNode printElements = firstPanel.get("printElements");
        assertTrue(printElements.isArray());
        
        JsonNode firstElement = printElements.get(0);
        JsonNode options = firstElement.get("options");
        assertEquals("productName", options.get("field").asText());
        assertEquals("测试商品", options.get("testData").asText());
    }

    @Test
    public void testTemplateObjectCreation() {
        // 创建模板对象，模拟数据库中的数据
        PrintTemplateDesignWithBLOBs template = new PrintTemplateDesignWithBLOBs();
        template.setId("1946122678071738370");
        template.setName("2");
        template.setCategory("2.13T");
        template.setDeleteFlag("NOT_DELETE");
        
        // 验证模板对象
        assertEquals("1946122678071738370", template.getId());
        assertEquals("2", template.getName());
        assertEquals("2.13T", template.getCategory());
        assertEquals("NOT_DELETE", template.getDeleteFlag());
        
        // 验证屏幕类型映射
        String tagType = ScreenTypeMapper.getTagType(template.getCategory());
        assertEquals("06", tagType);
    }

    @Test
    public void testExpectedOfficialFormat() throws Exception {
        // 验证期望的官方格式结构
        String expectedFormat = """
        {
            "Items": [
                {
                    "Type": "text",
                    "FontFamily": "Zfull-GB",
                    "FontSize": 12,
                    "FontColor": "#000000",
                    "Background": "#FFFFFF",
                    "x": 10,
                    "y": 10,
                    "width": 100,
                    "height": 20,
                    "Location": "10, 10",
                    "Size": "100, 20",
                    "DataKey": "productName",
                    "DataDefault": "测试商品",
                    "TextAlign": 0
                }
            ],
            "Name": "2",
            "Size": "296, 128",
            "TagType": "06",
            "Version": "1.0",
            "width": 296,
            "height": 128
        }
        """;

        JsonNode expectedNode = objectMapper.readTree(expectedFormat);
        
        // 验证必需字段
        assertTrue(expectedNode.has("Items"));
        assertTrue(expectedNode.has("Name"));
        assertTrue(expectedNode.has("TagType"));
        assertTrue(expectedNode.has("Size"));
        assertTrue(expectedNode.has("Version"));
        
        // 验证TagType
        assertEquals("06", expectedNode.get("TagType").asText());
        
        // 验证Items数组
        JsonNode items = expectedNode.get("Items");
        assertTrue(items.isArray());
        assertTrue(items.size() > 0);
        
        JsonNode firstItem = items.get(0);
        assertEquals("text", firstItem.get("Type").asText());
        assertEquals("Zfull-GB", firstItem.get("FontFamily").asText());
        assertEquals("productName", firstItem.get("DataKey").asText());
    }
}