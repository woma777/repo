package com.saas.auth.service;

import com.saas.auth.client.EmployeeServiceClient;
import com.saas.auth.dto.clientDto.EmployeeDto;
import com.saas.auth.dto.clientDto.TenantDto;
import com.saas.auth.dto.request.ResetPasswordRequest;
import com.saas.auth.dto.response.RoleResponse;
import com.saas.auth.dto.response.UserResponse;
import com.saas.auth.exception.ResourceExistsException;
import com.saas.auth.exception.ResourceNotFoundException;
import com.saas.auth.mapper.RoleMapper;
import com.saas.auth.mapper.UserMapper;
import com.saas.auth.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final Keycloak keycloak;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final ValidationUtil validationUtil;
    private final EmployeeServiceClient employeeServiceClient;

    @Value("${keycloak.realm}")
    private String realm;

    @Transactional
    public UserResponse addUser(UUID tenantId,
                                String employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        EmployeeDto employee = validationUtil.getEmployeeByEmployeeId(tenant.getId(), employeeId);
        UsersResource usersResource = getUsersResource();
        UserRepresentation userRepresentation = userMapper.mapToEntity(tenant, employeeId);
        List<UserRepresentation> existsByUser = usersResource
                .searchByUsername(employee.getEmployeeId(), true);
        if (!existsByUser.isEmpty()) {
            throw new ResourceExistsException(
                    "User with username '" + employee.getEmployeeId() + "' already exists.");
        }
        List<UserRepresentation> existsByEmail = usersResource
                .searchByEmail(employee.getEmail(), true);
        if (!existsByEmail.isEmpty()) {
            throw new ResourceExistsException(
                    "User with email '" + employee.getEmail() + "' already exists.");
        }
        String roleName = "default_role";
        RoleRepresentation roleRepresentation = validationUtil.getRoleRepresentation(tenant, roleName);
        usersResource.create(userRepresentation);
        userRepresentation = usersResource.searchByUsername(employee.getEmployeeId(), true).getFirst();
        UserResource userResource = usersResource.get(userRepresentation.getId());
        userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));
        return userMapper.mapToDto(userRepresentation);
    }

    @Transactional
    public UserResponse addAdminUser(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UsersResource usersResource = getUsersResource();
        String username = tenant.getAbbreviatedName().toLowerCase() + ".admin";
        UserRepresentation userRepresentation = userMapper.mapAdminUser(tenant);
        List<UserRepresentation> existsByUsername = usersResource.searchByUsername(username, true);
        if (!existsByUsername.isEmpty()) {
            throw new ResourceExistsException(
                    "User with username '" + username + "' already exists.");
        }
        List<UserRepresentation> existsByEmail = usersResource
                .searchByEmail(userRepresentation.getEmail(), true);
        if (!existsByEmail.isEmpty()) {
            throw new ResourceExistsException(
                    "User with email '" + userRepresentation.getEmail() + "' already exists.");
        }
        String roleName = "admin";
        RoleRepresentation roleRepresentation = validationUtil.getRoleRepresentation(tenant, roleName);
        usersResource.create(userRepresentation);
        userRepresentation = usersResource.searchByUsername(username, true).getFirst();
        UserResource userResource = usersResource.get(userRepresentation.getId());
        userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));
        return userMapper.mapToDto(userRepresentation);
    }

    public List<UserResponse> getAllUsers(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> userRepresentations = usersResource.list();
        return userRepresentations.stream()
                .filter(user -> {
                    Optional<String> userTenantId = Optional.ofNullable(
                            user.getAttributes()).map(attr -> attr.get("tenantId").getFirst());
                    return userTenantId.isPresent() && userTenantId.get().equals(tenant.getId().toString());
                })
                .map(userMapper::mapToDto)
                .toList();
    }

    public List<UserResponse> getUsersByRoleName(UUID tenantId,
                                                 String roleName) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        RoleRepresentation roleRepresentation = validationUtil.getRoleRepresentation(tenant, roleName);
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> userRepresentations = usersResource.list().stream()
                .filter(user ->
                        usersResource.get(user.getId()).roles().realmLevel().listAll().stream()
                        .anyMatch(r -> r.getName().equals(roleRepresentation.getName())))
                .toList();
        return userRepresentations.stream()
                .filter(user -> {
                    Optional<String> userTenantId = Optional.ofNullable(
                            user.getAttributes()).map(attr -> attr.get("tenantId").getFirst());
                    return userTenantId.isPresent() && userTenantId.get().equals(tenant.getId().toString());
                })
                .map(userMapper::mapToDto)
                .toList();
    }

    public UserResponse getUserById(UUID tenantId, String userId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UserRepresentation userRepresentation = validationUtil.getUserRepresentation(tenant.getId(), userId);
        return userMapper.mapToDto(userRepresentation);
    }

    public UserResponse getUserByUsername(UUID tenantId,
                                          String username) {


        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(username, true);
        if (userRepresentations.isEmpty()) {
            throw new ResourceNotFoundException("User not found with username '" + username + "'");
        }
        UserRepresentation userRepresentation = userRepresentations.getFirst();
        userRepresentation = validationUtil
                .getUserRepresentation(tenant.getId(), userRepresentation.getId());
        return userMapper.mapToDto(userRepresentation);
    }

    public List<RoleResponse> getUserRoles(UUID tenantId,
                                           String userId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UsersResource usersResource = getUsersResource();
        UserRepresentation userRepresentation = validationUtil.getUserRepresentation(tenant.getId(), userId);
        UserResource userResource = usersResource.get(userRepresentation.getId());
        List<RoleRepresentation> roleRepresentations = userResource.roles().realmLevel().listAll();
        List<RoleResponse> filteredRoles = new ArrayList<>();
        for (RoleRepresentation role : roleRepresentations) {
            String roleName = role.getName();
            RolesResource rolesResource = getRolesResource();
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

    @Transactional
    public UserResponse updateUser(UUID tenantId,
                                   String userId,
                                   String employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UsersResource usersResource = getUsersResource();
        UserRepresentation userRepresentation = validationUtil.getUserRepresentation(tenant.getId(), userId);
        userRepresentation = userMapper.updateEntity(tenant.getId(), userRepresentation, employeeId);
        List<UserRepresentation> existsByEmail = usersResource
                .searchByEmail(userRepresentation.getEmail(), true);
        if (!existsByEmail.isEmpty() &&
                !userRepresentation.getId().equals(existsByEmail.getFirst().getId())) {
            throw new ResourceExistsException(
                    "User with email " + userRepresentation.getEmail() + " already exists");
        }
        UserResource userResource = usersResource.get(userRepresentation.getId());
        userResource.update(userRepresentation);
        return userMapper.mapToDto(userRepresentation);
    }

    @Transactional
    public UserResponse updateUserEmail(UUID tenantId,
                                        String userId,
                                        String email) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UsersResource usersResource = getUsersResource();
        UserRepresentation userRepresentation = validationUtil.getUserRepresentation(tenant.getId(), userId);
        if (email != null && !email.isEmpty()) {
            userRepresentation.setEmail(email);
        }
        List<UserRepresentation> existsByEmail = usersResource
                .searchByEmail(email, true);
        if (!existsByEmail.isEmpty() &&
                !userRepresentation.getId().equals(existsByEmail.getFirst().getId())) {
            throw new ResourceExistsException(
                    "User with email " + userRepresentation.getEmail() + " already exists");
        }
        UserResource userResource = usersResource.get(userRepresentation.getId());
        userResource.update(userRepresentation);
        String adminUsername = tenant.getAbbreviatedName().toLowerCase() + ".admin";
        if (!userRepresentation.getUsername().equals(adminUsername)){
            EmployeeDto employee = validationUtil
                    .getEmployeeByEmployeeId(tenantId, userRepresentation.getUsername());
            employeeServiceClient.updateEmployeeEmail(tenantId, employee.getId(), email);
        }
        return userMapper.mapToDto(userRepresentation);
    }

    public void enableUser(UUID tenantId,
                           String userId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UsersResource usersResource = getUsersResource();
        UserRepresentation userRepresentation = validationUtil.getUserRepresentation(tenant.getId(), userId);
        userRepresentation.setEnabled(true);
        if (userRepresentation.isEnabled().equals(true)) {
            throw new ResourceExistsException("User already enabled");
        }
        UserResource userResource = usersResource.get(userRepresentation.getId());
        userResource.update(userRepresentation);
    }

    public void disableUser(UUID tenantId,
                           String userId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UsersResource usersResource = getUsersResource();
        UserRepresentation userRepresentation = validationUtil.getUserRepresentation(tenant.getId(), userId);
        String adminUsername = tenant.getAbbreviatedName().toLowerCase() + ".admin";
        if (userRepresentation.getUsername().equals(adminUsername)) {
            throw new IllegalStateException("Cannot disable admin user");
        }
        if (userRepresentation.isEnabled().equals(false)) {
            throw new ResourceExistsException("User already disabled");
        }
        userRepresentation.setEnabled(false);
        UserResource userResource = usersResource.get(userRepresentation.getId());
        userResource.update(userRepresentation);
    }

    public void sendVerificationEmail(UUID tenantId,
                                      String userId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UsersResource usersResource = getUsersResource();
        UserRepresentation userRepresentation = validationUtil.getUserRepresentation(tenant.getId(), userId);
        usersResource.get(userRepresentation.getId()).sendVerifyEmail();
    }

    public void forgotPassword(UUID tenantId,
                               String email) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> userRepresentations = usersResource.searchByEmail(email, true);
        if (userRepresentations.isEmpty()) {
            throw new ResourceNotFoundException("User not found with email '" + email + "'");
        }
        UserRepresentation userRepresentation = userRepresentations.getFirst();
        userRepresentation = validationUtil
                .getUserRepresentation(tenant.getId(), userRepresentation.getId());
        UserResource userResource = usersResource.get(userRepresentation.getId());
        userResource.executeActionsEmail(List.of("UPDATE_PASSWORD"));
    }

    @Transactional
    public void resetUserPassword(UUID tenantId,
                                  String userId,
                                  ResetPasswordRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UsersResource usersResource = getUsersResource();
        UserRepresentation userRepresentation = validationUtil.getUserRepresentation(tenant.getId(), userId);
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.getPassword());
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match.");
        }
        UserResource userResource = usersResource.get(userRepresentation.getId());
        userResource.resetPassword(credential);
    }

    @Transactional
    public void deleteUser(UUID tenantId,
                           String userId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UsersResource usersResource = getUsersResource();
        UserRepresentation userRepresentation = validationUtil.getUserRepresentation(tenant.getId(), userId);
        String adminUsername = tenant.getAbbreviatedName().toLowerCase() + ".admin";
        if (userRepresentation.getUsername().equals(adminUsername)){
            throw new IllegalStateException("Cannot delete admin user");
        }
        usersResource.delete(userRepresentation.getId());
    }

    public UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }

    private RolesResource getRolesResource() {
        return keycloak.realm(realm).roles();
    }
}