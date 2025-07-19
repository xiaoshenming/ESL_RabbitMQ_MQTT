
2_1C.json
{
  "TagType": "2.13T",
  "Version": 10,
  "Size": "250, 122",
  "width": 250,
  "hext": 6,
  "Items": [],
  "rgb": 3,
  "wext": 0,
  "Name": "2",
  "height": 122
}

这是你合成出来的模板？是不是哪里不对？
以下是官方模板

{
    "Items": [
        {
            "Background": "Red",
            "BorderColor": "Transparent",
            "BorderStyle": 0,
            "DataDefault": "",
            "DataKey": "",
            "DataKeyStyle": 0,
            "FontColor": "Black",
            "FontFamily": "微软雅黑",
            "FontSize": 12,
            "FontSpace": 0,
            "FontStyle": 0,
            "Location": "11, 13",
            "Size": "49, 57",
            "TextAlign": 0,
            "Type": "text",
            "height": 57,
            "width": 49,
            "x": 11,
            "y": 13
        },
        {
            "Background": "Black",
            "BorderColor": "Transparent",
            "BorderStyle": 0,
            "DataDefault": "",
            "DataKey": "",
            "DataKeyStyle": 0,
            "FontColor": "Black",
            "FontFamily": "微软雅黑",
            "FontSize": 12,
            "FontSpace": 0,
            "FontStyle": 0,
            "Location": "69, 14",
            "Size": "51, 55",
            "TextAlign": 0,
            "Type": "text",
            "height": 55,
            "width": 51,
            "x": 69,
            "y": 14
        },
        {
            "Background": "Transparent",
            "BorderColor": "Black",
            "BorderStyle": 2,
            "DataDefault": "",
            "DataKey": "",
            "DataKeyStyle": 0,
            "FontColor": "Black",
            "FontFamily": "微软雅黑",
            "FontSize": 12,
            "FontSpace": 0,
            "FontStyle": 0,
            "Location": "129, 14",
            "Size": "55, 54",
            "TextAlign": 0,
            "Type": "text",
            "height": 54,
            "width": 55,
            "x": 129,
            "y": 14
        },
        {
            "Background": "Transparent",
            "BorderColor": "Transparent",
            "BorderStyle": 0,
            "DataDefault": "text",
            "DataKey": "GOODS_CODE",
            "DataKeyStyle": 0,
            "FontColor": "Black",
            "FontFamily": "Zfull-GB",
            "FontSize": 14,
            "FontSpace": 0,
            "FontStyle": 0,
            "Location": "40, 80",
            "Size": "142, 32",
            "TextAlign": 1,
            "Type": "text",
            "height": 32,
            "width": 142,
            "x": 40,
            "y": 80
        },
        {
            "Background": "Transparent",
            "BorderColor": "Transparent",
            "BorderStyle": 0,
            "DataDefault": "text",
            "DataKey": "F_5",
            "DataKeyStyle": 0,
            "FontColor": "Black",
            "FontFamily": "Zfull-GB",
            "FontSize": 12,
            "FontSpace": 0,
            "FontStyle": 0,
            "Location": "27, 64",
            "Size": "80, 40",
            "TextAlign": 0,
            "Type": "text",
            "height": 40,
            "width": 80,
            "x": 27,
            "y": 64
        }
    ],
    "Name": "U",
    "Size": "250, 122",
    "TagType": "06",
    "Version": 10,
    "height": "122",
    "hext": "6",
    "rgb": "3",
    "wext": "0",
    "width": "250"
}

首先你合成出来不对的地方在于TagType应该为
    "TagType": "06",
    2.13T 对应 06
4.20T 对应 1C
4.20F 对应 1D
里面外面要对应上。
其次就是要其他各个数据结构类型基本上一致。

