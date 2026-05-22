package com.example.order.mapper;

import com.example.order.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    Product selectById(Integer id);

    int decreaseStock(@Param("id") Integer id, @Param("quantity") Integer quantity);

    List<Product> selectAll();
}
