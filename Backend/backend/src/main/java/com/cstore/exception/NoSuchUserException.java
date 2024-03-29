package com.cstore.exception;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException() {
        super();
    }

    public NoSuchUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NoSuchUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchUserException(String message) {
        super(message);
    }

    public NoSuchUserException(Throwable cause) {
        super(cause);
    }
}
