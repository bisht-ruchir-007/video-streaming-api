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
                title = "Video-Streaming: API'S",  // Title of the API documentation
                version = "1.0",  // Version of the API
                description = "API Documentation for Video-Streaming-backend."  // Brief description of the API
        )
)
@SecurityScheme(
        name = "bearerAuth",  // The name of the security scheme
        type = SecuritySchemeType.HTTP,  // The security scheme type (HTTP)
        bearerFormat = "JWT",  // The format of the bearer token (JWT)
        scheme = "Bearer",  // Scheme used in the Authorization header
        in = SecuritySchemeIn.HEADER  // The location of the token (in the HTTP header)
)
public class SwaggerConfig {

    /**
     * Bean configuration for the Swagger API documentation.
     * This groups the APIs under a "default" group and specifies the package
     * to scan for controllers (which contains the endpoints to document).
     *
     * @return A GroupedOpenApi instance with the defined group and package to scan.
     */
    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("default")  // The name of the group for the APIs
                .packagesToScan("com.api.practice.controllers")  // The package to scan for REST controllers
                .build();
    }
}
