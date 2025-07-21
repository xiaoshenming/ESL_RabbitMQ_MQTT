# 价签模板下发系统

## 项目概述

这是一个基于Spring Boot的价签模板下发系统，主要功能包括模板下发、价签刷新和MQTT消息通信。系统通过RabbitMQ消息队列和MQTT协议实现与价签设备的通信。

## 技术栈

- **Spring Boot 3.x** - 主框架
- **MyBatis** - 数据持久化
- **MySQL** - 数据库
- **RabbitMQ** - 消息队列
- **MQTT** - 物联网通信协议
- **Swagger/OpenAPI 3** - API文档
- **Jackson** - JSON处理
- **Lombok** - 代码简化

## 项目结构

```
src/main/java/com/pandatech/downloadcf/
├── DownloadcfApplication.java          # Spring Boot启动类
├── config/                             # 配置类目录
│   ├── EslRefreshProperties.java       # 价签刷新配置属性
│   ├── MqttConfig.java                 # MQTT客户端配置
│   ├── RabbitMQConfig.java             # RabbitMQ队列配置
│   ├── SwaggerConfig.java              # API文档配置
│   └── TemplateConfig.java             # 模板相关配置
├── controller/                         # 控制器目录
│   └── TemplateController.java         # 模板和价签操作控制器
├── dto/                                # 数据传输对象目录
│   ├── LoadTemplateRequest.java        # 模板加载请求DTO
│   ├── MqttDataDto.java               # MQTT数据DTO
│   ├── MqttMessageDto.java            # MQTT消息DTO
│   ├── RefreshDto.java                # 价签刷新请求DTO
│   └── TemplateDto.java               # 模板下发请求DTO
├── entity/                            # 实体类目录
│   ├── EslBrand.java                  # 价签品牌实体
│   ├── EslBrandExample.java           # 价签品牌查询条件
│   ├── EslBrandFieldMapping.java      # 价签品牌字段映射实体
│   ├── EslBrandFieldMappingExample.java # 字段映射查询条件
│   ├── EslFieldDefinition.java        # 价签字段定义实体
│   ├── EslFieldDefinitionExample.java # 字段定义查询条件
│   ├── EslModel.java                  # 价签型号实体
│   ├── EslModelExample.java           # 价签型号查询条件
│   ├── PandaEsl.java                  # 价签设备实体
│   ├── PandaEslExample.java           # 价签设备查询条件
│   ├── PandaProduct.java              # 商品信息实体
│   ├── PandaProductExample.java       # 商品查询条件
│   ├── PandaProductWithBLOBs.java     # 包含大字段的商品实体
│   ├── PrintTemplateDesign.java       # 模板设计实体
│   ├── PrintTemplateDesignExample.java # 模板设计查询条件
│   ├── PrintTemplateDesignWithBLOBs.java # 包含大字段的模板实体
│   ├── ProductEslBinding.java         # 商品价签绑定实体
│   └── ProductEslBindingExample.java  # 绑定关系查询条件
├── exception/                         # 异常类目录
│   └── TemplateException.java         # 模板相关异常类
├── mapper/                            # MyBatis映射器目录
│   ├── EslBrandFieldMappingMapper.java # 字段映射数据访问接口
│   ├── EslBrandMapper.java            # 价签品牌数据访问接口
│   ├── EslFieldDefinitionMapper.java  # 字段定义数据访问接口
│   ├── EslModelMapper.java            # 价签型号数据访问接口
│   ├── PandaEslMapper.java            # 价签设备数据访问接口
│   ├── PandaProductMapper.java        # 商品信息数据访问接口
│   ├── PrintTemplateDesignMapper.java # 模板设计数据访问接口
│   └── ProductEslBindingMapper.java   # 商品价签绑定数据访问接口
├── service/                           # 服务层目录
│   ├── EslRefreshService.java         # 价签刷新服务接口
│   ├── EslRefreshServiceImpl.java     # 价签刷新服务实现
│   ├── FieldMappingService.java       # 字段映射服务
│   ├── MqttService.java               # MQTT消息处理服务
│   ├── RabbitMQListener.java          # RabbitMQ消息监听器
│   ├── TemplateService.java           # 模板服务接口
│   └── TemplateServiceImpl.java       # 模板服务实现
└── util/                              # 工具类目录
    ├── ScreenTypeConverter.java       # 屏幕类型转换工具
    ├── ScreenTypeMapper.java          # 屏幕类型映射工具
    └── TemplateValidator.java         # 模板验证工具
```

## 核心功能模块

### 1. 配置模块 (config/)

- **EslRefreshProperties.java**: 价签刷新相关配置，包括默认租户ID、门店编码、模板ID/名称等
- **MqttConfig.java**: MQTT客户端配置，包括连接选项、消息处理器和通道配置
- **RabbitMQConfig.java**: RabbitMQ队列配置，定义模板队列和刷新队列
- **SwaggerConfig.java**: API文档配置，提供详细的接口文档和示例
- **TemplateConfig.java**: 模板相关配置，包括默认模板、屏幕类型映射、MQTT和验证配置

### 2. 控制器模块 (controller/)

- **TemplateController.java**: 提供三个核心API接口
  - `/api/res/templ/send` - 模板下发接口
  - `/api/res/templ/refresh` - 价签刷新接口
  - `/api/res/templ/loadtemple` - 模板文件下载接口

### 3. 数据传输对象 (dto/)

