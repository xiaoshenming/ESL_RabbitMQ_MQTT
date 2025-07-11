SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for platform_exchange
-- ----------------------------
DROP TABLE IF EXISTS `platform_exchange`;
CREATE TABLE `platform_exchange`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `chuangfeng_template` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `platform_template` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `yaliang_template` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for template_exchange
-- ----------------------------
DROP TABLE IF EXISTS `template_exchange`;
CREATE TABLE `template_exchange`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `chuangfeng_template` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

以上是数据库的原本结构template

但是我换成了新的结构叫做company
# mysql
url=mysql://10.3.36.25:3306/company?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true&useInformationSchema=true&rewriteBatchedStatements=true
username=panda
password=panda@123
strict=true

# rabbitmq
host=10.3.36.15
port=5672
username=panda
password=panda@123


# MQTT配置
url=tcp://10.3.36.25:1883
client-id=data_server_1
首先/templ/loadtemple保留给ap做下载用。然后，其他的api以及其方法全都删除!保留最干净的结果。
然后。我要做一个数据监听处理的程序，用来给中台服务提供支持，中台是一个控制电子价签的系统，他有的作用只有增删改查，他会指定对某个电子价签进行刷新模板的数据，那么这个模板就要在我这里实现数据的处理进行更新等。我这里要做的就是接收命令，Rabbitmq队列处理发送mqtt，接收回参传递回RabbitMq队列，前端就能查看任务完成情况了。这个程序主要做的是mqtt的监听并且接收api服务，

已知模板遵顼一套定义规则。
{
  "panels": [
    {
      "index": 0,
      "name": "11111",
      "paperType": "A6",
      "height": 148,
      "width": 105,
      "paperHeader": 0,
      "paperFooter": 419.52755905511816,
      "printElements": [],
      "paperNumberContinue": true,
      "watermarkOptions": {}
    }
  ]
}
这是空模板的json数据

{
  "panels": [
    {
      "index": 0,
      "name": "11111",
      "paperType": "A6",
      "height": 148,
      "width": 105,
      "paperHeader": 0,
      "paperFooter": 419.52755905511816,
      "printElements": [
        {
          "options": {
            "left": 28.5,
            "top": 66,
            "height": 25.5,
            "width": 50,
            "title": "ID",
            "field": "id",
            "testData": "ID",
            "fontWeight": "400",
            "textAlign": "center",
            "textContentVerticalAlign": "middle",
            "hideTitle": true,
            "coordinateSync": false,
            "widthHeightSync": false,
            "fontFamily": "SimSun",
            "qrCodeLevel": 0
          },
          "printElementType": {
            "title": "ID",
            "type": "text"
          }
        },
        {
          "options": {
            "left": 115.5,
            "top": 69,
            "height": 25.5,
            "width": 50,
            "title": "租户ID",
            "field": "tenantId",
            "testData": "租户ID",
            "fontWeight": "400",
            "textAlign": "center",
            "textContentVerticalAlign": "middle",
            "hideTitle": true
          },
          "printElementType": {
            "title": "租户ID",
            "type": "text"
          }
        },
        {
          "options": {
            "left": 19.5,
            "top": 135,
            "height": 25.5,
            "width": 50,
            "title": "商品编号",
            "field": "productId",
            "testData": "商品编号",
            "fontWeight": "400",
            "textAlign": "center",
            "textContentVerticalAlign": "middle",
            "hideTitle": true,
            "coordinateSync": false,
            "widthHeightSync": false,
            "fontFamily": "Microsoft YaHei",
            "qrCodeLevel": 0
          },
          "printElementType": {
            "title": "商品编号",
            "type": "text"
          }
        },
        {
          "options": {
            "left": 99,
            "top": 132,
            "height": 25.5,
            "width": 50,
            "title": "门店编码",
            "field": "storeCode",
            "testData": "门店编码",
            "fontWeight": "400",
            "textAlign": "center",
            "textContentVerticalAlign": "middle",
            "hideTitle": true
          },
          "printElementType": {
            "title": "门店编码",
            "type": "text"
          }
        },
        {
          "options": {
            "left": 15,
            "top": 262.5,
            "height": 25.5,
            "width": 50,
            "title": "商品库存",
            "field": "productStock",
            "testData": "100",
            "fontWeight": "400",
            "textAlign": "center",
            "textContentVerticalAlign": "middle",
            "hideTitle": true
          },
          "printElementType": {
            "title": "商品库存",
            "type": "text"
          }
        },
        {
          "options": {
            "left": 94.5,
            "top": 259.5,
            "height": 25.5,
            "width": 100,
            "src": "http://placeholder.com/image",
            "title": "商品图片",
            "field": "productImage",
            "testData": "http://placeholder.com/image",
            "fontWeight": "400",
            "textAlign": "center",
            "textContentVerticalAlign": "middle",
            "hideTitle": true
          },
          "printElementType": {
            "title": "商品图片",
            "type": "image"
          }
        }
      ],
      "paperNumberContinue": true,
      "watermarkOptions": {}
    }
  ]
}
这是我的模板有数据内容


