E:\JAVA\JAVA22\bin\java.exe -XX:TieredStopAtLevel=1 -Dspring.output.ansi.enabled=always -Dcom.sun.management.jmxremote -Dspring.jmx.enabled=true -Dspring.liveBeansView.mbeanDomain -Dspring.application.admin.enabled=true "-Dmanagement.endpoints.jmx.exposure.include=*" "-javaagent:D:\JetBrains\IntelliJ IDEA 2024.2.1\lib\idea_rt.jar=1862:D:\JetBrains\IntelliJ IDEA 2024.2.1\bin" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath E:\IdeaProjects\cfdownloadexample\target\classes;D:\Maven\repository\org\springframework\boot\spring-boot-starter-web\3.4.5\spring-boot-starter-web-3.4.5.jar;D:\Maven\repository\org\springframework\boot\spring-boot-starter\3.4.5\spring-boot-starter-3.4.5.jar;D:\Maven\repository\org\springframework\boot\spring-boot\3.4.5\spring-boot-3.4.5.jar;D:\Maven\repository\org\springframework\boot\spring-boot-starter-logging\3.4.5\spring-boot-starter-logging-3.4.5.jar;D:\Maven\repository\ch\qos\logback\logback-classic\1.5.18\logback-classic-1.5.18.jar;D:\Maven\repository\ch\qos\logback\logback-core\1.5.18\logback-core-1.5.18.jar;D:\Maven\repository\org\apache\logging\log4j\log4j-to-slf4j\2.24.3\log4j-to-slf4j-2.24.3.jar;D:\Maven\repository\org\apache\logging\log4j\log4j-api\2.24.3\log4j-api-2.24.3.jar;D:\Maven\repository\org\slf4j\jul-to-slf4j\2.0.17\jul-to-slf4j-2.0.17.jar;D:\Maven\repository\org\yaml\snakeyaml\2.3\snakeyaml-2.3.jar;D:\Maven\repository\org\springframework\boot\spring-boot-starter-json\3.4.5\spring-boot-starter-json-3.4.5.jar;D:\Maven\repository\com\fasterxml\jackson\core\jackson-databind\2.18.3\jackson-databind-2.18.3.jar;D:\Maven\repository\com\fasterxml\jackson\core\jackson-annotations\2.18.3\jackson-annotations-2.18.3.jar;D:\Maven\repository\com\fasterxml\jackson\core\jackson-core\2.18.3\jackson-core-2.18.3.jar;D:\Maven\repository\com\fasterxml\jackson\datatype\jackson-datatype-jdk8\2.18.3\jackson-datatype-jdk8-2.18.3.jar;D:\Maven\repository\com\fasterxml\jackson\datatype\jackson-datatype-jsr310\2.18.3\jackson-datatype-jsr310-2.18.3.jar;D:\Maven\repository\com\fasterxml\jackson\module\jackson-module-parameter-names\2.18.3\jackson-module-parameter-names-2.18.3.jar;D:\Maven\repository\org\springframework\boot\spring-boot-starter-tomcat\3.4.5\spring-boot-starter-tomcat-3.4.5.jar;D:\Maven\repository\org\apache\tomcat\embed\tomcat-embed-core\10.1.40\tomcat-embed-core-10.1.40.jar;D:\Maven\repository\org\apache\tomcat\embed\tomcat-embed-el\10.1.40\tomcat-embed-el-10.1.40.jar;D:\Maven\repository\org\apache\tomcat\embed\tomcat-embed-websocket\10.1.40\tomcat-embed-websocket-10.1.40.jar;D:\Maven\repository\org\springframework\spring-web\6.2.6\spring-web-6.2.6.jar;D:\Maven\repository\org\springframework\spring-beans\6.2.6\spring-beans-6.2.6.jar;D:\Maven\repository\io\micrometer\micrometer-observation\1.14.6\micrometer-observation-1.14.6.jar;D:\Maven\repository\io\micrometer\micrometer-commons\1.14.6\micrometer-commons-1.14.6.jar;D:\Maven\repository\org\springframework\spring-webmvc\6.2.6\spring-webmvc-6.2.6.jar;D:\Maven\repository\org\springframework\spring-aop\6.2.6\spring-aop-6.2.6.jar;D:\Maven\repository\org\springframework\spring-context\6.2.6\spring-context-6.2.6.jar;D:\Maven\repository\org\springframework\spring-expression\6.2.6\spring-expression-6.2.6.jar;D:\Maven\repository\jakarta\xml\bind\jakarta.xml.bind-api\4.0.2\jakarta.xml.bind-api-4.0.2.jar;D:\Maven\repository\jakarta\activation\jakarta.activation-api\2.1.3\jakarta.activation-api-2.1.3.jar;D:\Maven\repository\org\springframework\spring-core\6.2.6\spring-core-6.2.6.jar;D:\Maven\repository\org\springframework\spring-jcl\6.2.6\spring-jcl-6.2.6.jar;D:\Maven\repository\cn\hutool\hutool-all\5.8.24\hutool-all-5.8.24.jar;D:\Maven\repository\org\projectlombok\lombok\1.18.38\lombok-1.18.38.jar;D:\Maven\repository\org\mybatis\generator\mybatis-generator-core\1.4.1\mybatis-generator-core-1.4.1.jar;D:\Maven\repository\jakarta\annotation\jakarta.annotation-api\2.1.1\jakarta.annotation-api-2.1.1.jar;D:\Maven\repository\org\mybatis\spring\boot\mybatis-spring-boot-starter\3.0.3\mybatis-spring-boot-starter-3.0.3.jar;D:\Maven\repository\org\springframework\boot\spring-boot-starter-jdbc\3.4.5\spring-boot-starter-jdbc-3.4.5.jar;D:\Maven\repository\com\zaxxer\HikariCP\5.1.0\HikariCP-5.1.0.jar;D:\Maven\repository\org\springframework\spring-jdbc\6.2.6\spring-jdbc-6.2.6.jar;D:\Maven\repository\org\mybatis\spring\boot\mybatis-spring-boot-autoconfigure\3.0.3\mybatis-spring-boot-autoconfigure-3.0.3.jar;D:\Maven\repository\org\mybatis\mybatis\3.5.14\mybatis-3.5.14.jar;D:\Maven\repository\org\mybatis\mybatis-spring\3.0.3\mybatis-spring-3.0.3.jar;D:\Maven\repository\com\mysql\mysql-connector-j\8.0.33\mysql-connector-j-8.0.33.jar;D:\Maven\repository\com\alibaba\druid-spring-boot-starter\1.2.16\druid-spring-boot-starter-1.2.16.jar;D:\Maven\repository\com\alibaba\druid\1.2.16\druid-1.2.16.jar;D:\Maven\repository\org\slf4j\slf4j-api\2.0.17\slf4j-api-2.0.17.jar;D:\Maven\repository\org\springframework\boot\spring-boot-autoconfigure\3.4.5\spring-boot-autoconfigure-3.4.5.jar;D:\Maven\repository\org\springframework\boot\spring-boot-starter-amqp\3.4.5\spring-boot-starter-amqp-3.4.5.jar;D:\Maven\repository\org\springframework\spring-messaging\6.2.6\spring-messaging-6.2.6.jar;D:\Maven\repository\org\springframework\amqp\spring-rabbit\3.2.5\spring-rabbit-3.2.5.jar;D:\Maven\repository\org\springframework\amqp\spring-amqp\3.2.5\spring-amqp-3.2.5.jar;D:\Maven\repository\com\rabbitmq\amqp-client\5.22.0\amqp-client-5.22.0.jar;D:\Maven\repository\org\springframework\spring-tx\6.2.6\spring-tx-6.2.6.jar;D:\Maven\repository\org\springframework\integration\spring-integration-mqtt\6.4.4\spring-integration-mqtt-6.4.4.jar;D:\Maven\repository\org\springframework\integration\spring-integration-core\6.4.4\spring-integration-core-6.4.4.jar;D:\Maven\repository\org\springframework\retry\spring-retry\2.0.11\spring-retry-2.0.11.jar;D:\Maven\repository\io\projectreactor\reactor-core\3.7.5\reactor-core-3.7.5.jar;D:\Maven\repository\org\reactivestreams\reactive-streams\1.0.4\reactive-streams-1.0.4.jar;D:\Maven\repository\org\eclipse\paho\org.eclipse.paho.client.mqttv3\1.2.5\org.eclipse.paho.client.mqttv3-1.2.5.jar;D:\Maven\repository\org\springdoc\springdoc-openapi-starter-webmvc-ui\2.2.0\springdoc-openapi-starter-webmvc-ui-2.2.0.jar;D:\Maven\repository\org\springdoc\springdoc-openapi-starter-webmvc-api\2.2.0\springdoc-openapi-starter-webmvc-api-2.2.0.jar;D:\Maven\repository\org\springdoc\springdoc-openapi-starter-common\2.2.0\springdoc-openapi-starter-common-2.2.0.jar;D:\Maven\repository\io\swagger\core\v3\swagger-core-jakarta\2.2.15\swagger-core-jakarta-2.2.15.jar;D:\Maven\repository\org\apache\commons\commons-lang3\3.17.0\commons-lang3-3.17.0.jar;D:\Maven\repository\io\swagger\core\v3\swagger-annotations-jakarta\2.2.15\swagger-annotations-jakarta-2.2.15.jar;D:\Maven\repository\io\swagger\core\v3\swagger-models-jakarta\2.2.15\swagger-models-jakarta-2.2.15.jar;D:\Maven\repository\jakarta\validation\jakarta.validation-api\3.0.2\jakarta.validation-api-3.0.2.jar;D:\Maven\repository\com\fasterxml\jackson\dataformat\jackson-dataformat-yaml\2.18.3\jackson-dataformat-yaml-2.18.3.jar;D:\Maven\repository\org\webjars\swagger-ui\5.2.0\swagger-ui-5.2.0.jar com.pandatech.downloadcf.DownloadcfApplication

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v3.4.5)

