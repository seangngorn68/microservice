package com.example.notification_service.enitiy;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String username;

    private String message;
    private String type; // PAYMENT_SUCCESS, ORDER_CREATED

    private LocalDateTime createdAt;
}
