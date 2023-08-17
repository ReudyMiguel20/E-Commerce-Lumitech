package com.lumitech.ecommerceapp.orders.repository;

import com.lumitech.ecommerceapp.orders.model.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<PurchaseOrder, Long> {
    List<PurchaseOrder> findAllPurchaseOrderByUserId(Long userId);

}