2025-07-21T18:47:44.549+08:00  INFO 15740 --- [           main] c.p.downloadcf.DownloadcfApplication     : Starting DownloadcfApplication using Java 22.0.2 with PID 15740 (E:\IdeaProjects\cfdownloadexample\target\classes started by Ming in E:\IdeaProjects\cfdownloadexample)
2025-07-21T18:47:44.552+08:00  INFO 15740 --- [           main] c.p.downloadcf.DownloadcfApplication     : No active profile set, falling back to 1 default profile: "default"
2025-07-21T18:47:45.583+08:00  INFO 15740 --- [           main] faultConfiguringBeanFactoryPostProcessor : No bean named 'errorChannel' has been explicitly defined. Therefore, a default PublishSubscribeChannel will be created.
2025-07-21T18:47:45.594+08:00  INFO 15740 --- [           main] faultConfiguringBeanFactoryPostProcessor : No bean named 'integrationHeaderChannelRegistry' has been explicitly defined. Therefore, a default DefaultHeaderChannelRegistry will be created.
2025-07-21T18:47:46.258+08:00  INFO 15740 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8999 (http)
2025-07-21T18:47:46.271+08:00  INFO 15740 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2025-07-21T18:47:46.272+08:00  INFO 15740 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.40]
2025-07-21T18:47:46.320+08:00  INFO 15740 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2025-07-21T18:47:46.320+08:00  INFO 15740 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1693 ms
Logging initialized using 'class org.apache.ibatis.logging.stdout.StdOutImpl' adapter.
2025-07-21T18:47:47.070+08:00  INFO 15740 --- [           main] o.s.v.b.OptionalValidatorFactoryBean     : Failed to set up a Bean Validation provider: jakarta.validation.NoProviderFoundException: Unable to create a Configuration, because no Jakarta Bean Validation provider could be found. Add a provider like Hibernate Validator (RI) to your classpath.
2025-07-21T18:47:47.477+08:00  INFO 15740 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : Adding {logging-channel-adapter:_org.springframework.integration.errorLogger} as a subscriber to the 'errorChannel' channel
2025-07-21T18:47:47.478+08:00  INFO 15740 --- [           main] o.s.i.channel.PublishSubscribeChannel    : Channel 'application.errorChannel' has 1 subscriber(s).
2025-07-21T18:47:47.478+08:00  INFO 15740 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : started bean '_org.springframework.integration.errorLogger'
2025-07-21T18:47:47.478+08:00  INFO 15740 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : Adding {message-handler:mqttOutbound.serviceActivator} as a subscriber to the 'mqttOutboundChannel' channel
2025-07-21T18:47:47.478+08:00  INFO 15740 --- [           main] o.s.integration.channel.DirectChannel    : Channel 'application.mqttOutboundChannel' has 1 subscriber(s).
2025-07-21T18:47:47.478+08:00  INFO 15740 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : started bean 'mqttOutbound.serviceActivator'
2025-07-21T18:47:47.478+08:00  INFO 15740 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : Adding {service-activator:mqttService.handleMessage.serviceActivator} as a subscriber to the 'mqttInputChannel' channel
2025-07-21T18:47:47.478+08:00  INFO 15740 --- [           main] o.s.integration.channel.DirectChannel    : Channel 'application.mqttInputChannel' has 1 subscriber(s).
2025-07-21T18:47:47.478+08:00  INFO 15740 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : started bean 'mqttService.handleMessage.serviceActivator'
2025-07-21T18:47:47.823+08:00  INFO 15740 --- [           main] .m.i.MqttPahoMessageDrivenChannelAdapter : started bean 'inbound'; defined in: 'class path resource [com/pandatech/downloadcf/config/MqttConfig.class]'; from source: 'com.pandatech.downloadcf.config.MqttConfig.inbound()'
2025-07-21T18:47:47.847+08:00  INFO 15740 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8999 (http) with context path '/'
2025-07-21T18:47:47.848+08:00  INFO 15740 --- [           main] o.s.a.r.c.CachingConnectionFactory       : Attempting to connect to: [10.3.36.15:5672]
2025-07-21T18:47:47.890+08:00  INFO 15740 --- [           main] o.s.a.r.c.CachingConnectionFactory       : Created new connection: rabbitConnectionFactory#27ab206:0/SimpleConnection@575d48db [delegate=amqp://panda@10.3.36.15:5672/, localPort=1880]
2025-07-21T18:47:47.946+08:00  INFO 15740 --- [           main] c.p.downloadcf.DownloadcfApplication     : Started DownloadcfApplication in 3.849 seconds (process running for 4.348)
2025-07-21T18:48:01.154+08:00  INFO 15740 --- [nio-8999-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2025-07-21T18:48:01.155+08:00  INFO 15740 --- [nio-8999-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2025-07-21T18:48:01.156+08:00  INFO 15740 --- [nio-8999-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 1 ms
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@223ea8f8] was not registered for synchronization because synchronization is not active
2025-07-21T18:48:01.322+08:00  INFO 15740 --- [nio-8999-exec-1] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} inited
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7cb9a3a4] will not be managed by Spring
==>  Preparing: select 'false' as QUERYID, ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE from panda_esl WHERE ( ID = ? )
==> Parameters: 1947223724923940865(String)
<==    Columns: QUERYID, ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, ESL_ID, STORE_CODE, AP_SN, ESL_MODEL, BOUND_PRODUCT, BATTERY_LEVEL, TEMPERATURE, SIGNAL_STRENGTH, COMMUNICATION_COUNT, FAILURE_COUNT, ESL_CATEGORY, ESL_STATUS, SCREEN_COLOR, COMMUNICATION_METHOD, VERSION, HARDWARE
<==        Row: false, 1947223724923940865, -1, null, NOT_DELETE, 2025-07-21 17:14:48, 1543837863788879871, null, null, 06000000195A, 0002, null, 2.13T, 1947222838805917697, null, null, null, null, null, null, null, null, null, null, null
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@223ea8f8]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@25c87a4d] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7cb9a3a4] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE , EXT_JSON, PRODUCT_DESCRIPTION from panda_product where ID = ?
==> Parameters: 1947222838805917697(String)
<==    Columns: ID, TENANT_ID, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, PRODUCT_ID, STORE_CODE, PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_SPECIFICATION, PRODUCT_BRAND, PRODUCT_COST_PRICE, PRODUCT_RETAIL_PRICE, PRODUCT_MEMBERSHIP_PRICE, PRODUCT_DISCOUNT_PRICE, PRODUCT_DISCOUNT, PRODUCT_WHOLESALE_PRICE, PRODUCT_MATERIAL, PRODUCT_IMAGE, PRODUCT_ORIGIN, PRODUCT_QRCODE, PRODUCT_BARCODE, PRODUCT_UNIT, PRODUCT_WEIGHT, PRODUCT_STATUS, PRODUCT_STOCK, ESL_TEMPLATE_CODE, EXT_JSON, PRODUCT_DESCRIPTION
<==        Row: 1947222838805917697, -1, NOT_DELETE, 2025-07-21 17:11:17, 1543837863788879871, null, null, 001, 0002, 测试商品, 测试分类, PRODUCT_FRUIT, 攀攀, 9.99, 99.99, 99.90, 99.00, 0.09, 99.00, 测试材质, http://localhost:82/dev/file/download?id=1947222476036370434&Domain=http://localhost:81, 测试产地, www.baidu.com, www.baidu.com, ge, 9.00, 测试状态, 99, 为空, <<BLOB>>, <<BLOB>>
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@25c87a4d]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@717ff84e] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@7cb9a3a4] will not be managed by Spring
==>  Preparing: select ID, TENANT_ID, SORT_CODE, DELETE_FLAG, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER, BRAND_CODE, TEMPLATE_FIELD, FIELD_CODE, FORMAT_RULE , EXT_JSON from esl_brand_field_mapping where BRAND_CODE = ? and DELETE_FLAG = '0' order by SORT_CODE
==> Parameters: DEFAULT(String)
<==      Total: 0
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@717ff84e]
2025-07-21T18:48:01.549+08:00  WARN 15740 --- [nio-8999-exec-1] c.p.d.service.EslRefreshServiceImpl      : 未找到品牌编码 DEFAULT 的字段映射配置，使用默认品牌编码
2025-07-21T18:48:01.555+08:00  INFO 15740 --- [nio-8999-exec-1] c.p.d.service.EslRefreshServiceImpl      : 构造MQTT消息成功，ESL ID: 1947223724923940865, 门店: 0002
2025-07-21T18:48:01.599+08:00  INFO 15740 --- [nio-8999-exec-1] c.p.d.service.EslRefreshServiceImpl      : 发送价签刷新消息到队列成功: {"command":"wtag","data":[{"tag":6597069773146,"tmpl":"为空","model":6,"checksum":"c06283af3d7cf2736234cf5197c61448","forcefrash":1,"value":{"GOODS_NAME":"测试商品","GOODS_CODE":"001"},"taskid":81551,"token":592638}],"id":"4bf93052-8c34-46f2-b016-de0e42c4bbd7","timestamp":1.753094881551E9,"shop":"0002"}


