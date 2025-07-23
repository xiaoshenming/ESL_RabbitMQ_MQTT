# 价签模板下发系统

## 项目简介

这是一个企业级的价签模板下发系统，支持多品牌适配、消息队列、模板转换等功能。系统采用分层架构设计，具有良好的扩展性和可维护性。

## 核心功能

### 1. 价签刷新
- 单个价签刷新
- 批量价签刷新
- 按门店刷新价签
- 按商品刷新价签

### 2. 多品牌适配
- 支持攀攀(PANDA)品牌
- 可扩展支持其他品牌
- 品牌特定的数据转换
- 模板格式转换

### 3. 消息传输
- MQTT消息传输
- 可扩展HTTP传输
- 消息路由和分发
- 失败重试机制

### 4. 数据管理
- 统一数据访问层
- 缓存支持
- 数据验证
- 事务管理

## 技术栈

- **框架**: Spring Boot 3.4.5
- **数据库**: MySQL 8.0
- **ORM**: MyBatis 3.0.3
- **缓存**: Redis
- **消息**: MQTT, RabbitMQ
- **文档**: Swagger/OpenAPI 3
- **工具**: Lombok, Hutool

## 项目结构

```
src/main/java/com/pandatech/downloadcf/
├── adapter/                    # 品牌适配器
│   ├── BrandAdapter.java      # 品牌适配器接口
│   └── PandaBrandAdapter.java # 攀攀品牌适配器
├── config/                     # 配置类
│   ├── CacheConfig.java       # 缓存配置
│   └── EslConfig.java         # 价签系统配置
├── controller/                 # 控制器
│   ├── EslController.java     # 价签控制器
│   └── SystemController.java  # 系统控制器
├── dto/                        # 数据传输对象
│   ├── BrandOutputData.java   # 品牌输出数据
│   ├── EslCompleteData.java   # 价签完整数据
│   ├── EslRefreshRequest.java  # 价签刷新请求
│   └── MessageExecutionData.java # 消息执行数据
├── entity/                     # 实体类
├── exception/                  # 异常处理
│   ├── BusinessException.java  # 业务异常
│   └── GlobalExceptionHandler.java # 全局异常处理
├── executor/                   # 消息执行器
│   ├── MessageExecutor.java   # 消息执行器接口
│   └── MqttExecutor.java      # MQTT执行器
├── mapper/                     # 数据访问层
├── service/                    # 业务服务层
│   ├── DataService.java       # 数据服务
│   ├── EslRefreshService.java  # 价签刷新服务
│   └── MessageProducerService.java # 消息生产服务
└── util/                       # 工具类
    ├── ChecksumUtil.java      # 校验码工具
    ├── JsonUtil.java          # JSON工具
    └── TemplateUtil.java      # 模板工具
```

## 快速开始

### 1. 环境要求
- JDK 17+
- MySQL 8.0+
- Redis 6.0+
- MQTT Broker (如 Mosquitto)

### 2. 数据库配置
修改 `application.yml` 中的数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/esl_system
    username: your_username
    password: your_password
```

### 3. Redis配置
修改 `application.yml` 中的Redis连接信息：
```yaml
spring:
  redis:
    host: localhost
    port: 6379
    password: your_password
```

### 4. MQTT配置
修改 `application.yml` 中的MQTT连接信息：
```yaml
spring:
  mqtt:
    url: tcp://localhost:1883
    username: your_username
    password: your_password
```

### 5. 启动应用
```bash
mvn spring-boot:run
```

### 6. 访问接口文档
启动后访问: http://localhost:8080/swagger-ui.html

## API接口

### 价签管理

#### 刷新单个价签
```http
POST /api/esl/refresh
Content-Type: application/json

{
  "eslId": "ESL001",
  "brandCode": "PANDA",
  "forceRefresh": true,
  "storeCode": "STORE001"
}
```

#### 批量刷新价签
```http
POST /api/esl/refresh/batch
Content-Type: application/json

[
  {
    "eslId": "ESL001",
    "brandCode": "PANDA",
    "forceRefresh": true,
    "storeCode": "STORE001"
  }
]
```

#### 按门店刷新价签
```http
POST /api/esl/refresh/store/STORE001?brandCode=PANDA
```

#### 按商品刷新价签
```http
POST /api/esl/refresh/product/PRODUCT001?brandCode=PANDA
```

#### 获取支持的品牌
```http
GET /api/esl/brands
```

### 系统管理

#### 健康检查
```http
GET /api/system/health
```

#### 系统信息
```http
GET /api/system/info
```

## 扩展指南

### 添加新品牌适配器

1. 实现 `BrandAdapter` 接口：
```java
@Component
public class NewBrandAdapter implements BrandAdapter {
    @Override
    public String getSupportedBrandCode() {
        return "NEW_BRAND";
    }
    
    @Override
    public BrandOutputData convertData(EslCompleteData eslData) {
        // 实现品牌特定的数据转换逻辑
    }
    
    // 实现其他方法...
}
```

2. 在 `EslConfig` 中注册适配器：
```java
@Bean
public List<BrandAdapter> brandAdapters() {
    return Arrays.asList(
        new PandaBrandAdapter(),
        new NewBrandAdapter() // 添加新适配器
    );
}
```

### 添加新消息执行器

1. 实现 `MessageExecutor` 接口：
```java
@Component
public class HttpExecutor implements MessageExecutor {
    @Override
    public String getExecutorType() {
        return "HTTP";
    }
    
    @Override
    public boolean executeMessage(MessageExecutionData data) {
        // 实现HTTP消息发送逻辑
    }
    
    // 实现其他方法...
}
```

2. 在 `EslConfig` 中注册执行器：
```java
@Bean
public List<MessageExecutor> messageExecutors() {
    return Arrays.asList(
        new MqttExecutor(),
        new HttpExecutor() // 添加新执行器
    );
}
```

## 配置说明

### 价签系统配置
```yaml
esl:
  # 品牌配置
  brands:
    panda:
      name: 攀攀
      code: PANDA
      enabled: true
      executor-type: MQTT
      template-format: CUSTOM
      
  # 缓存配置
  cache:
    enabled: true
    ttl: 300 # 5分钟
    max-size: 1000
    
  # 批处理配置
  batch:
    size: 100
    timeout: 30000 # 30秒
    
  # 重试配置
  retry:
    max-attempts: 3
    delay: 1000 # 1秒
    multiplier: 2.0
```

## 监控和日志

### 日志配置
系统使用SLF4J + Logback进行日志管理，日志文件位于 `logs/esl-system.log`。

### 健康检查
访问 `/api/system/health` 可以检查系统运行状态。

## 故障排除

### 常见问题

1. **数据库连接失败**
   - 检查数据库服务是否启动
   - 验证连接信息是否正确
   - 确认数据库权限

2. **Redis连接失败**
   - 检查Redis服务是否启动
   - 验证连接信息是否正确
   - 检查网络连通性

3. **MQTT连接失败**
   - 检查MQTT Broker是否启动
   - 验证连接信息是否正确
   - 检查防火墙设置

4. **价签刷新失败**
   - 检查价签数据是否完整
   - 验证品牌适配器是否正确
   - 查看日志获取详细错误信息

## 贡献指南

1. Fork 项目
2. 创建特性分支
3. 提交更改
4. 推送到分支
5. 创建 Pull Request

## 许可证

本项目采用 MIT 许可证。

## 联系方式

- 项目维护者: PandaTech Team
- 邮箱: support@pandatech.com
- 文档: [项目Wiki](wiki链接)