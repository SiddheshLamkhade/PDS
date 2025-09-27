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

import com.adminservice.model.Commodity;
import com.adminservice.service.CommodityService;

@RestController
@RequestMapping("/api/admin/commodities")
public class CommodityController {
    private final CommodityService service;
    public CommodityController(CommodityService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Commodity> create(@RequestBody Commodity c) {
        Commodity created = service.create(c);
        return ResponseEntity.created(URI.create("/api/admin/commodities/" + created.getId())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Commodity>> list() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commodity> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Commodity> update(@PathVariable Long id, @RequestBody Commodity c) {
        return ResponseEntity.ok(service.update(id, c));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

