package com.example.online_shopping_website.service.ex;

public class GoodnameDuplicateException extends ServiceException{
    public GoodnameDuplicateException() {
        super();
    }

    public GoodnameDuplicateException(String message) {
        super(message);
    }

    public GoodnameDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoodnameDuplicateException(Throwable cause) {
        super(cause);
    }

    protected GoodnameDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
