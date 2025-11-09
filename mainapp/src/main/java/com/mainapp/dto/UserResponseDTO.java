package com.mainapp.dto;

import com.mainapp.model.User.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;
    private String username;
    private String email;
    private String fullName;
    private UserRole role;
    private String phone;
    private String aadhaarRef;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
