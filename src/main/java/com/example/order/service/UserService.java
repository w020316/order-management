package com.example.order.service;

import com.example.order.dto.CreateUserRequest;
import com.example.order.dto.UpdateUserRequest;
import com.example.order.entity.User;

import java.util.List;

public interface UserService {
    User getUserById(Integer id);
    List<User> getAllUsers();
    User createUser(CreateUserRequest request);
    User updateUser(Integer id, UpdateUserRequest request);
    void deleteUser(Integer id);
}
