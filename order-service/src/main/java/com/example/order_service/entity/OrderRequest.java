package com.example.order_service.entity;

import lombok.Data;

@Data
public class OrderRequest {

    private String product;
    private Double amount;

}
