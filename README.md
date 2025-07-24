# 价签模板下发系统 (ESL Template Distribution System)

## 项目概述

价签模板下发系统是一个企业级的电子价签管理平台，专门用于管理和控制电子价签的数据刷新和模板下发。系统采用微服务架构，通过RabbitMQ消息队列和MQTT协议实现高效、可靠的价签数据传输。

### 核心功能
- 🏷️ **价签管理**: 支持单个、批量、按商品、按门店的价签刷新
- 🎨 **模板管理**: 动态模板下发和管理
- 🔄 **品牌适配**: 支持多品牌价签协议转换（支持panda/PANDA/攀攀三种输入格式）
- 📊 **负载控制**: 智能负载监控和流量控制
- 🚀 **异步处理**: 基于消息队列的异步处理机制
- 📈 **监控统计**: 实时队列监控和处理统计

## 技术栈

### 后端框架
- **Spring Boot 3.2.5**: 主框架
- **Spring Integration**: 消息集成
- **MyBatis**: 数据持久层
- **Maven**: 项目构建工具

### 消息中间件
- **RabbitMQ**: 消息队列服务
- **MQTT**: 物联网消息传输协议

### 数据库
- **MySQL**: 主数据库
- **Druid**: 数据库连接池

### 工具库
- **Lombok**: 代码简化
- **Hutool**: Java工具库
- **Jackson**: JSON处理
- **Swagger/OpenAPI**: API文档

## 系统架构

### 整体架构图
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   前端应用      │───▶│   REST API      │───▶│   业务服务层    │
│   (Web/App)     │    │   Controller    │    │   Service       │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                                        │
                                                        ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   数据库        │◀───│   数据访问层    │◀───│   品牌适配器    │
│   MySQL         │    │   Mapper/DAO    │    │   BrandAdapter  │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                                        │
                                                        ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   MQTT Broker   │◀───│   消息消费者    │◀───│   RabbitMQ      │
│   (价签设备)    │    │   Listener      │    │   消息队列      │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### 数据流转图
```
数据库数据 ──▶ 数据提取 ──▶ 品牌适配器 ──▶ 数据转换 ──▶ RabbitMQ队列
                                                           │
                                                           ▼
价签设备 ◀── MQTT消息 ◀── 负载控制 ◀── 消息消费 ◀── 队列监控
```

## 核心概念详解

### 1. 队列 (Queue)

系统使用RabbitMQ实现两个核心队列：

#### 模板队列 (template.queue)
- **用途**: 处理模板下发消息
- **持久化**: 是
- **优先级**: 高
- **消费者**: 模板消息监听器

#### 刷新队列 (refresh.queue)
- **用途**: 处理价签刷新消息
- **持久化**: 是
- **优先级**: 中
- **消费者**: 刷新消息监听器

```java
// 队列配置示例
@Bean
public Queue templateQueue() {
    return new Queue("template.queue", true); // 持久化队列
}

@Bean
public Queue refreshQueue() {
    return new Queue("refresh.queue", true); // 持久化队列
}
```

### 2. 延迟 (Delay)

系统实现多层次的延迟控制机制：

#### 处理间隔延迟
- **最小处理间隔**: 100ms
- **目的**: 防止系统过载
- **实现**: `LoadInfoService.MIN_INTERVAL_MS`

#### 重试延迟
- **初始延迟**: 1秒
- **延迟倍数**: 2.0
- **最大重试**: 3次
- **实现**: 指数退避算法

```java
// 延迟控制示例
private static final long MIN_INTERVAL_MS = 100;
private void controlProcessingRate() {
    long timeSinceLastProcess = System.currentTimeMillis() - lastProcessTime.get();
    if (timeSinceLastProcess < MIN_INTERVAL_MS) {
        Thread.sleep(MIN_INTERVAL_MS - timeSinceLastProcess);
    }
}
```

### 3. 等待 (Wait)

系统实现智能等待机制：

#### 负载等待
- **最大并发数**: 10个消息
- **等待条件**: 当前负载 >= 最大并发数
- **等待策略**: 阻塞等待直到负载降低

#### 队列等待
- **监控频率**: 每30秒检查一次
- **告警阈值**: 模板队列>100, 刷新队列>1000
- **处理策略**: 自动调整消费速率

```java
// 负载控制示例
public boolean canProcess() {
    return currentLoad.get() < MAX_CONCURRENT_MESSAGES;
}

public void waitForCapacity() {
    while (!canProcess()) {
        Thread.sleep(50); // 等待50ms后重试
    }
}
```

