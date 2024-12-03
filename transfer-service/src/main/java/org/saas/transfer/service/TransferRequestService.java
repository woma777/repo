package org.saas.transfer.service;

import lombok.RequiredArgsConstructor;
import org.saas.transfer.dto.clientDto.TenantDto;
import org.saas.transfer.dto.request.ApproveRequestDto;
import org.saas.transfer.dto.request.RequestDto;
import org.saas.transfer.dto.response.ResponseDto;
import org.saas.transfer.enums.Decision;
import org.saas.transfer.exception.ResourceNotFoundException;
import org.saas.transfer.mapper.RequestMapper;
import org.saas.transfer.model.TransferRequest;
import org.saas.transfer.repository.TransferRequestRepository;
import org.saas.transfer.utility.ValidationUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferRequestService {
    private final TransferRequestRepository transferRequestRepository;
    private final ValidationUtil validationUtil;
    private final RequestMapper requestMapper;

    public ResponseDto CreateTransferRequest(UUID tenantId, RequestDto requestDto) {
        TenantDto tenantDto = validationUtil.getTenantById(tenantId);
        TransferRequest transferRequest = requestMapper.mapToEntity(tenantDto, requestDto);
        transferRequestRepository.save(transferRequest);
        return requestMapper.mapToDto(transferRequest);

    }
    public List<ResponseDto> getAllTransferRequests(UUID tenantId) {
        TenantDto tenantDto = validationUtil.getTenantById(tenantId);
        List<TransferRequest> transferRequests = transferRequestRepository.findAll();
        return  transferRequests
                .stream()
                .filter(request->request.getTenantId().equals(tenantDto.getId()))
                .map(requestMapper::mapToDto)
                .toList();

    }
    public ResponseDto getTransferRequestById(UUID tenantId, UUID transferRequestId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TransferRequest transferRequest =transferRequestRepository.getTransferRequestById(tenant.getId(), transferRequestId);


        return requestMapper.mapToDto(transferRequest);
    }

    public ResponseDto updateTransferRequestById(UUID tenantId, UUID transferRequestId, RequestDto requestDto) {
        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TransferRequest transferRequest = transferRequestRepository.getTransferRequestById(tenant.getId(), transferRequestId);
        transferRequest= requestMapper.updateEntity(transferRequest, requestDto);
        transferRequestRepository.save(transferRequest);
        return requestMapper.mapToDto(transferRequest);

    }
    public void deleteTransferRequest(UUID tenantId, UUID transferRequestId) {
        TransferRequest transferRequest = transferRequestRepository.getTransferRequestById(tenantId, transferRequestId);
        transferRequestRepository.delete(transferRequest);

    }
    public ResponseDto ApproveTransferRequest(UUID tenantId, UUID transferRequestId, ApproveRequestDto approveRequestDto) {
        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TransferRequest transferRequest = transferRequestRepository.getTransferRequestById(tenant.getId(), transferRequestId);
        if(approveRequestDto.getDecision() != null){
            transferRequest.setDecision(approveRequestDto.getDecision());
        }
        if(approveRequestDto.getComment() != null){
            transferRequest.setComment(approveRequestDto.getComment());
        }
        transferRequestRepository.save(transferRequest);
        return requestMapper.mapToDto(transferRequest);

    }



}

