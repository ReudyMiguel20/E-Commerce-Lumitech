package com.lumitech.ecommerceapp.product.exception.errors;

public class UpdateValuesSameAsExistingProductException extends RuntimeException {

    public UpdateValuesSameAsExistingProductException() {
    }

    public UpdateValuesSameAsExistingProductException(String message) {
        super(message);
    }

    public UpdateValuesSameAsExistingProductException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateValuesSameAsExistingProductException(Throwable cause) {
        super(cause);
    }

    public UpdateValuesSameAsExistingProductException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
