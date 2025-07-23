这是标准mqtt消息格式
esl/server/data/0002
{
  "command": "wtag",
  "data": [
    {
      "tag": 6597069773146,
      "tmpl": "2",
      "model": "6",
      "checksum": "4b9114d3b97bb55263b7f08b5a022c51",
      "forcefrash": 1,
      "value": {
        "F_9": "测试材质",
        "F_29": null,
        "GOODS_CODE": "001",
        "QRCODE": "www.baidu.com",
        "GOODS_NAME": "测试商品",
        "F_31": null,
        "F_30": null,
        "F_11": "测试产地",
        "F_10": "ge",
        "F_32": 99,
        "F_13": 9.0,
        "F_12": "ge",
        "F_15": null,
        "F_14": "测试状态",
        "F_17": null,
        "F_16": null,
        "F_19": null,
        "F_18": null,
        "F_20": "<p>测试描述</p>",
        "F_2": "测试分类",
        "F_22": null,
        "F_1": 99.99,
        "F_21": null,
        "F_4": "PRODUCT_FRUIT",
        "F_24": null,
        "F_3": 9.99,
        "F_23": null,
        "F_6": "攀攀",
        "F_26": null,
        "F_5": 99.9,
        "F_25": null,
        "F_8": 99.0,
        "F_28": null,
        "F_7": 0.09,
        "F_27": null
      },
      "taskid": 57789,
      "token": 623273
    }
  ],
  "id": "677dc360-ed89-48ad-aa9f-84e1d11f1fff",
  "timestamp": 1.753111757784e9,
  "shop": "0002"
}

这是你合成的消息
Topic: esl/server/data/0002QoS: 0
{"shop":"0002","data":[{"tmpl":"1946122678071738370","forcefrash":1,"checksum":"12780dc2f9234a1406c5853435f7296a","model":"213","tag":1947223724923940865,"value":{"PRICE":"9.99","GOODS_NAME":"测试商品","CATEGORY":"测试分类","UNIT_PRICE":"9.99","GOODS_CODE":"001","SPECIFICATION":"PRODUCT_FRUIT","PRODUCT_NAME":"测试商品","PRODUCT_CODE":"001","BRAND":"攀攀"},"taskid":95674,"token":5200}],"id":"1947223724923940865","command":"wtag","timestamp":1753278195}
但是这是你合成的消息，有问题，请修复。内容都错了。
curl 'http://localhost:8999/api/res/templ/refresh' \
  -H 'Accept-Language: zh-CN,zh;q=0.9' \
  -H 'Connection: keep-alive' \
  -H 'Content-Type: application/json' \
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


  记住他的标准格式是
  {
  "command": "wtag",
  "data": [
    {
      "tag": 6597069773146,
      "tmpl": "2",
      "model": "6",
      "checksum": "4b9114d3b97bb55263b7f08b5a022c51",
      "forcefrash": 1,
      "value": {
        "F_9": "测试材质",
        "F_29": null,
        "GOODS_CODE": "001",
        "QRCODE": "www.baidu.com",
        "GOODS_NAME": "测试商品",
        "F_31": null,
        "F_30": null,
        "F_11": "测试产地",
        "F_10": "ge",
        "F_32": 99,
        "F_13": 9.0,
        "F_12": "ge",
        "F_15": null,
        "F_14": "测试状态",
        "F_17": null,
        "F_16": null,
        "F_19": null,
        "F_18": null,
        "F_20": "<p>测试描述</p>",
        "F_2": "测试分类",
        "F_22": null,
        "F_1": 99.99,
        "F_21": null,
        "F_4": "PRODUCT_FRUIT",
        "F_24": null,
        "F_3": 9.99,
        "F_23": null,
        "F_6": "攀攀",
        "F_26": null,
        "F_5": 99.9,
        "F_25": null,
        "F_8": 99.0,
        "F_28": null,
        "F_7": 0.09,
        "F_27": null
      },
      "taskid": 57789,
      "token": 623273
    }
  ],
  "id": "677dc360-ed89-48ad-aa9f-84e1d11f1fff",
  "timestamp": 1.753111757784e9,
  "shop": "0002"
}

字段映射通过查找
esl_brand_field_mapping来获取

TEMPLATE_FIELD FIELD_CODE
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

这是数据库的部分数据。
一定要按照这个格式，这个是panda的格式。请发挥你的想象力，写出你认为最完美的答案。

