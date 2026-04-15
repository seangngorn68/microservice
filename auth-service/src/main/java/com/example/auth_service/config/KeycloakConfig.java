package com.example.auth_service.config;

import org.springframework.stereotype.Component;

@Component
public class KeycloakConfig {

    public static final String SERVER_URL = "http://localhost:8181";
    public static final String REALM = "microservices-realm";
    public static final String CLIENT_ID = "auth-service-client";
    public static final String CLIENT_SECRET = "YOUR_SECRET";
}