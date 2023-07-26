package com.lumitech.ecommerceapp.cart.model.entity;

import com.lumitech.ecommerceapp.product.model.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cartitem")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "cart_id")
    @ManyToOne
    private Cart cart;

    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;

    private int quantity;

}
