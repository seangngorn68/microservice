package com.example.notification_service.enitiy;

import lombok.Data;

@Data
public class NotificationRequest {

    private Long orderId;
    private String userId;
    private String username;
    private String status;
}
