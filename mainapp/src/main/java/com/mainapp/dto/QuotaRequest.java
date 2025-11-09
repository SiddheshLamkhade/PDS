package com.mainapp.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuotaRequest {

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotBlank(message = "Category is required")
    @Pattern(regexp = "BPL|APL", message = "Category must be BPL or APL")
    private String category;

    @NotNull(message = "Month is required")
    @Min(value = 1, message = "Month must be between 1 and 12")
    @Max(value = 12, message = "Month must be between 1 and 12")
    private Integer month;

    @NotNull(message = "Year is required")
    @Min(value = 2024, message = "Year must be valid")
    private Integer year;

    @NotNull(message = "Quota per citizen is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Quota must be greater than 0")
    private Double quotaPerCitizen;

    private String description;
}
