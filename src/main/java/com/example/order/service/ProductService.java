package com.example.order.service;

import com.example.order.entity.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(Integer id);
    List<Product> getAllProducts();
}
