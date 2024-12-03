package com.saas.training.service;

import com.saas.training.dto.clientDto.TenantDto;
import com.saas.training.dto.request.InternshipPaymentRequest;
import com.saas.training.dto.response.InternshipPaymentResponse;
import com.saas.training.exception.ResourceExistsException;
import com.saas.training.exception.ResourceNotFoundException;
import com.saas.training.repository.InternshipPaymentRepository;
import com.saas.training.utility.ValidationUtil;
import com.saas.training.model.InternshipPayment;
import com.saas.training.model.InternshipStudent;
import com.saas.training.mapper.InternshipPaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InternshipPaymentService {

    private final InternshipPaymentRepository internshipPaymentRepository;
    private final InternshipPaymentMapper internshipPaymentMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public InternshipPaymentResponse createInternshipPayment(UUID tenantId,
                                                             InternshipPaymentRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        InternshipStudent internshipStudent = validationUtil
                .getStudentById(tenant.getId(), request.getInternId());
        if (internshipPaymentRepository.existsByTenantIdAndInternshipStudentId(
                tenant.getId(), request.getInternId())) {
            throw new ResourceExistsException(
                    "Internship student with id '" + request.getInternId() + "' already paid");
        }
        if (internshipStudent.getPlacedDepartmentId() == null) {
            throw new IllegalStateException(
                    "Cannot add payment for non-placed internship student.");
        }
        InternshipPayment internshipPayment = internshipPaymentMapper
                .mapToEntity(tenant, internshipStudent, request);
        internshipPayment = internshipPaymentRepository.save(internshipPayment);
        return internshipPaymentMapper.mapToDto(internshipPayment);
    }

    public List<InternshipPaymentResponse> getAllInternshipPayments(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<InternshipPayment> internshipPayments = internshipPaymentRepository.findAll();
        return internshipPayments.stream()
                .filter(payment -> payment.getTenantId().equals(tenant.getId()))
                .map(internshipPaymentMapper::mapToDto)
                .toList();
    }

    public InternshipPaymentResponse getInternshipPaymentById(UUID tenantId,
                                                              UUID paymentId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        InternshipPayment internshipPayment = getPaymentById(tenant.getId(), paymentId);
        return internshipPaymentMapper.mapToDto(internshipPayment);
    }

    @Transactional
    public InternshipPaymentResponse updateInternshipPayment(UUID tenantId,
                                                             UUID paymentId,
                                                             InternshipPaymentRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        InternshipPayment internshipPayment = getPaymentById(tenant.getId(), paymentId);
        if (internshipPaymentRepository.existsByTenantIdAndInternshipStudentIdAndIdNot(
                tenant.getId(), request.getInternId(), internshipPayment.getId())) {
            throw new ResourceExistsException(
                    "Internship student with id '" + request.getInternId() + "' already paid");
        }
        internshipPayment = internshipPaymentMapper.updateEntity(internshipPayment, request);
        internshipPayment = internshipPaymentRepository.save(internshipPayment);
        return internshipPaymentMapper.mapToDto(internshipPayment);
    }

    public void deleteInternshipPayment(UUID tenantId,
                                        UUID paymentId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        InternshipPayment internshipPayment = getPaymentById(tenant.getId(), paymentId);
        internshipPaymentRepository.delete(internshipPayment);
    }

    public InternshipPayment getPaymentById(UUID tenantId, UUID paymentId) {

        return internshipPaymentRepository.findById(paymentId)
                .filter(payment -> payment.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found with id '" + paymentId + "'"));
    }
}