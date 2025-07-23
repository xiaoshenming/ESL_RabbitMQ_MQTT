
我希望重构代码。用mybatis直接生成完整的实体类。保证项目的高效及其便于维护。（有bug，生成之前要把原本的xml删除，避免错误。）重构代码，用dto的逻辑去重构代码。希望把数据库得出的数据转化成一个工具类利于调用。比如我能用一个方法直接拿到数据库的指定的模板，用一个方法拿到指定的数据库的指定的商品数据、商品对应的门店全部数据、商品对应的价签全部数据、商品对应的模板的全部数据。

然后定义输出层跟中间层。中间层负责把数据转化成实际要的输出效果。并且需要指定。比如目前的映射表是只有“攀攀”整个品牌。到时候还会有其他品牌。比如“雅量”，雅量的输出结构又不一样。所有需要我们去做适配。通过调用不同的中间件来输出不同的输出层。然后通过生产者生产到队列上，一个门店一个队列（或者你有更好的方法也行）。然后通过执行层来实现不同的执行方式，比如攀攀的执行方式就是目前的方式mqtt的消息，其他科技发出去的消息格式以及订阅地址规则不同。待定。
总之就是要写出一个贼有拓展性的“企业级”项目。请先把核心结构算法数据结构等关键的以及各种你认为的各种重要的东西先存一个文件里面避免忘记。且删除全部代码后重新开始重构代码。
# 企业级电子价签系统重构提示词

## 项目概述
基于Spring Boot 3.x + MyBatis + MySQL + RabbitMQ + MQTT的企业级电子价签管理系统，支持多品牌适配、模板管理、价签刷新等核心功能。

## 重构目标
构建一个高度可扩展、易维护的"企业级"项目架构，支持多品牌、多协议、多队列的灵活配置。

## 技术栈
- **框架**: Spring Boot 3.4.5 + Java 17
- **数据层**: MyBatis 3.0.3 + MySQL 8.0.33 + Druid连接池
- **消息队列**: RabbitMQ + MQTT
- **工具库**: Hutool 5.8.24 + Lombok
- **API文档**: SpringDoc OpenAPI 2.2.0

## 重构步骤

### 第一阶段：数据层重构

#### 1.1 清理现有代码
```bash
# 删除现有的mapper XML文件（避免MyBatis Generator冲突）
rm -rf src/main/resources/mapper/*.xml
# 删除现有的entity和mapper Java文件
rm -rf src/main/java/com/pandatech/downloadcf/entity/*
rm -rf src/main/java/com/pandatech/downloadcf/mapper/*
```

#### 1.2 MyBatis Generator配置优化
基于现有的`mybatis-generator-config.xml`，确保生成完整的实体类：
- 启用所有CRUD操作
- 生成Example类用于复杂查询
- 添加序列化支持
- 生成toString方法

#### 1.3 核心数据表
- `panda_esl` - 电子价签设备表
- `panda_product` - 商品信息表  
- `esl_brand_field_mapping` - 品牌字段映射表
- `product_esl_binding` - 商品价签绑定关系表
- `print_template_design` - 打印模板设计表

### 第二阶段：DTO层设计

#### 2.1 请求DTO
```java
// 模板下发请求
public class TemplateDeployRequest {
    private String templateId;
    private String storeCode;
    private String brandCode;
}

// 价签刷新请求
public class EslRefreshRequest {
    private String eslId;
    private String productId;
    private String brandCode;
}

// 批量操作请求
public class BatchOperationRequest<T> {
    private String brandCode;
    private List<T> items;
}
```

#### 2.2 响应DTO
```java
// 统一响应格式
public class ApiResponse<T> {
    private Integer code;
    private String message;
    private T data;
    private Long timestamp;
}

// 分页响应
public class PageResponse<T> extends ApiResponse<List<T>> {
    private Integer pageNum;
    private Integer pageSize;
    private Long total;
}
```

