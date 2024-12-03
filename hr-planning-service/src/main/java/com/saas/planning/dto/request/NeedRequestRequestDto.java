package com.saas.planning.dto.request;

import com.saas.planning.enums.EmploymentType;
import com.saas.planning.enums.HowToBeFilled;
import com.saas.planning.enums.WhenToBe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeedRequestRequestDto {

    private Integer noOfPosition;
    private EmploymentType employmentType;
    private HowToBeFilled howToBeFilled;
    private WhenToBe whenToBe;
    private String remark;
    private UUID budgetYearId;
    private UUID departmentId;
    private UUID staffPlanId;


}
