package com.adminservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.adminservice.model.Commodity;
import com.adminservice.model.EntitlementRule;
import com.adminservice.repository.CommodityRepository;
import com.adminservice.repository.EntitlementRepository;

@Service
public class EntitlementService {

    private final EntitlementRepository repo;
    private final CommodityRepository commodityRepo;

    public EntitlementService(EntitlementRepository repo, CommodityRepository commodityRepo) {
        this.repo = repo;
        this.commodityRepo = commodityRepo;
    }

    // ✅ Create new rule
    public EntitlementRule create(EntitlementRule r) {
        // Fetch commodity from DB (avoid transient object)
        Commodity commodity = commodityRepo.findById(r.getCommodity().getId())
                .orElseThrow(() -> new IllegalArgumentException("Commodity not found"));
        r.setCommodity(commodity);
        return repo.save(r);
    }


    // ✅ Update rule
    public EntitlementRule update(Long id, EntitlementRule r) {
        EntitlementRule existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rule not found"));

        existing.setCategory(r.getCategory());
        existing.setQuantity(r.getQuantity());
        existing.setUnit(r.getUnit());
        existing.setActive(r.isActive());

        // If commodity changed → fetch new one
        if (r.getCommodity() != null) {
            Commodity commodity = commodityRepo.findById(r.getCommodity().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Commodity not found"));
            existing.setCommodity(commodity);
        }

        return repo.save(existing);
    }

    // ✅ List all rules
    public List<EntitlementRule> listAll() {
        return repo.findAll();
    }

    // ✅ List rules by category (e.g., BPL / APL)
    public List<EntitlementRule> listByCategory(String category) {
        return repo.findByCategory(category);
    }

    // ✅ Delete rule
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
