package com.saas.leave.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class IncidentType extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String incidentTypeName;
    @ManyToOne
    @JoinColumn(name = "leaveRequest_id")
    private LeaveRequest leaveRequest;

}
