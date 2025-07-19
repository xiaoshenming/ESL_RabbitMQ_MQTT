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
  --data-raw $'{\n  "templateId": "1946122678071738370",\n  "storeCode": "009"\n}'

  模板下发请求已接收

{"shop":"009","data":{"tmpls":[{"name":"2_1C.json","id":"1946122678071738370","md5":"9510036df017e67e34ab85f1ee6ccdac"}],"url":"http://10.3.36.36:8999/api/res/templ/loadtemple","tid":"396a5189-53d8-4354-bcfa-27d57d9d69ad"},"id":"3f632c77-4695-43b6-b4aa-71fab656d096","command":"tmpllist","timestamp":1752926428}
2025-07-19 20:00:28:844

Topic: esl/ap/report/download/009QoS: 0
{ "shop":"009", "ap":"ESLAP00000053", "id":"1946122678071738370","file":"2_1C", "rt":404}
2025-07-19 20:00:30:033

Topic: esl/ap/ack/ESLAP00000053QoS: 0
{"cmd":"tmpllist","code":1,"id":"3f632c77-4695-43b6-b4aa-71fab656d096","timestamp":"2025-07-19 20:00:28","error":"","tagid":{}}

发现404报错。然后测试接口，发现这个即可没了？？？
请修复

curl 'http://localhost:8999/api/res/templ/loadtemple' \
  -H 'Accept-Language: zh-CN,zh;q=0.9' \
  -H 'Connection: keep-alive' \
  -H 'Content-Type: application/json' \
  -H 'Origin: http://localhost:8999' \
  -H 'Referer: http://localhost:8999/swagger-ui/index.html' \
  -H 'Sec-Fetch-Dest: empty' \
  -H 'Sec-Fetch-Mode: cors' \
  -H 'Sec-Fetch-Site: same-origin' \
  -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36 Edg/139.0.0.0' \
  -H 'accept: application/octet-stream' \
  -H 'sec-ch-ua: "Not;A=Brand";v="99", "Microsoft Edge";v="139", "Chromium";v="139"' \
  -H 'sec-ch-ua-mobile: ?0' \
  -H 'sec-ch-ua-platform: "Windows"' \
  --data-raw $'{\n  "id": "1946122678071738370",\n  "name": "2_1C.json"\n}'