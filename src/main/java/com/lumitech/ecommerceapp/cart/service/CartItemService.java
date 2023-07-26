package com.lumitech.ecommerceapp.cart.service;

import com.lumitech.ecommerceapp.cart.model.dto.ProductsOnUserCart;
import com.lumitech.ecommerceapp.product.model.entity.Product;
import com.lumitech.ecommerceapp.users.model.entity.User;

public interface CartItemService {

    User saveProductToUserCart(Product productToSave, User user);

    ProductsOnUserCart userProductsOnCart(User user);
}
