package com.saas.employee.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeHistoryResponse extends BaseResponse {

    private UUID employeeId;
    private UUID departmentId;
    private UUID jobId;
    private UUID payGradeId;
    private LocalDate startDate;
    private LocalDate endDate;
}
