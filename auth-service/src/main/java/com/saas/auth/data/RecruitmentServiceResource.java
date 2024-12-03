package com.saas.auth.data;

import com.saas.auth.dto.clientDto.TenantDto;
import com.saas.auth.enums.RecruitmentServiceResourceName;
import com.saas.auth.exception.ResourceExistsException;
import com.saas.auth.model.Resource;
import com.saas.auth.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class RecruitmentServiceResource {

    private final ResourceRepository resourceRepository;

    public void storeEndpoints(TenantDto tenant) {

        List<Resource> resources = new ArrayList<>();

        String defaultRole =  "default_role";

        /* Shortlist Criteria */
        resources.add(mapToEntity(RecruitmentServiceResourceName.ADD_SHORTLIST_CRITERIA.getValue(),
                null, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ALL_SHORTLIST_CRITERIA.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_SHORTLIST_CRITERIA_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.UPDATE_SHORTLIST_CRITERIA.getValue(),
                null, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DELETE_SHORTLIST_CRITERIA.getValue(),
                null, tenant));

        /* Recruitment */
        resources.add(mapToEntity(RecruitmentServiceResourceName.ADD_RECRUITMENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ALL_RECRUITMENTS.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_RECRUITMENT_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_RECRUITMENT_BY_VACANCY_NUMBER.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_RECRUITMENTS_BY_STATUS.getValue(),
                null, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.UPDATE_RECRUITMENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.APPROVE_RECRUITMENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DELETE_RECRUITMENT.getValue(),
                null, tenant));

        /* Media Type */
        resources.add(mapToEntity(RecruitmentServiceResourceName.ADD_MEDIA_TYPE.getValue(),
                null, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ALL_MEDIA_TYPES.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_MEDIA_TYPES_BY_ADVERTISEMENT_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_MEDIA_TYPE_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.UPDATE_MEDIA_TYPE.getValue(),
                null, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DELETE_MEDIA_TYPE.getValue(),
                null, tenant));

        /* Exam Result */
        resources.add(mapToEntity(RecruitmentServiceResourceName.ADD_EXAM_RESULT.getValue(),
                null, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ALL_EXAM_RESULTS.getValue(),
                null, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_EXAM_RESULT_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.UPDATE_EXAM_RESULT.getValue(),
                null, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DELETE_EXAM_RESULT.getValue(),
                null, tenant));

        /* Assessment Weight */
        resources.add(mapToEntity(RecruitmentServiceResourceName.ADD_ASSESSMENT_WEIGHT.getValue(),
                null, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ALL_ASSESSMENT_WEIGHTS.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ASSESSMENT_WEIGHT_BY_RECRUITMENT_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ASSESSMENT_WEIGHT_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.UPDATE_ASSESSMENT_WEIGHT.getValue(),
                null, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DELETE_ASSESSMENT_WEIGHT.getValue(),
                null, tenant));

        /* Applicant */
        resources.add(mapToEntity(RecruitmentServiceResourceName.ADD_APPLICANT.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ALL_APPLICANTS.getValue(),
                null, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_APPLICANT_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.UPDATE_APPLICANT.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DELETE_APPLICANT.getValue(),
                null, tenant));

        /* Applicant Training or Certificate */
        resources.add(mapToEntity(RecruitmentServiceResourceName.ADD_TRAINING.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ALL_TRAININGS.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_TRAINING_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DOWNLOAD_TRAINING_CERTIFICATE.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.UPDATE_TRAINING.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DELETE_TRAINING.getValue(),
                defaultRole, tenant));

        /* Applicant Reference */
        resources.add(mapToEntity(RecruitmentServiceResourceName.ADD_REFERENCE.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ALL_REFERENCES.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_REFERENCE_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.UPDATE_REFERENCE.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DELETE_REFERENCE.getValue(),
                defaultRole, tenant));

        /* Applicant Language */
        resources.add(mapToEntity(RecruitmentServiceResourceName.ADD_LANGUAGE.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ALL_LANGUAGES.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_LANGUAGE_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.UPDATE_LANGUAGE.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DELETE_LANGUAGE.getValue(),
                defaultRole, tenant));

        /* Applicant Experience */
        resources.add(mapToEntity(RecruitmentServiceResourceName.ADD_EXPERIENCE.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ALL_EXPERIENCES.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_EXPERIENCE_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DOWNLOAD_EXPERIENCE_DOCUMENT.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.UPDATE_EXPERIENCE.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DELETE_EXPERIENCE.getValue(),
                defaultRole, tenant));

        /* Applicant Education */
        resources.add(mapToEntity(RecruitmentServiceResourceName.ADD_EDUCATION.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ALL_EDUCATIONS.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_EDUCATION_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DOWNLOAD_EDUCATION_DOCUMENT.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.UPDATE_EDUCATION.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DELETE_EDUCATION.getValue(),
                defaultRole, tenant));

        /* Advertisement */
        resources.add(mapToEntity(RecruitmentServiceResourceName.ADD_ADVERTISEMENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ALL_ADVERTISEMENTS.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ADVERTISEMENT_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.GET_ADVERTISEMENT_BY_VACANCY_NUMBER.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.UPDATE_ADVERTISEMENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.REMOVE_ADVERTISEMENT_MEDIA_TYPE.getValue(),
                null, tenant));
        resources.add(mapToEntity(RecruitmentServiceResourceName.DELETE_ADVERTISEMENT.getValue(),
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
        resource.setServiceName("recruitment-service");
        resource.setTenantId(tenant.getId());
        resource.setDescription("");

        Set<String> roles = new HashSet<>();
        String adminRole =  "admin";
        roles.add(adminRole);
        if (roleName != null) {
            roles.add(roleName);
        }
        resource.setRequiredRoles(roles);

        return resource;
    }
}
