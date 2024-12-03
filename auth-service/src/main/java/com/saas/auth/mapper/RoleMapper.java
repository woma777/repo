package com.saas.auth.mapper;

import com.saas.auth.dto.clientDto.TenantDto;
import com.saas.auth.dto.request.RoleRequest;
import com.saas.auth.dto.response.RoleResponse;
import com.saas.auth.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class RoleMapper {

    private final ValidationUtil validationUtil;

    public RoleRepresentation mapToEntity(TenantDto tenant,
                                          RoleRequest request) {

        String abbreviatedName = tenant.getAbbreviatedName().toLowerCase();
        String roleName = abbreviatedName + "_"+ request.getRoleName().toLowerCase();

        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(roleName);
        roleRepresentation.setDescription(request.getDescription());
        roleRepresentation.singleAttribute("tenantId", tenant.getId().toString());

        return roleRepresentation;
    }

    public RoleResponse mapToDto(RoleRepresentation roleRepresentation) {

        UUID tenantId = UUID.fromString(roleRepresentation.getAttributes().get("tenantId").getFirst());
        TenantDto tenant = validationUtil.getTenantById(tenantId);
        String abbreviatedName = tenant.getAbbreviatedName().toLowerCase();

        RoleResponse response = new RoleResponse();
        response.setId(roleRepresentation.getId());
        String roleName = roleRepresentation.getName().replace(abbreviatedName + "_", "");
        response.setRoleName(roleName);
        response.setDescription(roleRepresentation.getDescription());
        if (roleRepresentation.getAttributes().get("tenantId").getFirst() != null ||
                !roleRepresentation.getAttributes().isEmpty()) {
            response.setTenantId(roleRepresentation.getAttributes().get("tenantId").getFirst());
        }

        return response;
    }


    public RoleRepresentation updateEntity(TenantDto tenant,
                                           RoleRepresentation roleRepresentation,
                                           RoleRequest request) {

        String abbreviatedName = tenant.getAbbreviatedName().toLowerCase();

        if (request.getRoleName() != null) {
            String roleName = abbreviatedName + "_"+ request.getRoleName().toLowerCase();
            roleRepresentation.setName(roleName);
        }
        if (request.getDescription() != null) {
            roleRepresentation.setDescription(request.getDescription());
        }

        return roleRepresentation;
    }

    public RoleRepresentation mapAdminRoleToEntity(TenantDto tenant) {

        RoleRepresentation roleRepresentation = new RoleRepresentation();
        String abbreviatedName = tenant.getAbbreviatedName().toLowerCase();
        String adminRoleName = abbreviatedName + "_admin";
        String description = "This role is exclusively for " + abbreviatedName + " admin";
        roleRepresentation.setName(adminRoleName);
        roleRepresentation.setDescription(description);
        roleRepresentation.singleAttribute("tenantId", tenant.getId().toString());

        return roleRepresentation;
    }

    public RoleRepresentation mapDefaultRoleToEntity(TenantDto tenant) {

        RoleRepresentation roleRepresentation = new RoleRepresentation();
        String abbreviatedName = tenant.getAbbreviatedName().toLowerCase();
        String defaultRoleName = abbreviatedName + "_default_role";
        String description = "All " + abbreviatedName + " employees have this role";
        roleRepresentation.setName(defaultRoleName);
        roleRepresentation.setDescription(description);
        roleRepresentation.singleAttribute("tenantId", tenant.getId().toString());

        return roleRepresentation;
    }
}
