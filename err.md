Topic: esl/server/data/0002QoS: 0
{"command":"wtag","data":[{"tag":6597069773146,"tmpl":"2","model":"06","checksum":"12780dc2f9234a1406c5853435f7296a","forcefrash":1,"value":{"GOODS_CODE":"001","GOODS_NAME":"测试商品","F_1":99.99,"F_2":"测试分类","F_3":9.99,"F_4":"PRODUCT_FRUIT","F_5":99.9,"F_6":99.0,"F_7":0.09,"F_8":99.0,"F_9":"测试材质","F_10":"http://localhost:82/dev/file/download?id=1947222476036370434&Domain=http://localhost:81","F_11":"测试产地","F_20":"<p>测试描述</p>","F_12":"ge","F_13":9.0,"F_14":"测试状态","F_32":99,"QRCODE":"www.baidu.com"},"taskid":56843,"token":603932}],"id":"1947223724923940865","timestamp":1.753321556843E9,"shop":"0002"}
2025-07-24 09:45:56:846
这是预计回参。如果3秒没收到，也算完成。进行下一条的队列处理
Topic: esl/ap/ack/ESLAP00000053 QoS: 0
{"shop":"0002", "ap":"ESLAP00000053","id":"1947223724923940865", "ts":1753321556773}



Topic: esl/server/data/0002QoS: 0
{"shop":"0002","data":{"tmpls":[{"name":"2_06.json","id":"1946122678071738370","md5":"f87de7d8df5f25f4b5fe3eb740ae161e"}],"url":"http://10.3.36.36:8999/api/res/templ/loadtemple","tid":"537b2fdb-54e3-4e76-ad95-cf4ede6e1e77"},"id":"6e02d239-907e-4bef-9b6c-5b0ccc15f89a","command":"tmpllist","timestamp":1753321609}
2025-07-24 09:46:49:071
这两条是预计回参（用或来处理）。如果3秒没收到，也算完成。进行下一条的队列处理
Topic: esl/ap/report/download/0002 QoS: 0
{ "shop":"0002", "ap":"ESLAP00000053", "id":"1946122678071738370","file":"2_06.json", "rt":1}
2025-07-24 09:46:50:040

Topic: esl/ap/ack/ESLAP00000053 QoS: 0
{"cmd":"tmpllist","code":1,"id":"6e02d239-907e-4bef-9b6c-5b0ccc15f89a","timestamp":"2025-07-24 09:46:48","error":"","tagid":{}}