package com.lumitech.ecommerceapp.orders.service;


import com.lumitech.ecommerceapp.orders.model.dto.OrderSummary;
import com.lumitech.ecommerceapp.orders.model.entity.PurchaseOrder;
import com.lumitech.ecommerceapp.users.model.entity.User;

import java.util.List;

public interface OrderService {

    OrderSummary createOrder(User user);

    List<PurchaseOrder> findAllOrders();

    PurchaseOrder findOrderById(long id);

    void deleteOrderById(long id);

    PurchaseOrder updateOrder(PurchaseOrder purchaseOrder);

    List<PurchaseOrder> findAllOrderByUserId(User user);
}
