package com.example.online_shopping_website.service.ex;

public class UserIdnumDuplicatedException extends ServiceException{
    public UserIdnumDuplicatedException() {
    }

    public UserIdnumDuplicatedException(String message) {
        super(message);
    }

    public UserIdnumDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserIdnumDuplicatedException(Throwable cause) {
        super(cause);
    }

    public UserIdnumDuplicatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
