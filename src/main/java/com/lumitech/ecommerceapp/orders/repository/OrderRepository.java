package com.lumitech.ecommerceapp.orders.repository;

import com.lumitech.ecommerceapp.orders.model.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<PurchaseOrder, Long> {
    List<PurchaseOrder> findAllPurchaseOrderByUserId(Long userId);

}