### 4. MQTT

MQTT协议用于与价签设备通信：

#### 连接配置
- **Broker地址**: tcp://10.3.36.25:1883
- **客户端ID**: data_server_1
- **用户名/密码**: panda/panda@123
- **保活间隔**: 2秒

#### 主题规则
- **数据主题**: `esl/server/data/{storeCode}`
- **模板主题**: `esl/template/{templateId}`
- **QoS级别**: 1 (至少一次传递)

```java
// MQTT消息发送示例
private void sendMqttMessage(String topic, byte[] payload) {
    Message<byte[]> message = MessageBuilder
            .withPayload(payload)
            .setHeader("mqtt_topic", topic)
            .build();
    mqttOutboundChannel.send(message);
}
```

### 5. 消息 (Message)

系统定义了标准的消息格式：

#### 刷新消息格式
```json
{
    "messageType": "refresh",
    "brandCode": "panda",
    "eslId": "ESL001",
    "storeCode": "STORE001",
    "mqttTopic": "esl/server/data/STORE001",
    "mqttPayload": {
        "data": "...",
        "template": "...",
        "checksum": "..."
    },
    "timestamp": 1703123456789,
    "priority": 1
}
```

#### 模板消息格式
```json
{
    "messageType": "template",
    "templateId": "TEMPLATE001",
    "storeCode": "STORE001",
    "templateData": "...",
    "timestamp": 1703123456789,
    "priority": 0
}
```

### 6. 数据转化 (Data Transformation)

品牌适配器负责数据格式转换：

#### 转换流程
1. **数据验证**: 检查必要字段完整性
2. **字段映射**: 根据品牌字段映射表转换
3. **格式化**: 按品牌协议格式化数据
4. **校验码**: 生成MD5校验码

#### Panda品牌适配器

系统支持panda/PANDA/攀攀三种输入格式的兼容性处理：

```java
// 使用BrandCodeUtil进行品牌代码标准化
String normalizedCode = BrandCodeUtil.normalizeBrandCode("PANDA"); // 返回 "panda"
String adapterCode = BrandCodeUtil.toAdapterBrandCode("panda"); // 返回 "攀攀"

@Override
public BrandOutputData transform(EslCompleteData completeData) {
    // 1. 构建数据映射
    Map<String, Object> dataMap = buildDataMap(completeData);
    
    // 2. 处理模板
    String templateContent = processTemplate(completeData);
    
    // 3. 生成校验码
    String checksum = generateChecksum(dataMap, templateContent);
    
    // 4. 构建输出数据
    BrandOutputData outputData = new BrandOutputData();
    outputData.setBrandCode("panda"); // 支持panda/PANDA/攀攀三种格式
    outputData.setDataMap(dataMap);
    outputData.setTemplate(templateContent);
    outputData.setChecksum(checksum);
    
    return outputData;
}
```

### 7. 数据提取 (Data Extraction)

数据服务层负责从数据库提取完整数据：

#### 数据提取流程
1. **价签信息**: 从`PANDA_ESL`表获取基本信息
2. **商品信息**: 从`PANDA_PRODUCT`表获取商品数据
3. **模板信息**: 从`PRINT_TEMPLATE_DESIGN`表获取模板
4. **字段映射**: 从`ESL_BRAND_FIELD_MAPPING`表获取映射规则

```java
public EslCompleteData getEslCompleteData(String eslId) {
    // 1. 获取价签基本信息
    PandaEsl esl = pandaEslMapper.selectByPrimaryKey(eslId);
    
    // 2. 获取绑定的商品信息
    PandaProductWithBLOBs product = getProductByEslId(eslId);
    
    // 3. 获取模板信息
    PrintTemplateDesignWithBLOBs template = getTemplateByEslId(eslId);
    
    // 4. 获取字段映射
    List<EslBrandFieldMapping> fieldMappings = getFieldMappingsByBrand(brandCode);
    
    // 5. 组装完整数据
    EslCompleteData completeData = new EslCompleteData();
    completeData.setEsl(esl);
    completeData.setProduct(product);
    completeData.setTemplate(template);
    completeData.setFieldMappings(fieldMappings);
    
    return completeData;
}
```

## 整体业务逻辑

### 价签刷新完整流程

