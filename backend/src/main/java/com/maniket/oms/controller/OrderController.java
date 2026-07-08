package com.maniket.oms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.maniket.oms.dto.OrderRequest;
import com.maniket.oms.entity.Order;
import com.maniket.oms.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order placeOrder(@Valid @RequestBody OrderRequest request) {

        return orderService.placeOrder(request);

    }

    @GetMapping("/{userId}")
    public List<Order> getOrders(@PathVariable Long userId) {

        return orderService.getOrders(userId);

    }

}