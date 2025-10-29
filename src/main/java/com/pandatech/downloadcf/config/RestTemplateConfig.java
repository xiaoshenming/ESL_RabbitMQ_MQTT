package com.pandatech.downloadcf.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * RestTemplate配置类
 * 使用Spring Boot的RestTemplateBuilder进行配置，优化性能
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    private final TemplateRenderingConfig templateRenderingConfig;

    /**
     * 创建RestTemplate Bean
     * 使用配置文件中的超时参数优化性能
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder
                .setConnectTimeout(Duration.ofMillis(templateRenderingConfig.getApi().getConnectTimeout()))
                .setReadTimeout(Duration.ofMillis(templateRenderingConfig.getApi().getReadTimeout()))
                .build();
        
        log.info("RestTemplate配置完成 - 连接超时: {}ms, 读取超时: {}ms", 
                templateRenderingConfig.getApi().getConnectTimeout(),
                templateRenderingConfig.getApi().getReadTimeout());
        
        return restTemplate;
    }
}