1. **接收请求**: REST API接收刷新请求
2. **数据提取**: DataService从数据库提取完整数据
3. **品牌适配**: BrandAdapter进行数据格式转换
4. **消息生产**: 将转换后的数据发送到RabbitMQ队列
5. **负载控制**: 系统监控当前负载，控制消费速率
6. **消息消费**: RabbitMQListener消费队列消息
7. **MQTT发送**: 通过MQTT协议发送到价签设备

### 负载控制机制

```java
// 负载控制核心逻辑
@RabbitListener(queues = "refresh.queue")
public void handleRefreshMessage(String message) {
    // 1. 检查系统负载
    if (!loadInfoService.canProcess()) {
        loadInfoService.waitForCapacity();
    }
    
    // 2. 增加负载计数
    loadInfoService.incrementLoad();
    
    try {
        // 3. 处理消息
        processRefreshMessage(message);
        
        // 4. 记录成功
        queueMonitorService.recordMessageSuccess("refresh");
        
    } catch (Exception e) {
        // 5. 记录失败
        queueMonitorService.recordMessageFailure("refresh");
        
    } finally {
        // 6. 减少负载计数
        loadInfoService.decrementLoad();
        
        // 7. 更新最后处理时间
        loadInfoService.updateLastProcessTime();
    }
}
```

## 配置说明

### 数据库配置
```yaml
spring:
  datasource:
    url: jdbc:mysql://10.3.36.25:3306/eslplatform
    username: panda
    password: panda@123
    type: com.alibaba.druid.pool.DruidDataSource
```

### RabbitMQ配置
```yaml
spring:
  rabbitmq:
    host: 10.3.36.15
    port: 5672
    username: panda
    password: panda@123
```

### MQTT配置
```yaml
spring:
  mqtt:
    username: panda
    password: panda@123
    url: tcp://10.3.36.25:1883
    client:
      id: data_server_1
```

### 业务配置
```yaml
app:
  esl:
    refresh:
      default-tenant-id: "396a5189-53d8-4354-bcfa-27d57d9d69ad"
      default-store-code: "STORE001"
      force-refresh: true
      checksum-algorithm: "md5"
    batch:
      size: 100
      timeout: 30000
    retry:
      max-attempts: 3
      delay: 1000
      multiplier: 2.0
```

## 监控和运维

### 队列监控
- **监控频率**: 每30秒
- **统计输出**: 每5分钟
- **健康检查**: 队列积压和成功率监控

### 性能指标
- **最大并发**: 10个消息同时处理
- **处理间隔**: 最小100ms
- **队列告警**: 模板队列>100, 刷新队列>1000
- **成功率**: 目标>95%

### 日志配置
```yaml
logging:
  level:
    com.pandatech.downloadcf.mapper: debug
```

## 扩展性设计

### 品牌适配器扩展
系统支持添加新的品牌适配器：

```java
// 添加雅量品牌适配器示例
@Component
public class YaliangBrandAdapter implements BrandAdapter {
    @Override
    public String getSupportedBrandCode() {
        return "雅量";
    }
    
    @Override
    public BrandOutputData transform(EslCompleteData completeData) {
        // 雅量品牌特定的转换逻辑
        return transformToYaliangFormat(completeData);
    }
}
```

### 输出层扩展
系统支持添加新的输出方式：

```java
// 添加HTTP输出层示例
@Component
public class HttpOutputAdapter implements OutputAdapter {
    @Override
    public void sendMessage(BrandOutputData outputData) {
        // HTTP方式发送消息
        httpClient.post(outputData);
    }
}
```

## 部署说明

### 环境要求
- JDK 17+
- MySQL 8.0+
- RabbitMQ 3.8+
- MQTT Broker (如Mosquitto)

### 启动步骤
1. 配置数据库连接
2. 启动RabbitMQ服务
3. 启动MQTT Broker
4. 运行Spring Boot应用

```bash
# 编译项目
mvn clean package

# 启动应用
java -jar target/cfdownloadexample-1.0.0.jar
```

### API文档
启动后访问: http://localhost:8999/swagger-ui.html

## 故障排查

### 常见问题
1. **队列积压**: 检查消费者是否正常工作
2. **MQTT连接失败**: 检查网络和认证配置
3. **数据转换错误**: 检查字段映射配置
4. **负载过高**: 调整并发参数

### 日志分析
- 查看队列处理日志
- 监控MQTT连接状态
- 分析错误堆栈信息

## 版本信息
- **当前版本**: 1.0.0
- **开发团队**: PandaTech
- **最后更新**: 2024年

---

*本文档详细介绍了价签模板下发系统的架构设计、核心概念和使用方法。如有疑问，请联系开发团队。*