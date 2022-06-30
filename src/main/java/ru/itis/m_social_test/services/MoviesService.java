package ru.itis.m_social_test.services;

import ru.itis.m_social_test.models.Movie;

import java.util.List;

public interface MoviesService {
    List<Movie> selectFilms(Integer offset);

    void addMovieToFavourite(Long userId, Long movieId);

    void deleteMovieFromFavourite(Long userId, Long movieId);

    List<Movie> showNoFavourites(Long userId, String loaderType);
}

