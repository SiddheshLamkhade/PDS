package com.mainapp.controller;

import com.mainapp.dto.ApiResponse;
import com.mainapp.dto.DealerApprovalRequest;
import com.mainapp.dto.DealerRegistrationRequest;
import com.mainapp.dto.DealerRequest;
import com.mainapp.dto.DealerResponse;
import com.mainapp.service.DealerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dealers")
@RequiredArgsConstructor
public class DealerController {

    private final DealerService dealerService;

    // ================== ADMIN CREATES DEALER (Direct) ==================

    @PostMapping
    public ResponseEntity<ApiResponse<DealerResponse>> createDealer(@Valid @RequestBody DealerRequest request) {
        DealerResponse dealer = dealerService.createDealer(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Dealer created successfully by admin", dealer));
    }

    // ================== DEALER SELF-REGISTRATION (Public) ==================

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<DealerResponse>> registerDealer(@Valid @RequestBody DealerRegistrationRequest request) {
        DealerResponse dealer = dealerService.registerDealer(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Dealer registration submitted successfully. Pending admin approval.", dealer));
    }

    // ================== ADMIN APPROVAL ENDPOINTS ==================

    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<DealerResponse>>> getPendingDealers() {
        List<DealerResponse> dealers = dealerService.getPendingDealers();
        return ResponseEntity.ok(ApiResponse.success("Pending dealers fetched successfully", dealers));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<DealerResponse>>> getDealersByStatus(@PathVariable String status) {
        List<DealerResponse> dealers = dealerService.getDealersByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Dealers with status " + status + " fetched successfully", dealers));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<DealerResponse>> approveOrRejectDealer(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "1") Long adminUserId, // TODO: Get from JWT token
            @Valid @RequestBody DealerApprovalRequest request) {
        DealerResponse dealer = dealerService.approveDealer(id, adminUserId, request);
        String message = "APPROVE".equalsIgnoreCase(request.getAction()) 
            ? "Dealer approved successfully" 
            : "Dealer rejected successfully";
        return ResponseEntity.ok(ApiResponse.success(message, dealer));
    }

    // ================== EXISTING ENDPOINTS ==================


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DealerResponse>> getDealerById(@PathVariable Long id) {
        DealerResponse dealer = dealerService.getDealerById(id);
        return ResponseEntity.ok(ApiResponse.success("Dealer fetched successfully", dealer));
    }

    @GetMapping("/license/{shopLicense}")
    public ResponseEntity<ApiResponse<DealerResponse>> getDealerByShopLicense(@PathVariable String shopLicense) {
        DealerResponse dealer = dealerService.getDealerByShopLicense(shopLicense);
        return ResponseEntity.ok(ApiResponse.success("Dealer fetched successfully", dealer));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<DealerResponse>> getDealerByUserId(@PathVariable Long userId) {
        DealerResponse dealer = dealerService.getDealerByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success("Dealer fetched successfully", dealer));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DealerResponse>>> getAllDealers() {
        List<DealerResponse> dealers = dealerService.getAllDealers();
        return ResponseEntity.ok(ApiResponse.success("Dealers fetched successfully", dealers));
    }

    @GetMapping("/region/{region}")
    public ResponseEntity<ApiResponse<List<DealerResponse>>> getDealersByRegion(@PathVariable String region) {
        List<DealerResponse> dealers = dealerService.getDealersByRegion(region);
        return ResponseEntity.ok(ApiResponse.success("Dealers fetched successfully", dealers));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DealerResponse>> updateDealer(
            @PathVariable Long id,
            @Valid @RequestBody DealerRequest request) {
        DealerResponse dealer = dealerService.updateDealer(id, request);
        return ResponseEntity.ok(ApiResponse.success("Dealer updated successfully", dealer));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<DealerResponse>> deactivateDealer(@PathVariable Long id) {
        DealerResponse dealer = dealerService.deactivateDealer(id);
        return ResponseEntity.ok(ApiResponse.success("Dealer deactivated successfully", dealer));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<DealerResponse>> activateDealer(@PathVariable Long id) {
        DealerResponse dealer = dealerService.activateDealer(id);
        return ResponseEntity.ok(ApiResponse.success("Dealer activated successfully", dealer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDealer(@PathVariable Long id) {
        dealerService.deleteDealer(id);
        return ResponseEntity.ok(ApiResponse.success("Dealer deleted successfully", null));
    }
}
