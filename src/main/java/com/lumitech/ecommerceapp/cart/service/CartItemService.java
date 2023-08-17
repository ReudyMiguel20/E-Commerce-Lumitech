package com.lumitech.ecommerceapp.cart.service;

import com.lumitech.ecommerceapp.cart.model.dto.UserProductCart;
import com.lumitech.ecommerceapp.cart.model.entity.CartItem;
import com.lumitech.ecommerceapp.product.model.entity.Product;
import com.lumitech.ecommerceapp.users.model.entity.User;

import java.util.List;

public interface CartItemService {

    User saveProductToUserCart(Product productToSave, int quantity ,User user);

    UserProductCart userProductsOnCart(User user);

    /* Create a ProductCart list to represent Products on cart, it also converts the Product to ProductCart
       inside the stream, items are still products this is just a DTO to represent them in a fashioned way */
    UserProductCart generateUserProductCart(List<CartItem> userCart);

    List<CartItem> getAllCartItems();

    /* Method that confirms that the user is a 'Customer' in order to check their cart. Any other role isn't able to check
           their cart because they are not allowed to buy products from the store */
    void validateUserIsCustomerForCart(User user);

    User deleteProductFromUserCart(Product productToDelete, User user);

    User deleteAllProductsFromCartAndReturnStock(User user);

    User deleteAllProductsFromUserCart(User user);

    double getPriceOfCartItems(User user);
}
