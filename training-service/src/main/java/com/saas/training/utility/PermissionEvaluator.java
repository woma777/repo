package com.saas.training.utility;

import com.saas.training.enums.TrainingServiceResourceName;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PermissionEvaluator {

    private final PermissionUtil permissionUtil;

    /* Annual Training Plan Permissions */
    public void addAnnualTrainingPlanPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.ADD_ANNUAL_TRAINING_PLAN);
    }

    public void getAllAnnualTrainingPlansPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_ALL_ANNUAL_TRAINING_PLANS);
    }

    public void getAnnualTrainingPlansByDepartmentIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_ANNUAL_TRAINING_PLANS_BY_DEPARTMENT_ID);
    }

    public void getAnnualTrainingPlanByIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_ANNUAL_TRAINING_PLAN_BY_ID);
    }

    public void updateAnnualTrainingPlanPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.UPDATE_ANNUAL_TRAINING_PLAN);
    }

    public void deleteAnnualTrainingPlanPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.DELETE_ANNUAL_TRAINING_PLAN);
    }

    /* Training Permissions */
    public void addTrainingPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.ADD_TRAINING);
    }

    public void getAllTrainingsPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_ALL_TRAININGS);
    }

    public void getTrainingsByStatusPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_TRAININGS_BY_STATUS);
    }

    public void getTrainingByIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_TRAINING_BY_ID);
    }

    public void updateTrainingPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.UPDATE_TRAINING);
    }

    public void approveTrainingPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.APPROVE_TRAINING);
    }

    public void deleteTrainingPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.DELETE_TRAINING);
    }

    /* Training Participant Permissions */
    public void addTrainingParticipantPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.ADD_TRAINING_PARTICIPANT);
    }

    public void getAllTrainingParticipantsPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_ALL_TRAINING_PARTICIPANTS);
    }

    public void getTrainingParticipantByIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_TRAINING_PARTICIPANT_BY_ID);
    }

    public void updateTrainingParticipantPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.UPDATE_TRAINING_PARTICIPANT);
    }

    public void deleteTrainingParticipantPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.DELETE_TRAINING_PARTICIPANT);
    }

    /* Training Institution Permissions */
    public void addInstitutionPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.ADD_INSTITUTION);
    }

    public void getAllInstitutionsPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_ALL_INSTITUTIONS);
    }

    public void getInstitutionByIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_INSTITUTION_BY_ID);
    }

    public void updateInstitutionPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.UPDATE_INSTITUTION);
    }

    public void deleteInstitutionPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.DELETE_INSTITUTION);
    }

    /* Training Course Permissions */
    public void addTrainingCoursePermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.ADD_TRAINING_COURSE);
    }

    public void getAllTrainingCoursesByCategoryIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_ALL_TRAINING_COURSES_BY_CATEGORY_ID);
    }

    public void getTrainingCourseByIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_TRAINING_COURSE_BY_ID);
    }

    public void updateTrainingCoursePermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.UPDATE_TRAINING_COURSE);
    }

    public void deleteTrainingCoursePermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.DELETE_TRAINING_COURSE);
    }

    /* Training Course Category Permissions */
    public void addTrainingCourseCategoryPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.ADD_TRAINING_COURSE_CATEGORY);
    }

    public void getAllTrainingCourseCategoriesPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_ALL_TRAINING_COURSE_CATEGORIES);
    }

    public void getTrainingCourseCategoryByIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_TRAINING_COURSE_CATEGORY_BY_ID);
    }

    public void updateTrainingCourseCategoryPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.UPDATE_TRAINING_COURSE_CATEGORY);
    }

    public void deleteTrainingCourseCategoryPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.DELETE_TRAINING_COURSE_CATEGORY);
    }

    /* Pre Service Trainee Result Permissions */
    public void addPreServiceTraineeResultPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.ADD_PRE_SERVICE_TRAINEE_RESULT);
    }

    public void getAllTraineeResultsByCourseIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_ALL_TRAINEE_RESULTS_BY_COURSE_ID);
    }

    public void getPreServiceTraineeResultByIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_PRE_SERVICE_TRAINEE_RESULT_BY_ID);
    }

    public void updatePreServiceTraineeResultPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.UPDATE_PRE_SERVICE_TRAINEE_RESULT);
    }

    public void deletePreServiceTraineeResultPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.DELETE_PRE_SERVICE_TRAINEE_RESULT);
    }

    /* Pre Service Trainee Permissions */
    public void addPreServiceTraineePermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.ADD_PRE_SERVICE_TRAINEE);
    }

    public void addCoursesToPreServiceTraineePermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.ADD_COURSES_TO_PRE_SERVICE_TRAINEE);
    }

    public void getAllPreServiceTraineesPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_ALL_PRE_SERVICE_TRAINEES);
    }

    public void getPreServiceTraineesByBudgetYearIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_PRE_SERVICE_TRAINEES_BY_BUDGET_YEAR_ID);
    }

    public void getPreServiceTraineeByIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_PRE_SERVICE_TRAINEE_BY_ID);
    }

    public void downloadPreServiceTraineeImagePermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.DOWNLOAD_PRE_SERVICE_TRAINEE_IMAGE);
    }

    public void updatePreServiceTraineePermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.UPDATE_PRE_SERVICE_TRAINEE);
    }

    public void deletePreServiceTraineePermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.DELETE_PRE_SERVICE_TRAINEE);
    }

    /* Pre Service Trainee Checked Document Permissions */
    public void addPreServiceCheckedDocumentPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.ADD_PRE_SERVICE_CHECKED_DOCUMENT);
    }

    public void getAllPreServiceCheckedDocumentsPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_ALL_PRE_SERVICE_CHECKED_DOCUMENTS);
    }

    public void getPreServiceCheckedDocumentsByTraineeIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_PRE_SERVICE_CHECKED_DOCUMENTS_BY_TRAINEE_ID);
    }

    public void getPreServiceCheckedDocumentByIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_PRE_SERVICE_CHECKED_DOCUMENT_BY_ID);
    }

    public void updatePreServiceCheckedDocumentPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.UPDATE_PRE_SERVICE_CHECKED_DOCUMENT);
    }

    public void removeTraineeCheckedDocumentPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.REMOVE_TRAINEE_CHECKED_DOCUMENT);
    }

    public void deletePreServiceCheckedDocumentPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.DELETE_PRE_SERVICE_CHECKED_DOCUMENT);
    }

    /* Pre Service Course Type Permissions */
    public void addPreServiceCourseTypePermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.ADD_PRE_SERVICE_COURSE_TYPE);
    }

    public void getAllPreServiceCourseTypesPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_ALL_PRE_SERVICE_COURSE_TYPES);
    }

    public void getPreServiceCourseTypeByIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_PRE_SERVICE_COURSE_TYPE_BY_ID);
    }

    public void updatePreServiceCourseTypePermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.UPDATE_PRE_SERVICE_COURSE_TYPE);
    }

    public void deletePreServiceCourseTypePermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.DELETE_PRE_SERVICE_COURSE_TYPE);
    }

    /* Pre Service Course Permissions */
    public void addPreServiceCoursePermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.ADD_PRE_SERVICE_COURSE);
    }

    public void getAllPreServiceCoursesPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_ALL_PRE_SERVICE_COURSES);
    }

    public void getPreServiceCoursesByTraineeIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_PRE_SERVICE_COURSES_BY_TRAINEE_ID);
    }

    public void getPreServiceCoursesByCourseTypeIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_PRE_SERVICE_COURSES_BY_COURSE_TYPE_ID);
    }

    public void getPreServiceCourseByIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_PRE_SERVICE_COURSE_BY_ID);
    }

    public void updatePreServiceCoursePermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.UPDATE_PRE_SERVICE_COURSE);
    }

    public void removeCourseByTraineeIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.REMOVE_COURSE_BY_TRAINEE_ID);
    }

    public void deletePreServiceCoursePermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.DELETE_PRE_SERVICE_COURSE);
    }

    /* University Permissions */
    public void addUniversityPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.ADD_UNIVERSITY);
    }

    public void getAllUniversitiesPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_ALL_UNIVERSITIES);
    }

    public void getUniversityByIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_UNIVERSITY_BY_ID);
    }

    public void updateUniversityPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.UPDATE_UNIVERSITY);
    }

    public void deleteUniversityPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.DELETE_UNIVERSITY);
    }

    /* Internship Student Permissions */
    public void addInternshipStudentPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.ADD_INTERNSHIP_STUDENT);
    }

    public void getAllInternshipStudentsPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_ALL_INTERNSHIP_STUDENTS);
    }

    public void getInternshipStudentsByBudgetYearIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_INTERNSHIP_STUDENTS_BY_BUDGET_YEAR_ID);
    }

    public void getInternshipStudentByIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_INTERNSHIP_STUDENT_BY_ID);
    }

    public void updateInternshipStudentPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.UPDATE_INTERNSHIP_STUDENT);
    }

    public void assignDepartmentToInternshipStudentPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.ASSIGN_DEPARTMENT_TO_INTERNSHIP_STUDENT);
    }

    public void assignStatusToInternshipStudentPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.ASSIGN_STATUS_TO_INTERNSHIP_STUDENT);
    }

    public void deleteInternshipStudentPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.DELETE_INTERNSHIP_STUDENT);
    }

    /* Internship Payment Permissions */
    public void addInternshipPaymentPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.ADD_INTERNSHIP_PAYMENT);
    }

    public void getAllInternshipPaymentsPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_ALL_INTERNSHIP_PAYMENTS);
    }

    public void getInternshipPaymentByIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_INTERNSHIP_PAYMENT_BY_ID);
    }

    public void updateInternshipPaymentPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.UPDATE_INTERNSHIP_PAYMENT);
    }

    public void deleteInternshipPaymentPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.DELETE_INTERNSHIP_PAYMENT);
    }

    /* Education Opportunity Permissions */
    public void addEducationOpportunityPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.ADD_EDUCATION_OPPORTUNITY);
    }

    public void getAllEducationOpportunitiesPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_ALL_EDUCATION_OPPORTUNITIES);
    }

    public void getEducationOpportunitiesByEmployeeIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_EDUCATION_OPPORTUNITIES_BY_EMPLOYEE_ID);
    }

    public void getEducationOpportunityByIdPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.GET_EDUCATION_OPPORTUNITY_BY_ID);
    }

    public void updateEducationOpportunityPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.UPDATE_EDUCATION_OPPORTUNITY);
    }

    public void deleteEducationOpportunityPermission(UUID tenantId) {
        checkPermission(tenantId, TrainingServiceResourceName.DELETE_EDUCATION_OPPORTUNITY);
    }

    private void checkPermission(UUID tenantId, TrainingServiceResourceName resourceName) {
        boolean hasPermission = permissionUtil.hasPermission(tenantId, resourceName.getValue());
        if (!hasPermission) {
            throw new AccessDeniedException("Access Denied");
        }
    }
}