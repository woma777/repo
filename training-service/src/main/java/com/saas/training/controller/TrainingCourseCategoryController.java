package com.saas.training.controller;

import com.saas.training.dto.request.TrainingCourseCategoryRequest;
import com.saas.training.dto.response.TrainingCourseCategoryResponse;
import com.saas.training.utility.PermissionEvaluator;
import com.saas.training.service.TrainingCourseCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/training/course-categories/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Training Course Category")
public class TrainingCourseCategoryController {

    private final TrainingCourseCategoryService trainingCourseCategoryService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addCourseCategory(
            @PathVariable("tenant-id") UUID tenantId,
            @Valid @RequestBody TrainingCourseCategoryRequest request) {

        permissionEvaluator.addTrainingCourseCategoryPermission(tenantId);

        TrainingCourseCategoryResponse response = trainingCourseCategoryService
                .addCourseCategory(tenantId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllCourseCategories(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllTrainingCourseCategoriesPermission(tenantId);

        List<TrainingCourseCategoryResponse> responses = trainingCourseCategoryService
                .getAllCourseCategories(tenantId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/get/{category-id}")
    public ResponseEntity<?> getCourseCategoryById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("category-id") UUID categoryId) {

        permissionEvaluator.getTrainingCourseCategoryByIdPermission(tenantId);

        TrainingCourseCategoryResponse response = trainingCourseCategoryService
                .getCourseCategoryById(tenantId, categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{category-id}")
    public ResponseEntity<?> updateCourseCategory(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("category-id") UUID courseCategoryId,
            @Valid @RequestBody TrainingCourseCategoryRequest request) {

        permissionEvaluator.updateTrainingCourseCategoryPermission(tenantId);

        TrainingCourseCategoryResponse response = trainingCourseCategoryService
                .updateCourseCategory(tenantId, courseCategoryId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{category-id}")
    public ResponseEntity<?> deleteCourseCategory(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("category-id") UUID courseCategoryId) {

        permissionEvaluator.deleteTrainingCourseCategoryPermission(tenantId);

        trainingCourseCategoryService.deleteCourseCategory(tenantId, courseCategoryId);
        return ResponseEntity.ok("Course Category Deleted Successfully");
    }
}