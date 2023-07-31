package com.lumitech.ecommerceapp.cart.service.impl;

import com.lumitech.ecommerceapp.cart.exception.NonCustomerCartAccessException;
import com.lumitech.ecommerceapp.cart.model.dto.ProductCart;
import com.lumitech.ecommerceapp.cart.model.dto.UserProductCart;
import com.lumitech.ecommerceapp.cart.model.entity.CartItem;
import com.lumitech.ecommerceapp.cart.repository.CartItemRepository;
import com.lumitech.ecommerceapp.cart.service.CartItemService;
import com.lumitech.ecommerceapp.product.model.entity.Product;
import com.lumitech.ecommerceapp.product.service.ProductService;
import com.lumitech.ecommerceapp.users.model.entity.Role;
import com.lumitech.ecommerceapp.users.model.entity.User;
import com.lumitech.ecommerceapp.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final ProductService productService;

    /**
     * Method that saves a CartItem object to the database, if the CartItem already exists then it only updates
     * the values, otherwise it creates a new CartItem, assign the cartItem to the user and persist it to the database
     *
     * @param cartItem - CartItem object to be saved
     * @param quantity - Quantity of the product to be saved
     */
    public void saveCartItem(CartItem cartItem, int quantity, User user) {
        // Check if the cartItem is already in the database
        CartItem temporalCartItem = cartItemExists(cartItem);

        if (temporalCartItem == null) {
            cartItemRepository.save(cartItem);
            user.getCart().getCartItems().add(cartItem);
        } else {
            cartItem = temporalCartItem;
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            // productService.updateProductStock(productToSave, 1);
        }
    }

    public CartItem cartItemExists(CartItem cartItemToCheck) {
        // Variables for checking the cart id and product id to use it to compare if it already exists
        long cartId = cartItemToCheck.getCart().getId();
        long productId = cartItemToCheck.getProduct().getId();

        // Checking if there's already a cart item with the same product and cart id
        Optional<CartItem> foundCartItem = getAllCartItems().stream()
                .filter(cartItemOnList -> cartItemOnList.getProduct().getId() == productId &&
                        cartItemOnList.getCart().getId() == cartId)
                .findAny();

        return foundCartItem.orElse(null);
    }

    /**
     * @param productToSave - the product to save to the cart
     * @param user          - the user to save the product to his cart
     * @return - the user with the product saved to his cart
     */
    @Override
    public User saveProductToUserCart(Product productToSave, User user) {
        validateUserIsCustomerForCart(user);

        CartItem cartItem = CartItem.builder()
                .product(productToSave)
                .cart(user.getCart())
                .quantity(1)
                .build();

        saveCartItem(cartItem, 1, user);

        // Update the product stock
        productService.updateProductStock(productToSave, 1);

        return userService.saveAndReturnUser(user);
    }

    /**
     * Returns the products on the user cart and the total price of the products, the products gets converted to
     * ProductCart object which is a more resumed way to represent the product to the user removing unnecessary
     * information.
     *
     * @param user - the user to get the products on his cart
     * @return - the products on the user cart and the total price of the products
     */
    @Override
    public UserProductCart userProductsOnCart(User user) {
        validateUserIsCustomerForCart(user);

        List<CartItem> userCart = user.getCart().getCartItems();

        /* Create a ProductCart list to represent Products on cart, it also converts the Product to ProductCart
           inside the stream */
        List<ProductCart> productsOnCart = userCart.stream()
                .map(cartItem -> productService.convertProductToProductCart(cartItem.getProduct(), cartItem.getQuantity()))
                .toList();

        double total = userCart.stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
                .sum();

        return UserProductCart.builder()
                .productList(productsOnCart)
                .total(total)
                .build();
    }

    @Override
    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    /* Method that confirms that the user is a 'Customer' in order to check their cart. Any other role isn't able to check
       their cart because they are not allowed to buy products from the store */
    public void validateUserIsCustomerForCart(User user) {
        if (!user.getRole().equals(Role.CUSTOMER)) {
            throw new NonCustomerCartAccessException();
        }
    }
}
