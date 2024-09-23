package ru.neoflex.jd.controller;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.neoflex.jd.exception.InvalidPasswordException;

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
        log.error("Exception usernameNotFoundHandler: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorMessage> invalidPasswordHandler(InvalidPasswordException exception) {
        log.error("Exception invalidPasswordHandler: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorMessage(exception.getMessage()));
    }
}
