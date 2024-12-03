package com.saas.leave.service;

import com.saas.leave.client.OrganizationServiceClient;
import com.saas.leave.dto.TenantDto;
import com.saas.leave.dto.request.HolidayManagementRequest;
import com.saas.leave.dto.response.HolidayManagementResponse;
import com.saas.leave.model.HolidayManagement;
import com.saas.leave.exception.ResourceExistsException;
import com.saas.leave.exception.ResourceNotFoundException;
import com.saas.leave.mapper.HolidayManagementMapper;
import com.saas.leave.repository.HolidayManagementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HolidayManagementService {

    private final HolidayManagementRepository holidayManagementRepository;
    private final HolidayManagementMapper holidayManagementMapper;
    private final OrganizationServiceClient organizationServiceClient;

    public HolidayManagementResponse createHolidayManagement(UUID tenantId, HolidayManagementRequest holidayManagementRequest) {
        // Validate the tenant ID
        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }
        if (holidayManagementRepository.existsByHolidayId(holidayManagementRequest.getHolidayId())) {
            throw new ResourceExistsException("Holiday with Name " + holidayManagementRequest.getHolidayId() + " already exists");
        }


        // Map the request to an entity
        HolidayManagement holidayManagement = holidayManagementMapper.mapToEntity(holidayManagementRequest);
        holidayManagement.setTenantId(tenant.getId());

        // Save the holiday management entity
        HolidayManagement savedHolidayManagement = holidayManagementRepository.save(holidayManagement);

        // Map the saved entity back to a response DTO
        return holidayManagementMapper.mapToDto(savedHolidayManagement);
    }

    public List<HolidayManagementResponse> getAllHolidayManagements(UUID tenantId) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        // Retrieve all holiday management entities
        List<HolidayManagement> holidayManagements = holidayManagementRepository.findAll();

        // Map the entities to response DTOs
        return holidayManagements.stream()
                .map(holidayManagementMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public HolidayManagementResponse getHolidayManagementById(UUID tenantId, UUID id) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        // Find the holiday management entity by ID
        HolidayManagement holidayManagement = holidayManagementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HolidayManagement not found with ID: " + id));

        // Map the entity to a response DTO
        return holidayManagementMapper.mapToDto(holidayManagement);
    }

    public HolidayManagementResponse updateHolidayManagement(UUID tenantId, UUID id, HolidayManagementRequest holidayManagementRequest) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        // Find the holiday management entity by ID
        HolidayManagement holidayManagement = holidayManagementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HolidayManagement not found with ID: " + id));

        // Update the holiday management entity with data from the request
        holidayManagementMapper.updateHolidayManagement(holidayManagement, holidayManagementRequest);

        // Save the updated holiday management entity
        HolidayManagement updatedHolidayManagement = holidayManagementRepository.save(holidayManagement);

        // Map the updated entity back to a response DTO
        return holidayManagementMapper.mapToDto(updatedHolidayManagement);
    }

    public void deleteHolidayManagement(UUID tenantId, UUID id) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        // Find the holiday management entity by ID
        HolidayManagement holidayManagement = holidayManagementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HolidayManagement not found with ID: " + id));

        // Delete the holiday management entity
        holidayManagementRepository.delete(holidayManagement);
    }
}

