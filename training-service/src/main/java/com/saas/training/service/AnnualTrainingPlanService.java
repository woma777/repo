package com.saas.training.service;

import com.saas.training.dto.clientDto.TenantDto;
import com.saas.training.dto.request.AnnualTrainingPlanRequest;
import com.saas.training.dto.response.AnnualTrainingPlanResponse;
import com.saas.training.exception.ResourceExistsException;
import com.saas.training.exception.ResourceNotFoundException;
import com.saas.training.mapper.AnnualTrainingPlanMapper;
import com.saas.training.repository.AnnualTrainingPlanRepository;
import com.saas.training.utility.ValidationUtil;
import com.saas.training.model.AnnualTrainingPlan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnnualTrainingPlanService {

    private final AnnualTrainingPlanRepository annualTrainingPlanRepository;
    private final AnnualTrainingPlanMapper annualTrainingPlanMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public AnnualTrainingPlanResponse addAnnualTrainingPlan(UUID tenantId,
                                                            AnnualTrainingPlanRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        if (annualTrainingPlanRepository
                .existsByTenantIdAndDepartmentIdAndBudgetYearIdAndTrainingCourseId(
                tenant.getId(), request.getDepartmentId(), request.getBudgetYearId(),
                        request.getTrainingCourseId())) {
            throw new ResourceExistsException(
                    "Training plan for course with id '" + request.getTrainingCourseId() + "' already exists");
        }
        AnnualTrainingPlan annualTrainingPlan = annualTrainingPlanMapper
                .mapToEntity(tenant, request);
        annualTrainingPlan = annualTrainingPlanRepository.save(annualTrainingPlan);
        return annualTrainingPlanMapper.mapToDto(annualTrainingPlan);
    }

    public List<AnnualTrainingPlanResponse> getAllAnnualTrainingPlans(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List< AnnualTrainingPlan> annualTrainingPlans = annualTrainingPlanRepository.findAll();
        return annualTrainingPlans.stream()
                .filter(trainingPlan -> trainingPlan.getTenantId().equals(tenant.getId()))
                .map(annualTrainingPlanMapper::mapToDto)
                .toList();
    }

    public AnnualTrainingPlanResponse getAnnualTrainingPlanById(UUID tenantId,
                                                                UUID trainingPlanId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        AnnualTrainingPlan annualTrainingPlan = getTrainingPlanById(tenant.getId(), trainingPlanId);
        return annualTrainingPlanMapper.mapToDto(annualTrainingPlan);
    }

    public List<AnnualTrainingPlanResponse> getAnnualTrainingPlansByDepartmentId(UUID tenantId,
                                                                                 UUID departmentId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<AnnualTrainingPlan> annualTrainingPlans = annualTrainingPlanRepository.findAll();
        return annualTrainingPlans.stream()
                .filter(trainingPlan -> trainingPlan.getTenantId().equals(tenant.getId()))
                .filter(trainingPlan -> trainingPlan.getDepartmentId().equals(departmentId))
                .map(annualTrainingPlanMapper::mapToDto)
                .toList();
    }

    @Transactional
    public AnnualTrainingPlanResponse updateAnnualTrainingPlan(UUID tenantId,
                                                               UUID trainingPlanId,
                                                               AnnualTrainingPlanRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        AnnualTrainingPlan annualTrainingPlan = getTrainingPlanById(tenant.getId(), trainingPlanId);
        if (annualTrainingPlanRepository
                .existsByTenantIdAndDepartmentIdAndBudgetYearIdAndTrainingCourseIdAndIdNot(
                        tenant.getId(), request.getDepartmentId(), request.getBudgetYearId(),
                        request.getTrainingCourseId(), annualTrainingPlan.getId())) {
            throw new ResourceExistsException(
                    "Training plan for course with id '" + request.getTrainingCourseId() + "' already exists");
        }
        annualTrainingPlan = annualTrainingPlanMapper.updateEntity(tenant, annualTrainingPlan, request);
        annualTrainingPlan = annualTrainingPlanRepository.save(annualTrainingPlan);
        return annualTrainingPlanMapper.mapToDto(annualTrainingPlan);
    }

    @Transactional
    public void deleteAnnualTrainingPlan(UUID tenantId,
                                         UUID trainingPlanId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        AnnualTrainingPlan annualTrainingPlan = getTrainingPlanById(tenant.getId(), trainingPlanId);
        annualTrainingPlanRepository.delete(annualTrainingPlan);
    }

    private AnnualTrainingPlan getTrainingPlanById(UUID tenantId, UUID trainingPlanId) {

        return annualTrainingPlanRepository.findById(trainingPlanId)
                .filter(trainingPlan -> trainingPlan.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Annual training plan not found with id '" + trainingPlanId + "'"));
    }
}