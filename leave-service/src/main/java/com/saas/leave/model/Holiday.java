package com.saas.leave.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Holiday extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String holidayName;
    @OneToMany(mappedBy = "holiday", fetch = FetchType.LAZY)
    private List<HolidayManagement> holidayManagements;
    private UUID tenantId;

}