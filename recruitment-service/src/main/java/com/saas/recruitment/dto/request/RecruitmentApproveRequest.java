package com.saas.recruitment.dto.request;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentApproveRequest {

    @Min(value = 1, message = "Number of employees approved must be at least 1")
    private Integer numberOfEmployeesApproved;

    @Size(max = 20, message = "Vacancy number must be less than 20 characters")
    private String vacancyNumber;

    @NotNull(message = "decision cannot be null")
    private String decision;

    @Size(max = 250, message = "Remark must be less than 250 characters")
    private String remark;
}
