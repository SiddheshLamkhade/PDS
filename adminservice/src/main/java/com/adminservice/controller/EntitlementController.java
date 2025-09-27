package com.adminservice.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adminservice.model.EntitlementRule;
import com.adminservice.service.EntitlementService;

@RestController
@RequestMapping("/api/admin/entitlements")
public class EntitlementController {
    private final EntitlementService service;

    public EntitlementController(EntitlementService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<EntitlementRule> create(@RequestBody EntitlementRule rule) {
        EntitlementRule created = service.create(rule);
        return ResponseEntity.created(URI.create("/api/admin/entitlements/" + created.getId())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<EntitlementRule>> list() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<EntitlementRule>> listByCategory(@PathVariable String category) {
        return ResponseEntity.ok(service.listByCategory(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntitlementRule> update(@PathVariable Long id, @RequestBody EntitlementRule rule) {
        return ResponseEntity.ok(service.update(id, rule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

