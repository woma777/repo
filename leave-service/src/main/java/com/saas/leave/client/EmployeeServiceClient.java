package com.saas.leave.client;

import com.saas.leave.dto.EmployeeDto;
import com.saas.leave.dto.ExperienceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient("employee-service")
public interface EmployeeServiceClient {

    @GetMapping("/api/employee/employees/{tenantId}/get/{employeeId}")
    EmployeeDto getEmployeeById(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable("employeeId") UUID employeeId);

    @GetMapping("/api/employee/experiences/{tenantId}/{employeeId}/get-all")
    List<ExperienceDto> getExperienceByEmployeeId(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable("employeeId") UUID employeeId);

    @GetMapping("/api/employee/employees/{tenantId}/get-all")
    List<EmployeeDto> getAllEmployees(
            @PathVariable("tenantId") UUID tenantId);
}
