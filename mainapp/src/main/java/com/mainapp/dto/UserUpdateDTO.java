package com.mainapp.dto;

import com.mainapp.model.User.UserRole;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDTO {

    private Long id;

    // Optional on update - no @NotBlank
    private String username;

    @Email(message = "Invalid email format")
    private String email;

    // Optional - only updated when provided
    private String password;

    private String fullName;

    private UserRole role;

    private Boolean active;
}
