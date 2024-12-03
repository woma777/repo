package org.saas.transfer.client;

import org.saas.transfer.dto.clientDto.ResourceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient("auth-service")
public interface AuthServiceClient {

    @GetMapping("/api/keycloak/resources/{tenantId}/get/resource-name")
    public ResourceDto getResourceByName(@PathVariable UUID tenantId,
                                         @RequestParam String resourceName);
}
