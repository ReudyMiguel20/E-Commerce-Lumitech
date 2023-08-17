package com.lumitech.ecommerceapp.orders.model.dto;

import com.lumitech.ecommerceapp.orders.model.entity.OrderItem;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderSummary {
    private String customerName;
    private List<OrderItem> orderItems;
    private double totalAmount;
}
