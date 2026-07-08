package com.maniket.oms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.maniket.oms.dto.CategoryRequest;
import com.maniket.oms.entity.Category;
import com.maniket.oms.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Category addCategory(@Valid @RequestBody CategoryRequest request) {

        return categoryService.addCategory(request);

    }

    @GetMapping
    public List<Category> getAllCategories() {

        return categoryService.getAllCategories();

    }

}