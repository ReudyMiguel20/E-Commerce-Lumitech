package com.lumitech.ecommerceapp.product.exception.errors;

public class ProductSortingException extends RuntimeException {

    public ProductSortingException() {
    }

    public ProductSortingException(String message) {
        super(message);
    }

    public ProductSortingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductSortingException(Throwable cause) {
        super(cause);
    }

    public ProductSortingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
