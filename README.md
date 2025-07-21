# ESL平台价签管理系统

## 项目简介

这是一个基于Spring Boot的价签管理系统，主要功能包括模板下发和价签刷新。系统通过RabbitMQ消息队列和MQTT协议与价签设备进行通信。

## 主要功能

### 1. 模板下发
- 根据模板ID和门店编码从数据库获取模板信息
- 构造MQTT消息格式并发送到RabbitMQ队列
- 支持多种价签型号的模板适配
- 自动计算模板文件的MD5校验码

### 2. 价签刷新
- 根据价签ID查询价签和商品信息
- 通过字段映射配置实现灵活的数据转换
- 构造符合MQTT协议的刷新消息
- 支持强制刷新和校验码验证

### 3. 模板下载
- 提供模板文件的二进制流下载
- 支持按ID或名称查找模板
- 自动生成标准化的文件名格式

## 技术架构

### 后端技术栈
- **Spring Boot 2.x** - 主框架
- **MyBatis** - 数据访问层
- **MySQL** - 数据库
- **RabbitMQ** - 消息队列
- **MQTT** - 设备通信协议
- **Druid** - 数据库连接池
- **Swagger** - API文档

### 数据库表结构
- `PANDA_ESL` - 价签信息表
- `PANDA_PRODUCT` - 商品信息表
- `ESL_BRAND_FIELD_MAPPING` - 字段映射配置表
- `PRINT_TEMPLATE_DESIGN` - 模板设计表

## 核心业务流程

### 价签刷新流程
1. 接收价签刷新请求（包含ESL ID）
2. 根据ESL ID查询价签信息
3. 根据价签绑定的商品ID查询商品信息
4. 获取对应品牌的字段映射配置
5. 构造MQTT消息格式
6. 计算数据校验码
7. 发送消息到RabbitMQ队列
8. 通过MQTT推送到价签设备

### 字段映射机制
- 支持按品牌配置不同的字段映射规则
- 可配置格式化规则对数据进行处理
- 支持强制转换特定字段（如商品名称、商品编码）

## API接口

### 1. 模板下发
```
POST /api/res/templ/send
Content-Type: application/json

{
    "templateId": "模板ID",
    "storeCode": "门店编码"
}
```

### 2. 价签刷新
```
POST /api/res/templ/refresh
Content-Type: application/json

{
    "eslId": "价签ID"
}
```

### 3. 模板下载
```
POST /api/res/templ/loadtemple
Content-Type: application/json

{
    "id": "模板ID",
    "name": "模板名称"
}
```

## 配置说明

### 应用配置 (application.yml)
```yaml
app:
  template:
    base-url: http://localhost:8999/api/res/templ/loadtemple
  esl:
    refresh:
      default-tenant-id: "396a5189-53d8-4354-bcfa-27d57d9d69ad"
      default-store-code: "STORE001"
      force-refresh: true
      default-template-id: "default_template"
      checksum-algorithm: "md5"
    field-mapping:
      default-brand-code: "DEFAULT"
      force-convert-fields:
        - "GOODS_NAME"
        - "GOODS_CODE"
```

### 数据库配置
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/eslplatform
    username: your_username
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### RabbitMQ配置
```yaml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: your_username
    password: your_password
```

### MQTT配置
```yaml
spring:
  mqtt:
    username: your_username
    password: your_password
    url: tcp://localhost:1883
    client:
      id: data_server_1
    default:
      topic: esl/server/data
```

## 部署说明

### 环境要求
- JDK 8+
- MySQL 5.7+
- RabbitMQ 3.8+
- MQTT Broker (如 Mosquitto)

### 启动步骤
1. 确保数据库、RabbitMQ和MQTT服务正常运行
2. 修改application.yml中的配置信息
3. 执行数据库初始化脚本
4. 启动Spring Boot应用

```bash
mvn spring-boot:run
```

### 访问地址
- 应用地址: http://localhost:8999
- Swagger文档: http://localhost:8999/swagger-ui.html
- API文档: http://localhost:8999/v3/api-docs

## 开发指南

### 项目结构
```
src/main/java/com/pandatech/downloadcf/
├── config/          # 配置类
├── controller/      # 控制器层
├── dto/            # 数据传输对象
├── entity/         # 实体类
├── mapper/         # 数据访问层
├── service/        # 业务逻辑层
└── util/           # 工具类
```

### 扩展开发
1. **添加新的字段映射**: 在ESL_BRAND_FIELD_MAPPING表中配置新的映射规则
2. **支持新的价签型号**: 在getModelFromEslModel方法中添加新的型号映射
3. **自定义格式化规则**: 在applyFormatRule方法中实现新的格式化逻辑

## 注意事项

1. **数据一致性**: 确保价签绑定的商品信息存在且有效
2. **消息格式**: MQTT消息格式需要严格按照协议规范
3. **错误处理**: 系统会记录详细的错误日志，便于问题排查
4. **性能优化**: 大批量操作时建议分批处理，避免系统负载过高

## 故障排查

### 常见问题
1. **价签刷新失败**: 检查价签ID是否存在，商品信息是否完整
2. **模板下发失败**: 检查模板ID是否有效，文件是否存在
3. **消息队列异常**: 检查RabbitMQ连接配置和队列状态
4. **MQTT连接失败**: 检查MQTT Broker地址和认证信息

### 日志查看
系统使用SLF4J记录日志，可以通过以下方式查看：
- 控制台输出
- 日志文件 (logs目录下)
- 数据库操作日志 (MyBatis日志)

## 版本历史

- v1.0.0 - 初始版本，支持基本的模板下发和价签刷新功能
- v1.1.0 - 增加字段映射配置，支持灵活的数据转换
- v1.2.0 - 优化消息格式，增加校验码验证机制