package ru.neoflex.jd.controller;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorMessage> feignExceptionHandler(FeignException exception) {
        log.error("Exception feignExceptionHandler: {}", exception.getMessage());
        return ResponseEntity.status(exception.status()).body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorMessage> usernameNotFoundHandler(UsernameNotFoundException exception) {
        log.error("Exception usernameNotFoundHandler: {}", exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(exception.getMessage()));
    }
}
