package com.mainapp.controller;

import com.mainapp.dto.ApiResponse;
import com.mainapp.dto.InventoryRequest;
import com.mainapp.dto.InventoryResponse;
import com.mainapp.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping({"/add-stock", "/add"})
    public ResponseEntity<ApiResponse<InventoryResponse>> addStock(@Valid @RequestBody InventoryRequest request) {
        try {
            InventoryResponse response = inventoryService.addStock(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Stock added successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/check-stock")
    public ResponseEntity<ApiResponse<InventoryResponse>> checkStock(
            @RequestParam Long dealerId,
            @RequestParam Long productId) {
        try {
            InventoryResponse response = inventoryService.checkStock(dealerId, productId);
            return ResponseEntity.ok(ApiResponse.success("Stock retrieved successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/dealer/{dealerId}")
    public ResponseEntity<ApiResponse<List<InventoryResponse>>> getInventoryByDealer(@PathVariable Long dealerId) {
        try {
            List<InventoryResponse> response = inventoryService.getInventoryByDealer(dealerId);
            return ResponseEntity.ok(ApiResponse.success("Inventory retrieved successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InventoryResponse>>> getAllInventory() {
        List<InventoryResponse> response = inventoryService.getAllInventory();
        return ResponseEntity.ok(ApiResponse.success("All inventory retrieved successfully", response));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<ApiResponse<List<InventoryResponse>>> getLowStockAlerts(
            @RequestParam(defaultValue = "50.0") Double threshold) {
        List<InventoryResponse> response = inventoryService.getLowStockAlerts(threshold);
        return ResponseEntity.ok(ApiResponse.success("Low stock alerts retrieved successfully", response));
    }

    @GetMapping("/low-stock/dealer/{dealerId}")
    public ResponseEntity<ApiResponse<List<InventoryResponse>>> getLowStockByDealer(
            @PathVariable Long dealerId,
            @RequestParam(defaultValue = "50.0") Double threshold) {
        try {
            List<InventoryResponse> response = inventoryService.getLowStockByDealer(dealerId, threshold);
            return ResponseEntity.ok(ApiResponse.success("Low stock alerts retrieved successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}
