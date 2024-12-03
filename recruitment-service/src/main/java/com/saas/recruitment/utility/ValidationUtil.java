package com.saas.recruitment.utility;

import com.saas.recruitment.dto.clientDto.*;
import com.saas.recruitment.exception.ResourceNotFoundException;
import com.saas.recruitment.model.Applicant;
import com.saas.recruitment.model.MediaType;
import com.saas.recruitment.model.Recruitment;
import com.saas.recruitment.repository.ApplicantRepository;
import com.saas.recruitment.repository.MediaTypeRepository;
import com.saas.recruitment.repository.RecruitmentRepository;
import com.saas.recruitment.client.EmployeeServiceClient;
import com.saas.recruitment.client.OrganizationServiceClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ValidationUtil {

    private final OrganizationServiceClient organizationServiceClient;
    private final EmployeeServiceClient employeeServiceClient;
    private final RecruitmentRepository recruitmentRepository;
    private final MediaTypeRepository mediaTypeRepository;
    private final ApplicantRepository applicantRepository;

    public TenantDto getTenantById(UUID tenantId) {

        try {
            return organizationServiceClient.getTenantById(tenantId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Tenant not found with id '" + tenantId + "'");
        }
    }

    public EmployeeDto getEmployeeByEmployeeId(UUID tenantId,
                                               String employeeId) {

        try {
            return employeeServiceClient.getEmployeeByEmployeeId(tenantId, employeeId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Employee not found with employee id '" + employeeId + "'");
        }
    }

    public CountryDto getCountryById(UUID tenantId,
                                     UUID countryId) {

        try {
            return employeeServiceClient.getCountryById(tenantId, countryId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Country not found with id '" + countryId + "'");
        }
    }

    public LanguageNameDto getLanguageNameById(UUID tenantId,
                                               UUID languageId) {

        try {
            return employeeServiceClient.getLanguageName(tenantId, languageId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Language name not found with id '" + languageId + "'");
        }
    }

    public EducationLevelDto getEducationLevelById(UUID tenantId,
                                                   UUID educationLevelId) {

        try {
            return organizationServiceClient.getEducationLevelById(educationLevelId, tenantId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Education level not found with id '" + educationLevelId + "'");
        }
    }

    public FieldOfStudyDto getFieldOfStudyById(UUID tenantId,
                                               UUID fieldOfStudyId) {

        try {
            return organizationServiceClient.getFieldOfStudyById(fieldOfStudyId, tenantId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException(
                    "Field of study not found with id '" + fieldOfStudyId + "'");
        }
    }

    public DepartmentDto getDepartmentById(UUID tenantId, UUID departmentId) {

        try {
            return organizationServiceClient.getDepartmentById(departmentId, tenantId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Department not found with id '" + departmentId + "'");
        }
    }

    public JobDto getJobById(UUID tenantId,
                             UUID departmentId,
                             UUID jobId) {

        try {
            List<JobDto> jobs = organizationServiceClient
                    .getAllJobsByTenantAndDepartment(tenantId, departmentId);
            JobDto job = organizationServiceClient.getJobById(jobId, tenantId);
            if (jobs.contains(job)) {
                return job;
            }
            throw new ResourceNotFoundException("Job not associated with the specified department");
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Job not found with id '" + jobId + "'");
        }
    }

    public Recruitment getRecruitmentById(UUID tenantId, UUID recruitmentId) {

        return recruitmentRepository.findById(recruitmentId)
                .filter(rec -> rec.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recruitment not found with id '" + recruitmentId + "'"));
    }

    public MediaType getMediaTypeById(UUID tenantId, UUID mediaTypeId) {

        return mediaTypeRepository.findById(mediaTypeId)
                .filter(mt -> mt.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Media type not found with id '" + mediaTypeId + "'"));
    }

    public Recruitment getRecruitmentByVacancyNumber(UUID tenantId, String vacancyNumber) {

        return recruitmentRepository.findByVacancyNumber(vacancyNumber)
                .filter(adv -> adv.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recruitment not found with vacancy number '" + vacancyNumber + "'"));
    }

    public Applicant getApplicantById(UUID tenantId, UUID applicantId) {

        return applicantRepository.findById(applicantId)
                .filter(app -> app.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "'"));
    }
}
