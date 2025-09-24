package com.userservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "citizens", uniqueConstraints = @UniqueConstraint(columnNames = "rationCardNo"))
public class Citizen {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String rationCardNo;
    private int familySize;
    private String category; // BPL/APL

    // link to dealer
    @ManyToOne
    @JoinColumn(name = "dealer_id")
    @JsonBackReference
    private Dealer dealer;

    // Optional: store Keycloak id for later integration
    private String authUserId;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRationCardNo() { return rationCardNo; }
    public void setRationCardNo(String rationCardNo) { this.rationCardNo = rationCardNo; }
    public int getFamilySize() { return familySize; }
    public void setFamilySize(int familySize) { this.familySize = familySize; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Dealer getDealer() { return dealer; }
    public void setDealer(Dealer dealer) { this.dealer = dealer; }
    public String getAuthUserId() { return authUserId; }
    public void setAuthUserId(String authUserId) { this.authUserId = authUserId; }
}
