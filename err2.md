# 问题修复报告

## 1. 二维码和条形码无法正确渲染问题

### 问题原因
在 `MqttService.java` 的 `convertPrintElementToItem` 方法中，没有对二维码和条形码类型的元素进行特殊处理，导致转换后的JSON缺少必要的属性。

### 解决方案
修改 `MqttService.java` 的 `convertPrintElementToItem` 方法，增加对二维码和条形码的特殊处理逻辑：
1. 根据 `textType` 字段识别二维码和条形码
2. 为二维码设置 `Type` 属性为 `qrcode`
3. 为条形码设置 `Type` 属性为 `barcode`，并设置条形码特有的属性：`Bartype`、`Barformat`、`Barheight`、`Barwidth`、`Showtext` 和 `Fontinval`

### 修复后的JSON转换示例
```json
// 二维码元素
{
  "Type": "qrcode",
  "Left": 10,
  "Top": 10,
  "Width": 100,
  "Height": 100,
  "Content": "https://example.com"
}

// 条形码元素
{
  "Type": "barcode",
  "Left": 10,
  "Top": 120,
  "Width": 200,
  "Height": 50,
  "Content": "123456789",
  "Bartype": "code128",
  "Barformat": "code128",
  "Barheight": 50,
  "Barwidth": 2,
  "Showtext": true,
  "Fontinval": 2
}
```

## 2. API通过ID查找数据而非name问题

### 问题原因
在 `TemplateController.java` 和 `TemplateServiceImpl.java` 中，`loadTemple` 方法的查找逻辑是优先使用 `name`，而用户需要优先使用 `id`。

### 解决方案
1. 修改 `TemplateController.java` 和 `TemplateServiceImpl.java` 中的 `loadTemple` 方法，使其优先通过 `id` 查找模板，如果 `id` 为空则使用 `name`
2. 保持文件命名方式不变，仍使用 `{模板名称}_{屏幕类型}.json` 的格式

### 修复后的API调用方式
```bash
curl 'http://localhost:8999/api/res/templ/loadtemple' \
  -H 'Content-Type: application/json' \
  -H 'accept: application/octet-stream' \
  --data-raw $'{\n  "id": "1946122678071738370"\n}'
```

或者

```bash
curl 'http://localhost:8999/api/res/templ/loadtemple' \
  -H 'Content-Type: application/json' \
  -H 'accept: application/octet-stream' \
  --data-raw $'{\n  "name": "2_06.json"\n}'
```

无论使用哪种方式，下载的文件名都将保持 `{模板名称}_{屏幕类型}.json` 的格式，例如 `2_06.json`。

## 3. 线条无法显示问题

### 问题原因
在 `MqttService.java` 的 `convertPrintElementToItem` 方法中，没有对线条类型的元素进行特殊处理，导致转换后的JSON缺少必要的属性。

### 解决方案
修改 `MqttService.java` 的 `convertPrintElementToItem` 方法，增加对线条的特殊处理逻辑：
1. 根据 `type` 字段识别线条
2. 为线条设置 `Type` 属性为 `line`
3. 设置线条特有的属性：`LineWidth`、`LineStyle` 等

### 修复后的JSON转换示例
```json
{
  "Type": "line",
  "Left": 10,
  "Top": 10,
  "Width": 100,
  "Height": 1,
  "LineWidth": 1,
  "LineStyle": "solid"
}
```

## 测试命令

```bash
curl 'http://localhost:8999/api/res/templ/loadtemple' \
  -H 'Accept-Language: zh-CN,zh;q=0.9' \
  -H 'Connection: keep-alive' \
  -H 'Content-Type: application/json' \
  -H 'Origin: http://localhost:8999' \
  -H 'Referer: http://localhost:8999/swagger-ui/index.html' \
  -H 'Sec-Fetch-Dest: empty' \
  -H 'Sec-Fetch-Mode: cors' \
  -H 'Sec-Fetch-Site: same-origin' \
  -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36 Edg/139.0.0.0' \
  -H 'accept: application/octet-stream' \
  -H 'sec-ch-ua: "Not;A=Brand";v="99", "Microsoft Edge";v="139", "Chromium";v="139"' \
  -H 'sec-ch-ua-mobile: ?0' \
  -H 'sec-ch-ua-platform: "Windows"' \
  --data-raw $'{\n  "id": "1946122678071738370",\n  "name": "2_06.json"\n}'
```

这个接口现在会优先使用ID查找模板，但下载的文件名仍保持 `{模板名称}_{屏幕类型}.json` 的格式，例如 `2_06.json`。