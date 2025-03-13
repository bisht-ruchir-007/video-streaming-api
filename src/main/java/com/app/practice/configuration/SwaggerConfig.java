package com.app.practice.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

/*
    Code Author : Ruchir Bisht

    This class configures the Swagger API documentation for the Video-Streaming backend.
    It uses the OpenAPI 3 specification to document the available RESTful APIs and includes
    security information for the JWT authentication schema.

    - The @OpenAPIDefinition annotation defines the metadata for the API documentation,
      including title, version, and description.
    - The @SecurityScheme annotation defines the security scheme to be used, specifying that
      JWT tokens will be used for authorization, and the token will be passed in the HTTP header.
 */

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Video-Streaming: BACKEND API'S",
                version = "1.0",
                description = "API Documentation for Video Engagement & Management System, a robust Spring Boot" +
                        " application designed to handle video content, metadata management, and user engagement " +
                        "analytics for a video streaming platform."
        )
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "Bearer",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("default")
                .packagesToScan("com.app.practice.controller")
                .build();
    }
}
