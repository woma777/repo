package com.saas.promotion.client;

import com.saas.promotion.dto.clientDto.RecruitmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient("recruitment-service")
public interface RecruitmentServiceClient {

    @GetMapping("/api/recruitment/recruitments/{tenant-id}/get/{recruitment-id}")
    RecruitmentDto getRecruitmentById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("recruitment-id") UUID recruitmentId);

    @GetMapping("/api/recruitment/recruitments/{tenant-id}/get/mode")
    List<RecruitmentDto> getRecruitmentsByMode(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("recruitment-mode") String recruitmentMode);

    @GetMapping("/api/recruitment/recruitments/{tenant-id}/get/vacancy")
    RecruitmentDto getRecruitmentByVacancyNumber(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("vacancy-number") String vacancyNumber);
}
