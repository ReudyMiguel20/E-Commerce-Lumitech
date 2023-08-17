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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final ProductService productService;


//    public CartItem updateCartItemAndReturn(User user)

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
    public User saveProductToUserCart(Product productToSave, int quantity, User user) {
        validateUserIsCustomerForCart(user);

        CartItem cartItem = CartItem.builder()
                .product(productToSave)
                .cart(user.getCart())
                .quantity(quantity)
                .build();

        // Update the product stock
        productService.reduceProductStock(productToSave, quantity);

        saveCartItem(cartItem, quantity, user);

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
       inside the stream, items are still products this is just a DTO to represent them in a fashioned way */
        return generateUserProductCart(userCart);
    }

    /* Create a ProductCart list to represent Products on cart, it also converts the Product to ProductCart
       inside the stream, items are still products this is just a DTO to represent them in a fashioned way */
    @Override
    public UserProductCart generateUserProductCart(List<CartItem> userCart) {
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
    @Override
    public void validateUserIsCustomerForCart(User user) {
        if (!user.getRole().equals(Role.CUSTOMER)) {
            throw new NonCustomerCartAccessException();
        }
    }

    /**
     * Deletes a product from the user cart and returns the user with the product deleted from his cart
     *
     * @param productToDelete - the product to delete from the user cart
     * @param user            - the user to delete the product from his cart
     * @return - the user with the product deleted from his cart
     */
    @Override
    public User deleteProductFromUserCart(Product productToDelete, User user) {
        validateUserIsCustomerForCart(user);

        // Assigning the user cart to a new variable for easier access to the cart items list
        List<CartItem> userCart = user.getCart().getCartItems();

        // Finding the cart item to delete from the database and also getting the product and quantity to restore the stock back
        CartItem cartItemToDelete = cartItemRepository.findCartItemByCartIdAndProductId(user.getCart().getId(), productToDelete.getId()).get();
        int stockQuantityToRestoreBack = cartItemToDelete.getQuantity();
        Product productToRestoreStock = cartItemToDelete.getProduct();

        // Removing the product from the user cart and deleting the cart item from the database
        userCart.removeIf(product -> product.getProduct().getName().equals(productToDelete.getName()));
        cartItemRepository.delete(cartItemToDelete);

        //Restoring the stock from the product deleted from the cart back to the product list
        productService.restoreProductStock(productToRestoreStock, stockQuantityToRestoreBack);

        return userService.saveAndReturnUser(user);
    }

    @Override
    public User deleteAllProductsFromCartAndReturnStock(User user) {
        validateUserIsCustomerForCart(user);

        // Get a copy of the user's cart items to avoid concurrent modification
        List<CartItem> userCart = new ArrayList<>(user.getCart().getCartItems());

        for (CartItem productToDelete : userCart) {
            // Retrieve the cart item from the database
            CartItem cartItemToDelete = cartItemRepository.findCartItemByCartIdAndProductId(user.getCart().getId(), productToDelete.getProduct().getId()).orElse(null);

            if (cartItemToDelete != null) {
                // Update the stock
                Product productToRestoreStock = cartItemToDelete.getProduct();
                int stockQuantityToRestoreBack = cartItemToDelete.getQuantity();
                productService.restoreProductStock(productToRestoreStock, stockQuantityToRestoreBack);

                // Remove the cart item from the user's cart and delete it from the database
                user.getCart().getCartItems().remove(cartItemToDelete);
                cartItemRepository.delete(cartItemToDelete);
            }
        }

        // Clear the user's cart
        user.getCart().getCartItems().clear();

        // Save the updated user
        return userService.saveAndReturnUser(user);
    }

    //Convert all the cart items to an order
    @Override
    public User deleteAllProductsFromUserCart(User user) {
        validateUserIsCustomerForCart(user);

        // Get a copy of the user's cart items to avoid concurrent modification
        List<CartItem> userCart = new ArrayList<>(user.getCart().getCartItems());


        for (CartItem productToDelete : userCart) {
            // Retrieve the cart item from the database
            CartItem cartItemToDelete = cartItemRepository.findCartItemByCartIdAndProductId(user.getCart().getId(), productToDelete.getProduct().getId()).orElse(null);

            if (cartItemToDelete != null) {
                // Remove the cart item from the user's cart and delete it from the database
                user.getCart().getCartItems().remove(cartItemToDelete);
                cartItemRepository.delete(cartItemToDelete);
            }
        }

        // Clear the user's cart
        user.getCart().getCartItems().clear();

        // Save the updated user
        return userService.saveAndReturnUser(user);
    }

    @Override
    public double getPriceOfCartItems(User user) {
        List<CartItem> cartItems = user.getCart().getCartItems();

        return cartItems.stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
                .sum();
    }

}
