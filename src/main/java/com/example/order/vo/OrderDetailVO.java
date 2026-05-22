package com.example.order.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetailVO {

    private Integer orderId;

    private String userName;

    private String productName;

    private Integer quantity;

    private BigDecimal totalPrice;

    private String orderTime;
}
