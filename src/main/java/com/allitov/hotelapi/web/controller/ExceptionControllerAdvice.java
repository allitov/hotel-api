package com.allitov.hotelapi.web.controller;

import com.allitov.hotelapi.exception.IllegalDataAccessException;
import com.allitov.hotelapi.web.dto.response.ErrorResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.DateTimeException;
import java.util.stream.Collectors;

/**
 * The class that handles errors and creates a response.
 * @author allitov
 */
@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    /**
     * Creates responses for <em>not found</em> exception types.
     * @param e exception to handle.
     * @return status 404 and response body.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundHandler(Exception e) {
        logExceptionHandling(e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }

    /**
     * Creates responses for <em>conflict</em> exception types.
     * @param e exception to handle
     * @return status 409 and response body.
     */
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorResponse> entityExistsHandler(Exception e) {
        logExceptionHandling(e);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage()));
    }

    /**
     * Creates responses for <em>bad request</em> exception types.
     * @param e exception to handle.
     * @return status 400 and response body.
     */
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
            DateTimeException.class,
            IllegalArgumentException.class,
    })
    public ResponseEntity<ErrorResponse> badRequestHandler(Exception e) {
        logExceptionHandling(e);

        if (e instanceof MethodArgumentNotValidException ex) {
            BindingResult bindingResult = ex.getBindingResult();
            String errorMessage = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
        }

        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    /**
     * Creates responses for <em>forbidden</em> exception types.
     * @param e exception to handle.
     * @return status 403 and response body.
     */
    @ExceptionHandler(IllegalDataAccessException.class)
    public ResponseEntity<ErrorResponse> forbiddenHandler(Exception e) {
        logExceptionHandling(e);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(e.getMessage()));
    }

    /**
     * Creates responses for <em>method not allowed</em> exception types.
     * @param e exception to handle.
     * @return status 405 and response body.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> methodNotAllowedHandler(Exception e) {
        logExceptionHandling(e);

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ErrorResponse(e.getMessage()));
    }

    /**
     * Creates responses for <em>internal server error</em> exception types.
     * @param e exception to handle.
     * @return status 500.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> internalServerErrorHandler(Exception e) {
        log.error("Handle {} with message = '{}'. Stack trace: {}",
                e.getClass().getName(), e.getMessage(), e.getStackTrace());

        return ResponseEntity.internalServerError().build();
    }

    private void logExceptionHandling(Exception e) {
        log.info("Handle {} with message = '{}'", e.getClass().getName(), e.getMessage());
    }
}
