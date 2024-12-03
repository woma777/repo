package com.saas.leave.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayResponse {
    private UUID id;
    private String holidayName;
    private UUID tenantId;
}
