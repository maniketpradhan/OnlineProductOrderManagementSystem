package com.maniket.oms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maniket.oms.dto.CategoryRequest;
import com.maniket.oms.entity.Category;
import com.maniket.oms.repository.CategoryRepository;
import com.maniket.oms.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category addCategory(CategoryRequest request) {

        if(categoryRepository.findByCategoryName(request.getCategoryName()).isPresent()) {
            throw new RuntimeException("Category already exists");
        }

        Category category = new Category();

        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {

        return categoryRepository.findAll();

    }

}