# 价签管理API接口文档

## 接口概述

本文档描述了价签管理系统的REST API接口，用于前端应用与后端服务的对接。所有接口均采用RESTful设计风格，支持JSON格式的数据交换。

## 基础信息

- **Base URL**: `http://localhost:8999`
- **Content-Type**: `application/json`
- **字符编码**: `UTF-8`

## 接口列表

### 1. 获取支持的品牌

获取系统支持的所有价签品牌列表。

#### 接口信息
- **URL**: `/api/esl/brands`
- **Method**: `GET`
- **描述**: 获取系统支持的所有品牌列表

#### 请求参数
无

#### 请求示例
```http
GET /api/esl/brands HTTP/1.1
Host: localhost:8999
Content-Type: application/json
```

#### 响应参数
| 字段名 | 类型 | 描述 |
|--------|------|------|
| success | boolean | 请求是否成功 |
| message | string | 响应消息 |
| brands | array | 支持的品牌列表 |

#### 响应示例
```json
{
    "success": true,
    "message": "获取品牌列表成功",
    "brands": [
        "攀攀"
    ]
}
```

#### 错误响应
```json
{
    "success": false,
    "message": "获取品牌列表失败",
    "error": "系统内部错误"
}
```

---

### 2. 刷新单个价签

根据价签ID刷新指定的价签显示内容。

#### 接口信息
- **URL**: `/api/esl/refresh`
- **Method**: `POST`
- **描述**: 刷新单个价签的显示内容

#### 请求参数
| 字段名 | 类型 | 必填 | 描述 | 示例 |
|--------|------|------|------|------|
| eslId | string | 是 | 价签ID | "ESL001" |
| brandCode | string | 否 | 品牌编码，默认为"攀攀" | "攀攀" |
| forceRefresh | boolean | 否 | 是否强制刷新，默认为true | true |

#### 请求示例
```http
POST /api/esl/refresh HTTP/1.1
Host: localhost:8999
Content-Type: application/json

{
    "eslId": "ESL001",
    "brandCode": "攀攀",
    "forceRefresh": true
}
```

#### 响应参数
| 字段名 | 类型 | 描述 |
|--------|------|------|
| success | boolean | 请求是否成功 |
| message | string | 响应消息 |
| eslId | string | 价签ID |
| timestamp | long | 处理时间戳 |

#### 响应示例
```json
{
    "success": true,
    "message": "价签刷新请求已提交",
    "eslId": "ESL001",
    "timestamp": 1703123456789
}
```

#### 错误响应
```json
{
    "success": false,
    "message": "价签刷新失败",
    "error": "价签不存在或未绑定商品",
    "eslId": "ESL001"
}
```

---

### 3. 批量刷新价签

批量刷新多个价签的显示内容。

#### 接口信息
- **URL**: `/api/esl/refresh/batch`
- **Method**: `POST`
- **描述**: 批量刷新多个价签的显示内容

#### 请求参数
| 字段名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| requests | array | 是 | 价签刷新请求列表 |
| requests[].eslId | string | 是 | 价签ID |
| requests[].brandCode | string | 否 | 品牌编码 |
| requests[].forceRefresh | boolean | 否 | 是否强制刷新 |

#### 请求示例
```http
POST /api/esl/refresh/batch HTTP/1.1
Host: localhost:8999
Content-Type: application/json

{
    "requests": [
        {
            "eslId": "ESL001",
            "brandCode": "攀攀",
            "forceRefresh": true
        },
        {
            "eslId": "ESL002",
            "brandCode": "攀攀",
            "forceRefresh": true
        }
    ]
}
```

#### 响应参数
| 字段名 | 类型 | 描述 |
|--------|------|------|
| success | boolean | 请求是否成功 |
| message | string | 响应消息 |
| totalCount | int | 总请求数量 |
| successCount | int | 成功处理数量 |
| failedCount | int | 失败处理数量 |
| results | array | 详细处理结果 |
| results[].eslId | string | 价签ID |
| results[].success | boolean | 是否成功 |
| results[].message | string | 处理消息 |

