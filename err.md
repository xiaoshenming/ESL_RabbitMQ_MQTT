{
  "shop": "009",
  "data": {
    "tmpls": [
      {
        "name": "6666.json",
        "id": "1945045387689762818",
        "md5": "86692b562eafb1ab6dd9eb1145033f0d"
      }
    ],
    "url": "http://10.3.36.36:8999/api/res/templ/loadtemple",
    "tid": "396a5189-53d8-4354-bcfa-27d57d9d69ad"
  },
  "id": "062d1ab8-1fa7-43d5-b421-0f0e3a8e6497",
  "command": "tmpllist",
  "timestamp": 1752583536
}

这是你现在发送的结果已经很不错了，但是还差一点点，那就是类型！
数据样例：
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
在name里面"name": "TAG_1C.json",
他的_后面的 1C就是类型。

1. 从价签型号转换获取
屏幕类型通过价签的型号（model）进行转换获取。在  文件中定义了 modelconvert 字典，用于将价签的硬件型号转换为对应的屏幕类型代码：
modelconvert = {
    '30': "01",  # 对应 ESL213R
    '32': "32",
    '33': "29",
    '36': "04",
    '39': "39",
    '3A': "07",  # 对应 ESL420R
    '5B': "5B",
    '3D': "08",
    '3F': "3F",
    '54': "54",
    '40': "0A",
    '43': "0A",
    '44': "0F",
}
2. 存储在数据库中
屏幕类型作为 stype 字段存储在 nsb_esl_temple_info 模板信息表中，与模板名称、尺寸等信息一起保存。
3. 模板文件命名规范
模板文件按照 {模板名称}_{屏幕类型}.json 的格式命名，例如：
● 213R.json - 对应屏幕类型 "01"
● 420R.json - 对应屏幕类型 "07" 
● 750R.json - 对应屏幕类型 "09"
目前支持的屏幕类型
根据模板文件和代码分析，目前系统支持以下屏幕类型：
屏幕类型代码	模板文件名	屏幕尺寸	价签型号	描述
"01"	213R.json	212×104	ESL213R	2.13寸红黑白屏幕
"07"	420R.json	400×300	ESL420R	4.2寸红黑白屏幕
"09"	750R.json	640×384	ESL750R	7.5寸红黑白屏幕
"04"	-	-	-	通过型号'36'映射
"08"	-	-	-	通过型号'3D'映射
"0A"	-	-	-	通过型号'40'/'43'映射
"0F"	-	-	-	通过型号'44'映射
"29"	-	-	-	通过型号'33'映射
"32"	-	-	-	通过型号'32'映射
"39"	-	-	-	通过型号'39'映射
"3F"	-	-	-	通过型号'3F'映射
"54"	-	-	-	通过型号'54'映射
"5B"	-	-	-	通过型号'5B'映射
屏幕类型的使用流程
1. 价签注册时：根据价签的硬件型号通过 modelconvert 字典转换为屏幕类型代码
2. 模板创建时：在  中将屏幕类型作为 stype 字段存入数据库
3. 模板文件生成时：按照 {模板名称}_{屏幕类型}.json 格式生成模板文件
4. 价签更新时：根据价签绑定的商品模板和屏幕类型，选择对应的模板文件进行显示
目前系统主要支持三种常用的电子价签屏幕：2.13寸、4.2寸和7.5寸的红黑白三色屏幕，其他屏幕类型代码可能用于扩展或特殊用途。



