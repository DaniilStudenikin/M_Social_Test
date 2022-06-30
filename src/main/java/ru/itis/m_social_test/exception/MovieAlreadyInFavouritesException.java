package ru.itis.m_social_test.exception;

public class MovieAlreadyInFavouritesException extends RuntimeException{
    public MovieAlreadyInFavouritesException() {
        super("Фильм уже у вас в избранных");
    }
}
