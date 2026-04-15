package com.example.payment_service.enitity;


import lombok.Data;

@Data
public class PaymentEvent {
    private Long orderId;
    private String userId;
    private String username;
    private Double amount;
    private String status;
}
