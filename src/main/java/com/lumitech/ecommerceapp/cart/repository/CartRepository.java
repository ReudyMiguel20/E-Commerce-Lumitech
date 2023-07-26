package com.lumitech.ecommerceapp.cart.repository;

import com.lumitech.ecommerceapp.cart.model.entity.Cart;
import com.lumitech.ecommerceapp.product.model.entity.Product;
import com.lumitech.ecommerceapp.users.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long>,
        PagingAndSortingRepository<Cart, Long> {
    Optional<Cart> findCartByUser(User user);
}