然后这是mqtt的结果
Topic: esl/server/refresh/nullQoS: 0
{"eslId":null,"command":"refresh","timestamp":1753094881}

请修复，重新查看我的项目需求。
数据样例：
{
	"command": "wtag",
	"data": [{
		"tag": 6597069770841,
		"tmpl": "PRICEPROMO",
		"model": 6,
		"checksum": "b3359abd8cd0c923afe88f539e750871",
		"forcefrash": 1,
		"value": {
			"GOODS_NAME": " \u8109\u52a8 \u7ef4\u751f\u7d20\u996e\u6599\u9752\u67e0\u53e3\u5473 600ML",
			"GOODS_CODE": "6902538004045",
			"F_1": "10.80",
			"F_2": "",
			"F_3": null,
			"F_4": null,
			"F_5": null,
			"F_6": null,
			"F_7": null,
			"F_8": null,
			"F_9": null,
			"QRCODE": "esl.wdyc.cn",
			"F_11": null,
			"F_20": null
		},
		"taskid": 39138,
		"token": 161986
	}],
	"id": "3db4b81b-da87-4aa1-b8bb-ab2adf785558",
	"timestamp": 1706512513.5774696,
	"shop": "ZH01"
}



已知
curl 'http://localhost:8999/api/res/templ/refresh' \
  -H 'Accept-Language: zh-CN,zh;q=0.9' \
  -H 'Connection: keep-alive' \
  -H 'Content-Type: application/json' \
  -b 'JSESSIONID=AC9AA0889488569DA6ACCC0FA9EDFCB0' \
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
  --data-raw $'{\n  "eslId": "1947223724923940865"\n}'

