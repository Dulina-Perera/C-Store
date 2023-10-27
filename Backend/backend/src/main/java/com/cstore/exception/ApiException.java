package com.cstore.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Builder @AllArgsConstructor
@Getter
@Setter
public class ApiException {
    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timeStamp;
}
