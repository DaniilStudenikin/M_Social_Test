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
        return ResponseEntity.badRequest().body("В вашем запросе отсутствует заголовок User-Id");
    }

    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<String> handleIAE() {
        return ResponseEntity.status(500).body("Неверный User-Id");
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<String> handeException() {
        return ResponseEntity.status(500).body("{\"error\": \"INTERNAL_ERROR\"}");
    }
}
