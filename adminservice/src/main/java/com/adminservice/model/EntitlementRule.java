package com.adminservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "entitlement_rules")
public class EntitlementRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // category e.g., "BPL", "APL"
    @Column(nullable = false)
    private String category;

    // Many-to-one mapping to Commodity table
    @ManyToOne
    @JoinColumn(name = "commodity_id", nullable = false) // foreign key
    @JsonIgnoreProperties("entitlementRules")
    private Commodity commodity;

    // quantity per period (e.g., per month)
    @Column(nullable = false)
    private Double quantity;

    // unit (redundant but convenient)
    private String unit;

    // valid or inactive
    private boolean active = true;

    // --- Getters/Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Commodity getCommodity() { return commodity; }
    public void setCommodity(Commodity commodity) { this.commodity = commodity; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
