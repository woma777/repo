package com.saas.recruitment.client;

import com.saas.recruitment.dto.clientDto.ResourceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient("auth-service")
public interface AuthServiceClient {

    @GetMapping("/api/keycloak/resources/{tenantId}/get/resource-name")
    ResourceDto getResourceByName(@PathVariable UUID tenantId,
                                         @RequestParam String resourceName);
}
