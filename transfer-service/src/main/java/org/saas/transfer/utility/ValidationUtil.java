package org.saas.transfer.utility;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.saas.transfer.client.EmployeeServiceClient;
import org.saas.transfer.client.OrganizationServiceClient;
import org.saas.transfer.dto.clientDto.DepartmentDto;
import org.saas.transfer.dto.clientDto.EmployeeDto;
import org.saas.transfer.dto.clientDto.TenantDto;
import org.saas.transfer.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ValidationUtil {

    private final OrganizationServiceClient organizationServiceClient;
    private final EmployeeServiceClient employeeServiceClient;

    public TenantDto getTenantById(UUID tenantId) {

        try {
            return organizationServiceClient.getTenantById(tenantId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Tenant not found with id '" + tenantId + "'");
        }
    }

    public EmployeeDto getEmployeeByName(UUID tenantId,
                                         String firstName,
                                         String middleName,
                                         String lastName) {

        try {
            return employeeServiceClient
                    .getEmployeeByName(tenantId, firstName, middleName, lastName);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException(
                    "Employee not found with name '" + firstName + " " + middleName + " " + lastName + "'");
        }
    }

    public EmployeeDto getEmployeeByEmployeeId(UUID tenantId,
                                               String employeeId) {

        try {
            return employeeServiceClient.getEmployeeByEmployeeId(tenantId, employeeId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Employee not found with employee id '" + employeeId + "'");
        }
    }

    public DepartmentDto getDepartmentById(UUID tenantId,
                                           UUID departmentId) {

        try {
            return organizationServiceClient.getDepartmentById(departmentId, tenantId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Department not found with id '" + departmentId + "'");
        }
    }
}
