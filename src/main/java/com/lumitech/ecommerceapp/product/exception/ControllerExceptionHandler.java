package com.lumitech.ecommerceapp.product.exception;


import com.lumitech.ecommerceapp.product.exception.errors.ProductExistsException;
import com.lumitech.ecommerceapp.product.exception.errors.ProductIsEmptyOrNull;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {



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

    @ExceptionHandler(ProductIsEmptyOrNull.class)
    public static ResponseEntity<CustomErrorMessage> handleProductEmptyOrNull() {
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
}
