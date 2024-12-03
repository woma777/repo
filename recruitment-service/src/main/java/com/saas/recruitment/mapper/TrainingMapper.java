package com.saas.recruitment.mapper;

import com.saas.recruitment.dto.clientDto.TenantDto;
import com.saas.recruitment.dto.request.TrainingRequest;
import com.saas.recruitment.dto.response.TrainingResponse;
import com.saas.recruitment.model.Applicant;
import com.saas.recruitment.model.Training;
import com.saas.recruitment.utility.FileUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class TrainingMapper {

    public Training mapToEntity(TenantDto tenant,
                                Applicant applicant,
                                TrainingRequest request,
                                MultipartFile file) throws IOException {

        Training training = new Training ();
        training.setTenantId(tenant.getId());
        training.setApplicant(applicant);
        training.setTrainingTitle (request.getTrainingTitle ());
        training.setInstitution (request.getInstitution ());
        training.setSponsoredBy (request.getSponsoredBy ());
        training.setStartDate (request.getStartDate ());
        training.setEndDate (request.getEndDate ());
        training.setAward(request.getAward());

        if (file != null) {
            training.setFileName(file.getOriginalFilename());
            training.setFileType(file.getContentType());
            training.setFileBytes(FileUtil.compressFile(file.getBytes()));
        }

        return training;
    }

    public TrainingResponse mapToDto(Training training) {

        TrainingResponse response = new TrainingResponse ();
        response.setId (training.getId ());
        response.setApplicantId(training.getApplicant().getId());
        response.setTrainingTitle (training.getTrainingTitle ());
        response.setInstitution (training.getInstitution ());
        response.setSponsoredBy (training.getSponsoredBy ());
        response.setStartDate (training.getStartDate ());
        response.setEndDate (training.getEndDate ());
        response.setAward(training.getAward());
        response.setTenantId (training.getTenantId ());
        response.setCreatedAt (training.getCreatedAt ());
        response.setUpdatedAt (training.getUpdatedAt ());
        response.setCreatedBy (training.getCreatedBy ());
        response.setUpdatedBy (training.getUpdatedBy ());

        return response;
    }

    public Training updateTraining(Training training,
                                   TrainingRequest request,
                                   MultipartFile file) throws IOException {

        if (request.getTrainingTitle () != null) {
            training.setTrainingTitle(request.getTrainingTitle());
        }
        if (request.getInstitution () != null) {
            training.setInstitution(request.getInstitution());
        }
        if (request.getSponsoredBy () != null) {
            training.setSponsoredBy(request.getSponsoredBy());
        }
        if (request.getStartDate () != null) {
            training.setStartDate(request.getStartDate());
        }
        if (request.getEndDate () != null) {
            training.setEndDate(request.getEndDate());
        }
        if (request.getAward() != null) {
            training.setAward(request.getAward());
        }

        if (file != null && !file.isEmpty()) {
            training.setFileName(file.getOriginalFilename());
            training.setFileType(file.getContentType());
            training.setFileBytes(FileUtil.compressFile(file.getBytes()));
        }

        return training;
    }
}
