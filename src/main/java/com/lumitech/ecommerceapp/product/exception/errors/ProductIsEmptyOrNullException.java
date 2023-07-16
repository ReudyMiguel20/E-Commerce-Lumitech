package com.lumitech.ecommerceapp.product.exception.errors;

public class ProductIsEmptyOrNullException extends RuntimeException {

    public ProductIsEmptyOrNullException() {
    }

    public ProductIsEmptyOrNullException(String message) {
        super(message);
    }

    public ProductIsEmptyOrNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductIsEmptyOrNullException(Throwable cause) {
        super(cause);
    }

    public ProductIsEmptyOrNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
