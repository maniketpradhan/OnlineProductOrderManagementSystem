package com.maniket.oms.service;

import java.util.List;

import com.maniket.oms.dto.CartRequest;
import com.maniket.oms.entity.CartItem;

public interface CartService {

    CartItem addToCart(CartRequest request);

    List<CartItem> getCart(Long userId);

    CartItem updateQuantity(Long cartItemId, Integer quantity);

    void removeFromCart(Long cartItemId);
}