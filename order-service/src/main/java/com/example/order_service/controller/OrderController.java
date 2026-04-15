package com.example.order_service.controller;

import com.example.order_service.entity.Order;
import com.example.order_service.entity.OrderRequest;
import com.example.order_service.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 🟢 CREATE ORDER API
    @PostMapping
    public Order createOrder(@RequestBody OrderRequest request,
                             @AuthenticationPrincipal Jwt jwt) {

        return orderService.create(request, jwt);
    }


}
