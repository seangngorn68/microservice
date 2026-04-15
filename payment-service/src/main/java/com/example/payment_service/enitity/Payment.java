package com.example.payment_service.enitity;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private String userId;
    private String username;
    private Double amount;
    private String status; // SUCCESS, FAILED
    private LocalDateTime createdAt;
}
