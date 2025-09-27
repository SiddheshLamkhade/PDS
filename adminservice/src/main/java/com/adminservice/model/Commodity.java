package com.adminservice.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "commodities")
public class Commodity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;        // e.g., "Rice"

    @Column(nullable = false)
    private String unit;        // e.g., "kg"

    private String description;

    // One commodity can have many entitlement rules
    @OneToMany(mappedBy = "commodity", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("commodity")
    private List<EntitlementRule> entitlementRules;

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<EntitlementRule> getEntitlementRules() { return entitlementRules; }
    public void setEntitlementRules(List<EntitlementRule> entitlementRules) { this.entitlementRules = entitlementRules; }
}

