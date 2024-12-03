package com.saas.auth.service;

import com.saas.auth.dto.clientDto.TenantDto;
import com.saas.auth.dto.request.RoleRequest;
import com.saas.auth.dto.response.RoleResponse;
import com.saas.auth.exception.ResourceExistsException;
import com.saas.auth.exception.ResourceNotFoundException;
import com.saas.auth.mapper.RoleMapper;
import com.saas.auth.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {

    private final Keycloak keycloak;
    private final RoleMapper roleMapper;
    private final UserService userService;
    private  final ValidationUtil validationUtil;

    @Value("${keycloak.realm}")
    private String realm;

    @Transactional
    public RoleResponse addRole(UUID tenantId,
                                RoleRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        RolesResource rolesResource = getRolesResource();
        RoleRepresentation roleRepresentation = roleMapper.mapToEntity(tenant, request);
        List<RoleRepresentation> roleRepresentations = rolesResource.list();
        for (RoleRepresentation existingRole : roleRepresentations) {
            if (existingRole.getName().equals(request.getRoleName())) {
                throw new ResourceExistsException(
                        "Role with name '" + request.getRoleName() + "' already exists");
            }
        }
        rolesResource.create(roleRepresentation);
        RoleResource roleResource = rolesResource.get(roleRepresentation.getName());
        roleRepresentation = roleResource.toRepresentation();
        return roleMapper.mapToDto(roleRepresentation);
    }

    @Transactional
    public List<RoleResponse> addDefaultAndAdminRole(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        RolesResource rolesResource = getRolesResource();
        String defaultRoleName = tenant.getAbbreviatedName().toLowerCase() + "_default_role";
        String adminRoleName = tenant.getAbbreviatedName().toLowerCase() + "_admin";
        List<RoleRepresentation> roleRepresentations = rolesResource.list();
        for (RoleRepresentation roleRepresentation : roleRepresentations) {
            if (roleRepresentation.getName().equals(defaultRoleName)) {
                throw new ResourceExistsException(
                        "Role with name '" + defaultRoleName + "' already exists");
            }
            if (roleRepresentation.getName().equals(adminRoleName)) {
                throw new ResourceExistsException(
                        "Role with name '" + adminRoleName + "' already exists");
            }
        }
        RoleRepresentation adminRole = roleMapper.mapAdminRoleToEntity(tenant);
        RoleRepresentation defaultRole = roleMapper.mapDefaultRoleToEntity(tenant);
        rolesResource.create(defaultRole);
        rolesResource.create(adminRole);
        List<RoleResponse> roleResponses = new ArrayList<>();
        defaultRole = rolesResource.get(defaultRoleName).toRepresentation();
        adminRole = rolesResource.get(adminRoleName).toRepresentation();
        roleResponses.add(roleMapper.mapToDto(defaultRole));
        roleResponses.add(roleMapper.mapToDto(adminRole));
        return roleResponses;
    }


    public List<RoleResponse> getAllRoles(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        RolesResource rolesResource = getRolesResource();
        List<RoleRepresentation> roleRepresentations = rolesResource.list();
        List<RoleResponse> filteredRoles = new ArrayList<>();
        for (RoleRepresentation role : roleRepresentations) {
            String roleName = role.getName();
            RoleResource roleResource = rolesResource.get(roleName);
            RoleRepresentation roleRepresentation = roleResource.toRepresentation();
            Map<String, List<String>> attributes = roleRepresentation.getAttributes();
            if (attributes != null && attributes.containsKey("tenantId")) {
                String userTenantId = attributes.get("tenantId").getFirst();
                if (userTenantId.equals(tenant.getId().toString())) {
                    filteredRoles.add(roleMapper.mapToDto(roleRepresentation));
                }
            }
        }
        return filteredRoles;
    }

    public RoleResponse getRoleByName(UUID tenantId,
                                      String roleName) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        RoleRepresentation roleRepresentation = validationUtil.getRoleRepresentation(tenant, roleName);
        return roleMapper.mapToDto(roleRepresentation);
    }

    @Transactional
    public RoleResponse updateRole(UUID tenantId,
                                   String roleName,
                                   RoleRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        RoleRepresentation roleRepresentation = validationUtil.getRoleRepresentation(tenant, roleName);
        RolesResource rolesResource = getRolesResource();
        RoleResource roleResource = rolesResource.get(roleRepresentation.getName());
        roleRepresentation = roleMapper.updateEntity(tenant, roleRepresentation, request);
        roleResource.update(roleRepresentation);
        return roleMapper.mapToDto(roleRepresentation);
    }

    @Transactional
    public RoleResponse assignRoleToUser(UUID tenantId,
                                         String userId,
                                         String roleName) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UserRepresentation userRepresentation = validationUtil.getUserRepresentation(tenant.getId(), userId);
        UsersResource usersResource = userService.getUsersResource();
        UserResource userResource = usersResource.get(userRepresentation.getId());
        RoleRepresentation roleRepresentation = validationUtil.getRoleRepresentation(tenant, roleName);
        if (userResource.roles().realmLevel().listAll().stream()
                .anyMatch(r -> r.getName().equals(roleRepresentation.getName()))) {
            throw new ResourceExistsException("User already have '" + roleName + "' role");
        }
        userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));
        return roleMapper.mapToDto(roleRepresentation);
    }

    @Transactional
    public void unAssignRoleFromUser(UUID tenantId, String userId, String roleName) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UserRepresentation userRepresentation = validationUtil.getUserRepresentation(tenant.getId(), userId);
        UsersResource usersResource = userService.getUsersResource();
        UserResource userResource = usersResource.get(userRepresentation.getId());
        RoleRepresentation roleRepresentation = validationUtil.getRoleRepresentation(tenant, roleName);
        String adminUsername = tenant.getAbbreviatedName().toLowerCase() + ".admin";
        if (roleName.equals("admin") && userRepresentation.getUsername().equals(adminUsername)) {
            throw new IllegalStateException("Cannot unassign admin role from admin user");
        }
        if (roleName.equals("default_role")) {
            throw new IllegalStateException("Cannot unassign default role from any user");
        }
        if (!userResource.roles().realmLevel().listEffective().contains(roleRepresentation)) {
            throw new ResourceNotFoundException(
                    "User does not have '" + roleName + "' role");
        }
        userResource.roles().realmLevel().remove(Collections.singletonList(roleRepresentation));
    }

    @Transactional
    public void deleteRole(UUID tenantId,
                           String roleName) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        RolesResource rolesResource = getRolesResource();
        RoleRepresentation roleRepresentation = validationUtil.getRoleRepresentation(tenant, roleName);
        if (roleName.equals("admin")) {
            throw new IllegalStateException("Cannot delete admin role");
        }
        if (roleName.equals("default_role")) {
            throw new IllegalStateException("Cannot delete default role");
        }
        rolesResource.deleteRole(roleRepresentation.getName());
    }

    private RolesResource getRolesResource() {
        return keycloak.realm(realm).roles();
    }
}
