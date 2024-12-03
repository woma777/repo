package com.saas.employee.utility;

import com.saas.employee.client.AuthServiceClient;
import com.saas.employee.config.RoleConverter;
import com.saas.employee.dto.clientDto.ResourceDto;
import com.saas.employee.dto.clientDto.TenantDto;
import com.saas.employee.model.Employee;
import com.saas.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PermissionUtil {

    private final RoleConverter roleConverter;
    private final SecurityUtil securityUtil;
    private final ValidationUtil validationUtil;
    private final AuthServiceClient authServiceClient;
    private final EmployeeRepository employeeRepository;

    public boolean hasPermission(UUID tenantId,
                                 String resourceName) {

        Jwt jwt = securityUtil.getUserJwt();
        Collection<GrantedAuthority> userRoles = roleConverter.extractAuthorities(jwt);
        ResourceDto resource = getResourceByName(tenantId, resourceName);
        Set<String> requiredRoles = resource.getRequiredRoles();
        if (requiredRoles == null || requiredRoles.isEmpty()) {
            throw new AccessDeniedException(
                    "Access Denied - No roles are assigned to access this resource.");
        }
        for (GrantedAuthority authority : userRoles) {
            TenantDto tenant = validationUtil.getTenantById(tenantId);
            String prefix = tenant.getAbbreviatedName().toLowerCase() + "_";
            Set<String> roles = new HashSet<>();
            for (String requiredRole : requiredRoles) {
                String role = prefix + requiredRole;
                requiredRole = requiredRole.replace(requiredRole, role);
                roles.add(requiredRole);
            }
            if (roles.contains(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmployeeHimself(UUID tenantId,
                                     UUID id,
                                     String employeeId) {

        String userTenantId = securityUtil.getTenantId();
        if (userTenantId == null || !userTenantId.equals(tenantId.toString())) {
            throw new AccessDeniedException("Access Denied");
        }
        String loggedInUsername = securityUtil.getUsername();
        if (loggedInUsername == null) {
            throw new AccessDeniedException("Access Denied - No username on token.");
        }
        if (employeeId == null) {
            Employee employee = getEmployeeById(tenantId, id);
            return userTenantId.equals(tenantId.toString()) &&
                    loggedInUsername.equals(employee.getEmployeeId());
        } else {
            return userTenantId.equals(tenantId.toString()) &&
                    loggedInUsername.equals(employeeId);
        }
    }

    private ResourceDto getResourceByName(UUID tenantId,
                                          String resourceName) {

        String userTenantId = securityUtil.getTenantId();
        if (userTenantId == null || !userTenantId.equals(tenantId.toString())) {
            throw new AccessDeniedException("Access Denied");
        }
        try {
            return authServiceClient.getResourceByName(tenantId, resourceName);
        } catch (AccessDeniedException e) {
            throw new AccessDeniedException("Access Denied - Resource not found.");
        }
    }

    private Employee getEmployeeById(UUID tenantId,
                                     UUID id) {

        String userTenantId = securityUtil.getTenantId();
        if (userTenantId == null || !userTenantId.equals(tenantId.toString())) {
            throw new AccessDeniedException("Access Denied");
        }
        return employeeRepository.findByTenantIdAndId(tenantId, id)
                .orElseThrow(() -> new AccessDeniedException("Access Denied"));

    }
}
