package com.pandatech.downloadcf.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.adapter.BrandAdapter;
import com.pandatech.downloadcf.executor.MessageExecutor;
import com.pandatech.downloadcf.executor.MqttExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageChannel;

import java.util.Arrays;
import java.util.List;

/**
 * 价签系统配置类
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class EslConfig {
    
    private final ObjectMapper objectMapper;
    private final MessageChannel mqttOutboundChannel;
    
    /**
     * 配置品牌适配器列表
     * 注意：品牌适配器现在通过 BrandAdapterConfig 进行配置
     * 这个方法保留用于向后兼容，但实际的适配器由各自的配置类管理
     */
    @Bean
    public List<BrandAdapter> brandAdapters(List<BrandAdapter> configuredAdapters) {
        log.info("初始化品牌适配器列表，共找到 {} 个适配器", configuredAdapters.size());
        return configuredAdapters;
    }
    
    /**
     * 配置消息执行器列表
     */
    @Bean
    public List<MessageExecutor> messageExecutors() {
        log.info("初始化消息执行器列表");
        
        return Arrays.asList(
            new MqttExecutor(mqttOutboundChannel, objectMapper)
            // 可以在这里添加更多消息执行器
            // new HttpExecutor(),
            // new WebSocketExecutor()
        );
    }
}