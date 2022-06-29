package ru.itis.m_social_test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.itis.m_social_test.DTO.UserDto;
import ru.itis.m_social_test.models.User;
import ru.itis.m_social_test.services.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "api/v1/users/register")
    public ResponseEntity<String> signUp(@Valid @RequestBody User user) {
        return ResponseEntity.status(201).body(userService.signUp(user));
    }

    @GetMapping(value = "api/v1/users/info")
    public ResponseEntity<?> getInfo(@RequestHeader(name = "User-Id") Long id) {
        Optional<User> userFromDb = userService.getInfoAboutUser(id);
        if (userFromDb.isPresent())
            return ResponseEntity.ok(UserDto.from(userFromDb.get()));
        else
            return ResponseEntity.status(401).body("Пользователя с таким id не существует, пожалуйста пройдите регистрацию");
    }

    @PutMapping(value = "api/v1/users/update")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserDto userDto, @RequestHeader(name = "User-Id") Long id) {
        Optional<User> userFromDb = userService.updateUser(userDto, id);
        if (userFromDb.isPresent())
            return ResponseEntity.ok("Изменения внесены");
        else
            return ResponseEntity.status(401).body("Пользователя с таким id не существует, пожалуйста пройдите регистрацию");
    }

    @DeleteMapping(value = "api/v1/users/delete")
    public ResponseEntity<String> deleteUser(@RequestHeader(name = "User-Id") Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
