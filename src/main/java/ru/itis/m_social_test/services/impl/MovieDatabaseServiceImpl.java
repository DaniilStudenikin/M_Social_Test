package ru.itis.m_social_test.services.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.itis.m_social_test.services.MovieDatabaseService;

import java.net.http.HttpRequest;

@Service
public class MovieDatabaseServiceImpl implements MovieDatabaseService {

    @Scheduled(fixedDelay = 60 * 60 * 3)
    @Override
    public void parseMovies() {

    }
}
