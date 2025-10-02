package com.app.habits.infrastructure.persistence.spring;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class CorrelationIdFilter implements Filter {

    private static final String KEY = "traceId";
    private static final String HEADER = "X-Trace-Id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        boolean put = false;
        if (MDC.get(KEY) == null) {
            MDC.put(KEY, UUID.randomUUID().toString());
            put = true;
        }
        try {
            if (response instanceof HttpServletResponse http) {
                http.setHeader(HEADER, MDC.get(KEY));
            }
            chain.doFilter(request, response);
        } finally {
            if (put) MDC.remove(KEY);
        }
    }
}
