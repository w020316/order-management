package com.example.order.mapper;

import com.example.order.entity.OrderDetail;
import com.example.order.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    int insert(Orders orders);
    OrderDetail selectOrderDetailById(Integer id);
    List<OrderDetail> selectOrderDetailsByUserId(@Param("userId") Integer userId,
                                                  @Param("offset") Integer offset,
                                                  @Param("pageSize") Integer pageSize);
    Long selectCountByUserId(Integer userId);
    Orders selectById(Integer id);
    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);
}
