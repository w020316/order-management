package com.example.order.service;

import com.example.order.common.OrderStatus;
import com.example.order.dto.CreateOrderRequest;
import com.example.order.entity.OrderDetail;
import com.example.order.entity.Orders;
import com.example.order.entity.Product;
import com.example.order.entity.User;
import com.example.order.exception.BusinessException;
import com.example.order.mapper.OrderMapper;
import com.example.order.mapper.ProductMapper;
import com.example.order.mapper.UserMapper;
import com.example.order.service.impl.OrderServiceImpl;
import com.example.order.vo.OrderDetailVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private ProductMapper productMapper;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private User testUser;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("张三");
        testUser.setPhone("13800138001");

        testProduct = new Product();
        testProduct.setId(1);
        testProduct.setProductName("iPhone 15");
        testProduct.setPrice(new BigDecimal("5999.00"));
        testProduct.setStock(100);
    }

    @Test
    void createOrder_success() {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setUserId(1);
        request.setProductId(1);
        request.setQuantity(2);

        when(userMapper.selectById(1)).thenReturn(testUser);
        when(productMapper.selectById(1)).thenReturn(testProduct);
        when(productMapper.decreaseStock(1, 2)).thenReturn(1);
        when(orderMapper.insert(any(Orders.class))).thenReturn(1);

        Orders result = orderService.createOrder(request);

        assertNotNull(result);
        assertEquals(new BigDecimal("11998.00"), result.getTotalPrice());
        assertEquals(OrderStatus.PENDING.getCode(), result.getStatus());
        verify(productMapper).decreaseStock(1, 2);
        verify(orderMapper).insert(any(Orders.class));
    }

    @Test
    void createOrder_userNotFound() {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setUserId(999);
        request.setProductId(1);
        request.setQuantity(1);

        when(userMapper.selectById(999)).thenReturn(null);

        assertThrows(BusinessException.class, () -> orderService.createOrder(request));
    }

    @Test
    void createOrder_productNotFound() {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setUserId(1);
        request.setProductId(999);
        request.setQuantity(1);

        when(userMapper.selectById(1)).thenReturn(testUser);
        when(productMapper.selectById(999)).thenReturn(null);

        assertThrows(BusinessException.class, () -> orderService.createOrder(request));
    }

    @Test
    void createOrder_insufficientStock() {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setUserId(1);
        request.setProductId(1);
        request.setQuantity(200);

        when(userMapper.selectById(1)).thenReturn(testUser);
        when(productMapper.selectById(1)).thenReturn(testProduct);

        BusinessException ex = assertThrows(BusinessException.class, () -> orderService.createOrder(request));
        assertTrue(ex.getMessage().contains("库存不足"));
    }

    @Test
    void createOrder_concurrentStockDecreaseFail() {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setUserId(1);
        request.setProductId(1);
        request.setQuantity(1);

        when(userMapper.selectById(1)).thenReturn(testUser);
        when(productMapper.selectById(1)).thenReturn(testProduct);
        when(productMapper.decreaseStock(1, 1)).thenReturn(0);

        assertThrows(BusinessException.class, () -> orderService.createOrder(request));
    }

    @Test
    void cancelOrder_success() {
        Orders order = new Orders();
        order.setId(1);
        order.setUserId(1);
        order.setProductId(1);
        order.setQuantity(2);
        order.setStatus(OrderStatus.PENDING.getCode());

        when(orderMapper.selectById(1)).thenReturn(order);
        when(orderMapper.updateStatus(1, OrderStatus.CANCELLED.getCode())).thenReturn(1);
        when(productMapper.increaseStock(1, 2)).thenReturn(1);

        OrderDetail detail = new OrderDetail();
        detail.setId(1);
        detail.setStatus(OrderStatus.CANCELLED.getCode());
        when(orderMapper.selectOrderDetailById(1)).thenReturn(detail);

        OrderDetailVO result = orderService.cancelOrder(1);
        assertNotNull(result);
        verify(productMapper).increaseStock(1, 2);
    }

    @Test
    void cancelOrder_alreadyPaid() {
        Orders order = new Orders();
        order.setId(1);
        order.setStatus(OrderStatus.PAID.getCode());

        when(orderMapper.selectById(1)).thenReturn(order);

        assertThrows(BusinessException.class, () -> orderService.cancelOrder(1));
    }

    @Test
    void payOrder_success() {
        Orders order = new Orders();
        order.setId(1);
        order.setStatus(OrderStatus.PENDING.getCode());

        when(orderMapper.selectById(1)).thenReturn(order);
        when(orderMapper.updateStatus(1, OrderStatus.PAID.getCode())).thenReturn(1);

        OrderDetail detail = new OrderDetail();
        detail.setId(1);
        detail.setStatus(OrderStatus.PAID.getCode());
        when(orderMapper.selectOrderDetailById(1)).thenReturn(detail);

        OrderDetailVO result = orderService.payOrder(1);
        assertNotNull(result);
        assertEquals(OrderStatus.PAID.getCode(), result.getStatus());
    }

    @Test
    void getOrderDetail_notFound() {
        when(orderMapper.selectOrderDetailById(999)).thenReturn(null);
        assertThrows(BusinessException.class, () -> orderService.getOrderDetail(999));
    }
}
