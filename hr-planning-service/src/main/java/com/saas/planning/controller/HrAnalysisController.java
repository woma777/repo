package com.saas.planning.controller;

import com.saas.planning.dto.request.HrAnalysisRequestDto;
import com.saas.planning.dto.response.HrAnalysisResponseDto;
import com.saas.planning.service.HrAnalysisService;
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
@RequestMapping("/api/hr-analyses/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "HR Analysis")
public class HrAnalysisController {

    private final HrAnalysisService hrAnalysisService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/create")
    public ResponseEntity<HrAnalysisResponseDto> createHrAnalysis(
            @PathVariable("tenantId") UUID tenantId,
            @Valid @RequestBody HrAnalysisRequestDto hrAnalysisRequestDto
    ) {

        permissionEvaluator.addHrAnalysisPermission(tenantId);

        HrAnalysisResponseDto createdHrAnalysis = hrAnalysisService.createHrAnalysis(tenantId, hrAnalysisRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHrAnalysis);
    }

    @GetMapping("/all")
    public ResponseEntity<List<HrAnalysisResponseDto>> getAllHrAnalyses(
            @PathVariable("tenantId") UUID tenantId
    ) {

        permissionEvaluator.getAllHrAnalysesPermission(tenantId);

        List<HrAnalysisResponseDto> allHrAnalyses = hrAnalysisService.getAllHrAnalyses(tenantId);
        return ResponseEntity.ok(allHrAnalyses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HrAnalysisResponseDto> getHrAnalysisById(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id
    ) {

        permissionEvaluator.getHrAnalysisByIdPermission(tenantId);

        HrAnalysisResponseDto hrAnalysis = hrAnalysisService.getHrAnalysisById(tenantId, id);
        return ResponseEntity.ok(hrAnalysis);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<HrAnalysisResponseDto> updateHrAnalysis(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id,
            @Valid @RequestBody HrAnalysisRequestDto hrAnalysisRequestDto
    ) {

        permissionEvaluator.updateHrAnalysisPermission(tenantId);

        HrAnalysisResponseDto updatedHrAnalysis = hrAnalysisService.updateHrAnalysis(tenantId, id, hrAnalysisRequestDto);
        return ResponseEntity.ok(updatedHrAnalysis);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteHrAnalysis(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id
    ) {

        permissionEvaluator.deleteHrAnalysisPermission(tenantId);

        hrAnalysisService.deleteHrAnalysis(tenantId, id);
        return ResponseEntity.ok("Hr demand and analysis deleted successfully! ");
    }
}
