package com.example.order.mapper;

import com.example.order.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    Product selectById(Integer id);

    int decreaseStock(@Param("id") Integer id, @Param("quantity") Integer quantity);

    int increaseStock(@Param("id") Integer id, @Param("quantity") Integer quantity);

    List<Product> selectAll();

    List<Product> selectByPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    Long selectCount();

    int insert(Product product);

    int updateById(Product product);

    int deleteById(Integer id);
}
