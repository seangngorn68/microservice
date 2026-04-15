package com.example.order_service.service;

import com.example.order_service.entity.Order;
import com.example.order_service.entity.OrderEvent;
import com.example.order_service.entity.OrderRequest;
import com.example.order_service.entity.OrderStatus;
import com.example.order_service.repo.OrderRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final OrderPublisher orderPublisher;

    public OrderService(OrderRepository orderRepository, OrderPublisher orderPublisher) {
        this.orderRepository = orderRepository;
        this.orderPublisher = orderPublisher;
    }
    public Order create(OrderRequest request, Jwt jwt) {

        Order order = new Order();
        order.setProduct(request.getProduct());
        order.setAmount(request.getAmount());
        order.setUserId(jwt.getSubject());
        order.setUsername(jwt.getClaim("preferred_username"));
        order.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);

        // 🔥 publish event after save
        OrderEvent event = new OrderEvent();
        event.setOrderId(savedOrder.getId());
        event.setProduct(savedOrder.getProduct());
        event.setAmount(savedOrder.getAmount());
        event.setUserId(savedOrder.getUserId());
        event.setUsername(savedOrder.getUsername());
        event.setStatus(OrderStatus.PENDING.name());

        orderPublisher.publish(event);

        return savedOrder;
    }
}
