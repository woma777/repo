package org.saas.transfer.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    @NotNull(message = "Department ID cannot be null")
    private UUID departmentId;
    @NotNull(message = "Employee ID cannot be null")
    private UUID employeeId;
    @Size(max = 150)
    private String comment;


}
