package com.saas.employee.controller;

import com.saas.employee.utility.PermissionEvaluator;
import com.saas.employee.dto.request.EmployeeRequest;
import com.saas.employee.dto.response.EmployeeHistoryResponse;
import com.saas.employee.dto.response.EmployeeResponse;
import com.saas.employee.dto.response.PromoteEmployeeResponse;
import com.saas.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee/employees/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(
            @PathVariable("tenant-id") UUID tenantId,
            @Valid @RequestPart("employee") EmployeeRequest employeeRequest,
            @RequestPart(value = "profileImage", required = false) MultipartFile file) throws IOException {

        permissionEvaluator.addEmployeePermission(tenantId);

        EmployeeResponse employee = employeeService
                .addEmployee(tenantId, employeeRequest, file);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllEmployees(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllEmployeesPermission(tenantId);

        List<EmployeeResponse> employees = employeeService
                .getAllEmployees(tenantId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/get/{employee-id}")
    public ResponseEntity<?> getEmployeeById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId) {

        permissionEvaluator.getEmployeeByIdPermission(tenantId, employeeId);

        EmployeeResponse employee = employeeService
                .getEmployeeById(tenantId, employeeId);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getEmployeeByEmployeeId(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("employee-id") String employeeId) {

        permissionEvaluator.getEmployeeByEmployeeIdPermission(tenantId, employeeId);

        EmployeeResponse employee = employeeService
                .getEmployeeByEmployeeId(tenantId, employeeId);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/get-employee")
    public ResponseEntity<?> getEmployeeByName(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("first-name") String firstName,
            @RequestParam(value = "middle-name", required = false) String middleName,
            @RequestParam(value = "last-name", required = false) String lastName) {

        permissionEvaluator.getEmployeeByNamePermission(tenantId);

        EmployeeResponse employee = employeeService
                .getEmployeeByName(tenantId, firstName, middleName, lastName);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/{department-id}/get")
    public ResponseEntity<?> getEmployeesByDepartmentId(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("department-id") UUID departmentId) {

        permissionEvaluator.getEmployeesByDepartmentIdPermission(tenantId);

        List<EmployeeResponse> employees = employeeService
                .getEmployeesByDepartmentId(tenantId, departmentId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/download-image/{employee-id}")
    public ResponseEntity<?> getEmployeeProfileImageById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId) {

        permissionEvaluator.downloadEmployeeImagePermission(tenantId, employeeId);

        EmployeeResponse employee = employeeService
                .getEmployeeById(tenantId, employeeId);
        if (employee == null || employee.getProfileImageType() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee profile image not found");
        }
        MediaType mediaType = MediaType.valueOf(employee.getProfileImageType());
        byte[] profileImage = employeeService
                .getEmployeeProfileImageById(tenantId, employeeId, mediaType);
        return ResponseEntity.ok().contentType(mediaType).body(profileImage);
    }

    @GetMapping("/get/employment-type")
    public ResponseEntity<?> getEmployeesByEmploymentType(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam String employmentType) {

        permissionEvaluator.getEmployeesByEmploymentTypePermission(tenantId);

        List<EmployeeResponse> employees = employeeService
                .getEmployeesByEmploymentType(tenantId, employmentType);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/get/duty-station/{stationId}")
    public ResponseEntity<?> getEmployeesByDutyStation(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable UUID stationId) {

        permissionEvaluator.getEmployeesByDutyStationPermission(tenantId);

        List<EmployeeResponse> employees = employeeService
                .getEmployeesByDutyStation(tenantId, stationId);
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/update/{employee-id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @Valid @RequestPart("employee") EmployeeRequest employeeRequest,
            @RequestPart(value = "profileImage", required = false) MultipartFile file) throws IOException {

        permissionEvaluator.updateEmployeePermission(tenantId);

        EmployeeResponse employee = employeeService
                .updateEmployee(tenantId, employeeId, employeeRequest, file);
        return ResponseEntity.ok(employee);
    }

    @PutMapping( "/renew-contract/{employee-id}")
    public ResponseEntity<?> renewEmployeeContract(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @RequestParam LocalDate endDate) {

        permissionEvaluator.updateEmployeePermission(tenantId);

        EmployeeResponse response = employeeService
                .renewEmployeeContract(tenantId, employeeId, endDate);
        return ResponseEntity.ok(response);
    }

    @PutMapping( "/update-email/{employee-id}")
    public ResponseEntity<?> updateEmployeeEmail(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @RequestParam String email) {

        permissionEvaluator.updateEmployeeEmailPermission(tenantId, employeeId);

        String response = employeeService
                .updateEmployeeEmail(tenantId, employeeId, email);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/add-history/{employee-id}")
    public ResponseEntity<?> addEmployeeHistory(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @RequestParam String vacancyNumber,
            @RequestParam UUID payGradeId) {

        permissionEvaluator.addEmployeeHistoryPermission(tenantId);

        PromoteEmployeeResponse response = employeeService
                .addEmployeeHistory(tenantId, employeeId, vacancyNumber, payGradeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-histories/{employee-id}")
    public ResponseEntity<?> getEmployeeHistories(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId) {

        permissionEvaluator.getEmployeesHistoriesPermission(tenantId, employeeId);

        List<EmployeeHistoryResponse> responses = employeeService
                .getEmployeeHistories(tenantId, employeeId);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("delete/{employee-id}")
    public ResponseEntity<?> deleteEmployee(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId) {

        permissionEvaluator.deleteEmployeePermission(tenantId);

        employeeService.deleteEmployee(tenantId, employeeId);
        return ResponseEntity.ok("Employee Deleted Successfully");
    }
}