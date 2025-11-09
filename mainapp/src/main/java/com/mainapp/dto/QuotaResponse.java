package com.mainapp.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuotaResponse {

    private Long id;
    private Long productId;
    private String productName;
    private String category;
    private Integer month;
    private Integer year;
    private Double quotaPerCitizen;
    private String unit;
    private Boolean active;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
