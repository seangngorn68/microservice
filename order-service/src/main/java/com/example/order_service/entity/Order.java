package com.example.order_service.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String product;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private String userId;   // from JWT (sub)
    private String username; // optional (preferred_username)
}
