package com.maniket.oms.service;

import java.util.List;

import com.maniket.oms.dto.OrderRequest;
import com.maniket.oms.entity.Order;

public interface OrderService {

    Order placeOrder(OrderRequest request);

    List<Order> getOrders(Long userId);

}