#### 响应示例
```json
{
    "success": true,
    "message": "批量刷新处理完成",
    "totalCount": 2,
    "successCount": 2,
    "failedCount": 0,
    "results": [
        {
            "eslId": "ESL001",
            "success": true,
            "message": "刷新成功"
        },
        {
            "eslId": "ESL002",
            "success": true,
            "message": "刷新成功"
        }
    ]
}
```

#### 错误响应
```json
{
    "success": false,
    "message": "批量刷新处理完成，部分失败",
    "totalCount": 2,
    "successCount": 1,
    "failedCount": 1,
    "results": [
        {
            "eslId": "ESL001",
            "success": true,
            "message": "刷新成功"
        },
        {
            "eslId": "ESL002",
            "success": false,
            "message": "价签不存在"
        }
    ]
}
```

---

### 4. 按商品刷新价签

刷新指定商品绑定的所有价签。

#### 接口信息
- **URL**: `/api/esl/refresh/product/{productId}`
- **Method**: `POST`
- **描述**: 刷新指定商品绑定的所有价签

#### 路径参数
| 参数名 | 类型 | 必填 | 描述 | 示例 |
|--------|------|------|------|------|
| productId | string | 是 | 商品ID | "PRODUCT001" |

#### 查询参数
| 参数名 | 类型 | 必填 | 描述 | 默认值 |
|--------|------|------|------|-------|
| brandCode | string | 否 | 品牌编码 | "攀攀" |

#### 请求示例
```http
POST /api/esl/refresh/product/PRODUCT001?brandCode=攀攀 HTTP/1.1
Host: localhost:8999
Content-Type: application/json
```

#### 响应参数
| 字段名 | 类型 | 描述 |
|--------|------|------|
| success | boolean | 请求是否成功 |
| message | string | 响应消息 |
| productId | string | 商品ID |
| successCount | int | 成功刷新的价签数量 |
| timestamp | long | 处理时间戳 |

#### 响应示例
```json
{
    "success": true,
    "message": "商品价签刷新请求已提交",
    "productId": "PRODUCT001",
    "successCount": 3,
    "timestamp": 1703123456789
}
```

#### 错误响应
```json
{
    "success": false,
    "message": "商品价签刷新失败",
    "error": "商品不存在或未绑定价签",
    "productId": "PRODUCT001",
    "successCount": 0
}
```

---

### 5. 按门店刷新价签

刷新指定门店的所有价签。

#### 接口信息
- **URL**: `/api/esl/refresh/store/{storeCode}`
- **Method**: `POST`
- **描述**: 刷新指定门店的所有价签

#### 路径参数
| 参数名 | 类型 | 必填 | 描述 | 示例 |
|--------|------|------|------|------|
| storeCode | string | 是 | 门店编码 | "STORE001" |

#### 查询参数
| 参数名 | 类型 | 必填 | 描述 | 默认值 |
|--------|------|------|------|-------|
| brandCode | string | 否 | 品牌编码 | "攀攀" |

#### 请求示例
```http
POST /api/esl/refresh/store/STORE001?brandCode=攀攀 HTTP/1.1
Host: localhost:8999
Content-Type: application/json
```

#### 响应参数
| 字段名 | 类型 | 描述 |
|--------|------|------|
| success | boolean | 请求是否成功 |
| message | string | 响应消息 |
| storeCode | string | 门店编码 |
| successCount | int | 成功刷新的价签数量 |
| timestamp | long | 处理时间戳 |

#### 响应示例
```json
{
    "success": true,
    "message": "门店价签刷新请求已提交",
    "storeCode": "STORE001",
    "successCount": 15,
    "timestamp": 1703123456789
}
```

#### 错误响应
```json
{
    "success": false,
    "message": "门店价签刷新失败",
    "error": "门店不存在或没有价签",
    "storeCode": "STORE001",
    "successCount": 0
}
```

---

## 通用错误码

| 错误码 | 描述 | 解决方案 |
|--------|------|----------|
| 400 | 请求参数错误 | 检查请求参数格式和必填字段 |
| 404 | 资源不存在 | 确认价签ID、商品ID或门店编码是否正确 |
| 500 | 服务器内部错误 | 联系技术支持或查看服务器日志 |
| 503 | 服务不可用 | 检查RabbitMQ和MQTT服务状态 |

