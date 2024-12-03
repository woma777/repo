package com.saas.planning.client;

import com.saas.planning.dto.ResourceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient("auth-service")
public interface AuthServiceClient {

    @GetMapping("/api/keycloak/resources/{tenantId}/get/resource-name")
    ResourceDto getResourceByName(@PathVariable UUID tenantId,
                                  @RequestParam String resourceName);
}