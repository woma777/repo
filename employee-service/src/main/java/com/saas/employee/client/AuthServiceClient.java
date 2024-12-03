package com.saas.employee.client;

import com.saas.employee.dto.clientDto.ResourceDto;
import com.saas.employee.dto.clientDto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient("auth-service")
public interface AuthServiceClient {

    @GetMapping("/api/keycloak/resources/{tenantId}/get-all")
    List<ResourceDto> getAllResources(@PathVariable UUID tenantId);

    @GetMapping("/api/keycloak/resources/{tenantId}/get/resource-name")
    ResourceDto getResourceByName(@PathVariable UUID tenantId,
                                  @RequestParam String resourceName);

    @PostMapping("/api/keycloak/users/{tenantId}/add")
    UserDto createUser(@PathVariable UUID tenantId,
                       @RequestParam String employeeId);

    @PutMapping("/api/keycloak/users/{tenantId}//update/{user-id}")
    UserDto updateUser(@PathVariable UUID tenantId,
                       @PathVariable("user-id") String userId,
                       @RequestParam String employeeId);

    @GetMapping("/api/keycloak/users/{tenantId}/get/username")
    UserDto getUserByUsername(@PathVariable UUID tenantId,
                              @RequestParam String username);

    @GetMapping("/api/keycloak/users/{tenantId}/get/{user-id}")
    UserDto getUserById(
            @PathVariable UUID tenantId,
            @PathVariable("user-id") String userId);

    @DeleteMapping("/api/keycloak/users/{tenantId}/delete/{user-id}")
    String deleteUser(@PathVariable UUID tenantId,
                      @PathVariable("user-id") String userId);
}
