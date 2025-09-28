package com.adminservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adminservice.dto.DealerProxyDto;
import com.adminservice.service.DealerProxyService;

@RestController
@RequestMapping("/api/admin/dealers")
public class DealerProxyController {

    private final DealerProxyService dealerProxyService;

    public DealerProxyController(DealerProxyService dealerProxyService) {
        this.dealerProxyService = dealerProxyService;
    }

    @GetMapping
    public ResponseEntity<List<DealerProxyDto>> listDealers() {
        return ResponseEntity.ok(dealerProxyService.getAllDealers());
    }

    @GetMapping("/{dealerId}")
    public ResponseEntity<DealerProxyDto> getDealer(@PathVariable Long dealerId) {
        return dealerProxyService.getDealerById(dealerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
}
