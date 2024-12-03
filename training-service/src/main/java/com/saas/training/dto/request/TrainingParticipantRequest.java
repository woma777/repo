package com.saas.training.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingParticipantRequest {

    private String SearchBy;
    private String firstName;
    private String middleName;
    private String lastName;
    @NotNull(message = "Participant employee ID cannot be null")
    private String participantEmployeeId;

    @Size(max = 250, message = "Reason must be less than 250 characters")
    private String reason;
}
