package ru.itis.m_social_test.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Пользователя с таким id не существует, пожалуйста пройдите регистрацию!");
    }
}
