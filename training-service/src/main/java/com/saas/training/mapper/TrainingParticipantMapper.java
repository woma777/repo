package com.saas.training.mapper;

import com.saas.training.dto.clientDto.EmployeeDto;
import com.saas.training.dto.clientDto.TenantDto;
import com.saas.training.dto.request.TrainingParticipantRequest;
import com.saas.training.dto.response.TrainingParticipantResponse;
import com.saas.training.utility.ValidationUtil;
import com.saas.training.model.Training;
import com.saas.training.model.TrainingParticipant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingParticipantMapper {

    private final ValidationUtil validationUtil;

    public TrainingParticipant mapToEntity(TenantDto tenant,
                                           Training training,
                                           EmployeeDto employee,
                                           TrainingParticipantRequest request) {

        TrainingParticipant trainingParticipant = new TrainingParticipant();
        trainingParticipant.setTenantId(tenant.getId());
        trainingParticipant.setParticipantEmployeeId(employee.getId());
        trainingParticipant.setTraining(training);
        trainingParticipant.setReason(request.getReason());

        return trainingParticipant;
    }

    public TrainingParticipantResponse mapToDto(TrainingParticipant trainingParticipant) {

        TrainingParticipantResponse response = new TrainingParticipantResponse();
        response.setId(trainingParticipant.getId());
        response.setParticipantEmployeeId(trainingParticipant.getParticipantEmployeeId());
        response.setTrainingId(trainingParticipant.getTraining().getId());
        response.setReason(trainingParticipant.getReason());
        response.setTenantId(trainingParticipant.getTenantId());
        response.setCreatedAt(trainingParticipant.getCreatedAt());
        response.setUpdatedAt(trainingParticipant.getUpdatedAt());
        response.setCreatedBy(trainingParticipant.getCreatedBy());
        response.setUpdatedBy(trainingParticipant.getUpdatedBy());

        return response;
    }
}
