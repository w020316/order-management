package com.example.order.service;

import com.example.order.dto.CreateOrderRequest;
import com.example.order.dto.PageRequest;
import com.example.order.entity.Orders;
import com.example.order.vo.OrderDetailVO;
import com.example.order.vo.PageResult;

public interface OrderService {
    OrderDetailVO createOrder(CreateOrderRequest request);
    OrderDetailVO getOrderDetail(Integer orderId);
    PageResult<OrderDetailVO> getOrdersByUserId(Integer userId, PageRequest pageRequest);
    OrderDetailVO cancelOrder(Integer orderId);
    OrderDetailVO payOrder(Integer orderId);
    OrderDetailVO completeOrder(Integer orderId);
}
