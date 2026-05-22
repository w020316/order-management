package com.example.order.controller;

import com.example.order.common.Result;
import com.example.order.dto.CreateOrderRequest;
import com.example.order.dto.PageRequest;
import com.example.order.entity.Orders;
import com.example.order.service.OrderService;
import com.example.order.vo.OrderDetailVO;
import com.example.order.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public Result<PageResult<OrderDetailVO>> getOrdersByUserId(
            @PathVariable Integer userId, PageRequest pageRequest) {
        PageResult<OrderDetailVO> result = orderService.getOrdersByUserId(userId, pageRequest);
        return Result.success(result);
    }

    @PutMapping("/{id}/cancel")
    public Result<OrderDetailVO> cancelOrder(@PathVariable Integer id) {
        OrderDetailVO detail = orderService.cancelOrder(id);
        return Result.success(detail);
    }

    @PutMapping("/{id}/pay")
    public Result<OrderDetailVO> payOrder(@PathVariable Integer id) {
        OrderDetailVO detail = orderService.payOrder(id);
        return Result.success(detail);
    }
}
