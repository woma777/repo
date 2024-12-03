package com.saas.organization.service;

import com.saas.organization.dto.requestDto.JobCategoryRequest;
import com.saas.organization.dto.responseDto.JobCategoryResponse;
import com.saas.organization.model.JobCategory;
import com.saas.organization.model.JobRegistration;
import com.saas.organization.model.Tenant;
import com.saas.organization.exception.ResourceExistsException;
import com.saas.organization.exception.ResourceNotFoundException;
import com.saas.organization.mapper.JobCategoryMapper;
import com.saas.organization.repository.JobCategoryRepository;
import com.saas.organization.repository.TenantRepository;
import com.saas.organization.repository.JobRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobCategoryService {

    private final JobCategoryRepository jobCategoryRepository;
    private final JobCategoryMapper jobCategoryMapper;
    private final TenantRepository tenantRepository;
    private final JobRegistrationRepository jobRegistrationRepository;

    public JobCategoryResponse createJobCategory(UUID tenantId, JobCategoryRequest jobCategoryRequest) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId + " "));

        if (jobCategoryRepository.existsByJobCategoryNameAndTenantId(
                jobCategoryRequest.getJobCategoryName(), tenant.getId())) {
            throw new ResourceExistsException("Job Category with Name " +
                    jobCategoryRequest.getJobCategoryName() + " already exists");
        }

        JobCategory jobCategory = jobCategoryMapper.mapToEntity(jobCategoryRequest);
        jobCategory.setTenant(tenant);
        jobCategory = jobCategoryRepository.save(jobCategory);
        return jobCategoryMapper.mapToDto(jobCategory);
    }

    public List<JobCategoryResponse> getAllJobCategories(UUID tenantId) {
        Tenant tenant = getTenantById(tenantId);

        List<JobCategory> jobCategories = jobCategoryRepository.findAll();
        return jobCategories.stream()
                .filter(jobCategory -> jobCategory.getTenant().getId().equals(tenant.getId()))
                .map(jobCategoryMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public JobCategoryResponse getJobCategoryById(UUID id, UUID tenantId) {
        Tenant tenant = getTenantById(tenantId);

        JobCategory jobCategory = jobCategoryRepository.findById(id)
                .filter(jc -> jc.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobCategory not found with id: " + id + " for the specified tenant"));

        return jobCategoryMapper.mapToDto(jobCategory);
    }

    public JobCategoryResponse updateJobCategory(UUID id, UUID tenantId, JobCategoryRequest jobCategoryRequest) {
        Tenant tenant = getTenantById(tenantId);

        JobCategory jobCategory = jobCategoryRepository.findById(id)
                .filter(jc -> jc.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobCategory not found with id: " + id + " for the specified tenant"));

        jobCategory = jobCategoryMapper.updateJobCategory(jobCategory, jobCategoryRequest);
        jobCategory = jobCategoryRepository.save(jobCategory);
        return jobCategoryMapper.mapToDto(jobCategory);
    }

    public void deleteJobCategory(UUID id, UUID tenantId) {
        Tenant tenant = getTenantById(tenantId);

        JobCategory jobCategory = jobCategoryRepository.findById(id)
                .filter(jc -> jc.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobCategory not found with id: " + id + " for the specified tenant"));

        jobCategoryRepository.delete(jobCategory);
    }

    private Tenant getTenantById(UUID tenantId) {
        return tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));
    }

    private JobRegistration getJobRegistrationById(UUID jobRegistrationId) {
        return jobRegistrationRepository.findById(jobRegistrationId)
                .orElseThrow(() -> new ResourceNotFoundException("JobRegistration not found with id: " + jobRegistrationId));
    }
}