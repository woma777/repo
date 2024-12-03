package com.saas.training.controller;

import com.saas.training.dto.request.TrainingCourseRequest;
import com.saas.training.dto.response.TrainingCourseResponse;
import com.saas.training.utility.PermissionEvaluator;
import com.saas.training.service.TrainingCourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/training/training-courses/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Training Course")
public class TrainingCourseController {

    private final TrainingCourseService trainingCourseService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addTrainingCourse(
            @PathVariable("tenant-id") UUID tenantId,
            @Valid @RequestBody TrainingCourseRequest request) {

        permissionEvaluator.addTrainingCoursePermission(tenantId);

        TrainingCourseResponse response = trainingCourseService
                .addTrainingCourse(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all/{category-id}")
    public ResponseEntity<?> getAllTrainingCourses(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("category-id") UUID categoryId) {

        permissionEvaluator.getAllTrainingCoursesByCategoryIdPermission(tenantId);

        List<TrainingCourseResponse> responses = trainingCourseService
                .getAllTrainingCourses(tenantId, categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/get/{course-id}")
    public ResponseEntity<?> getTrainingCourseById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("course-id") UUID courseId) {

        permissionEvaluator.getTrainingCourseByIdPermission(tenantId);

        TrainingCourseResponse response = trainingCourseService
                .getTrainingCourseById(tenantId, courseId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{course-id}")
    public ResponseEntity<?> updateTrainingCourse(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("course-id") UUID courseId,
            @Valid @RequestBody TrainingCourseRequest request) {

        permissionEvaluator.updateTrainingCoursePermission(tenantId);

        TrainingCourseResponse response = trainingCourseService
                .updateTrainingCourse(tenantId, courseId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{course-id}")
    public ResponseEntity<?> deleteTrainingCourse(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("course-id") UUID courseId) {

        permissionEvaluator.deleteTrainingCoursePermission(tenantId);

        trainingCourseService.deleteTrainingCourse(tenantId, courseId);
        return ResponseEntity.ok("Annual training course deleted successfully");
    }
}