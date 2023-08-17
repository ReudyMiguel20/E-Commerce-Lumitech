package com.lumitech.ecommerceapp.orders.service.impl;

import com.lumitech.ecommerceapp.cart.service.CartItemService;
import com.lumitech.ecommerceapp.orders.model.dto.OrderSummary;
import com.lumitech.ecommerceapp.orders.model.entity.PurchaseOrder;
import com.lumitech.ecommerceapp.orders.model.entity.OrderItem;
import com.lumitech.ecommerceapp.orders.repository.OrderRepository;
import com.lumitech.ecommerceapp.orders.service.OrderItemService;
import com.lumitech.ecommerceapp.orders.service.OrderService;
import com.lumitech.ecommerceapp.users.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartItemService cartItemService;
    private final OrderItemService orderItemService;

    @Override
    public OrderSummary createOrder(User user) {
        //create order
        PurchaseOrder purchaseOrder = PurchaseOrder.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .shippingAddress(user.getAddress())
                .total(cartItemService.getPriceOfCartItems(user))
                .build();

        orderRepository.save(purchaseOrder);

        //create, add and persist order items
        List<OrderItem> orderItems = new ArrayList<>();
        user.getCart().getCartItems().forEach(cartItem -> {
            OrderItem orderItem = OrderItem.builder()
                    .purchaseOrder(purchaseOrder)
                    .product(cartItem.getProduct())
                    .quantity(cartItem.getQuantity())
                    .build();

            orderItems.add(orderItem);

            orderItemService.saveOrderItem(orderItem);
        });
        purchaseOrder.setOrderItems(orderItems);

        //save order
        orderRepository.save(purchaseOrder);

        //delete cart items
        cartItemService.deleteAllProductsFromUserCart(user);

        //creating and returning order summary
        return OrderSummary.builder()
                .customerName(user.getFirstName() + " " + user.getLastName())
                .orderItems(orderItems)
                .totalAmount(purchaseOrder.getTotal())
                .build();
    }

    @Override
    public List<PurchaseOrder> findAllOrders() {
        return orderRepository.findAll();
    }

    //Can return an exception here instead of null
    @Override
    public PurchaseOrder findOrderById(long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteOrderById(long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public PurchaseOrder updateOrder(PurchaseOrder purchaseOrder) {
        return orderRepository.save(purchaseOrder);
    }

    @Override
    public List<PurchaseOrder> findAllOrderByUserId(User user) {
        return orderRepository.findAllPurchaseOrderByUserId(user.getId());
    }



}
