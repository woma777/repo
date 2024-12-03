package com.saas.leave.controller;

import com.saas.leave.dto.request.BudgetYearRequest;
import com.saas.leave.dto.response.BudgetYearResponse;
import com.saas.leave.service.BudgetYearService;
import com.saas.leave.utility.PermissionEvaluator;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/budget-years/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Budget Year")
public class BudgetYearController {

    private final BudgetYearService budgetYearService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<BudgetYearResponse> createBudgetYear(@PathVariable("tenantId") UUID tenantId,
                                                               @RequestBody @Valid BudgetYearRequest budgetYearRequest) {

        permissionEvaluator.addBudgetYearPermission(tenantId);

        BudgetYearResponse createdBudgetYear = budgetYearService.createBudgetYear(tenantId, budgetYearRequest);
        return ResponseEntity.ok(createdBudgetYear);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<BudgetYearResponse>> getAllBudgetYears(@PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllBudgetYearsPermission(tenantId);

        List<BudgetYearResponse> budgetYears = budgetYearService.getAllBudgetYears(tenantId);
        return ResponseEntity.ok(budgetYears);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BudgetYearResponse> getBudgetYearById(@PathVariable("tenantId") UUID tenantId, @PathVariable UUID id) {

        permissionEvaluator.getBudgetYearByIdPermission(tenantId);

        BudgetYearResponse budgetYear = budgetYearService.getBudgetYearById(tenantId, id);
        return ResponseEntity.ok(budgetYear);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BudgetYearResponse> updateBudgetYear(@PathVariable("tenantId") UUID tenantId, @PathVariable UUID id, @RequestBody @Valid BudgetYearRequest budgetYearRequest) {

        permissionEvaluator.updateBudgetYearPermission(tenantId);

        BudgetYearResponse updatedBudgetYear = budgetYearService.updateBudgetYear(tenantId, id, budgetYearRequest);
        return ResponseEntity.ok(updatedBudgetYear);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBudgetYear(@PathVariable("tenantId") UUID tenantId, @PathVariable UUID id) {

        permissionEvaluator.deleteBudgetYearPermission(tenantId);

        budgetYearService.deleteBudgetYear(tenantId, id);
        return ResponseEntity.ok("Holiday deleted successfully!");
    }
}
