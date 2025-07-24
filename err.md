
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.5)

2025-07-24T08:22:42.449+08:00  INFO 51612 --- [           main] c.p.downloadcf.DownloadcfApplication     : Starting DownloadcfApplication using Java 22.0.2 with PID 51612 (E:\IdeaProjects\cfdownloadexample\target\classes started by Ming in E:\IdeaProjects\cfdownloadexample)
2025-07-24T08:22:42.451+08:00  INFO 51612 --- [           main] c.p.downloadcf.DownloadcfApplication     : No active profile set, falling back to 1 default profile: "default"
2025-07-24T08:22:43.578+08:00  INFO 51612 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Multiple Spring Data modules found, entering strict repository configuration mode
2025-07-24T08:22:43.581+08:00  INFO 51612 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data Redis repositories in DEFAULT mode.
2025-07-24T08:22:43.623+08:00  INFO 51612 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 23 ms. Found 0 Redis repository interfaces.
2025-07-24T08:22:43.886+08:00  INFO 51612 --- [           main] faultConfiguringBeanFactoryPostProcessor : No bean named 'errorChannel' has been explicitly defined. Therefore, a default PublishSubscribeChannel will be created.
2025-07-24T08:22:43.895+08:00  INFO 51612 --- [           main] faultConfiguringBeanFactoryPostProcessor : No bean named 'integrationHeaderChannelRegistry' has been explicitly defined. Therefore, a default DefaultHeaderChannelRegistry will be created.
2025-07-24T08:22:45.210+08:00  INFO 51612 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8999 (http)
2025-07-24T08:22:45.232+08:00  INFO 51612 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2025-07-24T08:22:45.232+08:00  INFO 51612 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.20]
2025-07-24T08:22:45.299+08:00  INFO 51612 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2025-07-24T08:22:45.299+08:00  INFO 51612 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 2806 ms
Logging initialized using 'class org.apache.ibatis.logging.stdout.StdOutImpl' adapter.
2025-07-24T08:22:46.125+08:00  INFO 51612 --- [           main] c.pandatech.downloadcf.config.EslConfig  : 初始化品牌适配器列表
2025-07-24T08:22:46.127+08:00  INFO 51612 --- [           main] c.pandatech.downloadcf.config.EslConfig  : 初始化消息执行器列表
2025-07-24T08:22:46.878+08:00  INFO 51612 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : Adding {logging-channel-adapter:_org.springframework.integration.errorLogger} as a subscriber to the 'errorChannel' channel
2025-07-24T08:22:46.879+08:00  INFO 51612 --- [           main] o.s.i.channel.PublishSubscribeChannel    : Channel 'application.errorChannel' has 1 subscriber(s).
2025-07-24T08:22:46.879+08:00  INFO 51612 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : started bean '_org.springframework.integration.errorLogger'
2025-07-24T08:22:46.880+08:00  INFO 51612 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : Adding {message-handler:mqttOutbound.serviceActivator} as a subscriber to the 'mqttOutboundChannel' channel
2025-07-24T08:22:46.880+08:00  INFO 51612 --- [           main] o.s.integration.channel.DirectChannel    : Channel 'application.mqttOutboundChannel' has 1 subscriber(s).
2025-07-24T08:22:46.880+08:00  INFO 51612 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : started bean 'mqttOutbound.serviceActivator'
2025-07-24T08:22:46.880+08:00  INFO 51612 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : Adding {service-activator:mqttService.handleMessage.serviceActivator} as a subscriber to the 'mqttInputChannel' channel
2025-07-24T08:22:46.880+08:00  INFO 51612 --- [           main] o.s.integration.channel.DirectChannel    : Channel 'application.mqttInputChannel' has 1 subscriber(s).
2025-07-24T08:22:46.880+08:00  INFO 51612 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : started bean 'mqttService.handleMessage.serviceActivator'
2025-07-24T08:22:47.222+08:00  INFO 51612 --- [           main] .m.i.MqttPahoMessageDrivenChannelAdapter : started bean 'inbound'; defined in: 'class path resource [com/pandatech/downloadcf/config/MqttConfig.class]'; from source: 'com.pandatech.downloadcf.config.MqttConfig.inbound()'
2025-07-24T08:22:47.241+08:00  INFO 51612 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8999 (http) with context path ''
2025-07-24T08:22:47.243+08:00  INFO 51612 --- [           main] o.s.a.r.c.CachingConnectionFactory       : Attempting to connect to: [10.3.36.15:5672]
2025-07-24T08:22:47.308+08:00  INFO 51612 --- [           main] o.s.a.r.c.CachingConnectionFactory       : Created new connection: rabbitConnectionFactory#671da0f9:0/SimpleConnection@3694461d [delegate=amqp://panda@10.3.36.15:5672/, localPort=11708]
2025-07-24T08:22:47.384+08:00  INFO 51612 --- [           main] c.p.downloadcf.DownloadcfApplication     : Started DownloadcfApplication in 5.305 seconds (process running for 5.836)
2025-07-24T08:22:52.449+08:00  INFO 51612 --- [nio-8999-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2025-07-24T08:22:52.449+08:00  INFO 51612 --- [nio-8999-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2025-07-24T08:22:52.451+08:00  INFO 51612 --- [nio-8999-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 2 ms
2025-07-24T08:22:52.627+08:00  INFO 51612 --- [nio-8999-exec-1] c.p.d.service.TemplateServiceImpl        : 开始刷新价签，价签ID: 1947223724923940865, 门店编码: 0002, 品牌编码: PANDA
2025-07-24T08:22:52.628+08:00  INFO 51612 --- [nio-8999-exec-1] c.p.d.service.EslRefreshService          : 开始刷新价签: EslRefreshRequest(eslId=1947223724923940865, brandCode=PANDA, forceRefresh=false, storeCode=0002)
2025-07-24T08:22:52.629+08:00  INFO 51612 --- [nio-8999-exec-1] c.p.downloadcf.service.DataService       : 获取价签完整数据: 1947223724923940865
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@35b97605] was not registered for synchronization because synchronization is not active
2025-07-24T08:22:52.697+08:00  INFO 51612 --- [nio-8999-exec-1] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} inited
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947223724923940865(String)
<==    Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<==        Row: 1947223724923940865, -1, null, NOT_DELETE, 2025-07-21 17:14:48, 1543837863788879871, null, null, 06000000195A, 0002, null, 2.13T, 1947222838805917697, null, null, null, null, null, null, null, null, null, null, null, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@35b97605]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@27b1817c] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947223724923940865(String)
<==    Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<==        Row: 1947223724923940865, -1, null, NOT_DELETE, 2025-07-21 17:14:48, 1543837863788879871, null, null, 06000000195A, 0002, null, 2.13T, 1947222838805917697, null, null, null, null, null, null, null, null, null, null, null, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@27b1817c]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5824ca5c] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE , EXT_JSON, PRODUCT_DESCRIPTION from panda_product where ID = ?
==> Parameters: 1947222838805917697(String)
<==    Columns: ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE, EXT_JSON, PRODUCT_DESCRIPTION
<==        Row: 1947222838805917697, -1, NOT_DELETE, 2025-07-21 17:11:17, 1543837863788879871, 2025-07-21 19:59:45, 1543837863788879871, 001, 0002, 测试商品, 测试分类, PRODUCT_FRUIT, 攀攀, 9.99, 99.99, 99.90, 99.00, 0.09, 99.00, 测试材质, http://localhost:82/dev/file/download?id=1947222476036370434&Domain=http://localhost:81, 测试产地, www.baidu.com, www.baidu.com, ge, 9.00, 测试状态, 99, 1946122678071738370, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5824ca5c]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@54b76ac9] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947223724923940865(String)
<==    Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<==        Row: 1947223724923940865, -1, null, NOT_DELETE, 2025-07-21 17:14:48, 1543837863788879871, null, null, 06000000195A, 0002, null, 2.13T, 1947222838805917697, null, null, null, null, null, null, null, null, null, null, null, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@54b76ac9]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@128ff201] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE , EXT_JSON, PRODUCT_DESCRIPTION from panda_product where ID = ?
==> Parameters: 1947222838805917697(String)
<==    Columns: ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE, EXT_JSON, PRODUCT_DESCRIPTION
<==        Row: 1947222838805917697, -1, NOT_DELETE, 2025-07-21 17:11:17, 1543837863788879871, 2025-07-21 19:59:45, 1543837863788879871, 001, 0002, 测试商品, 测试分类, PRODUCT_FRUIT, 攀攀, 9.99, 99.99, 99.90, 99.00, 0.09, 99.00, 测试材质, http://localhost:82/dev/file/download?id=1947222476036370434&Domain=http://localhost:81, 测试产地, www.baidu.com, www.baidu.com, ge, 9.00, 测试状态, 99, 1946122678071738370, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@128ff201]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6258c7c8] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select 'false' as QUERYID, ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER , CONTENT, EXT_JSON from print_template_design WHERE ( CODE = ? )
==> Parameters: 1946122678071738370(String)
<==      Total: 0
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6258c7c8]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7c23574f] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select 'false' as QUERYID, ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER , CONTENT, EXT_JSON from print_template_design WHERE ( NAME = ? )
==> Parameters: 默认模板(String)
<==      Total: 0
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7c23574f]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@479b7736] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select 'false' as QUERYID, ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER , CONTENT, EXT_JSON from print_template_design
==> Parameters: 
<==    Columns: QUERYID, ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, CONTENT, EXT_JSON
<==        Row: false, 1946122678071738370, -1, 2, null, 2.13T, 99, NOT_DELETE, 2025-07-18 16:19:38, 1543837863788879871, 2025-07-21 22:41:46, 1543837863788879871, <<BLOB>>, <<BLOB>>
<==        Row: false, 1946129644835848194, -1, 2, PTD-1752828439254-705, 2.13T, 99, NOT_DELETE, 2025-07-18 16:47:19, 1543837863788879871, 2025-07-21 15:25:07, 1543837863788879871, <<BLOB>>, <<BLOB>>
<==        Row: false, 1946136975640395777, -1, 2, PTD-1752830187028-406, 2.13T, 99, NOT_DELETE, 2025-07-18 17:16:27, 1543837863788879871, null, null, <<BLOB>>, <<BLOB>>
<==        Row: false, 1947312285182455810, -1, 3, PTD-1753110402676-995, 4.20T, 99, NOT_DELETE, 2025-07-21 23:06:43, 1543837863788879871, 2025-07-21 23:07:24, 1543837863788879871, <<BLOB>>, <<BLOB>>
<==      Total: 4
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@479b7736]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2420f3f7] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select 'false' as QUERYID, ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, BRAND_CODE, TEMPLATE_FIELD, FIELD_CODE, FORMAT_RULE from esl_brand_field_mapping WHERE ( BRAND_CODE = ? )
==> Parameters: PANDA(String)
<==      Total: 0
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2420f3f7]
2025-07-24T08:22:53.022+08:00  INFO 51612 --- [nio-8999-exec-1] c.p.downloadcf.service.DataService       : 成功获取价签完整数据: eslId=1947223724923940865, productId=1947222838805917697, templateId=1946122678071738370
2025-07-24T08:22:53.022+08:00  INFO 51612 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 开始转换攀攀品牌数据: eslId=1947223724923940865
2025-07-24T08:22:53.023+08:00  INFO 51612 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 开始构建数据映射，商品ID: 001, 商品名称: 测试商品
2025-07-24T08:22:53.023+08:00  WARN 51612 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 没有找到字段映射配置，使用最小化默认映射
2025-07-24T08:22:53.034+08:00  INFO 51612 --- [nio-8999-exec-1] c.p.d.adapter.PandaBrandAdapter          : 攀攀品牌数据转换完成: eslId=1947223724923940865, actualEslId=06000000195A, checksum=12780dc2f9234a1406c5853435f7296a
2025-07-24T08:22:53.035+08:00  INFO 51612 --- [nio-8999-exec-1] c.p.d.service.MessageProducerService     : 开始发送消息: brandCode=PANDA, eslId=1947223724923940865
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7f55831f] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947223724923940865(String)
<==    Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<==        Row: 1947223724923940865, -1, null, NOT_DELETE, 2025-07-21 17:14:48, 1543837863788879871, null, null, 06000000195A, 0002, null, 2.13T, 1947222838805917697, null, null, null, null, null, null, null, null, null, null, null, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7f55831f]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7309a8ff] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE , EXT_JSON, PRODUCT_DESCRIPTION from panda_product where ID = ?
==> Parameters: 1947222838805917697(String)
<==    Columns: ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE, EXT_JSON, PRODUCT_DESCRIPTION
<==        Row: 1947222838805917697, -1, NOT_DELETE, 2025-07-21 17:11:17, 1543837863788879871, 2025-07-21 19:59:45, 1543837863788879871, 001, 0002, 测试商品, 测试分类, PRODUCT_FRUIT, 攀攀, 9.99, 99.99, 99.90, 99.00, 0.09, 99.00, 测试材质, http://localhost:82/dev/file/download?id=1947222476036370434&Domain=http://localhost:81, 测试产地, www.baidu.com, www.baidu.com, ge, 9.00, 测试状态, 99, 1946122678071738370, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7309a8ff]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@523ab4a8] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select 'false' as QUERYID, ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER , CONTENT, EXT_JSON from print_template_design WHERE ( CODE = ? )
==> Parameters: 1946122678071738370(String)
<==      Total: 0
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@523ab4a8]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5393d987] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select 'false' as QUERYID, ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER , CONTENT, EXT_JSON from print_template_design WHERE ( NAME = ? )
==> Parameters: 默认模板(String)
<==      Total: 0
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5393d987]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@711dc7c3] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select 'false' as QUERYID, ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER , CONTENT, EXT_JSON from print_template_design
==> Parameters: 
<==    Columns: QUERYID, ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, CONTENT, EXT_JSON
<==        Row: false, 1946122678071738370, -1, 2, null, 2.13T, 99, NOT_DELETE, 2025-07-18 16:19:38, 1543837863788879871, 2025-07-21 22:41:46, 1543837863788879871, <<BLOB>>, <<BLOB>>
<==        Row: false, 1946129644835848194, -1, 2, PTD-1752828439254-705, 2.13T, 99, NOT_DELETE, 2025-07-18 16:47:19, 1543837863788879871, 2025-07-21 15:25:07, 1543837863788879871, <<BLOB>>, <<BLOB>>
<==        Row: false, 1946136975640395777, -1, 2, PTD-1752830187028-406, 2.13T, 99, NOT_DELETE, 2025-07-18 17:16:27, 1543837863788879871, null, null, <<BLOB>>, <<BLOB>>
<==        Row: false, 1947312285182455810, -1, 3, PTD-1753110402676-995, 4.20T, 99, NOT_DELETE, 2025-07-21 23:06:43, 1543837863788879871, 2025-07-21 23:07:24, 1543837863788879871, <<BLOB>>, <<BLOB>>
<==      Total: 4
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@711dc7c3]
2025-07-24T08:22:53.061+08:00  INFO 51612 --- [nio-8999-exec-1] c.p.downloadcf.executor.MqttExecutor     : 执行MQTT消息发送: destination=esl/server/data/0002, eslId=1947223724923940865
2025-07-24T08:22:53.423+08:00  INFO 51612 --- [nio-8999-exec-1] c.p.downloadcf.executor.MqttExecutor     : MQTT消息发送成功: eslId=1947223724923940865
2025-07-24T08:22:53.423+08:00  INFO 51612 --- [nio-8999-exec-1] c.p.d.service.MessageProducerService     : 消息发送成功: brandCode=PANDA, eslId=1947223724923940865, executor=mqtt
2025-07-24T08:22:53.423+08:00  INFO 51612 --- [nio-8999-exec-1] c.p.d.service.EslRefreshService          : 价签刷新成功: eslId=1947223724923940865, brandCode=PANDA
2025-07-24T08:22:53.423+08:00  INFO 51612 --- [nio-8999-exec-1] c.p.d.service.TemplateServiceImpl        : 价签刷新请求已处理，价签ID: 1947223724923940865
2025-07-24T08:23:07.889+08:00  INFO 51612 --- [nio-8999-exec-2] c.p.d.service.TemplateServiceImpl        : 开始刷新价签，价签ID: 1947313501023105026, 门店编码: 0002, 品牌编码: PANDA
2025-07-24T08:23:07.889+08:00  INFO 51612 --- [nio-8999-exec-2] c.p.d.service.EslRefreshService          : 开始刷新价签: EslRefreshRequest(eslId=1947313501023105026, brandCode=PANDA, forceRefresh=false, storeCode=0002)
2025-07-24T08:23:07.889+08:00  INFO 51612 --- [nio-8999-exec-2] c.p.downloadcf.service.DataService       : 获取价签完整数据: 1947313501023105026
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@d7a59f] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947313501023105026(String)
<==    Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<==        Row: 1947313501023105026, -1, null, NOT_DELETE, 2025-07-21 23:11:33, 1543837863788879871, null, null, 1C000000100B, 0002, null, 4.20T, 1947312777082040322, null, null, null, null, null, null, null, null, null, null, null, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@d7a59f]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@a2fceef] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947313501023105026(String)
<==    Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<==        Row: 1947313501023105026, -1, null, NOT_DELETE, 2025-07-21 23:11:33, 1543837863788879871, null, null, 1C000000100B, 0002, null, 4.20T, 1947312777082040322, null, null, null, null, null, null, null, null, null, null, null, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@a2fceef]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@72ebeee9] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE , EXT_JSON, PRODUCT_DESCRIPTION from panda_product where ID = ?
==> Parameters: 1947312777082040322(String)
<==    Columns: ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE, EXT_JSON, PRODUCT_DESCRIPTION
<==        Row: 1947312777082040322, -1, NOT_DELETE, 2025-07-21 23:08:40, 1543837863788879871, null, null, 002, 0002, 002, 002, PRODUCT_FRUIT, 002, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 002, http://localhost:82/dev/file/download?id=1947312644269404161&Domain=http://localhost:81, 002, 002, 002, ge, 2.00, 002, 2, 1947312285182455810, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@72ebeee9]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@a04bd3e] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947313501023105026(String)
<==    Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<==        Row: 1947313501023105026, -1, null, NOT_DELETE, 2025-07-21 23:11:33, 1543837863788879871, null, null, 1C000000100B, 0002, null, 4.20T, 1947312777082040322, null, null, null, null, null, null, null, null, null, null, null, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@a04bd3e]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2b3912e] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE , EXT_JSON, PRODUCT_DESCRIPTION from panda_product where ID = ?
==> Parameters: 1947312777082040322(String)
<==    Columns: ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE, EXT_JSON, PRODUCT_DESCRIPTION
<==        Row: 1947312777082040322, -1, NOT_DELETE, 2025-07-21 23:08:40, 1543837863788879871, null, null, 002, 0002, 002, 002, PRODUCT_FRUIT, 002, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 002, http://localhost:82/dev/file/download?id=1947312644269404161&Domain=http://localhost:81, 002, 002, 002, ge, 2.00, 002, 2, 1947312285182455810, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2b3912e]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1100373d] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select 'false' as QUERYID, ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER , CONTENT, EXT_JSON from print_template_design WHERE ( CODE = ? )
==> Parameters: 1947312285182455810(String)
<==      Total: 0
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1100373d]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3568bb8c] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select 'false' as QUERYID, ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER , CONTENT, EXT_JSON from print_template_design WHERE ( NAME = ? )
==> Parameters: 默认模板(String)
<==      Total: 0
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3568bb8c]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@fadbe15] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select 'false' as QUERYID, ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER , CONTENT, EXT_JSON from print_template_design
==> Parameters: 
<==    Columns: QUERYID, ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, CONTENT, EXT_JSON
<==        Row: false, 1946122678071738370, -1, 2, null, 2.13T, 99, NOT_DELETE, 2025-07-18 16:19:38, 1543837863788879871, 2025-07-21 22:41:46, 1543837863788879871, <<BLOB>>, <<BLOB>>
<==        Row: false, 1946129644835848194, -1, 2, PTD-1752828439254-705, 2.13T, 99, NOT_DELETE, 2025-07-18 16:47:19, 1543837863788879871, 2025-07-21 15:25:07, 1543837863788879871, <<BLOB>>, <<BLOB>>
<==        Row: false, 1946136975640395777, -1, 2, PTD-1752830187028-406, 2.13T, 99, NOT_DELETE, 2025-07-18 17:16:27, 1543837863788879871, null, null, <<BLOB>>, <<BLOB>>
<==        Row: false, 1947312285182455810, -1, 3, PTD-1753110402676-995, 4.20T, 99, NOT_DELETE, 2025-07-21 23:06:43, 1543837863788879871, 2025-07-21 23:07:24, 1543837863788879871, <<BLOB>>, <<BLOB>>
<==      Total: 4
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@fadbe15]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@37448858] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select 'false' as QUERYID, ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, BRAND_CODE, TEMPLATE_FIELD, FIELD_CODE, FORMAT_RULE from esl_brand_field_mapping WHERE ( BRAND_CODE = ? )
==> Parameters: PANDA(String)
<==      Total: 0
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@37448858]
2025-07-24T08:23:07.918+08:00  INFO 51612 --- [nio-8999-exec-2] c.p.downloadcf.service.DataService       : 成功获取价签完整数据: eslId=1947313501023105026, productId=1947312777082040322, templateId=1946122678071738370
2025-07-24T08:23:07.919+08:00  INFO 51612 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 开始转换攀攀品牌数据: eslId=1947313501023105026
2025-07-24T08:23:07.919+08:00  INFO 51612 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 开始构建数据映射，商品ID: 002, 商品名称: 002
2025-07-24T08:23:07.919+08:00  WARN 51612 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 没有找到字段映射配置，使用最小化默认映射
2025-07-24T08:23:07.921+08:00  INFO 51612 --- [nio-8999-exec-2] c.p.d.adapter.PandaBrandAdapter          : 攀攀品牌数据转换完成: eslId=1947313501023105026, actualEslId=1C000000100B, checksum=12780dc2f9234a1406c5853435f7296a
2025-07-24T08:23:07.921+08:00  INFO 51612 --- [nio-8999-exec-2] c.p.d.service.MessageProducerService     : 开始发送消息: brandCode=PANDA, eslId=1947313501023105026
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6c9e7b93] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE , EXT_JSON from panda_esl where ID = ?
==> Parameters: 1947313501023105026(String)
<==    Columns: ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE, EXT_JSON
<==        Row: 1947313501023105026, -1, null, NOT_DELETE, 2025-07-21 23:11:33, 1543837863788879871, null, null, 1C000000100B, 0002, null, 4.20T, 1947312777082040322, null, null, null, null, null, null, null, null, null, null, null, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6c9e7b93]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3fb4556c] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE , EXT_JSON, PRODUCT_DESCRIPTION from panda_product where ID = ?
==> Parameters: 1947312777082040322(String)
<==    Columns: ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE, EXT_JSON, PRODUCT_DESCRIPTION
<==        Row: 1947312777082040322, -1, NOT_DELETE, 2025-07-21 23:08:40, 1543837863788879871, null, null, 002, 0002, 002, 002, PRODUCT_FRUIT, 002, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 002, http://localhost:82/dev/file/download?id=1947312644269404161&Domain=http://localhost:81, 002, 002, 002, ge, 2.00, 002, 2, 1947312285182455810, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3fb4556c]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@27c54e3c] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select 'false' as QUERYID, ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER , CONTENT, EXT_JSON from print_template_design WHERE ( CODE = ? )
==> Parameters: 1947312285182455810(String)
<==      Total: 0
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@27c54e3c]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1eb5e8cd] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select 'false' as QUERYID, ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER , CONTENT, EXT_JSON from print_template_design WHERE ( NAME = ? )
==> Parameters: 默认模板(String)
<==      Total: 0
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1eb5e8cd]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6ca9a764] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1bc5b677] will not be managed by Spring
==>  Preparing: select 'false' as QUERYID, ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER , CONTENT, EXT_JSON from print_template_design
==> Parameters: 
<==    Columns: QUERYID, ID, TENANT_ID, NAME, CODE, CATEGORY, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, CONTENT, EXT_JSON
<==        Row: false, 1946122678071738370, -1, 2, null, 2.13T, 99, NOT_DELETE, 2025-07-18 16:19:38, 1543837863788879871, 2025-07-21 22:41:46, 1543837863788879871, <<BLOB>>, <<BLOB>>
<==        Row: false, 1946129644835848194, -1, 2, PTD-1752828439254-705, 2.13T, 99, NOT_DELETE, 2025-07-18 16:47:19, 1543837863788879871, 2025-07-21 15:25:07, 1543837863788879871, <<BLOB>>, <<BLOB>>
<==        Row: false, 1946136975640395777, -1, 2, PTD-1752830187028-406, 2.13T, 99, NOT_DELETE, 2025-07-18 17:16:27, 1543837863788879871, null, null, <<BLOB>>, <<BLOB>>
<==        Row: false, 1947312285182455810, -1, 3, PTD-1753110402676-995, 4.20T, 99, NOT_DELETE, 2025-07-21 23:06:43, 1543837863788879871, 2025-07-21 23:07:24, 1543837863788879871, <<BLOB>>, <<BLOB>>
<==      Total: 4
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6ca9a764]
2025-07-24T08:23:07.936+08:00  INFO 51612 --- [nio-8999-exec-2] c.p.downloadcf.executor.MqttExecutor     : 执行MQTT消息发送: destination=esl/server/data/0002, eslId=1947313501023105026
2025-07-24T08:23:07.937+08:00  INFO 51612 --- [nio-8999-exec-2] c.p.downloadcf.executor.MqttExecutor     : MQTT消息发送成功: eslId=1947313501023105026
2025-07-24T08:23:07.937+08:00  INFO 51612 --- [nio-8999-exec-2] c.p.d.service.MessageProducerService     : 消息发送成功: brandCode=PANDA, eslId=1947313501023105026, executor=mqtt
2025-07-24T08:23:07.937+08:00  INFO 51612 --- [nio-8999-exec-2] c.p.d.service.EslRefreshService          : 价签刷新成功: eslId=1947313501023105026, brandCode=PANDA
2025-07-24T08:23:07.937+08:00  INFO 51612 --- [nio-8999-exec-2] c.p.d.service.TemplateServiceImpl        : 价签刷新请求已处理，价签ID: 1947313501023105026



