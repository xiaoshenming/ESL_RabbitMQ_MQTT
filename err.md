# RabbitMQ队列异步处理系统实现总结

## 系统架构概述

本系统实现了基于RabbitMQ的异步消息处理架构，解决了前端并发请求和批量操作的性能问题。

### 核心架构流程
```
API请求 → 数据处理 → 发送到RabbitMQ队列 → 队列监听器消费 → MQTT发送
```

## 主要组件

### 1. 队列配置 (RabbitMQConfig.java)
- **template.queue**: 模板下发队列
- **refresh.queue**: 价签刷新队列
- 配置了消息转换器、RabbitAdmin、监听器容器工厂
- 支持并发消费者配置和预取数量控制

### 2. 消息生产者

#### EslRefreshService.java
- **refreshEsl()**: 主要刷新方法，将消息发送到refresh.queue
- **refreshEslDirect()**: 直接刷新方法，用于批量处理时绕过队列
- **sendToRefreshQueue()**: 构建队列消息并发送到RabbitMQ

#### TemplateServiceImpl.java
- **sendTemplate()**: 模板下发方法，将消息发送到template.queue
- 构建包含完整MQTT载荷的队列消息

### 3. 消息消费者 (RabbitMQListener.java)
- **receiveTemplateMessage()**: 监听template.queue
- **receiveRefreshMessage()**: 监听refresh.queue
- 支持新旧消息格式兼容
- 实现负载控制和处理间隔控制
- 集成监控统计功能

### 4. 批量处理服务

#### BatchRefreshService.java
- **batchRefresh()**: 异步批量刷新价签
- **groupByStore()**: 按门店分组请求
- **canProcessBatch()**: 系统负载检查
- **sendBatchRefreshToQueue()**: 批量消息发送到队列

### 5. 队列监控服务 (QueueMonitorService.java)
- 实时监控队列积压情况
- 统计消息处理成功/失败数量
- 系统健康状态检查
- 定时输出监控报告

### 6. 管理接口 (QueueController.java)
- `/api/queue/batch-refresh`: 批量刷新接口
- `/api/queue/batch-refresh-queue`: 批量消息入队接口
- `/api/queue/status`: 队列状态查询
- `/api/queue/health`: 系统健康检查
- `/api/queue/start-monitoring`: 启动监控
- `/api/queue/stop-monitoring`: 停止监控

## 消息格式设计

### 队列消息格式
```json
{
  "messageType": "refresh|template|batchRefresh",
  "brandCode": "品牌编码",
  "eslId": "价签ID",
  "storeCode": "门店编码",
  "mqttTopic": "MQTT主题",
  "mqttPayload": {
    "command": "refresh|tmpllist",
    "id": "ID",
    "timestamp": 时间戳,
    "shop": "门店编码",
    "data": {}
  },
  "timestamp": 时间戳,
  "priority": 优先级
}
```

### 批量刷新消息格式
```json
{
  "messageType": "batchRefresh",
  "storeCode": "门店编码",
  "refreshRequests": [
    {
      "eslId": "价签ID",
      "brandCode": "品牌编码"
    }
  ],
  "priority": 优先级,
  "timestamp": 时间戳
}
```

## 核心特性

### 1. 负载控制
- 最大并发处理数限制 (MAX_CONCURRENT_MESSAGES = 10)
- 最小处理间隔控制 (MIN_INTERVAL_MS = 100ms)
- 动态负载检查和延迟处理

### 2. 优先级处理
- 模板下发优先级: 0 (最高)
- 价签刷新优先级: 1
- 批量刷新优先级: 5 (可配置)

### 3. 兼容性设计
- 支持新旧消息格式
- 向后兼容现有MQTT消息结构
- 渐进式迁移支持

### 4. 监控和统计
- 实时队列状态监控
- 消息处理成功/失败统计
- 系统健康状态检查
- 定时监控报告

### 5. 错误处理
- 消息处理异常捕获
- 失败消息统计记录
- 详细错误日志输出
- 系统稳定性保障

## 性能优化

### 1. 队列配置优化
- 并发消费者: 2-5个
- 预取数量: 1 (确保负载均衡)
- 持久化队列保证消息不丢失

### 2. 批量处理优化
- 按门店分组处理
- 负载检查避免系统过载
- 处理间隔控制避免MQTT拥塞

### 3. 内存优化
- 消息序列化/反序列化优化
- 对象复用减少GC压力
- 合理的线程池配置

## 部署和配置

### 1. RabbitMQ配置
```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

### 2. 应用配置
- 确保RabbitMQ服务运行
- 配置MQTT连接参数
- 设置合适的日志级别

### 3. 监控配置
- 启用队列监控服务
- 配置监控间隔和阈值
- 设置告警机制

## 使用示例

### 1. 单个价签刷新
```java
EslRefreshRequest request = new EslRefreshRequest();
request.setEslId("ESL001");
request.setBrandCode("攀攀");
request.setStoreCode("STORE001");
eslRefreshService.refreshEsl(request);
```

### 2. 批量价签刷新
```java
List<EslRefreshRequest> requests = Arrays.asList(
    new EslRefreshRequest("ESL001", "攀攀", "STORE001"),
    new EslRefreshRequest("ESL002", "攀攀", "STORE001")
);
batchRefreshService.batchRefresh(requests);
```

### 3. 模板下发
```java
templateService.sendTemplate("STORE001", "TEMPLATE001", "模板名称");
```

## 总结

本系统成功实现了基于RabbitMQ的异步消息处理架构，具有以下优势：

1. **高性能**: 通过队列异步处理，避免前端请求阻塞
2. **高可靠**: 消息持久化和错误处理机制保证系统稳定
3. **可扩展**: 支持批量处理和负载控制，适应业务增长
4. **可监控**: 完整的监控和统计功能，便于运维管理
5. **兼容性**: 支持新旧格式，平滑迁移现有系统

该系统能够有效解决前端并发请求和批量操作的性能问题，为ESL价签管理系统提供了稳定可靠的消息处理能力。