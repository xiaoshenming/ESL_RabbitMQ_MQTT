package com.pandatech.downloadcf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.integration.config.EnableIntegration;

@EnableIntegration
@SpringBootApplication
@MapperScan("com.pandatech.downloadcf.mapper")
public class DownloadcfApplication {

    public static void main(String[] args) {
        SpringApplication.run(DownloadcfApplication.class, args);
    }

}
