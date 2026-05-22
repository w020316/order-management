package com.example.order.service.impl;

import com.example.order.common.OrderStatus;
import com.example.order.dto.CreateOrderRequest;
import com.example.order.dto.PageRequest;
import com.example.order.entity.OrderDetail;
import com.example.order.entity.Orders;
import com.example.order.entity.Product;
import com.example.order.entity.User;
import com.example.order.exception.BusinessException;
import com.example.order.mapper.OrderMapper;
import com.example.order.mapper.ProductMapper;
import com.example.order.mapper.UserMapper;
import com.example.order.service.OrderService;
import com.example.order.vo.OrderDetailVO;
import com.example.order.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Orders createOrder(CreateOrderRequest request) {
        User user = userMapper.selectById(request.getUserId());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        Product product = productMapper.selectById(request.getProductId());
        if (product == null) {
            throw new BusinessException("商品不存在");
        }
        if (product.getStock() < request.getQuantity()) {
            throw new BusinessException("库存不足，当前库存: " + product.getStock());
        }

        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()));

        int rows = productMapper.decreaseStock(request.getProductId(), request.getQuantity());
        if (rows == 0) {
            throw new BusinessException("库存不足，下单失败");
        }

        Orders orders = new Orders();
        orders.setUserId(request.getUserId());
        orders.setProductId(request.getProductId());
        orders.setQuantity(request.getQuantity());
        orders.setTotalPrice(totalPrice);
        orders.setStatus(OrderStatus.PENDING.getCode());
        orderMapper.insert(orders);

        return orders;
    }

    @Override
    public OrderDetailVO getOrderDetail(Integer orderId) {
        OrderDetail detail = orderMapper.selectOrderDetailById(orderId);
        if (detail == null) {
            throw new BusinessException("订单不存在");
        }
        return convertToVO(detail);
    }

    @Override
    public PageResult<OrderDetailVO> getOrdersByUserId(Integer userId, PageRequest pageRequest) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        Long total = orderMapper.selectCountByUserId(userId);
        List<OrderDetail> details = orderMapper.selectOrderDetailsByUserId(
                userId, pageRequest.getOffset(), pageRequest.getPageSize());
        List<OrderDetailVO> voList = details.stream().map(this::convertToVO).collect(Collectors.toList());

        return PageResult.of(voList, total, pageRequest.getPageNum(), pageRequest.getPageSize());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDetailVO cancelOrder(Integer orderId) {
        Orders order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if (order.getStatus() != OrderStatus.PENDING.getCode()) {
            throw new BusinessException("只能取消待支付的订单");
        }

        orderMapper.updateStatus(orderId, OrderStatus.CANCELLED.getCode());
        productMapper.increaseStock(order.getProductId(), order.getQuantity());

        return getOrderDetail(orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDetailVO payOrder(Integer orderId) {
        Orders order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if (order.getStatus() != OrderStatus.PENDING.getCode()) {
            throw new BusinessException("只能支付待支付的订单");
        }

        orderMapper.updateStatus(orderId, OrderStatus.PAID.getCode());

        return getOrderDetail(orderId);
    }

    private OrderDetailVO convertToVO(OrderDetail detail) {
        OrderDetailVO vo = new OrderDetailVO();
        vo.setOrderId(detail.getId());
        vo.setUserName(detail.getUserName());
        vo.setProductName(detail.getProductName());
        vo.setQuantity(detail.getQuantity());
        vo.setTotalPrice(detail.getTotalPrice());
        vo.setStatus(detail.getStatus());
        vo.setStatusDesc(OrderStatus.fromCode(detail.getStatus()).getDesc());
        if (detail.getOrderTime() != null) {
            vo.setOrderTime(detail.getOrderTime().format(FORMATTER));
        }
        return vo;
    }
}
