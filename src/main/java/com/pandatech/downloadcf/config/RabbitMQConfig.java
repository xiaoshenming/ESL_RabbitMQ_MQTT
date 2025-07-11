package com.pandatech.downloadcf.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String TEMPLATE_QUEUE = "template.queue";
    public static final String REFRESH_QUEUE = "refresh.queue";

    @Bean
    public Queue templateQueue() {
        return new Queue(TEMPLATE_QUEUE, true);
    }

    @Bean
    public Queue refreshQueue() {
        return new Queue(REFRESH_QUEUE, true);
    }
}