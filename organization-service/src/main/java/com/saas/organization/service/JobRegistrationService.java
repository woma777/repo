package com.saas.organization.service;

import com.saas.organization.dto.requestDto.JobRegistrationRequest;
import com.saas.organization.dto.responseDto.JobRegistrationResponse;
import com.saas.organization.model.Department;
import com.saas.organization.model.JobRegistration;
import com.saas.organization.model.Tenant;
import com.saas.organization.exception.ResourceExistsException;
import com.saas.organization.exception.ResourceNotFoundException;
import com.saas.organization.mapper.JobRegistrationMapper;
import com.saas.organization.repository.DepartmentRepository;
import com.saas.organization.repository.JobRegistrationRepository;
import com.saas.organization.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobRegistrationService {

    private final JobRegistrationRepository jobRegistrationRepository;
    private final JobRegistrationMapper jobRegistrationMapper;
    private final TenantRepository tenantRepository;
    private final DepartmentRepository departmentRepository;

    public JobRegistrationResponse registerJob(UUID tenantId, JobRegistrationRequest jobRegistrationRequest) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId + " "));

        if (jobRegistrationRepository.existsByJobTitleAndTenantId(
                jobRegistrationRequest.getJobTitle(), tenant.getId())) {
            throw new ResourceExistsException("Job with Title " +
                    jobRegistrationRequest.getJobTitle() + " already exists");
        }

        if (jobRegistrationRepository.existsByJobCode(jobRegistrationRequest.getJobCode())) {
            throw new ResourceExistsException("Job with Code " +
                    jobRegistrationRequest.getJobCode() + " already exists");
        }

        JobRegistration jobRegistration = jobRegistrationMapper
                .mapToEntity(jobRegistrationRequest);
        jobRegistration.setTenant(tenant);
        jobRegistration = jobRegistrationRepository.save(jobRegistration);

        return jobRegistrationMapper.mapToDto(jobRegistration);
    }

    public List<JobRegistrationResponse> getAllJobs(UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId + " "));

        List<JobRegistration> jobRegistrations = jobRegistrationRepository.findAll();
        return jobRegistrations.stream()
                .filter(job -> job.getTenant().getId().equals(tenant.getId()))
                .map(jobRegistrationMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<JobRegistrationResponse> getAllJobsByDepartment(UUID tenantId, UUID departmentId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId));

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with id: " + departmentId));

        List<JobRegistration> jobRegistrations = jobRegistrationRepository
                .findByTenantAndDepartment(tenant, department);

        return jobRegistrations.stream()
                .map(jobRegistrationMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public JobRegistrationResponse getJobById(UUID id, UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId));

        JobRegistration jobRegistration = jobRegistrationRepository.findById(id)
                .filter(job -> job.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Job not found with id: " + id + " for the specified tenant"));

        return jobRegistrationMapper.mapToDto(jobRegistration);
    }

    public JobRegistrationResponse updateJobs(UUID id, UUID tenantId, JobRegistrationRequest jobRegistrationRequest) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId));

        JobRegistration jobRegistration = jobRegistrationRepository.findById(id)
                .filter(job -> job.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Job not found with id: " + id + " for the specified tenant"));

        jobRegistration = jobRegistrationMapper.updateJobRegistration(jobRegistration, jobRegistrationRequest);
        jobRegistration = jobRegistrationRepository.save(jobRegistration);

        return jobRegistrationMapper.mapToDto(jobRegistration);
    }

    public void deleteJob(UUID id, UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId));

        JobRegistration jobRegistration = jobRegistrationRepository.findById(id)
                .filter(job -> job.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Job not found with id: " + id + " for the specified tenant"));

        jobRegistrationRepository.delete(jobRegistration);
    }
}