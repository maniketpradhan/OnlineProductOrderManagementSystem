package com.maniket.oms.service;

import java.util.List;

import com.maniket.oms.dto.CategoryRequest;
import com.maniket.oms.entity.Category;

public interface CategoryService {

    Category addCategory(CategoryRequest request);

    List<Category> getAllCategories();

}