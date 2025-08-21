# 品牌适配器模块化架构

## 设计理念

本模块采用"统一获取、统一处理、统一发送"的三段式架构：

1. **统一获取**: `DataService` 负责从数据库获取完整的价签数据
2. **统一处理**: 各品牌适配器负责将通用数据转换为品牌特定格式
3. **统一发送**: `MessageProducerService` 负责将处理后的数据发送到消息队列

## 模块化结构

每个品牌都有独立的文件夹，包含：

- `adapter/`: 品牌适配器实现
- `config/`: 品牌特定配置
- `util/`: 品牌专用工具类
- `dto/`: 品牌特定数据传输对象
- `validator/`: 品牌数据验证器

## 品牌模块

### AES (攀攀科技)
- 品牌编码: AES001
- 支持MQTT协议
- 字段格式: 统一字符串格式

### YALIANG (雅量科技)
- 品牌编码: YALIANG001
- 支持MQTT协议
- 字段格式: 混合数字和字符串格式

## 扩展新品牌

1. 在 `brands/` 下创建新的品牌文件夹
2. 实现 `BaseBrandAdapter` 抽象类
3. 添加品牌特定的配置和工具类
4. 在 `application.yml` 中添加品牌配置
5. 更新 `BrandCodeUtil` 支持新品牌

## 核心接口

- `BrandAdapter`: 品牌适配器接口
- `BaseBrandAdapter`: 通用适配器抽象类
- `BrandConfig`: 品牌配置接口
- `BrandValidator`: 品牌验证器接口