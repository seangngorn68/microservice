package com.example.notification_service.enitiy;

import lombok.Data;

@Data
public class PaymentEvent {
    private Long orderId;
    private String userId;
    private String username;
    private Double amount;
    private String status; // or use Enum (recommended)
}
