package com.saas.auth.mapper;

import com.saas.auth.dto.clientDto.TenantDto;
import com.saas.auth.dto.request.ResourceRequest;
import com.saas.auth.dto.response.ResourceResponse;
import com.saas.auth.model.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class ResourceMapper {


    public Resource mapToEntity(TenantDto tenant,
                                ResourceRequest request) {

        Resource resource = new Resource();
        resource.setTenantId(tenant.getId());
        resource.setResourceName(request.getResourceName());
        resource.setServiceName(request.getServiceName());
        resource.setDescription(request.getDescription());

        return resource;
    }

    public ResourceResponse mapToDto(Resource resource) {

        ResourceResponse response = new ResourceResponse();
        response.setId(resource.getId());
        response.setResourceName(resource.getResourceName());
        response.setServiceName(resource.getServiceName());
        response.setDescription(resource.getDescription());
        response.setTenantId(resource.getTenantId());
        response.setCreatedAt(resource.getCreatedAt());
        response.setCreatedBy(resource.getCreatedBy());
        response.setUpdatedAt(resource.getUpdatedAt());
        response.setUpdatedBy(resource.getUpdatedBy());

        if (resource.getRequiredRoles() == null || resource.getRequiredRoles().isEmpty()) {
            response.setRequiredRoles(Collections.singleton("Not Assigned"));
        } else {
            response.setRequiredRoles(resource.getRequiredRoles());
        }

        return response;
    }

    public Resource updateEntity(Resource resource, ResourceRequest request) {

        if (request.getResourceName() != null) {
            resource.setResourceName(request.getResourceName());
        }
        if (request.getServiceName() != null) {
            resource.setServiceName(request.getServiceName());
        }
        if (request.getDescription() != null) {
            resource.setDescription(request.getDescription());
        }

        return resource;
    }

//    public ResourceRepresentation mapToEntity(ResourceRequest request) {
//
//        ResourceRepresentation resourceRepresentation = new ResourceRepresentation();
//        resourceRepresentation.setName(request.getResourceName());
//        resourceRepresentation.setDisplayName(request.getResourceName());
//        resourceRepresentation.setUris(request.getUris());
//
//        return resourceRepresentation;
//    }
//
//    public ResourceResponse mapToDto(ResourceRepresentation resourceRepresentation) {
//
//        ResourceResponse response = new ResourceResponse();
//        response.setId(resourceRepresentation.getId());
//        response.setResourceName(resourceRepresentation.getName());
//        response.setUris(resourceRepresentation.getUris());
//
//        return response;
//    }
//
//    public ResourceRepresentation updateEntity(ResourceRepresentation resourceRepresentation,
//                                               ResourceRequest request) {
//
//        if (request.getResourceName() != null) {
//            resourceRepresentation.setName(request.getResourceName());
//            resourceRepresentation.setDisplayName(request.getResourceName());
//        }
//        if (request.getUris() != null) {
//            resourceRepresentation.setUris(request.getUris());
//        }
//
//        return resourceRepresentation;
//    }
}