而且
{
  "panels": [
    {
      "index": 0,
      "name": "2",
      "paperType": "CUSTOM",
      "height": 122,
      "width": 250,
      "paperHeader": 0,
      "paperFooter": 345.82677165354335,
      "printElements": [
        {
          "options": {
            "left": 103.5,
            "top": 136.5,
            "height": 25.5,
            "width": 120,
            "title": "商品名称",
            "field": "PRODUCT_NAME",
            "testData": "示例商品名称",
            "templateField": "name",
            "fontWeight": "400",
            "textAlign": "center",
            "textContentVerticalAlign": "middle",
            "coordinateSync": false,
            "widthHeightSync": false,
            "fontFamily": "SimSun",
            "qrCodeLevel": 0
          },
          "printElementType": {
            "title": "商品名称",
            "type": "text"
          }
        }
      ],
      "paperNumberContinue": true,
      "watermarkOptions": {}
    }
  ]
}
这是数据库的拓展json字段取出的数据，你应该要解析出来比如商品名称的绑定字段啊。
转化的映射表也有。可以拿来就用，不在映射表的就不管他就行了啊
这是映射字段表
DROP TABLE IF EXISTS `esl_brand_field_mapping`;
CREATE TABLE `esl_brand_field_mapping`  (
  `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
  `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
  `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
  `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
  `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
  `BRAND_CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '品牌编码',
  `TEMPLATE_FIELD` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板字段名',
  `FIELD_CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统字段编码',
  `FORMAT_RULE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '格式化规则',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '品牌字段映射表' ROW_FORMAT = DYNAMIC;


use eslplatform;
SELECT * FROM esl_brand_field_mapping;

1946188424516816897	-1			NOT_DELETE	2025-07-18 20:40:54	1543837863788879871	2025-07-19 00:22:38	1543837863788879871	攀攀	code	PRODUCT_ID	商品名称
1946188616733380609	-1			NOT_DELETE	2025-07-18 20:41:39	1543837863788879871	2025-07-19 00:22:35	1543837863788879871	攀攀	name	PRODUCT_NAME	商品名称
1946230416441430018	-1			NOT_DELETE	2025-07-18 23:27:45	1543837863788879871	2025-07-19 00:22:31	1543837863788879871	攀攀	F_01	PRODUCT_RETAIL_PRICE	价格
1946244176275132418	-1			NOT_DELETE	2025-07-19 00:22:26	1543837863788879871			攀攀	F_02	PRODUCT_CATEGORY	商品分类
1946244319741300737	-1			NOT_DELETE	2025-07-19 00:23:00	1543837863788879871			攀攀	F_03	PRODUCT_COST_PRICE	商品成本价
1946244382848798721	-1			NOT_DELETE	2025-07-19 00:23:15	1543837863788879871			攀攀	F_04	PRODUCT_SPECIFICATION	商品规格
1946244450377093122	-1			NOT_DELETE	2025-07-19 00:23:31	1543837863788879871			攀攀	F_05	PRODUCT_MEMBERSHIP_PRICE	商品会员价
1946244527699087362	-1			NOT_DELETE	2025-07-19 00:23:50	1543837863788879871			攀攀	F_06	PRODUCT_DISCOUNT_PRICE	商品折扣价
1946244599702704129	-1			NOT_DELETE	2025-07-19 00:24:07	1543837863788879871			攀攀	F_07	PRODUCT_DISCOUNT	商品折扣（例如：0.8 表示8折）
1946244674935934978	-1			NOT_DELETE	2025-07-19 00:24:25	1543837863788879871			攀攀	F_08	PRODUCT_WHOLESALE_PRICE	商品批发
1946244741017194498	-1			NOT_DELETE	2025-07-19 00:24:40	1543837863788879871			攀攀	F_09	PRODUCT_MATERIAL	商品材质
1946244808008617986	-1			NOT_DELETE	2025-07-19 00:24:56	1543837863788879871			攀攀	F_10	PRODUCT_IMAGE	商品图片（路径或URL）
1946244885259309057	-1			NOT_DELETE	2025-07-19 00:25:15	1543837863788879871			攀攀	F_11	PRODUCT_ORIGIN	商品产地
1946244968667238402	-1			NOT_DELETE	2025-07-19 00:25:35	1543837863788879871			攀攀	F_20	PRODUCT_DESCRIPTION	商品描述
1946245050686853122	-1			NOT_DELETE	2025-07-19 00:25:54	1543837863788879871			攀攀	F_12	PRODUCT_UNIT	商品单位（如：个、件、瓶等）
1946245165296209922	-1			NOT_DELETE	2025-07-19 00:26:22	1543837863788879871			攀攀	F_13	PRODUCT_WEIGHT	产品重量（单位：kg）
1946245224305872898	-1			NOT_DELETE	2025-07-19 00:26:36	1543837863788879871			攀攀	F_14	PRODUCT_STATUS	商品状态（如：上架、下架、预售等）
1946245284653518849	-1			NOT_DELETE	2025-07-19 00:26:50	1543837863788879871			攀攀	F_32	PRODUCT_STOCK	商品库存


字段的绑定通过这个来实现，模板转化。