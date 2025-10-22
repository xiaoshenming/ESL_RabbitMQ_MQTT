package com.pandatech.downloadcf.brands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.adapter.BrandAdapter;
import com.pandatech.downloadcf.brands.aes.adapter.AesBrandAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 品牌适配器配置类
 * 负责注册所有品牌适配器到Spring容器
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class BrandAdapterConfig {
    
    private final ObjectMapper objectMapper;
    
    /**
     * 注册AES品牌适配器
     */
    @Bean
    public BrandAdapter aesBrandAdapter() {
        log.info("注册AES品牌适配器");
        return new AesBrandAdapter(objectMapper);
    }
    
    // 注意：YaliangBrandAdapter现在使用@Component注解自动注册
    // 不再需要手动@Bean配置，Spring会自动扫描并注册
    
    // 未来新增品牌适配器时，在这里添加对应的@Bean方法
    // 例如：
    // @Bean
    // public BrandAdapter newBrandAdapter() {
    //     log.info("注册新品牌适配器");
    //     return new NewBrandAdapter();
    // }
}