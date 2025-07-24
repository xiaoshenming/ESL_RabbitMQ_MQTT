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
| code | int | 响应状态码，200表示成功 |
| msg | string | 响应消息 |
| data | array | 支持的品牌列表 |

#### 响应示例
```json
{
    "code": 200,
    "msg": "获取品牌列表成功",
    "data": [
        "攀攀"
    ]
}
```

#### 错误响应
```json
{
    "code": 500,
    "msg": "获取品牌列表失败",
    "data": null
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
| code | int | 响应状态码，200表示成功 |
| msg | string | 响应消息 |
| data | object | 响应数据 |
| data.eslId | string | 价签ID |
| data.timestamp | long | 处理时间戳 |

#### 响应示例
```json
{
    "code": 200,
    "msg": "价签刷新请求已提交",
    "data": {
        "eslId": "ESL001",
        "timestamp": 1703123456789
    }
}
```

#### 错误响应
```json
{
    "code": 400,
    "msg": "价签不存在或未绑定商品",
    "data": {
        "eslId": "ESL001"
    }
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
| code | int | 响应状态码，200表示成功 |
| msg | string | 响应消息 |
| data | object | 响应数据 |
| data.totalCount | int | 总请求数量 |
| data.successCount | int | 成功处理数量 |
| data.failedCount | int | 失败处理数量 |
| data.results | array | 详细处理结果 |
| data.results[].eslId | string | 价签ID |
| data.results[].success | boolean | 是否成功 |
| data.results[].message | string | 处理消息 |

#### 响应示例
```json
{
    "code": 200,
    "msg": "批量刷新处理完成",
    "data": {
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
}
```

#### 错误响应
```json
{
    "code": 207,
    "msg": "批量刷新处理完成，部分失败",
    "data": {
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
| code | int | 响应状态码，200表示成功 |
| msg | string | 响应消息 |
| data | object | 响应数据 |
| data.productId | string | 商品ID |
| data.successCount | int | 成功刷新的价签数量 |
| data.timestamp | long | 处理时间戳 |

#### 响应示例
```json
{
    "code": 200,
    "msg": "商品价签刷新请求已提交",
    "data": {
        "productId": "PRODUCT001",
        "successCount": 3,
        "timestamp": 1703123456789
    }
}
```

#### 错误响应
```json
{
    "code": 404,
    "msg": "商品不存在或未绑定价签",
    "data": {
        "productId": "PRODUCT001",
        "successCount": 0
    }
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
| code | int | 响应状态码，200表示成功 |
| msg | string | 响应消息 |
| data | object | 响应数据 |
| data.storeCode | string | 门店编码 |
| data.successCount | int | 成功刷新的价签数量 |
| data.timestamp | long | 处理时间戳 |

#### 响应示例
```json
{
    "code": 200,
    "msg": "门店价签刷新请求已提交",
    "data": {
        "storeCode": "STORE001",
        "successCount": 15,
        "timestamp": 1703123456789
    }
}
```

#### 错误响应
```json
{
    "code": 404,
    "msg": "门店不存在或没有价签",
    "data": {
        "storeCode": "STORE001",
        "successCount": 0
    }
}
```

---

## 通用错误码

| 错误码 | 描述 | 解决方案 |
|--------|------|----------|
| 200 | 请求成功 | 正常响应 |
| 207 | 部分成功 | 批量操作中部分成功，检查详细结果 |
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
        
        if (response.data.code === 200) {
            console.log('价签刷新成功:', response.data.msg);
            console.log('价签ID:', response.data.data.eslId);
        } else {
            console.error('价签刷新失败:', response.data.msg);
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
        
        if (response.data.code === 200) {
            console.log('批量刷新成功:', response.data.msg);
            console.log('成功数量:', response.data.data.successCount);
            console.log('失败数量:', response.data.data.failedCount);
        } else {
            console.log('批量刷新部分失败:', response.data.msg);
        }
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
        
        if (response.data.code === 200) {
            console.log('商品价签刷新成功:', response.data.msg);
            console.log('刷新数量:', response.data.data.successCount);
        } else {
            console.error('商品价签刷新失败:', response.data.msg);
        }
        return response.data;
    } catch (error) {
        console.error('商品价签刷新失败:', error.message);
        throw error;
    }
}

// 获取支持的品牌
async function getSupportedBrands() {
    try {
        const response = await axios.get('/api/esl/brands');
        
        if (response.data.code === 200) {
            console.log('获取品牌列表成功:', response.data.data);
            return response.data.data;
        } else {
            console.error('获取品牌列表失败:', response.data.msg);
            return [];
        }
    } catch (error) {
        console.error('获取品牌列表失败:', error.message);
        return [];
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
        
        ResponseEntity<Map> response = restTemplate.postForEntity(
            ESL_API_BASE + "/refresh", 
            request, 
            Map.class
        );
        
        // 检查响应
        Map<String, Object> body = response.getBody();
        if (body != null && (Integer) body.get("code") == 200) {
            System.out.println("价签刷新成功: " + body.get("msg"));
            Map<String, Object> data = (Map<String, Object>) body.get("data");
            System.out.println("价签ID: " + data.get("eslId"));
        } else {
            System.err.println("价签刷新失败: " + body.get("msg"));
        }
        
        return response;
    }
    
    // 获取支持的品牌
    public List<String> getSupportedBrands() {
        ResponseEntity<Map> response = restTemplate.getForEntity(
            ESL_API_BASE + "/brands", 
            Map.class
        );
        
        Map<String, Object> body = response.getBody();
        if (body != null && (Integer) body.get("code") == 200) {
            return (List<String>) body.get("data");
        } else {
            System.err.println("获取品牌列表失败: " + body.get("msg"));
            return new ArrayList<>();
        }
    }
    
    // 批量刷新价签
    public Map<String, Object> batchRefreshEsl(List<Map<String, Object>> requests) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("requests", requests);
        
        ResponseEntity<Map> response = restTemplate.postForEntity(
            ESL_API_BASE + "/refresh/batch", 
            requestBody, 
            Map.class
        );
        
        Map<String, Object> body = response.getBody();
        if (body != null) {
            Integer code = (Integer) body.get("code");
            if (code == 200) {
                System.out.println("批量刷新成功: " + body.get("msg"));
            } else if (code == 207) {
                System.out.println("批量刷新部分成功: " + body.get("msg"));
            } else {
                System.err.println("批量刷新失败: " + body.get("msg"));
            }
            
            Map<String, Object> data = (Map<String, Object>) body.get("data");
            System.out.println("成功数量: " + data.get("successCount"));
            System.out.println("失败数量: " + data.get("failedCount"));
        }
        
        return body;
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


curl 'http://localhost:8999/api/esl/brands' \
  -H 'Accept-Language: zh-CN,zh;q=0.9' \
  -H 'Connection: keep-alive' \
  -b 'JSESSIONID=406CCC878CB84ADC8026DEE2D90EF3FA' \
  -H 'Referer: http://localhost:8999/swagger-ui/index.html' \
  -H 'Sec-Fetch-Dest: empty' \
  -H 'Sec-Fetch-Mode: cors' \
  -H 'Sec-Fetch-Site: same-origin' \
  -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36 Edg/139.0.0.0' \
  -H 'accept: */*' \
  -H 'sec-ch-ua: "Not;A=Brand";v="99", "Microsoft Edge";v="139", "Chromium";v="139"' \
  -H 'sec-ch-ua-mobile: ?0' \
  -H 'sec-ch-ua-platform: "Windows"'
  {
    "code": 200,
    "msg": "获取支持品牌成功",
    "data": [
        {
            "brandName": "攀攀",
            "enabled": true,
            "brandCode": "攀攀"
        }
    ]
}
curl 'http://localhost:8999/api/esl/refresh' \
  -H 'Accept-Language: zh-CN,zh;q=0.9' \
  -H 'Connection: keep-alive' \
  -H 'Content-Type: application/json' \
  -b 'JSESSIONID=406CCC878CB84ADC8026DEE2D90EF3FA' \
  -H 'Origin: http://localhost:8999' \
  -H 'Referer: http://localhost:8999/swagger-ui/index.html' \
  -H 'Sec-Fetch-Dest: empty' \
  -H 'Sec-Fetch-Mode: cors' \
  -H 'Sec-Fetch-Site: same-origin' \
  -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36 Edg/139.0.0.0' \
  -H 'accept: */*' \
  -H 'sec-ch-ua: "Not;A=Brand";v="99", "Microsoft Edge";v="139", "Chromium";v="139"' \
  -H 'sec-ch-ua-mobile: ?0' \
  -H 'sec-ch-ua-platform: "Windows"' \
  --data-raw $'{\n  "eslId": "1947313501023105026",\n  "brandCode": "PANDA",\n  "forceRefresh": true,\n  "storeCode": "0002"\n}'
  {
    "code": 200,
    "msg": "价签刷新请求已提交",
    "data": {
        "eslId": "1947313501023105026",
        "timestamp": 1753334448853
    }
}
{
    "code": 200,
    "msg": "批量刷新处理完成",
    "data": {
        "failedCount": 0,
        "successCount": 2,
        "totalCount": 2
    }
}
curl 'http://localhost:8999/api/esl/refresh/product' \
  -H 'Accept-Language: zh-CN,zh;q=0.9' \
  -H 'Connection: keep-alive' \
  -H 'Content-Type: application/json' \
  -b 'JSESSIONID=406CCC878CB84ADC8026DEE2D90EF3FA' \
  -H 'Origin: http://localhost:8999' \
  -H 'Referer: http://localhost:8999/swagger-ui/index.html' \
  -H 'Sec-Fetch-Dest: empty' \
  -H 'Sec-Fetch-Mode: cors' \
  -H 'Sec-Fetch-Site: same-origin' \
  -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36 Edg/139.0.0.0' \
  -H 'accept: */*' \
  -H 'sec-ch-ua: "Not;A=Brand";v="99", "Microsoft Edge";v="139", "Chromium";v="139"' \
  -H 'sec-ch-ua-mobile: ?0' \
  -H 'sec-ch-ua-platform: "Windows"' \
  --data-raw $'{\n  "productCode": "1947222838805917697",\n  "brandCode": "攀攀",\n  "forceRefresh": true\n}'
  {
    "code": 200,
    "msg": "商品价签刷新处理完成",
    "data": {
        "productCode": "1947222838805917697",
        "refreshCount": 1,
        "brandCode": "攀攀",
        "timestamp": 1753334710614
    }
}

curl 'http://localhost:8999/api/esl/refresh/store' \
  -H 'Accept-Language: zh-CN,zh;q=0.9' \
  -H 'Connection: keep-alive' \
  -H 'Content-Type: application/json' \
  -b 'JSESSIONID=406CCC878CB84ADC8026DEE2D90EF3FA' \
  -H 'Origin: http://localhost:8999' \
  -H 'Referer: http://localhost:8999/swagger-ui/index.html' \
  -H 'Sec-Fetch-Dest: empty' \
  -H 'Sec-Fetch-Mode: cors' \
  -H 'Sec-Fetch-Site: same-origin' \
  -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36 Edg/139.0.0.0' \
  -H 'accept: */*' \
  -H 'sec-ch-ua: "Not;A=Brand";v="99", "Microsoft Edge";v="139", "Chromium";v="139"' \
  -H 'sec-ch-ua-mobile: ?0' \
  -H 'sec-ch-ua-platform: "Windows"' \
  --data-raw $'{\n  "storeCode": "0002",\n  "brandCode": "攀攀",\n  "forceRefresh": true\n}'

  {
    "code": 200,
    "msg": "门店价签刷新处理完成",
    "data": {
        "refreshCount": 2,
        "storeCode": "0002",
        "brandCode": "攀攀",
        "timestamp": 1753334754224
    }
}