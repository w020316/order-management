package com.example.order.service;

import com.example.order.dto.CreateOrderRequest;
import com.example.order.entity.OrderDetail;
import com.example.order.entity.Orders;
import com.example.order.vo.OrderDetailVO;

import java.util.List;

public interface OrderService {
    Orders createOrder(CreateOrderRequest request);
    OrderDetailVO getOrderDetail(Integer orderId);
    List<OrderDetailVO> getOrdersByUserId(Integer userId);
}
