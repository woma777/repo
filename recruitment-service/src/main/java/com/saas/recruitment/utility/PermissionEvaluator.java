package com.saas.recruitment.utility;

import com.saas.recruitment.enums.RecruitmentServiceResourceName;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PermissionEvaluator {

    private final PermissionUtil permissionUtil;

    /* Shortlist Criteria Permissions */
    public void addShortlistCriteriaPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.ADD_SHORTLIST_CRITERIA);
    }

    public void getAllShortlistCriteriaPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_ALL_SHORTLIST_CRITERIA);
    }

    public void getShortlistCriteriaByIdPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_SHORTLIST_CRITERIA_BY_ID);
    }

    public void updateShortlistCriteriaPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.UPDATE_SHORTLIST_CRITERIA);
    }

    public void deleteShortlistCriteriaPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.DELETE_SHORTLIST_CRITERIA);
    }

    /* Recruitment Permissions */
    public void addRecruitmentPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.ADD_RECRUITMENT);
    }

    public void getAllRecruitmentsPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_ALL_RECRUITMENTS);
    }

    public void getRecruitmentByIdPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_RECRUITMENT_BY_ID);
    }

    public void getRecruitmentByVacancyNumberPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_RECRUITMENT_BY_VACANCY_NUMBER);
    }

    public void getRecruitmentsByStatusPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_RECRUITMENTS_BY_STATUS);
    }

    public void updateRecruitmentPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.UPDATE_RECRUITMENT);
    }

    public void approveRecruitmentPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.APPROVE_RECRUITMENT);
    }

    public void deleteRecruitmentPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.DELETE_RECRUITMENT);
    }

    /* Media Type Permissions */
    public void addMediaTypePermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.ADD_MEDIA_TYPE);
    }

    public void getAllMediaTypesPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_ALL_MEDIA_TYPES);
    }

    public void getMediaTypesByAdvertisementIdPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_MEDIA_TYPES_BY_ADVERTISEMENT_ID);
    }

    public void getMediaTypeByIdPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_MEDIA_TYPE_BY_ID);
    }

    public void updateMediaTypePermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.UPDATE_MEDIA_TYPE);
    }

    public void deleteMediaTypePermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.DELETE_MEDIA_TYPE);
    }

    /* Exam Result Permissions */
    public void addExamResultPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.ADD_EXAM_RESULT);
    }

    public void getAllExamResultsPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_ALL_EXAM_RESULTS);
    }

    public void getExamResultByIdPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_EXAM_RESULT_BY_ID);
    }

    public void updateExamResultPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.UPDATE_EXAM_RESULT);
    }

    public void deleteExamResultPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.DELETE_EXAM_RESULT);
    }

    /* Assessment Weight Permissions */
    public void addAssessmentWeightPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.ADD_ASSESSMENT_WEIGHT);
    }

    public void getAllAssessmentWeightsPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_ALL_ASSESSMENT_WEIGHTS);
    }

    public void getAssessmentWeightByRecruitmentIdPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_ASSESSMENT_WEIGHT_BY_RECRUITMENT_ID);
    }

    public void getAssessmentWeightByIdPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_ASSESSMENT_WEIGHT_BY_ID);
    }

    public void updateAssessmentWeightPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.UPDATE_ASSESSMENT_WEIGHT);
    }

    public void deleteAssessmentWeightPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.DELETE_ASSESSMENT_WEIGHT);
    }

    /* Applicant Permissions */
    public void addApplicantPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.ADD_APPLICANT);
    }

    public void getAllApplicantsPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_ALL_APPLICANTS);
    }

    public void getApplicantByIdPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_APPLICANT_BY_ID);
    }

    public void updateApplicantPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.UPDATE_APPLICANT);
    }

    public void deleteApplicantPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.DELETE_APPLICANT);
    }

    /* Applicant Training or Certificate Permissions */
    public void addTrainingPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.ADD_TRAINING);
    }

    public void getAllTrainingsPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_ALL_TRAININGS);
    }

    public void getTrainingByIdPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_TRAINING_BY_ID);
    }

    public void downloadTrainingCertificatePermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.DOWNLOAD_TRAINING_CERTIFICATE);
    }

    public void updateTrainingPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.UPDATE_TRAINING);
    }

    public void deleteTrainingPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.DELETE_TRAINING);
    }

    /* Applicant Reference Permissions */
    public void addReferencePermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.ADD_REFERENCE);
    }

    public void getAllReferencesPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_ALL_REFERENCES);
    }

    public void getReferenceByIdPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_REFERENCE_BY_ID);
    }

    public void updateReferencePermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.UPDATE_REFERENCE);
    }

    public void deleteReferencePermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.DELETE_REFERENCE);
    }

    /* Applicant Language Permissions */
    public void addLanguagePermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.ADD_LANGUAGE);
    }

    public void getAllLanguagesPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_ALL_LANGUAGES);
    }

    public void getLanguageByIdPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_LANGUAGE_BY_ID);
    }

    public void updateLanguagePermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.UPDATE_LANGUAGE);
    }

    public void deleteLanguagePermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.DELETE_LANGUAGE);
    }

    /* Applicant Experience Permissions */
    public void addExperiencePermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.ADD_EXPERIENCE);
    }

    public void getAllExperiencesPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_ALL_EXPERIENCES);
    }

    public void getExperienceByIdPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_EXPERIENCE_BY_ID);
    }

    public void downloadExperienceDocumentPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.DOWNLOAD_EXPERIENCE_DOCUMENT);
    }

    public void updateExperiencePermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.UPDATE_EXPERIENCE);
    }

    public void deleteExperiencePermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.DELETE_EXPERIENCE);
    }

    /* Applicant Education Permissions */
    public void addEducationPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.ADD_EDUCATION);
    }

    public void getAllEducationsPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_ALL_EDUCATIONS);
    }

    public void getEducationByIdPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_EDUCATION_BY_ID);
    }

    public void downloadEducationDocumentPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.DOWNLOAD_EDUCATION_DOCUMENT);
    }

    public void updateEducationPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.UPDATE_EDUCATION);
    }

    public void deleteEducationPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.DELETE_EDUCATION);
    }

    /* Advertisement Permissions */
    public void addAdvertisementPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.ADD_ADVERTISEMENT);
    }

    public void getAllAdvertisementsPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_ALL_ADVERTISEMENTS);
    }

    public void getAdvertisementByIdPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_ADVERTISEMENT_BY_ID);
    }

    public void getAdvertisementByVacancyNumberPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.GET_ADVERTISEMENT_BY_VACANCY_NUMBER);
    }

    public void updateAdvertisementPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.UPDATE_ADVERTISEMENT);
    }

    public void removeAdvertisementMediaTypePermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.REMOVE_ADVERTISEMENT_MEDIA_TYPE);
    }

    public void deleteAdvertisementPermission(UUID tenantId) {
        checkPermission(tenantId, RecruitmentServiceResourceName.DELETE_ADVERTISEMENT);
    }

    private void checkPermission(UUID tenantId, RecruitmentServiceResourceName resourceName) {
        boolean hasPermission = permissionUtil.hasPermission(tenantId, resourceName.getValue());
        if (!hasPermission) {
            throw new AccessDeniedException("Access Denied");
        }
    }
}