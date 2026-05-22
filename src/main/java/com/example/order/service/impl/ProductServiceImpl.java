package com.example.order.service.impl;

import com.example.order.dto.CreateProductRequest;
import com.example.order.dto.PageRequest;
import com.example.order.dto.UpdateProductRequest;
import com.example.order.entity.Product;
import com.example.order.exception.BusinessException;
import com.example.order.mapper.OrderMapper;
import com.example.order.mapper.ProductMapper;
import com.example.order.service.ProductService;
import com.example.order.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Product getProductById(Integer id) {
        Product product = productMapper.selectById(id);
        if (product == null) {
            throw new BusinessException("商品不存在");
        }
        return product;
    }

    @Override
    public PageResult<Product> getAllProducts(PageRequest pageRequest) {
        Long total = productMapper.selectCount();
        List<Product> list = productMapper.selectByPage(pageRequest.getOffset(), pageRequest.getPageSize());
        return PageResult.of(list, total, pageRequest.getPageNum(), pageRequest.getPageSize());
    }

    @Override
    public Product createProduct(CreateProductRequest request) {
        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        productMapper.insert(product);
        return product;
    }

    @Override
    public Product updateProduct(Integer id, UpdateProductRequest request) {
        Product product = productMapper.selectById(id);
        if (product == null) {
            throw new BusinessException("商品不存在");
        }
        if (request.getProductName() != null) {
            product.setProductName(request.getProductName());
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        if (request.getStock() != null) {
            product.setStock(request.getStock());
        }
        productMapper.updateById(product);
        return productMapper.selectById(id);
    }

    @Override
    public void deleteProduct(Integer id) {
        Product product = productMapper.selectById(id);
        if (product == null) {
            throw new BusinessException("商品不存在");
        }
        Long orderCount = orderMapper.selectCountByProductId(id);
        if (orderCount > 0) {
            throw new BusinessException("该商品存在关联订单，无法删除");
        }
        productMapper.deleteById(id);
    }
}
