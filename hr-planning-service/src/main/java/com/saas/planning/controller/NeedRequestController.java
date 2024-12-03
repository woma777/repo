package com.saas.planning.controller;

import com.saas.planning.dto.request.NeedRequestRequestDto;
import com.saas.planning.dto.response.NeedRequestResponseDto;
import com.saas.planning.exception.ResourceNotFoundException;
import com.saas.planning.service.NeedRequestService;
import com.saas.planning.utility.PermissionEvaluator;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/need-requests/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Need Request")
public class NeedRequestController {

    private final NeedRequestService needRequestService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<NeedRequestResponseDto> createNeedRequest(
            @PathVariable("tenantId") UUID tenantId,
            @Valid @RequestBody NeedRequestRequestDto needRequestRequest) {

        permissionEvaluator.addHrNeedRequestPermission(tenantId);

        NeedRequestResponseDto createdNeedRequest = needRequestService.createNeedRequest(tenantId, needRequestRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNeedRequest);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<NeedRequestResponseDto>> getAllNeedRequests(
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllHrNeedRequestsPermission(tenantId);

        List<NeedRequestResponseDto> needRequests = needRequestService.getAllNeedRequests(tenantId);
        return ResponseEntity.ok(needRequests);
    }

    @GetMapping("/get/need_request{id}")
    public ResponseEntity<NeedRequestResponseDto> getNeedRequestById(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable("id") UUID id) {

        permissionEvaluator.getHrNeedRequestByIdPermission(tenantId);

        NeedRequestResponseDto needRequest = needRequestService.getNeedRequestById(tenantId, id);
        return ResponseEntity.ok(needRequest);
    }

    @PutMapping("/update/need_request{id}")
    public ResponseEntity<NeedRequestResponseDto> updateNeedRequest(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable("id") UUID id,
            @Valid @RequestBody NeedRequestRequestDto needRequestRequest) {

        permissionEvaluator.updateHrNeedRequestPermission(tenantId);

        NeedRequestResponseDto updatedNeedRequest = needRequestService.updateNeedRequest(tenantId, id, needRequestRequest);
        return ResponseEntity.ok(updatedNeedRequest);
    }
    @GetMapping("/get-by-staffPlanId")
    public ResponseEntity<List<NeedRequestResponseDto>> getNeedRequestsByStaffPlanId(
            @PathVariable("tenantId") UUID tenantId,
            @RequestParam("staffPlanId") UUID staffPlanId) {

        permissionEvaluator.getHrNeedRequestByStaffPlanIdPermission(tenantId);


        try {
            List<NeedRequestResponseDto> needRequests = needRequestService.getNeedRequestByStaffplanId(tenantId, staffPlanId);
            return ResponseEntity.ok(needRequests); // Return the list of NeedRequestResponseDto with HTTP 200 status
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Return HTTP 400 Bad Request for illegal arguments
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return HTTP 404 Not Found if resources are missing
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Return HTTP 500 for other server errors
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNeedRequest(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable("id") UUID id) {

        permissionEvaluator.deleteHrNeedRequestPermission(tenantId);

        needRequestService.deleteNeedRequest(tenantId, id);
        return ResponseEntity.ok("Hr need deleted successfully!");
    }
}
