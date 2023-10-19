package com.cstore.exception;

public class NoSuchCategoryException extends RuntimeException {
    public NoSuchCategoryException() {
        super();
    }

    public NoSuchCategoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NoSuchCategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchCategoryException(String message) {
        super(message);
    }

    public NoSuchCategoryException(Throwable cause) {
        super(cause);
    }
}
