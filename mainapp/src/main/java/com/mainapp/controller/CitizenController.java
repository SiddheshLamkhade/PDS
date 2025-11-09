package com.mainapp.controller;

import com.mainapp.dto.ApiResponse;
import com.mainapp.dto.CitizenRequest;
import com.mainapp.dto.CitizenResponse;
import com.mainapp.model.Citizen.CitizenCategory;
import com.mainapp.service.CitizenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citizens")
@RequiredArgsConstructor
public class CitizenController {

    private final CitizenService citizenService;

    @PostMapping
    public ResponseEntity<ApiResponse<CitizenResponse>> createCitizen(@Valid @RequestBody CitizenRequest request) {
        CitizenResponse citizen = citizenService.createCitizen(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Citizen registered successfully", citizen));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CitizenResponse>> getCitizenById(@PathVariable Long id) {
        CitizenResponse citizen = citizenService.getCitizenById(id);
        return ResponseEntity.ok(ApiResponse.success("Citizen fetched successfully", citizen));
    }

    @GetMapping("/ration-card/{rationCardNumber}")
    public ResponseEntity<ApiResponse<CitizenResponse>> getCitizenByRationCard(
            @PathVariable String rationCardNumber) {
        CitizenResponse citizen = citizenService.getCitizenByRationCardNumber(rationCardNumber);
        return ResponseEntity.ok(ApiResponse.success("Citizen fetched successfully", citizen));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<CitizenResponse>> getCitizenByUserId(@PathVariable Long userId) {
        CitizenResponse citizen = citizenService.getCitizenByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success("Citizen fetched successfully", citizen));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CitizenResponse>>> getAllCitizens() {
        List<CitizenResponse> citizens = citizenService.getAllCitizens();
        return ResponseEntity.ok(ApiResponse.success("Citizens fetched successfully", citizens));
    }

    @GetMapping("/dealer/{dealerId}")
    public ResponseEntity<ApiResponse<List<CitizenResponse>>> getCitizensByDealerId(@PathVariable Long dealerId) {
        List<CitizenResponse> citizens = citizenService.getCitizensByDealerId(dealerId);
        return ResponseEntity.ok(ApiResponse.success("Citizens fetched successfully", citizens));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<CitizenResponse>>> getCitizensByCategory(
            @PathVariable CitizenCategory category) {
        List<CitizenResponse> citizens = citizenService.getCitizensByCategory(category);
        return ResponseEntity.ok(ApiResponse.success("Citizens fetched successfully", citizens));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CitizenResponse>> updateCitizen(
            @PathVariable Long id,
            @Valid @RequestBody CitizenRequest request) {
        CitizenResponse citizen = citizenService.updateCitizen(id, request);
        return ResponseEntity.ok(ApiResponse.success("Citizen updated successfully", citizen));
    }

    @PatchMapping("/{citizenId}/assign-dealer/{dealerId}")
    public ResponseEntity<ApiResponse<CitizenResponse>> assignDealer(
            @PathVariable Long citizenId,
            @PathVariable Long dealerId) {
        CitizenResponse citizen = citizenService.assignDealer(citizenId, dealerId);
        return ResponseEntity.ok(ApiResponse.success("Dealer assigned successfully", citizen));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCitizen(@PathVariable Long id) {
        citizenService.deleteCitizen(id);
        return ResponseEntity.ok(ApiResponse.success("Citizen deleted successfully", null));
    }
}
