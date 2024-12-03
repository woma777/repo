package com.saas.employee.service;

import com.saas.employee.client.AuthServiceClient;
import com.saas.employee.dto.clientDto.*;
import com.saas.employee.dto.request.EmployeeRequest;
import com.saas.employee.dto.response.EmployeeHistoryResponse;
import com.saas.employee.dto.response.EmployeeResponse;
import com.saas.employee.dto.response.PromoteEmployeeResponse;
import com.saas.employee.enums.EmploymentType;
import com.saas.employee.model.DutyStation;
import com.saas.employee.model.Employee;
import com.saas.employee.exception.ResourceExistsException;
import com.saas.employee.exception.ResourceNotFoundException;
import com.saas.employee.mapper.EmployeeMapper;
import com.saas.employee.model.EmployeeHistory;
import com.saas.employee.repository.EmployeeHistoryRepository;
import com.saas.employee.repository.EmployeeRepository;
import com.saas.employee.utility.FileUtil;
import com.saas.employee.utility.ValidationUtil;
import com.saas.employee.dto.clientDto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final ValidationUtil validationUtil;
    private final AuthServiceClient authServiceClient;
    private final EmployeeHistoryRepository employeeHistoryRepository;

    public EmployeeResponse addEmployee(UUID tenantId,
                                        EmployeeRequest request,
                                        MultipartFile file) throws IOException {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        if (employeeRepository
                .existsByTenantIdAndEmployeeId(tenant.getId(), request.getEmployeeId())) {
            throw new ResourceExistsException(
                    "Employee with employee Id '" + request.getEmployeeId() + "' already exists");
        }
        if (employeeRepository.existsByTenantIdAndEmail(tenant.getId(), request.getEmail())) {
            throw new ResourceExistsException
                    ("Employee with email '" + request.getEmail() + "' already exists");
        }
        Employee employee = employeeMapper.mapToEntity(tenant, request, file);
        if (employee.getEmploymentType().equals(EmploymentType.CONTRACT) &&
                employee.getEndDate() == null) {
            throw new IllegalArgumentException(
                    "When the employment type is contract, you must set the end date");
        }
        employee = employeeRepository.save(employee);
        return employeeMapper.mapToDto(employee);
    }

    public List<EmployeeResponse> getAllEmployees(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .filter(emp -> emp.getTenantId().equals(tenant.getId()))
                .map(employeeMapper::mapToDto)
                .toList();
    }

    public EmployeeResponse getEmployeeById(UUID tenantId,
                                            UUID employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        return employeeMapper.mapToDto(employee);
    }

    public EmployeeResponse getEmployeeByEmployeeId(UUID tenantId,
                                                    String employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeByEmployeeId(tenant.getId(), employeeId);
        return employeeMapper.mapToDto(employee);
    }

    public EmployeeResponse getEmployeeByName(UUID tenantId,
                                              String firstName,
                                              String middleName,
                                              String lastName) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = employeeRepository
                .findByTenantIdAndFirstNameAndMiddleNameAndLastName(
                        tenant.getId(), firstName, middleName, lastName)
                .filter(emp -> emp.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with name '" +
                                firstName + " " + middleName + " " + lastName + "'"));
        return employeeMapper.mapToDto(employee);
    }

    public List<EmployeeResponse> getEmployeesByDepartmentId(UUID tenantId,
                                                             UUID departmentId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        DepartmentDto department = validationUtil.getDepartmentById(tenant.getId(), departmentId);
        List<Employee> employees = employeeRepository
                .findByTenantIdAndDepartmentId(tenant.getId(), department.getId());
        return employees.stream()
                .filter(emp -> emp.getTenantId().equals(tenant.getId()))
                .map(employeeMapper::mapToDto)
                .toList();
    }

    public byte[] getEmployeeProfileImageById(UUID tenantId,
                                              UUID employeeId,
                                              MediaType mediaType) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        switch (employee.getProfileImageType()) {
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
                throw new IllegalArgumentException(
                        "Unsupported file type: " + employee.getProfileImageType());
        }
        byte[] fileBytes = FileUtil.decompressFile(employee.getProfileImageBytes());
        if (fileBytes.length == 0) {
            throw new ResourceNotFoundException("Image data is not available");
        }
        return fileBytes;
    }

    public List<EmployeeResponse> getEmployeesByEmploymentType(UUID tenantId,
                                                               String employmentType) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<Employee> employees = employeeRepository
                .findByTenantIdAndEmploymentType(tenant.getId(),
                        EmploymentType.valueOf(employmentType.toUpperCase()));
        return employees.stream()
                .map(employeeMapper::mapToDto)
                .toList();
    }

    public List<EmployeeResponse> getEmployeesByDutyStation(UUID tenantId,
                                                            UUID stationId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        DutyStation dutyStation = validationUtil.getDutyStationById(tenant.getId(), stationId);
        List<Employee> employees = employeeRepository
                .findByTenantIdAndDutyStation(tenant.getId(), dutyStation);
        return employees.stream()
                .map(employeeMapper::mapToDto)
                .toList();
    }

    @Transactional
    public EmployeeResponse updateEmployee(UUID tenantId,
                                           UUID employeeId,
                                           EmployeeRequest request,
                                           MultipartFile file) throws IOException {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        if (employeeRepository.existsByTenantIdAndEmployeeIdAndIdNot(
                tenant.getId(), request.getEmployeeId(), employee.getId())) {
            throw new ResourceExistsException(
                    "Employee with employee Id '" + request.getEmployeeId() + "' already exists");
        }
        employee = employeeMapper.updateEmployee(tenant, employee, request, file);
        employee = employeeRepository.save(employee);
        UserDto user = authServiceClient.getUserByUsername(tenantId, employee.getEmployeeId());
        if (user != null) {
            authServiceClient.updateUser(tenantId, user.getId(), employee.getEmployeeId());
        }
        return employeeMapper.mapToDto(employee);
    }

    public EmployeeResponse renewEmployeeContract(UUID tenantId,
                                                  UUID employeeId,
                                                  LocalDate endDate) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        if (!employee.getEmploymentType().equals(EmploymentType.CONTRACT)) {
            throw new IllegalArgumentException(
                    "No need to renew contract for permanent employee");
        }
        employee.setEndDate(endDate);
        employee = employeeRepository.save(employee);
        return employeeMapper.mapToDto(employee);
    }

    public String updateEmployeeEmail(UUID tenantId,
                                      UUID employeeId,
                                      String email) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        employee.setEmail(email);
        employee = employeeRepository.save(employee);
        return employee.getEmail();
    }

    public PromoteEmployeeResponse addEmployeeHistory(UUID tenantId,
                                                      UUID employeeId,
                                                      String vacancyNumber,
                                                      UUID payGradeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        RecruitmentDto recruitment = validationUtil
                .getRecruitmentByVacancyNumber(tenant.getId(), vacancyNumber);
        PayGradeDto payGrade = validationUtil
                .getPayGradeById(tenant.getId(), recruitment.getJobId(), payGradeId);
        employee.setDepartmentId(recruitment.getDepartmentId());
        employee.setJobId(recruitment.getJobId());
        employee.setPayGradeId(payGrade.getId());
        employee = employeeRepository.save(employee);
        addEmployeeHistory(tenant.getId(), employee);
        return employeeMapper.mapToPromoteDto(employee);
    }

    public List<EmployeeHistoryResponse> getEmployeeHistories(UUID tenantId,
                                                              UUID employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<EmployeeHistory> employeeHistories = employeeHistoryRepository
                .findByTenantIdAndEmployeeId(tenant.getId(), employeeId);
        return employeeHistories.stream()
                .map(employeeMapper::mapToHistoryDto)
                .toList();
    }

    @Transactional
    public void deleteEmployee(UUID tenantId, UUID employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Employee employee = validationUtil.getEmployeeById(tenant.getId(), employeeId);
        UserDto user = validationUtil.getUserByUsername(tenantId, employee.getEmployeeId());
        if (user != null) {
            authServiceClient.deleteUser(tenantId, user.getId());
        }
        employeeRepository.delete(employee);
    }

    private void addEmployeeHistory(UUID tenantId,
                                    Employee employee) {

        EmployeeHistory employeeHistory = employeeMapper.mapToHistoryEntity(employee);
        List<EmployeeHistory> employeeHistories = employeeHistoryRepository
                .findByTenantIdAndEmployeeId(tenantId, employee.getId());
        if (employeeHistories.isEmpty()) {
            employeeHistory.setStartDate(LocalDate.from(employee.getCreatedAt()));
        }  else {
            LocalDate now = LocalDate.now();
            LocalDate nearestEndDate = employeeHistories.stream()
                    .map(EmployeeHistory::getEndDate)
                    .filter(Objects::nonNull)
                    .min(Comparator.comparingLong(endDate -> ChronoUnit.DAYS.between(endDate, now)))
                    .orElse(null);
            employeeHistory.setStartDate(nearestEndDate);
        }
        employeeHistoryRepository.save(employeeHistory);
    }
}