package org.saas.transfer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.saas.transfer.enums.Decision;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TransferRequest extends Base {
    @NotNull
    @Column(nullable = false)
    private UUID departmentId;
    @NotNull
    @Column(nullable = false)
    private UUID employeeId;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Decision decision;
    private String comment;



}
