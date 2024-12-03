package com.saas.employee.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageRequest {

    @NotNull(message = "Listening proficiency cannot be null")
    private String listening;

    @NotNull(message = "Speaking proficiency cannot be null")
    private String speaking;

    @NotNull(message = "Reading proficiency cannot be null")
    private String reading;

    @NotNull(message = "Writing proficiency cannot be null")
    private String writing;

    @NotNull(message = "Language name ID cannot be null")
    private UUID languageNameId;
}
