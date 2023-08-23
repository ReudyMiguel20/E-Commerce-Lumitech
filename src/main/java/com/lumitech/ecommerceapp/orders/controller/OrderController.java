package com.lumitech.ecommerceapp.orders.controller;

import com.lumitech.ecommerceapp.orders.model.dto.OrderSummary;
import com.lumitech.ecommerceapp.orders.service.OrderService;
import com.lumitech.ecommerceapp.users.model.entity.User;
import com.lumitech.ecommerceapp.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<OrderSummary> createOrder(Authentication auth) {
        User user = userService.findByEmail(auth.getName()).get();

        OrderSummary orderSummary = orderService.createOrder(user);

        return ResponseEntity.ok(orderSummary);
    }

    @GetMapping
    public ResponseEntity<?> getAllOrdersByUser(Authentication auth) {
        User user = userService.findByEmail(auth.getName()).get();

        return ResponseEntity.ok(orderService.findAllOrderByUserId(user));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(orderService.findAllOrders());
    }

}
