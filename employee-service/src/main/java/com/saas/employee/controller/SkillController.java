package com.saas.employee.controller;

import com.saas.employee.utility.PermissionEvaluator;
import com.saas.employee.dto.request.SkillRequest;
import com.saas.employee.dto.response.SkillResponse;
import com.saas.employee.service.SkillService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee/skills/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Skill")
public class SkillController {

    private final SkillService skillService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/{employee-id}/add")
    public ResponseEntity<?> addSkill(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @Valid @RequestBody SkillRequest skillRequest) {

        permissionEvaluator.addSkillPermission(tenantId, employeeId);

        SkillResponse skill = skillService
                .addSkill(tenantId, employeeId, skillRequest);
        return new ResponseEntity<>(skill, HttpStatus.CREATED);
    }

    @GetMapping("/{employee-id}/get-all")
    public ResponseEntity<?> getAllSkills(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId) {

        permissionEvaluator.getAllSkillsPermission(tenantId, employeeId);

        List<SkillResponse> skills = skillService
                .getAllSkills(tenantId, employeeId);
        return ResponseEntity.ok(skills);
    }

    @GetMapping("/get/employee-skills")
    public ResponseEntity<?> getEmployeeSkills(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("employee-id") String employeeId) {

        permissionEvaluator.getSkillsByEmployeeIdPermission(tenantId);

        List<SkillResponse> skills = skillService
                .getEmployeeSkills(tenantId, employeeId);
        return ResponseEntity.ok(skills);
    }

    @GetMapping("/{employee-id}/get/{skill-id}")
    public ResponseEntity<?> getSkillById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("skill-id") UUID skillId) {

        permissionEvaluator.getSkillByIdPermission(tenantId, employeeId);

        SkillResponse skill = skillService
                .getSkillById(tenantId, employeeId, skillId);
        return ResponseEntity.ok(skill);
    }

    @PutMapping("/{employee-id}/update/{skill-id}")
    public ResponseEntity<?> updateSkill(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("skill-id") UUID skillId,
            @Valid @RequestBody SkillRequest skillRequest) {

        permissionEvaluator.updateSkillPermission(tenantId, employeeId);

        SkillResponse skill = skillService
                .updateSkill(tenantId, employeeId, skillId, skillRequest);
        return ResponseEntity.ok(skill);
    }

    @DeleteMapping("/{employee-id}/delete/{skill-id}")
    public ResponseEntity<String> deleteSkill(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("skill-id") UUID skillId) {

        permissionEvaluator.deleteSkillPermission(tenantId, employeeId);

        skillService.deleteSkill(tenantId, employeeId, skillId);
        return ResponseEntity.ok("Skill deleted successfully");
    }
}