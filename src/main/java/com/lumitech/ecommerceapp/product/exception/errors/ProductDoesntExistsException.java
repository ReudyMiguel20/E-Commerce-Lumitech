package com.lumitech.ecommerceapp.product.exception.errors;

public class ProductDoesntExistsException extends RuntimeException {

    public ProductDoesntExistsException() {
    }

    public ProductDoesntExistsException(String message) {
        super(message);
    }

    public ProductDoesntExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductDoesntExistsException(Throwable cause) {
        super(cause);
    }

    public ProductDoesntExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
