package ru.itis.m_social_test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itis.m_social_test.models.Movie;
import ru.itis.m_social_test.models.User;

import java.util.List;

@Repository
public interface MoviesRepository extends JpaRepository<Movie, Long> {
    @Query(value = "select distinct movies.id, movies.title, movies.poster_path\n" +
                    "from movies\n" +
                    "left join favourites on movies.id = favourites.mov_id\n" +
                    "where favourites.usr_id != ?1\n" +
                    "or favourites.usr_id is null\n" +
                    "order by id;",nativeQuery = true)
    List<Movie> findAllMoviesNotFavourite(Long userId);
}
