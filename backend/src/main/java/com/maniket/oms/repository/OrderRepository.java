package com.maniket.oms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maniket.oms.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserUserId(Long userId);

}