DROP TABLE IF EXISTS `esl_brand`;
CREATE TABLE `esl_brand`  (
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
  `BRAND_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '品牌名称',
  `PROTOCOL_TYPE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通信协议',
  `API_ENDPOINT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对接API地址',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'AP品牌信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of esl_brand
-- ----------------------------
INSERT INTO `esl_brand` VALUES ('1946223630744662018', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-18 23:00:47', '1543837863788879871', NULL, NULL, 'AES001', '攀攀科技', 'MQTT', NULL);
INSERT INTO `esl_brand` VALUES ('1958454715071246338', '-1', NULL, NULL, 'NOT_DELETE', '2025-08-21 17:02:45', '1543837863788879871', NULL, NULL, 'YALIANG001', '雅量科技', 'MQTT', NULL);

-- ----------------------------
-- Table structure for esl_brand_field_mapping
-- ----------------------------
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

-- ----------------------------
-- Records of esl_brand_field_mapping
-- ----------------------------
INSERT INTO `esl_brand_field_mapping` VALUES ('1946188424516816897', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-18 20:40:54', '1543837863788879871', '2025-08-21 17:32:43', '1543837863788879871', 'AES001', 'F_01', 'PRODUCT_ID', '商品编号');
INSERT INTO `esl_brand_field_mapping` VALUES ('1946188616733380609', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-18 20:41:39', '1543837863788879871', '2025-08-21 17:34:00', '1543837863788879871', 'AES001', 'F_02', 'PRODUCT_NAME', '商品名称');
INSERT INTO `esl_brand_field_mapping` VALUES ('1946230416441430018', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-18 23:27:45', '1543837863788879871', '2025-08-21 17:33:54', '1543837863788879871', 'AES001', 'F_03', 'PRODUCT_RETAIL_PRICE', '价格');
INSERT INTO `esl_brand_field_mapping` VALUES ('1946244176275132418', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-19 00:22:26', '1543837863788879871', '2025-08-21 17:33:50', '1543837863788879871', 'AES001', 'F_04', 'PRODUCT_CATEGORY', '商品分类');
INSERT INTO `esl_brand_field_mapping` VALUES ('1946244319741300737', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-19 00:23:00', '1543837863788879871', '2025-08-21 17:33:45', '1543837863788879871', 'AES001', 'F_05', 'PRODUCT_COST_PRICE', '商品成本价');
INSERT INTO `esl_brand_field_mapping` VALUES ('1946244382848798721', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-19 00:23:15', '1543837863788879871', '2025-08-21 17:33:08', '1543837863788879871', 'AES001', 'F_06', 'PRODUCT_SPECIFICATION', '商品规格');
INSERT INTO `esl_brand_field_mapping` VALUES ('1946244450377093122', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-19 00:23:31', '1543837863788879871', '2025-08-21 17:33:04', '1543837863788879871', 'AES001', 'F_07', 'PRODUCT_MEMBERSHIP_PRICE', '商品会员价');
INSERT INTO `esl_brand_field_mapping` VALUES ('1946244527699087362', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-19 00:23:50', '1543837863788879871', '2025-08-21 17:32:59', '1543837863788879871', 'AES001', 'F_08', 'PRODUCT_DISCOUNT_PRICE', '商品折扣价');
INSERT INTO `esl_brand_field_mapping` VALUES ('1946244599702704129', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-19 00:24:07', '1543837863788879871', '2025-08-21 17:32:54', '1543837863788879871', 'AES001', 'F_09', 'PRODUCT_DISCOUNT', '商品折扣（例如：0.8 表示8折）');
INSERT INTO `esl_brand_field_mapping` VALUES ('1946244674935934978', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-19 00:24:25', '1543837863788879871', '2025-08-21 17:32:49', '1543837863788879871', 'AES001', 'F_10', 'PRODUCT_WHOLESALE_PRICE', '商品批发');
INSERT INTO `esl_brand_field_mapping` VALUES ('1946244741017194498', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-19 00:24:40', '1543837863788879871', '2025-08-21 17:31:24', '1543837863788879871', 'AES001', 'F_11', 'PRODUCT_MATERIAL', '商品材质');
INSERT INTO `esl_brand_field_mapping` VALUES ('1946244808008617986', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-19 00:24:56', '1543837863788879871', '2025-08-21 17:32:37', '1543837863788879871', 'AES001', 'F_10', 'PRODUCT_IMAGE', '商品图片（路径或URL）');
INSERT INTO `esl_brand_field_mapping` VALUES ('1946244885259309057', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-19 00:25:15', '1543837863788879871', '2025-08-21 17:32:27', '1543837863788879871', 'AES001', 'F_11', 'PRODUCT_ORIGIN', '商品产地');
INSERT INTO `esl_brand_field_mapping` VALUES ('1946244968667238402', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-19 00:25:35', '1543837863788879871', '2025-08-21 17:32:08', '1543837863788879871', 'AES001', 'F_12', 'PRODUCT_DESCRIPTION', '商品描述');
INSERT INTO `esl_brand_field_mapping` VALUES ('1946245050686853122', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-19 00:25:54', '1543837863788879871', '2025-08-21 17:32:03', '1543837863788879871', 'AES001', 'F_13', 'PRODUCT_UNIT', '商品单位（如：个、件、瓶等）');
INSERT INTO `esl_brand_field_mapping` VALUES ('1946245165296209922', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-19 00:26:22', '1543837863788879871', '2025-08-21 17:31:58', '1543837863788879871', 'AES001', 'F_14', 'PRODUCT_WEIGHT', '产品重量（单位：kg）');
INSERT INTO `esl_brand_field_mapping` VALUES ('1946245224305872898', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-19 00:26:36', '1543837863788879871', '2025-08-21 17:31:46', '1543837863788879871', 'AES001', 'F_15', 'PRODUCT_STATUS', '商品状态（如：上架、下架、预售等）');
INSERT INTO `esl_brand_field_mapping` VALUES ('1946245284653518849', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-19 00:26:50', '1543837863788879871', '2025-08-21 17:31:42', '1543837863788879871', 'AES001', 'F_16', 'PRODUCT_STOCK', '商品库存');
INSERT INTO `esl_brand_field_mapping` VALUES ('1947173347558027266', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-21 13:54:37', '1543837863788879871', '2025-08-21 17:31:38', '1543837863788879871', 'AES001', 'F_17', 'PRODUCT_QRCODE', '二维码');
INSERT INTO `esl_brand_field_mapping` VALUES ('1947173427983806466', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-21 13:54:57', '1543837863788879871', '2025-08-21 17:31:29', '1543837863788879871', 'AES001', 'F_18', 'PRODUCT_BARCODE', '条形码');


DROP TABLE IF EXISTS `esl_model`;
CREATE TABLE `esl_model`  (
  `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
  `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
  `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
  `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
  `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
  `MODEL_CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '型号编码',
  `MODEL_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '型号名称',
  `BRAND_CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '品牌编码',
  `ESL_CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类',
  `SCREEN_COLOR` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '屏幕颜色',
  `COMMUNICATION_METHOD` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通讯方式',
  `OPERATING_TEMP_RANGE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作温度范围',
  `IP_RATING` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP防护等级',
  `SCREEN_SIZE_WIDTH` float NULL DEFAULT NULL COMMENT '屏幕尺寸',
  `SCREEN_SIZE_HIGH` float NULL DEFAULT NULL COMMENT '屏幕尺寸',
  `RESOLUTION` float NULL DEFAULT NULL COMMENT '分辨率',
  `BATTERY_LIFE` int UNSIGNED NULL DEFAULT 0 COMMENT '典型电池寿命(天)',
  `HARDWARE_VERSION` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '硬件版本',
  `SOFTWARE_VERSION` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '软件版本',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '电子价签型号表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of esl_model
-- ----------------------------
INSERT INTO `esl_model` VALUES ('1946228072533467137', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-18 23:18:26', '1543837863788879871', '2025-08-21 17:08:09', '1543837863788879871', 'AESTAG001', 'AES电子价签2.13T', '1946223630744662018', NULL, '红黑白', NULL, NULL, NULL, 250, 122, NULL, 0, NULL, NULL);
INSERT INTO `esl_model` VALUES ('1958456055847628802', '-1', NULL, NULL, 'NOT_DELETE', '2025-08-21 17:08:05', '1543837863788879871', NULL, NULL, 'AESTAG002', 'AES电子价签4.3T', '1946223630744662018', NULL, '红黑白', NULL, NULL, NULL, 400, 300, NULL, 0, NULL, NULL);
INSERT INTO `esl_model` VALUES ('1958456215382175745', '-1', NULL, NULL, 'NOT_DELETE', '2025-08-21 17:08:43', '1543837863788879871', '2025-08-21 18:33:29', '1543837863788879871', 'AESTAG002', 'AES电子价签4.3F', '1946223630744662018', NULL, '黄红黑白', NULL, NULL, NULL, 400, 300, NULL, 0, NULL, NULL);



DROP TABLE IF EXISTS `mqtt_connection_config`;
CREATE TABLE `mqtt_connection_config`  (
  `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
  `SERVER_HOST` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'MQTT服务器主机地址（如emqx-host）',
  `SERVER_PORT` int NOT NULL COMMENT 'MQTT服务器端口号（如1883）',
  `USERNAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '连接用户名',
  `PASSWORD` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '连接密码',
  `CLIENT_ID_PREFIX` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'price-tag-system-' COMMENT '客户端ID前缀',
  `CLEAN_SESSION` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否清除会话（0=false, 1=true）',
  `KEEP_ALIVE_INTERVAL` int NOT NULL DEFAULT 60 COMMENT '心跳间隔（秒）',
  `CONNECTION_TIMEOUT` int NOT NULL DEFAULT 30 COMMENT '连接超时时间（秒）',
  `AUTOMATIC_RECONNECT` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'A' COMMENT '是否自动重连（A=false, I=true）',
  `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
  `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
  `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
  `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'MQTT连接配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of mqtt_connection_config
-- ----------------------------
INSERT INTO `mqtt_connection_config` VALUES ('1946220532798173185', '-1', '192.168.10.30', 1883, 'admin', 'admin123', 'datacenter', 1, 60, 30, 'A', 6, NULL, 'NOT_DELETE', '2025-07-18 22:48:29', '1543837863788879871', NULL, NULL);

-- ----------------------------
-- Table structure for mqtt_store_binding
-- ----------------------------
DROP TABLE IF EXISTS `mqtt_store_binding`;
CREATE TABLE `mqtt_store_binding`  (
  `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
  `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
  `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
  `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
  `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
  `CONFIG_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'MQTT配置ID',
  `STORE_CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '门店编码',
  `BIND_STATUS` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'A' COMMENT '绑定状态(A=有效,I=无效)',
  `BIND_TIME` datetime NULL DEFAULT NULL COMMENT '绑定时间',
  `UNBIND_TIME` datetime NULL DEFAULT NULL COMMENT '解绑时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'MQTT配置-店铺关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of mqtt_store_binding
-- ----------------------------
INSERT INTO `mqtt_store_binding` VALUES ('1946223270722383873', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-18 22:59:21', '1543837863788879871', NULL, NULL, 'datacenter', 'BY001', 'A', '2025-07-18 22:59:12', NULL);
INSERT INTO `mqtt_store_binding` VALUES ('1948705655170961409', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-25 19:23:28', '1543837863788879871', NULL, NULL, '192.168.10.30', 'BY001', 'A', '2025-07-25 19:23:18', NULL);

DROP TABLE IF EXISTS `panda_access_point`;
CREATE TABLE `panda_access_point`  (
  `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
  `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
  `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
  `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
  `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
  `AP_SN` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'AP-sn编号',
  `STORE_CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '门店编码',
  `MQTT_SERVER_HOST` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'MQTT服务器主机地址（如emqx-host）',
  `AP_CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'AP分类',
  `AP_STATUS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'AP状态',
  `CPU_USAGE` float NULL DEFAULT NULL COMMENT 'CPU使用率（百分比）',
  `MEMORY_USAGE` float NULL DEFAULT NULL COMMENT '内存使用率（百分比）',
  `DISK_USAGE` float NULL DEFAULT NULL COMMENT '磁盘使用率（百分比）',
  `LAST_HEARTBEAT` datetime NULL DEFAULT NULL COMMENT '最后心跳时间',
  `IP_ADDRESS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `MAC_ADDRESS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'MAC地址',
  `VERSION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版本号',
  `MODULE_COUNT` int NULL DEFAULT NULL COMMENT '模块数',
  `MODULE_VERSION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块版本',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of panda_access_point
-- ----------------------------
INSERT INTO `panda_access_point` VALUES ('1946223842951278594', '-1', NULL, NULL, 'DELETED', '2025-07-18 23:01:38', '1543837863788879871', '2025-07-18 23:12:58', '1543837863788879871', 'ESLAP00000053', 'BY001', 'datacenter', NULL, '0', 12.8535, 54.6853, 42, '2025-08-18 19:09:10', '10.3.36.51', '66:4d:c7:9f:bc:8f', '0.3.6.0', NULL, NULL);
INSERT INTO `panda_access_point` VALUES ('1946226878469746689', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-18 23:13:42', '1543837863788879871', '2025-08-20 22:31:17', 'MQTT_SYSTEM', 'ESLAP00000053', 'BY001', 'datacenter', '1946223630744662018', '0', 12.8535, 54.6853, 42, '2025-08-20 22:31:17', '10.3.36.51', '66:4d:c7:9f:bc:8f', '0.3.6.0', 2, NULL);

-- ----------------------------
-- Table structure for panda_esl
-- ----------------------------
DROP TABLE IF EXISTS `panda_esl`;
CREATE TABLE `panda_esl`  (
  `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
  `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
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
  `SIGNAL_STRENGTH` int NULL DEFAULT NULL COMMENT '信号值',
  `COMMUNICATION_COUNT` int NULL DEFAULT NULL COMMENT '通讯次数',
  `FAILURE_COUNT` int NULL DEFAULT NULL COMMENT '失败次数',
  `ESL_CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ESL分类',
  `ESL_STATUS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子价签状态',
  `SCREEN_COLOR` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子价签屏幕颜色',
  `COMMUNICATION_METHOD` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子价签通讯方式（蓝牙/NFC/Wifi/ZigBee）',
  `VERSION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版本',
  `HARDWARE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '硬件',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of panda_esl
-- ----------------------------
INSERT INTO `panda_esl` VALUES ('1945833774331781121', '-1', NULL, NULL, 'DELETED', '2025-07-17 21:11:38', '1543837863788879871', '2025-07-17 21:11:47', '1543837863788879871', '1111', '1111', NULL, '1111', '11111', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `panda_esl` VALUES ('1946228658192523265', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-18 23:20:46', '1543837863788879871', '2025-08-20 20:11:09', '1543837863788879871', '60000001745', 'BY001', NULL, 'AESTAG001', '1947312777082040322', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `panda_esl` VALUES ('1947223724923940865', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-21 17:14:48', '1543837863788879871', '2025-08-21 17:56:17', '1543837863788879871', '06000000195A', 'BY001', 'ESLAP00000053', '1946228072533467137', '1947222838805917697', 295, 2500, -23, 3, NULL, NULL, 'Refresh Success', NULL, NULL, NULL, NULL);
INSERT INTO `panda_esl` VALUES ('1947313501023105026', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-21 23:11:33', '1543837863788879871', '2025-08-20 20:21:06', '1543837863788879871', '1C000000100B', 'BY001', NULL, '4.20T', '1947312777082040322', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `panda_esl` VALUES ('1948706172425113602', '-1', NULL, NULL, 'NOT_DELETE', '2025-07-25 19:25:31', '1543837863788879871', NULL, NULL, '11222333', 'BY001', NULL, '1946228072533467137', '1947222838805917697', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `panda_esl` VALUES ('1958129170894745601', '-1', NULL, NULL, 'DELETED', '2025-08-20 19:29:09', '1543837863788879871', '2025-08-20 19:34:03', '1543837863788879871', '06000000195A', 'BY001', NULL, '1946228072533467137', '1947312777082040322', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);



-- ----------------------------
-- Table structure for panda_product
-- ----------------------------
DROP TABLE IF EXISTS `panda_product`;
CREATE TABLE `panda_product`  (
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
  `PRODUCT_STOCK` int UNSIGNED NULL DEFAULT 0 COMMENT '商品库存',
  `ESL_TEMPLATE_CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子价签模版',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of panda_product
-- ----------------------------
INSERT INTO `panda_product` VALUES ('1947222838805917697', '-1', NULL, 'NOT_DELETE', '2025-07-21 17:11:17', '1543837863788879871', '2025-08-21 18:08:36', '1543837863788879871', '001', 'BY001', '测试商品', 'PRODUCT_FRUIT', 'PRODUCT_FRUIT', 'AES001', 9.99, 99.99, 99.90, 99.00, 0.09, 99.00, '测试材质', 'http://10.3.36.25:82/dev/file/download?id=1958168487407054849&Domain=http://192.168.10.50:81', '测试产地', '<p>测试描述</p>', 'www.bing.com', 'www.bing.com', 'ge', 9.00, '测试状态', 99, '1946122678071738370');
INSERT INTO `panda_product` VALUES ('1947312777082040322', '-1', NULL, 'NOT_DELETE', '2025-07-21 23:08:40', '1543837863788879871', '2025-08-21 18:01:46', '1543837863788879871', '002', '0002', '002', '002', 'PRODUCT_FRUIT', 'AES001', 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, '002', 'http://localhost:82/dev/file/download?id=1947312644269404161&Domain=http://localhost:81', '002', '<p>002</p>', '002', '002', 'ge', 2.00, '002', 2, '1947312285182455810');


-- ----------------------------
-- Table structure for print_template_design
-- ----------------------------
DROP TABLE IF EXISTS `print_template_design`;
CREATE TABLE `print_template_design`  (
  `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
  `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
  `CONTENT` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类',
  `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
  `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
  `DELETE_FLAG` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标识',
  `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '打印模板' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of print_template_design
-- ----------------------------
INSERT INTO `print_template_design` VALUES ('1946122678071738370', '-1', 'test1', NULL, '{\"panels\":[{\"index\":0,\"name\":\"test1\",\"paperType\":\"CUSTOM\",\"width\":250,\"height\":122,\"paperHeader\":0,\"paperFooter\":345.82677165354335,\"printElements\":[],\"paperNumberContinue\":true,\"eslConfig\":{\"screenType\":\"2.13T\",\"pixelWidth\":250,\"pixelHeight\":122,\"colorMode\":{\"black\":true,\"white\":true,\"red\":true,\"yellow\":false},\"orientation\":\"LANDSCAPE\"}}]}', '2.13T', 99, '{\"designConfig\":{\"panels\":[{\"index\":0,\"name\":\"2\",\"paperType\":\"CUSTOM\",\"height\":122,\"width\":250,\"paperHeader\":0,\"paperFooter\":345.82677165354335,\"printElements\":[{\"options\":{\"left\":19.5,\"top\":40.5,\"height\":25.5,\"width\":120,\"title\":\"商品材质\",\"field\":\"PRODUCT_MATERIAL\",\"testData\":\"塑料\",\"templateField\":\"F_11\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false},\"printElementType\":{\"title\":\"商品材质\",\"type\":\"text\"}},{\"options\":{\"left\":183,\"top\":52.5,\"height\":30,\"width\":120,\"title\":\"商品条形码\",\"field\":\"PRODUCT_BARCODE\",\"testData\":\"6901234567890\",\"templateField\":\"F_18\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false,\"textType\":\"barcode\"},\"printElementType\":{\"title\":\"商品条形码\",\"type\":\"text\"}},{\"options\":{\"left\":405,\"top\":64.5,\"height\":60,\"width\":60,\"title\":\"商品二维码\",\"field\":\"PRODUCT_QRCODE\",\"testData\":\"https://example.com/product/P001\",\"templateField\":\"F_17\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false,\"textType\":\"qrcode\",\"right\":465.0000457763672,\"bottom\":122.25,\"vCenter\":435.0000457763672,\"hCenter\":92.25},\"printElementType\":{\"title\":\"商品二维码\",\"type\":\"text\"}},{\"options\":{\"left\":151.5,\"top\":132,\"height\":25.5,\"width\":120,\"title\":\"条形码\",\"field\":\"PRODUCT_BARCODE\",\"testData\":\"默认值\",\"templateField\":\"F_18\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false},\"printElementType\":{\"title\":\"条形码\",\"type\":\"text\"}},{\"options\":{\"left\":39,\"top\":177,\"height\":25.5,\"width\":120,\"title\":\"商品状态\",\"field\":\"PRODUCT_STATUS\",\"testData\":\"正常\",\"templateField\":\"F_15\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false},\"printElementType\":{\"title\":\"商品状态\",\"type\":\"text\"}},{\"options\":{\"left\":199.5,\"top\":192,\"height\":25.5,\"width\":80,\"title\":\"商品批发价\",\"field\":\"PRODUCT_WHOLESALE_PRICE\",\"testData\":\"100.00\",\"templateField\":\"F_10\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false},\"printElementType\":{\"title\":\"商品批发价\",\"type\":\"text\"}},{\"options\":{\"left\":300,\"top\":201,\"height\":25.5,\"width\":120,\"title\":\"商品重量\",\"field\":\"PRODUCT_WEIGHT\",\"testData\":\"500\",\"templateField\":\"F_14\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false},\"printElementType\":{\"title\":\"商品重量\",\"type\":\"text\"}},{\"options\":{\"left\":127.5,\"top\":240,\"height\":25.5,\"width\":120,\"title\":\"二维码\",\"field\":\"PRODUCT_QRCODE\",\"testData\":\"默认值\",\"templateField\":\"F_17\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false},\"printElementType\":{\"title\":\"二维码\",\"type\":\"text\"}},{\"options\":{\"left\":43.5,\"top\":249,\"height\":25.5,\"width\":100,\"src\":\"http://placeholder.com/image\",\"title\":\"商品图片\",\"field\":\"PRODUCT_IMAGE\",\"testData\":\"http://placeholder.com/image\",\"templateField\":\"F_10\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false},\"printElementType\":{\"title\":\"商品图片\",\"type\":\"image\"}},{\"options\":{\"left\":295.5,\"top\":280.5,\"height\":25.5,\"width\":120,\"title\":\"商品单位\",\"field\":\"PRODUCT_UNIT\",\"testData\":\"个\",\"templateField\":\"F_13\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false},\"printElementType\":{\"title\":\"商品单位\",\"type\":\"text\"}},{\"options\":{\"left\":84,\"top\":297,\"height\":25.5,\"width\":120,\"title\":\"商品库存\",\"field\":\"PRODUCT_STOCK\",\"testData\":\"100\",\"templateField\":\"F_16\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false},\"printElementType\":{\"title\":\"商品库存\",\"type\":\"text\"}}],\"paperNumberContinue\":true,\"watermarkOptions\":{}}]}}', 'NOT_DELETE', '2025-07-18 16:19:38', '1543837863788879871', '2025-08-21 18:20:37', '1543837863788879871');
INSERT INTO `print_template_design` VALUES ('1946129644835848194', '-1', 'test2', 'PTD-1752828439254-705', '{\"panels\":[{\"index\":0,\"name\":\"test2\",\"paperType\":\"CUSTOM\",\"width\":250,\"height\":122,\"paperHeader\":0,\"paperFooter\":345.82677165354335,\"printElements\":[],\"paperNumberContinue\":true,\"eslConfig\":{\"screenType\":\"2.13T\",\"pixelWidth\":250,\"pixelHeight\":122,\"colorMode\":{\"black\":true,\"white\":true,\"red\":true,\"yellow\":false},\"orientation\":\"LANDSCAPE\"}}]}', '2.13T', 99, '{\"designConfig\":{\"panels\":[{\"index\":0,\"name\":\"2\",\"paperType\":\"CUSTOM\",\"height\":122,\"width\":250,\"paperHeader\":0,\"paperFooter\":345.82677165354335,\"printElements\":[{\"options\":{\"left\":394.5,\"top\":16.5,\"height\":295.5,\"width\":295.5,\"title\":\"二维码\",\"field\":\"PRODUCT_QRCODE\",\"testData\":\"https://example.com\",\"templateField\":\"QRCODE\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false,\"textType\":\"qrcode\",\"right\":690.744140625,\"bottom\":312.7470703125,\"vCenter\":542.994140625,\"hCenter\":164.9970703125},\"printElementType\":{\"title\":\"二维码\",\"type\":\"text\"}},{\"options\":{\"left\":34.5,\"top\":123,\"height\":96,\"width\":337.5,\"title\":\"条形码\",\"field\":\"PRODUCT_BARCODE\",\"testData\":\"1234567890123\",\"templateField\":\"QRCODE\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false,\"barcodeType\":\"code128\",\"textType\":\"barcode\",\"right\":371.98828125,\"bottom\":219.7412109375,\"vCenter\":203.23828125,\"hCenter\":171.7412109375},\"printElementType\":{\"title\":\"条形码\",\"type\":\"text\"}}],\"paperNumberContinue\":true,\"watermarkOptions\":{}}]}}', 'NOT_DELETE', '2025-07-18 16:47:19', '1543837863788879871', '2025-07-25 18:25:26', '1543837863788879871');
INSERT INTO `print_template_design` VALUES ('1946136975640395777', '-1', 'test3', 'PTD-1752830187028-406', '{\"panels\":[{\"index\":0,\"name\":\"test3\",\"paperType\":\"CUSTOM\",\"width\":250,\"height\":122,\"paperHeader\":0,\"paperFooter\":345.82677165354335,\"printElements\":[],\"paperNumberContinue\":true,\"eslConfig\":{\"screenType\":\"2.13T\",\"pixelWidth\":250,\"pixelHeight\":122,\"colorMode\":{\"black\":true,\"white\":true,\"red\":true,\"yellow\":false},\"orientation\":\"LANDSCAPE\"}}]}', '2.13T', 99, NULL, 'NOT_DELETE', '2025-07-18 17:16:27', '1543837863788879871', '2025-07-25 18:25:34', '1543837863788879871');
INSERT INTO `print_template_design` VALUES ('1947312285182455810', '-1', 'test4', 'PTD-1753110402676-995', '{\"panels\":[{\"index\":0,\"name\":\"test4\",\"paperType\":\"CUSTOM\",\"width\":400,\"height\":300,\"paperHeader\":0,\"paperFooter\":850.3937007874016,\"printElements\":[],\"paperNumberContinue\":true,\"eslConfig\":{\"screenType\":\"4.20T\",\"pixelWidth\":400,\"pixelHeight\":300,\"colorMode\":{\"black\":true,\"white\":true,\"red\":true,\"yellow\":false},\"orientation\":\"LANDSCAPE\"}}]}', '4.20T', 99, '{\"designConfig\":{\"panels\":[{\"index\":0,\"name\":\"3\",\"paperType\":\"CUSTOM\",\"height\":300,\"width\":400,\"paperHeader\":0,\"paperFooter\":850.3937007874016,\"printElements\":[{\"options\":{\"left\":156,\"top\":265.5,\"height\":25.5,\"width\":120,\"title\":\"商品材质\",\"field\":\"PRODUCT_MATERIAL\",\"testData\":\"塑料\",\"templateField\":\"F_09\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false},\"printElementType\":{\"title\":\"商品材质\",\"type\":\"text\"}},{\"options\":{\"left\":114,\"top\":306,\"height\":115.5,\"width\":399,\"title\":\"条形码\",\"field\":\"PRODUCT_BARCODE\",\"testData\":\"1234567890123\",\"templateField\":\"QRCODE\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false,\"barcodeType\":\"code128\",\"textType\":\"barcode\",\"right\":513,\"bottom\":421.5,\"vCenter\":313.5,\"hCenter\":363.75},\"printElementType\":{\"title\":\"条形码\",\"type\":\"text\"}},{\"options\":{\"left\":625.5,\"top\":360,\"height\":163.5,\"width\":120,\"title\":\"商品产地\",\"field\":\"PRODUCT_ORIGIN\",\"testData\":\"默认值\",\"templateField\":\"F_11\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false},\"printElementType\":{\"title\":\"商品产地\",\"type\":\"text\"}},{\"options\":{\"left\":858,\"top\":403.5,\"height\":25.5,\"width\":120,\"title\":\"商品产地\",\"field\":\"PRODUCT_ORIGIN\",\"testData\":\"默认值\",\"templateField\":\"F_11\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false},\"printElementType\":{\"title\":\"商品产地\",\"type\":\"text\"}},{\"options\":{\"left\":139.5,\"top\":453,\"height\":414,\"width\":414,\"title\":\"二维码\",\"field\":\"PRODUCT_QRCODE\",\"testData\":\"https://example.com\",\"templateField\":\"QRCODE\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false,\"textType\":\"qrcode\"},\"printElementType\":{\"title\":\"二维码\",\"type\":\"text\"}},{\"options\":{\"left\":912,\"top\":568.5,\"height\":25.5,\"width\":120,\"title\":\"商品名称\",\"field\":\"PRODUCT_NAME\",\"testData\":\"示例商品名称\",\"templateField\":\"name\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false,\"right\":977.25,\"bottom\":665.25,\"vCenter\":917.25,\"hCenter\":652.5},\"printElementType\":{\"title\":\"商品名称\",\"type\":\"text\"}},{\"options\":{\"left\":726,\"top\":591,\"height\":25.5,\"width\":80,\"title\":\"商品批发价\",\"field\":\"PRODUCT_WHOLESALE_PRICE\",\"testData\":\"100.00\",\"templateField\":\"F_08\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false},\"printElementType\":{\"title\":\"商品批发价\",\"type\":\"text\"}},{\"options\":{\"left\":705,\"top\":687,\"height\":25.5,\"width\":120,\"title\":\"商品规格\",\"field\":\"PRODUCT_SPECIFICATION\",\"testData\":\"500g\",\"templateField\":\"F_04\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false},\"printElementType\":{\"title\":\"商品规格\",\"type\":\"text\"}},{\"options\":{\"left\":834,\"top\":696,\"height\":25.5,\"width\":120,\"title\":\"商品名称\",\"field\":\"PRODUCT_NAME\",\"testData\":\"示例商品名称\",\"templateField\":\"name\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false},\"printElementType\":{\"title\":\"商品名称\",\"type\":\"text\"}},{\"options\":{\"left\":741,\"top\":787.5,\"height\":25.5,\"width\":80,\"title\":\"商品零售价\",\"field\":\"PRODUCT_RETAIL_PRICE\",\"testData\":\"100.00\",\"templateField\":\"F_01\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false},\"printElementType\":{\"title\":\"商品零售价\",\"type\":\"text\"}},{\"options\":{\"left\":514.5,\"top\":63,\"height\":225,\"width\":427.5,\"title\":\"商品零售价\",\"field\":\"PRODUCT_RETAIL_PRICE\",\"testData\":\"100.00\",\"templateField\":\"F_01\",\"fontWeight\":\"900\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"right\":942.0000457763672,\"bottom\":287.2500114440918,\"vCenter\":728.2500457763672,\"hCenter\":174.7500114440918,\"coordinateSync\":false,\"widthHeightSync\":false,\"fontSize\":21.75,\"letterSpacing\":5.25,\"qrCodeLevel\":0},\"printElementType\":{\"title\":\"商品零售价\",\"type\":\"text\"}}],\"paperNumberContinue\":true,\"watermarkOptions\":{}}]}}', 'NOT_DELETE', '2025-07-21 23:06:43', '1543837863788879871', '2025-07-25 18:25:42', '1543837863788879871');
INSERT INTO `print_template_design` VALUES ('1958477711475388418', '-1', 'test5', 'PTD-1755772447323-494', '{\"panels\":[{\"index\":0,\"name\":\"test5\",\"paperType\":\"CUSTOM\",\"width\":400,\"height\":300,\"paperHeader\":0,\"paperFooter\":850.3937007874016,\"printElements\":[],\"paperNumberContinue\":true,\"eslConfig\":{\"screenType\":\"4.20F\",\"pixelWidth\":400,\"pixelHeight\":300,\"colorMode\":{\"black\":true,\"white\":true,\"red\":true,\"yellow\":true},\"orientation\":\"LANDSCAPE\",\"modelId\":\"1958456215382175745\",\"modelCode\":\"AESTAG002\",\"modelName\":\"AES电子价签4.3F\"}}]}', '4.20F', 99, '{\"designConfig\":{\"panels\":[{\"index\":0,\"name\":\"test5\",\"paperType\":\"CUSTOM\",\"height\":300,\"width\":400,\"paperHeader\":0,\"paperFooter\":850.3937007874016,\"printElements\":[{\"options\":{\"left\":108,\"top\":204,\"height\":30,\"width\":120,\"title\":\"商品条形码\",\"field\":\"PRODUCT_BARCODE\",\"testData\":\"6901234567890\",\"templateField\":\"F_18\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false,\"textType\":\"barcode\"},\"printElementType\":{\"title\":\"商品条形码\",\"type\":\"text\"}},{\"options\":{\"left\":153,\"top\":282,\"height\":379.5,\"width\":379.5,\"title\":\"商品二维码\",\"field\":\"PRODUCT_QRCODE\",\"testData\":\"https://example.com/product/P001\",\"templateField\":\"F_17\",\"fontWeight\":\"400\",\"textAlign\":\"center\",\"textContentVerticalAlign\":\"middle\",\"hideTitle\":false,\"textType\":\"qrcode\",\"right\":212.25,\"bottom\":341.2500228881836,\"vCenter\":182.25,\"hCenter\":311.2500228881836},\"printElementType\":{\"title\":\"商品二维码\",\"type\":\"text\"}}],\"paperNumberContinue\":true,\"watermarkOptions\":{}}]}}', 'NOT_DELETE', '2025-08-21 18:34:08', '1543837863788879871', '2025-08-21 18:35:02', '1543837863788879871');
INSERT INTO `print_template_design` VALUES ('1958479186184286209', '-1', 'test6', 'PTD-1755772798930-477', '{\"panels\":[{\"index\":0,\"name\":\"test6\",\"paperType\":\"CUSTOM\",\"width\":400,\"height\":300,\"paperHeader\":0,\"paperFooter\":850.3937007874016,\"printElements\":[],\"paperNumberContinue\":true,\"eslConfig\":{\"screenType\":\"MODEL_1958456055847628802\",\"pixelWidth\":400,\"pixelHeight\":300,\"colorMode\":{\"black\":true,\"white\":true,\"red\":true,\"yellow\":false},\"orientation\":\"LANDSCAPE\",\"modelId\":\"1958456055847628802\",\"modelCode\":\"AESTAG002\",\"modelName\":\"AES电子价签4.3T\"}}]}', 'MODEL_1958456055847628802', 99, NULL, 'DELETED', '2025-08-21 18:39:59', '1543837863788879871', '2025-08-21 18:40:29', '1543837863788879871');
