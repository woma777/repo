package com.saas.employee.controller;

import com.saas.employee.utility.PermissionEvaluator;
import com.saas.employee.dto.request.AddressRequest;
import com.saas.employee.dto.response.AddressResponse;
import com.saas.employee.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee/addresses/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Address")
public class AddressController {

    private final AddressService addressService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/{employee-id}/add")
    public ResponseEntity<?> addAddress(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @Valid @RequestBody AddressRequest addressRequest) {

        permissionEvaluator.addAddressPermission(tenantId, employeeId);

        AddressResponse address = addressService
                .addAddress(tenantId, employeeId, addressRequest);
        return new ResponseEntity<>(address, HttpStatus.CREATED);
    }

    @GetMapping("/{employee-id}/get-all")
    public ResponseEntity<?> getAllAddresses(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId) {

        permissionEvaluator.getAllAddressesPermission(tenantId, employeeId);

        List<AddressResponse> addresses = addressService
                .getAllAddresses(tenantId, employeeId);
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/get/employee-addresses")
    public ResponseEntity<?> getEmployeeAddresses(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("employee-id") String employeeId) {

        permissionEvaluator.getAddressesByEmployeeIdPermission(tenantId);

        List<AddressResponse> addresses = addressService
                .getEmployeeAddresses(tenantId, employeeId);
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/{employee-id}/get/{address-id}")
    public ResponseEntity<?> getAddressById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("address-id") UUID addressId) {

        permissionEvaluator.getAddressByIdPermission(tenantId, employeeId);

        AddressResponse address = addressService
                .getAddressById(tenantId, employeeId, addressId);
        return ResponseEntity.ok(address);
    }

    @PutMapping("/{employee-id}/update/{address-id}")
    public ResponseEntity<?> updateAddress(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("address-id") UUID addressId,
            @Valid @RequestBody AddressRequest addressRequest) {

        permissionEvaluator.updateAddressPermission(tenantId, employeeId);

        AddressResponse address = addressService
                .updateAddress(tenantId, employeeId, addressId, addressRequest);
        return ResponseEntity.ok(address);
    }

    @DeleteMapping("/{employee-id}/delete/{address-id}")
    public ResponseEntity<?> deleteAddress(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("address-id") UUID addressId) {

        permissionEvaluator.deleteAddressPermission(tenantId, employeeId);

        addressService.deleteAddress(tenantId, employeeId, addressId);
        return ResponseEntity.ok("Address Deleted Successfully");
    }
}