package com.mainapp.controller;

import com.mainapp.dto.ApiResponse;
import com.mainapp.dto.DistributionResponse;
import com.mainapp.dto.InventoryResponse;
import com.mainapp.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dashboard/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboardStats() {
        Map<String, Object> stats = adminService.getDashboardStats();
        return ResponseEntity.ok(ApiResponse.success("Dashboard statistics retrieved successfully", stats));
    }

    @GetMapping("/reports/dealer/{dealerId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDealerReport(@PathVariable Long dealerId) {
        try {
            Map<String, Object> report = adminService.getDealerReport(dealerId);
            return ResponseEntity.ok(ApiResponse.success("Dealer report generated successfully", report));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/reports/dealer/{dealerId}/distributions")
    public ResponseEntity<ApiResponse<List<DistributionResponse>>> getDealerDistributions(@PathVariable Long dealerId) {
        try {
            List<DistributionResponse> distributions = adminService.getDealerDistributions(dealerId);
            return ResponseEntity.ok(ApiResponse.success("Dealer distributions retrieved successfully", distributions));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/reports/product/{productId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getProductReport(@PathVariable Long productId) {
        try {
            Map<String, Object> report = adminService.getProductReport(productId);
            return ResponseEntity.ok(ApiResponse.success("Product report generated successfully", report));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/reports/citizen/{rationCardNumber}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCitizenReport(@PathVariable String rationCardNumber) {
        try {
            Map<String, Object> report = adminService.getCitizenReport(rationCardNumber);
            return ResponseEntity.ok(ApiResponse.success("Citizen report generated successfully", report));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/alerts/low-stock")
    public ResponseEntity<ApiResponse<List<InventoryResponse>>> getLowStockAlerts() {
        List<InventoryResponse> alerts = adminService.getLowStockAlerts();
        return ResponseEntity.ok(ApiResponse.success("Low stock alerts retrieved successfully", alerts));
    }

    @GetMapping("/alerts/low-stock/dealer/{dealerId}")
    public ResponseEntity<ApiResponse<List<InventoryResponse>>> getLowStockAlertsByDealer(@PathVariable Long dealerId) {
        try {
            List<InventoryResponse> alerts = adminService.getLowStockAlertsByDealer(dealerId);
            return ResponseEntity.ok(ApiResponse.success("Low stock alerts retrieved successfully", alerts));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/reports/distributions/date-range")
    public ResponseEntity<ApiResponse<List<DistributionResponse>>> getDistributionsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<DistributionResponse> distributions = adminService.getDistributionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success("Distributions retrieved successfully", distributions));
    }

    @GetMapping("/reports/monthly/{year}/{month}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getMonthlyReport(
            @PathVariable int year,
            @PathVariable int month) {
        try {
            Map<String, Object> report = adminService.getMonthlyReport(year, month);
            return ResponseEntity.ok(ApiResponse.success("Monthly report generated successfully", report));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/reports/category/{category}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCategoryWiseReport(@PathVariable String category) {
        try {
            Map<String, Object> report = adminService.getCategoryWiseReport(category);
            return ResponseEntity.ok(ApiResponse.success("Category-wise report generated successfully", report));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}
