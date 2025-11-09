package com.mainapp.dto;

import com.mainapp.model.Citizen.CitizenCategory;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitizenRequest {

    // User-related fields
    @NotBlank(message = "Username is required")
    private String username;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Full name is required")
    private String fullName;

    // Citizen profile fields
    @NotBlank(message = "Ration card number is required")
    private String rationCardNumber;

    @NotBlank(message = "Address is required")
    private String address;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @NotNull(message = "Family size is required")
    @Min(value = 1, message = "Family size must be at least 1")
    private Integer familySize;

    @NotNull(message = "Category is required")
    private CitizenCategory category;

    private Long dealerId; // Optional: can be assigned later
}
