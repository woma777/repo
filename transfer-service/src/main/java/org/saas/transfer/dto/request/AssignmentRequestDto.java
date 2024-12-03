package org.saas.transfer.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.saas.transfer.enums.MovementType;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AssignmentRequestDto {
    @NotNull(message = "Department ID cannot be null")
    private UUID departmentId;
    @NotNull(message = "EmployeeId must be field")
    private UUID employeeId;
    @NotNull(message = "Movement type must be choose")
    private MovementType movementType;
    @NotNull(message = "Reference number must be field")
    private Integer referenceNumber;
    @NotNull(message = "Remark must be field")
    private String remark;

}
