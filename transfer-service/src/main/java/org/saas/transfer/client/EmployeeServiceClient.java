package org.saas.transfer.client;

import org.saas.transfer.dto.clientDto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient("employee-service")
public interface EmployeeServiceClient {

    @GetMapping("/api/employee/employees/{tenant-id}/get")
    EmployeeDto getEmployeeByEmployeeId(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("employee-id") String employeeId);

    @GetMapping("/api/employee/employees/{tenant-id}/get-employee")
    EmployeeDto getEmployeeByName(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("first-name") String firstName,
            @RequestParam(value = "middle-name", required = false) String middleName,
            @RequestParam(value = "last-name", required = false) String lastName);
}
