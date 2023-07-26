package com.lumitech.ecommerceapp.cart.service;

import com.lumitech.ecommerceapp.cart.model.entity.Cart;
import com.lumitech.ecommerceapp.product.model.entity.Product;
import com.lumitech.ecommerceapp.users.model.entity.User;

import java.util.List;

public interface CartService {

    User createCartAndAssignToUser(User user);

    Cart saveCart(Cart cart);

    Cart getCartByUser(User user);

//    List<Product> addProductToCart(Product product, User user);
}
