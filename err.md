{"TagType":"1C","Version":10,"Size":"400, 300","width":"400","hext":"6","Items":[{"FontFamily":"阿里普惠","DataKeyStyle":0,"FontStyle":0,"DataDefault":"塑料","Background":"Transparent","BorderStyle":0,"Size":"41, 9","FontColor":"Black","BorderColor":"Transparent","DataKey":"F_09","FontSpace":0,"Type":"text","FontSize":12,"TextAlign":1,"x":53,"width":41,"y":90,"height":9,"Location":"53, 90"},{"FontStyle":0,"Size":"135, 18","BorderColor":"Transparent","DataKey":"QRCODE","TextAlign":1,"height":18,"FontFamily":"阿里普惠","DataKeyStyle":0,"Barwidth":1,"DataDefault":"1234567890123","Background":"Transparent","BorderStyle":0,"FontColor":"Black","FontSpace":0,"Barheight":20,"Type":"barcode","Bartype":"code128","FontSize":12,"Barformat":0,"x":39,"width":135,"y":104,"Showtext":1,"Fontinval":1,"Location":"39, 104"},{"FontFamily":"阿里普惠","DataKeyStyle":0,"FontStyle":0,"DataDefault":"https://example.com","Background":"Transparent","BorderStyle":0,"Size":"140, 1","FontColor":"Black","BorderColor":"Transparent","DataKey":"QRCODE","FontSpace":0,"Type":"qrcode","FontSize":12,"TextAlign":1,"x":47,"width":140,"y":121,"height":1,"Location":"47, 121"},{"FontFamily":"阿里普惠","DataKeyStyle":0,"FontStyle":0,"DataDefault":"默认值","Background":"Transparent","BorderStyle":0,"Size":"38, 1","FontColor":"Black","BorderColor":"Transparent","DataKey":"F_11","FontSpace":0,"Type":"text","FontSize":12,"TextAlign":1,"x":212,"width":38,"y":121,"height":1,"Location":"212, 121"},{"FontFamily":"阿里普惠","DataKeyStyle":0,"FontStyle":0,"DataDefault":"100.00","Background":"Transparent","BorderStyle":0,"Size":"4, 1","FontColor":"Black","BorderColor":"Transparent","DataKey":"F_08","FontSpace":0,"Type":"text","FontSize":12,"TextAlign":1,"x":246,"width":4,"y":121,"height":1,"Location":"246, 121"},{"FontFamily":"阿里普惠","DataKeyStyle":0,"FontStyle":0,"DataDefault":"500g","Background":"Transparent","BorderStyle":0,"Size":"11, 1","FontColor":"Black","BorderColor":"Transparent","DataKey":"F_04","FontSpace":0,"Type":"text","FontSize":12,"TextAlign":1,"x":239,"width":11,"y":121,"height":1,"Location":"239, 121"},{"FontFamily":"阿里普惠","DataKeyStyle":0,"FontStyle":0,"DataDefault":"示例商品名称","Background":"Transparent","BorderStyle":0,"Size":"1, 1","FontColor":"Black","BorderColor":"Transparent","DataKey":"name","FontSpace":0,"Type":"text","FontSize":12,"TextAlign":1,"x":249,"width":1,"y":121,"height":1,"Location":"249, 121"},{"FontFamily":"阿里普惠","DataKeyStyle":0,"FontStyle":0,"DataDefault":"示例商品名称","Background":"Transparent","BorderStyle":0,"Size":"1, 1","FontColor":"Black","BorderColor":"Transparent","DataKey":"name","FontSpace":0,"Type":"text","FontSize":12,"TextAlign":1,"x":249,"width":1,"y":121,"height":1,"Location":"249, 121"},{"FontFamily":"阿里普惠","DataKeyStyle":0,"FontStyle":0,"DataDefault":"100.00","Background":"Transparent","BorderStyle":0,"Size":"1, 1","FontColor":"Black","BorderColor":"Transparent","DataKey":"F_01","FontSpace":0,"Type":"text","FontSize":12,"TextAlign":1,"x":249,"width":1,"y":121,"height":1,"Location":"249, 121"},{"FontFamily":"阿里普惠","DataKeyStyle":0,"FontStyle":0,"DataDefault":"默认值","Background":"Transparent","BorderStyle":0,"Size":"1, 1","FontColor":"Black","BorderColor":"Transparent","DataKey":"F_11","FontSpace":0,"Type":"text","FontSize":12,"TextAlign":1,"x":249,"width":1,"y":121,"height":1,"Location":"249, 121"}],"rgb":"3","wext":"0","Name":"3","height":"300"}

