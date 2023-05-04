package com.example.online_shopping_website.service.ex;

public class idDuplicateException extends ServiceException{
    public idDuplicateException() {
        super();
    }

    public idDuplicateException(String message) {
        super(message);
    }

    public idDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public idDuplicateException(Throwable cause) {
        super(cause);
    }

    protected idDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
