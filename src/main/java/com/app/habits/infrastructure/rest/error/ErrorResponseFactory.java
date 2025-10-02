package com.app.habits.infrastructure.rest.error;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;

import java.time.OffsetDateTime;
import java.util.List;

class ErrorResponseFactory {

    static ErrorResponse build(
            HttpServletRequest request,
            ErrorCode code,
            String message,
            String detail,
            List<ErrorResponse.FieldErrorItem> fieldErrors
    ) {
        String traceId = firstNonNull(
                MDC.get("traceId"),
                MDC.get("trace_id"),
                MDC.get("X-B3-TraceId"),
                null
        );
        return new ErrorResponse(
                OffsetDateTime.now(),
                request.getRequestURI(),
                traceId,
                code,
                message,
                detail,
                fieldErrors
        );
    }

    private static String firstNonNull(String... values) {
        for (String v : values) if (v != null && !v.isBlank()) return v;
        return null;
    }
}
