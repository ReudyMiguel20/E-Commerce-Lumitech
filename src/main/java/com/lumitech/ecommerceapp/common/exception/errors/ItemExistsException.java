package com.lumitech.ecommerceapp.common.exception.errors;

public class ItemExistsException extends RuntimeException {

    public ItemExistsException() {
    }

    public ItemExistsException(String message) {
        super(message);
    }

    public ItemExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemExistsException(Throwable cause) {
        super(cause);
    }

    public ItemExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
