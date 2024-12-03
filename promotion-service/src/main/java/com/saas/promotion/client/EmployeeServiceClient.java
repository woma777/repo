package com.saas.promotion.client;

import com.saas.promotion.dto.clientDto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient("employee-service")
public interface EmployeeServiceClient {

    @GetMapping("/api/employee/employees/{tenant-id}/get/{employee-id}")
    EmployeeDto getEmployeeById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId);

    @GetMapping("/api/employee/employees/{tenant-id}/get")
    EmployeeDto getEmployeeByEmployeeId(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("employee-id") String employeeId);

    @PutMapping("/api/employee/employees/{tenant-id}/add-history/{employee-id}")
    ResponseEntity<?> addEmployeeHistory(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @RequestParam String vacancyNumber,
            @RequestParam UUID payGradeId);
}
