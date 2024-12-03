package com.saas.gateway.config;

import jakarta.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

@Configuration
@RequiredArgsConstructor
class SwaggerConfig {

    private final SwaggerUiConfigProperties swaggerUiConfigProperties;
    private final RouteDefinitionLocator routeDefinitionLocator;

    @Bean
    public RouteLocator customSwaggerRoutes(RouteLocatorBuilder builder) {

        RouteLocatorBuilder.Builder routesBuilder = builder.routes();
        routeDefinitionLocator.getRouteDefinitions()
                .filter(routeDefinition -> routeDefinition.getId().endsWith("-service"))
                .toStream()
                .forEach(routeDefinition -> {
                    String serviceName = routeDefinition.getId().replace("-service", "");
                    String uri = routeDefinition.getUri().toString();

                    routesBuilder.route(serviceName + "_docs",
                            r -> r.path("/aggregate/" + serviceName + "/v3/api-docs")
                                    .filters(f -> f.setPath("/v3/api-docs"))
                                    .uri(uri));
                });

        return routesBuilder.build();
    }

    @PostConstruct
    public void init() {
        Flux<RouteDefinition> routeDefinitions = routeDefinitionLocator.getRouteDefinitions();
        Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> urls = new HashSet<>();

        routeDefinitions
                .filter(routeDefinition -> routeDefinition.getId().endsWith("-service"))
                .toStream()
                .forEach(routeDefinition -> {
                    String serviceName = routeDefinition.getId().replace("-service", "");
                    String displayName = routeDefinition.getId();
                    String url = "/aggregate/" + serviceName + "/v3/api-docs";
                    AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl =
                            new AbstractSwaggerUiConfigProperties.SwaggerUrl(serviceName, url, displayName);

                    urls.add(swaggerUrl);
                });

        swaggerUiConfigProperties.setUrls(urls);
    }
}