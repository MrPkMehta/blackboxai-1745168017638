package com.example.ecommerceplatform.controller;

import com.example.ecommerceplatform.model.Order;
import com.example.ecommerceplatform.model.User;
import com.example.ecommerceplatform.model.CartItem;
import com.example.ecommerceplatform.service.OrderService;
import com.example.ecommerceplatform.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(Authentication authentication, @RequestBody List<Long> productIds) {
        User user = userService.findByUsername(authentication.getName()).orElseThrow();
        // For simplicity, assume quantity 1 for each product in the order
        // In real app, you would pass quantities and validate stock accordingly
        List<CartItem> cartItems = user.getCart() != null ? user.getCart().getItems() : List.<CartItem>of();
        Order order = orderService.createOrder(user, cartItems);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrders(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName()).orElseThrow();
        List<Order> orders = orderService.getOrdersByUser(user);
        return ResponseEntity.ok(orders);
    }
}
