package com.saas.auth.mapper;

import com.saas.auth.dto.clientDto.EmployeeDto;
import com.saas.auth.dto.clientDto.TenantDto;
import com.saas.auth.dto.response.UserResponse;
import com.saas.auth.client.EmployeeServiceClient;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final EmployeeServiceClient employeeServiceClient;

    public UserRepresentation mapToEntity(TenantDto tenant,
                                          String employeeId) {

        EmployeeDto employee = employeeServiceClient
                .getEmployeeByEmployeeId(tenant.getId(), employeeId);

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.singleAttribute("tenantId", tenant.getId().toString());
        userRepresentation.setUsername(employee.getEmployeeId());
        userRepresentation.setFirstName(employee.getFirstName());
        userRepresentation.setLastName(employee.getMiddleName());
        userRepresentation.setEmail(employee.getEmail());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(false);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setValue(employee.getEmployeeId());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        userRepresentation.setCredentials(List.of(credentialRepresentation));

        return userRepresentation;
    }

    public UserResponse mapToDto(UserRepresentation userRepresentation) {

        UserResponse response = new UserResponse();
        response.setId(userRepresentation.getId());
        response.setTenantId(userRepresentation.getAttributes().get("tenantId").getFirst());
        response.setFirstName(userRepresentation.getFirstName());
        response.setLastName(userRepresentation.getLastName());
        response.setEmail(userRepresentation.getEmail());
        response.setUsername(userRepresentation.getUsername());
        response.setIsEnabled(userRepresentation.isEnabled());

        return response;
    }

    public UserRepresentation updateEntity(UUID tenantId,
                                           UserRepresentation userRepresentation,
                                           String employeeId) {

        EmployeeDto employee = employeeServiceClient
                .getEmployeeByEmployeeId(tenantId, employeeId);

        userRepresentation.setFirstName(employee.getFirstName());
        userRepresentation.setLastName(employee.getMiddleName());
        userRepresentation.setEmail(employee.getEmail());

        return userRepresentation;
    }

    public UserRepresentation mapAdminUser(TenantDto tenant) {

        UserRepresentation userRepresentation = new UserRepresentation();
        String username = tenant.getAbbreviatedName().toLowerCase() + ".admin";
        userRepresentation.setUsername(username);
        userRepresentation.setEmail("");
        String firstName = tenant.getAbbreviatedName().toLowerCase();
        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
        userRepresentation.setFirstName(firstName);
        userRepresentation.setLastName("Admin");
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(false);

        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("tenantId", Collections.singletonList(tenant.getId().toString()));
        userRepresentation.setAttributes(attributes);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setValue(username);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        userRepresentation.setCredentials(List.of(credentialRepresentation));

        return userRepresentation;
    }

}
