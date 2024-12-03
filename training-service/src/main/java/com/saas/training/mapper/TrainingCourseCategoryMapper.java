package com.saas.training.mapper;

import com.saas.training.dto.clientDto.TenantDto;
import com.saas.training.dto.request.TrainingCourseCategoryRequest;
import com.saas.training.dto.response.TrainingCourseCategoryResponse;
import com.saas.training.model.TrainingCourseCategory;
import org.springframework.stereotype.Component;

@Component
public class TrainingCourseCategoryMapper {

    public TrainingCourseCategory mapToEntity(TenantDto tenant,
                                              TrainingCourseCategoryRequest request) {

        TrainingCourseCategory trainingCourseCategory = new TrainingCourseCategory();
        trainingCourseCategory.setTenantId(tenant.getId());
        trainingCourseCategory.setCategoryName(request.getCategoryName());
        trainingCourseCategory.setDescription(request.getDescription());

        return trainingCourseCategory;
    }

    public TrainingCourseCategoryResponse mapToDto(TrainingCourseCategory trainingCourseCategory) {

        TrainingCourseCategoryResponse response = new TrainingCourseCategoryResponse();
        response.setId(trainingCourseCategory.getId());
        response.setCategoryName(trainingCourseCategory.getCategoryName());
        response.setDescription(trainingCourseCategory.getDescription());
        response.setTenantId(trainingCourseCategory.getTenantId());
        response.setCreatedAt(trainingCourseCategory.getCreatedAt());
        response.setUpdatedAt(trainingCourseCategory.getUpdatedAt());
        response.setCreatedBy(trainingCourseCategory.getCreatedBy());
        response.setUpdatedBy(trainingCourseCategory.getUpdatedBy());

        return response;
    }

    public TrainingCourseCategory updateEntity(TrainingCourseCategory trainingCourseCategory,
                                               TrainingCourseCategoryRequest request) {

        if (request.getCategoryName() != null) {
            trainingCourseCategory.setCategoryName(request.getCategoryName());
        }
        if (request.getDescription() != null) {
            trainingCourseCategory.setDescription(request.getDescription());
        }

        return trainingCourseCategory;
    }
}