#### 2.3 业务DTO
```java
// 完整的商品信息（包含关联数据）
public class ProductDetailDto {
    private PandaProduct product;
    private List<PandaEsl> eslList;
    private List<PrintTemplateDesign> templates;
    private String storeCode;
}

// 模板转换结果
public class TemplateConversionDto {
    private String originalFormat;
    private String convertedFormat;
    private String brandCode;
    private String screenType;
}
```

### 第三阶段：数据访问工具类

#### 3.1 统一数据访问接口
```java
public interface DataAccessService {
    // 模板相关
    PrintTemplateDesign getTemplateById(String templateId);
    List<PrintTemplateDesign> getTemplatesByBrand(String brandCode);
    
    // 商品相关
    ProductDetailDto getProductDetail(String productId);
    List<PandaProduct> getProductsByStore(String storeCode);
    
    // 价签相关
    List<PandaEsl> getEslsByProduct(String productId);
    List<PandaEsl> getEslsByStore(String storeCode);
    
    // 绑定关系
    List<ProductEslBinding> getBindingsByProduct(String productId);
}
```

#### 3.2 缓存策略
```java
@Service
@CacheConfig(cacheNames = "eslData")
public class CachedDataAccessService implements DataAccessService {
    @Cacheable(key = "#templateId")
    public PrintTemplateDesign getTemplateById(String templateId) { }
    
    @Cacheable(key = "#productId")
    public ProductDetailDto getProductDetail(String productId) { }
}
```

### 第四阶段：中间层架构

#### 4.1 品牌适配器模式
```java
// 品牌适配器接口
public interface BrandAdapter {
    String getBrandCode();
    Object convertToOutputFormat(Object inputData);
    Map<String, String> getFieldMapping();
    boolean supports(String brandCode);
}

// 攀攀品牌适配器
@Component
public class PandaBrandAdapter implements BrandAdapter {
    public String getBrandCode() { return "PANDA"; }
    public Object convertToOutputFormat(Object inputData) { /* 攀攀特定转换逻辑 */ }
}

// 雅量品牌适配器
@Component  
public class YaliangBrandAdapter implements BrandAdapter {
    public String getBrandCode() { return "YALIANG"; }
    public Object convertToOutputFormat(Object inputData) { /* 雅量特定转换逻辑 */ }
}
```

#### 4.2 适配器工厂
```java
@Service
public class BrandAdapterFactory {
    private final Map<String, BrandAdapter> adapters;
    
    public BrandAdapterFactory(List<BrandAdapter> adapterList) {
        this.adapters = adapterList.stream()
            .collect(Collectors.toMap(BrandAdapter::getBrandCode, adapter -> adapter));
    }
    
    public BrandAdapter getAdapter(String brandCode) {
        return adapters.get(brandCode);
    }
}
```

### 第五阶段：输出层设计

#### 5.1 消息生产者接口
```java
public interface MessageProducer {
    void sendToQueue(String queueName, Object message);
    void sendToTopic(String topicName, Object message);
    boolean supports(String brandCode);
}

// RabbitMQ生产者
@Component
public class RabbitMQProducer implements MessageProducer {
    public void sendToQueue(String queueName, Object message) { /* RabbitMQ实现 */ }
}

// MQTT生产者  
@Component
public class MqttProducer implements MessageProducer {
    public void sendToTopic(String topicName, Object message) { /* MQTT实现 */ }
}
```

#### 5.2 队列路由策略
```java
@Service
public class QueueRoutingService {
    // 一个门店一个队列的策略
    public String getQueueName(String storeCode, String brandCode) {
        return String.format("esl.%s.%s", brandCode.toLowerCase(), storeCode);
    }
    
    // 根据品牌获取MQTT主题
    public String getMqttTopic(String brandCode, String storeCode) {
        return String.format("esl/%s/%s", brandCode.toLowerCase(), storeCode);
    }
}
```

### 第六阶段：执行层架构

