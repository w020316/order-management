package com.example.order.mapper;

import com.example.order.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    User selectById(Integer id);
    List<User> selectAll();
    int insert(User user);
    int updateById(User user);
    int deleteById(Integer id);
}
