package com.maniket.oms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maniket.oms.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}