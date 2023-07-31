package com.lumitech.ecommerceapp.cart.repository;

import com.lumitech.ecommerceapp.cart.model.entity.Cart;
import com.lumitech.ecommerceapp.cart.model.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findCartItemByCartIdAndProductId(long cart_id, long product_id);
}
