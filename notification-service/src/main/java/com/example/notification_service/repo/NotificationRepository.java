package com.example.notification_service.repo;

import com.example.notification_service.enitiy.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
