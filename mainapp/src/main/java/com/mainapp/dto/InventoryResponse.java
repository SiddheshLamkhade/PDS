package com.mainapp.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponse {

    private Long id;
    private Long dealerId;
    private String dealerName;
    private Long productId;
    private String productName;
    private Double currentStock;
    private Double openingStock;
    private Double stockReceived;
    private Double stockDistributed;
    private LocalDateTime lastUpdated;
}
