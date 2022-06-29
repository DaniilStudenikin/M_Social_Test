package ru.itis.m_social_test.services;

import ru.itis.m_social_test.models.Movie;

import java.util.List;

public interface MoviesService {
    List<Movie> selectFilms(Integer offset);

    String addMovieToFavourite(Long userId, Long movieId);

    String deleteMovieFromFavourite(Long userId, Long movieId);

    List<Movie> showNoFavouritesSQL(Long userId);

    List<Movie> showNoFavouritesInMemory(Long userId);
}
