package com.saas.planning.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AnnualRecruitmentAndPromotion extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @NotNull(message = "Budget Year ID cannot be null")
    private UUID budgetYearId;
    @NotNull(message = "Work Unit ID cannot be null")
    private UUID workUnitId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hr_need_request_id", nullable = false)
    private HrNeedRequest hrNeedRequest;
    @NotNull(message = "Tenant ID cannot be null")
    private UUID tenantId;
    @NotNull(message = "Grand Total cannot be null")
    @Min(value = 0, message = "Grand Total must be greater than or equal to 0")
    private Integer grandTotal;
    private Boolean internalRecruitment;
    private Boolean externalRecruitment;
    private Boolean all;
    private String remark;
    @PastOrPresent(message = "Processed Date must be in the past or present")
    private LocalDate processedDate;
    @NotBlank(message = "Prepared By cannot be blank")
    private String preparedBy;
    private String comment;
}
