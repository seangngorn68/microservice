package com.example.order_service.consumer;

import com.example.order_service.entity.Order;
import com.example.order_service.entity.OrderStatus;
import com.example.order_service.entity.PaymentEvent;
import com.example.order_service.repo.OrderRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentConsumer {

    private final OrderRepository orderRepository;

    @PostConstruct
    public void init() {
        System.out.println("🔥 PaymentConsumer LOADED SUCCESSFULLY");
    }
    @RabbitListener(queues = "payment.queue", concurrency = "3-10")
    public void consume(PaymentEvent event) {

        try {
            System.out.println("🔥 Received: " + event);

            Order order = orderRepository.findById(event.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            order.setStatus(OrderStatus.valueOf(event.getStatus()));

            orderRepository.save(order);

            System.out.println("✅ Updated");

        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            throw e; // 🔥 MUST THROW for retry
        }
    }


//    @RabbitListener(queues = "payment.queue")
//    public void consume(PaymentEvent event) {
//
//        System.out.println("Payment update received: " + event);
//
//        Order order = orderRepository.findById(event.getOrderId())
//                .orElseThrow();
//
//        order.setStatus(OrderStatus.valueOf(event.getStatus()));
//
//        System.out.println("🔥event.getStatus()"+event.getStatus());
//
//        orderRepository.save(order);
//    }
}

