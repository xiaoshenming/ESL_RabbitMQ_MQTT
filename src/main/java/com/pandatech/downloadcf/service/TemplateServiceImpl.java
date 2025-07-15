package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.config.RabbitMQConfig;
import com.pandatech.downloadcf.dto.LoadTemplateRequest;
import com.pandatech.downloadcf.dto.RefreshDto;
import com.pandatech.downloadcf.dto.TemplateDto;
import com.pandatech.downloadcf.mapper.ActExtTemplatePrintMapper;
import com.pandatech.downloadcf.entity.ActExtTemplatePrintWithBLOBs;
import com.pandatech.downloadcf.service.MqttService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final ActExtTemplatePrintMapper templateMapper;
    private final MqttService mqttService;

    @Override
    public void sendTemplate(TemplateDto templateDto) {
        try {
            String message = objectMapper.writeValueAsString(templateDto);
            rabbitTemplate.convertAndSend(RabbitMQConfig.TEMPLATE_QUEUE, message);
            log.info("发送模板消息到队列: {}", message);
        } catch (Exception e) {
            log.error("发送模板消息失败", e);
        }
    }

    @Override
    public void refreshEsl(RefreshDto refreshDto) {
        try {
            String message = objectMapper.writeValueAsString(refreshDto);
            rabbitTemplate.convertAndSend(RabbitMQConfig.REFRESH_QUEUE, message);
            log.info("发送刷新消息到队列: {}", message);
        } catch (Exception e) {
            log.error("发送刷新消息失败", e);
        }
    }

    @Override
    public byte[] loadTemple(LoadTemplateRequest request) {
        ActExtTemplatePrintWithBLOBs template;
        if (request.getName() != null && !request.getName().isEmpty()) {
            template = templateMapper.findByName(request.getName());
        } else {
            template = templateMapper.findById(request.getId());
        }

        if (template == null || template.getContent() == null) {
            return null;
        }

        try {
            // 将数据库中的模板内容（JSON字符串）转换为官方模板格式
            String officialTemplateJson = mqttService.convertToOfficialTemplate(template.getContent());
            return officialTemplateJson.getBytes();
        } catch (JsonProcessingException e) {
            log.error("Error converting template to official format for ID: {} or Name: {}", request.getId(), request.getName(), e);
            return null;
        }
    }
}