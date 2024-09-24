package ru.neoflex.jd.controller;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> illegalArgumentHandler(IllegalArgumentException exception) {
        log.error("Exception illegalArgumentHandler: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorMessage> noSuchElementHandler(NoSuchElementException exception) {
        log.error("Exception noSuchElementHandler: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> httpMessageNotReadableHandler(HttpMessageNotReadableException exception) {
        log.error("Exception httpMessageNotReadableHandler: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationHandler(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder errorMessage = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            errorMessage.append(fieldError.getDefaultMessage()).append(".");
        }
        log.error("Exception handleValidationHandler: {}", errorMessage);
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(new ErrorMessage(errorMessage.toString()));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorMessage> feignExceptionHandler(FeignException exception) {
        log.error("Exception feignExceptionHandler: {}", exception.getMessage());
        return ResponseEntity.status(exception.status()).body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> unexpectedException(Exception exception) {
        log.error("Exception unexpectedException: {}", exception.getMessage());
        return ResponseEntity
                .status(520)
                .body(new ErrorMessage(exception.getMessage()));
    }
}