- **TemplateDto.java**: 模板下发请求体，包含模板ID和门店编码
- **RefreshDto.java**: 价签刷新请求体，包含价签ID
- **LoadTemplateRequest.java**: 模板加载请求体，支持按ID或名称查询
- **MqttMessageDto.java**: MQTT消息格式，包含命令、数据、时间戳等
- **MqttDataDto.java**: MQTT数据格式，包含价签标签、模板、型号、校验码等

### 4. 实体类模块 (entity/)

包含所有数据库表对应的实体类和查询条件类：
- **价签相关**: PandaEsl、EslModel、EslBrand等
- **商品相关**: PandaProduct、ProductEslBinding等
- **模板相关**: PrintTemplateDesign等
- **配置相关**: EslFieldDefinition、EslBrandFieldMapping等

### 5. 异常处理 (exception/)

- **TemplateException.java**: 模板相关异常基类，包含多个子异常类
  - TemplateNotFoundException - 模板未找到异常
  - InvalidTemplateFormatException - 模板格式无效异常
  - TemplateConversionException - 模板转换异常
  - TemplateValidationException - 模板验证异常
  - MqttSendException - MQTT发送异常
  - ScreenTypeConversionException - 屏幕类型转换异常

### 6. 数据访问层 (mapper/)

基于MyBatis的数据访问接口，提供标准的CRUD操作：
- 支持按条件查询、分页查询
- 支持批量操作
- 包含BLOB字段的特殊处理

### 7. 服务层 (service/)

- **TemplateService**: 模板服务，处理模板下发、加载和价签刷新
- **EslRefreshService**: 价签刷新服务，构建MQTT消息和发送刷新请求
- **MqttService**: MQTT消息处理服务，处理消息发送和接收
- **FieldMappingService**: 字段映射服务，管理品牌字段映射关系
- **RabbitMQListener**: RabbitMQ消息监听器，处理队列消息

### 8. 工具类 (util/)

- **ScreenTypeConverter.java**: 屏幕类型转换工具，支持多种转换策略
- **ScreenTypeMapper.java**: 屏幕类型映射工具，提供静态映射关系
- **TemplateValidator.java**: 模板验证工具，验证模板格式和内容

## 系统架构

```
客户端请求 → REST API → RabbitMQ队列 → 后端处理 → MQTT推送 → 价签设备
```

### 消息流程

1. **模板下发流程**:
   - 客户端调用 `/send` 接口
   - 系统查询数据库获取模板信息
   - 构造MQTT消息并发送到RabbitMQ
   - RabbitMQ监听器处理消息并通过MQTT推送到设备

2. **价签刷新流程**:
   - 客户端调用 `/refresh` 接口
   - 系统根据价签ID查询设备和商品信息
   - 通过字段映射构造完整的刷新数据
   - 发送MQTT消息到指定设备

3. **模板下载流程**:
   - 客户端调用 `/loadtemple` 接口
   - 系统根据ID或名称查询模板
   - 返回模板文件的二进制流

## 数据库设计

系统涉及的主要数据表：
- **PANDA_ESL**: 价签设备信息
- **PANDA_PRODUCT**: 商品信息
- **PRINT_TEMPLATE_DESIGN**: 模板设计
- **ESL_BRAND_FIELD_MAPPING**: 品牌字段映射
- **ESL_MODEL**: 价签型号
- **PRODUCT_ESL_BINDING**: 商品价签绑定关系

## 配置说明

### 应用配置
```yaml
app:
  template:
    base-url: http://localhost:8999/api/res/templ/loadtemple
  esl:
    refresh:
      default-tenant-id: "1"
      default-store-code: "001"
      default-template-id: "PRICEPROMO"
      default-template-name: "2"
```

### MQTT配置
- 支持消息重试和超时设置
- 可配置是否包含模板内容
- 支持消息压缩

### RabbitMQ队列
- `template.queue`: 模板下发队列
- `refresh.queue`: 价签刷新队列

## API接口

### 1. 模板下发
```http
POST /api/res/templ/send
Content-Type: application/json

{
  "templateId": "1945045387689762818",
  "storeCode": "009"
}
```

### 2. 价签刷新
```http
POST /api/res/templ/refresh
Content-Type: application/json

{
  "eslId": "1"
}
```

### 3. 模板下载
```http
POST /api/res/templ/loadtemple
Content-Type: application/json

{
  "id": "1945045387689762818",
  "name": "模板名称"
}
```

## 部署说明

### 环境要求
- JDK 17+
- MySQL 8.0+
- RabbitMQ 3.8+
- MQTT Broker

### 启动步骤
1. 配置数据库连接
2. 启动RabbitMQ服务
3. 启动MQTT Broker
4. 运行Spring Boot应用

### 访问地址
- 应用地址: http://localhost:8999
- API文档: http://localhost:8999/swagger-ui.html
- 测试环境: http://10.3.36.25:8999

## 代码质量检查结果

经过全面的代码检查，确认：

✅ **无冗余代码**: 所有类和方法都有明确的业务用途
✅ **架构清晰**: 分层架构合理，职责分离明确
✅ **配置完整**: 所有配置类都有实际用途
✅ **异常处理**: 完善的异常处理机制
✅ **工具类实用**: 所有工具类都被实际使用
✅ **服务层完整**: 业务逻辑封装良好
✅ **数据访问规范**: MyBatis映射器标准化

## 维护说明

- 定期检查MQTT连接状态
- 监控RabbitMQ队列积压情况
- 关注模板验证错误日志
- 定期清理过期的消息数据

---

**开发团队**: 熊猫科技开发团队  
**联系邮箱**: dev@pandatech.com  
**项目版本**: 1.0.0