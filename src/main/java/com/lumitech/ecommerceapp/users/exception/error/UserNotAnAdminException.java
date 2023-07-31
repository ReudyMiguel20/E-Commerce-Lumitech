package com.lumitech.ecommerceapp.users.exception.error;

public class UserNotAnAdminException extends RuntimeException {
    public UserNotAnAdminException() {
    }

    public UserNotAnAdminException(String message) {
        super(message);
    }

    public UserNotAnAdminException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotAnAdminException(Throwable cause) {
        super(cause);
    }

    public UserNotAnAdminException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
