package com.saas.organization.mapper;

import com.saas.organization.dto.requestDto.DepartmentTypeRequest;
import com.saas.organization.dto.responseDto.DepartmentTypeResponse;
import com.saas.organization.model.DepartmentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepartmentTypeMapper {
    @Autowired
    private TenantMapper tenantMapper;
    public DepartmentType mapToEntity(DepartmentTypeRequest departmentTypeRequest) {
        DepartmentType departmentType = new DepartmentType ();
        departmentType.setDepartmentTypeName(departmentTypeRequest.getDepartmentTypeName());
        departmentType.setDescription(departmentTypeRequest.getDescription());
        return departmentType;
    }

    public DepartmentTypeResponse mapToDto(DepartmentType departmentType) {
        DepartmentTypeResponse response = new DepartmentTypeResponse ();
        response.setId(departmentType.getId());
        response.setDepartmentTypeName(departmentType.getDepartmentTypeName());
        response.setDescription(departmentType.getDescription());
        response.setTenantId(departmentType.getTenant().getId());

        return response;
    }

    public DepartmentType updateDepartmentType(DepartmentType departmentType, DepartmentTypeRequest departmentTypeRequest) {
        if (departmentTypeRequest.getDepartmentTypeName() != null)
            departmentType.setDepartmentTypeName (departmentTypeRequest.getDepartmentTypeName());
        if (departmentTypeRequest.getDescription () != null)
            departmentType.setDescription (departmentTypeRequest.getDescription());
        return departmentType;
    }
}
