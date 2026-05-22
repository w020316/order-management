package com.example.order.controller;

import com.example.order.common.Result;
import com.example.order.dto.CreateOrderRequest;
import com.example.order.entity.Orders;
import com.example.order.service.OrderService;
import com.example.order.vo.OrderDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Result<Orders> createOrder(@Validated @RequestBody CreateOrderRequest request) {
        Orders orders = orderService.createOrder(request);
        return Result.success(orders);
    }

    @GetMapping("/{id}")
    public Result<OrderDetailVO> getOrderDetail(@PathVariable Integer id) {
        OrderDetailVO detail = orderService.getOrderDetail(id);
        return Result.success(detail);
    }

    @GetMapping("/user/{userId}")
    public Result<List<OrderDetailVO>> getOrdersByUserId(@PathVariable Integer userId) {
        List<OrderDetailVO> list = orderService.getOrdersByUserId(userId);
        return Result.success(list);
    }
}
