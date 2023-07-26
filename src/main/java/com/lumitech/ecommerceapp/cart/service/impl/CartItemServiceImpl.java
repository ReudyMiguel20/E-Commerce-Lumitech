package com.lumitech.ecommerceapp.cart.service.impl;

import com.lumitech.ecommerceapp.cart.model.dto.ProductsOnUserCart;
import com.lumitech.ecommerceapp.cart.model.entity.Cart;
import com.lumitech.ecommerceapp.cart.model.entity.CartItem;
import com.lumitech.ecommerceapp.cart.repository.CartItemRepository;
import com.lumitech.ecommerceapp.cart.service.CartItemService;
import com.lumitech.ecommerceapp.product.model.entity.Product;
import com.lumitech.ecommerceapp.product.service.ProductService;
import com.lumitech.ecommerceapp.users.model.entity.User;
import com.lumitech.ecommerceapp.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final ProductService productService;


    /**
     *
     *
     * @param productToSave - the product to save to the cart
     * @param user - the user to save the product to his cart
     * @return - the user with the product saved to his cart
     */
    @Override
    public User saveProductToUserCart(Product productToSave, User user) {
        userService.isUserCostumer(user);

        CartItem cartItem = CartItem.builder()
                .product(productToSave)
                .cart(user.getCart())
                .quantity(1)
                .build();

        cartItemRepository.save(cartItem);

        Cart cart = user.getCart();

        cart.getCartItems().add(cartItem);
        productService.updateProductStock(productToSave, 1);

        return userService.saveAndReturnUser(user);
    }

    /**
     * Returns the products on the user cart and the total price of the products
     * @param user - the user to get the products on his cart
     * @return - the products on the user cart and the total price of the products
     */
    @Override
    public ProductsOnUserCart userProductsOnCart (User user) {
        userService.isUserCostumer(user);

        List<CartItem> userCart =  user.getCart().getCartItems();

        List<Product> productsOnCart = userCart.stream()
                .map(cartItem -> cartItem.getProduct())
                .toList();

        double total = userCart.stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getPrice())
                .sum();

        return ProductsOnUserCart.builder()
                .productList(productsOnCart)
                .total(total)
                .build();
    }
}
