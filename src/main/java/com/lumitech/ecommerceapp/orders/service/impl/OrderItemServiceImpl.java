package com.lumitech.ecommerceapp.orders.service.impl;

import com.lumitech.ecommerceapp.orders.model.entity.OrderItem;
import com.lumitech.ecommerceapp.orders.repository.OrderItemRepository;
import com.lumitech.ecommerceapp.orders.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }



}
