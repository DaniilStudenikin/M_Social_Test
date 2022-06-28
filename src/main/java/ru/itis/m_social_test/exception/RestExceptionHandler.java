package ru.itis.m_social_test.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({MissingRequestHeaderException.class})
    protected ResponseEntity<String> handeRequestHeader() {
        return ResponseEntity.badRequest().body("Required request header 'User-Id' for method parameter type String is not present");
    }

}
