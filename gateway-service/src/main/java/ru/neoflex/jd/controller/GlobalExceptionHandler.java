package ru.neoflex.jd.controller;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorMessage> feignExceptionHandler(FeignException exception) {
        log.error("Exception feignExceptionHandler: {}", exception.getMessage());
        return ResponseEntity.status(exception.status()).body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorMessage> missingRequestHeaderHandler(MissingRequestHeaderException exception) {
        log.error("Exception missingRequestHeaderHandler: Отсутствует header '" + exception.getHeaderName() + "'");
        return ResponseEntity.status(exception.getStatusCode()).body(new ErrorMessage("Отсутствует header " +
                exception.getHeaderName() + "'"));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> methodArgumentTypeMismatchHandler(MethodArgumentTypeMismatchException exception) {
        log.error("Exception methodArgumentTypeMismatchHandler: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage("Ошибка при конвертации параметра:"
                        + exception.getParameter().getParameterName() + ", значение: " + exception.getValue()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> httpMessageNotReadableHandler(HttpMessageNotReadableException exception) {
        log.error("Exception httpMessageNotReadableHandler: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("Отсутствует требуемое тело запроса"));
    }
}
