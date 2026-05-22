package com.example.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.order.mapper")
public class OrderManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderManagementApplication.class, args);
    }
}
