package com.saas.employee.service;

import com.saas.employee.dto.clientDto.TenantDto;
import com.saas.employee.dto.request.EducationRequest;
import com.saas.employee.dto.response.EducationResponse;
import com.saas.employee.model.Education;
import com.saas.employee.model.Employee;
import com.saas.employee.exception.ResourceNotFoundException;
import com.saas.employee.mapper.EducationMapper;
import com.saas.employee.repository.EducationRepository;
import com.saas.employee.utility.FileUtil;
import com.saas.employee.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;
    private final EducationMapper educationMapper;
    private final ValidationUtil validationUtil;

    public EducationResponse addEducation(UUID tenantId,
                                          UUID employeeId,
                                          EducationRequest request,
                                          MultipartFile file) throws IOException {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Education education = educationMapper.mapToEntity(tenant, employee, request, file);
        education = educationRepository.save(education);
        return educationMapper.mapToDto(education);
    }

    public List<EducationResponse> getAllEducations(UUID tenantId,
                                                    UUID employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        List<Education> educations = educationRepository.findByEmployeeId(employee.getId());
        return educations.stream()
                .filter(edu -> edu.getTenantId().equals(tenant.getId()))
                .map(educationMapper::mapToDto)
                .toList();
    }

    public List<EducationResponse> getEmployeeEducations(UUID tenantId,
                                                         String employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeByEmployeeId(tenant.getId(), employeeId);
        List<Education> educations = educationRepository.findByEmployeeId(employee.getId());
        return educations.stream()
                .filter(edu -> edu.getTenantId().equals(tenant.getId()))
                .map(educationMapper::mapToDto)
                .toList();
    }

    public EducationResponse getEducationById(UUID tenantId,
                                              UUID employeeId,
                                              UUID educationId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Education education = getEducationById(tenant.getId(), employee, educationId);
        return educationMapper.mapToDto(education);
    }

    public byte[] getEducationDocumentById(UUID tenantId,
                                           UUID employeeId,
                                           UUID educationId,
                                           MediaType mediaType) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Education education = getEducationById(tenant.getId(), employee, educationId);
        switch (education.getFileType()) {
            case "application/pdf":
                mediaType = MediaType.valueOf(MediaType.APPLICATION_PDF_VALUE);
                break;
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                mediaType = MediaType.valueOf(
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                break;
            case "image/jpeg":
                mediaType = MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE);
                break;
            case "image/png":
                mediaType = MediaType.valueOf(MediaType.IMAGE_PNG_VALUE);
                break;
            case "image/gif":
                mediaType = MediaType.valueOf(MediaType.IMAGE_GIF_VALUE);
                break;
            default:
                throw new IllegalArgumentException("Unsupported file type: " + education.getFileType());
        }
        byte[] fileBytes = FileUtil.decompressFile(education.getFileBytes());
        if (fileBytes.length == 0) {
            throw new ResourceNotFoundException("Education file is not available");
        }
        return fileBytes;
    }

    public EducationResponse updateEducation(UUID tenantId,
                                             UUID employeeId,
                                             UUID educationId,
                                             EducationRequest request,
                                             MultipartFile file) throws IOException {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Education education = getEducationById(tenant.getId(), employee, educationId);
        education = educationMapper.updateEducation(tenant, education, request, file);
        education = educationRepository.save(education);
        return educationMapper.mapToDto(education);
    }

    public void deleteEducation(UUID tenantId,
                                UUID employeeId,
                                UUID educationId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        Education education = getEducationById(tenant.getId(), employee, educationId);
        educationRepository.delete(education);
    }

    private Education getEducationById(UUID tenantId,
                                       Employee employee,
                                       UUID educationId) {

        return educationRepository.findById(educationId)
                .filter(edu -> edu.getTenantId().equals(tenantId))
                .filter(edu -> edu.getEmployee().equals(employee))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Education not found with id '" + educationId + "'"));
    }
}