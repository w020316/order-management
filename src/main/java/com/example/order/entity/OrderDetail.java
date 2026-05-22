package com.example.order.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDetail {
    private Integer id;
    private Integer userId;
    private String userName;
    private Integer productId;
    private String productName;
    private Integer quantity;
    private BigDecimal totalPrice;
    private Integer status;
    private LocalDateTime orderTime;
}
