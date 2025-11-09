package com.mainapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "dealer_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dealer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Shop name is required")
    private String shopName;

    @Column(unique = true, nullable = false, length = 50)
    @NotBlank(message = "Shop license is required")
    private String shopLicense;

    @Column(nullable = false, length = 255)
    @NotBlank(message = "Address is required")
    private String address;

    @Column(length = 15)
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @Column(length = 100)
    private String region;

    @Column(nullable = false)
    private Boolean active = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DealerStatus status = DealerStatus.PENDING_APPROVAL;

    @Column(length = 500)
    private String rejectionReason;

    @Column
    private LocalDateTime approvedAt;

    @Column
    private Long approvedBy; // Admin user ID who approved

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
