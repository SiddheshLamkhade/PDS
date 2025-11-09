package com.mainapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "distributions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Distribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Citizen ID is required")
    private Long citizenId;

    @Column(nullable = false)
    @NotNull(message = "Dealer ID is required")
    private Long dealerId;

    @Column(nullable = false)
    @NotNull(message = "Product ID is required")
    private Long productId;

    @Column(nullable = false)
    @DecimalMin(value = "0.0", inclusive = false, message = "Quantity must be greater than 0")
    private Double quantity;

    @Column(nullable = false)
    @DecimalMin(value = "0.0", message = "Total amount cannot be negative")
    private Double totalAmount;

    @Column(nullable = false)
    private LocalDateTime distributionDate;

    @Column(nullable = false, length = 20)
    private String status = "COMPLETED"; // COMPLETED, PENDING, CANCELLED

    @Column(length = 500)
    private String remarks;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (distributionDate == null) {
            distributionDate = LocalDateTime.now();
        }
    }
}
