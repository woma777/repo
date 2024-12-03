package com.saas.training.client;

import com.saas.training.dto.clientDto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient("organization-service")
public interface OrganizationServiceClient {

    @GetMapping("/api/organization/tenants/get/{id}")
    TenantDto getTenantById(@PathVariable UUID id);

    @GetMapping("/api/organization/locations/{tenant-id}/get/{id}")
    LocationDto getLocationById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId);

    @GetMapping("/api/organization/departments/{tenant-id}/get/{id}")
    DepartmentDto getDepartmentById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId);

    @GetMapping("/api/organization/education-levels/{tenant-id}/get/{id}")
    EducationLevelDto getEducationLevelById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId);

    @GetMapping("/api/organization/field-of-studies/{tenant-id}/get/{id}")
    FieldOfStudyDto getFieldOfStudyById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId);

    @GetMapping("/api/organization/qualifications/{tenantId}/get/{id}")
    QualificationDto getQualificationById(
            @PathVariable UUID id,
            @PathVariable("tenantId") UUID tenantId);
}

