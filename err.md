curl 'http://localhost:8999/api/res/templ/refresh' \
  -H 'Accept-Language: zh-CN,zh;q=0.9' \
  -H 'Connection: keep-alive' \
  -H 'Content-Type: application/json' \
  -b 'JSESSIONID=AC9AA0889488569DA6ACCC0FA9EDFCB0' \
  -H 'Origin: http://localhost:8999' \
  -H 'Referer: http://localhost:8999/swagger-ui/index.html' \
  -H 'Sec-Fetch-Dest: empty' \
  -H 'Sec-Fetch-Mode: cors' \
  -H 'Sec-Fetch-Site: same-origin' \
  -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36 Edg/139.0.0.0' \
  -H 'accept: text/plain' \
  -H 'sec-ch-ua: "Not;A=Brand";v="99", "Microsoft Edge";v="139", "Chromium";v="139"' \
  -H 'sec-ch-ua-mobile: ?0' \
  -H 'sec-ch-ua-platform: "Windows"' \
  --data-raw $'{\n  "eslId": "1947223724923940865"\n}'
  这个api通过查找
  DROP TABLE IF EXISTS `PANDA_ESL`;
CREATE TABLE `PANDA_ESL`  (
  `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
  `SORT_CODE` int(11) NULL DEFAULT NULL COMMENT '排序码',
  `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
  `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
  `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
  `ESL_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子价签编号',
  `STORE_CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '门店编码',
  `AP_SN` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '门店编码',
  `ESL_MODEL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子价签型号',
  `BOUND_PRODUCT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '绑定商品',
  `BATTERY_LEVEL` float NULL DEFAULT NULL COMMENT '电子价签电量（百分比）',
  `TEMPERATURE` float NULL DEFAULT NULL COMMENT '电子价签温度（摄氏度）',
  `SIGNAL_STRENGTH` int(11) NULL DEFAULT NULL COMMENT '信号值',
  `COMMUNICATION_COUNT` int(11) NULL DEFAULT NULL COMMENT '通讯次数',
  `FAILURE_COUNT` int(11) NULL DEFAULT NULL COMMENT '失败次数',
  `ESL_CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ESL分类',
  `ESL_STATUS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子价签状态',
  `SCREEN_COLOR` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子价签屏幕颜色',
  `COMMUNICATION_METHOD` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子价签通讯方式（蓝牙/NFC/Wifi/ZigBee）',
  `VERSION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版本',
  `HARDWARE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '硬件',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

这张表的ID来查找到
ESL_ID-电子价签编号（06000000195A）
BOUND_PRODUCT-商品id（1947222838805917697）
STORE_CODE-门店编码（0002）
ESL_MODEL-电子价签型号（2.13T）
然后用BOUND_PRODUCT-商品id（1947222838805917697）进行联表查询
联动这张表
panda_product
DROP TABLE IF EXISTS `PANDA_PRODUCT`;
CREATE TABLE `PANDA_PRODUCT`  (
  `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
  `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
  `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
  `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
  `PRODUCT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品编号',
  `STORE_CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '门店编码',
  `PRODUCT_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `PRODUCT_CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品分类',
  `PRODUCT_SPECIFICATION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品规格',
  `PRODUCT_BRAND` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品品牌',
  `PRODUCT_COST_PRICE` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品成本价',
  `PRODUCT_RETAIL_PRICE` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品零售价',
  `PRODUCT_MEMBERSHIP_PRICE` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品会员价',
  `PRODUCT_DISCOUNT_PRICE` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品折扣价',
  `PRODUCT_DISCOUNT` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品折扣',
  `PRODUCT_WHOLESALE_PRICE` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品批发价',
  `PRODUCT_MATERIAL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品材质',
  `PRODUCT_IMAGE` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品图片',
  `PRODUCT_ORIGIN` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品产地',
  `PRODUCT_DESCRIPTION` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商品描述',
  `PRODUCT_QRCODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '二维码',
  `PRODUCT_BARCODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '条形码',
  `PRODUCT_UNIT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品单位',
  `PRODUCT_WEIGHT` decimal(10, 2) NULL DEFAULT NULL COMMENT '产品重量（kg）',
  `PRODUCT_STATUS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品状态',
  `PRODUCT_STOCK` int(10) UNSIGNED NULL DEFAULT 0 COMMENT '商品库存',
  `ESL_TEMPLATE_CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子价签模版',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

拿到详细的商品数据。然后通过这张表
DROP TABLE IF EXISTS `ESL_BRAND_FIELD_MAPPING`;
CREATE TABLE `ESL_BRAND_FIELD_MAPPING`  (
  `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
  `SORT_CODE` int(11) NULL DEFAULT NULL COMMENT '排序码',
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
里面的
FIELD_CODE-系统字段编码来转化成TEMPLATE_FIELD
这是目前的数据
code	PRODUCT_ID
name	PRODUCT_NAME
F_01	PRODUCT_RETAIL_PRICE
F_02	PRODUCT_CATEGORY
F_03	PRODUCT_COST_PRICE
F_04	PRODUCT_SPECIFICATION
F_05	PRODUCT_MEMBERSHIP_PRICE
F_06	PRODUCT_DISCOUNT_PRICE
F_07	PRODUCT_DISCOUNT
F_08	PRODUCT_WHOLESALE_PRICE
F_09	PRODUCT_MATERIAL
F_10	PRODUCT_IMAGE
F_11	PRODUCT_ORIGIN
F_20	PRODUCT_DESCRIPTION
F_12	PRODUCT_UNIT
F_13	PRODUCT_WEIGHT
F_14	PRODUCT_STATUS
F_32	PRODUCT_STOCK
QRCODE	PRODUCT_QRCODE
QRCODE	PRODUCT_BARCODE
最后把数据整合之后

转化为这种格式
{
	"command": "wtag",
	"data": [{
		"tag": 6597069770841,
		"tmpl": "PRICEPROMO",
		"model": 6,
		"checksum": "b3359abd8cd0c923afe88f539e750871",
		"forcefrash": 1,
		"value": {
			"GOODS_NAME": "商品名称",（名称）
			"GOODS_CODE": "6902538004045",（编码）
			"F_1": "10.80",
			"F_2": "",
			"F_3": null,
			"F_4": null,
			"F_5": null,
			"F_6": null,
			"F_7": null,
			"F_8": null,
			"F_9": null,
			"QRCODE": "esl.wdyc.cn",
			"F_11": null,
			"F_20": null
		},
		"taskid": 39138,
		"token": 161986
	}],
	"id": "3db4b81b-da87-4aa1-b8bb-ab2adf785558",
	"timestamp": 1706512513.5774696,
	"shop": "ZH01"（获取到的门店编码）
}
生产者发送到到rabbitmq队列然后被消费者消费到mqtt里面去。
其中要注意的是这个mqtt消息里面的所有字段格式都必须严格一致按照数据库的对应映射
除了！！！！			
"GOODS_NAME": "商品名称",（名称）
"GOODS_CODE": "6902538004045",（编码）
这两个属于AP的固定格式，必须是GOODS_NAME跟GOODS_CODE，所以这两个不需要按照数据库的格式转化，而是强制转化。