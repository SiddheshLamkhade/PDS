package com.userservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.userservice.model.Citizen;
import java.util.List;

@Entity
@Table(name = "dealers")
public class Dealer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dealerName;
    private String shopLocation;
    private String contactInfo;

    // optional: link back to citizens
    @OneToMany(mappedBy = "dealer")
    @JsonManagedReference
    private List<Citizen> citizens;

    // getters & setters
    // (generate in IDE or paste manually)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDealerName() { return dealerName; }
    public void setDealerName(String dealerName) { this.dealerName = dealerName; }
    public String getShopLocation() { return shopLocation; }
    public void setShopLocation(String shopLocation) { this.shopLocation = shopLocation; }
    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
    public List<Citizen> getCitizens() { return citizens; }
    public void setCitizens(List<Citizen> citizens) { this.citizens = citizens; }
}


