package com.saas.auth.service;

import com.saas.auth.data.*;
import com.saas.auth.dto.clientDto.TenantDto;
import com.saas.auth.dto.request.ResourceRequest;
import com.saas.auth.dto.request.RolesRequest;
import com.saas.auth.dto.response.ResourceResponse;
import com.saas.auth.exception.ResourceExistsException;
import com.saas.auth.exception.ResourceNotFoundException;
import com.saas.auth.mapper.ResourceMapper;
import com.saas.auth.model.Resource;
import com.saas.auth.repository.ResourceRepository;
import com.saas.auth.utility.ResourceUtil;
import com.saas.auth.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;
    private final ValidationUtil validationUtil;
    private final ResourceUtil resourceUtil;
    private final OrganizationServiceResource organizationServiceResource;
    private final EmployeeServiceResource employeeServiceResource;
    private final AuthServiceResource authServiceResource;
    private final LeaveServiceResource leaveServiceResource;
    private final RecruitmentServiceResource recruitmentServiceResource;
    private final TrainingServiceResource trainingServiceResource;
    private final HrPlanningServiceResource hrPlanningServiceResource;
    private final PromotionServiceResource promotionServiceResource;
    private final TransferServiceResource transferServiceResource;

    @Transactional
    public List<ResourceResponse> addResource(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        organizationServiceResource.storeEndpoints(tenant);
        employeeServiceResource.storeEndpoints(tenant);
        authServiceResource.storeEndpoints(tenant);
        leaveServiceResource.storeEndpoints(tenant);
        recruitmentServiceResource.storeEndpoints(tenant);
        trainingServiceResource.storeEndpoints(tenant);
        hrPlanningServiceResource.storeEndpoints(tenant);
        promotionServiceResource.storeEndpoints(tenant);
        transferServiceResource.storeEndpoints(tenant);

        List<Resource> resources = resourceRepository.findByTenantId(tenant.getId());
        return resources.stream()
                .map(resourceMapper::mapToDto)
                .toList();
    }

    public List<ResourceResponse> getAllResources(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<Resource> resources = resourceRepository.findByTenantId(tenant.getId());
        return resources.stream()
                .map(resourceMapper::mapToDto)
                .toList();
    }

    public List<ResourceResponse> getResourcesNotContainDefaultRole(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<Resource> resources = resourceRepository.findByTenantId(tenant.getId());
        String defaultRole =  "default_role";
        return resources.stream()
                .filter(r -> !r.getRequiredRoles().contains(defaultRole))
                .map(resourceMapper::mapToDto)
                .toList();
    }

    public ResourceResponse getResourceById(UUID tenantId,
                                            UUID resourceId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Resource resource = resourceRepository.findById(resourceId)
                .filter(res -> res.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resource not found with id '" + resourceId + "'"));
        return resourceMapper.mapToDto(resource);
    }

    public ResourceResponse getResponseByName(UUID tenantId,
                                              String resourceName) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Resource resource = resourceRepository.findByTenantIdAndResourceName(tenant.getId(), resourceName)
                .filter(res -> res.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resource not found with name '" + resourceName + "'"));
        return resourceMapper.mapToDto(resource);
    }

    public List<ResourceResponse> getResourcesByServiceName(UUID tenantId,
                                                            String serviceName) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<Resource> resources = resourceRepository.findByTenantIdAndServiceName(tenant.getId(), serviceName);
        String defaultRole =  "default_role";
        return resources.stream()
                .filter(r -> !r.getRequiredRoles().contains(defaultRole))
                .map(resourceMapper::mapToDto)
                .toList();
    }

    @Transactional
    public ResourceResponse updateResource(UUID tenantId,
                                           UUID resourceId,
                                           ResourceRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Resource resource = resourceRepository.findById(resourceId)
                .filter(res -> res.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resource not found with id '" + resourceId + "'"));
        if (resourceRepository.existsByTenantIdAndResourceNameAndIdNot(
                tenant.getId(), request.getResourceName(), resource.getId())) {
            throw new ResourceExistsException(
                    "Resource already exists with name '" + request.getResourceName() + "'");
        }
        resource = resourceMapper.updateEntity(resource, request);
        resource = resourceRepository.save(resource);
        return resourceMapper.mapToDto(resource);
    }

    public List<ResourceResponse> getResourcesByRole(UUID tenantId,
                                                     String roleName) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        RoleRepresentation roleRepresentation = validationUtil.getRoleRepresentation(tenant, roleName);
        List<Resource> resources = resourceRepository.findAll();
        return resources.stream()
                .filter(r -> r.getTenantId().equals(tenant.getId()))
                .filter(r -> r.getRequiredRoles().contains(roleRepresentation.getName()))
                .map(resourceMapper::mapToDto)
                .toList();
    }

    @Transactional
    public List<String> grantResourceAccessToRole(UUID tenantId,
                                                  UUID resourceId,
                                                  RolesRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Resource resource = resourceRepository.findById(resourceId)
                .filter(res -> res.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resource not found with id '" + resourceId + "'"));
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            Set<String> requiredRoles = new HashSet<>();
            for (String roleName : request.getRoles()) {
                RoleRepresentation roleRepresentation = validationUtil.getRoleRepresentation(tenant, roleName);
                String prefix = tenant.getAbbreviatedName().toLowerCase() + "_";
                String requiredRole = roleRepresentation.getName().replace(prefix, "");
                requiredRoles.add(requiredRole);
            }
            Set<String> existingRoles = resource.getRequiredRoles();
            if (existingRoles == null) {
                existingRoles = new HashSet<>();
            }
            Set<String> newRoles = new HashSet<>(requiredRoles);
            existingRoles.addAll(newRoles);
            resource.setRequiredRoles(existingRoles);
            resourceRepository.save(resource);
            resourceUtil.assignRoleToRelatedResources(tenantId, resource.getResourceName(), existingRoles);
            return resource.getRequiredRoles().stream().toList();
        } else {
            throw new IllegalArgumentException(
                    "You must assign at least one role to the resource.");
        }
    }

    @Transactional
    public List<String> revokeResourceAccessFromRole(UUID tenantId,
                                                     UUID resourceId,
                                                     String roleName) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Resource resource = resourceRepository.findById(resourceId)
                .filter(res -> res.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resource not found with id '" + resourceId + "'"));
        if (roleName.equals("admin")) {
            throw new IllegalStateException("Cannot revoke admin role from resource");
        }
        RoleRepresentation roleRepresentation = validationUtil.getRoleRepresentation(tenant, roleName);
        String prefix = tenant.getAbbreviatedName().toLowerCase() + "_";
        String requiredRole = roleRepresentation.getName().replace(prefix, "");
        if (!resource.getRequiredRoles().contains(requiredRole)) {
            throw new ResourceNotFoundException("The specified role is not associated with the resource");
        }
        resource.getRequiredRoles().remove(requiredRole);
        resourceRepository.save(resource);
        return resource.getRequiredRoles().stream().toList();
    }

    @Transactional
    public void deleteResource(UUID tenantId,
                               UUID resourceId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Resource resource = resourceRepository.findById(resourceId)
                .filter(res -> res.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resource not found with id '" + resourceId + "'"));
        resourceRepository.delete(resource);
    }

    @Transactional
    public void deleteAllResources(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<Resource> resources = resourceRepository.findByTenantId(tenant.getId());
        resourceRepository.deleteAll(resources);
    }

//    private final Keycloak keycloak;
//    private final ResourceMapper resourceMapper;
//
//    @Value("${keycloak.realm}")
//    private String realm;
//
//    @Value("${keycloak.client-id}")
//    private String clientId;
//
//    public ResourceResponse addResource(ResourceRequest request) {
//
//        ResourcesResource resourcesResource = getResourcesResource();
//        ResourceRepresentation resourceRepresentation = resourceMapper.mapToEntity(request);
//        try{
//            resourcesResource.create(resourceRepresentation);
//            log.info("New resource has been created");
////            ResourceRepresentation createdResource = resourcesResource.resources()
////                    .get(Integer.parseInt(resourceRepresentation.getId()));
//            return resourceMapper.mapToDto(resourceRepresentation);
//        } catch (Exception e){
//            log.error("Failed to create new resource: {}", e.getMessage());
//            throw new RuntimeException("Failed to create new resource ", e);
//        }
//    }
//
//    public List<ResourceResponse> getAllResources() {
//
//        ResourcesResource resourcesResource = getResourcesResource();
//        List<ResourceRepresentation> resourceRepresentations = resourcesResource.resources();
//        return resourceRepresentations.stream()
//                .map(resourceMapper::mapToDto)
//                .toList();
//    }
//
//    public ResourceResponse getResourceById(String resourceId) {
//
//        ResourcesResource resourcesResource = getResourcesResource();
//        ResourceRepresentation resourceRepresentation = resourcesResource.resource(resourceId).toRepresentation();
//        return resourceMapper.mapToDto(resourceRepresentation);
//    }
//
//    public ResourceResponse updateResource(String resourceId,
//                                           ResourceRequest request) {
//
//        ResourcesResource resourcesResource = getResourcesResource();
//        ResourceRepresentation resourceRepresentation = resourcesResource.resource(resourceId).toRepresentation();
//        resourceRepresentation = resourceMapper.updateEntity(resourceRepresentation, request);
//        resourcesResource.resource(resourceId).update(resourceRepresentation);
//        return resourceMapper.mapToDto(resourceRepresentation);
//    }
//
//    public void deleteResource(String resourceId) {
//
//        ResourcesResource resourcesResource = getResourcesResource();
//        ResourceRepresentation resourceRepresentation = resourcesResource.resource(resourceId).toRepresentation();
//        resourcesResource.resource(resourceRepresentation.getId()).remove();
//    }
//
//    private ResourcesResource getResourcesResource() {
//
//        RealmResource realmResource = keycloak.realm(realm);
//        ClientsResource clientsResource = realmResource.clients();
//        ClientRepresentation clientRepresentation = clientsResource.findByClientId(clientId).get(0);
//        ClientResource clientResource = clientsResource.get(clientRepresentation.getId());
//        AuthorizationResource authorizationResource = clientResource.authorization();
//        return authorizationResource.resources();
//    }
}