这是被录入到ap的字段，
{
  "panels": [
    {
      "index": 0,
      "name": "3",
      "paperType": "CUSTOM",
      "height": 300,
      "width": 400,
      "paperHeader": 0,
      "paperFooter": 850.3937007874016,
      "printElements": [
        {
          "options": {
            "left": 156,
            "top": 265.5,
            "height": 25.5,
            "width": 120,
            "title": "商品材质",
            "field": "PRODUCT_MATERIAL",
            "testData": "塑料",
            "templateField": "F_09",
            "fontWeight": "400",
            "textAlign": "center",
            "textContentVerticalAlign": "middle",
            "hideTitle": false
          },
          "printElementType": {
            "title": "商品材质",
            "type": "text"
          }
        },
        {
          "options": {
            "left": 114,
            "top": 306,
            "height": 115.5,
            "width": 399,
            "title": "条形码",
            "field": "PRODUCT_BARCODE",
            "testData": "1234567890123",
            "templateField": "QRCODE",
            "fontWeight": "400",
            "textAlign": "center",
            "textContentVerticalAlign": "middle",
            "hideTitle": false,
            "barcodeType": "code128",
            "textType": "barcode",
            "right": 513,
            "bottom": 421.5,
            "vCenter": 313.5,
            "hCenter": 363.75
          },
          "printElementType": {
            "title": "条形码",
            "type": "text"
          }
        },
        {
          "options": {
            "left": 625.5,
            "top": 360,
            "height": 163.5,
            "width": 120,
            "title": "商品产地",
            "field": "PRODUCT_ORIGIN",
            "testData": "默认值",
            "templateField": "F_11",
            "fontWeight": "400",
            "textAlign": "center",
            "textContentVerticalAlign": "middle",
            "hideTitle": false
          },
          "printElementType": {
            "title": "商品产地",
            "type": "text"
          }
        },
        {
          "options": {
            "left": 858,
            "top": 403.5,
            "height": 25.5,
            "width": 120,
            "title": "商品产地",
            "field": "PRODUCT_ORIGIN",
            "testData": "默认值",
            "templateField": "F_11",
            "fontWeight": "400",
            "textAlign": "center",
            "textContentVerticalAlign": "middle",
            "hideTitle": false
          },
          "printElementType": {
            "title": "商品产地",
            "type": "text"
          }
        },
        {
          "options": {
            "left": 139.5,
            "top": 453,
            "height": 414,
            "width": 414,
            "title": "二维码",
            "field": "PRODUCT_QRCODE",
            "testData": "https://example.com",
            "templateField": "QRCODE",
            "fontWeight": "400",
            "textAlign": "center",
            "textContentVerticalAlign": "middle",
            "hideTitle": false,
            "textType": "qrcode"
          },
          "printElementType": {
            "title": "二维码",
            "type": "text"
          }
        },
        {
          "options": {
            "left": 912,
            "top": 568.5,
            "height": 25.5,
            "width": 120,
            "title": "商品名称",
            "field": "PRODUCT_NAME",
            "testData": "示例商品名称",
            "templateField": "name",
            "fontWeight": "400",
            "textAlign": "center",
            "textContentVerticalAlign": "middle",
            "hideTitle": false,
            "right": 977.25,
            "bottom": 665.25,
            "vCenter": 917.25,
            "hCenter": 652.5
          },
          "printElementType": {
            "title": "商品名称",
            "type": "text"
          }
        },
        {
          "options": {
            "left": 726,
            "top": 591,
            "height": 25.5,
            "width": 80,
            "title": "商品批发价",
            "field": "PRODUCT_WHOLESALE_PRICE",
            "testData": "100.00",
            "templateField": "F_08",
            "fontWeight": "400",
            "textAlign": "center",
            "textContentVerticalAlign": "middle",
            "hideTitle": false
          },
          "printElementType": {
            "title": "商品批发价",
            "type": "text"
          }
        },
        {
          "options": {
            "left": 705,
            "top": 687,
            "height": 25.5,
            "width": 120,
            "title": "商品规格",
            "field": "PRODUCT_SPECIFICATION",
            "testData": "500g",
            "templateField": "F_04",
            "fontWeight": "400",
            "textAlign": "center",
            "textContentVerticalAlign": "middle",
            "hideTitle": false
          },
          "printElementType": {
            "title": "商品规格",
            "type": "text"
          }
        },
        {
          "options": {
            "left": 834,
            "top": 696,
            "height": 25.5,
            "width": 120,
            "title": "商品名称",
            "field": "PRODUCT_NAME",
            "testData": "示例商品名称",
            "templateField": "name",
            "fontWeight": "400",
            "textAlign": "center",
            "textContentVerticalAlign": "middle",
            "hideTitle": false
          },
          "printElementType": {
            "title": "商品名称",
            "type": "text"
          }
        },
        {
          "options": {
            "left": 741,
            "top": 787.5,
            "height": 25.5,
            "width": 80,
            "title": "商品零售价",
            "field": "PRODUCT_RETAIL_PRICE",
            "testData": "100.00",
            "templateField": "F_01",
            "fontWeight": "400",
            "textAlign": "center",
            "textContentVerticalAlign": "middle",
            "hideTitle": false
          },
          "printElementType": {
            "title": "商品零售价",
            "type": "text"
          }
        }
      ],
      "paperNumberContinue": true,
      "watermarkOptions": {}
    }
  ]
}
这是数据库的原拓展字段，转化的时候出问题了。已知2.13T的转化没有问题，但是4.20Td1转化就有问题了，很多东西都挤成一坨，影响使用。