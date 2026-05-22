package com.example.order.controller;

import com.example.order.common.Result;
import com.example.order.entity.Product;
import com.example.order.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public Result<List<Product>> getAllProducts() {
        return Result.success(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public Result<Product> getProductById(@PathVariable Integer id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return Result.error(404, "商品不存在");
        }
        return Result.success(product);
    }
}
