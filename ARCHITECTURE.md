# 价签模板下发系统 - 重构架构设计

## 核心业务流程
1. **数据获取**: 根据价签ID获取商品、门店、模板等相关数据
2. **数据转换**: 通过品牌适配器将数据转换为特定品牌的输出格式
3. **消息生产**: 将转换后的数据发送到对应门店的消息队列
4. **消息执行**: 通过不同的执行器发送到对应的设备（MQTT、HTTP等）

## 分层架构设计

### 1. 数据访问层 (Repository Layer)
- **DataService**: 统一的数据访问服务，提供便捷的数据获取方法
- **Repository**: 基于MyBatis的数据访问接口

### 2. 业务逻辑层 (Service Layer)
- **EslRefreshService**: 价签刷新核心业务逻辑
- **TemplateService**: 模板管理服务
- **DataTransformService**: 数据转换服务

### 3. 适配器层 (Adapter Layer)
- **BrandAdapter**: 品牌适配器接口，支持不同品牌的数据格式转换
- **PandaBrandAdapter**: 攀攀品牌适配器
- **YaliangBrandAdapter**: 雅量品牌适配器（预留）

### 4. 消息层 (Message Layer)
- **MessageProducer**: 消息生产者，负责将数据发送到队列
- **MessageExecutor**: 消息执行器接口，支持不同的执行方式
- **MqttExecutor**: MQTT执行器
- **HttpExecutor**: HTTP执行器（预留）

### 5. 控制层 (Controller Layer)
- **EslController**: 价签相关API接口
- **TemplateController**: 模板相关API接口

## 核心数据结构

### 统一数据传输对象 (DTO)
```java
// 价签刷新请求
public class EslRefreshRequest {
    private String eslId;
    private String brandCode;
    private boolean forceRefresh;
}

// 价签完整数据
public class EslCompleteData {
    private PandaEsl esl;
    private PandaProduct product;
    private PrintTemplateDesign template;
    private List<EslBrandFieldMapping> fieldMappings;
    private String storeCode;
}

// 品牌输出数据
public class BrandOutputData {
    private String brandCode;
    private Map<String, Object> data;
    private String templateContent;
    private String checksum;
}

// 消息执行数据
public class MessageExecutionData {
    private String executorType;
    private String destination;
    private Object payload;
    private Map<String, String> headers;
}
```

## 扩展性设计

### 品牌扩展
- 通过实现 `BrandAdapter` 接口支持新品牌
- 配置文件中注册新的品牌适配器
- 支持品牌特定的字段映射和模板格式

### 执行器扩展
- 通过实现 `MessageExecutor` 接口支持新的执行方式
- 支持不同的消息格式和传输协议
- 支持动态路由和负载均衡

### 队列策略
- 支持按门店分队列
- 支持按品牌分队列
- 支持优先级队列

## 关键算法

### 数据获取算法
1. 根据价签ID获取价签基本信息
2. 根据价签绑定的商品ID获取商品信息
3. 根据商品和价签信息获取对应的模板
4. 根据品牌获取字段映射配置

### 模板转换算法
1. 解析模板内容（支持多种格式）
2. 根据字段映射转换数据
3. 生成最终的模板内容
4. 计算校验码

### 消息路由算法
1. 根据门店编码确定目标队列
2. 根据品牌确定执行器类型
3. 根据设备类型确定消息格式

## 配置管理

### 品牌配置
```yaml
app:
  brands:
    panda:
      adapter: com.pandatech.downloadcf.adapter.PandaBrandAdapter
      executor: mqtt
      queue-prefix: esl.panda
    yaliang:
      adapter: com.pandatech.downloadcf.adapter.YaliangBrandAdapter
      executor: http
      queue-prefix: esl.yaliang
```

### 执行器配置
```yaml
app:
  executors:
    mqtt:
      class: com.pandatech.downloadcf.executor.MqttExecutor
      config:
        broker: tcp://10.3.36.25:1883
        topic-pattern: esl/{storeCode}/data
    http:
      class: com.pandatech.downloadcf.executor.HttpExecutor
      config:
        base-url: http://api.example.com
        endpoint-pattern: /esl/{storeCode}/refresh
```

## 性能优化

### 缓存策略
- 模板内容缓存（基于模板ID和版本）
- 字段映射缓存（基于品牌编码）
- 商品信息缓存（基于商品ID）

### 批处理
- 支持批量价签刷新
- 支持批量消息发送
- 支持事务性操作

### 异步处理
- 异步消息发送
- 异步模板转换
- 异步日志记录

## 监控和日志

### 关键指标
- 价签刷新成功率
- 消息发送延迟
- 模板转换耗时
- 队列积压情况

### 日志记录
- 请求响应日志
- 业务操作日志
- 错误异常日志
- 性能监控日志