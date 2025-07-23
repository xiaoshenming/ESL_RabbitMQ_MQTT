package com.pandatech.downloadcf.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置类
 * 定义消息队列和消息转换器
 */
@Configuration
public class RabbitMQConfig {

    public static final String TEMPLATE_QUEUE = "template.queue";
    public static final String REFRESH_QUEUE = "refresh.queue";

    /**
     * 模板下发队列
     */
    @Bean
    public Queue templateQueue() {
        return new Queue(TEMPLATE_QUEUE, true);
    }

    /**
     * 价签刷新队列
     */
    @Bean
    public Queue refreshQueue() {
        return new Queue(REFRESH_QUEUE, true);
    }

    /**
     * JSON消息转换器 - 解决反序列化安全问题
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * RabbitTemplate配置 - 使用JSON转换器
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    /**
     * 监听器容器工厂配置 - 使用JSON转换器
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }
}