package com.saas.training.service;

import com.saas.training.dto.clientDto.EmployeeDto;
import com.saas.training.dto.clientDto.TenantDto;
import com.saas.training.dto.request.EducationOpportunityRequest;
import com.saas.training.dto.response.EducationOpportunityResponse;
import com.saas.training.exception.ResourceExistsException;
import com.saas.training.exception.ResourceNotFoundException;
import com.saas.training.repository.EducationOpportunityRepository;
import com.saas.training.utility.ValidationUtil;
import com.saas.training.model.EducationOpportunity;
import com.saas.training.mapper.EducationOpportunityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EducationOpportunityService {

    private final EducationOpportunityRepository educationOpportunityRepository;
    private final EducationOpportunityMapper educationOpportunityMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public EducationOpportunityResponse createEducationOpportunity(UUID tenantId,
                                                                   EducationOpportunityRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        EmployeeDto employee = validationUtil
                .getEmployeeByEmployeeId(tenant.getId(), request.getEmployeeId());
        if (educationOpportunityRepository.existsByTenantIdAndBudgetYearIdAndEmployeeId(
                tenant.getId(), request.getBudgetYearId(), employee.getId())) {
            throw new ResourceExistsException(
                    "Employee with id '" + employee.getEmployeeId() + "' already been granted an education" +
                            " opportunity for the budget year with id '" + request.getBudgetYearId() + "'");
        }
        EducationOpportunity educationOpportunity = educationOpportunityMapper
                .mapToEntity(tenant, employee, request);
        educationOpportunity = educationOpportunityRepository.save(educationOpportunity);
        return educationOpportunityMapper.mapToDto(educationOpportunity);
    }

    public List<EducationOpportunityResponse> getAllEducationOpportunities(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<EducationOpportunity> educationOpportunities = educationOpportunityRepository.findAll();
        return educationOpportunities.stream()
                .filter(ed -> ed.getTenantId().equals(tenant.getId()))
                .map(educationOpportunityMapper::mapToDto)
                .toList();
    }

    public EducationOpportunityResponse getEducationOpportunityById(UUID tenantId,
                                                                    UUID educationId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        EducationOpportunity educationOpportunity = getOpportunityById(tenant.getId(), educationId);
        return educationOpportunityMapper.mapToDto(educationOpportunity);
    }

    public List<EducationOpportunityResponse> getEducationOpportunityByEmployeeId(UUID tenantId,
                                                                                  UUID employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<EducationOpportunity> educationOpportunities = educationOpportunityRepository
                .findByTenantIdAndEmployeeId(tenant.getId(), employeeId);
        return educationOpportunities.stream()
                .filter(ed -> ed.getTenantId().equals(tenant.getId()))
                .map(educationOpportunityMapper::mapToDto)
                .toList();
    }

    @Transactional
    public EducationOpportunityResponse updateEducationOpportunity(UUID tenantId,
                                                                   UUID educationId,
                                                                   EducationOpportunityRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        EmployeeDto employee = validationUtil
                .getEmployeeByEmployeeId(tenant.getId(), request.getEmployeeId());
        EducationOpportunity educationOpportunity = getOpportunityById(tenant.getId(), educationId);
        if (educationOpportunityRepository.existsByTenantIdAndBudgetYearIdAndEmployeeIdAndIdNot(
                tenant.getId(), request.getBudgetYearId(), employee.getId(), educationOpportunity.getId())) {
            throw new ResourceExistsException(
                    "Employee with id '" + employee.getEmployeeId() + "' already been granted an education" +
                            " opportunity for the budget year with id '" + request.getBudgetYearId() + "'");
        }
        educationOpportunity = educationOpportunityMapper
                .updateEntity(tenant, employee,  educationOpportunity, request);
        educationOpportunity = educationOpportunityRepository.save(educationOpportunity);
        return educationOpportunityMapper.mapToDto(educationOpportunity);
    }

    @Transactional
    public void deleteEducationOpportunity(UUID tenantId,
                                           UUID educationId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        EducationOpportunity educationOpportunity = getOpportunityById(tenant.getId(), educationId);
        educationOpportunityRepository.delete(educationOpportunity);
    }

    public EducationOpportunity getOpportunityById(UUID tenantId, UUID opportunityId) {

        return educationOpportunityRepository.findById(opportunityId)
                .filter(ed -> ed.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Education opportunity not found with id '" + opportunityId + "'"));
    }
}