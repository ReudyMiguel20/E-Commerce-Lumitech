package com.lumitech.ecommerceapp.product.exception.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Product are equals, no need to update")
public class ProductsAreEqualsException extends RuntimeException{

    public ProductsAreEqualsException() {
    }

    public ProductsAreEqualsException(String message) {
        super(message);
    }

    public ProductsAreEqualsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductsAreEqualsException(Throwable cause) {
        super(cause);
    }

    public ProductsAreEqualsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
