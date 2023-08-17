package com.lumitech.ecommerceapp.orders.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lumitech.ecommerceapp.cart.model.entity.CartItem;
import com.lumitech.ecommerceapp.product.model.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "orderitem")
@Builder
@NotNull
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
}
