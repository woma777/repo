package com.saas.employee.client;

import com.saas.employee.dto.clientDto.RecruitmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient("recruitment-service")
public interface RecruitmentServiceClient {

    @GetMapping("/api/recruitment/recruitments/{tenant-id}/get/vacancy")
    RecruitmentDto getRecruitmentByVacancyNumber(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("vacancy-number") String vacancyNumber);

    @GetMapping("/api/recruitment/recruitments/{tenant-id}/get/mode")
    List<RecruitmentDto> getRecruitmentsByMode(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("recruitment-mode") String recruitmentMode);
}
