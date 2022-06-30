package ru.itis.m_social_test.exception;

public class IncorrectLoaderTypeException extends RuntimeException{
    public IncorrectLoaderTypeException() {
        super("Неверный loader type!");
    }
}
