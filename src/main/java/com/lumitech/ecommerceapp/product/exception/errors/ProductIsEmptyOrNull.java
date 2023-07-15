package com.lumitech.ecommerceapp.product.exception.errors;

public class ProductIsEmptyOrNull extends RuntimeException {

    public ProductIsEmptyOrNull() {
    }

    public ProductIsEmptyOrNull(String message) {
        super(message);
    }

    public ProductIsEmptyOrNull(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductIsEmptyOrNull(Throwable cause) {
        super(cause);
    }

    public ProductIsEmptyOrNull(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
