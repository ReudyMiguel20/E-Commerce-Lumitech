package com.lumitech.ecommerceapp.orders.repository;

import com.lumitech.ecommerceapp.orders.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
