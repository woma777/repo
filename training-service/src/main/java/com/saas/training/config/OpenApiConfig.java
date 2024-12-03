package com.saas.training.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${keycloak.openIdConnectUrl}")
    private String keycloakOpenIdConnectUrl;

    @Value("${ip-address}")
    private String ipAddress;

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("OpenApi Specification - SaaS")
                        .version("1.0")
                        .description("OpenApi documentation for training service")
                        .termsOfService("Terms of service")
                        .contact(new Contact()
                                .name("Owner name")
                                .email("Owner email address")
                                .url("Owner website url"))
                        .license(new License()
                                .name("Licence name")
                                .url("Licence url")))
                .addServersItem(new Server().url("http://"+ipAddress+":8183").description("Remote ENV"))
                .addServersItem(new Server().url("http://localhost:8183").description("Local ENV"))
                .addSecurityItem(new SecurityRequirement().addList("Keycloak"))
                .schemaRequirement("Keycloak", new SecurityScheme()
                        .type(SecurityScheme.Type.OPENIDCONNECT)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .openIdConnectUrl(keycloakOpenIdConnectUrl));
    }
}