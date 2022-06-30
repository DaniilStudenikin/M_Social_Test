package ru.itis.m_social_test.exception;

public class IncorrectPageException extends RuntimeException{
    public IncorrectPageException() {
        super("Такой страницы не существует");
    }
}
