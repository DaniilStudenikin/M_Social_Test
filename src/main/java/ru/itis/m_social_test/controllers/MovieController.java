package ru.itis.m_social_test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.m_social_test.DTO.MovieDto;
import ru.itis.m_social_test.services.MoviesService;


import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class MovieController {

    @Autowired
    private MoviesService moviesService;

    @GetMapping(value = "api/v1/movies")
    public ResponseEntity<?> selectMovies(@RequestParam(required = false, name = "page", defaultValue = "1") String page, @RequestHeader(name = "User-Id") Long id) {
        try {
            int page_int = Integer.parseInt(page);
            if (page_int <= 0) {
                return ResponseEntity.status(404).body("Такой страницы не существует");
            } else
                return ResponseEntity.ok(MovieDto.from(moviesService.selectFilms(page_int)));
        } catch (NumberFormatException e) {
            return ResponseEntity.status(404).body("Такой страницы не существует");
        }
    }

    @PutMapping(value = "api/v1/movies/add")
    public ResponseEntity<?> addMovieToFavourite(@RequestHeader(name = "User-Id") Long userId, @RequestParam(name = "movie_id") Long movieId) {
        return ResponseEntity.ok(moviesService.addMovieToFavourite(userId, movieId));
    }

    @DeleteMapping(value = "api/v1/movies/delete")
    public ResponseEntity<String> deleteMovieFromFavourite(@RequestHeader(name = "User-Id") Long userId, @RequestParam(name = "movie_id") Long movieId) {
        return ResponseEntity.status(204).body(moviesService.deleteMovieFromFavourite(userId, movieId));
    }

    @GetMapping(value = "api/v1/movies/favourites")
    public ResponseEntity<List<MovieDto>> showFavourites(@RequestHeader(name = "User-Id") Long userId, @RequestParam(name = "loaderType") String loaderType) {
        if (loaderType.equals("sql")) {
            return ResponseEntity.ok(MovieDto.from(moviesService.showNoFavouritesSQL(userId)));
        } else if (loaderType.equals("inMemory")) {
            return ResponseEntity.ok(MovieDto.from(moviesService.showNoFavouritesInMemory(userId)));
        } else
            return ResponseEntity.badRequest().build();
    }
}
