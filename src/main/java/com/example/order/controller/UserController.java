package com.example.order.controller;

import com.example.order.common.Result;
import com.example.order.dto.CreateUserRequest;
import com.example.order.dto.UpdateUserRequest;
import com.example.order.entity.User;
import com.example.order.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Result<List<User>> getAllUsers() {
        return Result.success(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Integer id) {
        return Result.success(userService.getUserById(id));
    }

    @PostMapping
    public Result<User> createUser(@Validated @RequestBody CreateUserRequest request) {
        return Result.success(userService.createUser(request));
    }

    @PutMapping("/{id}")
    public Result<User> updateUser(@PathVariable Integer id, @RequestBody UpdateUserRequest request) {
        return Result.success(userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return Result.success();
    }
}
