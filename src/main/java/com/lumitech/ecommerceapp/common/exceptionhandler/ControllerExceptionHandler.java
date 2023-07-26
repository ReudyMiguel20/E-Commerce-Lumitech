package com.lumitech.ecommerceapp.common.exceptionhandler;


import com.lumitech.ecommerceapp.auth.exception.CartNotFoundException;
import com.lumitech.ecommerceapp.product.exception.errors.*;
import com.lumitech.ecommerceapp.users.exception.error.EmailNotFoundException;
import com.lumitech.ecommerceapp.users.exception.error.UserAlreadyExistsException;
import com.lumitech.ecommerceapp.users.exception.error.UserNotACustomerException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        //Initializing the HttpServletRequest object to get the path of the request that caused the error.
        HttpServletRequest requestServlet = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String path = requestServlet.getRequestURI();

        //Creating the body of the response
        CustomErrorMessage customErrorMessage = new CustomErrorMessage(
                LocalDateTime.now(),
                status.value(),
                status.toString(),
                "The request contains invalid data, probably left some field empty or null.",
                path
        );

        return ResponseEntity.badRequest().body(customErrorMessage);
    }

    /* ************************* Product Exceptions ************************* */

    @ExceptionHandler(ProductExistsException.class)
    public static ResponseEntity<CustomErrorMessage> handleProductExists() {
    /* This is also a good way to get the path, it is more abstract and requires a WebRequest object on the PARAMETER of the METHOD,
       but I need to manipulate the path to get the result I want. Keeping it here for future reference */
//      String path = request.getDescription(false);
//      path = path.replace("uri=", "");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String path = request.getRequestURI();

        CustomErrorMessage customErrorMessage = new CustomErrorMessage(
                LocalDateTime.now(),
                400,
                "Bad Request",
                "Item already exists",
                 path
        );
        return ResponseEntity.badRequest().body(customErrorMessage);
    }

    @ExceptionHandler(ProductIsEmptyOrNullException.class)
    public static ResponseEntity<CustomErrorMessage> handleProductEmptyOrNull() {
        //Initializing the HttpServletRequest object to get the path of the request that caused the error.
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String path = request.getRequestURI();

        CustomErrorMessage customErrorMessage = new CustomErrorMessage(
                LocalDateTime.now(),
                400,
                "Bad Request",
                "Is Empty or Null",
                path
        );
        return ResponseEntity.badRequest().body(customErrorMessage);
    }

    @ExceptionHandler(ProductDoesntExistsException.class)
    public static ResponseEntity<CustomErrorMessage> handleProductDoesntExists() {
        //Initializing the HttpServletRequest object to get the path of the request that caused the error.
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String path = request.getRequestURI();

        CustomErrorMessage customErrorMessage = new CustomErrorMessage(
                LocalDateTime.now(),
                404,
                "Not Found",
                "Product with that ID or Name doesn't exist",
                path
        );
        return ResponseEntity.badRequest().body(customErrorMessage);
    }

    @ExceptionHandler(ProductsAreEqualsException.class)
    public static ResponseEntity<CustomErrorMessage> handleProductsAreEquals() {
        //Initializing the HttpServletRequest object to get the path of the request that caused the error.
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String path = request.getRequestURI();

        CustomErrorMessage customErrorMessage = new CustomErrorMessage(
                LocalDateTime.now(),
                400,
                "Bad Request",
                "Product are equals, no need to update",
                path
        );
        return ResponseEntity.badRequest().body(customErrorMessage);
    }

    @ExceptionHandler(UpdateValuesSameAsExistingProductException.class)
    public static ResponseEntity<CustomErrorMessage> handleUpdateValuesSameAsExistingProduct() {
        //Initializing the HttpServletRequest object to get the path of the request that caused the error.
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String path = request.getRequestURI();

        CustomErrorMessage customErrorMessage = new CustomErrorMessage(
                LocalDateTime.now(),
                400,
                "Bad Request",
                "Update values are the same as an already existing product",
                path
        );
        return ResponseEntity.badRequest().body(customErrorMessage);
    }

    @ExceptionHandler(ProductSortingException.class)
    public static ResponseEntity<CustomErrorMessage> handleProductSortingException() {
        //Initializing the HttpServletRequest object to get the path of the request that caused the error.
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String path = request.getRequestURI();

        CustomErrorMessage customErrorMessage = new CustomErrorMessage(
                LocalDateTime.now(),
                400,
                "Bad Request",
                "Error, make sure to sort a product by: id, name, price, brand, category",
                path
        );
        return ResponseEntity.badRequest().body(customErrorMessage);
    }

    /* ************************* User Exceptions ************************* */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public static ResponseEntity<CustomErrorMessage> handleUserAlreadyExistsException() {
        //Initializing the HttpServletRequest object to get the path of the request that caused the error.
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String path = request.getRequestURI();

        CustomErrorMessage customErrorMessage = new CustomErrorMessage(
                LocalDateTime.now(),
                409,
                "Conflict",
                "There's an user with the same email already registered in the system.",
                path
        );
        return ResponseEntity.status(409).body(customErrorMessage);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public static ResponseEntity<CustomErrorMessage> handleEmailNotFound() {
        //Initializing the HttpServletRequest object to get the path of the request that caused the error.
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String path = request.getRequestURI();

        CustomErrorMessage customErrorMessage = new CustomErrorMessage(
                LocalDateTime.now(),
                404,
                "Not Found",
                "Email not found",
                path
        );
        return ResponseEntity.status(404).body(customErrorMessage);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public static ResponseEntity<CustomErrorMessage> handleCartNotFound() {
        //Initializing the HttpServletRequest object to get the path of the request that caused the error.
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String path = request.getRequestURI();

        CustomErrorMessage customErrorMessage = new CustomErrorMessage(
                LocalDateTime.now(),
                404,
                "Not Found",
                "Cart not found",
                path
        );
        return ResponseEntity.status(404).body(customErrorMessage);
    }

    @ExceptionHandler(NotEnoughStockException.class)
    public static ResponseEntity<CustomErrorMessage> handleNotEnoughStock() {
        //Initializing the HttpServletRequest object to get the path of the request that caused the error.
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String path = request.getRequestURI();

        CustomErrorMessage customErrorMessage = new CustomErrorMessage(
                LocalDateTime.now(),
                400,
                "Bad Request",
                "Not Enough Stock for this quantity",
                path
        );
        return ResponseEntity.status(400).body(customErrorMessage);
    }

    @ExceptionHandler(UserNotACustomerException.class)
    public static ResponseEntity<CustomErrorMessage> handleUserNotACustomer() {
        //Initializing the HttpServletRequest object to get the path of the request that caused the error.
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String path = request.getRequestURI();

        CustomErrorMessage customErrorMessage = new CustomErrorMessage(
                LocalDateTime.now(),
                400,
                "Bad Request",
                "User need to be a customer to perform this action",
                path
        );
        return ResponseEntity.status(400).body(customErrorMessage);
    }
}