Topic: esl/server/data/0002QoS: 0
{"command":"wtag","data":[{"tag":6597069773146,"tmpl":"2","model":"6","checksum":"12780dc2f9234a1406c5853435f7296a","forcefrash":1,"value":{"GOODS_CODE":"001","GOODS_NAME":"测试商品","QRCODE":"www.baidu.com","F_1":99.99},"taskid":73061,"token":300565}],"id":"1947223724923940865","timestamp":1.753316573061E9,"shop":"0002"}

Topic: esl/server/data/0002QoS: 0
{"command":"wtag","data":[{"tag":30786325581835,"tmpl":"2","model":"6","checksum":"12780dc2f9234a1406c5853435f7296a","forcefrash":1,"value":{"GOODS_CODE":"002","GOODS_NAME":"002","QRCODE":"002","F_1":2.0},"taskid":87936,"token":396686}],"id":"1947313501023105026","timestamp":1.753316587936E9,"shop":"0002"}

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


  第一条api使用之后了因为字段残缺，导致刷新失败。第二条也同样失败。
  但是第二条错误了

{
  "command": "wtag",
  "data": [
    {
      "tag": 30786325581835,
      "tmpl": "2",（错误，此时应该是3，说明没有查到正确的模板名称，一个解析json
      
      {"panels":[{"index":0,"name":"3","paperType":"CUSTOM","width":400,"height":300,"paperHeader":0,"paperFooter":850.3937007874016,"printElements":[],"paperNumberContinue":true,"eslConfig":{"screenType":"4.20T","pixelWidth":400,"pixelHeight":300,"colorMode":{"black":true,"white":true,"red":true,"yellow":false},"orientation":"LANDSCAPE"}}]}
      
      看到没，这是数据库print_template_design表的CONTENT字段的内容，里面的name就是这里实际输出的名称。即empl。然后里面的"screenType":"4.20T"就是类型，转化为1C就是下面需要的输出model
      ）
      "model": "6",（错误，此时应该为1C 说明没有查到正确的类型）
      "checksum": "12780dc2f9234a1406c5853435f7296a",
      "forcefrash": 1,
      "value": {
        "GOODS_CODE": "002",
        "GOODS_NAME": "002",
        "QRCODE": "002",
        "F_1": 2.0
      },
      "taskid": 87936,
      "token": 396686
    }
  ],
  "id": "1947313501023105026",
  "timestamp": 1.753316587936e9,
  "shop": "0002"
}





