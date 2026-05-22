package com.example.order.dto;

import lombok.Data;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CreateProductRequest {

    @NotBlank(message = "商品名称不能为空")
    private String productName;

    @NotNull(message = "商品价格不能为空")
    @DecimalMin(value = "0.01", message = "商品价格必须大于0")
    private BigDecimal price;

    @NotNull(message = "商品库存不能为空")
    @Min(value = 0, message = "商品库存不能为负数")
    private Integer stock;
}
