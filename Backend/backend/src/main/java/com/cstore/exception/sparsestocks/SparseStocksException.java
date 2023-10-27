package com.cstore.exception.sparsestocks;

public class SparseStocksException extends RuntimeException {

    public SparseStocksException() {
        super();
    }

    public SparseStocksException(
        String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SparseStocksException(
        String message,
        Throwable cause
    ) {
        super(message, cause);
    }

    public SparseStocksException(String message) {
        super(message);
    }

    public SparseStocksException(Throwable cause) {
        super(cause);
    }
}
