package com.saas.leave.client;
import com.saas.leave.dto.DepartmentDto;
import com.saas.leave.dto.TenantDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient("organization-service")
public interface OrganizationServiceClient {

    @GetMapping("/api/organization/tenants/get/{id}")
    TenantDto getTenantById(@PathVariable UUID id);

    @GetMapping("/api/organization/departments/{tenant-id}/get/{id}")
    DepartmentDto getDepartmentById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId);
}
