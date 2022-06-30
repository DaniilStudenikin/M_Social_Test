package ru.itis.m_social_test.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.itis.m_social_test.exception.IncorrectLoaderTypeException;
import ru.itis.m_social_test.exception.MovieAlreadyInFavouritesException;
import ru.itis.m_social_test.exception.MovieNotFoundException;
import ru.itis.m_social_test.exception.UserNotFoundException;
import ru.itis.m_social_test.models.Movie;
import ru.itis.m_social_test.models.User;
import ru.itis.m_social_test.repositories.MoviesRepository;
import ru.itis.m_social_test.repositories.UsersRepository;
import ru.itis.m_social_test.services.MoviesService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MoviesServiceImpl implements MoviesService {
    @Autowired
    private MoviesRepository moviesRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<Movie> selectFilms(Integer offset) {
        offset -= 1;
        return moviesRepository.findAll(PageRequest.of(offset, 15)).getContent();
    }

    @Override
    public void addMovieToFavourite(Long userId, Long movieId) {
        User userFromDb = usersRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Movie movieFromDb = moviesRepository.findById(movieId).orElseThrow(MovieNotFoundException::new);

        if (!userFromDb.getMovieList().contains(movieFromDb)) {
            userFromDb.getMovieList().add(movieFromDb);
            movieFromDb.getUserList().add(userFromDb);
            usersRepository.save(userFromDb);
            moviesRepository.save(movieFromDb);
        } else
            throw new MovieAlreadyInFavouritesException();
    }

    @Override
    public void deleteMovieFromFavourite(Long userId, Long movieId) {
        Movie movieFromDb = moviesRepository.findById(movieId).orElseThrow(MovieNotFoundException::new);
        User userFromDb = usersRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        userFromDb.getMovieList().remove(movieFromDb);
        movieFromDb.getUserList().remove(userFromDb);

        usersRepository.save(userFromDb);
        moviesRepository.save(movieFromDb);
    }

    @Override
    public List<Movie> showNoFavourites(Long userId, String loaderType) {
        if (loaderType.equals("sql"))
            return showNoFavouritesSQL(userId);
        else if (loaderType.equals("inMemory"))
            return showNoFavouritesInMemory(userId);
        else
            throw new IncorrectLoaderTypeException();
    }


    public List<Movie> showNoFavouritesSQL(Long userId) {
        return moviesRepository.findAllMoviesNotFavourite(userId);
    }


    public List<Movie> showNoFavouritesInMemory(Long userId) {
        List<Movie> movies = moviesRepository.findAll();
        List<Movie> favourites = usersRepository.findById(userId).orElseThrow(UserNotFoundException::new).getMovieList();
        return movies.stream()
                .filter(movie -> !favourites.contains(movie))
                .collect(Collectors.toList());
    }
}
