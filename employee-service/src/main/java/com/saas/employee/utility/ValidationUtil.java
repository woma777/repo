package com.saas.employee.utility;

import com.saas.employee.client.AuthServiceClient;
import com.saas.employee.client.RecruitmentServiceClient;
import com.saas.employee.dto.clientDto.*;
import com.saas.employee.exception.ResourceNotFoundException;
import com.saas.employee.model.*;
import com.saas.employee.repository.*;
import com.saas.employee.client.OrganizationServiceClient;
import com.saas.employee.dto.clientDto.*;
import com.saas.employee.model.*;
import com.saas.employee.repository.*;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ValidationUtil {

    private final OrganizationServiceClient organizationServiceClient;
    private final RecruitmentServiceClient recruitmentServiceClient;
    private final AuthServiceClient authServiceClient;
    private final EmployeeRepository employeeRepository;
    private final TitleNameRepository titleNameRepository;
    private final LanguageNameRepository languageNameRepository;
    private final DutyStationRepository dutyStationRepository;
    private final CountryRepository countryRepository;

    public TenantDto getTenantById(UUID tenantId) {

        try {
            return organizationServiceClient.getTenantById(tenantId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Tenant not found with id '" + tenantId + "'");
        }
    }

    public Employee getEmployeeById(UUID tenantId,
                                    UUID id) {

        return employeeRepository.findById(id)
                .filter(emp -> emp.getTenantId ().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + id + "'"));

    }

    public Employee getEmployeeByEmployeeId(UUID tenantId,
                                            String employeeId) {

        return employeeRepository.findByTenantIdAndEmployeeId(tenantId, employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with employee id '" + employeeId + "'"));
    }

    public LocationDto getLocationById(UUID tenantId,
                                       UUID locationId) {

        try {
            return organizationServiceClient.getLocationById(locationId, tenantId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Location not found with id '" + locationId + "'");
        }
    }

    public EducationLevelDto getEducationLevelById(UUID tenantId,
                                                   UUID educationLevelId) {

        try {
            return organizationServiceClient.getEducationLevelById(educationLevelId, tenantId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException(
                    "Education level not found with id '" + educationLevelId + "'");
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

    public PayGradeDto getPayGradeById(UUID tenantId,
                                       UUID jobId,
                                       UUID payGradeId) {
        JobDto job;
        try {
            job = organizationServiceClient.getJobById(jobId, tenantId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Job not found with id '" + jobId + "'");
        }
        try {
            List<PayGradeDto> payGrades = organizationServiceClient
                    .getPayGradesByJobGradeId(job.getJobGradeId(), tenantId);
            PayGradeDto payGrade = organizationServiceClient.getPayGradeById(payGradeId, tenantId);
            if (payGrades.contains(payGrade)) {
                return payGrade;
            }
            throw new ResourceNotFoundException("Pay grade not found in the specified job");
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Pay grade not found with id '" + payGradeId + "'");
        }
    }

    public TitleName getTitleNameById(UUID tenantId,
                                      UUID titleNameId) {

        return titleNameRepository.findById(titleNameId)
                .filter(title -> title.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Title name not found with id '" + titleNameId + "'"));
    }

    public LanguageName getLanguageNameById(UUID tenantId,
                                            UUID languageId) {

        return languageNameRepository.findById(languageId)
                .filter(language -> language.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Language not found with id '" + languageId + "'"));
    }

    public RecruitmentDto getRecruitmentByVacancyNumber(UUID tenantId,
                                                        String vacancyNumber) {
        String recruitmentMode = "INTERNAL";
        List<RecruitmentDto> recruitments = recruitmentServiceClient
                .getRecruitmentsByMode(tenantId, recruitmentMode);
        for (RecruitmentDto recruitment : recruitments) {
            if (recruitment.getVacancyNumber().equals(vacancyNumber)) {
                if (recruitment.getRecruitmentStatus().equals("Approved")) {
                    return recruitment;
                }
                throw new IllegalStateException(
                        "Recruitment with vacancy number '" + vacancyNumber +
                                "' is found but not approved.");
            }
        }
        throw new ResourceNotFoundException(
                "Recruitment not found with vacancy number '" + vacancyNumber +
                        "' in the list of internal recruitments.");
    }

    public UserDto getUserByUsername(UUID tenantId,
                                     String username) {

        try {
            return authServiceClient.getUserByUsername(tenantId, username);
        } catch (FeignException.NotFound e) {
            return null;
        }
    }

    public DutyStation getDutyStationById(UUID tenantId,
                                          UUID stationId) {

        return dutyStationRepository.findById(stationId)
                .filter(ds -> ds.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Duty station not found with id '" + stationId + "'"));
    }

    public Country getCountryById(UUID tenantId,
                                  UUID countryId) {

        return countryRepository.findByTenantIdAndId(tenantId, countryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Country not found with id '" + countryId + "'"));
    }
}
