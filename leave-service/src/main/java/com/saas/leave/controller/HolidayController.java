package com.saas.leave.controller;

import com.saas.leave.dto.request.HolidayRequest;
import com.saas.leave.dto.response.HolidayResponse;
import com.saas.leave.service.HolidayService;
import com.saas.leave.utility.PermissionEvaluator;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/holidays/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Holiday")
public class HolidayController {

    private final HolidayService holidayService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<HolidayResponse> createHoliday(@PathVariable("tenantId") UUID tenantId,
                                                         @RequestBody @Valid HolidayRequest holidayRequest) {

        permissionEvaluator.addHolidayPermission(tenantId);

        HolidayResponse createdHoliday = holidayService.createHoliday(tenantId, holidayRequest);
//        return new ResponseEntity<>(createdHoliday, HttpStatus.CREATED);

        return ResponseEntity.ok(createdHoliday);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<HolidayResponse>> getAllHolidays(@PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllHolidaysPermission(tenantId);

        List<HolidayResponse> holidays = holidayService.getAllHolidays(tenantId);
        return ResponseEntity.ok(holidays);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<HolidayResponse> getHolidayById(@PathVariable("tenantId") UUID tenantId,
                                                          @PathVariable UUID id) {

        permissionEvaluator.getHolidayByIdPermission(tenantId);

        HolidayResponse holiday = holidayService.getHolidayById(tenantId, id);
        return ResponseEntity.ok(holiday);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HolidayResponse> updateHoliday(@PathVariable("tenantId") UUID tenantId,
                                                         @PathVariable UUID id,
                                                         @RequestBody @Valid HolidayRequest holidayRequest) {

        permissionEvaluator.updateHolidayPermission(tenantId);

        HolidayResponse updatedHoliday = holidayService.updateHoliday(tenantId, id, holidayRequest);
        return ResponseEntity.ok(updatedHoliday);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHoliday(@PathVariable("tenantId") UUID tenantId,
                                              @PathVariable UUID id) {

        permissionEvaluator.deleteHolidayPermission(tenantId);

        holidayService.deleteHoliday(tenantId, id);
       return ResponseEntity.ok("Holiday deleted successfully!");
    }
}
