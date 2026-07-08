package com.maniket.oms.dto;
import jakarta.validation.constraints.NotBlank;

public class CategoryRequest {

    @NotBlank(message = "Category Name is required")
    private String categoryName;

    @NotBlank(message = "Description is required")
    private String description;

    public CategoryRequest() {}

    public CategoryRequest(String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}