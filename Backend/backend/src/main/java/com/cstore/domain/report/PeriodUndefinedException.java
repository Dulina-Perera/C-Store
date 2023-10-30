package com.cstore.domain.report;

public class PeriodUndefinedException extends RuntimeException {
    public PeriodUndefinedException() {
    }

    public PeriodUndefinedException(String message) {
        super(message);
    }

    public PeriodUndefinedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PeriodUndefinedException(Throwable cause) {
        super(cause);
    }

    public PeriodUndefinedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
