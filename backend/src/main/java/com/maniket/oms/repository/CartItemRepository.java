package com.maniket.oms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maniket.oms.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUserUserId(Long userId);

    Optional<CartItem> findByUserUserIdAndProductProductId(Long userId, Long productId);

}