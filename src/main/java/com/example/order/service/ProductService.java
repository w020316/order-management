package com.example.order.service;

import com.example.order.dto.CreateProductRequest;
import com.example.order.dto.PageRequest;
import com.example.order.dto.UpdateProductRequest;
import com.example.order.entity.Product;
import com.example.order.vo.PageResult;

public interface ProductService {
    Product getProductById(Integer id);
    PageResult<Product> getAllProducts(PageRequest pageRequest);
    Product createProduct(CreateProductRequest request);
    Product updateProduct(Integer id, UpdateProductRequest request);
    void deleteProduct(Integer id);
}
