package com.maniket.oms.dto;

import jakarta.validation.constraints.NotNull;

public class OrderRequest {

    @NotNull(message="User Id is required")
    private Long userId;

    public OrderRequest() {
    }

    public OrderRequest(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}