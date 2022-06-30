package ru.itis.m_social_test.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<String> userAlreadyExists() {
        return ResponseEntity.ok("Пользователь с такими данными уже существует.");
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<String> userNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(MovieNotFoundException.class)
    protected ResponseEntity<String> movieNotFound(MovieNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(IncorrectPageException.class)
    protected ResponseEntity<String> incorrectPage(IncorrectPageException ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler(MovieAlreadyInFavouritesException.class)
    protected ResponseEntity<String> alreadyInFavourites(MovieAlreadyInFavouritesException ex) {
        return ResponseEntity.ok(ex.getMessage());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    protected ResponseEntity<String> handeRequestHeader() {
        return ResponseEntity.badRequest().body("В вашем запросе отсутствует заголовок User-Id");
    }

    @ExceptionHandler(IncorrectLoaderTypeException.class)
    protected ResponseEntity<String> incorrectLoaderType(IncorrectLoaderTypeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> handleAllExceptions() {
        return ResponseEntity.internalServerError().body("\"error\":\"INTERNAL_ERROR\"");
    }
}
