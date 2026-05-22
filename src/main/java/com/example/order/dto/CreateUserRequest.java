package com.example.order.dto;

import lombok.Data;

import jakarta.validation.constraints.Pattern;

@Data
public class CreateUserRequest {

    private String username;

    private String phone;
}
