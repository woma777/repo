package com.saas.auth.client;

import com.saas.auth.dto.clientDto.TenantDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient("organization-service")
public interface OrganizationServiceClient {

    @GetMapping("/api/organization/tenants/get/{id}")
    TenantDto getTenantById(@PathVariable UUID id);
}

