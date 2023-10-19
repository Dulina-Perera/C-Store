package com.cstore.exception;

public class NoSuchOrderException extends RuntimeException {
    public NoSuchOrderException() {
        super();
    }

    public NoSuchOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NoSuchOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchOrderException(String message) {
        super(message);
    }

    public NoSuchOrderException(Throwable cause) {
        super(cause);
    }
}
