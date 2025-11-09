package com.mainapp.controller;

import com.mainapp.dto.CitizenQuotaStatus;
import com.mainapp.dto.QuotaRequest;
import com.mainapp.dto.QuotaResponse;
import com.mainapp.service.QuotaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quotas")
@RequiredArgsConstructor
public class QuotaController {

    private final QuotaService quotaService;

    // ================== ADMIN OPERATIONS ==================

    @PostMapping
    public ResponseEntity<QuotaResponse> createQuota(@Valid @RequestBody QuotaRequest request) {
        QuotaResponse response = quotaService.createQuota(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuotaResponse> getQuotaById(@PathVariable Long id) {
        QuotaResponse response = quotaService.getQuotaById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<QuotaResponse>> getAllQuotas() {
        List<QuotaResponse> responses = quotaService.getAllQuotas();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/month/{month}/year/{year}")
    public ResponseEntity<List<QuotaResponse>> getQuotasByMonthAndYear(
            @PathVariable Integer month,
            @PathVariable Integer year) {
        List<QuotaResponse> responses = quotaService.getQuotasByMonthAndYear(month, year);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/active")
    public ResponseEntity<List<QuotaResponse>> getActiveQuotas() {
        List<QuotaResponse> responses = quotaService.getActiveQuotas();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuotaResponse> updateQuota(
            @PathVariable Long id,
            @Valid @RequestBody QuotaRequest request) {
        QuotaResponse response = quotaService.updateQuota(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuota(@PathVariable Long id) {
        quotaService.deleteQuota(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<QuotaResponse> toggleQuotaStatus(@PathVariable Long id) {
        QuotaResponse response = quotaService.toggleQuotaStatus(id);
        return ResponseEntity.ok(response);
    }

    // ================== CITIZEN OPERATIONS ==================

    /**
     * Get citizen's quota status for a specific month
     * Shows: Total Quota, Redeemed, Remaining for each product
     */
    @GetMapping("/citizen/{rationCardNumber}/status")
    public ResponseEntity<List<CitizenQuotaStatus>> getCitizenQuotaStatus(
            @PathVariable String rationCardNumber,
            @RequestParam Integer month,
            @RequestParam Integer year) {
        List<CitizenQuotaStatus> statusList = quotaService.getCitizenQuotaStatus(
                rationCardNumber, month, year);
        return ResponseEntity.ok(statusList);
    }

    /**
     * Get citizen's current month quota status
     * Most commonly used by citizens to check their monthly balance
     */
    @GetMapping("/citizen/{rationCardNumber}/current")
    public ResponseEntity<List<CitizenQuotaStatus>> getCitizenCurrentMonthQuota(
            @PathVariable String rationCardNumber) {
        List<CitizenQuotaStatus> statusList = quotaService.getCitizenCurrentMonthQuota(rationCardNumber);
        return ResponseEntity.ok(statusList);
    }
}
