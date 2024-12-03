package com.saas.employee.mapper;

import com.saas.employee.dto.clientDto.DepartmentDto;
import com.saas.employee.dto.clientDto.JobDto;
import com.saas.employee.dto.clientDto.PayGradeDto;
import com.saas.employee.dto.clientDto.TenantDto;
import com.saas.employee.dto.request.EmployeeRequest;
import com.saas.employee.dto.response.EmployeeHistoryResponse;
import com.saas.employee.dto.response.EmployeeResponse;
import com.saas.employee.dto.response.PromoteEmployeeResponse;
import com.saas.employee.model.*;
import com.saas.employee.enums.EmploymentType;
import com.saas.employee.enums.Gender;
import com.saas.employee.enums.MaritalStatus;
import com.saas.employee.model.*;
import com.saas.employee.utility.FileUtil;
import com.saas.employee.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {

    private final ValidationUtil validationUtil;

    public Employee mapToEntity(TenantDto tenant,
                                EmployeeRequest request,
                                MultipartFile file) throws IOException {

        DepartmentDto department = validationUtil
                .getDepartmentById(tenant.getId(), request.getDepartmentId());
        JobDto job = validationUtil
                .getJobById(tenant.getId(), department.getId(), request.getJobId());
        PayGradeDto payGrade = validationUtil
                .getPayGradeById(tenant.getId(), job.getId(), request.getPayGradeId());
        TitleName titleName = validationUtil.getTitleNameById(tenant.getId(), request.getTitleNameId());
        DutyStation dutyStation = validationUtil
                .getDutyStationById(tenant.getId(), request.getDutyStationId());
        Country country = validationUtil.getCountryById(tenant.getId(), request.getCountryId());

        Employee employee = new Employee();
        employee.setTenantId(tenant.getId());
        employee.setDepartmentId(department.getId());
        employee.setJobId(job.getId());
        employee.setPayGradeId(payGrade.getId());
        employee.setTitleName(titleName);
        employee.setEmployeeId(request.getEmployeeId());
        employee.setFirstName(request.getFirstName());
        employee.setMiddleName(request.getMiddleName());
        employee.setLastName(request.getLastName());
        employee.setGender(Gender.valueOf(request.getGender().toUpperCase()));
        employee.setDateOfBirth(request.getDateOfBirth());
        employee.setDateOfBirth(request.getDateOfBirth());
        employee.setMaritalStatus(MaritalStatus.valueOf(request.getMaritalStatus().toUpperCase()));
        employee.setEmploymentType(EmploymentType.valueOf(request.getEmploymentType().toUpperCase()));
        employee.setHiredDate(request.getHiredDate());
        employee.setEndDate(request.getEndDate());
        employee.setDutyStation(dutyStation);
        employee.setCountry(country);
        employee.setFydaNumber(request.getFydaNumber());
        employee.setPassportNumber(request.getPassportNumber());
        employee.setTinNumber(request.getTinNumber());
        employee.setPensionNumber(request.getPensionNumber());
        employee.setEmail(request.getEmail());

        if (file != null) {
            employee.setProfileImageName(file.getOriginalFilename());
            employee.setProfileImageType(file.getContentType());
            employee.setProfileImageBytes(FileUtil.compressFile(file.getBytes()));
        }

        return employee;
    }

    public EmployeeResponse mapToDto(Employee employee) {

        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setTenantId(employee.getTenantId());
        response.setEmployeeId(employee.getEmployeeId());
        response.setDepartmentId(employee.getDepartmentId());
        response.setJobId(employee.getJobId());
        response.setPayGradeId(employee.getPayGradeId());
        response.setTitleNameId(employee.getTitleName().getId());
        response.setFirstName(employee.getFirstName());
        response.setMiddleName(employee.getMiddleName());
        response.setLastName(employee.getLastName());
        response.setGender(employee.getGender().getGender());
        response.setDateOfBirth(employee.getDateOfBirth());
        response.setAge(calculateAge(employee.getDateOfBirth()));
        response.setMaritalStatus(employee.getMaritalStatus().getMaritalStatus());
        response.setEmploymentType(employee.getEmploymentType().getEmploymentType());
        response.setJobId(employee.getJobId());
        response.setDutyStationId(employee.getDutyStation().getId());
        response.setCountryId(employee.getCountry().getId());
        response.setFydaNumber(employee.getFydaNumber());
        response.setPassportNumber(employee.getPassportNumber());
        response.setTinNumber(employee.getTinNumber());
        response.setPensionNumber(employee.getPensionNumber());
        response.setEmail(employee.getEmail());
        response.setHiredDate(employee.getHiredDate());
        response.setEndDate(employee.getEndDate());
        response.setProfileImageName(employee.getProfileImageName());
        response.setProfileImageType(employee.getProfileImageType());
        response.setProfileImageBytes(employee.getProfileImageBytes());
        response.setCreatedAt(employee.getCreatedAt());
        response.setUpdatedAt(employee.getUpdatedAt());
        response.setCreatedBy(employee.getCreatedBy());
        response.setUpdatedBy(employee.getUpdatedBy());

        return response;
    }

    public Employee updateEmployee(TenantDto tenant,
                                   Employee employee,
                                   EmployeeRequest request,
                                   MultipartFile file) throws IOException {

        DepartmentDto department = validationUtil
                .getDepartmentById(tenant.getId(), request.getDepartmentId());
        JobDto job = validationUtil
                .getJobById(tenant.getId(), department.getId(), request.getJobId());
        PayGradeDto payGrade = validationUtil
                .getPayGradeById(tenant.getId(), job.getId(), request.getPayGradeId());
        TitleName titleName = validationUtil.getTitleNameById(tenant.getId(), request.getTitleNameId());
        DutyStation dutyStation = validationUtil
                .getDutyStationById(tenant.getId(), request.getDutyStationId());
        Country country = validationUtil.getCountryById(tenant.getId(), request.getCountryId());

        if (request.getDepartmentId() != null) {
            employee.setDepartmentId(department.getId());
        }
        if (request.getJobId() != null) {
            employee.setJobId(job.getId());
        }
        if (request.getPayGradeId() != null) {
            employee.setPayGradeId(payGrade.getId());
        }
        if (request.getTitleNameId() != null) {
            employee.setTitleName(titleName);
        }
        if (request.getEmployeeId() != null) {
            employee.setEmployeeId(request.getEmployeeId());
        }
        if (request.getFirstName() != null) {
            employee.setFirstName(request.getFirstName());
        }
        if (request.getMiddleName() != null) {
            employee.setMiddleName(request.getMiddleName());
        }
        if (request.getLastName() != null) {
            employee.setLastName(request.getLastName());
        }
        if (request.getGender() != null) {
            employee.setGender(Gender.valueOf(request.getGender().toUpperCase()));
        }
        if (request.getDateOfBirth() != null) {
            employee.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getMaritalStatus() != null) {
            employee.setMaritalStatus(MaritalStatus.valueOf(request.getMaritalStatus().toUpperCase()));
        }
        if (request.getEmploymentType() != null) {
            employee.setEmploymentType(EmploymentType.valueOf(request.getEmploymentType().toUpperCase()));
        }
        if (request.getHiredDate() != null) {
            employee.setHiredDate(request.getHiredDate());
        }
        if (request.getEndDate() != null) {
            employee.setEndDate(request.getEndDate());
        }
        if (request.getDutyStationId() != null) {
            employee.setDutyStation(dutyStation);
        }
        if (request.getCountryId() != null) {
            employee.setCountry(country);
        }
        if (request.getFydaNumber() != null) {
            employee.setFydaNumber(request.getFydaNumber());
        }
        if (request.getPassportNumber() != null) {
            employee.setPassportNumber(request.getPassportNumber());
        }
        if (request.getTinNumber() != null) {
            employee.setTinNumber(request.getTinNumber());
        }
        if (request.getPensionNumber() != null) {
            employee.setPensionNumber(request.getPensionNumber());
        }
        if (request.getEmail() != null) {
            employee.setEmail(request.getEmail());
        }

        if (file != null && !file.isEmpty()) {
            employee.setProfileImageName(file.getOriginalFilename());
            employee.setProfileImageType(file.getContentType());
            employee.setProfileImageBytes(FileUtil.compressFile(file.getBytes()));
        }

        return employee;
    }

    public EmployeeHistory mapToHistoryEntity(Employee employee) {

        EmployeeHistory employeeHistory = new EmployeeHistory();
        employeeHistory.setEmployee(employee);
        employeeHistory.setDepartmentId(employee.getDepartmentId());
        employeeHistory.setJobId(employee.getJobId());
        employeeHistory.setPayGradeId(employee.getPayGradeId());

        return employeeHistory;
    }

    public EmployeeHistoryResponse mapToHistoryDto(EmployeeHistory employeeHistory) {

        EmployeeHistoryResponse response = new EmployeeHistoryResponse();
        response.setEmployeeId(employeeHistory.getEmployee().getId());
        response.setDepartmentId(employeeHistory.getDepartmentId());
        response.setJobId(employeeHistory.getJobId());
        response.setPayGradeId(employeeHistory.getPayGradeId());
        response.setStartDate(employeeHistory.getStartDate());
        response.setEndDate(employeeHistory.getEndDate());
        response.setTenantId(employeeHistory.getTenantId());
        response.setCreatedAt(employeeHistory.getCreatedAt());
        response.setUpdatedAt(employeeHistory.getUpdatedAt());
        response.setCreatedBy(employeeHistory.getCreatedBy());
        response.setUpdatedBy(employeeHistory.getUpdatedBy());

        return response;
    }

    public PromoteEmployeeResponse mapToPromoteDto(Employee employee) {

        PromoteEmployeeResponse response = new PromoteEmployeeResponse();
        response.setEmployeeId(employee.getId());
        response.setDepartmentId(employee.getDepartmentId());
        response.setJobId(employee.getJobId());
        response.setPayGradeId(employee.getPayGradeId());
        response.setTenantId(employee.getTenantId());
        response.setCreatedAt(employee.getCreatedAt());
        response.setUpdatedAt(employee.getUpdatedAt());
        response.setCreatedBy(employee.getCreatedBy());
        response.setUpdatedBy(employee.getUpdatedBy());

        return response;
    }

    public static int calculateAge (LocalDate dob) {

        LocalDate curDate = LocalDate.now();
        if (dob != null) {
            return Period.between(dob, curDate).getYears();
        } else {
            return 0;
        }
    }
}