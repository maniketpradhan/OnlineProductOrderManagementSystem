package com.maniket.oms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maniket.oms.dto.CartRequest;
import com.maniket.oms.entity.CartItem;
import com.maniket.oms.entity.Product;
import com.maniket.oms.entity.User;
import com.maniket.oms.repository.CartItemRepository;
import com.maniket.oms.repository.ProductRepository;
import com.maniket.oms.repository.UserRepository;
import com.maniket.oms.service.CartService;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public CartItem addToCart(CartRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem existingItem = cartItemRepository
                .findByUserUserIdAndProductProductId(
                        request.getUserId(),
                        request.getProductId())
                .orElse(null);

        if (existingItem != null) {

            existingItem.setQuantity(
                    existingItem.getQuantity() + request.getQuantity());

            return cartItemRepository.save(existingItem);
        }

        CartItem cartItem = new CartItem();

        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(request.getQuantity());

        return cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItem> getCart(Long userId) {

        return cartItemRepository.findByUserUserId(userId);

    }

    @Override
    public CartItem updateQuantity(Long cartItemId, Integer quantity) {

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart Item not found"));

        cartItem.setQuantity(quantity);

        return cartItemRepository.save(cartItem);
    }

    @Override
    public void removeFromCart(Long cartItemId) {

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart Item not found"));

        cartItemRepository.delete(cartItem);

    }

}