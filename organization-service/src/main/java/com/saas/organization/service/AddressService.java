package com.saas.organization.service;

import com.saas.organization.dto.requestDto.AddressRequest;
import com.saas.organization.dto.responseDto.AddressResponse;
import com.saas.organization.model.Address;
import com.saas.organization.model.Department;
import com.saas.organization.model.Tenant;
import com.saas.organization.exception.ResourceExistsException;
import com.saas.organization.exception.ResourceNotFoundException;
import com.saas.organization.mapper.AddressMapper;
import com.saas.organization.repository.AddressRepository;
import com.saas.organization.repository.DepartmentRepository;
import com.saas.organization.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final TenantRepository tenantRepository;
    private final DepartmentRepository departmentRepository;

    public AddressResponse createAddress(UUID tenantId, AddressRequest addressRequest) {

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId));

        // Check if department with the same name already exists
        if (addressRepository.existsByDepartmentIdAndTenantId(
                addressRequest.getDepartmentId(), tenant.getId())) {
            throw new ResourceExistsException("Department with Name " +
                    addressRequest.getDepartmentId() + " already exists");
        }

        Address address = addressMapper.mapToEntity(addressRequest);
        address.setTenant(tenant);
        address = addressRepository.save(address);
        return addressMapper.mapToDto(address);
    }

    public List<AddressResponse> getAllAddresses(UUID tenantId) {

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId));

        List<Address> addresses = addressRepository.findAll();
        return addresses.stream()
                .filter(ad -> ad.getTenant().getId().equals(tenant.getId()))
                .map(addressMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public AddressResponse getAddressById(UUID tenantId,
                                          UUID addressId) {

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId));
        Address address = addressRepository.findById(addressId)
                .filter(a -> a.getTenant().equals(tenant))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Address not found with ID: " + addressId));
        return addressMapper.mapToDto(address);
    }

    public List<AddressResponse> getAddressByDepartmentId(UUID tenantId, UUID departmentId) {

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId));

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with id: " + departmentId));

        List<Address> addresses = addressRepository.findByDepartmentId(departmentId);
        return addresses.stream()
                .filter(address -> address.getTenant().getId().equals(tenant.getId()))
                .map(addressMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public AddressResponse updateAddress(UUID id, UUID tenantId, AddressRequest addressRequest) {

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId));

        Address address = addressRepository.findById(id)
                .filter(ad -> ad.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Address not found with id: " + id + " for the specified tenant"));

        address = addressMapper.updateAddress(address, addressRequest);
        address = addressRepository.save(address);
        return addressMapper.mapToDto(address);
    }

    public void deleteAddress(UUID id, UUID tenantId) {

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId));

        Address address = addressRepository.findById(id)
                .filter(ad -> ad.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Address not found with id: " + id + " for the specified tenant"));

        addressRepository.delete(address);
    }
}