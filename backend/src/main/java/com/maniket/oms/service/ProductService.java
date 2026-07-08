package com.maniket.oms.service;

import java.util.List;

import com.maniket.oms.dto.ProductRequest;
import com.maniket.oms.entity.Product;

public interface ProductService {

    Product addProduct(ProductRequest request);

    List<Product> getAllProducts();

    Product getProductById(Long id);

    List<Product> getProductsByCategory(Long categoryId);

    List<Product> searchProducts(String keyword);

    Product updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);
}