package com.saas.training.utility;

import com.saas.training.dto.clientDto.*;
import com.saas.training.model.*;
import com.saas.training.repository.*;
import com.saas.training.exception.ResourceNotFoundException;
import com.saas.training.client.EmployeeServiceClient;
import com.saas.training.client.LeaveServiceClient;
import com.saas.training.client.OrganizationServiceClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ValidationUtil {

    private final OrganizationServiceClient organizationServiceClient;
    private final EmployeeServiceClient employeeServiceClient;
    private final LeaveServiceClient leaveServiceClient;
    private final TrainingInstitutionRepository trainingInstitutionRepository;
    private final TrainingCourseCategoryRepository trainingCourseCategoryRepository;
    private final TrainingCourseRepository trainingCourseRepository;
    private final CheckedDocumentRepository checkedDocumentRepository;
    private final PreServiceTraineeRepository preServiceTraineeRepository;
    private final InternshipStudentRepository internshipStudentRepository;
    private final UniversityRepository universityRepository;
    private final PreServiceCourseTypeRepository preServiceCourseTypeRepository;
    private final PreServiceCourseRepository preServiceCourseRepository;
    private final TrainingRepository trainingRepository;

    public TenantDto getTenantById(UUID tenantId) {

        try {
            return organizationServiceClient.getTenantById(tenantId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Tenant not found with id '" + tenantId + "'");
        }
    }

    public EmployeeDto getEmployeeByName(UUID tenantId,
                                         String firstName,
                                         String middleName,
                                         String lastName) {

        try {
            return employeeServiceClient
                    .getEmployeeByName(tenantId, firstName, middleName, lastName);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException(
                    "Employee not found with name '" + firstName + " " + middleName + " " + lastName + "'");
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

    public DepartmentDto getDepartmentById(UUID tenantId,
                                           UUID departmentId) {

        try {
            return organizationServiceClient.getDepartmentById(departmentId, tenantId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Department not found with id '" + departmentId + "'");
        }
    }

    public QualificationDto getQualificationById(UUID tenantId,
                                                 UUID qualificationId) {

        try {
            return organizationServiceClient.getQualificationById(qualificationId, tenantId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Qualification not found with id '" + qualificationId + "'");
        }
    }

    public BudgetYearDto getBudgetYearById(UUID tenantId,
                                           UUID budgetYearId) {

        try {
            return leaveServiceClient.getBudgetYearById(tenantId, budgetYearId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Budget year not found with id '" + budgetYearId + "'");
        }
    }

    public TrainingInstitution getInstitutionById(UUID tenantId,
                                                  UUID institutionId) {

        return trainingInstitutionRepository.findById(institutionId)
                .filter(institution -> institution.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training institution not found with id '" + institutionId + "'"));
    }

    public TrainingCourseCategory getCategoryById(UUID tenantId,
                                                  UUID categoryId) {

        return trainingCourseCategoryRepository.findById(categoryId)
                .filter(category -> category.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course category not found with id '" + categoryId + "'"));
    }

    public TrainingCourse getTrainingCourseById(UUID tenantId,
                                                UUID courseId) {

        return trainingCourseRepository.findById(courseId)
                .filter(course -> course.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training course not found with id '" + courseId + "'"));
    }

    public CheckedDocument getCheckedDocumentById(UUID tenantId,
                                                  UUID documentId) {

        return checkedDocumentRepository.findById(documentId)
                .filter(title -> title.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Document not found with id '" + documentId + "'"));
    }

    public PreServiceTrainee getTraineeById(UUID tenantId,
                                            UUID traineeId) {

        return preServiceTraineeRepository.findById(traineeId)
                .filter(trainee -> trainee.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pre Service Trainee not found with id '" + traineeId + "'"));
    }

    public InternshipStudent getStudentById(UUID tenantId,
                                            UUID studentId) {

        return internshipStudentRepository.findById(studentId)
                .filter(intern -> intern.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Internship student not found with id '" + studentId + "'"));
    }

    public University getUniversityById(UUID tenantId,
                                        UUID universityId) {

        return universityRepository.findById(universityId)
                .filter(univ -> univ.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "University not found with id '" + universityId + "'"));
    }

    public PreServiceCourseType getCourseTypeById(UUID tenantId,
                                                  UUID courseTypeId) {

        return preServiceCourseTypeRepository.findById(courseTypeId)
                .filter(type -> type.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course type not found with id '" + courseTypeId + "'"));
    }

    public PreServiceCourse getPreServiceCourseById(UUID tenantId,
                                                    UUID courseId) {

        return preServiceCourseRepository.findById(courseId)
                .filter(course -> course.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pre Service Course Not Found with id '" + courseId + "'"));
    }

    public Training getTrainingById(UUID tenantId,
                                    UUID trainingId) {

        return trainingRepository.findById(trainingId)
                .filter(train -> train.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training not found with id '" + trainingId + "'"));
    }
}
