package com.lumitech.ecommerceapp.users.exception.error;


public class UserNotACustomerException extends RuntimeException {
    public UserNotACustomerException() {
    }

    public UserNotACustomerException(String message) {
        super(message);
    }

    public UserNotACustomerException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotACustomerException(Throwable cause) {
        super(cause);
    }

    public UserNotACustomerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
