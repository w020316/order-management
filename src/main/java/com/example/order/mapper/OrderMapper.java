package com.example.order.mapper;

import com.example.order.entity.OrderDetail;
import com.example.order.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    int insert(Orders orders);
    OrderDetail selectOrderDetailById(Integer id);
    List<OrderDetail> selectOrderDetailsByUserId(Integer userId);
}
