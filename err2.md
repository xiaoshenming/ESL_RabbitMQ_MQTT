Topic: esl/server/data/0002QoS: 0
{"command":"wtag","data":[{"tag":6597069773146,"tmpl":"1946122678071738370","model":6,"checksum":"b56c0f28428067768582b023852b9fea","forcefrash":1,"value":{"F_19":null,"F_9":"测试产地","F_18":null,"GOODS_CODE":"001","QRCODE":"001","GOODS_NAME":"测试商品","F_20":99,"F_2":"测试分类","F_11":"www.baidu.com","F_1":99.99,"F_10":"ge","F_4":99.90,"F_13":null,"F_3":9.99,"F_12":null,"F_6":"攀攀","F_15":null,"F_5":99.00,"F_14":null,"F_8":"<p>测试描述</p>","F_17":null,"F_7":"PRODUCT_FRUIT","F_16":null},"taskid":48636,"token":1066998}],"id":"746a5073-675a-4483-98ba-5f764cc93a18","timestamp":1.753108248636E9,"shop":"0002"}
这是你最终的输出
但是！！！

{
  "command": "wtag",
  "data": [
    {
      "tag": 6597069773146,
      "tmpl": "2",
      "model": "6",
      "checksum": "b56c0f28428067768582b023852b9fea",
      "forcefrash": 1,
      "value": {
        "F_19": null,
        "F_9": "测试产地",
        "F_18": null,
        "GOODS_CODE": "001",
        "QRCODE": "001",
        "GOODS_NAME": "测试商品",
        "F_20": 99,
        "F_2": "测试分类",
        "F_11": "www.baidu.com",
        "F_1": 99.99,
        "F_10": "ge",
        "F_4": 99.9,
        "F_13": null,
        "F_3": 9.99,
        "F_12": null,
        "F_6": "攀攀",
        "F_15": null,
        "F_5": 99.0,
        "F_14": null,
        "F_8": "<p>测试描述</p>",
        "F_17": null,
        "F_7": "PRODUCT_FRUIT",
        "F_16": null
      },
      "taskid": 369812,
      "token": 1018282
    }
  ],
  "id": "55e3ea5c-f256-4105-b877-631009dd17b6",
  "timestamp": 1.753105936981e9,
  "shop": "0002"
}

这才是理论上的标准输出，其中差别在于model这里是字符串
tmpl这里是模板的name