package com.saas.employee.mapper;

import com.saas.employee.dto.clientDto.TenantDto;
import com.saas.employee.dto.request.FamilyRequest;
import com.saas.employee.dto.response.FamilyResponse;
import com.saas.employee.model.Employee;
import com.saas.employee.model.Family;
import com.saas.employee.enums.Gender;
import com.saas.employee.enums.RelationshipType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
@RequiredArgsConstructor
public class FamilyMapper {

    public Family mapToEntity(TenantDto tenant,
                              Employee employee,
                              FamilyRequest request) {

        Family family = new Family ();
        family.setTenantId(tenant.getId());
        family.setEmployee(employee);
        family.setRelationshipType (RelationshipType.valueOf(
                request.getRelationshipType ().toUpperCase()));
        family.setFirstName (request.getFirstName ());
        family.setMiddleName (request.getMiddleName ());
        family.setLastName (request.getLastName ());
        family.setDateOfBirth (request.getDateOfBirth ());
        family.setGender (Gender.valueOf(request.getGender().toUpperCase()));
        family.setHouseNumber(request.getHouseNumber ());
        family.setHomeTelephone (request.getHomeTelephone ());
        family.setOfficeTelephone (request.getOfficeTelephone ());
        family.setMobileNumber (request.getMobileNumber ());
        family.setEmail (request.getEmail ());
        family.setPoBox (request.getPoBox ());
        family.setEmergencyContact (request.isEmergencyContact());

        return family;
    }

    public FamilyResponse mapToDto(Family family) {

        FamilyResponse response = new FamilyResponse ();
        response.setId (family.getId ());
        response.setTenantId (family.getTenantId ());
        response.setRelationshipType (family.getRelationshipType ().getRelationshipType());
        response.setFirstName (family.getFirstName ());
        response.setMiddleName (family.getMiddleName ());
        response.setLastName (family.getLastName ());
        response.setDateOfBirth (family.getDateOfBirth ());
        response.setAge (Period.between (
                family.getDateOfBirth (), LocalDate.now ()).getYears ());
        response.setGender (family.getGender ().getGender());
        response.setHouseNumber(family.getHouseNumber ());
        response.setHomeTelephone (family.getHomeTelephone ());
        response.setOfficeTelephone (family.getOfficeTelephone ());
        response.setMobileNumber (family.getMobileNumber ());
        response.setEmail (family.getEmail ());
        response.setPoBox (family.getPoBox ());
        response.setEmergencyContact (family.isEmergencyContact());
        response.setCreatedAt (family.getCreatedAt ());
        response.setUpdatedAt (family.getUpdatedAt ());
        response.setCreatedBy (family.getCreatedBy ());
        response.setUpdatedBy (family.getUpdatedBy ());
        response.setEmployeeId (family.getEmployee ().getId ());

        return response;
    }

    public Family updateFamily(Family family, FamilyRequest request) {

        if (request.getRelationshipType () != null) {
            family.setRelationshipType(RelationshipType.valueOf(
                    request.getRelationshipType().toUpperCase()));
        }
        if (request.getFirstName () != null) {
            family.setFirstName(request.getFirstName());
        }
        if (request.getMiddleName () != null) {
            family.setMiddleName(request.getMiddleName());
        }
        if (request.getLastName () != null) {
            family.setLastName(request.getLastName());
        }
        if (request.getDateOfBirth () != null) {
            family.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getGender () != null) {
            family.setGender(Gender.valueOf(request.getGender().toUpperCase()));
        }
        if (request.getHouseNumber () != null) {
            family.setHouseNumber(request.getHouseNumber());
        }
        if (request.getHomeTelephone () != null) {
            family.setHomeTelephone(request.getHomeTelephone());
        }
        if (request.getOfficeTelephone () != null) {
            family.setOfficeTelephone(request.getOfficeTelephone());
        }
        if (request.getMobileNumber () != null) {
            family.setMobileNumber(request.getMobileNumber());
        }
        if (request.getEmail () != null) {
            family.setEmail(request.getEmail());
        }
        if (request.getPoBox () != null) {
            family.setPoBox(request.getPoBox());
        }

        family.setEmergencyContact (request.isEmergencyContact());

        return family;
    }
}
