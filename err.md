
2025-07-24T09:27:40.063+08:00  INFO 35888 --- [           main] c.p.downloadcf.DownloadcfApplication     : Starting DownloadcfApplication using Java 22.0.2 with PID 35888 (E:\IdeaProjects\cfdownloadexample\target\classes started by Ming in E:\IdeaProjects\cfdownloadexample)
2025-07-24T09:27:40.067+08:00  INFO 35888 --- [           main] c.p.downloadcf.DownloadcfApplication     : No active profile set, falling back to 1 default profile: "default"
2025-07-24T09:27:40.895+08:00  INFO 35888 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Multiple Spring Data modules found, entering strict repository configuration mode
2025-07-24T09:27:40.897+08:00  INFO 35888 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data Redis repositories in DEFAULT mode.
2025-07-24T09:27:40.925+08:00  INFO 35888 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 18 ms. Found 0 Redis repository interfaces.
2025-07-24T09:27:40.998+08:00  INFO 35888 --- [           main] faultConfiguringBeanFactoryPostProcessor : No bean named 'errorChannel' has been explicitly defined. Therefore, a default PublishSubscribeChannel will be created.
2025-07-24T09:27:41.006+08:00  INFO 35888 --- [           main] faultConfiguringBeanFactoryPostProcessor : No bean named 'integrationHeaderChannelRegistry' has been explicitly defined. Therefore, a default DefaultHeaderChannelRegistry will be created.
2025-07-24T09:27:41.552+08:00  INFO 35888 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8999 (http)
2025-07-24T09:27:41.560+08:00  INFO 35888 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2025-07-24T09:27:41.560+08:00  INFO 35888 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.20]
2025-07-24T09:27:41.607+08:00  INFO 35888 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2025-07-24T09:27:41.607+08:00  INFO 35888 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1480 ms
Logging initialized using 'class org.apache.ibatis.logging.stdout.StdOutImpl' adapter.
2025-07-24T09:27:42.363+08:00  INFO 35888 --- [           main] c.pandatech.downloadcf.config.EslConfig  : 初始化品牌适配器列表
2025-07-24T09:27:42.364+08:00  INFO 35888 --- [           main] c.pandatech.downloadcf.config.EslConfig  : 初始化消息执行器列表
2025-07-24T09:27:42.952+08:00  INFO 35888 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : Adding {logging-channel-adapter:_org.springframework.integration.errorLogger} as a subscriber to the 'errorChannel' channel
2025-07-24T09:27:42.952+08:00  INFO 35888 --- [           main] o.s.i.channel.PublishSubscribeChannel    : Channel 'application.errorChannel' has 1 subscriber(s).
2025-07-24T09:27:42.952+08:00  INFO 35888 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : started bean '_org.springframework.integration.errorLogger'
2025-07-24T09:27:42.952+08:00  INFO 35888 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : Adding {message-handler:mqttOutbound.serviceActivator} as a subscriber to the 'mqttOutboundChannel' channel
2025-07-24T09:27:42.953+08:00  INFO 35888 --- [           main] o.s.integration.channel.DirectChannel    : Channel 'application.mqttOutboundChannel' has 1 subscriber(s).
2025-07-24T09:27:42.953+08:00  INFO 35888 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : started bean 'mqttOutbound.serviceActivator'
2025-07-24T09:27:42.953+08:00  INFO 35888 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : Adding {service-activator:mqttService.handleMessage.serviceActivator} as a subscriber to the 'mqttInputChannel' channel
2025-07-24T09:27:42.953+08:00  INFO 35888 --- [           main] o.s.integration.channel.DirectChannel    : Channel 'application.mqttInputChannel' has 1 subscriber(s).
2025-07-24T09:27:42.953+08:00  INFO 35888 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : started bean 'mqttService.handleMessage.serviceActivator'
2025-07-24T09:27:43.291+08:00  INFO 35888 --- [           main] .m.i.MqttPahoMessageDrivenChannelAdapter : started bean 'inbound'; defined in: 'class path resource [com/pandatech/downloadcf/config/MqttConfig.class]'; from source: 'com.pandatech.downloadcf.config.MqttConfig.inbound()'
2025-07-24T09:27:43.306+08:00  INFO 35888 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8999 (http) with context path ''
2025-07-24T09:27:43.307+08:00  INFO 35888 --- [           main] o.s.a.r.c.CachingConnectionFactory       : Attempting to connect to: [10.3.36.15:5672]
2025-07-24T09:27:43.340+08:00  INFO 35888 --- [           main] o.s.a.r.c.CachingConnectionFactory       : Created new connection: rabbitConnectionFactory#671da0f9:0/SimpleConnection@3694461d [delegate=amqp://panda@10.3.36.15:5672/, localPort=14858]
2025-07-24T09:27:43.391+08:00  INFO 35888 --- [           main] c.p.downloadcf.DownloadcfApplication     : Started DownloadcfApplication in 3.87 seconds (process running for 4.351)
2025-07-24T09:27:53.950+08:00  INFO 35888 --- [nio-8999-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2025-07-24T09:27:53.950+08:00  INFO 35888 --- [nio-8999-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2025-07-24T09:27:53.951+08:00  INFO 35888 --- [nio-8999-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 1 ms
2025-07-24T09:27:54.101+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.TemplateServiceImpl        : 开始刷新价签，价签ID: 1947223724923940865, 门店编码: 0002, 品牌编码: PANDA
2025-07-24T09:27:54.102+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.EslRefreshService          : 开始刷新价签: EslRefreshRequest(eslId=1947223724923940865, brandCode=PANDA, forceRefresh=false, storeCode=0002)
2025-07-24T09:27:54.103+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.EslRefreshService          : 请求中的品牌编码: PANDA
2025-07-24T09:27:54.104+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.downloadcf.service.DataService       : 获取价签完整数据: 1947223724923940865
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@70e281d5] was not registered for synchronization because synchronization is not active
2025-07-24T09:27:54.159+08:00  INFO 35888 --- [nio-8999-exec-1] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} inited
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947223724923940865(String)
<==    Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<==        Row: 1947223724923940865, -1, null, NOT_DELETE, 2025-07-21 17:14:48, 1543837863788879871, null, null, 06000000195A, 0002, null, 2.13T, 1947222838805917697, null, null, null, null, null, null, null, null, null, null, null, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@70e281d5]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4c04ec1b] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947223724923940865(String)
<==    Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<==        Row: 1947223724923940865, -1, null, NOT_DELETE, 2025-07-21 17:14:48, 1543837863788879871, null, null, 06000000195A, 0002, null, 2.13T, 1947222838805917697, null, null, null, null, null, null, null, null, null, null, null, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4c04ec1b]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@750b12fa] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE , EXT_JSON, PRODUCT_DESCRIPTION from panda_product where ID = ?
==> Parameters: 1947222838805917697(String)
<==    Columns: ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE, EXT_JSON, PRODUCT_DESCRIPTION
<==        Row: 1947222838805917697, -1, NOT_DELETE, 2025-07-21 17:11:17, 1543837863788879871, 2025-07-21 19:59:45, 1543837863788879871, 001, 0002, 测试商品, 测试分类, PRODUCT_FRUIT, 攀攀, 9.99, 99.99, 99.90, 99.00, 0.09, 99.00, 测试材质, http://localhost:82/dev/file/download?id=1947222476036370434&Domain=http://localhost:81, 测试产地, www.baidu.com, www.baidu.com, ge, 9.00, 测试状态, 99, 1946122678071738370, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@750b12fa]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6252da10] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947223724923940865(String)
<==    Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<==        Row: 1947223724923940865, -1, null, NOT_DELETE, 2025-07-21 17:14:48, 1543837863788879871, null, null, 06000000195A, 0002, null, 2.13T, 1947222838805917697, null, null, null, null, null, null, null, null, null, null, null, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6252da10]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1c737511] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE , EXT_JSON, PRODUCT_DESCRIPTION from panda_product where ID = ?
==> Parameters: 1947222838805917697(String)
<==    Columns: ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE, EXT_JSON, PRODUCT_DESCRIPTION
<==        Row: 1947222838805917697, -1, NOT_DELETE, 2025-07-21 17:11:17, 1543837863788879871, 2025-07-21 19:59:45, 1543837863788879871, 001, 0002, 测试商品, 测试分类, PRODUCT_FRUIT, 攀攀, 9.99, 99.99, 99.90, 99.00, 0.09, 99.00, 测试材质, http://localhost:82/dev/file/download?id=1947222476036370434&Domain=http://localhost:81, 测试产地, www.baidu.com, www.baidu.com, ge, 9.00, 测试状态, 99, 1946122678071738370, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1c737511]
2025-07-24T09:27:54.401+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.downloadcf.service.DataService       : 查询模板，模板ID: 1946122678071738370
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2aaebff5] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER , CONTENT, EXT_JSON from print_template_design where ID = ?
==> Parameters: 1946122678071738370(String)
<==    Columns: ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, CONTENT, EXT_JSON
<==        Row: 1946122678071738370, -1, 2, null, 2.13T, 99, NOT_DELETE, 2025-07-18 16:19:38, 1543837863788879871, 2025-07-21 22:41:46, 1543837863788879871, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2aaebff5]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2df783fe] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select 'false' as QUERYID, ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, BRAND_CODE, TEMPLATE_FIELD, FIELD_CODE, FORMAT_RULE from esl_brand_field_mapping WHERE ( BRAND_CODE = ? )
==> Parameters: 攀攀(String)
<==    Columns: QUERYID, ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, BRAND_CODE, TEMPLATE_FIELD, FIELD_CODE, FORMAT_RULE
<==        Row: false, 1946188424516816897, -1, null, NOT_DELETE, 2025-07-18 20:40:54, 1543837863788879871, 2025-07-19 00:22:38, 1543837863788879871, 攀攀, code, PRODUCT_ID, 商品名称
<==        Row: false, 1946188616733380609, -1, null, NOT_DELETE, 2025-07-18 20:41:39, 1543837863788879871, 2025-07-19 00:22:35, 1543837863788879871, 攀攀, name, PRODUCT_NAME, 商品名称
<==        Row: false, 1946230416441430018, -1, null, NOT_DELETE, 2025-07-18 23:27:45, 1543837863788879871, 2025-07-19 00:22:31, 1543837863788879871, 攀攀, F_01, PRODUCT_RETAIL_PRICE, 价格
<==        Row: false, 1946244176275132418, -1, null, NOT_DELETE, 2025-07-19 00:22:26, 1543837863788879871, null, null, 攀攀, F_02, PRODUCT_CATEGORY, 商品分类
<==        Row: false, 1946244319741300737, -1, null, NOT_DELETE, 2025-07-19 00:23:00, 1543837863788879871, null, null, 攀攀, F_03, PRODUCT_COST_PRICE, 商品成本价
<==        Row: false, 1946244382848798721, -1, null, NOT_DELETE, 2025-07-19 00:23:15, 1543837863788879871, null, null, 攀攀, F_04, PRODUCT_SPECIFICATION, 商品规格
<==        Row: false, 1946244450377093122, -1, null, NOT_DELETE, 2025-07-19 00:23:31, 1543837863788879871, null, null, 攀攀, F_05, PRODUCT_MEMBERSHIP_PRICE, 商品会员价
<==        Row: false, 1946244527699087362, -1, null, NOT_DELETE, 2025-07-19 00:23:50, 1543837863788879871, null, null, 攀攀, F_06, PRODUCT_DISCOUNT_PRICE, 商品折扣价
<==        Row: false, 1946244599702704129, -1, null, NOT_DELETE, 2025-07-19 00:24:07, 1543837863788879871, null, null, 攀攀, F_07, PRODUCT_DISCOUNT, 商品折扣（例如：0.8 表示8折）
<==        Row: false, 1946244674935934978, -1, null, NOT_DELETE, 2025-07-19 00:24:25, 1543837863788879871, null, null, 攀攀, F_08, PRODUCT_WHOLESALE_PRICE, 商品批发
<==        Row: false, 1946244741017194498, -1, null, NOT_DELETE, 2025-07-19 00:24:40, 1543837863788879871, null, null, 攀攀, F_09, PRODUCT_MATERIAL, 商品材质
<==        Row: false, 1946244808008617986, -1, null, NOT_DELETE, 2025-07-19 00:24:56, 1543837863788879871, null, null, 攀攀, F_10, PRODUCT_IMAGE, 商品图片（路径或URL）
<==        Row: false, 1946244885259309057, -1, null, NOT_DELETE, 2025-07-19 00:25:15, 1543837863788879871, null, null, 攀攀, F_11, PRODUCT_ORIGIN, 商品产地
<==        Row: false, 1946244968667238402, -1, null, NOT_DELETE, 2025-07-19 00:25:35, 1543837863788879871, null, null, 攀攀, F_20, PRODUCT_DESCRIPTION, 商品描述
<==        Row: false, 1946245050686853122, -1, null, NOT_DELETE, 2025-07-19 00:25:54, 1543837863788879871, null, null, 攀攀, F_12, PRODUCT_UNIT, 商品单位（如：个、件、瓶等）
<==        Row: false, 1946245165296209922, -1, null, NOT_DELETE, 2025-07-19 00:26:22, 1543837863788879871, null, null, 攀攀, F_13, PRODUCT_WEIGHT, 产品重量（单位：kg）
<==        Row: false, 1946245224305872898, -1, null, NOT_DELETE, 2025-07-19 00:26:36, 1543837863788879871, null, null, 攀攀, F_14, PRODUCT_STATUS, 商品状态（如：上架、下架、预售等）
<==        Row: false, 1946245284653518849, -1, null, NOT_DELETE, 2025-07-19 00:26:50, 1543837863788879871, null, null, 攀攀, F_32, PRODUCT_STOCK, 商品库存
<==        Row: false, 1947173347558027266, -1, null, NOT_DELETE, 2025-07-21 13:54:37, 1543837863788879871, null, null, 攀攀, QRCODE, PRODUCT_QRCODE, 二维码
<==        Row: false, 1947173427983806466, -1, null, NOT_DELETE, 2025-07-21 13:54:57, 1543837863788879871, null, null, 攀攀, QRCODE, PRODUCT_BARCODE, 条形码
<==      Total: 20
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2df783fe]
2025-07-24T09:27:54.441+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.downloadcf.service.DataService       : 字段映射配置查询结果: brandCode=攀攀, mappingCount=20
2025-07-24T09:27:54.441+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.downloadcf.service.DataService       : 成功获取价签完整数据: eslId=1947223724923940865, productId=1947222838805917697, templateId=1946122678071738370
2025-07-24T09:27:54.441+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.EslRefreshService          : 获取到价签数据，商品品牌: 攀攀
2025-07-24T09:27:54.441+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.EslRefreshService          : 请求参数中的品牌编码: PANDA (用于选择解析器)
2025-07-24T09:27:54.441+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.EslRefreshService          : 商品数据中的品牌字段: 攀攀
2025-07-24T09:27:54.441+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.EslRefreshService          : 实际使用的品牌编码: 攀攀 (用于查找品牌适配器)
2025-07-24T09:27:54.442+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.EslRefreshService          : 获取到品牌适配器: PandaBrandAdapter
2025-07-24T09:27:54.442+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.EslRefreshService          : 品牌适配器支持的品牌编码: 攀攀
2025-07-24T09:27:54.442+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.EslRefreshService          : 数据验证通过
2025-07-24T09:27:54.442+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 开始转换攀攀品牌数据: eslId=1947223724923940865
2025-07-24T09:27:54.442+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 开始构建数据映射，商品ID: 001, 商品名称: 测试商品
2025-07-24T09:27:54.442+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 使用字段映射配置，映射数量: 20
2025-07-24T09:27:54.442+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_ID -> code (格式: 商品名称)
2025-07-24T09:27:54.442+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_NAME -> name (格式: 商品名称)
2025-07-24T09:27:54.442+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_RETAIL_PRICE -> F_01 (格式: 价格)
2025-07-24T09:27:54.442+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_CATEGORY -> F_02 (格式: 商品分类)
2025-07-24T09:27:54.442+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_COST_PRICE -> F_03 (格式: 商品成本价)
2025-07-24T09:27:54.442+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_SPECIFICATION -> F_04 (格式: 商品规格)
2025-07-24T09:27:54.442+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_MEMBERSHIP_PRICE -> F_05 (格式: 商品会员价)
2025-07-24T09:27:54.442+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_DISCOUNT_PRICE -> F_06 (格式: 商品折扣价)
2025-07-24T09:27:54.442+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_DISCOUNT -> F_07 (格式: 商品折扣（例如：0.8 表示8折）)
2025-07-24T09:27:54.442+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_WHOLESALE_PRICE -> F_08 (格式: 商品批发)
2025-07-24T09:27:54.442+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_MATERIAL -> F_09 (格式: 商品材质)
2025-07-24T09:27:54.443+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_IMAGE -> F_10 (格式: 商品图片（路径或URL）)
2025-07-24T09:27:54.443+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_ORIGIN -> F_11 (格式: 商品产地)
2025-07-24T09:27:54.443+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_DESCRIPTION -> F_20 (格式: 商品描述)
2025-07-24T09:27:54.443+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_UNIT -> F_12 (格式: 商品单位（如：个、件、瓶等）)
2025-07-24T09:27:54.443+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_WEIGHT -> F_13 (格式: 产品重量（单位：kg）)
2025-07-24T09:27:54.443+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_STATUS -> F_14 (格式: 商品状态（如：上架、下架、预售等）)
2025-07-24T09:27:54.443+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_STOCK -> F_32 (格式: 商品库存)
2025-07-24T09:27:54.443+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_QRCODE -> QRCODE (格式: 二维码)
2025-07-24T09:27:54.443+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_BARCODE -> QRCODE (格式: 条形码)
2025-07-24T09:27:54.443+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 字段映射完成，实际映射字段数量: 19
2025-07-24T09:27:54.443+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 映射结果: {GOODS_CODE=001, GOODS_NAME=测试商品, F_1=99.99, F_2=测试分类, F_3=9.99, F_4=PRODUCT_FRUIT, F_5=99.9, F_6=99.0, F_7=0.09, F_8=99.0, F_9=测试材质, F_10=http://localhost:82/dev/file/download?id=1947222476036370434&Domain=http://localhost:81, F_11=测试产地, F_20=<p>测试描述</p>, F_12=ge, F_13=9.0, F_14=测试状态, F_32=99, QRCODE=www.baidu.com}
2025-07-24T09:27:54.450+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 攀攀品牌数据转换完成: eslId=1947223724923940865, actualEslId=06000000195A, checksum=12780dc2f9234a1406c5853435f7296a
2025-07-24T09:27:54.450+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.EslRefreshService          : 数据转换完成，输出数据类型: BrandOutputData
2025-07-24T09:27:54.450+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.EslRefreshService          : 准备发送消息，使用品牌编码: 攀攀
2025-07-24T09:27:54.450+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.MessageProducerService     : 开始发送消息: brandCode=攀攀, eslId=1947223724923940865
2025-07-24T09:27:54.451+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.MessageProducerService     : 开始构建MQTT载荷，ESL ID: 1947223724923940865, 门店代码: 0002
2025-07-24T09:27:54.451+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.MessageProducerService     : 开始构建data数组，ESL ID: 1947223724923940865
2025-07-24T09:27:54.451+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.MessageProducerService     : 开始获取模板名称，ESL ID: 1947223724923940865
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@922f8d7] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947223724923940865(String)
<==    Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<==        Row: 1947223724923940865, -1, null, NOT_DELETE, 2025-07-21 17:14:48, 1543837863788879871, null, null, 06000000195A, 0002, null, 2.13T, 1947222838805917697, null, null, null, null, null, null, null, null, null, null, null, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@922f8d7]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@391e9642] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE , EXT_JSON, PRODUCT_DESCRIPTION from panda_product where ID = ?
==> Parameters: 1947222838805917697(String)
<==    Columns: ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE, EXT_JSON, PRODUCT_DESCRIPTION
<==        Row: 1947222838805917697, -1, NOT_DELETE, 2025-07-21 17:11:17, 1543837863788879871, 2025-07-21 19:59:45, 1543837863788879871, 001, 0002, 测试商品, 测试分类, PRODUCT_FRUIT, 攀攀, 9.99, 99.99, 99.90, 99.00, 0.09, 99.00, 测试材质, http://localhost:82/dev/file/download?id=1947222476036370434&Domain=http://localhost:81, 测试产地, www.baidu.com, www.baidu.com, ge, 9.00, 测试状态, 99, 1946122678071738370, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@391e9642]
2025-07-24T09:27:54.458+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.downloadcf.service.DataService       : 查询模板，模板ID: 1946122678071738370
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@c97aaa3] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER , CONTENT, EXT_JSON from print_template_design where ID = ?
==> Parameters: 1946122678071738370(String)
<==    Columns: ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, CONTENT, EXT_JSON
<==        Row: 1946122678071738370, -1, 2, null, 2.13T, 99, NOT_DELETE, 2025-07-18 16:19:38, 1543837863788879871, 2025-07-21 22:41:46, 1543837863788879871, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@c97aaa3]
2025-07-24T09:27:54.461+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.MessageProducerService     : 找到模板数据，模板ID: 1946122678071738370, 模板名称: 2
2025-07-24T09:27:54.461+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.MessageProducerService     : 开始从CONTENT字段解析模板名称
2025-07-24T09:27:54.461+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.MessageProducerService     : CONTENT字段内容长度: 338 字符
2025-07-24T09:27:54.462+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.MessageProducerService     : 从panels[0].name提取到模板名称: 2
2025-07-24T09:27:54.462+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.MessageProducerService     : 从CONTENT字段解析到模板名称: 2
2025-07-24T09:27:54.462+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.MessageProducerService     : 开始获取model值，ESL ID: 1947223724923940865
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@20f58913] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947223724923940865(String)
<==    Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<==        Row: 1947223724923940865, -1, null, NOT_DELETE, 2025-07-21 17:14:48, 1543837863788879871, null, null, 06000000195A, 0002, null, 2.13T, 1947222838805917697, null, null, null, null, null, null, null, null, null, null, null, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@20f58913]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6bf23c71] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE , EXT_JSON, PRODUCT_DESCRIPTION from panda_product where ID = ?
==> Parameters: 1947222838805917697(String)
<==    Columns: ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE, EXT_JSON, PRODUCT_DESCRIPTION
<==        Row: 1947222838805917697, -1, NOT_DELETE, 2025-07-21 17:11:17, 1543837863788879871, 2025-07-21 19:59:45, 1543837863788879871, 001, 0002, 测试商品, 测试分类, PRODUCT_FRUIT, 攀攀, 9.99, 99.99, 99.90, 99.00, 0.09, 99.00, 测试材质, http://localhost:82/dev/file/download?id=1947222476036370434&Domain=http://localhost:81, 测试产地, www.baidu.com, www.baidu.com, ge, 9.00, 测试状态, 99, 1946122678071738370, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6bf23c71]
2025-07-24T09:27:54.470+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.downloadcf.service.DataService       : 查询模板，模板ID: 1946122678071738370
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2f6ce895] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER , CONTENT, EXT_JSON from print_template_design where ID = ?
==> Parameters: 1946122678071738370(String)
<==    Columns: ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, CONTENT, EXT_JSON
<==        Row: 1946122678071738370, -1, 2, null, 2.13T, 99, NOT_DELETE, 2025-07-18 16:19:38, 1543837863788879871, 2025-07-21 22:41:46, 1543837863788879871, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2f6ce895]
2025-07-24T09:27:54.472+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.MessageProducerService     : 找到模板数据，模板ID: 1946122678071738370, CATEGORY: 2.13T
2025-07-24T09:27:54.473+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.MessageProducerService     : 从模板中解析到屏幕类型: 2.13T
2025-07-24T09:27:54.473+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.MessageProducerService     : 开始转换屏幕类型到model，输入屏幕类型: 2.13t
2025-07-24T09:27:54.473+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.MessageProducerService     : 标准化后的屏幕类型: 2.13t
2025-07-24T09:27:54.473+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.MessageProducerService     : 屏幕类型转换完成: 2.13t -> 06
2025-07-24T09:27:54.473+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.MessageProducerService     : 屏幕类型 2.13T 转换为model: 06
2025-07-24T09:27:54.474+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.MessageProducerService     : data数组构建完成，tag: 6597069773146, tmpl: 2, model: 06, taskid: 74473, token: 406445
2025-07-24T09:27:54.474+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.MessageProducerService     : MQTT载荷构建完成，载荷大小: 5 字段
2025-07-24T09:27:54.474+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.downloadcf.executor.MqttExecutor     : 执行MQTT消息发送: destination=esl/server/data/0002, eslId=1947223724923940865
2025-07-24T09:27:54.824+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.downloadcf.executor.MqttExecutor     : MQTT消息发送成功: eslId=1947223724923940865
2025-07-24T09:27:54.824+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.MessageProducerService     : 消息发送成功: brandCode=攀攀, eslId=1947223724923940865, executor=mqtt
2025-07-24T09:27:54.824+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.EslRefreshService          : 价签刷新成功: eslId=1947223724923940865, brandCode=攀攀
2025-07-24T09:27:54.824+08:00  INFO 35888 --- [nio-8999-exec-1] c.p.d.service.TemplateServiceImpl        : 价签刷新请求已处理，价签ID: 1947223724923940865
2025-07-24T09:27:56.263+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.TemplateServiceImpl        : 开始刷新价签，价签ID: 1947313501023105026, 门店编码: 0002, 品牌编码: PANDA
2025-07-24T09:27:56.263+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.EslRefreshService          : 开始刷新价签: EslRefreshRequest(eslId=1947313501023105026, brandCode=PANDA, forceRefresh=false, storeCode=0002)
2025-07-24T09:27:56.263+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.EslRefreshService          : 请求中的品牌编码: PANDA
2025-07-24T09:27:56.263+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.downloadcf.service.DataService       : 获取价签完整数据: 1947313501023105026
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7bf291d4] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947313501023105026(String)
<==    Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<==        Row: 1947313501023105026, -1, null, NOT_DELETE, 2025-07-21 23:11:33, 1543837863788879871, null, null, 1C000000100B, 0002, null, 4.20T, 1947312777082040322, null, null, null, null, null, null, null, null, null, null, null, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7bf291d4]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@710afaa4] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947313501023105026(String)
<==    Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<==        Row: 1947313501023105026, -1, null, NOT_DELETE, 2025-07-21 23:11:33, 1543837863788879871, null, null, 1C000000100B, 0002, null, 4.20T, 1947312777082040322, null, null, null, null, null, null, null, null, null, null, null, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@710afaa4]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5b1b3185] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE , EXT_JSON, PRODUCT_DESCRIPTION from panda_product where ID = ?
==> Parameters: 1947312777082040322(String)
<==    Columns: ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE, EXT_JSON, PRODUCT_DESCRIPTION
<==        Row: 1947312777082040322, -1, NOT_DELETE, 2025-07-21 23:08:40, 1543837863788879871, null, null, 002, 0002, 002, 002, PRODUCT_FRUIT, 攀攀, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 002, http://localhost:82/dev/file/download?id=1947312644269404161&Domain=http://localhost:81, 002, 002, 002, ge, 2.00, 002, 2, 1947312285182455810, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5b1b3185]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@238f3c4c] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947313501023105026(String)
<==    Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<==        Row: 1947313501023105026, -1, null, NOT_DELETE, 2025-07-21 23:11:33, 1543837863788879871, null, null, 1C000000100B, 0002, null, 4.20T, 1947312777082040322, null, null, null, null, null, null, null, null, null, null, null, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@238f3c4c]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@563211b6] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE , EXT_JSON, PRODUCT_DESCRIPTION from panda_product where ID = ?
==> Parameters: 1947312777082040322(String)
<==    Columns: ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE, EXT_JSON, PRODUCT_DESCRIPTION
<==        Row: 1947312777082040322, -1, NOT_DELETE, 2025-07-21 23:08:40, 1543837863788879871, null, null, 002, 0002, 002, 002, PRODUCT_FRUIT, 攀攀, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 002, http://localhost:82/dev/file/download?id=1947312644269404161&Domain=http://localhost:81, 002, 002, 002, ge, 2.00, 002, 2, 1947312285182455810, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@563211b6]
2025-07-24T09:27:56.279+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.downloadcf.service.DataService       : 查询模板，模板ID: 1947312285182455810
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@388bd572] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER , CONTENT, EXT_JSON from print_template_design where ID = ?
==> Parameters: 1947312285182455810(String)
<==    Columns: ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, CONTENT, EXT_JSON
<==        Row: 1947312285182455810, -1, 3, PTD-1753110402676-995, 4.20T, 99, NOT_DELETE, 2025-07-21 23:06:43, 1543837863788879871, 2025-07-21 23:07:24, 1543837863788879871, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@388bd572]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@16102922] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select 'false' as QUERYID, ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, BRAND_CODE, TEMPLATE_FIELD, FIELD_CODE, FORMAT_RULE from esl_brand_field_mapping WHERE ( BRAND_CODE = ? )
==> Parameters: 攀攀(String)
<==    Columns: QUERYID, ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, BRAND_CODE, TEMPLATE_FIELD, FIELD_CODE, FORMAT_RULE
<==        Row: false, 1946188424516816897, -1, null, NOT_DELETE, 2025-07-18 20:40:54, 1543837863788879871, 2025-07-19 00:22:38, 1543837863788879871, 攀攀, code, PRODUCT_ID, 商品名称
<==        Row: false, 1946188616733380609, -1, null, NOT_DELETE, 2025-07-18 20:41:39, 1543837863788879871, 2025-07-19 00:22:35, 1543837863788879871, 攀攀, name, PRODUCT_NAME, 商品名称
<==        Row: false, 1946230416441430018, -1, null, NOT_DELETE, 2025-07-18 23:27:45, 1543837863788879871, 2025-07-19 00:22:31, 1543837863788879871, 攀攀, F_01, PRODUCT_RETAIL_PRICE, 价格
<==        Row: false, 1946244176275132418, -1, null, NOT_DELETE, 2025-07-19 00:22:26, 1543837863788879871, null, null, 攀攀, F_02, PRODUCT_CATEGORY, 商品分类
<==        Row: false, 1946244319741300737, -1, null, NOT_DELETE, 2025-07-19 00:23:00, 1543837863788879871, null, null, 攀攀, F_03, PRODUCT_COST_PRICE, 商品成本价
<==        Row: false, 1946244382848798721, -1, null, NOT_DELETE, 2025-07-19 00:23:15, 1543837863788879871, null, null, 攀攀, F_04, PRODUCT_SPECIFICATION, 商品规格
<==        Row: false, 1946244450377093122, -1, null, NOT_DELETE, 2025-07-19 00:23:31, 1543837863788879871, null, null, 攀攀, F_05, PRODUCT_MEMBERSHIP_PRICE, 商品会员价
<==        Row: false, 1946244527699087362, -1, null, NOT_DELETE, 2025-07-19 00:23:50, 1543837863788879871, null, null, 攀攀, F_06, PRODUCT_DISCOUNT_PRICE, 商品折扣价
<==        Row: false, 1946244599702704129, -1, null, NOT_DELETE, 2025-07-19 00:24:07, 1543837863788879871, null, null, 攀攀, F_07, PRODUCT_DISCOUNT, 商品折扣（例如：0.8 表示8折）
<==        Row: false, 1946244674935934978, -1, null, NOT_DELETE, 2025-07-19 00:24:25, 1543837863788879871, null, null, 攀攀, F_08, PRODUCT_WHOLESALE_PRICE, 商品批发
<==        Row: false, 1946244741017194498, -1, null, NOT_DELETE, 2025-07-19 00:24:40, 1543837863788879871, null, null, 攀攀, F_09, PRODUCT_MATERIAL, 商品材质
<==        Row: false, 1946244808008617986, -1, null, NOT_DELETE, 2025-07-19 00:24:56, 1543837863788879871, null, null, 攀攀, F_10, PRODUCT_IMAGE, 商品图片（路径或URL）
<==        Row: false, 1946244885259309057, -1, null, NOT_DELETE, 2025-07-19 00:25:15, 1543837863788879871, null, null, 攀攀, F_11, PRODUCT_ORIGIN, 商品产地
<==        Row: false, 1946244968667238402, -1, null, NOT_DELETE, 2025-07-19 00:25:35, 1543837863788879871, null, null, 攀攀, F_20, PRODUCT_DESCRIPTION, 商品描述
<==        Row: false, 1946245050686853122, -1, null, NOT_DELETE, 2025-07-19 00:25:54, 1543837863788879871, null, null, 攀攀, F_12, PRODUCT_UNIT, 商品单位（如：个、件、瓶等）
<==        Row: false, 1946245165296209922, -1, null, NOT_DELETE, 2025-07-19 00:26:22, 1543837863788879871, null, null, 攀攀, F_13, PRODUCT_WEIGHT, 产品重量（单位：kg）
<==        Row: false, 1946245224305872898, -1, null, NOT_DELETE, 2025-07-19 00:26:36, 1543837863788879871, null, null, 攀攀, F_14, PRODUCT_STATUS, 商品状态（如：上架、下架、预售等）
<==        Row: false, 1946245284653518849, -1, null, NOT_DELETE, 2025-07-19 00:26:50, 1543837863788879871, null, null, 攀攀, F_32, PRODUCT_STOCK, 商品库存
<==        Row: false, 1947173347558027266, -1, null, NOT_DELETE, 2025-07-21 13:54:37, 1543837863788879871, null, null, 攀攀, QRCODE, PRODUCT_QRCODE, 二维码
<==        Row: false, 1947173427983806466, -1, null, NOT_DELETE, 2025-07-21 13:54:57, 1543837863788879871, null, null, 攀攀, QRCODE, PRODUCT_BARCODE, 条形码
<==      Total: 20
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@16102922]
2025-07-24T09:27:56.288+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.downloadcf.service.DataService       : 字段映射配置查询结果: brandCode=攀攀, mappingCount=20
2025-07-24T09:27:56.289+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.downloadcf.service.DataService       : 成功获取价签完整数据: eslId=1947313501023105026, productId=1947312777082040322, templateId=1947312285182455810
2025-07-24T09:27:56.289+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.EslRefreshService          : 获取到价签数据，商品品牌: 攀攀
2025-07-24T09:27:56.289+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.EslRefreshService          : 请求参数中的品牌编码: PANDA (用于选择解析器)
2025-07-24T09:27:56.289+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.EslRefreshService          : 商品数据中的品牌字段: 攀攀
2025-07-24T09:27:56.289+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.EslRefreshService          : 实际使用的品牌编码: 攀攀 (用于查找品牌适配器)
2025-07-24T09:27:56.289+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.EslRefreshService          : 获取到品牌适配器: PandaBrandAdapter
2025-07-24T09:27:56.289+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.EslRefreshService          : 品牌适配器支持的品牌编码: 攀攀
2025-07-24T09:27:56.289+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.EslRefreshService          : 数据验证通过
2025-07-24T09:27:56.289+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 开始转换攀攀品牌数据: eslId=1947313501023105026
2025-07-24T09:27:56.289+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 开始构建数据映射，商品ID: 002, 商品名称: 002
2025-07-24T09:27:56.289+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 使用字段映射配置，映射数量: 20
2025-07-24T09:27:56.289+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_ID -> code (格式: 商品名称)
2025-07-24T09:27:56.289+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_NAME -> name (格式: 商品名称)
2025-07-24T09:27:56.289+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_RETAIL_PRICE -> F_01 (格式: 价格)
2025-07-24T09:27:56.289+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_CATEGORY -> F_02 (格式: 商品分类)
2025-07-24T09:27:56.289+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_COST_PRICE -> F_03 (格式: 商品成本价)
2025-07-24T09:27:56.290+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_SPECIFICATION -> F_04 (格式: 商品规格)
2025-07-24T09:27:56.290+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_MEMBERSHIP_PRICE -> F_05 (格式: 商品会员价)
2025-07-24T09:27:56.290+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_DISCOUNT_PRICE -> F_06 (格式: 商品折扣价)
2025-07-24T09:27:56.290+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_DISCOUNT -> F_07 (格式: 商品折扣（例如：0.8 表示8折）)
2025-07-24T09:27:56.290+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_WHOLESALE_PRICE -> F_08 (格式: 商品批发)
2025-07-24T09:27:56.290+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_MATERIAL -> F_09 (格式: 商品材质)
2025-07-24T09:27:56.290+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_IMAGE -> F_10 (格式: 商品图片（路径或URL）)
2025-07-24T09:27:56.290+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_ORIGIN -> F_11 (格式: 商品产地)
2025-07-24T09:27:56.290+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_DESCRIPTION -> F_20 (格式: 商品描述)
2025-07-24T09:27:56.290+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_UNIT -> F_12 (格式: 商品单位（如：个、件、瓶等）)
2025-07-24T09:27:56.290+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_WEIGHT -> F_13 (格式: 产品重量（单位：kg）)
2025-07-24T09:27:56.290+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_STATUS -> F_14 (格式: 商品状态（如：上架、下架、预售等）)
2025-07-24T09:27:56.290+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_STOCK -> F_32 (格式: 商品库存)
2025-07-24T09:27:56.290+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_QRCODE -> QRCODE (格式: 二维码)
2025-07-24T09:27:56.290+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_BARCODE -> QRCODE (格式: 条形码)
2025-07-24T09:27:56.290+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 字段映射完成，实际映射字段数量: 19
2025-07-24T09:27:56.290+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 映射结果: {GOODS_CODE=002, GOODS_NAME=002, F_1=2.0, F_2=002, F_3=2.0, F_4=PRODUCT_FRUIT, F_5=2.0, F_6=2.0, F_7=2.0, F_8=2.0, F_9=002, F_10=http://localhost:82/dev/file/download?id=1947312644269404161&Domain=http://localhost:81, F_11=002, F_20=<p>002</p>, F_12=ge, F_13=2.0, F_14=002, F_32=2, QRCODE=002}
2025-07-24T09:27:56.292+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 攀攀品牌数据转换完成: eslId=1947313501023105026, actualEslId=1C000000100B, checksum=12780dc2f9234a1406c5853435f7296a
2025-07-24T09:27:56.292+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.EslRefreshService          : 数据转换完成，输出数据类型: BrandOutputData
2025-07-24T09:27:56.292+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.EslRefreshService          : 准备发送消息，使用品牌编码: 攀攀
2025-07-24T09:27:56.292+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.MessageProducerService     : 开始发送消息: brandCode=攀攀, eslId=1947313501023105026
2025-07-24T09:27:56.292+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.MessageProducerService     : 开始构建MQTT载荷，ESL ID: 1947313501023105026, 门店代码: 0002
2025-07-24T09:27:56.292+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.MessageProducerService     : 开始构建data数组，ESL ID: 1947313501023105026
2025-07-24T09:27:56.292+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.MessageProducerService     : 开始获取模板名称，ESL ID: 1947313501023105026
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@752ee14f] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947313501023105026(String)
<==    Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<==        Row: 1947313501023105026, -1, null, NOT_DELETE, 2025-07-21 23:11:33, 1543837863788879871, null, null, 1C000000100B, 0002, null, 4.20T, 1947312777082040322, null, null, null, null, null, null, null, null, null, null, null, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@752ee14f]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7bf9ffa2] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE , EXT_JSON, PRODUCT_DESCRIPTION from panda_product where ID = ?
==> Parameters: 1947312777082040322(String)
<==    Columns: ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE, EXT_JSON, PRODUCT_DESCRIPTION
<==        Row: 1947312777082040322, -1, NOT_DELETE, 2025-07-21 23:08:40, 1543837863788879871, null, null, 002, 0002, 002, 002, PRODUCT_FRUIT, 攀攀, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 002, http://localhost:82/dev/file/download?id=1947312644269404161&Domain=http://localhost:81, 002, 002, 002, ge, 2.00, 002, 2, 1947312285182455810, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7bf9ffa2]
2025-07-24T09:27:56.299+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.downloadcf.service.DataService       : 查询模板，模板ID: 1947312285182455810
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2ed03e2e] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER , CONTENT, EXT_JSON from print_template_design where ID = ?
==> Parameters: 1947312285182455810(String)
<==    Columns: ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, CONTENT, EXT_JSON
<==        Row: 1947312285182455810, -1, 3, PTD-1753110402676-995, 4.20T, 99, NOT_DELETE, 2025-07-21 23:06:43, 1543837863788879871, 2025-07-21 23:07:24, 1543837863788879871, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2ed03e2e]
2025-07-24T09:27:56.301+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.MessageProducerService     : 找到模板数据，模板ID: 1947312285182455810, 模板名称: 3
2025-07-24T09:27:56.301+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.MessageProducerService     : 开始从CONTENT字段解析模板名称
2025-07-24T09:27:56.301+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.MessageProducerService     : CONTENT字段内容长度: 337 字符
2025-07-24T09:27:56.301+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.MessageProducerService     : 从panels[0].name提取到模板名称: 3
2025-07-24T09:27:56.302+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.MessageProducerService     : 从CONTENT字段解析到模板名称: 3
2025-07-24T09:27:56.302+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.MessageProducerService     : 开始获取model值，ESL ID: 1947313501023105026
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4a1683a8] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947313501023105026(String)
<==    Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<==        Row: 1947313501023105026, -1, null, NOT_DELETE, 2025-07-21 23:11:33, 1543837863788879871, null, null, 1C000000100B, 0002, null, 4.20T, 1947312777082040322, null, null, null, null, null, null, null, null, null, null, null, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4a1683a8]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7d450c83] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE , EXT_JSON, PRODUCT_DESCRIPTION from panda_product where ID = ?
==> Parameters: 1947312777082040322(String)
<==    Columns: ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE, EXT_JSON, PRODUCT_DESCRIPTION
<==        Row: 1947312777082040322, -1, NOT_DELETE, 2025-07-21 23:08:40, 1543837863788879871, null, null, 002, 0002, 002, 002, PRODUCT_FRUIT, 攀攀, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 002, http://localhost:82/dev/file/download?id=1947312644269404161&Domain=http://localhost:81, 002, 002, 002, ge, 2.00, 002, 2, 1947312285182455810, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7d450c83]
2025-07-24T09:27:56.308+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.downloadcf.service.DataService       : 查询模板，模板ID: 1947312285182455810
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4ee2bd0c] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER , CONTENT, EXT_JSON from print_template_design where ID = ?
==> Parameters: 1947312285182455810(String)
<==    Columns: ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, CONTENT, EXT_JSON
<==        Row: 1947312285182455810, -1, 3, PTD-1753110402676-995, 4.20T, 99, NOT_DELETE, 2025-07-21 23:06:43, 1543837863788879871, 2025-07-21 23:07:24, 1543837863788879871, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4ee2bd0c]
2025-07-24T09:27:56.311+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.MessageProducerService     : 找到模板数据，模板ID: 1947312285182455810, CATEGORY: 4.20T
2025-07-24T09:27:56.312+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.MessageProducerService     : 从模板中解析到屏幕类型: 4.20T
2025-07-24T09:27:56.312+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.MessageProducerService     : 开始转换屏幕类型到model，输入屏幕类型: 4.20t
2025-07-24T09:27:56.312+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.MessageProducerService     : 标准化后的屏幕类型: 4.20t
2025-07-24T09:27:56.312+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.MessageProducerService     : 屏幕类型转换完成: 4.20t -> 1C
2025-07-24T09:27:56.312+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.MessageProducerService     : 屏幕类型 4.20T 转换为model: 1C
2025-07-24T09:27:56.312+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.MessageProducerService     : data数组构建完成，tag: 30786325581835, tmpl: 3, model: 1C, taskid: 76312, token: 118628
2025-07-24T09:27:56.312+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.MessageProducerService     : MQTT载荷构建完成，载荷大小: 5 字段
2025-07-24T09:27:56.312+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.downloadcf.executor.MqttExecutor     : 执行MQTT消息发送: destination=esl/server/data/0002, eslId=1947313501023105026
2025-07-24T09:27:56.313+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.downloadcf.executor.MqttExecutor     : MQTT消息发送成功: eslId=1947313501023105026
2025-07-24T09:27:56.313+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.MessageProducerService     : 消息发送成功: brandCode=攀攀, eslId=1947313501023105026, executor=mqtt
2025-07-24T09:27:56.313+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.EslRefreshService          : 价签刷新成功: eslId=1947313501023105026, brandCode=攀攀
2025-07-24T09:27:56.313+08:00  INFO 35888 --- [nio-8999-exec-2] c.p.d.service.TemplateServiceImpl        : 价签刷新请求已处理，价签ID: 1947313501023105026
2025-07-24T09:31:39.899+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.TemplateServiceImpl        : 开始刷新价签，价签ID: 1947313501023105026, 门店编码: 0002, 品牌编码: PANDA
2025-07-24T09:31:39.899+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.EslRefreshService          : 开始刷新价签: EslRefreshRequest(eslId=1947313501023105026, brandCode=PANDA, forceRefresh=false, storeCode=0002)
2025-07-24T09:31:39.899+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.EslRefreshService          : 请求中的品牌编码: PANDA
2025-07-24T09:31:39.899+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.downloadcf.service.DataService       : 获取价签完整数据: 1947313501023105026
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2b348eb7] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947313501023105026(String)
<==    Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<==        Row: 1947313501023105026, -1, null, NOT_DELETE, 2025-07-21 23:11:33, 1543837863788879871, null, null, 1C000000100B, 0002, null, 4.20T, 1947312777082040322, null, null, null, null, null, null, null, null, null, null, null, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2b348eb7]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1408fc] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947313501023105026(String)
<==    Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<==        Row: 1947313501023105026, -1, null, NOT_DELETE, 2025-07-21 23:11:33, 1543837863788879871, null, null, 1C000000100B, 0002, null, 4.20T, 1947312777082040322, null, null, null, null, null, null, null, null, null, null, null, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1408fc]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@95661c6] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE , EXT_JSON, PRODUCT_DESCRIPTION from panda_product where ID = ?
==> Parameters: 1947312777082040322(String)
<==    Columns: ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE, EXT_JSON, PRODUCT_DESCRIPTION
<==        Row: 1947312777082040322, -1, NOT_DELETE, 2025-07-21 23:08:40, 1543837863788879871, null, null, 002, 0002, 002, 002, PRODUCT_FRUIT, 攀攀, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 002, http://localhost:82/dev/file/download?id=1947312644269404161&Domain=http://localhost:81, 002, 002, 002, ge, 2.00, 002, 2, 1947312285182455810, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@95661c6]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@8d2a4aa] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947313501023105026(String)
<==    Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<==        Row: 1947313501023105026, -1, null, NOT_DELETE, 2025-07-21 23:11:33, 1543837863788879871, null, null, 1C000000100B, 0002, null, 4.20T, 1947312777082040322, null, null, null, null, null, null, null, null, null, null, null, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@8d2a4aa]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5f2ced73] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE , EXT_JSON, PRODUCT_DESCRIPTION from panda_product where ID = ?
==> Parameters: 1947312777082040322(String)
<==    Columns: ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE, EXT_JSON, PRODUCT_DESCRIPTION
<==        Row: 1947312777082040322, -1, NOT_DELETE, 2025-07-21 23:08:40, 1543837863788879871, null, null, 002, 0002, 002, 002, PRODUCT_FRUIT, 攀攀, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 002, http://localhost:82/dev/file/download?id=1947312644269404161&Domain=http://localhost:81, 002, 002, 002, ge, 2.00, 002, 2, 1947312285182455810, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5f2ced73]
2025-07-24T09:31:39.924+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.downloadcf.service.DataService       : 查询模板，模板ID: 1947312285182455810
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6212d4c7] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER , CONTENT, EXT_JSON from print_template_design where ID = ?
==> Parameters: 1947312285182455810(String)
<==    Columns: ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, CONTENT, EXT_JSON
<==        Row: 1947312285182455810, -1, 3, PTD-1753110402676-995, 4.20T, 99, NOT_DELETE, 2025-07-21 23:06:43, 1543837863788879871, 2025-07-21 23:07:24, 1543837863788879871, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6212d4c7]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@48f3e85f] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select 'false' as QUERYID, ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, BRAND_CODE, TEMPLATE_FIELD, FIELD_CODE, FORMAT_RULE from esl_brand_field_mapping WHERE ( BRAND_CODE = ? )
==> Parameters: 攀攀(String)
<==    Columns: QUERYID, ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, BRAND_CODE, TEMPLATE_FIELD, FIELD_CODE, FORMAT_RULE
<==        Row: false, 1946188424516816897, -1, null, NOT_DELETE, 2025-07-18 20:40:54, 1543837863788879871, 2025-07-19 00:22:38, 1543837863788879871, 攀攀, code, PRODUCT_ID, 商品名称
<==        Row: false, 1946188616733380609, -1, null, NOT_DELETE, 2025-07-18 20:41:39, 1543837863788879871, 2025-07-19 00:22:35, 1543837863788879871, 攀攀, name, PRODUCT_NAME, 商品名称
<==        Row: false, 1946230416441430018, -1, null, NOT_DELETE, 2025-07-18 23:27:45, 1543837863788879871, 2025-07-19 00:22:31, 1543837863788879871, 攀攀, F_01, PRODUCT_RETAIL_PRICE, 价格
<==        Row: false, 1946244176275132418, -1, null, NOT_DELETE, 2025-07-19 00:22:26, 1543837863788879871, null, null, 攀攀, F_02, PRODUCT_CATEGORY, 商品分类
<==        Row: false, 1946244319741300737, -1, null, NOT_DELETE, 2025-07-19 00:23:00, 1543837863788879871, null, null, 攀攀, F_03, PRODUCT_COST_PRICE, 商品成本价
<==        Row: false, 1946244382848798721, -1, null, NOT_DELETE, 2025-07-19 00:23:15, 1543837863788879871, null, null, 攀攀, F_04, PRODUCT_SPECIFICATION, 商品规格
<==        Row: false, 1946244450377093122, -1, null, NOT_DELETE, 2025-07-19 00:23:31, 1543837863788879871, null, null, 攀攀, F_05, PRODUCT_MEMBERSHIP_PRICE, 商品会员价
<==        Row: false, 1946244527699087362, -1, null, NOT_DELETE, 2025-07-19 00:23:50, 1543837863788879871, null, null, 攀攀, F_06, PRODUCT_DISCOUNT_PRICE, 商品折扣价
<==        Row: false, 1946244599702704129, -1, null, NOT_DELETE, 2025-07-19 00:24:07, 1543837863788879871, null, null, 攀攀, F_07, PRODUCT_DISCOUNT, 商品折扣（例如：0.8 表示8折）
<==        Row: false, 1946244674935934978, -1, null, NOT_DELETE, 2025-07-19 00:24:25, 1543837863788879871, null, null, 攀攀, F_08, PRODUCT_WHOLESALE_PRICE, 商品批发
<==        Row: false, 1946244741017194498, -1, null, NOT_DELETE, 2025-07-19 00:24:40, 1543837863788879871, null, null, 攀攀, F_09, PRODUCT_MATERIAL, 商品材质
<==        Row: false, 1946244808008617986, -1, null, NOT_DELETE, 2025-07-19 00:24:56, 1543837863788879871, null, null, 攀攀, F_10, PRODUCT_IMAGE, 商品图片（路径或URL）
<==        Row: false, 1946244885259309057, -1, null, NOT_DELETE, 2025-07-19 00:25:15, 1543837863788879871, null, null, 攀攀, F_11, PRODUCT_ORIGIN, 商品产地
<==        Row: false, 1946244968667238402, -1, null, NOT_DELETE, 2025-07-19 00:25:35, 1543837863788879871, null, null, 攀攀, F_20, PRODUCT_DESCRIPTION, 商品描述
<==        Row: false, 1946245050686853122, -1, null, NOT_DELETE, 2025-07-19 00:25:54, 1543837863788879871, null, null, 攀攀, F_12, PRODUCT_UNIT, 商品单位（如：个、件、瓶等）
<==        Row: false, 1946245165296209922, -1, null, NOT_DELETE, 2025-07-19 00:26:22, 1543837863788879871, null, null, 攀攀, F_13, PRODUCT_WEIGHT, 产品重量（单位：kg）
<==        Row: false, 1946245224305872898, -1, null, NOT_DELETE, 2025-07-19 00:26:36, 1543837863788879871, null, null, 攀攀, F_14, PRODUCT_STATUS, 商品状态（如：上架、下架、预售等）
<==        Row: false, 1946245284653518849, -1, null, NOT_DELETE, 2025-07-19 00:26:50, 1543837863788879871, null, null, 攀攀, F_32, PRODUCT_STOCK, 商品库存
<==        Row: false, 1947173347558027266, -1, null, NOT_DELETE, 2025-07-21 13:54:37, 1543837863788879871, null, null, 攀攀, QRCODE, PRODUCT_QRCODE, 二维码
<==        Row: false, 1947173427983806466, -1, null, NOT_DELETE, 2025-07-21 13:54:57, 1543837863788879871, null, null, 攀攀, QRCODE, PRODUCT_BARCODE, 条形码
<==      Total: 20
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@48f3e85f]
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.downloadcf.service.DataService       : 字段映射配置查询结果: brandCode=攀攀, mappingCount=20
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.downloadcf.service.DataService       : 成功获取价签完整数据: eslId=1947313501023105026, productId=1947312777082040322, templateId=1947312285182455810
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.EslRefreshService          : 获取到价签数据，商品品牌: 攀攀
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.EslRefreshService          : 请求参数中的品牌编码: PANDA (用于选择解析器)
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.EslRefreshService          : 商品数据中的品牌字段: 攀攀
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.EslRefreshService          : 实际使用的品牌编码: 攀攀 (用于查找品牌适配器)
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.EslRefreshService          : 获取到品牌适配器: PandaBrandAdapter
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.EslRefreshService          : 品牌适配器支持的品牌编码: 攀攀
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.EslRefreshService          : 数据验证通过
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 开始转换攀攀品牌数据: eslId=1947313501023105026
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 开始构建数据映射，商品ID: 002, 商品名称: 002
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 使用字段映射配置，映射数量: 20
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_ID -> code (格式: 商品名称)
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_NAME -> name (格式: 商品名称)
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_RETAIL_PRICE -> F_01 (格式: 价格)
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_CATEGORY -> F_02 (格式: 商品分类)
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_COST_PRICE -> F_03 (格式: 商品成本价)
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_SPECIFICATION -> F_04 (格式: 商品规格)
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_MEMBERSHIP_PRICE -> F_05 (格式: 商品会员价)
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_DISCOUNT_PRICE -> F_06 (格式: 商品折扣价)
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_DISCOUNT -> F_07 (格式: 商品折扣（例如：0.8 表示8折）)
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_WHOLESALE_PRICE -> F_08 (格式: 商品批发)
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_MATERIAL -> F_09 (格式: 商品材质)
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_IMAGE -> F_10 (格式: 商品图片（路径或URL）)
2025-07-24T09:31:39.933+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_ORIGIN -> F_11 (格式: 商品产地)
2025-07-24T09:31:39.934+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_DESCRIPTION -> F_20 (格式: 商品描述)
2025-07-24T09:31:39.934+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_UNIT -> F_12 (格式: 商品单位（如：个、件、瓶等）)
2025-07-24T09:31:39.934+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_WEIGHT -> F_13 (格式: 产品重量（单位：kg）)
2025-07-24T09:31:39.934+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_STATUS -> F_14 (格式: 商品状态（如：上架、下架、预售等）)
2025-07-24T09:31:39.934+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_STOCK -> F_32 (格式: 商品库存)
2025-07-24T09:31:39.934+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_QRCODE -> QRCODE (格式: 二维码)
2025-07-24T09:31:39.934+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 字段映射配置: PRODUCT_BARCODE -> QRCODE (格式: 条形码)
2025-07-24T09:31:39.934+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 字段映射完成，实际映射字段数量: 19
2025-07-24T09:31:39.934+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 映射结果: {GOODS_CODE=002, GOODS_NAME=002, F_1=2.0, F_2=002, F_3=2.0, F_4=PRODUCT_FRUIT, F_5=2.0, F_6=2.0, F_7=2.0, F_8=2.0, F_9=002, F_10=http://localhost:82/dev/file/download?id=1947312644269404161&Domain=http://localhost:81, F_11=002, F_20=<p>002</p>, F_12=ge, F_13=2.0, F_14=002, F_32=2, QRCODE=002}
2025-07-24T09:31:39.934+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.adapter.PandaBrandAdapter          : 攀攀品牌数据转换完成: eslId=1947313501023105026, actualEslId=1C000000100B, checksum=12780dc2f9234a1406c5853435f7296a
2025-07-24T09:31:39.934+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.EslRefreshService          : 数据转换完成，输出数据类型: BrandOutputData
2025-07-24T09:31:39.934+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.EslRefreshService          : 准备发送消息，使用品牌编码: 攀攀
2025-07-24T09:31:39.935+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.MessageProducerService     : 开始发送消息: brandCode=攀攀, eslId=1947313501023105026
2025-07-24T09:31:39.935+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.MessageProducerService     : 开始构建MQTT载荷，ESL ID: 1947313501023105026, 门店代码: 0002
2025-07-24T09:31:39.935+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.MessageProducerService     : 开始构建data数组，ESL ID: 1947313501023105026
2025-07-24T09:31:39.935+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.MessageProducerService     : 开始获取模板名称，ESL ID: 1947313501023105026
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@446f6d9e] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947313501023105026(String)
<==    Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<==        Row: 1947313501023105026, -1, null, NOT_DELETE, 2025-07-21 23:11:33, 1543837863788879871, null, null, 1C000000100B, 0002, null, 4.20T, 1947312777082040322, null, null, null, null, null, null, null, null, null, null, null, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@446f6d9e]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@32697cf6] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE , EXT_JSON, PRODUCT_DESCRIPTION from panda_product where ID = ?
==> Parameters: 1947312777082040322(String)
<==    Columns: ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE, EXT_JSON, PRODUCT_DESCRIPTION
<==        Row: 1947312777082040322, -1, NOT_DELETE, 2025-07-21 23:08:40, 1543837863788879871, null, null, 002, 0002, 002, 002, PRODUCT_FRUIT, 攀攀, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 002, http://localhost:82/dev/file/download?id=1947312644269404161&Domain=http://localhost:81, 002, 002, 002, ge, 2.00, 002, 2, 1947312285182455810, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@32697cf6]
2025-07-24T09:31:39.941+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.downloadcf.service.DataService       : 查询模板，模板ID: 1947312285182455810
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@50f4dbaf] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER , CONTENT, EXT_JSON from print_template_design where ID = ?
==> Parameters: 1947312285182455810(String)
<==    Columns: ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, CONTENT, EXT_JSON
<==        Row: 1947312285182455810, -1, 3, PTD-1753110402676-995, 4.20T, 99, NOT_DELETE, 2025-07-21 23:06:43, 1543837863788879871, 2025-07-21 23:07:24, 1543837863788879871, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@50f4dbaf]
2025-07-24T09:31:39.944+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.MessageProducerService     : 找到模板数据，模板ID: 1947312285182455810, 模板名称: 3
2025-07-24T09:31:39.944+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.MessageProducerService     : 开始从CONTENT字段解析模板名称
2025-07-24T09:31:39.944+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.MessageProducerService     : CONTENT字段内容长度: 337 字符
2025-07-24T09:31:39.944+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.MessageProducerService     : 从panels[0].name提取到模板名称: 3
2025-07-24T09:31:39.944+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.MessageProducerService     : 从CONTENT字段解析到模板名称: 3
2025-07-24T09:31:39.944+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.MessageProducerService     : 开始获取model值，ESL ID: 1947313501023105026
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@48d503b7] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947313501023105026(String)
<==    Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<==        Row: 1947313501023105026, -1, null, NOT_DELETE, 2025-07-21 23:11:33, 1543837863788879871, null, null, 1C000000100B, 0002, null, 4.20T, 1947312777082040322, null, null, null, null, null, null, null, null, null, null, null, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@48d503b7]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@b9a5ff4] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE , EXT_JSON, PRODUCT_DESCRIPTION from panda_product where ID = ?
==> Parameters: 1947312777082040322(String)
<==    Columns: ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE, EXT_JSON, PRODUCT_DESCRIPTION
<==        Row: 1947312777082040322, -1, NOT_DELETE, 2025-07-21 23:08:40, 1543837863788879871, null, null, 002, 0002, 002, 002, PRODUCT_FRUIT, 攀攀, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 002, http://localhost:82/dev/file/download?id=1947312644269404161&Domain=http://localhost:81, 002, 002, 002, ge, 2.00, 002, 2, 1947312285182455810, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@b9a5ff4]
2025-07-24T09:31:39.951+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.downloadcf.service.DataService       : 查询模板，模板ID: 1947312285182455810
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@121061e] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7d3fb56] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER , CONTENT, EXT_JSON from print_template_design where ID = ?
==> Parameters: 1947312285182455810(String)
<==    Columns: ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, CONTENT, EXT_JSON
<==        Row: 1947312285182455810, -1, 3, PTD-1753110402676-995, 4.20T, 99, NOT_DELETE, 2025-07-21 23:06:43, 1543837863788879871, 2025-07-21 23:07:24, 1543837863788879871, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@121061e]
2025-07-24T09:31:39.954+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.MessageProducerService     : 找到模板数据，模板ID: 1947312285182455810, CATEGORY: 4.20T
2025-07-24T09:31:39.955+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.MessageProducerService     : 从模板中解析到屏幕类型: 4.20T
2025-07-24T09:31:39.955+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.MessageProducerService     : 开始转换屏幕类型到model，输入屏幕类型: 4.20t
2025-07-24T09:31:39.955+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.MessageProducerService     : 标准化后的屏幕类型: 4.20t
2025-07-24T09:31:39.955+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.MessageProducerService     : 屏幕类型转换完成: 4.20t -> 1C
2025-07-24T09:31:39.955+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.MessageProducerService     : 屏幕类型 4.20T 转换为model: 1C
2025-07-24T09:31:39.955+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.MessageProducerService     : data数组构建完成，tag: 30786325581835, tmpl: 3, model: 1C, taskid: 99955, token: 893038
2025-07-24T09:31:39.955+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.MessageProducerService     : MQTT载荷构建完成，载荷大小: 5 字段
2025-07-24T09:31:39.955+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.downloadcf.executor.MqttExecutor     : 执行MQTT消息发送: destination=esl/server/data/0002, eslId=1947313501023105026
2025-07-24T09:31:39.955+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.downloadcf.executor.MqttExecutor     : MQTT消息发送成功: eslId=1947313501023105026
2025-07-24T09:31:39.955+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.MessageProducerService     : 消息发送成功: brandCode=攀攀, eslId=1947313501023105026, executor=mqtt
2025-07-24T09:31:39.955+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.EslRefreshService          : 价签刷新成功: eslId=1947313501023105026, brandCode=攀攀
2025-07-24T09:31:39.955+08:00  INFO 35888 --- [nio-8999-exec-5] c.p.d.service.TemplateServiceImpl        : 价签刷新请求已处理，价签ID: 1947313501023105026


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



效果很完美。就是。请检查代码。现在调用api之后是先处理完毕之后通过生产者添加到队列里面。然后消费者去消费的吗？