package com.example.online_shopping_website.service.ex;

public class ShopnameDuplicateException extends RuntimeException{
    public ShopnameDuplicateException() {
        super();
    }

    public ShopnameDuplicateException(String message) {
        super(message);
    }

    public ShopnameDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShopnameDuplicateException(Throwable cause) {
        super(cause);
    }

    protected ShopnameDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
