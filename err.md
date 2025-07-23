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


Topic: esl/0002/dataQoS: 0
{"shop":"0002","data":[{"tmpl":"{\n  \"Items\": [\n    {\n      \"Type\": \"Text\",\n      \"X\": 10,\n      \"Y\": 10,\n      \"Width\": 200,\n      \"Height\": 30,\n      \"FontFamily\": \"阿里普惠\",\n      \"FontSize\": 16,\n      \"Text\": \"{{GOODS_NAME}}\"\n    },\n    {\n      \"Type\": \"Text\",\n      \"X\": 10,\n      \"Y\": 50,\n      \"Width\": 100,\n      \"Height\": 25,\n      \"FontFamily\": \"阿里普惠\",\n      \"FontSize\": 14,\n      \"Text\": \"￥{{PRICE}}\"\n    }\n  ]\n}","forcefrash":1,"checksum":"12780dc2f9234a1406c5853435f7296a","model":"2.13","tag":1947223724923940865,"value":{"PRICE":9.99,"GOODS_NAME":"测试商品","CATEGORY":"测试分类","GOODS_CODE":"001","SPECIFICATION":"PRODUCT_FRUIT","BRAND":"攀攀"},"taskid":1,"token":1}],"id":"1947223724923940865","command":"wtag","timestamp":1.753274901112E9}
但是这是你合成的消息，有问题，请修复。
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


  记住他的标注格式是
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


一定要按照这个格式，这个是panda的格式。请发挥你的想象力，写出你认为最完美的答案。


并且！
curl 'http://localhost:8999/api/res/templ/send' \
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
  --data-raw $'{\n  "templateId": "1946122678071738370",\n  "storeCode": "0002"\n}'
  这个接口也要增加brandCode字段来区分是攀攀还是雅量。目前雅量的暂不实现。目前写成功的是panda的案例。