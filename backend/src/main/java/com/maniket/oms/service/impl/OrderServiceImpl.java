package com.maniket.oms.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maniket.oms.dto.OrderRequest;
import com.maniket.oms.entity.CartItem;
import com.maniket.oms.entity.Order;
import com.maniket.oms.entity.OrderItem;
import com.maniket.oms.entity.Product;
import com.maniket.oms.entity.User;
import com.maniket.oms.repository.CartItemRepository;
import com.maniket.oms.repository.OrderRepository;
import com.maniket.oms.repository.ProductRepository;
import com.maniket.oms.repository.UserRepository;
import com.maniket.oms.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Order placeOrder(OrderRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CartItem> cartItems = cartRepository.findByUserUserId(request.getUserId());

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();

        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");

        List<OrderItem> orderItems = new ArrayList<>();

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cart : cartItems) {

            Product product = cart.getProduct();

            if (product.getStockQuantity() < cart.getQuantity()) {
                throw new RuntimeException(product.getProductName() + " Out of Stock");
            }

            product.setStockQuantity(
                    product.getStockQuantity() - cart.getQuantity());

            productRepository.save(product);

            OrderItem item = new OrderItem();

            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(cart.getQuantity());
            item.setPrice(product.getPrice());

            orderItems.add(item);

            total = total.add(
                    product.getPrice().multiply(
                            BigDecimal.valueOf(cart.getQuantity())));
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(total);

        Order savedOrder = orderRepository.save(order);

        cartRepository.deleteAll(cartItems);

        return savedOrder;
    }

    @Override
    public List<Order> getOrders(Long userId) {

        return orderRepository.findByUserUserId(userId);

    }

}