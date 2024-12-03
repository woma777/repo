package com.saas.recruitment.service;

import com.saas.recruitment.dto.clientDto.TenantDto;
import com.saas.recruitment.dto.request.RecruitmentApproveRequest;
import com.saas.recruitment.dto.request.RecruitmentRequest;
import com.saas.recruitment.dto.response.RecruitmentResponse;
import com.saas.recruitment.enums.RecruitmentMode;
import com.saas.recruitment.model.Recruitment;
import com.saas.recruitment.enums.RecruitmentStatus;
import com.saas.recruitment.mapper.RecruitmentMapper;
import com.saas.recruitment.repository.RecruitmentRepository;
import com.saas.recruitment.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final RecruitmentMapper recruitmentMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public RecruitmentResponse createRecruitment(UUID tenantId,
                                                 RecruitmentRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = recruitmentMapper.mapToEntity(tenant, request);
        recruitment = recruitmentRepository.save(recruitment);
        return recruitmentMapper.mapToDto(recruitment);
    }

    public List<RecruitmentResponse> getAllRecruitments(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<Recruitment> recruitments = recruitmentRepository.findAll();
        return recruitments.stream()
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .map(recruitmentMapper::mapToDto)
                .toList();
    }

    public RecruitmentResponse getRecruitmentById(UUID tenantId,
                                                  UUID recruitmentId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil.getRecruitmentById(tenant.getId(), recruitmentId);
        return recruitmentMapper.mapToDto(recruitment);
    }

    public RecruitmentResponse getRecruitmentByVacancyNumber(UUID tenantId,
                                                             String vacancyNumber) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil
                .getRecruitmentByVacancyNumber(tenant.getId(), vacancyNumber);
        return recruitmentMapper.mapToDto(recruitment);
    }

    public List<RecruitmentResponse> getRecruitmentsByStatus(UUID tenantId,
                                                            RecruitmentStatus recruitmentStatus) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<Recruitment> recruitments = recruitmentRepository
                .findByRecruitmentStatus(recruitmentStatus);
        return recruitments.stream()
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .map(recruitmentMapper::mapToDto)
                .toList();
    }

    public List<RecruitmentResponse> getRecruitmentsByMode(UUID tenantId,
                                                           RecruitmentMode recruitmentModer) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<Recruitment> recruitments = recruitmentRepository
                .findByRecruitmentMode(recruitmentModer);
        return recruitments.stream()
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .map(recruitmentMapper::mapToDto)
                .toList();
    }

    @Transactional
    public RecruitmentResponse updateRecruitment(UUID tenantId,
                                                 UUID recruitmentId,
                                                 RecruitmentRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil.getRecruitmentById(tenant.getId(), recruitmentId);
        if (recruitment.getRecruitmentStatus().equals(RecruitmentStatus.APPROVED)) {
            throw new IllegalStateException(
                    "Recruitment has already been approved. Updating an approved recruitment is not possible.");
        }
        if (recruitment.getRecruitmentStatus().equals(RecruitmentStatus.REJECTED)) {
            throw new IllegalStateException(
                    "Recruitment has already been rejected. Updating a rejected recruitment is not possible.");
        }
        recruitment = recruitmentMapper.updateRecruitment(tenant, recruitment, request);
        recruitment = recruitmentRepository.save(recruitment);
        return recruitmentMapper.mapToDto(recruitment);
    }

    @Transactional
    public RecruitmentResponse approveRecruitment(UUID tenantId,
                                                  UUID recruitmentId,
                                                  RecruitmentApproveRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil.getRecruitmentById(tenant.getId(), recruitmentId);
        recruitment = recruitmentMapper.approveRecruitment(recruitment, request);
        recruitment = recruitmentRepository.save(recruitment);
        return recruitmentMapper.mapToDto(recruitment);
    }

    @Transactional
    public void deleteRecruitment(UUID tenantId,
                                  UUID recruitmentId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil.getRecruitmentById(tenant.getId(), recruitmentId);
        recruitmentRepository.delete(recruitment);
    }
}