package com.saas.employee.client;

import com.saas.employee.dto.clientDto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient("organization-service")
public interface OrganizationServiceClient {

    @GetMapping("/api/organization/tenants/get/{id}")
    TenantDto getTenantById(@PathVariable UUID id);

    @GetMapping("/api/organization/departments/{tenant-id}/get/{id}")
    DepartmentDto getDepartmentById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId);

    @GetMapping("/api/organization/job-registrations/{tenant-id}/get/{id}")
    JobDto getJobById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId);

    @GetMapping("/api/organization/job-registrations/{tenant-id}/jobs/{departmentId}")
    List<JobDto> getAllJobsByTenantAndDepartment(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("departmentId") UUID departmentId);

    @GetMapping("/api/organization/pay-grades/{tenant-id}/get/{id}")

    PayGradeDto getPayGradeById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId);

    @GetMapping("/api/organization/pay-grades/{tenant-id}/jobgrade/{jobGradeId}")
    List<PayGradeDto> getPayGradesByJobGradeId(
            @PathVariable UUID jobGradeId,
            @PathVariable("tenant-id") UUID tenantId);

    @GetMapping("/api/organization/locations/{tenant-id}/get/{id}")
    LocationDto getLocationById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId);

    @GetMapping("/api/organization/field-of-studies/{tenant-id}/get/{id}")
    FieldOfStudyDto getFieldOfStudyById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId);

    @GetMapping("/api/organization/education-levels/{tenant-id}/get/{id}")
    EducationLevelDto getEducationLevelById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId);
}