## 业务流程说明

### 价签刷新流程
1. **数据验证**: 验证请求参数的有效性
2. **数据提取**: 从数据库获取价签、商品、模板等完整数据
3. **品牌适配**: 根据品牌类型进行数据格式转换
4. **消息队列**: 将处理请求发送到RabbitMQ队列
5. **异步处理**: 系统在负载允许时异步处理消息
6. **MQTT发送**: 通过MQTT协议发送到价签设备

### 负载控制机制
- 系统会自动监控当前负载，避免过载
- 最大并发处理数量：10个消息
- 处理间隔：最小100ms
- 队列积压告警：刷新队列>1000条消息

## 最佳实践

### 1. 批量操作建议
- 单次批量刷新建议不超过100个价签
- 大量价签刷新建议分批次进行
- 避免在业务高峰期进行大批量操作

### 2. 错误处理
- 建议实现重试机制，最大重试3次
- 对于失败的请求，可以查看详细错误信息
- 监控接口响应时间和成功率

### 3. 性能优化
- 使用按门店或按商品的批量接口，避免单个循环调用
- 合理设置请求超时时间（建议30秒）
- 实现客户端缓存机制，减少重复请求

## 示例代码

### JavaScript/Axios示例
```javascript
// 刷新单个价签
async function refreshSingleEsl(eslId, brandCode = '攀攀') {
    try {
        const response = await axios.post('/api/esl/refresh', {
            eslId: eslId,
            brandCode: brandCode,
            forceRefresh: true
        });
        
        if (response.data.success) {
            console.log('价签刷新成功:', response.data.message);
        } else {
            console.error('价签刷新失败:', response.data.message);
        }
    } catch (error) {
        console.error('请求失败:', error.message);
    }
}

// 批量刷新价签
async function batchRefreshEsl(eslIds, brandCode = '攀攀') {
    const requests = eslIds.map(eslId => ({
        eslId: eslId,
        brandCode: brandCode,
        forceRefresh: true
    }));
    
    try {
        const response = await axios.post('/api/esl/refresh/batch', {
            requests: requests
        });
        
        console.log('批量刷新结果:', response.data);
        return response.data;
    } catch (error) {
        console.error('批量刷新失败:', error.message);
        throw error;
    }
}

// 按商品刷新价签
async function refreshEslByProduct(productId, brandCode = '攀攀') {
    try {
        const response = await axios.post(
            `/api/esl/refresh/product/${productId}?brandCode=${brandCode}`
        );
        
        console.log('商品价签刷新结果:', response.data);
        return response.data;
    } catch (error) {
        console.error('商品价签刷新失败:', error.message);
        throw error;
    }
}
```

### Java/Spring示例
```java
@RestController
public class EslClientController {
    
    @Autowired
    private RestTemplate restTemplate;
    
    private static final String ESL_API_BASE = "http://localhost:8999/api/esl";
    
    // 刷新单个价签
    public ResponseEntity<Map> refreshSingleEsl(String eslId, String brandCode) {
        Map<String, Object> request = new HashMap<>();
        request.put("eslId", eslId);
        request.put("brandCode", brandCode);
        request.put("forceRefresh", true);
        
        return restTemplate.postForEntity(
            ESL_API_BASE + "/refresh", 
            request, 
            Map.class
        );
    }
    
    // 获取支持的品牌
    public List<String> getSupportedBrands() {
        ResponseEntity<Map> response = restTemplate.getForEntity(
            ESL_API_BASE + "/brands", 
            Map.class
        );
        
        Map<String, Object> body = response.getBody();
        return (List<String>) body.get("brands");
    }
}
```

## 联系信息

如有技术问题或需要支持，请联系：
- **开发团队**: PandaTech
- **技术支持**: 请查看系统日志或联系管理员
- **API文档**: http://localhost:8999/swagger-ui.html

---

*本文档版本：v1.0.0，最后更新：2024年*