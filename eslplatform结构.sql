/*
 Navicat Premium Dump SQL

 Source Server         : 内网服务器穿透
 Source Server Type    : MySQL
 Source Server Version : 80036 (8.0.36)
 Source Host           : 10.3.36.25:3306
 Source Schema         : eslplatform

 Target Server Type    : MySQL
 Target Server Version : 80036 (8.0.36)
 File Encoding         : 65001

 Date: 21/07/2025 23:01:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for act_ext_agent
-- ----------------------------
DROP TABLE IF EXISTS `act_ext_agent`;
CREATE TABLE `act_ext_agent`  (
                                  `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
                                  `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                  `PRINCIPAL_USER` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '委托人',
                                  `AGENT_USER` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '受托人',
                                  `PROCESS` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '委托流程',
                                  `START_TIME` datetime NULL DEFAULT NULL COMMENT '开始时间',
                                  `END_TIME` datetime NULL DEFAULT NULL COMMENT '结束时间',
                                  `CAUSE` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原因',
                                  `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                  `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                  `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                  `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                  `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                  `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新用户',
                                  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '流程委托' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_ext_model
-- ----------------------------
DROP TABLE IF EXISTS `act_ext_model`;
CREATE TABLE `act_ext_model`  (
                                  `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                  `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                  `DEFINITION_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '定义id',
                                  `DEPLOYMENT_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部署id',
                                  `ADMIN_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '管理员id',
                                  `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
                                  `CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
                                  `VERSION_CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版本',
                                  `FORM_TYPE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表单类型',
                                  `CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类',
                                  `ICON` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
                                  `ICON_MOBILE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '移动端图标',
                                  `COLOR` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '颜色',
                                  `WRITE_DATA_TABLE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '写入数据表',
                                  `TABLE_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据库表JSON',
                                  `FORM_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '表单JSON',
                                  `FORM_URL` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '表单URL',
                                  `PROCESS_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '流程JSON',
                                  `PROCESS_XML` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '流程XML',
                                  `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
                                  `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                  `MODEL_STATUS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模型状态',
                                  `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                  `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                  `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                  `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                  `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                                  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '模型' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_ext_relation
-- ----------------------------
DROP TABLE IF EXISTS `act_ext_relation`;
CREATE TABLE `act_ext_relation`  (
                                     `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                     `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                     `OBJECT_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对象ID',
                                     `TARGET_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目标ID',
                                     `CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类',
                                     `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                     `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                     `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                     `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                     `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                     `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                                     PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '关系' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_ext_seal
-- ----------------------------
DROP TABLE IF EXISTS `act_ext_seal`;
CREATE TABLE `act_ext_seal`  (
                                 `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
                                 `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                 `TITLE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '印章名称',
                                 `TYPE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '印章类型（法人章、公章）',
                                 `ORG_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '印章机构',
                                 `IS_DEFAULT` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否默认',
                                 `SEAL_IMG` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '印章图片',
                                 `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                 `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                 `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                 `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                 `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                 `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新用户',
                                 PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '电子印章' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_ext_template_print
-- ----------------------------
DROP TABLE IF EXISTS `act_ext_template_print`;
CREATE TABLE `act_ext_template_print`  (
                                           `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                           `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                           `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
                                           `CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
                                           `CONTENT` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
                                           `CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类',
                                           `TYPE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类别',
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
-- Table structure for act_ext_template_sn
-- ----------------------------
DROP TABLE IF EXISTS `act_ext_template_sn`;
CREATE TABLE `act_ext_template_sn`  (
                                        `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                        `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                        `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
                                        `CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
                                        `CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类',
                                        `PREFIX` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前缀',
                                        `DATE_FORMAT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日期格式',
                                        `SUFFIX_DIGITS` int NULL DEFAULT NULL COMMENT '后缀位数',
                                        `SUFFIX_INITIAL_VALUE` int NULL DEFAULT NULL COMMENT '后缀初始值',
                                        `SUFFIX_INCREMENTAL_VALUE` int NULL DEFAULT NULL COMMENT '后缀增量',
                                        `PREVIEW_VALUE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '显示值',
                                        `LATEST_VALUE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最新值',
                                        `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
                                        `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                        `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                        `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                        `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                        `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                        `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                                        PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '流水号模板' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_ge_bytearray
-- ----------------------------
DROP TABLE IF EXISTS `act_ge_bytearray`;
CREATE TABLE `act_ge_bytearray`  (
                                     `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                     `REV_` int NULL DEFAULT NULL,
                                     `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                     `DEPLOYMENT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                     `BYTES_` longblob NULL,
                                     `GENERATED_` tinyint NULL DEFAULT NULL,
                                     `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                     `TYPE_` int NULL DEFAULT NULL,
                                     `CREATE_TIME_` datetime NULL DEFAULT NULL,
                                     `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                     `REMOVAL_TIME_` datetime NULL DEFAULT NULL,
                                     PRIMARY KEY (`ID_`) USING BTREE,
                                     INDEX `ACT_FK_BYTEARR_DEPL`(`DEPLOYMENT_ID_` ASC) USING BTREE,
                                     INDEX `ACT_IDX_BYTEARRAY_ROOT_PI`(`ROOT_PROC_INST_ID_` ASC) USING BTREE,
                                     INDEX `ACT_IDX_BYTEARRAY_RM_TIME`(`REMOVAL_TIME_` ASC) USING BTREE,
                                     INDEX `ACT_IDX_BYTEARRAY_NAME`(`NAME_` ASC) USING BTREE,
                                     CONSTRAINT `ACT_FK_BYTEARR_DEPL` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_ge_property
-- ----------------------------
DROP TABLE IF EXISTS `act_ge_property`;
CREATE TABLE `act_ge_property`  (
                                    `NAME_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                    `VALUE_` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `REV_` int NULL DEFAULT NULL,
                                    PRIMARY KEY (`NAME_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_ge_schema_log
-- ----------------------------
DROP TABLE IF EXISTS `act_ge_schema_log`;
CREATE TABLE `act_ge_schema_log`  (
                                      `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                      `TIMESTAMP_` datetime NULL DEFAULT NULL,
                                      `VERSION_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                      PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_hi_actinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_actinst`;
CREATE TABLE `act_hi_actinst`  (
                                   `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                   `PARENT_ACT_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `PROC_DEF_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                   `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                   `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                   `ACT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                   `TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `CALL_PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `CALL_CASE_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `ACT_NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `ACT_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                   `ASSIGNEE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `START_TIME_` datetime NOT NULL,
                                   `END_TIME_` datetime NULL DEFAULT NULL,
                                   `DURATION_` bigint NULL DEFAULT NULL,
                                   `ACT_INST_STATE_` int NULL DEFAULT NULL,
                                   `SEQUENCE_COUNTER_` bigint NULL DEFAULT NULL,
                                   `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `REMOVAL_TIME_` datetime NULL DEFAULT NULL,
                                   PRIMARY KEY (`ID_`) USING BTREE,
                                   INDEX `ACT_IDX_HI_ACTINST_ROOT_PI`(`ROOT_PROC_INST_ID_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_ACT_INST_START_END`(`START_TIME_` ASC, `END_TIME_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_ACT_INST_END`(`END_TIME_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_ACT_INST_PROCINST`(`PROC_INST_ID_` ASC, `ACT_ID_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_ACT_INST_COMP`(`EXECUTION_ID_` ASC, `ACT_ID_` ASC, `END_TIME_` ASC, `ID_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_ACT_INST_STATS`(`PROC_DEF_ID_` ASC, `PROC_INST_ID_` ASC, `ACT_ID_` ASC, `END_TIME_` ASC, `ACT_INST_STATE_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_ACT_INST_TENANT_ID`(`TENANT_ID_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_ACT_INST_PROC_DEF_KEY`(`PROC_DEF_KEY_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_AI_PDEFID_END_TIME`(`PROC_DEF_ID_` ASC, `END_TIME_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_ACT_INST_RM_TIME`(`REMOVAL_TIME_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_hi_attachment
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_attachment`;
CREATE TABLE `act_hi_attachment`  (
                                      `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                      `REV_` int NULL DEFAULT NULL,
                                      `USER_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                      `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                      `DESCRIPTION_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                      `TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                      `TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                      `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                      `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                      `URL_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                      `CONTENT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                      `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                      `CREATE_TIME_` datetime NULL DEFAULT NULL,
                                      `REMOVAL_TIME_` datetime NULL DEFAULT NULL,
                                      PRIMARY KEY (`ID_`) USING BTREE,
                                      INDEX `ACT_IDX_HI_ATTACHMENT_CONTENT`(`CONTENT_ID_` ASC) USING BTREE,
                                      INDEX `ACT_IDX_HI_ATTACHMENT_ROOT_PI`(`ROOT_PROC_INST_ID_` ASC) USING BTREE,
                                      INDEX `ACT_IDX_HI_ATTACHMENT_PROCINST`(`PROC_INST_ID_` ASC) USING BTREE,
                                      INDEX `ACT_IDX_HI_ATTACHMENT_TASK`(`TASK_ID_` ASC) USING BTREE,
                                      INDEX `ACT_IDX_HI_ATTACHMENT_RM_TIME`(`REMOVAL_TIME_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_hi_batch
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_batch`;
CREATE TABLE `act_hi_batch`  (
                                 `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                 `TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                 `TOTAL_JOBS_` int NULL DEFAULT NULL,
                                 `JOBS_PER_SEED_` int NULL DEFAULT NULL,
                                 `INVOCATIONS_PER_JOB_` int NULL DEFAULT NULL,
                                 `SEED_JOB_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                 `MONITOR_JOB_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                 `BATCH_JOB_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                 `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                 `CREATE_USER_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                 `START_TIME_` datetime NOT NULL,
                                 `END_TIME_` datetime NULL DEFAULT NULL,
                                 `REMOVAL_TIME_` datetime NULL DEFAULT NULL,
                                 `EXEC_START_TIME_` datetime NULL DEFAULT NULL,
                                 PRIMARY KEY (`ID_`) USING BTREE,
                                 INDEX `ACT_HI_BAT_RM_TIME`(`REMOVAL_TIME_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_hi_comment
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_comment`;
CREATE TABLE `act_hi_comment`  (
                                   `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                   `TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `TIME_` datetime NOT NULL,
                                   `USER_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `ACTION_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `MESSAGE_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `FULL_MSG_` longblob NULL,
                                   `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `REMOVAL_TIME_` datetime NULL DEFAULT NULL,
                                   PRIMARY KEY (`ID_`) USING BTREE,
                                   INDEX `ACT_IDX_HI_COMMENT_TASK`(`TASK_ID_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_COMMENT_ROOT_PI`(`ROOT_PROC_INST_ID_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_COMMENT_PROCINST`(`PROC_INST_ID_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_COMMENT_RM_TIME`(`REMOVAL_TIME_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_hi_detail
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_detail`;
CREATE TABLE `act_hi_detail`  (
                                  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                  `TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                  `PROC_DEF_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `CASE_DEF_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `CASE_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `CASE_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `CASE_EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `ACT_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `VAR_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                  `VAR_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `REV_` int NULL DEFAULT NULL,
                                  `TIME_` datetime NOT NULL,
                                  `BYTEARRAY_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `DOUBLE_` double NULL DEFAULT NULL,
                                  `LONG_` bigint NULL DEFAULT NULL,
                                  `TEXT_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `TEXT2_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `SEQUENCE_COUNTER_` bigint NULL DEFAULT NULL,
                                  `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `OPERATION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `REMOVAL_TIME_` datetime NULL DEFAULT NULL,
                                  `INITIAL_` tinyint(1) NULL DEFAULT NULL,
                                  PRIMARY KEY (`ID_`) USING BTREE,
                                  INDEX `ACT_IDX_HI_DETAIL_ROOT_PI`(`ROOT_PROC_INST_ID_` ASC) USING BTREE,
                                  INDEX `ACT_IDX_HI_DETAIL_PROC_INST`(`PROC_INST_ID_` ASC) USING BTREE,
                                  INDEX `ACT_IDX_HI_DETAIL_ACT_INST`(`ACT_INST_ID_` ASC) USING BTREE,
                                  INDEX `ACT_IDX_HI_DETAIL_CASE_INST`(`CASE_INST_ID_` ASC) USING BTREE,
                                  INDEX `ACT_IDX_HI_DETAIL_CASE_EXEC`(`CASE_EXECUTION_ID_` ASC) USING BTREE,
                                  INDEX `ACT_IDX_HI_DETAIL_TIME`(`TIME_` ASC) USING BTREE,
                                  INDEX `ACT_IDX_HI_DETAIL_NAME`(`NAME_` ASC) USING BTREE,
                                  INDEX `ACT_IDX_HI_DETAIL_TASK_ID`(`TASK_ID_` ASC) USING BTREE,
                                  INDEX `ACT_IDX_HI_DETAIL_TENANT_ID`(`TENANT_ID_` ASC) USING BTREE,
                                  INDEX `ACT_IDX_HI_DETAIL_PROC_DEF_KEY`(`PROC_DEF_KEY_` ASC) USING BTREE,
                                  INDEX `ACT_IDX_HI_DETAIL_BYTEAR`(`BYTEARRAY_ID_` ASC) USING BTREE,
                                  INDEX `ACT_IDX_HI_DETAIL_RM_TIME`(`REMOVAL_TIME_` ASC) USING BTREE,
                                  INDEX `ACT_IDX_HI_DETAIL_TASK_BYTEAR`(`BYTEARRAY_ID_` ASC, `TASK_ID_` ASC) USING BTREE,
                                  INDEX `ACT_IDX_HI_DETAIL_VAR_INST_ID`(`VAR_INST_ID_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_hi_ext_task_log
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_ext_task_log`;
CREATE TABLE `act_hi_ext_task_log`  (
                                        `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                        `TIMESTAMP_` timestamp NOT NULL,
                                        `EXT_TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                        `RETRIES_` int NULL DEFAULT NULL,
                                        `TOPIC_NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `WORKER_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `PRIORITY_` bigint NOT NULL DEFAULT 0,
                                        `ERROR_MSG_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `ERROR_DETAILS_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `ACT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `ACT_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `PROC_DEF_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `STATE_` int NULL DEFAULT NULL,
                                        `REV_` int NULL DEFAULT NULL,
                                        `REMOVAL_TIME_` datetime NULL DEFAULT NULL,
                                        PRIMARY KEY (`ID_`) USING BTREE,
                                        INDEX `ACT_HI_EXT_TASK_LOG_ROOT_PI`(`ROOT_PROC_INST_ID_` ASC) USING BTREE,
                                        INDEX `ACT_HI_EXT_TASK_LOG_PROCINST`(`PROC_INST_ID_` ASC) USING BTREE,
                                        INDEX `ACT_HI_EXT_TASK_LOG_PROCDEF`(`PROC_DEF_ID_` ASC) USING BTREE,
                                        INDEX `ACT_HI_EXT_TASK_LOG_PROC_DEF_KEY`(`PROC_DEF_KEY_` ASC) USING BTREE,
                                        INDEX `ACT_HI_EXT_TASK_LOG_TENANT_ID`(`TENANT_ID_` ASC) USING BTREE,
                                        INDEX `ACT_IDX_HI_EXTTASKLOG_ERRORDET`(`ERROR_DETAILS_ID_` ASC) USING BTREE,
                                        INDEX `ACT_HI_EXT_TASK_LOG_RM_TIME`(`REMOVAL_TIME_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_hi_identitylink
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_identitylink`;
CREATE TABLE `act_hi_identitylink`  (
                                        `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                        `TIMESTAMP_` timestamp NOT NULL,
                                        `TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `USER_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `GROUP_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `OPERATION_TYPE_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `ASSIGNER_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `PROC_DEF_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `REMOVAL_TIME_` datetime NULL DEFAULT NULL,
                                        PRIMARY KEY (`ID_`) USING BTREE,
                                        INDEX `ACT_IDX_HI_IDENT_LNK_ROOT_PI`(`ROOT_PROC_INST_ID_` ASC) USING BTREE,
                                        INDEX `ACT_IDX_HI_IDENT_LNK_USER`(`USER_ID_` ASC) USING BTREE,
                                        INDEX `ACT_IDX_HI_IDENT_LNK_GROUP`(`GROUP_ID_` ASC) USING BTREE,
                                        INDEX `ACT_IDX_HI_IDENT_LNK_TENANT_ID`(`TENANT_ID_` ASC) USING BTREE,
                                        INDEX `ACT_IDX_HI_IDENT_LNK_PROC_DEF_KEY`(`PROC_DEF_KEY_` ASC) USING BTREE,
                                        INDEX `ACT_IDX_HI_IDENT_LINK_TASK`(`TASK_ID_` ASC) USING BTREE,
                                        INDEX `ACT_IDX_HI_IDENT_LINK_RM_TIME`(`REMOVAL_TIME_` ASC) USING BTREE,
                                        INDEX `ACT_IDX_HI_IDENT_LNK_TIMESTAMP`(`TIMESTAMP_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_hi_incident
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_incident`;
CREATE TABLE `act_hi_incident`  (
                                    `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                    `PROC_DEF_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `CREATE_TIME_` timestamp NOT NULL,
                                    `END_TIME_` timestamp NULL DEFAULT NULL,
                                    `INCIDENT_MSG_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `INCIDENT_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                    `ACTIVITY_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `FAILED_ACTIVITY_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `CAUSE_INCIDENT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `ROOT_CAUSE_INCIDENT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `CONFIGURATION_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `HISTORY_CONFIGURATION_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `INCIDENT_STATE_` int NULL DEFAULT NULL,
                                    `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `JOB_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `ANNOTATION_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `REMOVAL_TIME_` datetime NULL DEFAULT NULL,
                                    PRIMARY KEY (`ID_`) USING BTREE,
                                    INDEX `ACT_IDX_HI_INCIDENT_TENANT_ID`(`TENANT_ID_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_HI_INCIDENT_PROC_DEF_KEY`(`PROC_DEF_KEY_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_HI_INCIDENT_ROOT_PI`(`ROOT_PROC_INST_ID_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_HI_INCIDENT_PROCINST`(`PROC_INST_ID_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_HI_INCIDENT_RM_TIME`(`REMOVAL_TIME_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_HI_INCIDENT_CREATE_TIME`(`CREATE_TIME_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_HI_INCIDENT_END_TIME`(`END_TIME_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_hi_job_log
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_job_log`;
CREATE TABLE `act_hi_job_log`  (
                                   `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                   `TIMESTAMP_` datetime NOT NULL,
                                   `JOB_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                   `JOB_DUEDATE_` datetime NULL DEFAULT NULL,
                                   `JOB_RETRIES_` int NULL DEFAULT NULL,
                                   `JOB_PRIORITY_` bigint NOT NULL DEFAULT 0,
                                   `JOB_EXCEPTION_MSG_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `JOB_EXCEPTION_STACK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `JOB_STATE_` int NULL DEFAULT NULL,
                                   `JOB_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `JOB_DEF_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `JOB_DEF_CONFIGURATION_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `ACT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `FAILED_ACT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `PROCESS_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `PROCESS_DEF_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `DEPLOYMENT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `SEQUENCE_COUNTER_` bigint NULL DEFAULT NULL,
                                   `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `HOSTNAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `REMOVAL_TIME_` datetime NULL DEFAULT NULL,
                                   PRIMARY KEY (`ID_`) USING BTREE,
                                   INDEX `ACT_IDX_HI_JOB_LOG_ROOT_PI`(`ROOT_PROC_INST_ID_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_JOB_LOG_PROCINST`(`PROCESS_INSTANCE_ID_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_JOB_LOG_PROCDEF`(`PROCESS_DEF_ID_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_JOB_LOG_TENANT_ID`(`TENANT_ID_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_JOB_LOG_JOB_DEF_ID`(`JOB_DEF_ID_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_JOB_LOG_PROC_DEF_KEY`(`PROCESS_DEF_KEY_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_JOB_LOG_EX_STACK`(`JOB_EXCEPTION_STACK_ID_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_JOB_LOG_RM_TIME`(`REMOVAL_TIME_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_JOB_LOG_JOB_CONF`(`JOB_DEF_CONFIGURATION_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_hi_op_log
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_op_log`;
CREATE TABLE `act_hi_op_log`  (
                                  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                  `DEPLOYMENT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `PROC_DEF_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `CASE_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `CASE_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `CASE_EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `JOB_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `JOB_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `BATCH_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `USER_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `TIMESTAMP_` timestamp NOT NULL,
                                  `OPERATION_TYPE_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `OPERATION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `ENTITY_TYPE_` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `PROPERTY_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `ORG_VALUE_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `NEW_VALUE_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `REMOVAL_TIME_` datetime NULL DEFAULT NULL,
                                  `CATEGORY_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `EXTERNAL_TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `ANNOTATION_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  PRIMARY KEY (`ID_`) USING BTREE,
                                  INDEX `ACT_IDX_HI_OP_LOG_ROOT_PI`(`ROOT_PROC_INST_ID_` ASC) USING BTREE,
                                  INDEX `ACT_IDX_HI_OP_LOG_PROCINST`(`PROC_INST_ID_` ASC) USING BTREE,
                                  INDEX `ACT_IDX_HI_OP_LOG_PROCDEF`(`PROC_DEF_ID_` ASC) USING BTREE,
                                  INDEX `ACT_IDX_HI_OP_LOG_TASK`(`TASK_ID_` ASC) USING BTREE,
                                  INDEX `ACT_IDX_HI_OP_LOG_RM_TIME`(`REMOVAL_TIME_` ASC) USING BTREE,
                                  INDEX `ACT_IDX_HI_OP_LOG_TIMESTAMP`(`TIMESTAMP_` ASC) USING BTREE,
                                  INDEX `ACT_IDX_HI_OP_LOG_USER_ID`(`USER_ID_` ASC) USING BTREE,
                                  INDEX `ACT_IDX_HI_OP_LOG_OP_TYPE`(`OPERATION_TYPE_` ASC) USING BTREE,
                                  INDEX `ACT_IDX_HI_OP_LOG_ENTITY_TYPE`(`ENTITY_TYPE_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_hi_procinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_procinst`;
CREATE TABLE `act_hi_procinst`  (
                                    `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                    `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                    `BUSINESS_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `PROC_DEF_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                    `START_TIME_` datetime NOT NULL,
                                    `END_TIME_` datetime NULL DEFAULT NULL,
                                    `REMOVAL_TIME_` datetime NULL DEFAULT NULL,
                                    `DURATION_` bigint NULL DEFAULT NULL,
                                    `START_USER_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `START_ACT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `END_ACT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `SUPER_PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `SUPER_CASE_INSTANCE_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `CASE_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `DELETE_REASON_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `STATE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    PRIMARY KEY (`ID_`) USING BTREE,
                                    UNIQUE INDEX `PROC_INST_ID_`(`PROC_INST_ID_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_HI_PRO_INST_END`(`END_TIME_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_HI_PRO_I_BUSKEY`(`BUSINESS_KEY_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_HI_PRO_INST_TENANT_ID`(`TENANT_ID_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_HI_PRO_INST_PROC_DEF_KEY`(`PROC_DEF_KEY_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_HI_PRO_INST_PROC_TIME`(`START_TIME_` ASC, `END_TIME_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_HI_PI_PDEFID_END_TIME`(`PROC_DEF_ID_` ASC, `END_TIME_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_HI_PRO_INST_ROOT_PI`(`ROOT_PROC_INST_ID_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_HI_PRO_INST_RM_TIME`(`REMOVAL_TIME_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_hi_taskinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_taskinst`;
CREATE TABLE `act_hi_taskinst`  (
                                    `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                    `TASK_DEF_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `PROC_DEF_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `CASE_DEF_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `CASE_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `CASE_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `CASE_EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `ACT_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `PARENT_TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `DESCRIPTION_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `OWNER_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `ASSIGNEE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `START_TIME_` datetime NOT NULL,
                                    `END_TIME_` datetime NULL DEFAULT NULL,
                                    `DURATION_` bigint NULL DEFAULT NULL,
                                    `DELETE_REASON_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `PRIORITY_` int NULL DEFAULT NULL,
                                    `DUE_DATE_` datetime NULL DEFAULT NULL,
                                    `FOLLOW_UP_DATE_` datetime NULL DEFAULT NULL,
                                    `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `REMOVAL_TIME_` datetime NULL DEFAULT NULL,
                                    PRIMARY KEY (`ID_`) USING BTREE,
                                    INDEX `ACT_IDX_HI_TASKINST_ROOT_PI`(`ROOT_PROC_INST_ID_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_HI_TASK_INST_TENANT_ID`(`TENANT_ID_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_HI_TASK_INST_PROC_DEF_KEY`(`PROC_DEF_KEY_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_HI_TASKINST_PROCINST`(`PROC_INST_ID_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_HI_TASKINSTID_PROCINST`(`ID_` ASC, `PROC_INST_ID_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_HI_TASK_INST_RM_TIME`(`REMOVAL_TIME_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_HI_TASK_INST_START`(`START_TIME_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_HI_TASK_INST_END`(`END_TIME_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_hi_varinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_varinst`;
CREATE TABLE `act_hi_varinst`  (
                                   `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                   `PROC_DEF_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `ACT_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `CASE_DEF_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `CASE_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `CASE_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `CASE_EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                   `VAR_TYPE_` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `CREATE_TIME_` datetime NULL DEFAULT NULL,
                                   `REV_` int NULL DEFAULT NULL,
                                   `BYTEARRAY_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `DOUBLE_` double NULL DEFAULT NULL,
                                   `LONG_` bigint NULL DEFAULT NULL,
                                   `TEXT_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `TEXT2_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `STATE_` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `REMOVAL_TIME_` datetime NULL DEFAULT NULL,
                                   PRIMARY KEY (`ID_`) USING BTREE,
                                   INDEX `ACT_IDX_HI_VARINST_ROOT_PI`(`ROOT_PROC_INST_ID_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_PROCVAR_PROC_INST`(`PROC_INST_ID_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_PROCVAR_NAME_TYPE`(`NAME_` ASC, `VAR_TYPE_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_CASEVAR_CASE_INST`(`CASE_INST_ID_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_VAR_INST_TENANT_ID`(`TENANT_ID_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_VAR_INST_PROC_DEF_KEY`(`PROC_DEF_KEY_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_VARINST_BYTEAR`(`BYTEARRAY_ID_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_VARINST_RM_TIME`(`REMOVAL_TIME_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_VAR_PI_NAME_TYPE`(`PROC_INST_ID_` ASC, `NAME_` ASC, `VAR_TYPE_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_VARINST_NAME`(`NAME_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_HI_VARINST_ACT_INST_ID`(`ACT_INST_ID_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_re_camformdef
-- ----------------------------
DROP TABLE IF EXISTS `act_re_camformdef`;
CREATE TABLE `act_re_camformdef`  (
                                      `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                      `REV_` int NULL DEFAULT NULL,
                                      `KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                      `VERSION_` int NOT NULL,
                                      `DEPLOYMENT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                      `RESOURCE_NAME_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                      `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                      PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_re_deployment
-- ----------------------------
DROP TABLE IF EXISTS `act_re_deployment`;
CREATE TABLE `act_re_deployment`  (
                                      `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                      `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                      `DEPLOY_TIME_` datetime NULL DEFAULT NULL,
                                      `SOURCE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                      `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                      PRIMARY KEY (`ID_`) USING BTREE,
                                      INDEX `ACT_IDX_DEPLOYMENT_NAME`(`NAME_` ASC) USING BTREE,
                                      INDEX `ACT_IDX_DEPLOYMENT_TENANT_ID`(`TENANT_ID_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_re_procdef
-- ----------------------------
DROP TABLE IF EXISTS `act_re_procdef`;
CREATE TABLE `act_re_procdef`  (
                                   `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                   `REV_` int NULL DEFAULT NULL,
                                   `CATEGORY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                   `VERSION_` int NOT NULL,
                                   `DEPLOYMENT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `RESOURCE_NAME_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `DGRM_RESOURCE_NAME_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `HAS_START_FORM_KEY_` tinyint NULL DEFAULT NULL,
                                   `SUSPENSION_STATE_` int NULL DEFAULT NULL,
                                   `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `VERSION_TAG_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                   `HISTORY_TTL_` int NULL DEFAULT NULL,
                                   `STARTABLE_` tinyint(1) NOT NULL DEFAULT 1,
                                   PRIMARY KEY (`ID_`) USING BTREE,
                                   INDEX `ACT_IDX_PROCDEF_DEPLOYMENT_ID`(`DEPLOYMENT_ID_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_PROCDEF_TENANT_ID`(`TENANT_ID_` ASC) USING BTREE,
                                   INDEX `ACT_IDX_PROCDEF_VER_TAG`(`VERSION_TAG_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_ru_authorization
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_authorization`;
CREATE TABLE `act_ru_authorization`  (
                                         `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                         `REV_` int NOT NULL,
                                         `TYPE_` int NOT NULL,
                                         `GROUP_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                         `USER_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                         `RESOURCE_TYPE_` int NOT NULL,
                                         `RESOURCE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                         `PERMS_` int NULL DEFAULT NULL,
                                         `REMOVAL_TIME_` datetime NULL DEFAULT NULL,
                                         `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                         PRIMARY KEY (`ID_`) USING BTREE,
                                         UNIQUE INDEX `ACT_UNIQ_AUTH_USER`(`USER_ID_` ASC, `TYPE_` ASC, `RESOURCE_TYPE_` ASC, `RESOURCE_ID_` ASC) USING BTREE,
                                         UNIQUE INDEX `ACT_UNIQ_AUTH_GROUP`(`GROUP_ID_` ASC, `TYPE_` ASC, `RESOURCE_TYPE_` ASC, `RESOURCE_ID_` ASC) USING BTREE,
                                         INDEX `ACT_IDX_AUTH_GROUP_ID`(`GROUP_ID_` ASC) USING BTREE,
                                         INDEX `ACT_IDX_AUTH_RESOURCE_ID`(`RESOURCE_ID_` ASC) USING BTREE,
                                         INDEX `ACT_IDX_AUTH_ROOT_PI`(`ROOT_PROC_INST_ID_` ASC) USING BTREE,
                                         INDEX `ACT_IDX_AUTH_RM_TIME`(`REMOVAL_TIME_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_ru_batch
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_batch`;
CREATE TABLE `act_ru_batch`  (
                                 `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                 `REV_` int NOT NULL,
                                 `TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                 `TOTAL_JOBS_` int NULL DEFAULT NULL,
                                 `JOBS_CREATED_` int NULL DEFAULT NULL,
                                 `JOBS_PER_SEED_` int NULL DEFAULT NULL,
                                 `INVOCATIONS_PER_JOB_` int NULL DEFAULT NULL,
                                 `SEED_JOB_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                 `BATCH_JOB_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                 `MONITOR_JOB_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                 `SUSPENSION_STATE_` int NULL DEFAULT NULL,
                                 `CONFIGURATION_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                 `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                 `CREATE_USER_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                 `START_TIME_` datetime NULL DEFAULT NULL,
                                 `EXEC_START_TIME_` datetime NULL DEFAULT NULL,
                                 PRIMARY KEY (`ID_`) USING BTREE,
                                 INDEX `ACT_IDX_BATCH_SEED_JOB_DEF`(`SEED_JOB_DEF_ID_` ASC) USING BTREE,
                                 INDEX `ACT_IDX_BATCH_MONITOR_JOB_DEF`(`MONITOR_JOB_DEF_ID_` ASC) USING BTREE,
                                 INDEX `ACT_IDX_BATCH_JOB_DEF`(`BATCH_JOB_DEF_ID_` ASC) USING BTREE,
                                 CONSTRAINT `ACT_FK_BATCH_JOB_DEF` FOREIGN KEY (`BATCH_JOB_DEF_ID_`) REFERENCES `act_ru_jobdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                 CONSTRAINT `ACT_FK_BATCH_MONITOR_JOB_DEF` FOREIGN KEY (`MONITOR_JOB_DEF_ID_`) REFERENCES `act_ru_jobdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                 CONSTRAINT `ACT_FK_BATCH_SEED_JOB_DEF` FOREIGN KEY (`SEED_JOB_DEF_ID_`) REFERENCES `act_ru_jobdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_ru_event_subscr
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_event_subscr`;
CREATE TABLE `act_ru_event_subscr`  (
                                        `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                        `REV_` int NULL DEFAULT NULL,
                                        `EVENT_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                        `EVENT_NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `ACTIVITY_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `CONFIGURATION_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `CREATED_` datetime NOT NULL,
                                        `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        PRIMARY KEY (`ID_`) USING BTREE,
                                        INDEX `ACT_IDX_EVENT_SUBSCR_CONFIG_`(`CONFIGURATION_` ASC) USING BTREE,
                                        INDEX `ACT_IDX_EVENT_SUBSCR_TENANT_ID`(`TENANT_ID_` ASC) USING BTREE,
                                        INDEX `ACT_FK_EVENT_EXEC`(`EXECUTION_ID_` ASC) USING BTREE,
                                        INDEX `ACT_IDX_EVENT_SUBSCR_EVT_NAME`(`EVENT_NAME_` ASC) USING BTREE,
                                        CONSTRAINT `ACT_FK_EVENT_EXEC` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_ru_execution
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_execution`;
CREATE TABLE `act_ru_execution`  (
                                     `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                     `REV_` int NULL DEFAULT NULL,
                                     `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                     `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                     `BUSINESS_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                     `PARENT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                     `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                     `SUPER_EXEC_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                     `SUPER_CASE_EXEC_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                     `CASE_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                     `ACT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                     `ACT_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                     `IS_ACTIVE_` tinyint NULL DEFAULT NULL,
                                     `IS_CONCURRENT_` tinyint NULL DEFAULT NULL,
                                     `IS_SCOPE_` tinyint NULL DEFAULT NULL,
                                     `IS_EVENT_SCOPE_` tinyint NULL DEFAULT NULL,
                                     `SUSPENSION_STATE_` int NULL DEFAULT NULL,
                                     `CACHED_ENT_STATE_` int NULL DEFAULT NULL,
                                     `SEQUENCE_COUNTER_` bigint NULL DEFAULT NULL,
                                     `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                     PRIMARY KEY (`ID_`) USING BTREE,
                                     INDEX `ACT_IDX_EXEC_ROOT_PI`(`ROOT_PROC_INST_ID_` ASC) USING BTREE,
                                     INDEX `ACT_IDX_EXEC_BUSKEY`(`BUSINESS_KEY_` ASC) USING BTREE,
                                     INDEX `ACT_IDX_EXEC_TENANT_ID`(`TENANT_ID_` ASC) USING BTREE,
                                     INDEX `ACT_FK_EXE_PROCINST`(`PROC_INST_ID_` ASC) USING BTREE,
                                     INDEX `ACT_FK_EXE_PARENT`(`PARENT_ID_` ASC) USING BTREE,
                                     INDEX `ACT_FK_EXE_SUPER`(`SUPER_EXEC_` ASC) USING BTREE,
                                     INDEX `ACT_FK_EXE_PROCDEF`(`PROC_DEF_ID_` ASC) USING BTREE,
                                     CONSTRAINT `ACT_FK_EXE_PARENT` FOREIGN KEY (`PARENT_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                     CONSTRAINT `ACT_FK_EXE_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                     CONSTRAINT `ACT_FK_EXE_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE,
                                     CONSTRAINT `ACT_FK_EXE_SUPER` FOREIGN KEY (`SUPER_EXEC_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_ru_ext_task
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_ext_task`;
CREATE TABLE `act_ru_ext_task`  (
                                    `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                    `REV_` int NOT NULL,
                                    `WORKER_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `TOPIC_NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `RETRIES_` int NULL DEFAULT NULL,
                                    `ERROR_MSG_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `ERROR_DETAILS_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `LOCK_EXP_TIME_` datetime NULL DEFAULT NULL,
                                    `SUSPENSION_STATE_` int NULL DEFAULT NULL,
                                    `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `PROC_DEF_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `ACT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `ACT_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `PRIORITY_` bigint NOT NULL DEFAULT 0,
                                    `LAST_FAILURE_LOG_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    PRIMARY KEY (`ID_`) USING BTREE,
                                    INDEX `ACT_IDX_EXT_TASK_TOPIC`(`TOPIC_NAME_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_EXT_TASK_TENANT_ID`(`TENANT_ID_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_EXT_TASK_PRIORITY`(`PRIORITY_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_EXT_TASK_ERR_DETAILS`(`ERROR_DETAILS_ID_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_EXT_TASK_EXEC`(`EXECUTION_ID_` ASC) USING BTREE,
                                    CONSTRAINT `ACT_FK_EXT_TASK_ERROR_DETAILS` FOREIGN KEY (`ERROR_DETAILS_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                    CONSTRAINT `ACT_FK_EXT_TASK_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_ru_filter
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_filter`;
CREATE TABLE `act_ru_filter`  (
                                  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                  `REV_` int NOT NULL,
                                  `RESOURCE_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                  `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                  `OWNER_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `QUERY_` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                  `PROPERTIES_` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL,
                                  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_ru_identitylink
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_identitylink`;
CREATE TABLE `act_ru_identitylink`  (
                                        `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                        `REV_` int NULL DEFAULT NULL,
                                        `GROUP_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `USER_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                        PRIMARY KEY (`ID_`) USING BTREE,
                                        INDEX `ACT_IDX_IDENT_LNK_USER`(`USER_ID_` ASC) USING BTREE,
                                        INDEX `ACT_IDX_IDENT_LNK_GROUP`(`GROUP_ID_` ASC) USING BTREE,
                                        INDEX `ACT_IDX_ATHRZ_PROCEDEF`(`PROC_DEF_ID_` ASC) USING BTREE,
                                        INDEX `ACT_FK_TSKASS_TASK`(`TASK_ID_` ASC) USING BTREE,
                                        CONSTRAINT `ACT_FK_ATHRZ_PROCEDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                        CONSTRAINT `ACT_FK_TSKASS_TASK` FOREIGN KEY (`TASK_ID_`) REFERENCES `act_ru_task` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_ru_incident
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_incident`;
CREATE TABLE `act_ru_incident`  (
                                    `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                    `REV_` int NOT NULL,
                                    `INCIDENT_TIMESTAMP_` datetime NOT NULL,
                                    `INCIDENT_MSG_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `INCIDENT_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                    `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `ACTIVITY_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `FAILED_ACTIVITY_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `CAUSE_INCIDENT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `ROOT_CAUSE_INCIDENT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `CONFIGURATION_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `JOB_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `ANNOTATION_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    PRIMARY KEY (`ID_`) USING BTREE,
                                    INDEX `ACT_IDX_INC_CONFIGURATION`(`CONFIGURATION_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_INC_TENANT_ID`(`TENANT_ID_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_INC_JOB_DEF`(`JOB_DEF_ID_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_INC_CAUSEINCID`(`CAUSE_INCIDENT_ID_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_INC_EXID`(`EXECUTION_ID_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_INC_PROCDEFID`(`PROC_DEF_ID_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_INC_PROCINSTID`(`PROC_INST_ID_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_INC_ROOTCAUSEINCID`(`ROOT_CAUSE_INCIDENT_ID_` ASC) USING BTREE,
                                    CONSTRAINT `ACT_FK_INC_CAUSE` FOREIGN KEY (`CAUSE_INCIDENT_ID_`) REFERENCES `act_ru_incident` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE,
                                    CONSTRAINT `ACT_FK_INC_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                    CONSTRAINT `ACT_FK_INC_JOB_DEF` FOREIGN KEY (`JOB_DEF_ID_`) REFERENCES `act_ru_jobdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                    CONSTRAINT `ACT_FK_INC_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                    CONSTRAINT `ACT_FK_INC_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                    CONSTRAINT `ACT_FK_INC_RCAUSE` FOREIGN KEY (`ROOT_CAUSE_INCIDENT_ID_`) REFERENCES `act_ru_incident` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_ru_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_job`;
CREATE TABLE `act_ru_job`  (
                               `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                               `REV_` int NULL DEFAULT NULL,
                               `TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                               `LOCK_EXP_TIME_` datetime NULL DEFAULT NULL,
                               `LOCK_OWNER_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                               `EXCLUSIVE_` tinyint(1) NULL DEFAULT NULL,
                               `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                               `PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                               `PROCESS_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                               `PROCESS_DEF_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                               `RETRIES_` int NULL DEFAULT NULL,
                               `EXCEPTION_STACK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                               `EXCEPTION_MSG_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                               `FAILED_ACT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                               `DUEDATE_` datetime NULL DEFAULT NULL,
                               `REPEAT_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                               `REPEAT_OFFSET_` bigint NULL DEFAULT 0,
                               `HANDLER_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                               `HANDLER_CFG_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                               `DEPLOYMENT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                               `SUSPENSION_STATE_` int NOT NULL DEFAULT 1,
                               `JOB_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                               `PRIORITY_` bigint NOT NULL DEFAULT 0,
                               `SEQUENCE_COUNTER_` bigint NULL DEFAULT NULL,
                               `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                               `CREATE_TIME_` datetime NULL DEFAULT NULL,
                               `LAST_FAILURE_LOG_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                               PRIMARY KEY (`ID_`) USING BTREE,
                               INDEX `ACT_IDX_JOB_EXECUTION_ID`(`EXECUTION_ID_` ASC) USING BTREE,
                               INDEX `ACT_IDX_JOB_HANDLER`(`HANDLER_TYPE_`(100) ASC, `HANDLER_CFG_`(155) ASC) USING BTREE,
                               INDEX `ACT_IDX_JOB_PROCINST`(`PROCESS_INSTANCE_ID_` ASC) USING BTREE,
                               INDEX `ACT_IDX_JOB_TENANT_ID`(`TENANT_ID_` ASC) USING BTREE,
                               INDEX `ACT_IDX_JOB_JOB_DEF_ID`(`JOB_DEF_ID_` ASC) USING BTREE,
                               INDEX `ACT_FK_JOB_EXCEPTION`(`EXCEPTION_STACK_ID_` ASC) USING BTREE,
                               INDEX `ACT_IDX_JOB_HANDLER_TYPE`(`HANDLER_TYPE_` ASC) USING BTREE,
                               CONSTRAINT `ACT_FK_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_ru_jobdef
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_jobdef`;
CREATE TABLE `act_ru_jobdef`  (
                                  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                  `REV_` int NULL DEFAULT NULL,
                                  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `PROC_DEF_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `ACT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `JOB_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                  `JOB_CONFIGURATION_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `SUSPENSION_STATE_` int NULL DEFAULT NULL,
                                  `JOB_PRIORITY_` bigint NULL DEFAULT NULL,
                                  `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  `DEPLOYMENT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                  PRIMARY KEY (`ID_`) USING BTREE,
                                  INDEX `ACT_IDX_JOBDEF_TENANT_ID`(`TENANT_ID_` ASC) USING BTREE,
                                  INDEX `ACT_IDX_JOBDEF_PROC_DEF_ID`(`PROC_DEF_ID_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_ru_meter_log
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_meter_log`;
CREATE TABLE `act_ru_meter_log`  (
                                     `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                     `NAME_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                     `REPORTER_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                     `VALUE_` bigint NULL DEFAULT NULL,
                                     `TIMESTAMP_` datetime NULL DEFAULT NULL,
                                     `MILLISECONDS_` bigint NULL DEFAULT 0,
                                     PRIMARY KEY (`ID_`) USING BTREE,
                                     INDEX `ACT_IDX_METER_LOG_MS`(`MILLISECONDS_` ASC) USING BTREE,
                                     INDEX `ACT_IDX_METER_LOG_NAME_MS`(`NAME_` ASC, `MILLISECONDS_` ASC) USING BTREE,
                                     INDEX `ACT_IDX_METER_LOG_REPORT`(`NAME_` ASC, `REPORTER_` ASC, `MILLISECONDS_` ASC) USING BTREE,
                                     INDEX `ACT_IDX_METER_LOG_TIME`(`TIMESTAMP_` ASC) USING BTREE,
                                     INDEX `ACT_IDX_METER_LOG`(`NAME_` ASC, `TIMESTAMP_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_ru_task
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_task`;
CREATE TABLE `act_ru_task`  (
                                `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                `REV_` int NULL DEFAULT NULL,
                                `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                `CASE_EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                `CASE_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                `CASE_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                `PARENT_TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                `DESCRIPTION_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                `TASK_DEF_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                `OWNER_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                `ASSIGNEE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                `DELEGATION_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                `PRIORITY_` int NULL DEFAULT NULL,
                                `CREATE_TIME_` datetime NULL DEFAULT NULL,
                                `LAST_UPDATED_` datetime NULL DEFAULT NULL,
                                `DUE_DATE_` datetime NULL DEFAULT NULL,
                                `FOLLOW_UP_DATE_` datetime NULL DEFAULT NULL,
                                `SUSPENSION_STATE_` int NULL DEFAULT NULL,
                                `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                PRIMARY KEY (`ID_`) USING BTREE,
                                INDEX `ACT_IDX_TASK_CREATE`(`CREATE_TIME_` ASC) USING BTREE,
                                INDEX `ACT_IDX_TASK_LAST_UPDATED`(`LAST_UPDATED_` ASC) USING BTREE,
                                INDEX `ACT_IDX_TASK_ASSIGNEE`(`ASSIGNEE_` ASC) USING BTREE,
                                INDEX `ACT_IDX_TASK_OWNER`(`OWNER_` ASC) USING BTREE,
                                INDEX `ACT_IDX_TASK_TENANT_ID`(`TENANT_ID_` ASC) USING BTREE,
                                INDEX `ACT_FK_TASK_EXE`(`EXECUTION_ID_` ASC) USING BTREE,
                                INDEX `ACT_FK_TASK_PROCINST`(`PROC_INST_ID_` ASC) USING BTREE,
                                INDEX `ACT_FK_TASK_PROCDEF`(`PROC_DEF_ID_` ASC) USING BTREE,
                                CONSTRAINT `ACT_FK_TASK_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                CONSTRAINT `ACT_FK_TASK_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                CONSTRAINT `ACT_FK_TASK_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_ru_task_meter_log
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_task_meter_log`;
CREATE TABLE `act_ru_task_meter_log`  (
                                          `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                          `ASSIGNEE_HASH_` bigint NULL DEFAULT NULL,
                                          `TIMESTAMP_` datetime NULL DEFAULT NULL,
                                          PRIMARY KEY (`ID_`) USING BTREE,
                                          INDEX `ACT_IDX_TASK_METER_LOG_TIME`(`TIMESTAMP_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for act_ru_variable
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_variable`;
CREATE TABLE `act_ru_variable`  (
                                    `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                    `REV_` int NULL DEFAULT NULL,
                                    `TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                    `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                    `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `CASE_EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `CASE_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `BATCH_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `BYTEARRAY_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `DOUBLE_` double NULL DEFAULT NULL,
                                    `LONG_` bigint NULL DEFAULT NULL,
                                    `TEXT_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `TEXT2_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    `VAR_SCOPE_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                                    `SEQUENCE_COUNTER_` bigint NULL DEFAULT NULL,
                                    `IS_CONCURRENT_LOCAL_` tinyint NULL DEFAULT NULL,
                                    `TENANT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
                                    PRIMARY KEY (`ID_`) USING BTREE,
                                    UNIQUE INDEX `ACT_UNIQ_VARIABLE`(`VAR_SCOPE_` ASC, `NAME_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_VARIABLE_TASK_ID`(`TASK_ID_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_VARIABLE_TENANT_ID`(`TENANT_ID_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_VARIABLE_TASK_NAME_TYPE`(`TASK_ID_` ASC, `NAME_` ASC, `TYPE_` ASC) USING BTREE,
                                    INDEX `ACT_FK_VAR_EXE`(`EXECUTION_ID_` ASC) USING BTREE,
                                    INDEX `ACT_FK_VAR_PROCINST`(`PROC_INST_ID_` ASC) USING BTREE,
                                    INDEX `ACT_FK_VAR_BYTEARRAY`(`BYTEARRAY_ID_` ASC) USING BTREE,
                                    INDEX `ACT_IDX_BATCH_ID`(`BATCH_ID_` ASC) USING BTREE,
                                    CONSTRAINT `ACT_FK_VAR_BATCH` FOREIGN KEY (`BATCH_ID_`) REFERENCES `act_ru_batch` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                    CONSTRAINT `ACT_FK_VAR_BYTEARRAY` FOREIGN KEY (`BYTEARRAY_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                    CONSTRAINT `ACT_FK_VAR_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                    CONSTRAINT `ACT_FK_VAR_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for auth_third_user
-- ----------------------------
DROP TABLE IF EXISTS `auth_third_user`;
CREATE TABLE `auth_third_user`  (
                                    `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                    `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                    `THIRD_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '三方用户id',
                                    `USER_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统用户id',
                                    `AVATAR` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '头像',
                                    `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
                                    `NICKNAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
                                    `GENDER` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
                                    `CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类',
                                    `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                    `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                    `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                    `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                    `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                    `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                                    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '三方用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for biz_notice
-- ----------------------------
DROP TABLE IF EXISTS `biz_notice`;
CREATE TABLE `biz_notice`  (
                               `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
                               `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                               `TITLE` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
                               `IMAGE` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面图',
                               `CONTENT` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
                               `DIGEST` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '摘要',
                               `TYPE` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型',
                               `PLACE` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发布位置',
                               `STATUS` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
                               `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序',
                               `REMARK` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
                               `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                               `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                               `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                               `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                               `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '更新时间',
                               `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新用户',
                               PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '通知公告' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for chuang_feng_esl_key
-- ----------------------------
DROP TABLE IF EXISTS `chuang_feng_esl_key`;
CREATE TABLE `chuang_feng_esl_key`  (
                                        `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                        `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                        `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
                                        `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                        `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                        `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                        `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                        `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                        `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                                        `STORE_CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '门店编码',
                                        `AP_SN` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'AP编码',
                                        `ESL_SN` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子价签编号',
                                        `ESL_SERVER_KEY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子价签serverkey',
                                        `ESL_TAG_KEY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子价签tagkey',
                                        PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for client_relation
-- ----------------------------
DROP TABLE IF EXISTS `client_relation`;
CREATE TABLE `client_relation`  (
                                    `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                    `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                    `OBJECT_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对象ID',
                                    `TARGET_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目标ID',
                                    `CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类',
                                    `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '关系' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for client_user
-- ----------------------------
DROP TABLE IF EXISTS `client_user`;
CREATE TABLE `client_user`  (
                                `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                `AVATAR` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '头像',
                                `SIGNATURE` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '签名',
                                `ACCOUNT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账号',
                                `PASSWORD` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
                                `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
                                `NICKNAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
                                `GENDER` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
                                `AGE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年龄',
                                `BIRTHDAY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '出生日期',
                                `NATION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '民族',
                                `NATIVE_PLACE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '籍贯',
                                `HOME_ADDRESS` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '家庭住址',
                                `MAILING_ADDRESS` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '通信地址',
                                `ID_CARD_TYPE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件类型',
                                `ID_CARD_NUMBER` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件号码',
                                `CULTURE_LEVEL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文化程度',
                                `POLITICAL_OUTLOOK` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '政治面貌',
                                `COLLEGE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '毕业院校',
                                `EDUCATION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学历',
                                `EDU_LENGTH` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学制',
                                `DEGREE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学位',
                                `PHONE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机',
                                `EMAIL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
                                `HOME_TEL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '家庭电话',
                                `OFFICE_TEL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '办公电话',
                                `EMERGENCY_CONTACT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '紧急联系人',
                                `EMERGENCY_PHONE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '紧急联系人电话',
                                `EMERGENCY_ADDRESS` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '紧急联系人地址',
                                `LAST_LOGIN_IP` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上次登录ip',
                                `LAST_LOGIN_ADDRESS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上次登录地点',
                                `LAST_LOGIN_TIME` datetime NULL DEFAULT NULL COMMENT '上次登录时间',
                                `LAST_LOGIN_DEVICE` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '上次登录设备',
                                `LATEST_LOGIN_IP` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最新登录ip',
                                `LATEST_LOGIN_ADDRESS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最新登录地点',
                                `LATEST_LOGIN_TIME` datetime NULL DEFAULT NULL COMMENT '最新登录时间',
                                `LATEST_LOGIN_DEVICE` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '最新登录设备',
                                `USER_STATUS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户状态',
                                `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
                                `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                                PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'C端用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for dev_config
-- ----------------------------
DROP TABLE IF EXISTS `dev_config`;
CREATE TABLE `dev_config`  (
                               `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                               `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                               `CONFIG_KEY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置键',
                               `CONFIG_VALUE` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '配置值',
                               `CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类',
                               `REMARK` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
                               `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
                               `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                               `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                               `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                               `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                               `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                               `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                               PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for dev_dfc
-- ----------------------------
DROP TABLE IF EXISTS `dev_dfc`;
CREATE TABLE `dev_dfc`  (
                            `ID` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '主键',
                            `TENANT_ID` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '租户id',
                            `DBS_ID` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '数据源',
                            `TABLE_NAME` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '表名称',
                            `COLUMN_NAME` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '字段名称',
                            `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
                            `NAME` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '表单域属性名',
                            `LABEL` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标签文本',
                            `REQUIRED` tinyint(1) NULL DEFAULT NULL COMMENT '必填',
                            `PLACEHOLDER` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '提示语',
                            `TYPE` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '类型',
                            `SELECT_OPTION_TYPE` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '选择项类型',
                            `DICT_TYPE_CODE` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '字典',
                            `SEL_OPTION_API_URL` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '选择项api地址',
                            `SEL_DATA_API_URL` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '已选择数据api地址',
                            `IS_MULTIPLE` tinyint(1) NULL DEFAULT NULL COMMENT '是否多选',
                            `STATUS` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '状态',
                            `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                            `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                            `CREATE_USER` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                            `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '更新时间',
                            `UPDATE_USER` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '更新用户',
                            PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '动态字段配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for dev_dict
-- ----------------------------
DROP TABLE IF EXISTS `dev_dict`;
CREATE TABLE `dev_dict`  (
                             `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                             `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                             `PARENT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父id',
                             `DICT_LABEL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典文字',
                             `DICT_VALUE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典值',
                             `CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类',
                             `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
                             `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                             `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                             `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                             `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                             `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                             `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                             PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for dev_email
-- ----------------------------
DROP TABLE IF EXISTS `dev_email`;
CREATE TABLE `dev_email`  (
                              `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                              `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                              `ENGINE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮件引擎',
                              `SEND_ACCOUNT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发件人邮箱',
                              `SEND_USER` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发件人昵称',
                              `RECEIVE_ACCOUNTS` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '接收人',
                              `SUBJECT` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '邮件主题',
                              `CONTENT` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '邮件正文',
                              `TAG_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签名',
                              `TEMPLATE_NAME` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '模板名',
                              `TEMPLATE_PARAM` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '发送参数',
                              `RECEIPT_INFO` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '回执信息',
                              `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                              `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                              `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                              `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                              `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                              `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                              PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '邮件' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for dev_file
-- ----------------------------
DROP TABLE IF EXISTS `dev_file`;
CREATE TABLE `dev_file`  (
                             `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                             `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                             `ENGINE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '存储引擎',
                             `BUCKET` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '存储桶',
                             `NAME` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文件名称',
                             `SUFFIX` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件后缀',
                             `SIZE_KB` bigint NULL DEFAULT NULL COMMENT '文件大小kb',
                             `SIZE_INFO` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件大小（格式化后）',
                             `OBJ_NAME` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文件的对象名（唯一名称）',
                             `STORAGE_PATH` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文件存储路径',
                             `DOWNLOAD_PATH` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文件下载路径',
                             `THUMBNAIL` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '图片缩略图',
                             `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                             `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                             `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                             `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                             `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                             `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                             PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for dev_job
-- ----------------------------
DROP TABLE IF EXISTS `dev_job`;
CREATE TABLE `dev_job`  (
                            `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                            `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                            `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
                            `CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
                            `CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类',
                            `ACTION_CLASS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务类名',
                            `CRON_EXPRESSION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'cron表达式',
                            `JOB_STATUS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务状态',
                            `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
                            `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                            `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                            `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                            `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                            `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                            `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                            PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for dev_log
-- ----------------------------
DROP TABLE IF EXISTS `dev_log`;
CREATE TABLE `dev_log`  (
                            `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
                            `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                            `CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志分类',
                            `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志名称',
                            `EXE_STATUS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行状态',
                            `EXE_MESSAGE` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '具体消息',
                            `OP_IP` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作ip',
                            `OP_ADDRESS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作地址',
                            `OP_BROWSER` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作浏览器',
                            `OP_OS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
                            `CLASS_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类名称',
                            `METHOD_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法名称',
                            `REQ_METHOD` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方式',
                            `REQ_URL` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求地址',
                            `PARAM_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求参数',
                            `RESULT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '返回结果',
                            `OP_TIME` datetime NULL DEFAULT NULL COMMENT '操作时间',
                            `OP_USER` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人姓名',
                            `SIGN_DATA` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '签名数据',
                            `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                            `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                            `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                            `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                            PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for dev_message
-- ----------------------------
DROP TABLE IF EXISTS `dev_message`;
CREATE TABLE `dev_message`  (
                                `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                `CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类',
                                `SUBJECT` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '主题',
                                `CONTENT` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '正文',
                                `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                                PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '站内信' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for dev_relation
-- ----------------------------
DROP TABLE IF EXISTS `dev_relation`;
CREATE TABLE `dev_relation`  (
                                 `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                 `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                 `OBJECT_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对象ID',
                                 `TARGET_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目标ID',
                                 `CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类',
                                 `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                 PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '关系' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for dev_slideshow
-- ----------------------------
DROP TABLE IF EXISTS `dev_slideshow`;
CREATE TABLE `dev_slideshow`  (
                                  `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
                                  `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                  `TITLE` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
                                  `PLACE` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '展示位置',
                                  `IMAGE` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片',
                                  `PATH_DETAILS` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路径详情',
                                  `STATUS` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
                                  `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序',
                                  `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                  `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                  `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                  `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                  `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                  `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新用户',
                                  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '轮播图' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for dev_sms
-- ----------------------------
DROP TABLE IF EXISTS `dev_sms`;
CREATE TABLE `dev_sms`  (
                            `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                            `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                            `ENGINE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短信引擎',
                            `PHONE_NUMBERS` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '手机号',
                            `SIGN_NAME` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '短信签名',
                            `TEMPLATE_CODE` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '模板编码',
                            `TEMPLATE_PARAM` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '发送参数',
                            `RECEIPT_INFO` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '回执信息',
                            `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                            `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                            `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                            `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                            `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                            `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                            PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '短信' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for esl_brand
-- ----------------------------
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
-- Table structure for esl_field_definition
-- ----------------------------
DROP TABLE IF EXISTS `esl_field_definition`;
CREATE TABLE `esl_field_definition`  (
                                         `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                         `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                         `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
                                         `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                         `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                         `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                         `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                         `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                         `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                                         `FIELD_CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段编码',
                                         `FIELD_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
                                         `DATA_TYPE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据类型(\'STRING\',\'NUMBER\',\'DATETIME\',\'CURRENCY\')',
                                         `SOURCE_TABLE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'PANDA_PRODUCT' COMMENT '来源表',
                                         `SOURCE_COLUMN` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来源字段',
                                         `DEFAULT_VALUE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '默认值',
                                         PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '价签字段定义表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for esl_model
-- ----------------------------
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
-- Table structure for ext_database
-- ----------------------------
DROP TABLE IF EXISTS `ext_database`;
CREATE TABLE `ext_database`  (
                                 `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                 `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                 `POOL_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
                                 `URL` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '连接URL',
                                 `USERNAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
                                 `PASSWORD` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '密码',
                                 `DRIVER_NAME` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '驱动名称',
                                 `CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类',
                                 `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
                                 `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                 `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                 `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                 `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                 `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                 `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                                 PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据源' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ext_dfc
-- ----------------------------
DROP TABLE IF EXISTS `ext_dfc`;
CREATE TABLE `ext_dfc`  (
                            `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
                            `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                            `DBS_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据源',
                            `TABLE_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表名称',
                            `COLUMN_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
                            `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
                            `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表单域属性名',
                            `LABEL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签文本',
                            `REQUIRED` tinyint(1) NULL DEFAULT NULL COMMENT '必填',
                            `PLACEHOLDER` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提示语',
                            `TYPE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型',
                            `SELECT_OPTION_TYPE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '选择项类型',
                            `DICT_TYPE_CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典',
                            `SEL_OPTION_API_URL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '选择项api地址',
                            `SEL_DATA_API_URL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '已选择数据api地址',
                            `IS_MULTIPLE` tinyint(1) NULL DEFAULT NULL COMMENT '是否多选',
                            `STATUS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
                            `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                            `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                            `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                            `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '更新时间',
                            `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新用户',
                            PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '动态字段' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ext_tenant
-- ----------------------------
DROP TABLE IF EXISTS `ext_tenant`;
CREATE TABLE `ext_tenant`  (
                               `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                               `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                               `DBS_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据源id',
                               `DBS_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据源名称',
                               `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
                               `CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
                               `DOMAIN` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '绑定域名',
                               `CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类',
                               `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
                               `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                               `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                               `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                               `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                               `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                               `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                               PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '租户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ext_urp
-- ----------------------------
DROP TABLE IF EXISTS `ext_urp`;
CREATE TABLE `ext_urp`  (
                            `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                            `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                            `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
                            `CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
                            `REPORT_XML` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '报表XML',
                            `CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类',
                            `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
                            `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                            `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                            `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                            `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                            `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                            `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                            PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '报表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for gen_basic
-- ----------------------------
DROP TABLE IF EXISTS `gen_basic`;
CREATE TABLE `gen_basic`  (
                              `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
                              `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                              `DBS_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据源',
                              `SUB_DATABASE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否分库',
                              `DBS_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据源名称',
                              `PLUGIN_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '插件名',
                              `MODULE_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块名',
                              `DB_TABLE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主表',
                              `DB_TABLE_KEY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主表主键',
                              `TABLE_PREFIX` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '移除表前缀',
                              `GENERATE_TYPE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成方式',
                              `MODULE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属模块',
                              `MENU_PID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上级目录',
                              `MOBILE_MODULE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '移动端所属模块',
                              `FUNCTION_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '功能名',
                              `BUS_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务名',
                              `CLASS_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类名',
                              `FORM_LAYOUT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表单布局',
                              `GRID_WHETHER` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '使用栅格',
                              `PACKAGE_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '包名',
                              `AUTHOR_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作者',
                              `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序',
                              `DFC_WHETHER` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '使用动态字段',
                              `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                              `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                              `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                              `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                              `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                              PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '生成基础' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for gen_config
-- ----------------------------
DROP TABLE IF EXISTS `gen_config`;
CREATE TABLE `gen_config`  (
                               `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
                               `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                               `BASIC_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '基础ID',
                               `IS_TABLE_KEY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否主键',
                               `FIELD_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段',
                               `FIELD_REMARK` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
                               `FIELD_TYPE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型',
                               `FIELD_JAVA_TYPE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '实体类型',
                               `EFFECT_TYPE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作用类型',
                               `DICT_TYPE_CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典',
                               `WHETHER_TABLE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '列表显示',
                               `WHETHER_RETRACT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '列省略',
                               `WHETHER_ADD_UPDATE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否增改',
                               `WHETHER_REQUIRED` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '必填',
                               `QUERY_WHETHER` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '查询',
                               `QUERY_TYPE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '查询方式',
                               `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序',
                               `DELETE_FLAG` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                               `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                               `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                               `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                               `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                               PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '生成配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for mobile_resource
-- ----------------------------
DROP TABLE IF EXISTS `mobile_resource`;
CREATE TABLE `mobile_resource`  (
                                    `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
                                    `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                    `PARENT_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父ID',
                                    `TITLE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
                                    `CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
                                    `CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类',
                                    `MODULE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块',
                                    `MENU_TYPE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单类型',
                                    `PATH` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路径',
                                    `ICON` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
                                    `COLOR` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '颜色',
                                    `REG_TYPE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规则类型',
                                    `STATUS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '可用状态',
                                    `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
                                    `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                    `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                    `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                    `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                    `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                    `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                                    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '移动资源' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for mqtt_connection_config
-- ----------------------------
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
-- Table structure for panda_access_point
-- ----------------------------
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
-- Table structure for pay_order
-- ----------------------------
DROP TABLE IF EXISTS `pay_order`;
CREATE TABLE `pay_order`  (
                              `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                              `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                              `OUT_TRADE_NO` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户订单号',
                              `TRADE_NO` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付平台订单号',
                              `SUBJECT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单标题',
                              `BODY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单描述',
                              `ORDER_AMOUNT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单金额',
                              `PAY_USER_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '买家id',
                              `PAY_ACCOUNT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '买家账号',
                              `PAY_AMOUNT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付金额',
                              `PAY_PLATFORM` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付平台',
                              `PAY_STATUS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付状态',
                              `PAY_TIME` datetime NULL DEFAULT NULL COMMENT '支付时间',
                              `HAS_REFUND` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退款标志',
                              `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                              `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                              `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                              `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                              `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                              PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pay_order_details
-- ----------------------------
DROP TABLE IF EXISTS `pay_order_details`;
CREATE TABLE `pay_order_details`  (
                                      `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
                                      `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                      `ORDER_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单id',
                                      `PRODUCT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品id',
                                      `PRODUCT_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品名称',
                                      `PRODUCT_AMOUNT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品价格',
                                      `PRODUCT_COUNT` int NULL DEFAULT NULL COMMENT '产品数量',
                                      `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                      `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                      `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                      `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                      `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                                      PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单明细' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pay_order_refund
-- ----------------------------
DROP TABLE IF EXISTS `pay_order_refund`;
CREATE TABLE `pay_order_refund`  (
                                     `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                     `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                     `ORDER_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单id',
                                     `OUT_TRADE_NO` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户订单号',
                                     `TRADE_NO` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付平台退款单号',
                                     `REFUND_NO` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户退款单号',
                                     `REFUND_USER_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退款到买家id',
                                     `REFUND_ACCOUNT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退款到买家账号',
                                     `REFUND_AMOUNT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退款金额',
                                     `REFUND_STATUS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退款状态',
                                     `REFUND_TIME` datetime NULL DEFAULT NULL COMMENT '退款时间',
                                     `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                     `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                     `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                     `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                     `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                                     PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单退款' ROW_FORMAT = DYNAMIC;

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
-- Table structure for product_esl_binding
-- ----------------------------
DROP TABLE IF EXISTS `product_esl_binding`;
CREATE TABLE `product_esl_binding`  (
                                        `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                        `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                        `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
                                        `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                        `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                        `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                        `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                        `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                        `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                                        `ESL_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子价签ID',
                                        `PRODUCT_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品ID',
                                        `BIND_STATUS` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'A' COMMENT '绑定状态(A=有效,I=无效)',
                                        `BIND_TIME` datetime NULL DEFAULT NULL COMMENT '绑定时间',
                                        `UNBIND_TIME` datetime NULL DEFAULT NULL COMMENT '解绑时间',
                                        `BINDING_TYPE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'PERMANENT' COMMENT '绑定类型(PERMANENT/TEMPORARY)',
                                        `EXPIRY_DATE` datetime NULL DEFAULT NULL COMMENT '临时绑定到期时间',
                                        PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品-电子价签绑定关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group`  (
                              `ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
                              `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                              `NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
                              `REMARK` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
                              `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
                              `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                              `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                              `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                              `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                              `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                              `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                              PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户组' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org`  (
                            `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                            `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                            `PARENT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父id',
                            `DIRECTOR_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主管ID',
                            `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
                            `CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
                            `CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类',
                            `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
                            `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                            `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                            `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                            `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                            `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                            `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                            PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '组织' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_position
-- ----------------------------
DROP TABLE IF EXISTS `sys_position`;
CREATE TABLE `sys_position`  (
                                 `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                 `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                 `ORG_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织id',
                                 `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
                                 `CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
                                 `CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类',
                                 `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
                                 `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                 `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                 `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                 `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                 `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                 `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                                 PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '职位' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_relation`;
CREATE TABLE `sys_relation`  (
                                 `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                 `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                 `OBJECT_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对象ID',
                                 `TARGET_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目标ID',
                                 `CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类',
                                 `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                 PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '关系' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource`  (
                                 `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                 `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                 `PARENT_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父id',
                                 `TITLE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
                                 `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '别名',
                                 `CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
                                 `CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类',
                                 `MODULE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块',
                                 `MENU_TYPE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单类型',
                                 `PATH` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '路径',
                                 `COMPONENT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件',
                                 `ICON` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
                                 `COLOR` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '颜色',
                                 `VISIBLE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否显示',
                                 `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
                                 `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                 `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                 `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                 `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                 `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                 `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                                 PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
                             `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                             `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                             `ORG_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织id',
                             `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
                             `CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
                             `CATEGORY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类',
                             `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
                             `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                             `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                             `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                             `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                             `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                             `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                             PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
                             `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                             `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                             `AVATAR` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '头像',
                             `SIGNATURE` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '签名',
                             `ACCOUNT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账号',
                             `PASSWORD` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
                             `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
                             `NICKNAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
                             `GENDER` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
                             `AGE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年龄',
                             `BIRTHDAY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '出生日期',
                             `NATION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '民族',
                             `NATIVE_PLACE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '籍贯',
                             `HOME_ADDRESS` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '家庭住址',
                             `MAILING_ADDRESS` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '通信地址',
                             `ID_CARD_TYPE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件类型',
                             `ID_CARD_NUMBER` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件号码',
                             `CULTURE_LEVEL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文化程度',
                             `POLITICAL_OUTLOOK` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '政治面貌',
                             `COLLEGE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '毕业院校',
                             `EDUCATION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学历',
                             `EDU_LENGTH` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学制',
                             `DEGREE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学位',
                             `PHONE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机',
                             `EMAIL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
                             `HOME_TEL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '家庭电话',
                             `OFFICE_TEL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '办公电话',
                             `EMERGENCY_CONTACT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '紧急联系人',
                             `EMERGENCY_PHONE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '紧急联系人电话',
                             `EMERGENCY_ADDRESS` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '紧急联系人地址',
                             `EMP_NO` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '员工编号',
                             `ENTRY_DATE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '入职日期',
                             `ORG_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机构id',
                             `POSITION_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职位id',
                             `POSITION_LEVEL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职级',
                             `DIRECTOR_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主管id',
                             `POSITION_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '兼任信息',
                             `LAST_LOGIN_IP` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上次登录ip',
                             `LAST_LOGIN_ADDRESS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上次登录地点',
                             `LAST_LOGIN_TIME` datetime NULL DEFAULT NULL COMMENT '上次登录时间',
                             `LAST_LOGIN_DEVICE` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '上次登录设备',
                             `LATEST_LOGIN_IP` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最新登录ip',
                             `LATEST_LOGIN_ADDRESS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最新登录地点',
                             `LATEST_LOGIN_TIME` datetime NULL DEFAULT NULL COMMENT '最新登录时间',
                             `LATEST_LOGIN_DEVICE` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '最新登录设备',
                             `USER_STATUS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户状态',
                             `SORT_CODE` int NULL DEFAULT NULL COMMENT '排序码',
                             `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                             `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                             `CREATE_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                             `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                             `UPDATE_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                             `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                             PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
