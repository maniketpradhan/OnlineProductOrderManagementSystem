package com.maniket.oms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.maniket.oms.dto.CartRequest;
import com.maniket.oms.entity.CartItem;
import com.maniket.oms.service.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public CartItem addToCart(@Valid @RequestBody CartRequest request) {

        return cartService.addToCart(request);

    }

    @GetMapping("/{userId}")
    public List<CartItem> getCart(@PathVariable Long userId) {

        return cartService.getCart(userId);

    }

    @PutMapping("/{cartItemId}")
    public CartItem updateQuantity(@PathVariable Long cartItemId,
                                   @RequestParam Integer quantity) {

        return cartService.updateQuantity(cartItemId, quantity);

    }

    @DeleteMapping("/{cartItemId}")
    public String removeFromCart(@PathVariable Long cartItemId) {

        cartService.removeFromCart(cartItemId);

        return "Item Removed Successfully";

    }
}