package com.app.habits.infrastructure.rest.error;

import com.app.habits.domain.exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // === Exceções de domínio (ApiException) ===
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex, HttpServletRequest req) {
        ErrorResponse body = ErrorResponseFactory.build(
                req,
                ex.getCode(),
                ex.getMessage(),
                ex.getDetail(),
                null
        );
        return ResponseEntity.status(ex.getStatus()).body(body);
    }

    // === Validação (Bean Validation @Valid) ===
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<ErrorResponse.FieldErrorItem> fields = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::toFieldItem)
                .toList();

        ErrorResponse body = ErrorResponseFactory.build(
                req,
                ErrorCode.VALIDATION_ERROR,
                "Falha de validação nos dados enviados.",
                null,
                fields
        );
        return ResponseEntity.unprocessableEntity().body(body); // 422
    }

    private ErrorResponse.FieldErrorItem toFieldItem(FieldError e) {
        return new ErrorResponse.FieldErrorItem(e.getField(), e.getDefaultMessage(), e.getRejectedValue());
    }

    // === Validação em parâmetros (ex.: @RequestParam, @PathVariable) ===
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest req) {
        List<ErrorResponse.FieldErrorItem> fields = ex.getConstraintViolations().stream()
                .map(v -> new ErrorResponse.FieldErrorItem(
                        v.getPropertyPath() != null ? v.getPropertyPath().toString() : null,
                        v.getMessage(),
                        v.getInvalidValue()
                ))
                .toList();

        ErrorResponse body = ErrorResponseFactory.build(
                req,
                ErrorCode.VALIDATION_ERROR,
                "Falha de validação nos parâmetros.",
                null,
                fields
        );
        return ResponseEntity.unprocessableEntity().body(body);
    }

    // === JSON inválido / corpo malformado ===
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleNotReadable(HttpMessageNotReadableException ex, HttpServletRequest req) {
        ErrorResponse body = ErrorResponseFactory.build(
                req,
                ErrorCode.PAYLOAD_INVALID,
                "Corpo da requisição inválido ou malformado.",
                rootMsg(ex),
                null
        );
        return ResponseEntity.badRequest().body(body);
    }

    // === Parâmetro obrigatório ausente ===
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParam(MissingServletRequestParameterException ex, HttpServletRequest req) {
        ErrorResponse body = ErrorResponseFactory.build(
                req,
                ErrorCode.BAD_REQUEST,
                "Parâmetro obrigatório ausente.",
                ex.getParameterName() + " é obrigatório.",
                null
        );
        return ResponseEntity.badRequest().body(body);
    }

    // === Métod não suportado / mídia não suportada ===
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex, HttpServletRequest req) {
        ErrorResponse body = ErrorResponseFactory.build(
                req,
                ErrorCode.METHOD_NOT_ALLOWED,
                "Método HTTP não suportado para esta rota.",
                rootMsg(ex),
                null
        );
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(body);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedMedia(HttpMediaTypeNotSupportedException ex, HttpServletRequest req) {
        ErrorResponse body = ErrorResponseFactory.build(
                req,
                ErrorCode.UNSUPPORTED_MEDIA_TYPE,
                "Content-Type não suportado.",
                rootMsg(ex),
                null
        );
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(body);
    }

    // === Segurança ===
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleForbidden(AccessDeniedException ex, HttpServletRequest req) {
        ErrorResponse body = ErrorResponseFactory.build(
                req,
                ErrorCode.FORBIDDEN,
                "Acesso negado.",
                rootMsg(ex),
                null
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    // === Banco de dados / integridade ===
    @ExceptionHandler({DataIntegrityViolationException.class, TransactionSystemException.class})
    public ResponseEntity<ErrorResponse> handleDatabase(Exception ex, HttpServletRequest req) {
        ErrorResponse body = ErrorResponseFactory.build(
                req,
                ErrorCode.DATABASE_ERROR,
                "Erro ao processar dados no banco.",
                rootMsg(ex),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    // === Fallback (última barreira) ===
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnknown(Exception ex, HttpServletRequest req) {
        ErrorResponse body = ErrorResponseFactory.build(
                req,
                ErrorCode.INTERNAL_ERROR,
                "Ocorreu um erro inesperado.",
                rootMsg(ex),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    private String rootMsg(Throwable t) {
        Throwable c = t;
        while (c.getCause() != null) c = c.getCause();
        return c.getMessage();
    }
}
