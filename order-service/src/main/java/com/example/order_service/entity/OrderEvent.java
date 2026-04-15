package com.example.order_service.entity;


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
