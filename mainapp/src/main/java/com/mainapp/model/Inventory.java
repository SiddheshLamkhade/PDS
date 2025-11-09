package com.mainapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"dealer_id", "product_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dealer_id", nullable = false)
    @NotNull(message = "Dealer ID is required")
    private Long dealerId;

    @Column(name = "product_id", nullable = false)
    @NotNull(message = "Product ID is required")
    private Long productId;

    @Column(nullable = false)
    @DecimalMin(value = "0.0", message = "Current stock cannot be negative")
    private Double currentStock = 0.0;

    @Column(nullable = false)
    @DecimalMin(value = "0.0", message = "Opening stock cannot be negative")
    private Double openingStock = 0.0;

    @Column(nullable = false)
    @DecimalMin(value = "0.0", message = "Stock received cannot be negative")
    private Double stockReceived = 0.0;

    @Column(nullable = false)
    @DecimalMin(value = "0.0", message = "Stock distributed cannot be negative")
    private Double stockDistributed = 0.0;

    @Column(nullable = false)
    private LocalDateTime lastUpdated;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        lastUpdated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }
}
