package com.bite.forum.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 配置类
@Configuration
public class SwaggerConfig {
    /**
     * SpringDoc OpenAPI基本配置
     * @return
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bite论坛系统API")
                        .description("Bite论坛系统前后端分离API测试")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Bit Tech")
                                .url("https://hlxpy.hljs.qzz.io")
                                .email("hlxpy@qq.com")));
    }
}
