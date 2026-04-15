package com.example.auth_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keycloakId;
    private String username;
    private String email;
    private String fullName;
    private String phone;

    private LocalDateTime createdAt = LocalDateTime.now();
}
