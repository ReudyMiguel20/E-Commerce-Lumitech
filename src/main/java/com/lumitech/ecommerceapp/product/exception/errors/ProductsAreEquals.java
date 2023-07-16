package com.lumitech.ecommerceapp.product.exception.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Product are equals, no need to update")
public class ProductsAreEquals extends RuntimeException{

    public ProductsAreEquals() {
    }

    public ProductsAreEquals(String message) {
        super(message);
    }

    public ProductsAreEquals(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductsAreEquals(Throwable cause) {
        super(cause);
    }

    public ProductsAreEquals(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
