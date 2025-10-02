package com.app.habits.infrastructure.rest.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    private OffsetDateTime timestamp;
    private String path;
    private String traceId;
    private ErrorCode code;
    private String message;
    private String detail;
    private List<FieldErrorItem> fieldErrors;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class FieldErrorItem {
        private String field;
        private String message;
        private Object rejectedValue;
    }
}
