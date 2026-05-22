package com.example.order.controller;

import com.example.order.common.Result;
import com.example.order.dto.CreateProductRequest;
import com.example.order.dto.PageRequest;
import com.example.order.dto.UpdateProductRequest;
import com.example.order.entity.Product;
import com.example.order.service.ProductService;
import com.example.order.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public Result<PageResult<Product>> getAllProducts(PageRequest pageRequest) {
        return Result.success(productService.getAllProducts(pageRequest));
    }

    @GetMapping("/{id}")
    public Result<Product> getProductById(@PathVariable Integer id) {
        return Result.success(productService.getProductById(id));
    }

    @PostMapping
    public Result<Product> createProduct(@Validated @RequestBody CreateProductRequest request) {
        return Result.success(productService.createProduct(request));
    }

    @PutMapping("/{id}")
    public Result<Product> updateProduct(@PathVariable Integer id, @RequestBody UpdateProductRequest request) {
        return Result.success(productService.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return Result.success();
    }
}
