package com.maniket.oms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maniket.oms.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryCategoryId(Long categoryId);

    List<Product> findByProductNameContainingIgnoreCase(String productName);

}