应该查找数据库
mysql> use eslplatform;
Database changed
mysql> SELECT * FROM panda_esl;
+---------------------+-----------+-----------+----------+-------------+---------------------+---------------------+---------------------+---------------------+--------------+------------+-------+-----------+---------------------+---------------+-------------+-----------------+---------------------+---------------+--------------+------------+--------------+----------------------+---------+----------+
| ID                  | TENANT_ID | SORT_CODE | EXT_JSON | DELETE_FLAG | CREATE_TIME         | CREATE_USER         | UPDATE_TIME         | UPDATE_USER         | ESL_ID       | STORE_CODE | AP_SN | ESL_MODEL | BOUND_PRODUCT       | BATTERY_LEVEL | TEMPERATURE | SIGNAL_STRENGTH | COMMUNICATION_COUNT | FAILURE_COUNT | ESL_CATEGORY | ESL_STATUS | SCREEN_COLOR | COMMUNICATION_METHOD | VERSION | HARDWARE |
+---------------------+-----------+-----------+----------+-------------+---------------------+---------------------+---------------------+---------------------+--------------+------------+-------+-----------+---------------------+---------------+-------------+-----------------+---------------------+---------------+--------------+------------+--------------+----------------------+---------+----------+
| 1945833774331781121 | -1        |      NULL | NULL     | DELETED     | 2025-07-17 21:11:38 | 1543837863788879871 | 2025-07-17 21:11:47 | 1543837863788879871 | 1111         | 1111       | NULL  | 1111      | 11111               |          NULL |        NULL |            NULL |                NULL |          NULL | NULL         | NULL       | NULL         | NULL                 | NULL    | NULL     |
| 1946228658192523265 | -1        |      NULL | NULL     | NOT_DELETE  | 2025-07-18 23:20:46 | 1543837863788879871 | NULL                | NULL                | 60000001745  | BY001      | NULL  | AESTAG001 | NULL                |          NULL |        NULL |            NULL |                NULL |          NULL | NULL         | NULL       | NULL         | NULL                 | NULL    | NULL     |
| 1947223724923940865 | -1        |      NULL | NULL     | NOT_DELETE  | 2025-07-21 17:14:48 | 1543837863788879871 | NULL                | NULL                | 06000000195A | 0002       | NULL  | 2.13T     | 1947222838805917697 |          NULL |        NULL |            NULL |                NULL |          NULL | NULL         | NULL       | NULL         | NULL                 | NULL    | NULL     |
+---------------------+-----------+-----------+----------+-------------+---------------------+---------------------+---------------------+---------------------+--------------+------------+-------+-----------+---------------------+---------------+-------------+-----------------+---------------------+---------------+--------------+------------+--------------+----------------------+---------+----------+
3 rows in set (0.00 sec)

