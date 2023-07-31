package com.lumitech.ecommerceapp.cart.controller;

import com.lumitech.ecommerceapp.cart.model.dto.AddProductToCartDTO;
import com.lumitech.ecommerceapp.cart.model.dto.UserProductCart;
import com.lumitech.ecommerceapp.cart.service.CartItemService;
import com.lumitech.ecommerceapp.cart.service.CartService;
import com.lumitech.ecommerceapp.product.model.entity.Product;
import com.lumitech.ecommerceapp.product.service.ProductService;
import com.lumitech.ecommerceapp.users.model.entity.User;
import com.lumitech.ecommerceapp.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;
    private final CartItemService cartItemService;

    @GetMapping
    public ResponseEntity<UserProductCart> getUserCart(Authentication auth) {
        User user = userService.findByEmail(auth.getName()).get();
        UserProductCart userProductCart = cartItemService.userProductsOnCart(user);
        return ResponseEntity.ok(userProductCart);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProductCart> getUserCartById(Authentication auth, @PathVariable int id) {
        userService.isUserAdmin(userService.findByEmail(auth.getName()).get());

        User customer = userService.findById(id).get();
        UserProductCart userProductCart = cartItemService.userProductsOnCart(customer);
        return ResponseEntity.ok(userProductCart);
    }

    /**
     * @param auth                - Authentication object from Spring Security that contains the user's email
     * @param addProductToCartDTO - DTO that contains the product name to be added to the cart
     * @return - ResponseEntity with the updated cart of the user
     */
    @PostMapping
    public ResponseEntity<UserProductCart> addItemToCart(Authentication auth, @Valid @RequestBody AddProductToCartDTO addProductToCartDTO) {
        // Get the user and the product to add to the cart
        User user = userService.findByEmail(auth.getName()).get();
        Product productToAdd = productService.findByNameIgnoreCase(addProductToCartDTO.getProductName());

        // Add the product to the User cart
        User testUser = cartItemService.saveProductToUserCart(productToAdd, user);

        // Get the updated cart of the user
        UserProductCart userProductCart = cartItemService.userProductsOnCart(testUser);

        return ResponseEntity.ok(userProductCart);
    }
}
