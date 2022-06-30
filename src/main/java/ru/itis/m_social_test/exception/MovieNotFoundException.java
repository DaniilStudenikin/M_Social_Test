package ru.itis.m_social_test.exception;

public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException() {
        super("Фильм с таким id не найден!");
    }
}
