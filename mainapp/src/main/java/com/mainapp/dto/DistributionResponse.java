package com.mainapp.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistributionResponse {

    private Long id;
    private Long citizenId;
    private String citizenName;
    private String rationCardNumber;
    private Long dealerId;
    private String dealerName;
    private Long productId;
    private String productName;
    private Double quantity;
    private Double totalAmount;
    private LocalDateTime distributionDate;
    private String status;
    private String remarks;
}
