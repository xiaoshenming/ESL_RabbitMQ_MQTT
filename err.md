

curl 'http://localhost:8999/api/res/templ/refresh' \
  -H 'Accept-Language: zh-CN,zh;q=0.9' \
  -H 'Connection: keep-alive' \
  -H 'Content-Type: application/json' \
  -b 'JSESSIONID=0E8E4A998F89E4B26D1089508B74F0B6' \
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
  --data-raw $'{\n  "eslId": "1947223724923940865",\n  "storeCode": "0002",\n  "brandCode": "PANDA",\n  "forceRefresh": false\n}'


curl 'http://localhost:8999/api/res/templ/refresh' \
  -H 'Accept-Language: zh-CN,zh;q=0.9' \
  -H 'Connection: keep-alive' \
  -H 'Content-Type: application/json' \
  -b 'JSESSIONID=0E8E4A998F89E4B26D1089508B74F0B6' \
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
  --data-raw $'{\n  "eslId": "1947313501023105026",\n  "storeCode": "0002",\n  "brandCode": "PANDA",\n  "forceRefresh": false\n}'

Topic: esl/server/data/0002QoS: 0
{"command":"wtag","data":[{"tag":6597069773146,"tmpl":"2","model":"06","checksum":"12780dc2f9234a1406c5853435f7296a","forcefrash":1,"value":{"GOODS_CODE":"001","GOODS_NAME":"测试商品","F_1":99.99,"F_2":"测试分类","F_3":9.99,"F_4":"PRODUCT_FRUIT","F_5":99.9,"F_6":99.0,"F_7":0.09,"F_8":99.0,"F_9":"测试材质","F_10":"http://localhost:82/dev/file/download?id=1947222476036370434&Domain=http://localhost:81","F_11":"测试产地","F_20":"<p>测试描述</p>","F_12":"ge","F_13":9.0,"F_14":"测试状态","F_32":99,"QRCODE":"www.baidu.com"},"taskid":74473,"token":406445}],"id":"1947223724923940865","timestamp":1.753320474474E9,"shop":"0002"}
2025-07-24 09:27:54:827

Topic: esl/server/data/0002QoS: 0
{"command":"wtag","data":[{"tag":30786325581835,"tmpl":"3","model":"1C","checksum":"12780dc2f9234a1406c5853435f7296a","forcefrash":1,"value":{"GOODS_CODE":"002","GOODS_NAME":"002","F_1":2.0,"F_2":"002","F_3":2.0,"F_4":"PRODUCT_FRUIT","F_5":2.0,"F_6":2.0,"F_7":2.0,"F_8":2.0,"F_9":"002","F_10":"http://localhost:82/dev/file/download?id=1947312644269404161&Domain=http://localhost:81","F_11":"002","F_20":"<p>002</p>","F_12":"ge","F_13":2.0,"F_14":"002","F_32":2,"QRCODE":"002"},"taskid":76312,"token":118628}],"id":"1947313501023105026","timestamp":1.753320476312E9,"shop":"0002"}


并且。我的数据库esl_brand_field_mapping表目前只有
BRAND_CODE,TEMPLATE_FIELD,FIELD_CODE,FORMAT_RULE
攀攀	code	PRODUCT_ID	商品名称
攀攀	name	PRODUCT_NAME	商品名称
攀攀	F_01	PRODUCT_RETAIL_PRICE	价格
攀攀	F_02	PRODUCT_CATEGORY	商品分类
攀攀	F_03	PRODUCT_COST_PRICE	商品成本价
攀攀	F_04	PRODUCT_SPECIFICATION	商品规格
攀攀	F_05	PRODUCT_MEMBERSHIP_PRICE	商品会员价
攀攀	F_06	PRODUCT_DISCOUNT_PRICE	商品折扣价
攀攀	F_07	PRODUCT_DISCOUNT	商品折扣（例如：0.8 表示8折）
攀攀	F_08	PRODUCT_WHOLESALE_PRICE	商品批发
攀攀	F_09	PRODUCT_MATERIAL	商品材质
攀攀	F_10	PRODUCT_IMAGE	商品图片（路径或URL）
攀攀	F_11	PRODUCT_ORIGIN	商品产地
攀攀	F_20	PRODUCT_DESCRIPTION	商品描述
攀攀	F_12	PRODUCT_UNIT	商品单位（如：个、件、瓶等）
攀攀	F_13	PRODUCT_WEIGHT	产品重量（单位：kg）
攀攀	F_14	PRODUCT_STATUS	商品状态（如：上架、下架、预售等）
攀攀	F_32	PRODUCT_STOCK	商品库存
攀攀	QRCODE	PRODUCT_QRCODE	二维码
攀攀	QRCODE	PRODUCT_BARCODE	条形码

为什么实际输出的字段残缺了。请修复！