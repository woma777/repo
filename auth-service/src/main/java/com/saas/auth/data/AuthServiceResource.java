package com.saas.auth.data;

import com.saas.auth.dto.clientDto.TenantDto;
import com.saas.auth.enums.AuthServiceResourceName;
import com.saas.auth.exception.ResourceExistsException;
import com.saas.auth.model.Resource;
import com.saas.auth.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class AuthServiceResource {

    private final ResourceRepository resourceRepository;

    public void storeEndpoints(TenantDto tenant) {

        List<Resource> resources = new ArrayList<>();

        String defaultRole = "default_role";

        /* User */
        resources.add(mapToEntity(AuthServiceResourceName.ADD_USER.getValue(),
                null, tenant));
        resources.add(mapToEntity(AuthServiceResourceName.GET_ALL_USERS.getValue(),
                null, tenant));
        resources.add(mapToEntity(AuthServiceResourceName.GET_USERS_BY_ROLE_NAME.getValue(),
                null, tenant));
        resources.add(mapToEntity(AuthServiceResourceName.GET_USER_ROLES.getValue(),
                null , tenant));
        resources.add(mapToEntity(AuthServiceResourceName.GET_USER_BY_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(AuthServiceResourceName.GET_USER_BY_USERNAME.getValue(),
                null, tenant));
        resources.add(mapToEntity(AuthServiceResourceName.UPDATE_USER.getValue(),
                null, tenant));
        resources.add(mapToEntity(AuthServiceResourceName.ENABLE_USER.getValue(),
                null, tenant));
        resources.add(mapToEntity(AuthServiceResourceName.DISABLE_USER.getValue(),
                null, tenant));
        resources.add(mapToEntity(AuthServiceResourceName.DELETE_USER.getValue(),
                null, tenant));

        /* Role */
        resources.add(mapToEntity(AuthServiceResourceName.ADD_ROLE.getValue(),
                null, tenant));
        resources.add(mapToEntity(AuthServiceResourceName.ASSIGN_ROLE_TO_USER.getValue(),
                null, tenant));
        resources.add(mapToEntity(AuthServiceResourceName.GET_ALL_ROLES.getValue(),
                null, tenant));
        resources.add(mapToEntity(AuthServiceResourceName.GET_ROLE_BY_NAME.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(AuthServiceResourceName.UPDATE_ROLE.getValue(),
                null, tenant));
        resources.add(mapToEntity(AuthServiceResourceName.UNASSIGN_ROLE_FROM_USER.getValue(),
                null, tenant));
        resources.add(mapToEntity(AuthServiceResourceName.DELETE_ROLE.getValue(),
                null, tenant));

        /* Resource */
        resources.add(mapToEntity(AuthServiceResourceName.GET_ALL_RESOURCES.getValue(),
                null, tenant));
        resources.add(mapToEntity(AuthServiceResourceName.GET_RESOURCES_BY_ROLE_NAME.getValue(),
                null, tenant));
        resources.add(mapToEntity(AuthServiceResourceName.GET_RESOURCE_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(AuthServiceResourceName.GET_RESOURCE_BY_NAME.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(AuthServiceResourceName.GET_RESOURCES_BY_SERVICE_NAME.getValue(),
                null, tenant));
        resources.add(mapToEntity(AuthServiceResourceName.GRANT_RESOURCE_ACCESS_TO_ROLE.getValue(),
                null, tenant));
        resources.add(mapToEntity(AuthServiceResourceName.REVOKE_RESOURCE_ACCESS_FROM_ROLE.getValue(),
                null, tenant));

        for (Resource resource : resources) {
            if (resourceRepository
                    .existsByTenantIdAndResourceName(tenant.getId(), resource.getResourceName())) {
                throw new ResourceExistsException(
                        "Resource already exists with name '" + resource.getResourceName() + "'");
            }
            resourceRepository.save(resource);
        }
    }

    private Resource mapToEntity(String requestName,
                                 String roleName,
                                 TenantDto tenant) {

        Resource resource = new Resource();
        resource.setResourceName(requestName);
        resource.setServiceName("auth-service");
        resource.setTenantId(tenant.getId());
        resource.setDescription("");

        Set<String> roles = new HashSet<>();
        String adminRole = "admin";
        roles.add(adminRole);
        if (roleName != null) {
            roles.add(roleName);
        }
        resource.setRequiredRoles(roles);

        return resource;
    }
}