mysql> SELECT * FROM panda_product;
+---------------------+-----------+----------+-------------+---------------------+---------------------+-------------+-------------+------------+------------+--------------+------------------+-----------------------+---------------+--------------------+----------------------+--------------------------+------------------------+------------------+-------------------------+------------------+-----------------------------------------------------------------------------------------+----------------+---------------------+----------------+-----------------+--------------+----------------+----------------+---------------+-------------------+
| ID                  | TENANT_ID | EXT_JSON | DELETE_FLAG | CREATE_TIME         | CREATE_USER         | UPDATE_TIME | UPDATE_USER | PRODUCT_ID | STORE_CODE | PRODUCT_NAME | PRODUCT_CATEGORY | PRODUCT_SPECIFICATION | PRODUCT_BRAND | PRODUCT_COST_PRICE | PRODUCT_RETAIL_PRICE | PRODUCT_MEMBERSHIP_PRICE | PRODUCT_DISCOUNT_PRICE | PRODUCT_DISCOUNT | PRODUCT_WHOLESALE_PRICE | PRODUCT_MATERIAL | PRODUCT_IMAGE                                                                           | PRODUCT_ORIGIN | PRODUCT_DESCRIPTION | PRODUCT_QRCODE | PRODUCT_BARCODE | PRODUCT_UNIT | PRODUCT_WEIGHT | PRODUCT_STATUS | PRODUCT_STOCK | ESL_TEMPLATE_CODE |
+---------------------+-----------+----------+-------------+---------------------+---------------------+-------------+-------------+------------+------------+--------------+------------------+-----------------------+---------------+--------------------+----------------------+--------------------------+------------------------+------------------+-------------------------+------------------+-----------------------------------------------------------------------------------------+----------------+---------------------+----------------+-----------------+--------------+----------------+----------------+---------------+-------------------+
| 1947222838805917697 | -1        | NULL     | NOT_DELETE  | 2025-07-21 17:11:17 | 1543837863788879871 | NULL        | NULL        | 001        | 0002       | 测试商品     | 测试分类         | PRODUCT_FRUIT         | 攀攀          |               9.99 |                99.99 |                    99.90 |                  99.00 |             0.09 |                   99.00 | 测试材质         | http://localhost:82/dev/file/download?id=1947222476036370434&Domain=http://localhost:81 | 测试产地       | <p>测试描述</p>     | www.baidu.com  | www.baidu.com   | ge           |           9.00 | 测试状态       |            99 | 为空              |
+---------------------+-----------+----------+-------------+---------------------+---------------------+-------------+-------------+------------+------------+--------------+------------------+-----------------------+---------------+--------------------+----------------------+--------------------------+------------------------+------------------+-------------------------+------------------+-----------------------------------------------------------------------------------------+----------------+---------------------+----------------+-----------------+--------------+----------------+----------------+---------------+-------------------+
1 row in set (0.00 sec)

mysql>

然后转化格式输出到rabbitmq到mqtt