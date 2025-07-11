package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.config.RabbitMQConfig;
import com.pandatech.downloadcf.dto.RefreshDto;
import com.pandatech.downloadcf.dto.TemplateDto;
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
}