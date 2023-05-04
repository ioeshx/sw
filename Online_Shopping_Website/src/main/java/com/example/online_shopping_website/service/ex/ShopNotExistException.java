package com.example.online_shopping_website.service.ex;

public class ShopNotExistException extends RuntimeException{
    public ShopNotExistException() {
        super();
    }

    public ShopNotExistException(String message) {
        super(message);
    }

    public ShopNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShopNotExistException(Throwable cause) {
        super(cause);
    }

    protected ShopNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}