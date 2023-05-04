package com.example.online_shopping_website.service.ex;

public class EmailDuplicatedException extends ServiceException{
    public EmailDuplicatedException() {
    }

    public EmailDuplicatedException(String message) {
        super(message);
    }

    public EmailDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailDuplicatedException(Throwable cause) {
        super(cause);
    }

    public EmailDuplicatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
