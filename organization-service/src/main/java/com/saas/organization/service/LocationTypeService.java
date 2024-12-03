package com.saas.organization.service;

import com.saas.organization.dto.requestDto.LocationTypeRequest;
import com.saas.organization.dto.responseDto.LocationTypeResponse;
import com.saas.organization.model.LocationType;
import com.saas.organization.model.Tenant;
import com.saas.organization.exception.ResourceExistsException;
import com.saas.organization.exception.ResourceNotFoundException;
import com.saas.organization.mapper.LocationTypeMapper;
import com.saas.organization.repository.LocationTypeRepository;
import com.saas.organization.repository.TenantRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationTypeService {

    private final LocationTypeRepository locationTypeRepository;
    private final LocationTypeMapper locationTypeMapper;
    private final TenantRepository tenantRepository;

    public LocationTypeResponse createLocationType(UUID tenantId,
                                                   @Valid LocationTypeRequest locationTypeRequest) {
        // Validate input data
        if (locationTypeRequest.getLocationTypeName() == null ||
                locationTypeRequest.getLocationTypeName().isEmpty()) {
            throw new IllegalArgumentException("Location type name must be provided");
        }

        if (locationTypeRequest.getDescription() == null || locationTypeRequest.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Description must be provided");
        }

        // Check if the tenant exists
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        // Check if the location type already exists for the tenant
        if (locationTypeRepository.existsByLocationTypeNameAndTenantId(
                locationTypeRequest.getLocationTypeName(), tenant.getId())) {
            throw new ResourceExistsException("LocationType with Name " +
                    locationTypeRequest.getLocationTypeName() + " already exists for tenant ID " + tenantId);
        }

        // Map request to entity
        LocationType locationType = locationTypeMapper.mapToEntity(locationTypeRequest);
        locationType.setTenant(tenant);

        // Persist the entity
        locationType = locationTypeRepository.save(locationType);

        // Map entity to response DTO
        return locationTypeMapper.mapToDto(locationType);
    }

    public List<LocationTypeResponse> getAllLocationTypes(UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        List<LocationType> locationTypes = locationTypeRepository.findAll();
        return locationTypes.stream()
                .filter(lt -> lt.getTenant().getId().equals(tenant.getId()))
                .map(locationTypeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public LocationTypeResponse getLocationTypeById(UUID id, UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        LocationType locationType = locationTypeRepository.findById(id)
                .filter(lt -> lt.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("LocationType not found with id: " + id + " for the specified tenant"));

        return locationTypeMapper.mapToDto(locationType);
    }

    public LocationTypeResponse updateLocationType(UUID id, UUID tenantId,
                                                   LocationTypeRequest locationTypeRequest) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        LocationType locationType = locationTypeRepository.findById(id)
                .filter(lt -> lt.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("LocationType not found with id: " + id +
                        " for the specified tenant"));

        // Check if the location type name already exists for the tenant (excluding the current location type)
        if (locationTypeRepository.existsByLocationTypeNameAndTenantIdAndIdNot(
                locationTypeRequest.getLocationTypeName(), tenantId, id)) {
            throw new ResourceExistsException("LocationType with Name " +
                    locationTypeRequest.getLocationTypeName() + " already exists for tenant ID " + tenantId);
        }

        locationType = locationTypeMapper.updateLocationType(locationType, locationTypeRequest);
        locationType = locationTypeRepository.save(locationType);

        return locationTypeMapper.mapToDto(locationType);
    }

    public void deleteLocationType(UUID id, UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        LocationType locationType = locationTypeRepository.findById(id)
                .filter(lt -> lt.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("LocationType not found with id: " +
                        id + " for the specified tenant"));

        locationTypeRepository.delete(locationType);
    }
}