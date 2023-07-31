package com.lumitech.ecommerceapp.cart.exception;

public class NonCustomerCartAccessException extends RuntimeException {
    public NonCustomerCartAccessException() {
    }

    public NonCustomerCartAccessException(String message) {
        super(message);
    }

    public NonCustomerCartAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonCustomerCartAccessException(Throwable cause) {
        super(cause);
    }

    public NonCustomerCartAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
