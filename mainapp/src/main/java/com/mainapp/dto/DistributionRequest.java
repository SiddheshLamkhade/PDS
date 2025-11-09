package com.mainapp.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistributionRequest {

    @NotBlank(message = "Ration card number is required")
    private String rationCardNumber;

    @NotNull(message = "Dealer ID is required")
    private Long dealerId;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Quantity must be greater than 0")
    private Double quantity;

    private String remarks;
}
