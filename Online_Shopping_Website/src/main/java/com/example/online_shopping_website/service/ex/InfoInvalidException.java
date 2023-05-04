package com.example.online_shopping_website.service.ex;

public class InfoInvalidException extends RuntimeException{
    public InfoInvalidException() {
    }

    public InfoInvalidException(String message) {
        super(message);
    }

    public InfoInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public InfoInvalidException(Throwable cause) {
        super(cause);
    }

    public InfoInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
