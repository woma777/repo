package com.saas.auth.controller;

import com.saas.auth.utility.PermissionEvaluator;
import com.saas.auth.dto.request.ResourceRequest;
import com.saas.auth.dto.request.RolesRequest;
import com.saas.auth.dto.response.ResourceResponse;
import com.saas.auth.service.ResourceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/keycloak/resources/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Resource")
public class ResourceController {

    private final ResourceService resourceService;
    private final PermissionEvaluator permissionEvaluator;

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/add")
    public ResponseEntity<?> addResource(
            @PathVariable UUID tenantId) {

        List<ResourceResponse> responses = resourceService
                .addResource(tenantId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllResources(
            @PathVariable UUID tenantId) {

        permissionEvaluator.getAllResourcesPermission(tenantId);

        List<ResourceResponse> response = resourceService
                .getAllResources(tenantId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get/exclude-default-role")
    public ResponseEntity<?> getResourcesNotContainDefaultRole(
            @PathVariable UUID tenantId) {

        permissionEvaluator.getAllResourcesPermission(tenantId);

        List<ResourceResponse> response = resourceService
                .getResourcesNotContainDefaultRole(tenantId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get/{resource-id}")
    public ResponseEntity<?> getResourceById(
            @PathVariable UUID tenantId,
            @PathVariable("resource-id") UUID resourceId) {

        permissionEvaluator.getResourceByIdPermission(tenantId);

        ResourceResponse response = resourceService
                .getResourceById(tenantId, resourceId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get/resource-name")
    public ResponseEntity<?> getResourceByName(
            @PathVariable UUID tenantId,
            @RequestParam String resourceName) {

        permissionEvaluator.getResourceByNamePermission(tenantId);

        ResourceResponse response = resourceService
                .getResponseByName(tenantId, resourceName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get/service-name")
    public ResponseEntity<?> getResourcesByServiceName(
            @PathVariable UUID tenantId,
            @RequestParam String serviceName) {

        permissionEvaluator.getResourcesByServiceNamePermission(tenantId);

        List<ResourceResponse> response = resourceService
                .getResourcesByServiceName(tenantId, serviceName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('admin')")
    @PutMapping("/update/{resource-id}")
    public ResponseEntity<?> updateResource(
            @PathVariable UUID tenantId,
            @PathVariable("resource-id") UUID resourceId,
            @RequestBody ResourceRequest request) {

        ResourceResponse response = resourceService
                .updateResource(tenantId, resourceId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/role/{roleName}")
    public ResponseEntity<?> getResourcesByRole(
            @PathVariable UUID tenantId,
            @PathVariable String roleName) {

        permissionEvaluator.getResourcesByRoleNamePermission(tenantId);

        List<ResourceResponse> response = resourceService
                .getResourcesByRole(tenantId, roleName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/role/grant-access/{resourceId}")
    public ResponseEntity<?> giveResourceAccessToRole(
            @PathVariable UUID tenantId,
            @PathVariable UUID resourceId,
            @RequestBody RolesRequest request) {

        permissionEvaluator.grantResourceAccessToRolePermission(tenantId);

        List<String> response = resourceService
                .grantResourceAccessToRole(tenantId, resourceId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/role/revoke-access/{resourceId}/{roleName}")
    public ResponseEntity<?> removeResourceAccessFromRole(
            @PathVariable UUID tenantId,
            @PathVariable UUID resourceId,
            @PathVariable String roleName) {

        permissionEvaluator.revokeResourceAccessFromRolePermission(tenantId);

        List<String> response = resourceService
                .revokeResourceAccessFromRole(tenantId, resourceId, roleName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/delete/{resource-id}")
    public ResponseEntity<?> deleteResource(
            @PathVariable UUID tenantId,
            @PathVariable("resource-id") UUID resourceId) {

        resourceService.deleteResource(tenantId, resourceId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Resource deleted successfully!");
    }

    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/delete-all")
    public ResponseEntity<?> deleteAllResource(
            @PathVariable UUID tenantId) {

        resourceService.deleteAllResources(tenantId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("All resources are deleted successfully!");
    }
}