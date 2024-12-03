package com.saas.leave.mapper;

import com.saas.leave.dto.request.DepartmentApproveRequest;
import com.saas.leave.dto.request.HrDecision;
import com.saas.leave.dto.request.LeaveRequestRequest;
import com.saas.leave.dto.response.LeaveRequestResponse;
import com.saas.leave.enums.Status;
import com.saas.leave.model.BudgetYear;
import com.saas.leave.model.LeaveRequest;
import com.saas.leave.model.LeaveType;
import com.saas.leave.repository.LeaveTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class LeaveRequestMapper {

    private final LeaveTypeRepository leaveTypeRepository;

    public LeaveRequest toEntity(LeaveRequestRequest requestDTO) {
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployeeId(requestDTO.getEmployeeId());
        leaveRequest.setNumberOfDays(requestDTO.getNumberOfDays());
        leaveRequest.setLeaveStart(requestDTO.getLeaveStart());
        leaveRequest.setDay(requestDTO.getDay());
        leaveRequest.setDescription(requestDTO.getDescription());
        leaveRequest.setCreatedAt(LocalDateTime.now());

        // Setting BudgetYear with ID
        if (requestDTO.getBudgetYearId() != null) {
            BudgetYear budgetYear = new BudgetYear();
            budgetYear.setId(requestDTO.getBudgetYearId());
            leaveRequest.setBudgetYear(budgetYear);
        }

        // Fetching LeaveType and setting it
        LeaveType leaveType = leaveTypeRepository.findById(requestDTO.getLeaveTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid leaveTypeId: " + requestDTO.getLeaveTypeId()));
        leaveRequest.setLeaveType(leaveType);

        return leaveRequest;
    }

    public LeaveRequestResponse toResponseDTO(LeaveRequest entity) {
        LeaveRequestResponse responseDTO = new LeaveRequestResponse();
        responseDTO.setId(entity.getId());
        responseDTO.setEmployeeId(entity.getEmployeeId());
        responseDTO.setNumberOfDays(entity.getNumberOfDays());
        responseDTO.setLeaveStart(entity.getLeaveStart());
        responseDTO.setDay(entity.getDay());
        responseDTO.setReturnDate(entity.getReturnDate());
        responseDTO.setDescription(entity.getDescription());
        responseDTO.setNumberOfApprovedDays(entity.getNumberOfApprovedDays());
        responseDTO.setComment(entity.getComment());

        // Handle potential nulls for decisions
        responseDTO.setDepartmentDecision(entity.getDepartmentDecision() != null ?
                entity.getDepartmentDecision() : Status.PENDING);
        responseDTO.setHrDecision(entity.getHrDecision() != null ?
                entity.getHrDecision(): Status.PENDING);


        // Ensure LeaveType and BudgetYear are not null before accessing their IDs
        if (entity.getLeaveType() != null) {
            responseDTO.setLeaveTypeId(entity.getLeaveType().getId());
        } else {
            responseDTO.setLeaveTypeId(null);
        }

        if (entity.getBudgetYear() != null) {
            responseDTO.setBudgetYearId(entity.getBudgetYear().getId());
        } else {
            responseDTO.setBudgetYearId(null);
        }

        responseDTO.setTenantId(entity.getTenantId());
        return responseDTO;
    }

    public void updateLeaveRequest(LeaveRequest leaveRequest, LeaveRequestRequest requestDTO) {
        if (requestDTO.getEmployeeId() != null) {
            leaveRequest.setEmployeeId(requestDTO.getEmployeeId());
            log.debug("Updated employeeId to {}", requestDTO.getEmployeeId());
        }


            leaveRequest.setNumberOfDays(requestDTO.getNumberOfDays());
            log.debug("Updated numberOfDays to {}", requestDTO.getNumberOfDays());


        if (requestDTO.getLeaveStart() != null) {
            leaveRequest.setLeaveStart(requestDTO.getLeaveStart());
            log.debug("Updated leaveStart to {}", requestDTO.getLeaveStart());
        }
        if (requestDTO.getDay() != null) {
            leaveRequest.setDay(requestDTO.getDay());
            log.debug("Updated day to {}", requestDTO.getDay());
        }
        if (requestDTO.getDescription() != null) {
            leaveRequest.setDescription(requestDTO.getDescription());
            log.debug("Updated description to {}", requestDTO.getDescription());
        }
        if (requestDTO.getLeaveTypeId() != null) {
            LeaveType leaveType = leaveTypeRepository.findById(requestDTO.getLeaveTypeId())
                    .orElseThrow(() -> {
                        log.error("Invalid leaveTypeId: {}", requestDTO.getLeaveTypeId());
                        return new IllegalArgumentException("Invalid leaveTypeId: " + requestDTO.getLeaveTypeId());
                    });
            leaveRequest.setLeaveType(leaveType);
            log.debug("Updated leaveTypeId to {}", requestDTO.getLeaveTypeId());
        }
    }

    public LeaveRequest departmentApprove(LeaveRequest leaveRequest, DepartmentApproveRequest approveRequest) {
        if (approveRequest.getNumberOfApprovedDays() != null) {
            leaveRequest.setNumberOfApprovedDays(approveRequest.getNumberOfApprovedDays());
            log.debug("Updated numberOfApprovedDays to {}", approveRequest.getNumberOfApprovedDays());
        }

        if (approveRequest.getDecision() != null) {
            leaveRequest.setDepartmentDecision(approveRequest.getDecision());
            log.debug("Updated departmentDecision to {}", approveRequest.getDecision());
        }
        if(approveRequest.getComment() != null) {
            leaveRequest.setComment(leaveRequest.getComment());
        }

        return leaveRequest;
    }
    public LeaveRequest hRApproval(LeaveRequest leaveRequest, HrDecision hrDecision) {
        if(hrDecision.getDecision() != null) {
            leaveRequest.setHrDecision(hrDecision.getDecision());
        }
        if (hrDecision.getComment() != null) {
            leaveRequest.setComment(hrDecision.getComment());
        }
        return leaveRequest;

    }
}