package com.saas.leave.mapper;

import com.saas.leave.client.OrganizationServiceClient;
import com.saas.leave.dto.request.BudgetYearRequest;
import com.saas.leave.dto.response.BudgetYearResponse;
import com.saas.leave.model.BudgetYear;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BudgetYearMapper {

    private final OrganizationServiceClient organizationServiceClient;

    /**
     * Maps a BudgetYearRequest to a BudgetYear entity.
     *
     * @param request the BudgetYearRequest to map
     * @return the mapped BudgetYear entity
     */
    public BudgetYear mapToEntity(BudgetYearRequest request) {

        BudgetYear budgetYear = new BudgetYear();
        budgetYear.setBudgetYear(request.getBudgetYear());
        budgetYear.setDescription(request.getDescription());
        budgetYear.setActive(request.isActive());
        return budgetYear;
    }

    /**
     * Maps a BudgetYear entity to a BudgetYearResponse.
     *
     * @param entity the BudgetYear entity to map
     * @return the mapped BudgetYearResponse
     */
    public BudgetYearResponse mapToDto(BudgetYear entity) {
        BudgetYearResponse response = new BudgetYearResponse();
        response.setId(entity.getId());
        response.setBudgetYear(entity.getBudgetYear());
        response.setDescription(entity.getDescription());
        response.setActive(entity.isActive());
        response.setTenantId(entity.getTenantId());
        return response;
    }

    /**
     * Updates an existing BudgetYear entity with data from a BudgetYearRequest.
     *
     * @param budgetYear the existing BudgetYear entity to update
     * @param request the BudgetYearRequest containing updated data
     */
    public void updateBudgetYear(BudgetYear budgetYear, BudgetYearRequest request) {
    if (request.getBudgetYear()!=null) {
        budgetYear.setBudgetYear(request.getBudgetYear());
    }
    if(budgetYear.getDescription()!=null) {
        budgetYear.setDescription(request.getDescription());
    }

        budgetYear.setActive(request.isActive());

    }
}