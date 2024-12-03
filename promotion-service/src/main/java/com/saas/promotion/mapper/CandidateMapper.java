package com.saas.promotion.mapper;

import com.saas.promotion.dto.clientDto.EmployeeDto;
import com.saas.promotion.dto.clientDto.RecruitmentDto;
import com.saas.promotion.dto.clientDto.TenantDto;
import com.saas.promotion.dto.request.CandidateRequest;
import com.saas.promotion.dto.response.CandidateResponse;
import com.saas.promotion.model.Candidate;
import com.saas.promotion.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CandidateMapper {

    private final ValidationUtil validationUtil;

    public Candidate mapToEntity(TenantDto tenant,
                                 CandidateRequest request) {

        RecruitmentDto recruitment = validationUtil
                .getRecruitmentByVacancyNumber(tenant.getId(), request.getVacancyNumber());
        EmployeeDto employee = validationUtil
                .getEmployeeByEmployeeId(tenant.getId(), request.getEmployeeId());

        Candidate candidate = new Candidate();
        candidate.setTenantId(tenant.getId());
        candidate.setRecruitmentId(recruitment.getId());
        candidate.setEmployeeId(employee.getId());

        return candidate;
    }

    public CandidateResponse mapToDto(Candidate candidate) {

        CandidateResponse response = new CandidateResponse();
        response.setId(candidate.getId());
        response.setRecruitmentId(candidate.getRecruitmentId());
        response.setEmployeeId(candidate.getEmployeeId());
        response.setTenantId(candidate.getTenantId());
        response.setCreatedAt(candidate.getCreatedAt());
        response.setUpdatedAt(candidate.getUpdatedAt());
        response.setCreatedBy(candidate.getCreatedBy());
        response.setUpdatedBy(candidate.getUpdatedBy());

        return response;
    }

    public Candidate updateEntity(TenantDto tenant,
                                  Candidate candidate,
                                  CandidateRequest request) {

        RecruitmentDto recruitment = validationUtil
                .getRecruitmentByVacancyNumber(tenant.getId(), request.getVacancyNumber());
        EmployeeDto employee = validationUtil
                .getEmployeeByEmployeeId(tenant.getId(), request.getEmployeeId());

        if (request.getVacancyNumber() != null) {
            candidate.setRecruitmentId(recruitment.getId());
        }

        if (request.getEmployeeId() != null) {
            candidate.setEmployeeId(employee.getId());
        }

        return candidate;
    }
}
