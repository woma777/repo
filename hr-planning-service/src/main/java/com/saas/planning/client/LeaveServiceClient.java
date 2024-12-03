package com.saas.planning.client;
import com.saas.planning.dto.BudgetYearDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;


@FeignClient("leave-service")
public interface LeaveServiceClient {
    @GetMapping("/api/leave/budget-years/{tenantId}/get/{id}")
    BudgetYearDto getBudgetYearById(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id);
    @GetMapping("/api/leave/budget-years/{tenantId}/get-all")
    BudgetYearDto getAllBudgetYears(
            @PathVariable("tenantId") UUID tenantId);
}
