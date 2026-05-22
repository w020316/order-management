package com.example.order.controller;

import com.example.order.common.Result;
import com.example.order.dto.CreateOrderRequest;
import com.example.order.dto.PageRequest;
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
    public Result<OrderDetailVO> createOrder(@Validated @RequestBody CreateOrderRequest request) {
        return Result.success(orderService.createOrder(request));
    }

    @GetMapping("/{id}")
    public Result<OrderDetailVO> getOrderDetail(@PathVariable Integer id) {
        return Result.success(orderService.getOrderDetail(id));
    }

    @GetMapping("/user/{userId}")
    public Result<PageResult<OrderDetailVO>> getOrdersByUserId(
            @PathVariable Integer userId, PageRequest pageRequest) {
        return Result.success(orderService.getOrdersByUserId(userId, pageRequest));
    }

    @PutMapping("/{id}/cancel")
    public Result<OrderDetailVO> cancelOrder(@PathVariable Integer id) {
        return Result.success(orderService.cancelOrder(id));
    }

    @PutMapping("/{id}/pay")
    public Result<OrderDetailVO> payOrder(@PathVariable Integer id) {
        return Result.success(orderService.payOrder(id));
    }

    @PutMapping("/{id}/complete")
    public Result<OrderDetailVO> completeOrder(@PathVariable Integer id) {
        return Result.success(orderService.completeOrder(id));
    }
}
