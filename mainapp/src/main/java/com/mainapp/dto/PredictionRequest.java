package com.mainapp.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PredictionRequest {

    @NotNull(message = "Dealer ID is required")
    private Long dealerId;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotBlank(message = "Prediction month is required (format: YYYY-MM)")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])$", message = "Month format must be YYYY-MM")
    private String predictionMonth;
}
