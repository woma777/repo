package com.saas.employee.controller;

import com.saas.employee.dto.request.CountryRequest;
import com.saas.employee.dto.response.CountryResponse;
import com.saas.employee.service.CountryService;
import com.saas.employee.utility.PermissionEvaluator;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee/countries/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Country")
public class CountryController {

    private final CountryService countryService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addCountry(
            @PathVariable UUID tenantId,
            @Valid @RequestBody CountryRequest request) {

        permissionEvaluator.addCountryPermission(tenantId);

        CountryResponse response = countryService
                .addCountry(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllCountries(
            @PathVariable UUID tenantId) {

        permissionEvaluator.getAllCountriesPermission(tenantId);

        List<CountryResponse> responses = countryService
                .getAllCountries(tenantId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/get/{countryId}")
    public ResponseEntity<?> getCountryById(
            @PathVariable UUID tenantId,
            @PathVariable UUID countryId) {

        permissionEvaluator.getCountryByIdPermission(tenantId);

        CountryResponse response = countryService
                .getCountryById(tenantId, countryId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{countryId}")
    public ResponseEntity<?> updateCountry(
            @PathVariable UUID tenantId,
            @PathVariable UUID countryId,
            @Valid @RequestBody CountryRequest request) {

        permissionEvaluator.updateCountryPermission(tenantId);

        CountryResponse response = countryService
                .updateCountry(tenantId, countryId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/delete/{countryId}")
    public ResponseEntity<?> deleteCountry(
            @PathVariable UUID tenantId,
            @PathVariable UUID countryId) {

        permissionEvaluator.deleteCountryPermission(tenantId);

        countryService.deleteCountry(tenantId, countryId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Country deleted successfully!");
    }
}
