package com.example.payment_service.service;

import com.example.payment_service.config.RabbitConfig;
import com.example.payment_service.enitity.OrderEvent;
import com.example.payment_service.enitity.Payment;
import com.example.payment_service.enitity.PaymentEvent;
import com.example.payment_service.repo.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RabbitTemplate rabbitTemplate;

    public Payment processPayment(OrderEvent event) {

        Payment payment = new Payment();
        payment.setOrderId(event.getOrderId());
        payment.setUserId(event.getUserId());
        payment.setUsername(event.getUsername());
        payment.setAmount(event.getAmount());

        // 👉 simulate payment logic
        payment.setStatus("PAID");

        Payment saved = paymentRepository.save(payment);

        // 👉 publish event to notification
        PaymentEvent paymentEvent = new PaymentEvent();
        paymentEvent.setOrderId(saved.getOrderId());
        paymentEvent.setUserId(saved.getUserId());
        paymentEvent.setUsername(saved.getUsername());
        paymentEvent.setAmount(saved.getAmount());
        paymentEvent.setStatus(saved.getStatus());

        rabbitTemplate.convertAndSend(
                RabbitConfig.PAYMENT_EXCHANGE,
                RabbitConfig.PAYMENT_ROUTING_KEY,
                paymentEvent
        );

        return saved;
    }
}