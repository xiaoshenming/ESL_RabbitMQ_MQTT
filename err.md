
接口用
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
  --data-raw $'{\n  "id": "1946122678071738370",\n  "name": "2_06.json",\n  "storeCode": "0002"\n}'
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
  --data-raw $'{\n  "id": "1946122678071738370",\n  "name": "2_06.json"\n}'
  都对，但是你输出的下载文件名字应该叫做2_06.json.是通过计算模板的转化的来的
  然后这是你的文件数据
  {
  "panels": [
    {
      "index": 0,
      "name": "2",
      "paperType": "CUSTOM",
      "width": 250,
      "height": 122,
      "paperHeader": 0,
      "paperFooter": 345.82677165354335,
      "printElements": [],
      "paperNumberContinue": true,
      "eslConfig": {
        "screenType": "2.13T",
        "pixelWidth": 250,
        "pixelHeight": 122,
        "colorMode": {
          "black": true,
          "white": true,
          "red": true,
          "yellow": false
        },
        "orientation": "LANDSCAPE"
      }
    }
  ]
}
错误的。


{
  "Items": [
    {
      "Background": "Transparent",
      "BorderColor": "Black",
      "BorderStyle": 1,
      "DataDefault": "",
      "DataKey": "",
      "DataKeyStyle": 0,
      "FontColor": "Black",
      "FontFamily": "Zfull-GB",
      "FontSize": 12,
      "FontSpace": 0,
      "FontStyle": 0,
      "Location": "9, 0",
      "Size": "226, 115",
      "TextAlign": 0,
      "Type": "rect",
      "height": 115,
      "width": 226,
      "x": 9,
      "y": 0
    },
    {
      "Background": "Transparent",
      "BorderColor": "Black",
      "BorderStyle": 1,
      "DataDefault": "",
      "DataKey": "",
      "DataKeyStyle": 0,
      "FontColor": "Black",
      "FontFamily": "Zfull-GB",
      "FontSize": 12,
      "FontSpace": 0,
      "FontStyle": 0,
      "Location": "3, 0",
      "Size": "31, 31",
      "TextAlign": 0,
      "Type": "oval",
      "height": 31,
      "width": 31,
      "x": 3,
      "y": 0
    },
    {
      "Background": "Transparent",
      "BorderColor": "Transparent",
      "BorderStyle": 0,
      "DataDefault": "P001",
      "DataKey": "code",
      "DataKeyStyle": 0,
      "FontColor": "Black",
      "FontFamily": "Zfull-GB",
      "FontSize": 12,
      "FontSpace": 0,
      "FontStyle": 0,
      "Location": "31, 2",
      "Size": "41, 9",
      "TextAlign": 1,
      "Type": "text",
      "height": 9,
      "width": 41,
      "x": 31,
      "y": 2
    },
    {
      "Background": "Transparent",
      "BorderColor": "Black",
      "BorderStyle": 1,
      "DataDefault": "",
      "DataKey": "",
      "DataKeyStyle": 0,
      "FontColor": "Black",
      "FontFamily": "Zfull-GB",
      "FontSize": 12,
      "FontSpace": 0,
      "FontStyle": 0,
      "Location": "210, 2",
      "Size": "31, 31",
      "TextAlign": 0,
      "Type": "oval",
      "height": 31,
      "width": 31,
      "x": 210,
      "y": 2
    },
    {
      "Background": "Transparent",
      "BorderColor": "Transparent",
      "BorderStyle": 0,
      "DataDefault": "100.00",
      "DataKey": "F_05",
      "DataKeyStyle": 0,
      "FontColor": "Black",
      "FontFamily": "Zfull-GB",
      "FontSize": 12,
      "FontSpace": 0,
      "FontStyle": 0,
      "Location": "184, 3",
      "Size": "27, 9",
      "TextAlign": 1,
      "Type": "text",
      "height": 9,
      "width": 27,
      "x": 184,
      "y": 3
    },
    {
      "Background": "Transparent",
      "Barformat": 0,
      "Barheight": 14.5,
      "Bartype": "code128",
      "Barwidth": 1,
      "BorderColor": "Transparent",
      "BorderStyle": 0,
      "DataDefault": "1234567890123",
      "DataKey": "QRCODE",
      "DataKeyStyle": 0,
      "FontColor": "Black",
      "FontFamily": "Zfull-GB",
      "FontSize": 12,
      "FontSpace": 0,
      "FontStyle": 0,
      "Fontinval": 1,
      "Location": "24, 11",
      "Showtext": 1,
      "Size": "102, 29",
      "TextAlign": 1,
      "Type": "barcode",
      "height": 29,
      "width": 102,
      "x": 24,
      "y": 11
    },
    {
      "Background": "Transparent",
      "BorderColor": "Transparent",
      "BorderStyle": 0,
      "DataDefault": "https://example.com",
      "DataKey": "QRCODE",
      "DataKeyStyle": 0,
      "FontColor": "Black",
      "FontFamily": "Zfull-GB",
      "FontSize": 12,
      "FontSpace": 0,
      "FontStyle": 0,
      "Location": "143, 24",
      "Size": "86, 86",
      "TextAlign": 1,
      "Type": "qrcode",
      "height": 86,
      "width": 86,
      "x": 143,
      "y": 24
    },
    {
      "Background": "Transparent",
      "BorderColor": "Transparent",
      "BorderStyle": 0,
      "DataDefault": "100.00",
      "DataKey": "F_06",
      "DataKeyStyle": 0,
      "FontColor": "Black",
      "FontFamily": "Zfull-GB",
      "FontSize": 12,
      "FontSpace": 0,
      "FontStyle": 0,
      "Location": "207, 34",
      "Size": "27, 9",
      "TextAlign": 1,
      "Type": "text",
      "height": 9,
      "width": 27,
      "x": 207,
      "y": 34
    },
    {
      "Background": "Transparent",
      "BorderColor": "Transparent",
      "BorderStyle": 0,
      "DataDefault": "示例商品名称",
      "DataKey": "name",
      "DataKeyStyle": 0,
      "FontColor": "Black",
      "FontFamily": "Zfull-GB",
      "FontSize": 12,
      "FontSpace": 0,
      "FontStyle": 0,
      "Location": "11, 36",
      "Size": "41, 9",
      "TextAlign": 1,
      "Type": "text",
      "height": 9,
      "width": 41,
      "x": 11,
      "y": 36
    },
    {
      "Background": "Transparent",
      "BorderColor": "Transparent",
      "BorderStyle": 0,
      "DataDefault": "500g",
      "DataKey": "F_04",
      "DataKeyStyle": 0,
      "FontColor": "Black",
      "FontFamily": "阿里普惠",
      "FontSize": 12,
      "FontSpace": 0,
      "FontStyle": 0,
      "Location": "108, 54",
      "Size": "41, 9",
      "TextAlign": 1,
      "Type": "text",
      "height": 9,
      "width": 41,
      "x": 108,
      "y": 54
    },
    {
      "Background": "Transparent",
      "BorderColor": "Transparent",
      "BorderStyle": 0,
      "DataDefault": "食品饮料",
      "DataKey": "F_02",
      "DataKeyStyle": 0,
      "FontColor": "Black",
      "FontFamily": "Zfull-GB",
      "FontSize": 12,
      "FontSpace": 0,
      "FontStyle": 0,
      "Location": "5, 75",
      "Size": "41, 9",
      "TextAlign": 1,
      "Type": "text",
      "height": 9,
      "width": 41,
      "x": 5,
      "y": 75
    },
    {
      "Background": "Transparent",
      "BorderColor": "Transparent",
      "BorderStyle": 0,
      "DataDefault": "0.8",
      "DataKey": "F_07",
      "DataKeyStyle": 0,
      "FontColor": "Black",
      "FontFamily": "Zfull-GB",
      "FontSize": 12,
      "FontSpace": 0,
      "FontStyle": 0,
      "Location": "203, 77",
      "Size": "41, 9",
      "TextAlign": 1,
      "Type": "text",
      "height": 9,
      "width": 41,
      "x": 203,
      "y": 77
    },
    {
      "Background": "Transparent",
      "BorderColor": "Black",
      "BorderStyle": 1,
      "DataDefault": "",
      "DataKey": "",
      "DataKeyStyle": 0,
      "FontColor": "Black",
      "FontFamily": "Zfull-GB",
      "FontSize": 12,
      "FontSpace": 0,
      "FontStyle": 0,
      "Location": "0, 84",
      "Size": "31, 31",
      "TextAlign": 0,
      "Type": "oval",
      "height": 31,
      "width": 31,
      "x": 0,
      "y": 84
    },
    {
      "Background": "Transparent",
      "BorderColor": "Black",
      "BorderStyle": 1,
      "DataDefault": "",
      "DataKey": "",
      "DataKeyStyle": 0,
      "FontColor": "Black",
      "FontFamily": "Zfull-GB",
      "FontSize": 12,
      "FontSpace": 0,
      "FontStyle": 0,
      "Location": "210, 85",
      "Size": "31, 31",
      "TextAlign": 0,
      "Type": "oval",
      "height": 31,
      "width": 31,
      "x": 210,
      "y": 85
    },
    {
      "Background": "Transparent",
      "BorderColor": "Transparent",
      "BorderStyle": 0,
      "DataDefault": "100.00",
      "DataKey": "F_03",
      "DataKeyStyle": 0,
      "FontColor": "Black",
      "FontFamily": "Zfull-GB",
      "FontSize": 12,
      "FontSpace": 0,
      "FontStyle": 0,
      "Location": "31, 107",
      "Size": "27, 9",
      "TextAlign": 1,
      "Type": "text",
      "height": 9,
      "width": 27,
      "x": 31,
      "y": 107
    },
    {
      "Background": "Transparent",
      "BorderColor": "Transparent",
      "BorderStyle": 0,
      "DataDefault": "100.00",
      "DataKey": "F_08",
      "DataKeyStyle": 0,
      "FontColor": "Black",
      "FontFamily": "Zfull-GB",
      "FontSize": 12,
      "FontSpace": 0,
      "FontStyle": 0,
      "Location": "181, 109",
      "Size": "27, 9",
      "TextAlign": 1,
      "Type": "text",
      "height": 9,
      "width": 27,
      "x": 181,
      "y": 109
    },
    {
      "Background": "Black",
      "BorderColor": "Transparent",
      "BorderStyle": 0,
      "DataDefault": "",
      "DataKey": "",
      "DataKeyStyle": 0,
      "FontColor": "Black",
      "FontFamily": "Zfull-GB",
      "FontSize": 12,
      "FontSpace": 0,
      "FontStyle": 0,
      "Location": "54, 64",
      "Size": "31, 3",
      "TextAlign": 0,
      "Type": "hline",
      "height": 3,
      "width": 31,
      "x": 54,
      "y": 64
    },
    {
      "Background": "Black",
      "BorderColor": "Transparent",
      "BorderStyle": 0,
      "DataDefault": "",
      "DataKey": "",
      "DataKeyStyle": 0,
      "FontColor": "Black",
      "FontFamily": "Zfull-GB",
      "FontSize": 12,
      "FontSpace": 0,
      "FontStyle": 0,
      "Location": "72, 53",
      "Size": "3, 31",
      "TextAlign": 0,
      "Type": "vline",
      "height": 31,
      "width": 3,
      "x": 72,
      "y": 53
    }
  ],
  "Name": "2",
  "Size": "250, 122",
  "TagType": "06",
  "Version": 10,
  "height": "122",
  "hext": "6",
  "rgb": "3",
  "wext": "0",
  "width": "250"
}

这才是我目前的模板数据。你从格式到数据结构都不对，请修复，保证跟原本的项目的输出结构一模一样。发挥你的想象力写出你认为最完美的代码，说中文。
严格按照我要求的数据结构