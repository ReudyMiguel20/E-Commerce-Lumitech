package com.lumitech.ecommerceapp.cart.service.impl;

import com.lumitech.ecommerceapp.auth.exception.CartNotFoundException;
import com.lumitech.ecommerceapp.cart.model.entity.Cart;
import com.lumitech.ecommerceapp.cart.repository.CartRepository;
import com.lumitech.ecommerceapp.cart.service.CartService;
import com.lumitech.ecommerceapp.users.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    /**
     * Creates a cart for the user and assigns it to the user, persist the cart to the database then returns the user
     *
     * @param user: the user to create the cart for and assign it to him
     * @return the user with the cart assigned to him
     */
    @Override
    public User createCartAndAssignToUser(User user) {
        Cart cart = Cart.builder()
                .user(user)
                .cartItems(new ArrayList<>())
                .build();

        cartRepository.save(cart);
        user.setCart(cart);

        return user;
    }

    @Override
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

//    @Override
//    public void addCartItemToCart(Product product, User user) {
//        CartItem cartItem = CartItem.builder()
//    }

    @Override
    public Cart getCartByUser(User user) {
        return cartRepository.findCartByUser(user)
                .orElseThrow(CartNotFoundException::new);
    }

//    @Override
//    public List<Product> addProductToCart(Product product, User user) {
//        Cart userCart = getCartByUser(user);
//        userCart.getProductCart().add(product);
//
//        userCart = saveCart(userCart);
//
//        return userCart.getProductCart();
//    }



}
