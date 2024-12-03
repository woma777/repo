package com.saas.auth.data;

import com.saas.auth.dto.clientDto.TenantDto;
import com.saas.auth.enums.TrainingServiceResourceName;
import com.saas.auth.exception.ResourceExistsException;
import com.saas.auth.model.Resource;
import com.saas.auth.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class TrainingServiceResource {

    private final ResourceRepository resourceRepository;

    public void storeEndpoints(TenantDto tenant) {

        List<Resource> resources = new ArrayList<>();

        String defaultRole =  "default_role";

        /* Annual Training Plan */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_ANNUAL_TRAINING_PLAN.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_ANNUAL_TRAINING_PLANS.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ANNUAL_TRAINING_PLANS_BY_DEPARTMENT_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ANNUAL_TRAINING_PLAN_BY_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_ANNUAL_TRAINING_PLAN.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_ANNUAL_TRAINING_PLAN.getValue(),
                null, tenant));

        /* Training */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_TRAINING.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_TRAININGS.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_TRAININGS_BY_STATUS.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_TRAINING_BY_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_TRAINING.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.APPROVE_TRAINING.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_TRAINING.getValue(),
                null, tenant));

        /* Training Participant */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_TRAINING_PARTICIPANT.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_TRAINING_PARTICIPANTS.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_TRAINING_PARTICIPANT_BY_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_TRAINING_PARTICIPANT.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_TRAINING_PARTICIPANT.getValue(),
                null, tenant));

        /* Training Institution */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_INSTITUTION.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_INSTITUTIONS.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_INSTITUTION_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_INSTITUTION.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_INSTITUTION.getValue(),
                null, tenant));

        /* Training Course */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_TRAINING_COURSE.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_TRAINING_COURSES_BY_CATEGORY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_TRAINING_COURSE_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_TRAINING_COURSE.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_TRAINING_COURSE.getValue(),
                null, tenant));

        /* Training Course Category */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_TRAINING_COURSE_CATEGORY.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_TRAINING_COURSE_CATEGORIES.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_TRAINING_COURSE_CATEGORY_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_TRAINING_COURSE_CATEGORY.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_TRAINING_COURSE_CATEGORY.getValue(),
                null, tenant));

        /* Pre Service Trainee Result */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_PRE_SERVICE_TRAINEE_RESULT.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_TRAINEE_RESULTS_BY_COURSE_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_PRE_SERVICE_TRAINEE_RESULT_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_PRE_SERVICE_TRAINEE_RESULT.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_PRE_SERVICE_TRAINEE_RESULT.getValue(),
                null, tenant));

        /* Pre Service Trainee */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_PRE_SERVICE_TRAINEE.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_COURSES_TO_PRE_SERVICE_TRAINEE.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_PRE_SERVICE_TRAINEES.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_PRE_SERVICE_TRAINEES_BY_BUDGET_YEAR_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_PRE_SERVICE_TRAINEE_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.DOWNLOAD_PRE_SERVICE_TRAINEE_IMAGE.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_PRE_SERVICE_TRAINEE.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_PRE_SERVICE_TRAINEE.getValue(),
                null, tenant));

        /* Pre Service Trainee Checked Document */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_PRE_SERVICE_CHECKED_DOCUMENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_PRE_SERVICE_CHECKED_DOCUMENTS.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_PRE_SERVICE_CHECKED_DOCUMENTS_BY_TRAINEE_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_PRE_SERVICE_CHECKED_DOCUMENT_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_PRE_SERVICE_CHECKED_DOCUMENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.REMOVE_TRAINEE_CHECKED_DOCUMENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_PRE_SERVICE_CHECKED_DOCUMENT.getValue(),
                null, tenant));

        /* Pre Service Course Type */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_PRE_SERVICE_COURSE_TYPE.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_PRE_SERVICE_COURSE_TYPES.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_PRE_SERVICE_COURSE_TYPE_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_PRE_SERVICE_COURSE_TYPE.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_PRE_SERVICE_COURSE_TYPE.getValue(),
                null, tenant));

        /* Pre Service Course */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_PRE_SERVICE_COURSE.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_PRE_SERVICE_COURSES.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_PRE_SERVICE_COURSES_BY_TRAINEE_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_PRE_SERVICE_COURSES_BY_COURSE_TYPE_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_PRE_SERVICE_COURSE_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_PRE_SERVICE_COURSE.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.REMOVE_COURSE_BY_TRAINEE_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_PRE_SERVICE_COURSE.getValue(),
                null, tenant));

        /* University */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_UNIVERSITY.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_UNIVERSITIES.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_UNIVERSITY_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_UNIVERSITY.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_UNIVERSITY.getValue(),
                null, tenant));

        /* Internship Student */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_INTERNSHIP_STUDENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_INTERNSHIP_STUDENTS.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_INTERNSHIP_STUDENTS_BY_BUDGET_YEAR_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_INTERNSHIP_STUDENT_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_INTERNSHIP_STUDENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.ASSIGN_DEPARTMENT_TO_INTERNSHIP_STUDENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.ASSIGN_STATUS_TO_INTERNSHIP_STUDENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_INTERNSHIP_STUDENT.getValue(),
                null, tenant));

        /* Internship Payment */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_INTERNSHIP_PAYMENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_INTERNSHIP_PAYMENTS.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_INTERNSHIP_PAYMENT_BY_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_INTERNSHIP_PAYMENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_INTERNSHIP_PAYMENT.getValue(),
                null, tenant));

        /* Education Opportunity */
        resources.add(mapToEntity(TrainingServiceResourceName.ADD_EDUCATION_OPPORTUNITY.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_ALL_EDUCATION_OPPORTUNITIES.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_EDUCATION_OPPORTUNITIES_BY_EMPLOYEE_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.GET_EDUCATION_OPPORTUNITY_BY_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.UPDATE_EDUCATION_OPPORTUNITY.getValue(),
                null, tenant));
        resources.add(mapToEntity(TrainingServiceResourceName.DELETE_EDUCATION_OPPORTUNITY.getValue(),
                null, tenant));

        for (Resource resource : resources) {
            if (resourceRepository
                    .existsByTenantIdAndResourceName(tenant.getId(), resource.getResourceName())) {
                throw new ResourceExistsException(
                        "Resource already exists with name '" + resource.getResourceName() + "'");
            }
            resourceRepository.save(resource);
        }
    }

    private Resource mapToEntity(String requestName,
                                 String roleName,
                                 TenantDto tenant) {

        Resource resource = new Resource();
        resource.setResourceName(requestName);
        resource.setServiceName("training-service");
        resource.setTenantId(tenant.getId());
        resource.setDescription("");

        Set<String> roles = new HashSet<>();
        String adminRole = "admin";
        roles.add(adminRole);
        if (roleName != null) {
            roles.add(roleName);
        }
        resource.setRequiredRoles(roles);

        return resource;
    }
}
