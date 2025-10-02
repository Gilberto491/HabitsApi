package com.app.habits.domain.exception;

import com.app.habits.infrastructure.rest.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApiException extends RuntimeException {

    private final HttpStatus status;
    private final ErrorCode code;
    private final String detail;
}
