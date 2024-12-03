package org.saas.transfer.mapper;

import lombok.RequiredArgsConstructor;
import org.saas.transfer.dto.clientDto.DepartmentDto;
import org.saas.transfer.dto.clientDto.EmployeeDto;
import org.saas.transfer.dto.clientDto.TenantDto;
import org.saas.transfer.dto.request.AssignmentRequestDto;
import org.saas.transfer.dto.response.AssignmentResponseDto;
import org.saas.transfer.model.DirectAssignment;
import org.saas.transfer.utility.ValidationUtil;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DirectAssignmentMapper {
    private final ValidationUtil validationUtil;

    public DirectAssignment mapToEntity(TenantDto tenantDto, AssignmentRequestDto assignmentRequestDto) {
        DepartmentDto departmentDto = validationUtil.getDepartmentById(tenantDto.getId(),assignmentRequestDto.getDepartmentId());
        EmployeeDto employeeDto = validationUtil.getEmployeeByEmployeeId(tenantDto.getId(), String.valueOf(assignmentRequestDto.getEmployeeId()));
        DirectAssignment directAssignment = new DirectAssignment();
        directAssignment.setDepartmentId(departmentDto.getId());
        directAssignment.setEmployeeId(employeeDto.getId());
        directAssignment.setReferenceNumber(assignmentRequestDto.getReferenceNumber());
        directAssignment.setRemark(assignmentRequestDto.getRemark());
        directAssignment.setMovementType(assignmentRequestDto.getMovementType());
        return directAssignment;

    }
    public AssignmentResponseDto mapToDto(DirectAssignment directAssignment) {
        AssignmentResponseDto assignmentResponseDto = new AssignmentResponseDto();
        assignmentResponseDto.setId(directAssignment.getId());
        assignmentResponseDto.setDepartmentId(directAssignment.getDepartmentId());
        assignmentResponseDto.setEmployeeId(directAssignment.getEmployeeId());
        assignmentResponseDto.setReferenceNumber(directAssignment.getReferenceNumber());
        assignmentResponseDto.setRemark(directAssignment.getRemark());
        assignmentResponseDto.setMovementType(directAssignment.getMovementType());
        return assignmentResponseDto;
    }

    public DirectAssignment updateEntity( DirectAssignment directAssignment,AssignmentRequestDto assignmentRequestDto) {

        if(assignmentRequestDto.getEmployeeId()!=null){
            directAssignment.setEmployeeId(assignmentRequestDto.getEmployeeId());
        }
        if(assignmentRequestDto.getDepartmentId()!=null){
            directAssignment.setDepartmentId(assignmentRequestDto.getDepartmentId());
        }
        if(assignmentRequestDto.getReferenceNumber()!=null){
            directAssignment.setReferenceNumber(assignmentRequestDto.getReferenceNumber());
        }
        if(assignmentRequestDto.getRemark()!=null){
            directAssignment.setRemark(assignmentRequestDto.getRemark());
        }
        if(assignmentRequestDto.getMovementType()!=null){
            directAssignment.setMovementType(assignmentRequestDto.getMovementType());
        }
        return directAssignment;

    }
}
