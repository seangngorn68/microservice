package com.example.payment_service.enitity;

import lombok.Data;

@Data
public class OrderEvent {
    private Long orderId;
    private String product;
    private Double amount;
    private String userId;
    private String username;
    private String status;
}
