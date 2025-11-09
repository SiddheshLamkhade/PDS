package com.mainapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "quotas", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"product_id", "category", "month", "year"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Product ID is required")
    private Long productId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    @NotNull(message = "Category is required")
    private Citizen.CitizenCategory category; // BPL or APL

    @Column(nullable = false)
    @Min(value = 1, message = "Month must be between 1 and 12")
    @Max(value = 12, message = "Month must be between 1 and 12")
    private Integer month;

    @Column(nullable = false)
    @Min(value = 2024, message = "Year must be valid")
    private Integer year;

    @Column(nullable = false)
    @DecimalMin(value = "0.0", inclusive = false, message = "Quota must be greater than 0")
    private Double quotaPerCitizen; // Quota per citizen for this product

    @Column(nullable = false)
    private Boolean active = true;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
