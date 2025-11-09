package com.mainapp.dto;

import com.mainapp.model.Citizen.CitizenCategory;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitizenResponse {

    private Long id;
    private Long userId;
    private String username;
    private String email;
    private String fullName;
    private String rationCardNumber;
    private String address;
    private String phoneNumber;
    private Integer familySize;
    private CitizenCategory category;
    private Long dealerId;
    private String dealerShopName; // For convenience
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
