package com.saas.auth.utility;

import com.saas.auth.dto.clientDto.EmployeeDto;
import com.saas.auth.dto.clientDto.TenantDto;
import com.saas.auth.exception.ResourceNotFoundException;
import com.saas.auth.client.EmployeeServiceClient;
import com.saas.auth.client.OrganizationServiceClient;
import com.saas.auth.repository.ResourceRepository;
import feign.FeignException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ValidationUtil {

    private final OrganizationServiceClient organizationServiceClient;
    private final EmployeeServiceClient employeeServiceClient;
    private final Keycloak keycloak;
    private final ResourceRepository resourceRepository;

    @Value("${keycloak.realm}")
    private String realm;

    public TenantDto getTenantById(UUID tenantId) {

        try {
            return organizationServiceClient.getTenantById(tenantId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Tenant not found with id '" + tenantId + "'");
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

    public UserRepresentation getUserRepresentation(UUID tenantId,
                                                    String userId) {

        try {
            UsersResource usersResource = keycloak.realm(realm).users();
            UserResource userResource = usersResource.get(userId);
            UserRepresentation userRepresentation = userResource.toRepresentation();
            String userTenantId = userRepresentation.getAttributes().get("tenantId").getFirst();
            if (userTenantId.isEmpty() || !userTenantId.equals(tenantId.toString())) {
                throw new ResourceNotFoundException("User not found with id '" + userId + "'");
            }
            return userRepresentation;
        } catch (NotFoundException e) {
            throw new ResourceNotFoundException("User not found with id '" + userId + "'");
        }
    }

    public RoleRepresentation getRoleRepresentation(TenantDto tenant,
                                                    String roleName) {
        try {
            RolesResource rolesResource = keycloak.realm(realm).roles();
            String role = tenant.getAbbreviatedName().toLowerCase() + "_" + roleName.toLowerCase();
            RoleRepresentation roleRepresentation = rolesResource.get(role).toRepresentation();
            String roleTenantId = roleRepresentation.getAttributes().get("tenantId").getFirst();
            if (roleTenantId.isEmpty() || !roleTenantId.equals(tenant.getId().toString())) {
                throw new ResourceNotFoundException("Role not found with name '" + roleName + "'");
            }
            return roleRepresentation;
        } catch (NotFoundException e) {
            throw new ResourceNotFoundException("Role not found with name '" + roleName + "'");
        }
    }
}
