package com.mainapp.model;

public enum DealerStatus {
    PENDING_APPROVAL,   // Self-registered, waiting for admin approval
    APPROVED,           // Admin approved the dealer
    ACTIVE,             // Dealer is active and can distribute
    REJECTED,           // Admin rejected the application
    SUSPENDED,          // Temporarily suspended by admin
    INACTIVE            // Deactivated
}
