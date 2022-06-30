package ru.itis.m_social_test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.m_social_test.DTO.MovieDto;
import ru.itis.m_social_test.services.MoviesService;


import java.util.List;

@RestController
public class MovieController {

    @Autowired
    private MoviesService moviesService;

    @GetMapping(value = "api/v1/movies")
    public ResponseEntity<List<MovieDto>> selectMovies(@RequestParam(required = false, name = "page", defaultValue = "1") Integer pageNum, @RequestHeader(name = "User-Id") Long id) {
        return ResponseEntity.ok(MovieDto.from(moviesService.selectFilms(pageNum)));
    }


    @PutMapping(value = "api/v1/movies/add")
    public ResponseEntity<String> addMovieToFavourite(@RequestHeader(name = "User-Id") Long userId, @RequestParam(name = "movie_id") Long movieId) {
        moviesService.addMovieToFavourite(userId, movieId);
        return ResponseEntity.ok("Успешно!");
    }

    @DeleteMapping(value = "api/v1/movies/delete")
    public ResponseEntity<String> deleteMovieFromFavourite(@RequestHeader(name = "User-Id") Long userId, @RequestParam(name = "movie_id") Long movieId) {
        moviesService.deleteMovieFromFavourite(userId, movieId);
        return ResponseEntity.status(204).build();
    }

    @GetMapping(value = "api/v1/movies/favourites")
    public ResponseEntity<List<MovieDto>> showFavourites(@RequestHeader(name = "User-Id") Long userId, @RequestParam(name = "loaderType") String loaderType) {
        return ResponseEntity.ok(MovieDto.from(moviesService.showNoFavourites(userId, loaderType)));

    }
}
