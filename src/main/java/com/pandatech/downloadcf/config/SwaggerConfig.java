package com.pandatech.downloadcf.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger配置类
 * 配置API文档
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("价签模板下发系统 API 文档")
                        .version("1.0.0")
                        .description("### **🏷️ 价签模板下发系统接口文档**\n\n" +
                                "本系统提供完整的价签模板下发和刷新功能，支持通过RabbitMQ消息队列和MQTT协议进行设备通信。\n\n" +
                                "#### **📋 主要功能模块:**\n\n" +
                                "- **🔄 模板下发接口:** 用于将指定的模板内容下发到对应的价签设备\n" +
                                "- **🔃 价签刷新接口:** 用于触发价签刷新，显示最新的商品信息\n" +
                                "- **🔧 MQTT模拟接口:** 用于开发测试环境模拟MQTT消息推送\n\n" +
                                "#### **⚡ 系统架构:**\n\n" +
                                "```\n" +
                                "客户端请求 → REST API → RabbitMQ队列 → 后端处理 → MQTT推送 → 价签设备\n" +
                                "```\n\n" +
                                "#### **📝 使用说明:**\n\n" +
                                "1. **Content-Type:** 所有POST请求请确保设置为 `application/json`\n" +
                                "2. **参数格式:** 请参考各接口的示例参数格式\n" +
                                "3. **错误处理:** 接口返回标准的HTTP状态码和错误信息\n" +
                                "4. **测试环境:** 可直接在此页面进行接口测试\n\n" +
                                "#### **🔗 相关链接:**\n\n" +
                                "- **项目地址:** [GitHub Repository](https://github.com/pandatech/esl-template-system)\n" +
                                "- **技术支持:** dev@pandatech.com\n" +
                                "- **更新日志:** [CHANGELOG.md](https://github.com/pandatech/esl-template-system/blob/main/CHANGELOG.md)")
                        .contact(new Contact()
                                .name("熊猫科技开发团队")
                                .email("dev@pandatech.com")
                                .url("https://www.pandatech.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8999").description("本地开发环境"),
                        new Server().url("http://10.3.36.25:8999").description("测试环境"),
                        new Server().url("https://api.pandatech.com").description("生产环境")
                ));
    }
}