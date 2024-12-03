package com.saas.leave.service;

import com.saas.leave.client.OrganizationServiceClient;
import com.saas.leave.dto.TenantDto;
import com.saas.leave.dto.request.BudgetYearRequest;
import com.saas.leave.dto.response.BudgetYearResponse;
import com.saas.leave.model.BudgetYear;
import com.saas.leave.exception.ResourceExistsException;
import com.saas.leave.exception.ResourceNotFoundException;
import com.saas.leave.mapper.BudgetYearMapper;
import com.saas.leave.repository.BudgetYearRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetYearService {

    private final BudgetYearRepository budgetYearRepository;
    private final BudgetYearMapper budgetYearMapper;
    private final OrganizationServiceClient organizationServiceClient;

    public BudgetYearResponse createBudgetYear(UUID tenantId, BudgetYearRequest budgetYearRequest) {
        // Validate the tenant ID
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }
        if (budgetYearRepository.existsByBudgetYearAndTenantId(budgetYearRequest.getBudgetYear(),tenant.getId())) {
            throw new ResourceExistsException("BudgetYear with Name " + budgetYearRequest.getBudgetYear() +  " already exists");
        }

        BudgetYear budgetYear = budgetYearMapper.mapToEntity(budgetYearRequest);
        budgetYear.setTenantId(tenant.getId());

        // Save the budget year entity
        BudgetYear savedBudgetYear = budgetYearRepository.save(budgetYear);

        // Map the saved entity back to a response DTO
        return budgetYearMapper.mapToDto(savedBudgetYear);
    }

    public List<BudgetYearResponse> getAllBudgetYears(UUID tenantId) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        // Retrieve all budget years
        List<BudgetYear> budgetYears = budgetYearRepository.findAll();

        // Map the entities to response DTOs
        return budgetYears.stream()
                .map(budgetYearMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public BudgetYearResponse getBudgetYearById(UUID tenantId, UUID id) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        // Find the budget year entity by ID
        BudgetYear budgetYear = budgetYearRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetYear not found with ID: " + id));

        // Map the entity to a response DTO
        return budgetYearMapper.mapToDto(budgetYear);
    }

    public BudgetYearResponse updateBudgetYear(UUID tenantId, UUID id, BudgetYearRequest budgetYearRequest) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        // Find the budget year entity by ID
        BudgetYear budgetYear = budgetYearRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetYear not found with ID: " + id));

        // Update the budget year entity with data from the request
        budgetYearMapper.updateBudgetYear(budgetYear, budgetYearRequest);

        // Save the updated budget year entity
        BudgetYear updatedBudgetYear = budgetYearRepository.save(budgetYear);

        // Map the updated entity back to a response DTO
        return budgetYearMapper.mapToDto(updatedBudgetYear);
    }

    public void deleteBudgetYear(UUID tenantId, UUID id) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        // Find the budget year entity by ID
        BudgetYear budgetYear = budgetYearRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetYear not found with ID: " + id));

        // Delete the budget year entity
        budgetYearRepository.delete(budgetYear);
    }


}
