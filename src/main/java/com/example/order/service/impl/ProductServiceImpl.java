package com.example.order.service.impl;

import com.example.order.entity.Product;
import com.example.order.mapper.ProductMapper;
import com.example.order.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product getProductById(Integer id) {
        return productMapper.selectById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productMapper.selectAll();
    }
}
