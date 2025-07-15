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

这是标准格式。
{
    "TagType": "06",
    "Version": 10,
    "Size": "74, 105",
    "width": "74",
    "hext": "6",
    "Items": [
        {
            "FontFamily": "Zfull-GB",
            "DataKeyStyle": 0,
            "FontStyle": 0,
            "DataDefault": "ID",
            "Background": "Transparent",
            "BorderStyle": 0,
            "Size": "50, 25",
            "FontColor": "Black",
            "BorderColor": "Transparent",
            "DataKey": "id",
            "FontSpace": 0,
            "Type": "text",
            "FontSize": 12,
            "TextAlign": 1,
            "x": 27,
            "width": 50,
            "y": 72,
            "height": 25,
            "Location": "27, 72"
        },
        {
            "FontFamily": "Zfull-GB",
            "DataKeyStyle": 0,
            "FontStyle": 0,
            "DataDefault": "门店编码",
            "Background": "Transparent",
            "BorderStyle": 0,
            "Size": "50, 25",
            "FontColor": "Black",
            "BorderColor": "Transparent",
            "DataKey": "storeCode",
            "FontSpace": 0,
            "Type": "text",
            "FontSize": 12,
            "TextAlign": 1,
            "x": 103,
            "width": 50,
            "y": 99,
            "height": 25,
            "Location": "103, 99"
        },
        {
            "FontFamily": "Zfull-GB",
            "DataKeyStyle": 0,
            "FontStyle": 0,
            "DataDefault": "商品名称",
            "Background": "Transparent",
            "BorderStyle": 0,
            "Size": "50, 25",
            "FontColor": "Black",
            "BorderColor": "Transparent",
            "DataKey": "productName",
            "FontSpace": 0,
            "Type": "text",
            "FontSize": 12,
            "TextAlign": 1,
            "x": 34,
            "width": 50,
            "y": 175,
            "height": 25,
            "Location": "34, 175"
        },
        {
            "FontFamily": "Zfull-GB",
            "DataKeyStyle": 0,
            "FontStyle": 0,
            "DataDefault": "这是默认的商品描述",
            "Background": "Transparent",
            "BorderStyle": 0,
            "Size": "50, 25",
            "FontColor": "Black",
            "BorderColor": "Transparent",
            "DataKey": "productDescription",
            "FontSpace": 0,
            "Type": "text",
            "FontSize": 12,
            "TextAlign": 1,
            "x": 99,
            "width": 50,
            "y": 219,
            "height": 25,
            "Location": "99, 219"
        }
    ],
    "rgb": "3",
    "wext": "0",
    "Name": "U",
    "height": "105"
}
这是你的输出？？？你去查找数据库了吗？我哪来的名字为U？？？？你是不是哪里搞错了？请修复
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@69b0bea4]
2025-07-15T21:20:24.350+08:00  WARN 43916 --- [io-8999-exec-10] c.p.downloadcf.util.ScreenTypeConverter  : 无法提取屏幕类型，使用默认值: 06
2025-07-15T21:20:24.350+08:00  INFO 43916 --- [io-8999-exec-10] c.p.d.service.TemplateServiceImpl        : 模板ID: 1945045387689762818 提取到屏幕类型: 06
2025-07-15T21:20:24.350+08:00  INFO 43916 --- [io-8999-exec-10] c.p.d.service.TemplateServiceImpl        : 生成的模板文件名: 6666_1C.json
2025-07-15T21:20:24.383+08:00  INFO 43916 --- [io-8999-exec-10] c.p.d.service.TemplateServiceImpl        : 模板消息已发送到RabbitMQ队列: {"shop":"009","data":{"tmpls":[{"name":"6666_1C.json","id":"1945045387689762818","md5":"1185f3005d0b95c0697a83af6d4d0660"}],"url":"http://10.3.36.36:8999/api/res/templ/loadtemple","tid":"396a5189-53d8-4354-bcfa-27d57d9d69ad"},"id":"34fc4e81-8619-4835-8305-78f74ab8b545","command":"tmpllist","timestamp":1752585624}
2025-07-15T21:20:24.395+08:00  INFO 43916 --- [ntContainer#0-1] c.p.downloadcf.service.RabbitMQListener  : 从RabbitMQ接收到模板消息: {"shop":"009","data":{"tmpls":[{"name":"6666_1C.json","id":"1945045387689762818","md5":"1185f3005d0b95c0697a83af6d4d0660"}],"url":"http://10.3.36.36:8999/api/res/templ/loadtemple","tid":"396a5189-53d8-4354-bcfa-27d57d9d69ad"},"id":"34fc4e81-8619-4835-8305-78f74ab8b545","command":"tmpllist","timestamp":1752585624}
2025-07-15T21:20:24.733+08:00  INFO 43916 --- [ntContainer#0-1] c.p.downloadcf.service.RabbitMQListener  : 模板消息已发送到MQTT主题 esl/server/data/009: {"shop":"009","data":{"tmpls":[{"name":"6666_1C.json","id":"1945045387689762818","md5":"1185f3005d0b95c0697a83af6d4d0660"}],"url":"http://10.3.36.36:8999/api/res/templ/loadtemple","tid":"396a5189-53d8-4354-bcfa-27d57d9d69ad"},"id":"34fc4e81-8619-4835-8305-78f74ab8b545","command":"tmpllist","timestamp":1752585624}
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@76d4a564] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@702e1a26] will not be managed by Spring
==>  Preparing: SELECT * FROM act_ext_template_print WHERE name = ?
==> Parameters: 6666_1C(String)
<==      Total: 0
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@76d4a564]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4768f01a] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@702e1a26] will not be managed by Spring
==>  Preparing: SELECT * FROM act_ext_template_print WHERE name = ?
==> Parameters: 6666(String)
<==    Columns: ID, TENANT_ID, NAME, CODE, CONTENT, CATEGORY, TYPE, SORT_CODE, EXT_JSON, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER
<==        Row: 1945045387689762818, -1, 6666, xw5XoeRSPl, <<BLOB>>, MINI_TAG, DESIGN, 99, <<BLOB>>, NOT_DELETE, 2025-07-15 16:58:52, 1543837863788879871, null, null
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4768f01a]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@417b8656] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@702e1a26] will not be managed by Spring
==>  Preparing: SELECT * FROM act_ext_template_print WHERE name = ?
==> Parameters: 6666_1C(String)
<==      Total: 0
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@417b8656]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4b83ed26] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@702e1a26] will not be managed by Spring
==>  Preparing: SELECT * FROM act_ext_template_print WHERE name = ?
==> Parameters: 6666(String)
<==    Columns: ID, TENANT_ID, NAME, CODE, CONTENT, CATEGORY, TYPE, SORT_CODE, EXT_JSON, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER
<==        Row: 1945045387689762818, -1, 6666, xw5XoeRSPl, <<BLOB>>, MINI_TAG, DESIGN, 99, <<BLOB>>, NOT_DELETE, 2025-07-15 16:58:52, 1543837863788879871, null, null
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4b83ed26]
