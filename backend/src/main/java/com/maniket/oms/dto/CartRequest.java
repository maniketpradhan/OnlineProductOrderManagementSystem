package com.maniket.oms.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CartRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long productId;

    @NotNull
    @Positive(message="Quantity should be greater than zero")
    private Integer quantity;

    public CartRequest() {
    }

    public CartRequest(Long userId, Long productId, Integer quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}