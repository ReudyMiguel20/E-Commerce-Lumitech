package com.lumitech.ecommerceapp.product.exception.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ProductDoesntExists extends RuntimeException {

    public ProductDoesntExists() {
    }

    public ProductDoesntExists(String message) {
        super(message);
    }

    public ProductDoesntExists(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductDoesntExists(Throwable cause) {
        super(cause);
    }

    public ProductDoesntExists(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
