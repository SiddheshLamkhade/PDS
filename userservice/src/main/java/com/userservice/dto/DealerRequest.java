package com.userservice.dto;

import jakarta.validation.constraints.NotBlank;

public class DealerRequest {
    @NotBlank public String dealerName;
    @NotBlank public String shopLocation;
    public String contactInfo;
}