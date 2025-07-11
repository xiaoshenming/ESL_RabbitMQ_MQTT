package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.entity.ActExtTemplatePrint;
import com.pandatech.downloadcf.entity.PandaEsl;
import com.pandatech.downloadcf.entity.PandaProduct;
import com.pandatech.downloadcf.mapper.ActExtTemplatePrintMapper;
import com.pandatech.downloadcf.mapper.PandaEslMapper;
import com.pandatech.downloadcf.mapper.PandaProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MqttService {

    private final ActExtTemplatePrintMapper templatePrintMapper;
    private final PandaEslMapper eslMapper;
    private final PandaProductMapper productMapper;
    private final ObjectMapper objectMapper;

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(@Header(MqttHeaders.RECEIVED_TOPIC) String topic, String payload) {
        log.info("接收到MQTT消息 - 主题: {}, 内容: {}", topic, payload);
        // 假设 /templ/loadtemple 是一个特定的主题或包含在payload中用于区分
        if (topic.contains("loadtemple")) {
            try {
                // 这里模拟根据payload（可能是价签ID）加载模板和产品数据
                // 实际场景中，payload的格式需要根据业务确定
                Map<String, Object> request = objectMapper.readValue(payload, HashMap.class);
                String eslId = (String) request.get("eslId");

                PandaEsl esl = eslMapper.selectByPrimaryKey(eslId);
                if (esl == null) {
                    log.error("未找到价签: {}", eslId);
                    return;
                }

                PandaProduct product = productMapper.selectByPrimaryKey(esl.getBoundProductId());
                if (product == null) {
                    log.error("未找到产品: {}", esl.getBoundProductId());
                    return;
                }

                ActExtTemplatePrint template = templatePrintMapper.selectByPrimaryKey(product.getEslTemplateId());
                if (template == null) {
                    log.error("未找到模板: {}", product.getEslTemplateId());
                    return;
                }

                // 将模板和产品数据组合成最终的二进制流
                byte[] binaryData = convertToBinary(template, product);

                // 此处应有将二进制流推送到MQTT的逻辑，但由于我们是从MQTT接收，
                // 所以这里只打印日志模拟。实际的推送逻辑在发送方。
                log.info("成功为价签 {} 生成模板数据，数据长度: {}", eslId, binaryData.length);

            } catch (Exception e) {
                log.error("处理MQTT消息失败", e);
            }
        }
    }

    private byte[] convertToBinary(ActExtTemplatePrint template, PandaProduct product) {
        // 在这里实现将模板和产品信息转换为U_06.json格式的二进制流的逻辑
        // 这是一个复杂的转换过程，这里只返回一个示例字节数组
        String jsonData = String.format("{\"templateId\":\"%s\", \"productName\":\"%s\", \"price\":%s}",
                template.getId(), product.getProductName(), product.getProductSalePrice());
        return jsonData.getBytes();
    }
}