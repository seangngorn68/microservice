package com.example.order_service.entity;

import lombok.Data;

@Data
public class PaymentEvent {
    private Long orderId;
    private String status; // SUCCESS / FAILED
    private String userId;
    private String username;
}