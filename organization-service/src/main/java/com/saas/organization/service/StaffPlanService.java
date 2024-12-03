package com.saas.organization.service;

import com.saas.organization.dto.requestDto.StaffPlanRequest;
import com.saas.organization.dto.responseDto.StaffPlanResponse;
import com.saas.organization.model.Department;
import com.saas.organization.model.StaffPlan;
import com.saas.organization.model.Tenant;
import com.saas.organization.exception.ResourceExistsException;
import com.saas.organization.exception.ResourceNotFoundException;
import com.saas.organization.mapper.StaffPlanMapper;
import com.saas.organization.repository.DepartmentRepository;
import com.saas.organization.repository.JobRegistrationRepository;
import com.saas.organization.repository.StaffPlanRepository;
import com.saas.organization.repository.TenantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StaffPlanService {

    private final StaffPlanRepository staffPlanRepository;
    private final StaffPlanMapper staffPlanMapper;
    private final TenantRepository tenantRepository;
    private final DepartmentRepository departmentRepository;
    private final JobRegistrationRepository jobRegistrationRepository;

    private static final Logger logger = LoggerFactory.getLogger(StaffPlanService.class);

    public StaffPlanResponse createStaffPlan(UUID tenantId, StaffPlanRequest staffPlanRequest) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> {
                    logger.error("Tenant not found with id: " + tenantId);
                    return new ResourceNotFoundException("Tenant not found with id: " + tenantId);
                });

        Optional.ofNullable(staffPlanRequest.getJobRegistrationId()).ifPresent(jobRegistrationId ->
                jobRegistrationRepository.findById(jobRegistrationId)
                        .orElseThrow(() -> new EntityNotFoundException("Job Registration not found with ID: " +
                                jobRegistrationId))
        );

        Optional.ofNullable(staffPlanRequest.getDepartmentId()).ifPresent(departmentId ->
                departmentRepository.findById(departmentId)
                        .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " +
                                departmentId))
        );

        if (staffPlanRepository.existsByTenantId(tenant.getId())) {
            throw new ResourceExistsException("Staff Plan with tenant " + tenant.getId() + " already exists");
        }

        StaffPlan staffPlan = staffPlanMapper.mapToEntity(staffPlanRequest);
        staffPlan.setTenant(tenant);
        staffPlan = staffPlanRepository.save(staffPlan);
        return staffPlanMapper.mapToDto(staffPlan);
    }

    public List<StaffPlanResponse> getAllStaffPlans(UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> {
                    logger.error("Tenant not found with id: " + tenantId);
                    return new ResourceNotFoundException("Tenant not found with id: " + tenantId);
                });

        List<StaffPlan> staffPlans = staffPlanRepository.findAll();
        return staffPlans.stream()
                .filter(staff -> staff.getTenant().getId().equals(tenant.getId()))
                .map(staffPlanMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public StaffPlanResponse getStaffPlanById(UUID tenantId,
                                              UUID staffPlanId) {

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        StaffPlan staffPlan = staffPlanRepository.findById(staffPlanId)
                .filter(sp -> sp.getTenant().equals(tenant))
                .orElseThrow(() -> new ResourceNotFoundException("Staff Plan not found with ID: " + staffPlanId));

        return staffPlanMapper.mapToDto(staffPlan);
    }

    public List<StaffPlanResponse> getStaffPlanByDepartmentId(UUID departmentId, UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));

        List<StaffPlan> staffPlans = staffPlanRepository.findByDepartmentId(departmentId);

        return staffPlans.stream()
                .filter(staff -> staff.getTenant().getId().equals(tenant.getId()))
                .map(staffPlanMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public StaffPlanResponse updateStaffPlan(UUID id, UUID tenantId, StaffPlanRequest staffPlanRequest) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> {
                    logger.error("Tenant not found with id: " + tenantId);
                    return new ResourceNotFoundException("Tenant not found with id: " + tenantId);
                });

        StaffPlan staffPlan = staffPlanRepository.findById(id)
                .filter(staff -> staff.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> {
                    logger.error("Staff-plan not found with id: " + id + " for the specified tenant");
                    return new ResourceNotFoundException("Staff-plan not found with id: " + id +
                            " for the specified tenant");
                });

        staffPlan = staffPlanMapper.updateStaffPlan(staffPlan, staffPlanRequest);
        staffPlan = staffPlanRepository.save(staffPlan);
        return staffPlanMapper.mapToDto(staffPlan);
    }

    public void deleteStaffPlan(UUID id, UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> {
                    logger.error("Tenant not found with id: " + tenantId);
                    return new ResourceNotFoundException("Tenant not found with id: " + tenantId);
                });

        StaffPlan staffPlan = staffPlanRepository.findById(id)
                .filter(staff -> staff.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> {
                    logger.error("Staff-plan not found with id: " + id + " for the specified tenant");
                    return new ResourceNotFoundException("Staff-plan not found with id: " + id + " for the specified tenant");
                });

        staffPlanRepository.delete(staffPlan);
    }
}