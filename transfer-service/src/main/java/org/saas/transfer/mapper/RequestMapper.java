package org.saas.transfer.mapper;

import lombok.RequiredArgsConstructor;
import org.saas.transfer.dto.clientDto.DepartmentDto;
import org.saas.transfer.dto.clientDto.EmployeeDto;
import org.saas.transfer.dto.clientDto.TenantDto;
import org.saas.transfer.dto.request.ApproveRequestDto;
import org.saas.transfer.dto.request.AssignmentRequestDto;
import org.saas.transfer.dto.request.RequestDto;
import org.saas.transfer.dto.response.ResponseDto;
import org.saas.transfer.model.TransferRequest;
import org.saas.transfer.utility.ValidationUtil;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestMapper {
    private final ValidationUtil validationUtil;
    public TransferRequest mapToEntity(TenantDto tenantDto,RequestDto requestDto) {
        DepartmentDto departmentDto = validationUtil.getDepartmentById(tenantDto.getId(),requestDto.getDepartmentId());
        EmployeeDto employeeDto = validationUtil.getEmployeeByEmployeeId(tenantDto.getId(), String.valueOf(requestDto.getEmployeeId()));

        TransferRequest transferRequestDto = new TransferRequest();
        transferRequestDto.setTenantId(tenantDto.getId());
        transferRequestDto.setDepartmentId(departmentDto.getId());
        transferRequestDto.setEmployeeId(employeeDto.getId());
        transferRequestDto.setComment(requestDto.getComment());

        return transferRequestDto;
    }
    public ResponseDto mapToDto(TransferRequest transferRequest) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setId(transferRequest.getId());
        responseDto.setDepartmentId(transferRequest.getDepartmentId());
        responseDto.setEmployeeId(transferRequest.getEmployeeId());
        responseDto.setComment(transferRequest.getComment());
        return responseDto;
    }

    public TransferRequest updateEntity( TransferRequest transferRequest, RequestDto requestDto) {
//        DepartmentDto departmentDto = validationUtil.getDepartmentById(tenantDto.getId(),transferRequest.getDepartmentId());
        if(requestDto.getDepartmentId() != null){
            transferRequest.setDepartmentId(requestDto.getDepartmentId());
        }
        return transferRequest;
    }

//    public TransferRequest TransferApprove(TransferRequest transferRequest, ApproveRequestDto approveRequestDto) {
//        if(approveRequestDto.getDecision() != null){
//            transferRequest.setDecision(approveRequestDto.getDecision());
//        }
//        if(approveRequestDto.getComment() != null){
//            transferRequest.setComment(approveRequestDto.getComment());
//        }
//        return transferRequest;
//
//    }
}
