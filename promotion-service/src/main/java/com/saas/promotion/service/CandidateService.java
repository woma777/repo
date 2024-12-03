package com.saas.promotion.service;

import com.saas.promotion.dto.clientDto.EmployeeDto;
import com.saas.promotion.dto.clientDto.RecruitmentDto;
import com.saas.promotion.dto.clientDto.TenantDto;
import com.saas.promotion.dto.request.CandidateRequest;
import com.saas.promotion.dto.response.CandidateResponse;
import com.saas.promotion.exception.ResourceExistsException;
import com.saas.promotion.mapper.CandidateMapper;
import com.saas.promotion.model.Candidate;
import com.saas.promotion.repository.CandidateRepository;
import com.saas.promotion.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public CandidateResponse addCandidate(UUID tenantId,
                                                   CandidateRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        RecruitmentDto recruitment = validationUtil
                .getRecruitmentByVacancyNumber(tenant.getId(), request.getVacancyNumber());
        EmployeeDto employee = validationUtil
                .getEmployeeByEmployeeId(tenant.getId(), request.getEmployeeId());
        if (candidateRepository.existsByTenantIdAndRecruitmentIdAndEmployeeId(
                tenantId, recruitment.getId(), employee.getId())) {
            throw new ResourceExistsException("Employee already applied for this promotion");
        }
        Candidate candidate = candidateMapper.mapToEntity(tenant, request);
        candidate = candidateRepository.save(candidate);
        return candidateMapper.mapToDto(candidate);
    }

    public List<CandidateResponse> getAllCandidates(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<Candidate> candidates = candidateRepository
                .findByTenantId(tenant.getId());
        return candidates.stream()
                .map(candidateMapper::mapToDto)
                .toList();
    }

    public CandidateResponse getCandidateById(UUID tenantId,
                                                       UUID promotionId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Candidate candidate = validationUtil
                .getCandidateById(tenant.getId(), promotionId);
        return candidateMapper.mapToDto(candidate);
    }

    public CandidateResponse updateCandidate(UUID tenantId,
                                                      UUID promotionId,
                                                      CandidateRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Candidate candidate = validationUtil
                .getCandidateById(tenant.getId(), promotionId);
        RecruitmentDto recruitment = validationUtil
                .getRecruitmentByVacancyNumber(tenant.getId(), request.getVacancyNumber());
        EmployeeDto employee = validationUtil
                .getEmployeeByEmployeeId(tenant.getId(), request.getEmployeeId());
        if (candidateRepository.existsByTenantIdAndRecruitmentIdAndEmployeeIdAndIdNot(
                tenantId, recruitment.getId(), employee.getId(), promotionId)) {
            throw new ResourceExistsException("Employee already applied for this promotion");
        }
        candidate = candidateMapper.updateEntity(tenant, candidate, request);
        candidate = candidateRepository.save(candidate);
        return candidateMapper.mapToDto(candidate);
    }

    public void deleteCandidate(UUID tenantId,
                                     UUID promotionId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Candidate candidate = validationUtil
                .getCandidateById(tenant.getId(), promotionId);
        candidateRepository.delete(candidate);
    }
}
