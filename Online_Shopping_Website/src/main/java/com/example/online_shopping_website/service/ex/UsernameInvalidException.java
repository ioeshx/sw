package com.example.online_shopping_website.service.ex;

public class UsernameInvalidException extends RuntimeException{
    public UsernameInvalidException() {
    }

    public UsernameInvalidException(String message) {
        super(message);
    }

    public UsernameInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameInvalidException(Throwable cause) {
        super(cause);
    }

    public UsernameInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
