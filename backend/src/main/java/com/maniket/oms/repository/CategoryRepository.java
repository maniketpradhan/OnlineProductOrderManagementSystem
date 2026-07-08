package com.maniket.oms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maniket.oms.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

    Optional<Category> findByCategoryName(String categoryName);

}