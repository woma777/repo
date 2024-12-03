package org.saas.transfer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.saas.transfer.enums.MovementType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentResponseDto {
    private UUID id;
    private UUID departmentId;
    private UUID employeeId;
    private MovementType movementType;
    private Integer referenceNumber;
    private String remark;
    private UUID tenantId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
