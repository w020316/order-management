package com.example.order.service.impl;

import com.example.order.entity.User;
import com.example.order.mapper.UserMapper;
import com.example.order.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(Integer id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return null;
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectAll();
    }
}
