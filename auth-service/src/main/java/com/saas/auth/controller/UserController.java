package com.saas.auth.controller;

import com.saas.auth.utility.PermissionEvaluator;
import com.saas.auth.dto.request.ResetPasswordRequest;
import com.saas.auth.dto.response.RoleResponse;
import com.saas.auth.dto.response.UserResponse;
import com.saas.auth.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/keycloak/users/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {

    private final UserService userService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> createUser(
            @PathVariable UUID tenantId,
            @RequestParam String employeeId) {

        permissionEvaluator.addUserPermission(tenantId);

        UserResponse response = userService.addUser(tenantId, employeeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/add/admin")
    public ResponseEntity<?> addAdminUser(
            @PathVariable UUID tenantId) {

        UserResponse response = userService.addAdminUser(tenantId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllUsers(
            @PathVariable UUID tenantId) {

        permissionEvaluator.getAllUsersPermission(tenantId);

        List<UserResponse> response = userService.getAllUsers(tenantId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get-users/role-name")
    public ResponseEntity<?> getUsersByRoleName(
            @PathVariable UUID tenantId,
            @RequestParam String roleName) {

        permissionEvaluator.getUsersByRoleNamePermission(tenantId);

        List<UserResponse> response = userService.getUsersByRoleName(tenantId, roleName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get/{user-id}")
    public ResponseEntity<?> getUserById(
            @PathVariable UUID tenantId,
            @PathVariable("user-id") String userId) {

        permissionEvaluator.getUserByIdPermission(tenantId, userId);

        UserResponse response = userService.getUserById(tenantId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get/username")
    public ResponseEntity<?> getUserByUsername(
            @PathVariable UUID tenantId,
            @RequestParam String username) {

        permissionEvaluator.getUserByUsernamePermission(tenantId, username);

        UserResponse response = userService.getUserByUsername(tenantId, username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get/user-roles/{user-id}")
    public ResponseEntity<?> getUserRoles(
            @PathVariable UUID tenantId,
            @PathVariable("user-id") String userId) {

        permissionEvaluator.getUserRolesPermission(tenantId, userId);

        List<RoleResponse> response = userService.getUserRoles(tenantId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{user-id}")
    public ResponseEntity<?> updateUser(
            @PathVariable UUID tenantId,
            @PathVariable("user-id") String userId,
            @RequestParam String employeeId) {

        permissionEvaluator.updateUserPermission(tenantId, userId);

        UserResponse response = userService.updateUser(tenantId, userId, employeeId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update-email/{user-id}")
    public ResponseEntity<?> updateUserEmail(
            @PathVariable UUID tenantId,
            @PathVariable("user-id") String userId,
            @RequestParam String email) {

        permissionEvaluator.updateUserPermission(tenantId, userId);

        UserResponse response = userService.updateUserEmail(tenantId, userId, email);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/enable-user/{user-id}")
    public ResponseEntity<?> enableUser(
            @PathVariable UUID tenantId,
            @PathVariable("user-id") String userId) {

        permissionEvaluator.enableUserPermission(tenantId);

        userService.enableUser(tenantId, userId);
        return ResponseEntity.status(HttpStatus.OK).body("User enabled successfully!");
    }

    @PutMapping("/disable-user/{user-id}")
    public ResponseEntity<?> disableUser(
            @PathVariable UUID tenantId,
            @PathVariable("user-id") String userId) {

        permissionEvaluator.disableUserPermission(tenantId);

        userService.disableUser(tenantId, userId);
        return ResponseEntity.status(HttpStatus.OK).body("User disabled successfully!");
    }

    @PreAuthorize("hasRole('default-roles-saas-realm')")
    @PutMapping("/send-verification-email/{userId}")
    public ResponseEntity<?> sendVerificationEmail(
            @PathVariable UUID tenantId,
            @PathVariable String userId) {

        userService.sendVerificationEmail(tenantId, userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Verification email sent successfully!");
    }

    @PreAuthorize("hasRole('default-roles-saas-realm')")
    @PutMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @PathVariable UUID tenantId,
            @RequestParam String email) {

        userService.forgotPassword(tenantId, email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasRole('default-roles-saas-realm')")
    @PutMapping("{userId}/reset-password")
    public ResponseEntity<?> resetUserPassword(
            @PathVariable UUID tenantId,
            @PathVariable String userId,
            @RequestBody ResetPasswordRequest request) {

        userService.resetUserPassword(tenantId, userId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/delete/{user-id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable UUID tenantId,
            @PathVariable("user-id") String userId) {

        permissionEvaluator.deleteUserPermission(tenantId);

        userService.deleteUser(tenantId, userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("User deleted successfully!");
    }
}