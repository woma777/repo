package com.saas.organization.controller;

import com.saas.organization.utility.PermissionEvaluator;
import com.saas.organization.dto.requestDto.AddressRequest;
import com.saas.organization.dto.responseDto.AddressResponse;
import com.saas.organization.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/organization/addresses/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Address")
public class AddressController {

    private final AddressService addressService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add-address")
    public ResponseEntity<?> createAddress(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody AddressRequest addressRequest) {

        permissionEvaluator.addAddressPermission(tenantId);

        AddressResponse address = addressService.createAddress(tenantId, addressRequest);
        return new ResponseEntity<>(address, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllAddresses(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllAddressesPermission(tenantId);

        List<AddressResponse> addresses = addressService.getAllAddresses(tenantId);
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/get/{addressId}")
    public ResponseEntity<?> getAddressById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable UUID addressId) {

        permissionEvaluator.getAddressByIdPermission(tenantId);

        AddressResponse address = addressService.getAddressById(tenantId, addressId);
        return ResponseEntity.ok(address);
    }

    @GetMapping("/departments/{department-id}")
    public ResponseEntity<?> getAddressByDepartmentId(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("department-id") UUID departmentId) {

        permissionEvaluator.getAddressesByDepartmentIdPermission(tenantId);

        List<AddressResponse> addresses = addressService.getAddressByDepartmentId(tenantId, departmentId);
        return ResponseEntity.ok(addresses);
    }

    @PutMapping("/edit-address/{id}")
    public ResponseEntity<?> updateAddress(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable UUID id,
            @RequestBody AddressRequest addressRequest) {

        permissionEvaluator.updateAddressPermission(tenantId);

        AddressResponse address = addressService.updateAddress(id, tenantId, addressRequest);
        return ResponseEntity.ok(address);
    }

    @DeleteMapping("/remove-address/{id}")
    public ResponseEntity<?> deleteAddress(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable UUID id) {

        permissionEvaluator.deleteAddressPermission(tenantId);

        addressService.deleteAddress(id, tenantId);
        return ResponseEntity.ok("Address deleted successfully!");
    }
}