然后！这是官方模板！
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




举个例子，选中一个esl选择刷新，云端选择使用某个api对这个电子价签刷新内容。
是去看panda_esl这张表，拿到BOUND_PRODUCT_ID->商品id,
联表查询panda_product表查看商品信息,拿到ESL_TEMPLATE_ID->电子价签模板id,(以及各个商品信息和门店编码id)
联表查询act_ext_template_print表的CONTENT数据，获得模板,
然后将我的数据库的模板转型为电子价签的模板,然后弄成一个api的推流结果，二进制流推送出去。
目前这个项目里面的/templ/loadtemple就是最好的结果，他就是最完美的最准去的推流。
以下是规则。通过这个方法下发模板。
mqtt发送:
## MQTT消息规则
### 订阅主题规则
主题：esl/server/data/{shop}
```
{

 "command": "tmpllist",

 "data": {

  "url": "http://esl.openesl.cn/api/res/templ/loadtemple",

  "tmpls": [{

   "name": "TAG_1C.json",

   "md5": "a9734007c15984557e4e0aa3db4d73c5",

   "id": "06b05ec7-ac31-44e2-85c1-cf35b182c7d2"

  }],

  "tid": "396a5189-53d8-4354-bcfa-27d57d9d69ad"

 },

 "id": "7ba34d3f-1dab-4c19-926d-e66b60a0bb3e",

 "timestamp": 1706308012,

 "shop": "ZH01"

}
```
### 字段说明
**模板操作** ：command=tmpllist

| **参数名** | **必选** | **类型**   | **说明**  |
| ------- | ------ | -------- | ------- |
| url     | 是      | string   | 模版更新接口  |
| tid     | 是      | string   | 多租户id   |
| tmpls   | 是      | String[] | 更新的模版列表 |
| name    | 是      | string   | 模版文件名   |
| md5     | 是      | string   | 模板md5校验 |
| id      | 是      | string   | 模板id    |

唯一要注意的一点是所有的FontFamily都改成"FontFamily": "Zfull-GB"默认字体，避免字体显示不出来.
 "shop": "ZH01"是商品号码



然后再发送mqtt
wtag 命令通过MQTT主题 esl/server/data/{shop} 发送，其数据结构如下：

```
{
  "id": "uuid",
  "timestamp": 1234567890,
  "shop": "门店代码",
  "command": "wtag",
  "data": [
    {
      "tag": "价签SN",
      "tmpl": "模板名称",
      "model": 价签型号(16进制转10进制),
      "checksum": "校验和",
      "forcefrash": 是否强制刷新,
      "value": {
        "字段名": "字段值",
        ...
      },
      "taskid": "任务ID",
      "token": "令牌"
    }
  ]
}


举例:
{
	"command": "wtag",
	"data": [{
		"tag": 6597069770841,
		"tmpl": "PRICEPROMO",
		"model": 6,
		"checksum": "b3359abd8cd0c923afe88f539e750871",
		"forcefrash": 1,
		"value": {
			"GOODS_NAME": " \u8109\u52a8 \u7ef4\u751f\u7d20\u996e\u6599\u9752\u67e0\u53e3\u5473 600ML",
			"GOODS_CODE": "6902538004045",
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
	"shop": "ZH01"
}


就大概是写这么一个功能，能下发模板，然后用数据库字段转官方格式来更新（发送mqtt）。
用两条api来调用。一条/templ/loadtemple保留给ap做下载用。然后，其他的api以及其方法全都删除!保留最干净的结果。
最主要作用是用来给设备下发模板以及，设置绑定字段。大致是这么个想法。能帮我写一份更好的技术大纲来告诉要我具体要怎么实现吗？