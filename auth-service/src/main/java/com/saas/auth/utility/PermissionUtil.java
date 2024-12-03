package com.saas.auth.utility;

import com.saas.auth.config.RoleConverter;
import com.saas.auth.dto.clientDto.TenantDto;
import com.saas.auth.model.Resource;
import com.saas.auth.repository.ResourceRepository;
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
    private final ResourceRepository resourceRepository;

    public boolean hasPermission(UUID tenantId, String resourceName) {

        Jwt jwt = securityUtil.getUserJwt();
        Collection<GrantedAuthority> userRoles = roleConverter.extractAuthorities(jwt);
        Resource resource = getResourceByName(tenantId, resourceName);
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
        throw new AccessDeniedException("Access Denied");
    }

    public boolean isAdmin() {

        Jwt jwt = securityUtil.getUserJwt();
        Collection<GrantedAuthority> userRoles = roleConverter.extractAuthorities(jwt);
        for (GrantedAuthority authority : userRoles) {
            if (authority.getAuthority().equals("admin")) {
                return true;
            }
        }
        return false;
    }

    public boolean isUserHimself(UUID tenantId, String userId, String username) {

        Jwt jwt = securityUtil.getUserJwt();
        String loggedInUserId = jwt.getSubject();
        String userTenantId = securityUtil.getTenantId();
        if (userTenantId == null) {
            throw new AccessDeniedException("Access Denied");
        }
        String loggedInUsername = securityUtil.getUsername();
        if (username == null) {
            return userTenantId.equals(tenantId.toString()) && loggedInUserId.equals(userId);
        } else {
            return userTenantId.equals(tenantId.toString()) && loggedInUsername.equals(username);
        }
    }


    private Resource getResourceByName(UUID tenantId,
                                      String resourceName) {

        String userTenantId = securityUtil.getTenantId();
        if (userTenantId == null || !userTenantId.equals(tenantId.toString())) {
            throw new AccessDeniedException("Access Denied");
        }
        return resourceRepository.findByTenantIdAndResourceName(tenantId, resourceName)
                .orElseThrow(() -> new AccessDeniedException("Access Denied - Resource not found."));
    }
}
