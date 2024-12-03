package com.saas.training.service;

import com.saas.training.dto.clientDto.TenantDto;
import com.saas.training.dto.request.CheckedDocumentRequest;
import com.saas.training.dto.response.CheckedDocumentResponse;
import com.saas.training.exception.ResourceExistsException;
import com.saas.training.exception.ResourceNotFoundException;
import com.saas.training.repository.CheckedDocumentRepository;
import com.saas.training.repository.PreServiceTraineeRepository;
import com.saas.training.utility.ValidationUtil;
import com.saas.training.model.CheckedDocument;
import com.saas.training.model.PreServiceTrainee;
import com.saas.training.mapper.CheckedDocumentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckedDocumentService {

    private final CheckedDocumentRepository checkedDocumentRepository;
    private final CheckedDocumentMapper checkedDocumentMapper;
    private final PreServiceTraineeRepository preServiceTraineeRepository;
    private final ValidationUtil validationUtil;

    @Transactional
    public CheckedDocumentResponse addCheckedDocument(UUID tenantId,
                                                      CheckedDocumentRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        CheckedDocument checkedDocument = checkedDocumentMapper.mapToEntity(tenant, request);
        if (checkedDocumentRepository.existsByTenantIdAndDocumentName(
                tenant.getId(), request.getDocumentName())) {
            throw new ResourceExistsException(
                    "Document with name '" + request.getDocumentName() + "' already exists");
        }
        checkedDocument = checkedDocumentRepository.save(checkedDocument);
        return checkedDocumentMapper.mapToDto(checkedDocument);
    }

    public List<CheckedDocumentResponse> getAllCheckedDocuments(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<CheckedDocument> checkedDocuments = checkedDocumentRepository.findAll();
        return checkedDocuments.stream()
                .filter(title -> title.getTenantId().equals(tenant.getId()))
                .map(checkedDocumentMapper::mapToDto)
                .toList();
    }

    public CheckedDocumentResponse getCheckedDocumentById(UUID tenantId,
                                                          UUID documentId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        CheckedDocument checkedDocument = validationUtil.getCheckedDocumentById(tenant.getId(), documentId);
        return checkedDocumentMapper.mapToDto(checkedDocument);
    }

    public List<CheckedDocumentResponse> getCheckedDocumentByTraineeId(UUID tenantId,
                                                                       UUID traineeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceTrainee preServiceTrainee = validationUtil.getTraineeById(tenant.getId(), traineeId);
        Set<CheckedDocument> checkedDocuments = preServiceTrainee.getCheckedDocuments();
        return checkedDocuments.stream()
                .filter(title -> title.getTenantId().equals(tenant.getId()))
                .map(checkedDocumentMapper::mapToDto)
                .toList();
    }

    @Transactional
    public CheckedDocumentResponse updateCheckedDocument(UUID tenantId,
                                                         UUID documentId,
                                                         CheckedDocumentRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        CheckedDocument checkedDocument = validationUtil.getCheckedDocumentById(tenant.getId(), documentId);
        if (checkedDocumentRepository.existsByTenantIdAndDocumentNameAndIdNot(
                tenant.getId(), request.getDocumentName(), checkedDocument.getId())) {
            throw new ResourceExistsException(
                    "Document with name '" + request.getDocumentName() + "' already exists");
        }
        checkedDocument = checkedDocumentMapper.updateEntity(checkedDocument, request);
        checkedDocument = checkedDocumentRepository.save(checkedDocument);
        return checkedDocumentMapper.mapToDto(checkedDocument);
    }

    @Transactional
    public void removeCheckedDocumentFromTrainee(UUID tenantId,
                                                 UUID traineeId,
                                                 UUID documentId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceTrainee preServiceTrainee = validationUtil.getTraineeById(tenant.getId(), traineeId);
        CheckedDocument documentToRemove = validationUtil.getCheckedDocumentById(tenant.getId(), documentId);
        Set<CheckedDocument> checkedDocuments = preServiceTrainee.getCheckedDocuments();
        boolean removed = checkedDocuments.remove(documentToRemove);
        if (!removed) {
            throw new ResourceNotFoundException(
                    "Document not associated with the trainee '" + documentId + "'");
        }
        preServiceTrainee.setCheckedDocuments(checkedDocuments);
        preServiceTraineeRepository.save(preServiceTrainee);
    }

    @Transactional
    public void deleteCheckedDocument(UUID tenantId,
                                      UUID documentId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        CheckedDocument checkedDocument = validationUtil.getCheckedDocumentById(tenant.getId(), documentId);
        Set<PreServiceTrainee> preServiceTrainees = checkedDocument.getPreServiceTrainees();
        for (PreServiceTrainee trainee : preServiceTrainees) {
            trainee.getCheckedDocuments().remove(checkedDocument);
            preServiceTraineeRepository.save(trainee);
        }
        checkedDocumentRepository.delete(checkedDocument);
    }
}