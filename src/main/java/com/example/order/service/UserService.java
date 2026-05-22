package com.example.order.service;

import com.example.order.entity.Product;
import com.example.order.entity.User;

import java.util.List;

public interface UserService {
    User getUserById(Integer id);
    List<User> getAllUsers();
}
