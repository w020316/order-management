package com.example.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {

    private String productName;

    private BigDecimal price;

    private Integer stock;
}
