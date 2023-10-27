package com.cstore.exception.sparsestocks;

import com.cstore.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class SparseStocksExceptionHandler {
    @ExceptionHandler(value = SparseStocksException.class)
    public ResponseEntity<Object> handle(
        SparseStocksException e
    ) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;

        ApiException apiException = new ApiException(
            e.getMessage(),
            notFound,
            ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, notFound);
    }
}
