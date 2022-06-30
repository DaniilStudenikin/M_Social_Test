package ru.itis.m_social_test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.m_social_test.DTO.UserDto;
import ru.itis.m_social_test.models.User;
import ru.itis.m_social_test.services.UserService;

import javax.validation.Valid;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "api/v1/users/register")
    public ResponseEntity<String> signUp(@Valid @RequestBody User user) {
        return ResponseEntity.status(201).body(userService.signUp(user));
    }

    @GetMapping(value = "api/v1/users/info")
    public ResponseEntity<UserDto> getInfo(@RequestHeader(name = "User-Id") Long id) {
        return ResponseEntity.ok(UserDto.from(userService.getInfoAboutUser(id)));
    }

    @PutMapping(value = "api/v1/users/update")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserDto userDto, @RequestHeader(name = "User-Id") Long id) {
        userService.updateUser(userDto, id);
        return ResponseEntity.ok("Изменения внесены!");
    }

    @DeleteMapping(value = "api/v1/users/delete")
    public ResponseEntity<String> deleteUser(@RequestHeader(name = "User-Id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(204).build();
    }
}
