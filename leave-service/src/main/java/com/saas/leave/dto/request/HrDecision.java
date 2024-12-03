package com.saas.leave.dto.request;

import com.saas.leave.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HrDecision {
    private Status decision;
    private String comment;
}
