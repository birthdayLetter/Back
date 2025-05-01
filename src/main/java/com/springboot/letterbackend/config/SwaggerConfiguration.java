package com.springboot.letterbackend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
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
        String securitySchemeName = "Authorization";


//        OpenApiCustomizer openApiCustomizer=new OpenApiCustomizer() {
//            @Override
//            public void customise(OpenAPI openApi) {
//                openApi.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
//                        .components(new Components()
//                                .addSecuritySchemes(securitySchemeName,new SecurityScheme()
//                                        .name(securitySchemeName)
//                                        .type(SecurityScheme.Type.HTTP)
//                                        .scheme("bearer")
//                                        .bearerFormat("JWT")
//                                )
//                        );
//
//            }
//        };
        return GroupedOpenApi
                .builder()
                .group("letterbackend")
                .pathsToMatch(paths)
                .packagesToScan("com.springboot.letterbackend")
                .build();
    }
}
