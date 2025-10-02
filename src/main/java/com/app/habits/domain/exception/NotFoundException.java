package com.app.habits.domain.exception;

import com.app.habits.infrastructure.rest.error.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, message);
    }
}
