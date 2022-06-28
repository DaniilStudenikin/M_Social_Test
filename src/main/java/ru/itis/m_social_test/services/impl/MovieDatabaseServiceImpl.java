package ru.itis.m_social_test.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.itis.m_social_test.models.Movie;
import ru.itis.m_social_test.repositories.MoviesRepository;
import ru.itis.m_social_test.services.MovieDatabaseService;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MovieDatabaseServiceImpl implements MovieDatabaseService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MoviesRepository moviesRepository;

    @Scheduled(fixedDelay = 1000 * 60 * 60 * 3)
    @Override
    public void parseMovies() {
        System.out.println("Start parsing");

        Map<String, Movie> movies = new HashMap<>();
        List<Movie> moviesFromDb = moviesRepository.findAll();

        for (Movie movie : moviesFromDb) {
            movies.put(movie.getTitle(), movie);
        }

        for (int i = 1; i < 6; i++) {
            String url = "https://api.themoviedb.org/3/discover/movie?api_key=45c9ff0594bfa02380ed8d472c08b675&page=" + i;
            try {
                JsonNode json = objectMapper.readTree(restTemplate.getForObject(url, String.class));
                for (JsonNode result : json.get("results")) {
                    String title = result.get("title").asText();
                    String posterPath = result.get("poster_path").asText();
                    if (!movies.containsKey(title))
                        movies.put(title, Movie.builder()
                                .title(title)
                                .poster_path(posterPath)
                                .build());
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        for (Map.Entry<String, Movie> entry : movies.entrySet()) {
            moviesRepository.save(entry.getValue());
        }
        System.out.println("Parsing end");
    }
}
