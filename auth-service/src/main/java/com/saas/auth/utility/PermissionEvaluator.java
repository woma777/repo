package com.saas.auth.utility;

import com.saas.auth.enums.AuthServiceResourceName;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PermissionEvaluator {

    private final PermissionUtil permissionUtil;

    /* User Permission */
    public void addUserPermission(UUID tenantId) {
        checkPermission(tenantId, AuthServiceResourceName.ADD_USER);
    }

    public void getAllUsersPermission(UUID tenantId) {
        checkPermission(tenantId, AuthServiceResourceName.GET_ALL_USERS);
    }

    public void getUsersByRoleNamePermission(UUID tenantId) {
        boolean isAdmin = permissionUtil.isAdmin();
        if (!isAdmin) {
            checkPermission(tenantId, AuthServiceResourceName.GET_USERS_BY_ROLE_NAME);
        }
    }

    public void getUserRolesPermission(UUID tenantId, String userId) {
        boolean isUserHimself = permissionUtil.isUserHimself(tenantId, userId, null);
        if (!isUserHimself) {
            checkPermission(tenantId, AuthServiceResourceName.GET_USER_ROLES);
        }
    }

    public void getUserByIdPermission(UUID tenantId, String userId) {
        boolean isUserHimself = permissionUtil.isUserHimself(tenantId, userId, null);
        if (!isUserHimself) {
            checkPermission(tenantId, AuthServiceResourceName.GET_USER_BY_ID);
        }
    }

    public void getUserByUsernamePermission(UUID tenantId, String username) {
        boolean isUserHimself = permissionUtil.isUserHimself(tenantId, null, username);
        if (!isUserHimself) {
            checkPermission(tenantId, AuthServiceResourceName.GET_USER_BY_USERNAME);
        }
    }

    public void updateUserPermission(UUID tenantId, String userId) {
        boolean isUserHimself = permissionUtil.isUserHimself(tenantId, userId, null);
        if (!isUserHimself) {
            checkPermission(tenantId, AuthServiceResourceName.UPDATE_USER);
        }
    }

    public void enableUserPermission(UUID tenantId) {
        checkPermission(tenantId, AuthServiceResourceName.ENABLE_USER);
    }

    public void disableUserPermission(UUID tenantId) {
        checkPermission(tenantId, AuthServiceResourceName.DISABLE_USER);
    }

    public void deleteUserPermission(UUID tenantId) {
        checkPermission(tenantId, AuthServiceResourceName.DELETE_USER);
    }

    /* Role Permission */
    public void addRolePermission(UUID tenantId) {
        checkPermission(tenantId, AuthServiceResourceName.ADD_ROLE);
    }

    public void assignRoleToUserPermission(UUID tenantId) {
        checkPermission(tenantId, AuthServiceResourceName.ASSIGN_ROLE_TO_USER);
    }

    public void getAllRolesPermission(UUID tenantId) {
        checkPermission(tenantId, AuthServiceResourceName.GET_ALL_ROLES);
    }

    public void getRoleByNamePermission(UUID tenantId) {
        boolean isAdmin = permissionUtil.isAdmin();
        if (!isAdmin) {
            checkPermission(tenantId, AuthServiceResourceName.GET_ROLE_BY_NAME);
        }
    }

    public void updateRolePermission(UUID tenantId) {
        checkPermission(tenantId, AuthServiceResourceName.UPDATE_ROLE);
    }

    public void unassignRoleFromUserPermission(UUID tenantId) {
        checkPermission(tenantId, AuthServiceResourceName.UNASSIGN_ROLE_FROM_USER);
    }

    public void deleteRolePermission(UUID tenantId) {
        checkPermission(tenantId, AuthServiceResourceName.DELETE_ROLE);
    }

    /* Resource Permission */
    public void getAllResourcesPermission(UUID tenantId) {
        boolean isAdmin = permissionUtil.isAdmin();
        if (!isAdmin) {
            checkPermission(tenantId, AuthServiceResourceName.GET_ALL_RESOURCES);
        }
    }

    public void getResourcesByRoleNamePermission(UUID tenantId) {
        checkPermission(tenantId, AuthServiceResourceName.GET_RESOURCES_BY_ROLE_NAME);
    }

    public void getResourceByIdPermission(UUID tenantId) {
        boolean isAdmin = permissionUtil.isAdmin();
        if (!isAdmin) {
            checkPermission(tenantId, AuthServiceResourceName.GET_RESOURCE_BY_ID);
        }
    }

    public void getResourceByNamePermission(UUID tenantId) {
        boolean isAdmin = permissionUtil.isAdmin();
        if (!isAdmin) {
            checkPermission(tenantId, AuthServiceResourceName.GET_RESOURCE_BY_NAME);
        }
    }

    public void getResourcesByServiceNamePermission(UUID tenantId) {
        boolean isAdmin = permissionUtil.isAdmin();
        if (!isAdmin) {
            checkPermission(tenantId, AuthServiceResourceName.GET_RESOURCES_BY_SERVICE_NAME);
        }
    }

    public void grantResourceAccessToRolePermission(UUID tenantId) {
        checkPermission(tenantId, AuthServiceResourceName.GRANT_RESOURCE_ACCESS_TO_ROLE);
    }

    public void revokeResourceAccessFromRolePermission(UUID tenantId) {
        checkPermission(tenantId, AuthServiceResourceName.REVOKE_RESOURCE_ACCESS_FROM_ROLE);
    }

    private void checkPermission(UUID tenantId, AuthServiceResourceName resourceName) {
        boolean hasPermission = permissionUtil.hasPermission(tenantId, resourceName.getValue());
        if (!hasPermission) {
            throw new AccessDeniedException("Access Denied");
        }
    }
}