并且。我的数据库esl_brand_field_mapping表目前只有
BRAND_CODE,TEMPLATE_FIELD,FIELD_CODE,FORMAT_RULE
攀攀	code	PRODUCT_ID	商品名称
攀攀	name	PRODUCT_NAME	商品名称
攀攀	F_01	PRODUCT_RETAIL_PRICE	价格
攀攀	F_02	PRODUCT_CATEGORY	商品分类
攀攀	F_03	PRODUCT_COST_PRICE	商品成本价
攀攀	F_04	PRODUCT_SPECIFICATION	商品规格
攀攀	F_05	PRODUCT_MEMBERSHIP_PRICE	商品会员价
攀攀	F_06	PRODUCT_DISCOUNT_PRICE	商品折扣价
攀攀	F_07	PRODUCT_DISCOUNT	商品折扣（例如：0.8 表示8折）
攀攀	F_08	PRODUCT_WHOLESALE_PRICE	商品批发
攀攀	F_09	PRODUCT_MATERIAL	商品材质
攀攀	F_10	PRODUCT_IMAGE	商品图片（路径或URL）
攀攀	F_11	PRODUCT_ORIGIN	商品产地
攀攀	F_20	PRODUCT_DESCRIPTION	商品描述
攀攀	F_12	PRODUCT_UNIT	商品单位（如：个、件、瓶等）
攀攀	F_13	PRODUCT_WEIGHT	产品重量（单位：kg）
攀攀	F_14	PRODUCT_STATUS	商品状态（如：上架、下架、预售等）
攀攀	F_32	PRODUCT_STOCK	商品库存
攀攀	QRCODE	PRODUCT_QRCODE	二维码
攀攀	QRCODE	PRODUCT_BARCODE	条形码

为什么实际输出的字段残缺了。请修复！