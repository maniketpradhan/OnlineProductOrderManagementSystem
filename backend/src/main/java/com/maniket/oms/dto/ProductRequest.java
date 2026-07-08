package com.maniket.oms.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductRequest {

    @NotBlank(message="Product Name is required")
    private String productName;

    @NotBlank(message="Description is required")
    private String description;

    @NotNull(message="Price is required")
    @Positive(message="Price must be greater than zero")
    private BigDecimal price;

    @NotNull(message="Stock Quantity is required")
    @Positive(message="Stock must be greater than zero")
    private Integer stockQuantity;

    @NotBlank(message="Image URL is required")
    private String imageUrl;

    @NotNull(message="Category is required")
    private Long categoryId;

    public ProductRequest() {
    }

    public ProductRequest(String productName, String description,
                          BigDecimal price, Integer stockQuantity,
                          String imageUrl, Long categoryId) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}