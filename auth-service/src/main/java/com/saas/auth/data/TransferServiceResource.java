package com.saas.auth.data;

import com.saas.auth.dto.clientDto.TenantDto;
import com.saas.auth.enums.EmployeeServiceResourceName;
import com.saas.auth.enums.TransferServiceResourceName;
import com.saas.auth.exception.ResourceExistsException;
import com.saas.auth.model.Resource;
import com.saas.auth.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class TransferServiceResource {
    private final ResourceRepository resourceRepository;

    public void storeEndpoints(TenantDto tenant) {

        List<Resource> resources = new ArrayList<>();

        String defaultRole =  "default_role";

        resources.add(mapToEntity(TransferServiceResourceName.CREAT_TRANSFER.getValue(),
                null, tenant));
        resources.add(mapToEntity(TransferServiceResourceName.GET_ALL_TRANSFER_REQUEST.getValue(),
                null, tenant));
        resources.add(mapToEntity(TransferServiceResourceName.GET_TRANSFER_REQUEST_BY_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(TransferServiceResourceName.UPDATE_TRANSFER_REQUEST.getValue(),
                null, tenant));
        resources.add(mapToEntity(TransferServiceResourceName.DELETE_TRANSFER_REQUEST.getValue(),
                null, tenant));
        resources.add(mapToEntity(TransferServiceResourceName.APPROVE_TRANSFER_REQUEST.getValue(),
                null, tenant));
        resources.add(mapToEntity(TransferServiceResourceName.CREAT_ASSIGNMENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(TransferServiceResourceName.GET_ALL_ASSIGNMENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(TransferServiceResourceName.GET_ASSIGNMENT_BY_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(TransferServiceResourceName.UPDATE_ASSIGNMENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(TransferServiceResourceName.DELETE_ASSIGNMENT.getValue(),
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
        resource.setServiceName("transfer-service");
        resource.setTenantId(tenant.getId());
        resource.setDescription("");

        Set<String> roles = new HashSet<>();
        String adminRole =  "admin";
        roles.add(adminRole);
        if (roleName != null) {
            roles.add(roleName);
        }
        resource.setRequiredRoles(roles);

        return resource;
    }
}
