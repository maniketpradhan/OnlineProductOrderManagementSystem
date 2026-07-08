package com.maniket.oms.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI customOpenAPI() {

        return new OpenAPI()

                .info(new Info()

                        .title("Online Product Order Management System API")

                        .version("1.0")

                        .description("Spring Boot REST APIs"))

                .addSecurityItem(new SecurityRequirement()

                        .addList("Bearer Authentication"))

                .schemaRequirement(

                        "Bearer Authentication",

                        new SecurityScheme()

                                .name("Bearer Authentication")

                                .type(SecurityScheme.Type.HTTP)

                                .scheme("bearer")

                                .bearerFormat("JWT"));

    }

}