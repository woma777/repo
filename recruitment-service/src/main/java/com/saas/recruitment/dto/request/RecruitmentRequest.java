package com.saas.recruitment.dto.request;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentRequest {

    @NotNull(message = "Requester employee ID cannot be null")
    private String requesterEmployeeId;

    @NotNull(message = "Department ID cannot be null")
    private UUID departmentId;

    @NotNull(message = "Job ID cannot be null")
    private UUID jobId;

    @Min(value = 1, message = "Number of employees requested must be at least 1")
    private Integer numberOfEmployeesRequested;

    @NotNull(message = "Recruitment type cannot be null")
    private String recruitmentType;

    @NotNull(message = "Recruitment mode cannot be null")
    private String recruitmentMode;

    @Size(max = 250, message = "Remark must be less than 250 characters")
    private String remark;
}
