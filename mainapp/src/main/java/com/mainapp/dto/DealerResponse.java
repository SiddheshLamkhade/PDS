package com.mainapp.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DealerResponse {

    private Long id;
    private Long userId;
    private String username;
    private String email;
    private String fullName;
    private String shopName;
    private String shopLicense;
    private String address;
    private String phoneNumber;
    private String region;
    private Boolean active;
    private String status; // PENDING_APPROVAL, APPROVED, ACTIVE, REJECTED, SUSPENDED, INACTIVE
    private String rejectionReason;
    private LocalDateTime approvedAt;
    private Long approvedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
