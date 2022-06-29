package ru.itis.m_social_test.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.itis.m_social_test.models.Movie;
import ru.itis.m_social_test.models.User;
import ru.itis.m_social_test.repositories.MoviesRepository;
import ru.itis.m_social_test.repositories.UsersRepository;
import ru.itis.m_social_test.services.MoviesService;

import java.util.List;
import java.util.Optional;
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
    public String addMovieToFavourite(Long userId, Long movieId) {
        Optional<User> userFromDb = usersRepository.findById(userId);
        Optional<Movie> movieFromDb = moviesRepository.findById(movieId);

        if (!userFromDb.orElseThrow(IllegalArgumentException::new).getMovieList().contains(movieFromDb.orElseThrow(IllegalArgumentException::new))) {
            userFromDb.get().getMovieList().add(movieFromDb.get());
            movieFromDb.get().getUserList().add(userFromDb.get());
            usersRepository.save(userFromDb.get());
            moviesRepository.save(movieFromDb.get());
            return "Успешно!";
        }
        return "Уже добавлено!";

    }

    @Override
    public String deleteMovieFromFavourite(Long userId, Long movieId) {
        Optional<Movie> movieFromDb = moviesRepository.findById(movieId);
        Optional<User> userFromDb = usersRepository.findById(userId);
        if (movieFromDb.isPresent() && userFromDb.isPresent()) {
            userFromDb.get().getMovieList().remove(movieFromDb.get());
            movieFromDb.get().getUserList().remove(userFromDb.get());
            usersRepository.save(userFromDb.get());
            moviesRepository.save(movieFromDb.get());
            return "Удаление завершено!";
        } else {
            return "Вы указали неверные данные!";
        }
    }

    @Override
    public List<Movie> showNoFavouritesSQL(Long userId) {
        return moviesRepository.findAllMoviesNotFavourite(userId);
    }

    @Override
    public List<Movie> showNoFavouritesInMemory(Long userId) {
        List<Movie> movies = moviesRepository.findAll();
        List<Movie> favourites = usersRepository.findById(userId).orElseThrow(IllegalArgumentException::new).getMovieList();
        return movies.stream()
                .filter(favourites::contains)
                .collect(Collectors.toList());
    }
}
