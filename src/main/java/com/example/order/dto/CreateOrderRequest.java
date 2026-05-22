package com.example.order.dto;

import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class CreateOrderRequest {

    @NotNull(message = "用户ID不能为空")
    private Integer userId;

    @NotNull(message = "商品ID不能为空")
    private Integer productId;

    @NotNull(message = "购买数量不能为空")
    @Min(value = 1, message = "购买数量至少为1")
    private Integer quantity;
}