#### 6.1 执行策略接口
```java
public interface ExecutionStrategy {
    String getBrandCode();
    void execute(Object message, String destination);
    boolean supports(String brandCode);
}

// 攀攀执行策略（MQTT）
@Component
public class PandaExecutionStrategy implements ExecutionStrategy {
    public void execute(Object message, String destination) {
        // MQTT消息发送逻辑
    }
}

// 其他品牌执行策略
@Component
public class YaliangExecutionStrategy implements ExecutionStrategy {
    public void execute(Object message, String destination) {
        // 其他协议的消息发送逻辑
    }
}
```

#### 6.2 执行器工厂
```java
@Service
public class ExecutionStrategyFactory {
    private final Map<String, ExecutionStrategy> strategies;
    
    public ExecutionStrategy getStrategy(String brandCode) {
        return strategies.get(brandCode);
    }
}
```

### 第七阶段：统一服务门面

#### 7.1 核心业务服务
```java
@Service
@Transactional
public class EslBusinessService {
    
    // 模板下发
    public ApiResponse<Void> deployTemplate(TemplateDeployRequest request) {
        // 1. 获取模板数据
        // 2. 品牌适配转换
        // 3. 发送到队列
        // 4. 执行层处理
    }
    
    // 价签刷新
    public ApiResponse<Void> refreshEsl(EslRefreshRequest request) {
        // 1. 获取商品和价签数据
        // 2. 品牌适配转换
        // 3. 发送到队列
        // 4. 执行层处理
    }
    
    // 批量操作
    public ApiResponse<Void> batchOperation(BatchOperationRequest<?> request) {
        // 批量处理逻辑
    }
}
```

### 第八阶段：配置管理

#### 8.1 品牌配置
```yaml
esl:
  brands:
    panda:
      adapter: pandaBrandAdapter
      producer: mqttProducer
      strategy: pandaExecutionStrategy
      queue-prefix: "esl.panda"
      topic-prefix: "esl/panda"
    yaliang:
      adapter: yaliangBrandAdapter  
      producer: rabbitMQProducer
      strategy: yaliangExecutionStrategy
      queue-prefix: "esl.yaliang"
      topic-prefix: "esl/yaliang"
```

#### 8.2 动态配置加载
```java
@ConfigurationProperties(prefix = "esl")
@Data
public class EslConfigProperties {
    private Map<String, BrandConfig> brands;
    
    @Data
    public static class BrandConfig {
        private String adapter;
        private String producer;
        private String strategy;
        private String queuePrefix;
        private String topicPrefix;
    }
}
```

## 项目结构
```
src/main/java/com/pandatech/downloadcf/
├── config/          # 配置类
├── controller/      # REST控制器
├── dto/            # 数据传输对象
│   ├── request/    # 请求DTO
│   ├── response/   # 响应DTO
│   └── business/   # 业务DTO
├── entity/         # MyBatis生成的实体类
├── mapper/         # MyBatis Mapper接口
├── service/        # 业务服务层
│   ├── adapter/    # 品牌适配器
│   ├── producer/   # 消息生产者
│   ├── strategy/   # 执行策略
│   └── facade/     # 服务门面
├── util/           # 工具类
└── exception/      # 异常处理
```

## 扩展性设计要点

1. **品牌扩展**: 新增品牌只需实现对应的Adapter、Producer、Strategy
2. **协议扩展**: 新增消息协议只需实现MessageProducer接口
3. **队列策略**: 可灵活配置队列路由规则
4. **配置驱动**: 通过配置文件控制品牌行为
5. **插件化**: 各组件松耦合，支持热插拔

## 实施建议

1. **分阶段实施**: 按上述8个阶段逐步重构
2. **保持向后兼容**: 重构期间保持现有API可用
3. **充分测试**: 每个阶段完成后进行集成测试
4. **文档同步**: 及时更新API文档和架构文档
5. **性能监控**: 添加监控和日志记录

这样的架构设计确保了系统的高可扩展性、可维护性和企业级的稳定性。