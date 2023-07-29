package com.lumitech.ecommerceapp.cart.service;

import com.lumitech.ecommerceapp.cart.model.dto.UserProductCart;
import com.lumitech.ecommerceapp.cart.model.entity.CartItem;
import com.lumitech.ecommerceapp.product.model.entity.Product;
import com.lumitech.ecommerceapp.users.model.entity.User;

import java.util.List;

public interface CartItemService {

    User saveProductToUserCart(Product productToSave, User user);

    UserProductCart userProductsOnCart(User user);

    List<CartItem> getAllCartItems();
}