这是数据库的数据
1945045387689762818	-1	6666	xw5XoeRSPl	{"panels":[{"index":0,"name":"打印模板","paperType":"A7","height":105,"width":74,"paperHeader":0,"paperFooter":297.6377952755906,"printElements":[{"options":{"left":27,"top":72,"height":25.5,"width":50,"title":"ID","field":"id","testData":"ID","fontWeight":"400","textAlign":"center","textContentVerticalAlign":"middle","hideTitle":true},"printElementType":{"title":"ID","type":"text"}},{"options":{"left":103.5,"top":99,"height":25.5,"width":50,"title":"门店编码","field":"storeCode","testData":"门店编码","fontWeight":"400","textAlign":"center","textContentVerticalAlign":"middle","hideTitle":true},"printElementType":{"title":"门店编码","type":"text"}},{"options":{"left":34.5,"top":175.5,"height":25.5,"width":50,"title":"商品名称","field":"productName","testData":"商品名称","fontWeight":"400","textAlign":"center","textContentVerticalAlign":"middle","hideTitle":true},"printElementType":{"title":"商品名称","type":"text"}},{"options":{"left":99,"top":219,"height":25.5,"width":50,"title":"商品描述","field":"productDescription","testData":"这是默认的商品描述","fontWeight":"400","textAlign":"center","textContentVerticalAlign":"middle","hideTitle":true},"printElementType":{"title":"商品描述","type":"text"}}],"paperNumberContinue":true,"watermarkOptions":{}}]}	MINI_TAG	DESIGN	99	{"paperFormat":"A7","hiprintConfig":"{\n  \"panels\": [\n    {\n      \"index\": 0,\n      \"name\": \"打印模板\",\n      \"paperType\": \"A7\",\n      \"width\": 74,\n      \"height\": 105,\n      \"paperHeader\": 0,\n      \"paperFooter\": 297.6377952755906,\n      \"printElements\": [],\n      \"paperNumberContinue\": true\n    }\n  ]\n}"}	NOT_DELETE	2025-07-15 16:58:52	1543837863788879871		

你要通过CONTENT和EXT_JSON的数据去合成出来类型然后添加在mqtt的消息里面去，不然因为类型不对会导致下载错误。
然后，这是一个简单的模板U_06.json
{"Items":[{"Background":"Red","BorderColor":"Transparent","BorderStyle":0,"DataDefault":"","DataKey":"","DataKeyStyle":0,"FontColor":"Black","FontFamily":"微软雅黑","FontSize":12,"FontSpace":0,"FontStyle":0,"Location":"11, 13","Size":"49, 57","TextAlign":0,"Type":"text","height":57,"width":49,"x":11,"y":13},{"Background":"Black","BorderColor":"Transparent","BorderStyle":0,"DataDefault":"","DataKey":"","DataKeyStyle":0,"FontColor":"Black","FontFamily":"微软雅黑","FontSize":12,"FontSpace":0,"FontStyle":0,"Location":"69, 14","Size":"51, 55","TextAlign":0,"Type":"text","height":55,"width":51,"x":69,"y":14},{"Background":"Transparent","BorderColor":"Black","BorderStyle":2,"DataDefault":"","DataKey":"","DataKeyStyle":0,"FontColor":"Black","FontFamily":"微软雅黑","FontSize":12,"FontSpace":0,"FontStyle":0,"Location":"129, 14","Size":"55, 54","TextAlign":0,"Type":"text","height":54,"width":55,"x":129,"y":14},{"Background":"Transparent","BorderColor":"Transparent","BorderStyle":0,"DataDefault":"text","DataKey":"GOODS_CODE","DataKeyStyle":0,"FontColor":"Black","FontFamily":"Zfull-GB","FontSize":14,"FontSpace":0,"FontStyle":0,"Location":"40, 80","Size":"142, 32","TextAlign":1,"Type":"text","height":32,"width":142,"x":40,"y":80},{"Background":"Transparent","BorderColor":"Transparent","BorderStyle":0,"DataDefault":"text","DataKey":"F_5","DataKeyStyle":0,"FontColor":"Black","FontFamily":"Zfull-GB","FontSize":12,"FontSpace":0,"FontStyle":0,"Location":"27, 64","Size":"80, 40","TextAlign":0,"Type":"text","height":40,"width":80,"x":27,"y":64}],"Name":"U","Size":"250, 122","TagType":"06","Version":10,"height":"122","hext":"6","rgb":"3","wext":"0","width":"250"}

二进制流的格式为模板所示。