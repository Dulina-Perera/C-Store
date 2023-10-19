package com.cstore.exception;

public class NoSuchWarehouseException extends RuntimeException {
    public NoSuchWarehouseException() {
        super();
    }

    public NoSuchWarehouseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NoSuchWarehouseException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchWarehouseException(String message) {
        super(message);
    }

    public NoSuchWarehouseException(Throwable cause) {
        super(cause);
    }
}
