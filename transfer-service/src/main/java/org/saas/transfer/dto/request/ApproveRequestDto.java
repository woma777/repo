package org.saas.transfer.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.saas.transfer.enums.Decision;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApproveRequestDto {
    @NotNull(message = "Decision must be choose ")
    private Decision decision;
    @Size(max = 150)
    private String comment;
}
