package com.mainapp.controller;

import com.mainapp.dto.ApiResponse;
import com.mainapp.dto.DistributionRequest;
import com.mainapp.dto.DistributionResponse;
import com.mainapp.service.DistributionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/distributions")
@RequiredArgsConstructor
public class DistributionController {

    private final DistributionService distributionService;

    @PostMapping("/distribute")
    public ResponseEntity<ApiResponse<DistributionResponse>> distributeRation(
            @Valid @RequestBody DistributionRequest request) {
        try {
            DistributionResponse response = distributionService.distributeRation(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Ration distributed successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DistributionResponse>> getDistributionById(@PathVariable Long id) {
        try {
            DistributionResponse response = distributionService.getDistributionById(id);
            return ResponseEntity.ok(ApiResponse.success("Distribution retrieved successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DistributionResponse>>> getAllDistributions() {
        List<DistributionResponse> response = distributionService.getAllDistributions();
        return ResponseEntity.ok(ApiResponse.success("Distributions retrieved successfully", response));
    }

    @GetMapping("/citizen/{citizenId}")
    public ResponseEntity<ApiResponse<List<DistributionResponse>>> getDistributionsByCitizen(
            @PathVariable Long citizenId) {
        try {
            List<DistributionResponse> response = distributionService.getDistributionsByCitizen(citizenId);
            return ResponseEntity.ok(ApiResponse.success("Distributions retrieved successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/ration-card/{rationCardNumber}")
    public ResponseEntity<ApiResponse<List<DistributionResponse>>> getDistributionsByRationCard(
            @PathVariable String rationCardNumber) {
        try {
            List<DistributionResponse> response = distributionService.getDistributionsByRationCard(rationCardNumber);
            return ResponseEntity.ok(ApiResponse.success("Distributions retrieved successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/dealer/{dealerId}")
    public ResponseEntity<ApiResponse<List<DistributionResponse>>> getDistributionsByDealer(
            @PathVariable Long dealerId) {
        try {
            List<DistributionResponse> response = distributionService.getDistributionsByDealer(dealerId);
            return ResponseEntity.ok(ApiResponse.success("Distributions retrieved successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<ApiResponse<List<DistributionResponse>>> getDistributionsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<DistributionResponse> response = distributionService.getDistributionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success("Distributions retrieved successfully", response));
    }
}
