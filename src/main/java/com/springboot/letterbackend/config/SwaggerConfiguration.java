package com.springboot.letterbackend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Letter api",
                description = "Letter 서비스 api 명세서",
                version = "v1"
        )
)
@RequiredArgsConstructor
@Configuration
public class SwaggerConfiguration {
    @Bean
    public GroupedOpenApi groupedOpenApi() {
        String[] paths = {
                "/**",
        };
        return GroupedOpenApi
                .builder()
                .group("letterbackend")
                .pathsToMatch(paths)
                .packagesToScan("com.springboot.letterbackend")
                .build();
    }
}
