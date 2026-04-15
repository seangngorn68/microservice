package com.example.payment_service.service;

import com.example.payment_service.enitity.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderConsumer {

    private final PaymentService paymentService;

    @RabbitListener(queues = "order.queue")
    public void consume(OrderEvent event) {
        System.out.println("Received Order: " + event);
        paymentService.processPayment(event);
    }
}
