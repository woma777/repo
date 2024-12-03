package com.saas.training.service;

import com.saas.training.dto.clientDto.DepartmentDto;
import com.saas.training.dto.clientDto.TenantDto;
import com.saas.training.dto.request.InternshipPlacementRequest;
import com.saas.training.dto.request.InternshipStudentRequest;
import com.saas.training.dto.response.InternshipStudentResponse;
import com.saas.training.exception.ResourceExistsException;
import com.saas.training.repository.InternshipStudentRepository;
import com.saas.training.utility.ValidationUtil;
import com.saas.training.model.InternshipStudent;
import com.saas.training.enums.InternshipStatus;
import com.saas.training.enums.Semester;
import com.saas.training.mapper.InternshipStudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InternshipStudentService {

    private final InternshipStudentRepository internshipStudentRepository;
    private final InternshipStudentMapper internshipStudentMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public InternshipStudentResponse addInternshipStudent(UUID tenantId,
                                                          InternshipStudentRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        if (internshipStudentRepository.existsByTenantIdAndBudgetYearIdAndSemesterAndIdNumber(
                tenant.getId(), request.getBudgetYearId(),
                Semester.valueOf(request.getSemester().toUpperCase()), request.getIdNumber())) {
            throw new ResourceExistsException(
                    "Internship student with id '" + request.getIdNumber() +
                            "' already exists for semester '" + request.getSemester() + "'");
        }
        InternshipStudent internshipStudent = internshipStudentMapper
                .mapToEntity(tenant, request);
        internshipStudent = internshipStudentRepository.save(internshipStudent);
        return internshipStudentMapper.mapToDto(internshipStudent);
    }

    public List<InternshipStudentResponse> getAllInternshipStudents(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<InternshipStudent> internshipStudents = internshipStudentRepository.findAll();
        return internshipStudents.stream()
                .filter(intern -> intern.getTenantId().equals(tenant.getId()))
                .map(internshipStudentMapper::mapToDto)
                .toList();
    }

    public InternshipStudentResponse getInternshipStudentById(UUID tenantId,
                                                              UUID internId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        InternshipStudent internshipStudent = validationUtil.getStudentById(tenant.getId(), internId);
        return internshipStudentMapper.mapToDto(internshipStudent);
    }

    public List<InternshipStudentResponse> getInternshipStudentsByYearAndSemester(UUID tenantId,
                                                                                  UUID yearId,
                                                                                  String semester) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<InternshipStudent> internshipStudents = internshipStudentRepository
                .findByTenantIdAndBudgetYearIdAndSemester(
                        tenant.getId(), yearId, Semester.valueOf(semester.toUpperCase()));
        return internshipStudents.stream()
                .filter(intern -> intern.getTenantId().equals(tenant.getId()))
                .map(internshipStudentMapper::mapToDto)
                .toList();
    }

    @Transactional
    public InternshipStudentResponse updateInternshipStudent(UUID tenantId,
                                                             UUID internId,
                                                             InternshipStudentRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        InternshipStudent internshipStudent = validationUtil.getStudentById(tenant.getId(), internId);
        if (internshipStudentRepository.existsByTenantIdAndBudgetYearIdAndSemesterAndIdNumberAndIdNot(
                tenant.getId(), request.getBudgetYearId(),
                Semester.valueOf(request.getSemester().toUpperCase()),
                request.getIdNumber(), internshipStudent.getId())) {
            throw new ResourceExistsException(
                    "Internship student with id '" + request.getIdNumber() +
                            "' already exists for semester '" + request.getSemester() + "'");
        }
        internshipStudent = internshipStudentMapper.updateEntity(tenant, internshipStudent, request);
        internshipStudent = internshipStudentRepository.save(internshipStudent);
        return internshipStudentMapper.mapToDto(internshipStudent);
    }

    @Transactional
    public InternshipStudentResponse assignInternToPlacementDepartment(UUID tenantId,
                                                                       UUID internId,
                                                                       InternshipPlacementRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        DepartmentDto department = validationUtil
                .getDepartmentById(tenant.getId(), request.getPlacedDepartmentId());
        InternshipStudent internshipStudent = validationUtil.getStudentById(tenant.getId(), internId);
        if (request.getPlacedDepartmentId() != null) {
            internshipStudent.setPlacedDepartmentId(department.getId());
        }
        if (request.getRemark() != null) {
            internshipStudent.setRemark(request.getRemark());
        }
        internshipStudent = internshipStudentRepository.save(internshipStudent);
        return internshipStudentMapper.mapToDto(internshipStudent);
    }

    @Transactional
    public InternshipStudentResponse assignInternshipStatus(UUID tenantId,
                                                            UUID internId,
                                                            String internshipStatus) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        InternshipStudent internshipStudent = validationUtil.getStudentById(tenant.getId(), internId);
        if (internshipStudent.getPlacedDepartmentId() == null) {
            throw new IllegalStateException("Please assign a placement department for internship student");
        }
        if (internshipStatus != null) {
            internshipStudent.setInternshipStatus(InternshipStatus.valueOf(internshipStatus.toUpperCase()));
        }
        internshipStudent = internshipStudentRepository.save(internshipStudent);
        return internshipStudentMapper.mapToDto(internshipStudent);
    }

    @Transactional
    public void deleteInternshipStudent(UUID tenantId,
                                        UUID internId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        InternshipStudent internshipStudent = validationUtil.getStudentById(tenant.getId(), internId);
        internshipStudentRepository.delete(internshipStudent);
    }
}