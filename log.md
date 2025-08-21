2025-08-21T20:24:07.542+08:00 INFO 969270 --- [nio-8999-exec-6] c.p.downloadcf.controller.EslController : 接收到价签刷新请求: EslRefreshRequest(eslId=1947223724923940865, brandCode=panda, forceRefresh=true, storeCode=BY001)
2025-08-21T20:24:07.543+08:00 INFO 969270 --- [nio-8999-exec-6] c.p.d.service.EslRefreshService : 开始刷新价签: EslRefreshRequest(eslId=1947223724923940865, brandCode=panda, forceRefresh=true, storeCode=BY001)
2025-08-21T20:24:07.543+08:00 INFO 969270 --- [nio-8999-exec-6] c.p.d.service.EslRefreshService : 请求中的品牌编码: panda
2025-08-21T20:24:07.543+08:00 INFO 969270 --- [nio-8999-exec-6] c.p.downloadcf.service.DataService : 获取价签完整数据: 1947223724923940865
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4b4c82e8] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@3899ce59] will not be managed by Spring
==> Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947223724923940865(String)
<== Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<== Row: 1947223724923940865, -1, null, NOT_DELETE, 2025-07-21 17:14:48, 1543837863788879871, 2025-08-21 17:56:17, 1543837863788879871, 06000000195A, BY001, ESLAP00000053, 1946228072533467137, 1947222838805917697, 295.0, 2500.0, -23, 3, null, null, Refresh Success, null, null, null, null, <<BLOB>>
<== Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4b4c82e8]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1abce341] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@3899ce59] will not be managed by Spring
==> Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947223724923940865(String)
<== Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<== Row: 1947223724923940865, -1, null, NOT_DELETE, 2025-07-21 17:14:48, 1543837863788879871, 2025-08-21 17:56:17, 1543837863788879871, 06000000195A, BY001, ESLAP00000053, 1946228072533467137, 1947222838805917697, 295.0, 2500.0, -23, 3, null, null, Refresh Success, null, null, null, null, <<BLOB>>
<== Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1abce341]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5463e833] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@3899ce59] will not be managed by Spring
==> Preparing: select ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE , EXT_JSON, PRODUCT_DESCRIPTION from panda_product where ID = ?
==> Parameters: 1947222838805917697(String)
<== Columns: ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE, EXT_JSON, PRODUCT_DESCRIPTION
<== Row: 1947222838805917697, -1, NOT_DELETE, 2025-07-21 17:11:17, 1543837863788879871, 2025-08-21 18:08:36, 1543837863788879871, 001, BY001, 测试商品, PRODUCT_FRUIT, PRODUCT_FRUIT, AES001, 9.99, 99.99, 99.90, 99.00, 0.09, 99.00, 测试材质, http://10.3.36.25:82/dev/file/download?id=1958168487407054849&Domain=http://192.168.10.50:81, 测试产地, www.bing.com, www.bing.com, ge, 9.00, 测试状态, 99, 1946122678071738370, <<BLOB>>, <<BLOB>>
<== Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5463e833]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@78124cc5] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@3899ce59] will not be managed by Spring
==> Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947223724923940865(String)
<== Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<== Row: 1947223724923940865, -1, null, NOT_DELETE, 2025-07-21 17:14:48, 1543837863788879871, 2025-08-21 17:56:17, 1543837863788879871, 06000000195A, BY001, ESLAP00000053, 1946228072533467137, 1947222838805917697, 295.0, 2500.0, -23, 3, null, null, Refresh Success, null, null, null, null, <<BLOB>>
<== Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@78124cc5]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@735f44df] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@3899ce59] will not be managed by Spring
==> Preparing: select ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE , EXT_JSON, PRODUCT_DESCRIPTION from panda_product where ID = ?
==> Parameters: 1947222838805917697(String)
<== Columns: ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE, EXT_JSON, PRODUCT_DESCRIPTION
<== Row: 1947222838805917697, -1, NOT_DELETE, 2025-07-21 17:11:17, 1543837863788879871, 2025-08-21 18:08:36, 1543837863788879871, 001, BY001, 测试商品, PRODUCT_FRUIT, PRODUCT_FRUIT, AES001, 9.99, 99.99, 99.90, 99.00, 0.09, 99.00, 测试材质, http://10.3.36.25:82/dev/file/download?id=1958168487407054849&Domain=http://192.168.10.50:81, 测试产地, www.bing.com, www.bing.com, ge, 9.00, 测试状态, 99, 1946122678071738370, <<BLOB>>, <<BLOB>>
<== Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@735f44df]
2025-08-21T20:24:07.552+08:00 INFO 969270 --- [nio-8999-exec-6] c.p.downloadcf.service.DataService : 查询模板，模板ID: 1946122678071738370
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@544b2b28] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@3899ce59] will not be managed by Spring
==> Preparing: select ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER , CONTENT, EXT_JSON from print_template_design where ID = ?
==> Parameters: 1946122678071738370(String)
<== Columns: ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, CONTENT, EXT_JSON
<== Row: 1946122678071738370, -1, test1, null, 2.13T, 99, NOT_DELETE, 2025-07-18 16:19:38, 1543837863788879871, 2025-08-21 18:20:37, 1543837863788879871, <<BLOB>>, <<BLOB>>
<== Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@544b2b28]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@55898496] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@3899ce59] will not be managed by Spring
==> Preparing: select 'false' as QUERYID, ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, BRAND_CODE, TEMPLATE_FIELD, FIELD_CODE, FORMAT_RULE from esl_brand_field_mapping WHERE ( BRAND_CODE = ? )
==> Parameters: 攀攀(String)
<== Total: 0
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@55898496]
2025-08-21T20:24:07.555+08:00 INFO 969270 --- [nio-8999-exec-6] c.p.downloadcf.service.DataService : 字段映射配置查询结果: brandCode=攀攀, mappingCount=0
2025-08-21T20:24:07.556+08:00 INFO 969270 --- [nio-8999-exec-6] c.p.downloadcf.service.DataService : 成功获取价签完整数据: eslId=1947223724923940865, productId=1947222838805917697, templateId=1946122678071738370
2025-08-21T20:24:07.556+08:00 INFO 969270 --- [nio-8999-exec-6] c.p.d.service.EslRefreshService : 获取到价签数据，商品品牌: AES001
2025-08-21T20:24:07.556+08:00 INFO 969270 --- [nio-8999-exec-6] c.p.d.service.EslRefreshService : 请求参数中的品牌编码: panda -> 标准化: panda
2025-08-21T20:24:07.556+08:00 INFO 969270 --- [nio-8999-exec-6] c.p.d.service.EslRefreshService : 商品数据中的品牌字段: AES001 -> 标准化: aes001
2025-08-21T20:24:07.556+08:00 INFO 969270 --- [nio-8999-exec-6] c.p.d.service.EslRefreshService : 实际使用的品牌编码: aes001 -> 适配器品牌编码: aes001
2025-08-21T20:24:07.556+08:00 ERROR 969270 --- [nio-8999-exec-6] c.p.d.service.EslRefreshService : 未找到品牌适配器: brandCode=aes001