package com.cstore.domain.report.sales;

public class NoSuchQuarterException extends RuntimeException {
    public NoSuchQuarterException() {
    }

    public NoSuchQuarterException(String message) {
        super(message);
    }

    public NoSuchQuarterException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchQuarterException(Throwable cause) {
        super(cause);
    }

    public NoSuchQuarterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
