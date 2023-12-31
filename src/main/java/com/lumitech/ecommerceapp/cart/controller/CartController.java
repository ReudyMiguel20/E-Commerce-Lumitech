package com.lumitech.ecommerceapp.cart.controller;

import com.lumitech.ecommerceapp.cart.model.dto.AddProductToCartDTO;
import com.lumitech.ecommerceapp.cart.model.dto.DeleteProductFromCartDTO;
import com.lumitech.ecommerceapp.cart.model.dto.UserProductCart;
import com.lumitech.ecommerceapp.cart.service.CartItemService;
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

    private final ProductService productService;
    private final UserService userService;
    private final CartItemService cartItemService;

    @GetMapping
    public ResponseEntity<UserProductCart> getUserCart(Authentication auth) {
        User user = userService.findByEmail(auth.getName()).get();
        UserProductCart userProductCart = cartItemService.userProductsOnCart(user);
        return ResponseEntity.ok(userProductCart);
    }

//    @Secured("ROLE_ADMIN") - Wondering how this works, going to keep it here for reminder purposes
    /**
     * In order to get a cart by id the user accessing the endpoint must be an 'Admin' or 'Employee'
     *
     * @param auth - Authentication object from Spring Security that contains the user's email and role (ROLE_ADMIN)
     * @param id - id of the user to get the cart from with (ROLE_CUSTOMER)
     * @return - ResponseEntity with the cart of the user with role ROLE_CUSTOMER
     */
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
        int quantity = addProductToCartDTO.getQuantity();

        // Add the product to the User cart
        user = cartItemService.saveProductToUserCart(productToAdd, quantity ,user);

        // Get the updated cart of the user
        UserProductCart userProductCart = cartItemService.userProductsOnCart(user);

        return ResponseEntity.ok(userProductCart);
    }

    @DeleteMapping
    public ResponseEntity<UserProductCart> deleteItemFromCart(Authentication auth, @Valid @RequestBody DeleteProductFromCartDTO deleteProductFromCartDTO) {
        // Get the user and the product to delete from the cart
        User user = userService.findByEmail(auth.getName()).get();
        Product productToDelete = productService.findByNameIgnoreCase(deleteProductFromCartDTO.getProductName());

        // Delete the product to the User cart and get the updated user
        user = cartItemService.deleteProductFromUserCart(productToDelete, user);

        // Get the updated cart of the user
        UserProductCart userProductCart = cartItemService.userProductsOnCart(user);

        return ResponseEntity.ok(userProductCart);
    }

    @DeleteMapping("/empty")
    public ResponseEntity<UserProductCart> deleteAllItemsFromCart(Authentication auth) {
        // Get the user to get the cart from it and delete all
        User user = userService.findByEmail(auth.getName()).get();

        user = cartItemService.deleteAllProductsFromCartAndReturnStock(user);

        // Get the updated cart of the user
        UserProductCart userProductCart = cartItemService.userProductsOnCart(user);

        return ResponseEntity.ok(userProductCart);
    }
}
