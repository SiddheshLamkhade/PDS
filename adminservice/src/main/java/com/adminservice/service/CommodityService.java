package com.adminservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.adminservice.model.Commodity;
import com.adminservice.repository.CommodityRepository;

@Service
public class CommodityService {
    private final CommodityRepository repo;

    public CommodityService(CommodityRepository repo) {
        this.repo = repo;
    }

    // ✅ Create new commodity (avoid duplicates)
    public Commodity create(Commodity c) {
        Optional<Commodity> exists = repo.findByNameIgnoreCase(c.getName());
        if (exists.isPresent()) {
            throw new IllegalArgumentException("Commodity already exists: " + c.getName());
        }
        return repo.save(c);
    }

    // ✅ Update existing commodity
    public Commodity update(Long id, Commodity updated) {
        Commodity c = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Commodity not found with id " + id));

        // Update fields
        c.setName(updated.getName());
        c.setUnit(updated.getUnit());
        c.setDescription(updated.getDescription());

        return repo.save(c);
    }

    //✅ Delete commodity (safe: check entitlement rules first)
    public void delete(Long id) {
        Commodity c = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Commodity not found with id " + id));

        if (c.getEntitlementRules() != null && !c.getEntitlementRules().isEmpty()) {
            throw new IllegalStateException("Cannot delete commodity linked to entitlement rules");
        }

        repo.delete(c);
    }

    // ✅ List all commodities
    public List<Commodity> listAll() {
        return repo.findAll();
    }

    // ✅ Get single commodity
    public Commodity getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Commodity not found with id " + id));
    }
}

