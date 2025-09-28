package com.adminservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adminservice.dto.CitizenProxyDto;
import com.adminservice.service.CitizenProxyService;

@RestController
@RequestMapping("/api/admin/citizens")
public class CitizenProxyController {

    private final CitizenProxyService citizenProxyService;

    public CitizenProxyController(CitizenProxyService citizenProxyService) {
        this.citizenProxyService = citizenProxyService;
    }

    @GetMapping
    public ResponseEntity<List<CitizenProxyDto>> listCitizens() {
        return ResponseEntity.ok(citizenProxyService.getAllCitizens());
    }

    @GetMapping("/{rationCardNo}")
    public ResponseEntity<CitizenProxyDto> getCitizenByRationCard(@PathVariable String rationCardNo) {
        return citizenProxyService.getCitizenByRationCard(rationCardNo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-auth-id/{authUserId}")
    public ResponseEntity<CitizenProxyDto> getCitizenByAuthUserId(@PathVariable String authUserId) {
        return citizenProxyService.getCitizenByAuthUserId(authUserId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
