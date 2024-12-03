package org.saas.transfer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.saas.transfer.enums.MovementType;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectAssignment extends Base{
    @NotNull
    @Column(nullable = false)
    private UUID employeeId;
    @NotNull
    @Column(nullable = false)
    private UUID departmentId;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType movementType;
    @NotNull
    @Column(nullable = false)
    private Integer referenceNumber;
    @NotNull
    @Size(max = 100)
    private String remark;





}
