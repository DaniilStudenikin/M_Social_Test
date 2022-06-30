package ru.itis.m_social_test.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.itis.m_social_test.models.Movie;
import ru.itis.m_social_test.repositories.MoviesRepository;
import ru.itis.m_social_test.services.MovieDatabaseService;


import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MovieDatabaseServiceImpl implements MovieDatabaseService {
    private final Logger logger = LoggerFactory.getLogger(MovieDatabaseServiceImpl.class);
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;
    @Value("${movie.database.api-key}")
    private String apiKey;

    @Autowired
    private MoviesRepository moviesRepository;

    @Scheduled(fixedDelay = 1000 * 60 * 60 * 3)
    @Override
    public void parseMovies() {
        logger.info("Start parsing movies.");

        Map<String, Movie> movies = moviesRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Movie::getTitle, Function.identity()));


        for (int i = 1; i < 6; i++) {
            String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + apiKey + "&page=" + i;
            try {
                JsonNode jsonFromApi = objectMapper.readTree(restTemplate.getForObject(url, String.class));
                jsonFromApi.get("results").forEach(result -> {
                    String title = result.get("title").asText();
                    String posterPath = result.get("poster_path").asText();
                    if (!movies.containsKey(title))
                        movies.put(title, Movie.builder()
                                .title(title)
                                .poster_path(posterPath)
                                .build());
                });

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        moviesRepository.saveAll(movies.values());

        logger.info("Parsing end.");
    }
}
