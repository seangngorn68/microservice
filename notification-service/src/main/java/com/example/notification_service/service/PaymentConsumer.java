package com.example.notification_service.service;

import com.example.notification_service.enitiy.PaymentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentConsumer {

    @RabbitListener(queues = "payment.queue")
    public void receive(PaymentEvent event) {

        System.out.println("📩 Notification received: " + event);

        // 👉 simulate sending notification
        if ("SUCCESS".equals(event.getStatus())) {
            System.out.println("✅ Payment success for user: " + event.getUsername());
        } else {
            System.out.println("❌ Payment failed for user: " + event.getUsername());
        }
    }
}
