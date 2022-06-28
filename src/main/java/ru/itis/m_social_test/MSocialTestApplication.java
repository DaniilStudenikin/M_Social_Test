package ru.itis.m_social_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MSocialTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MSocialTestApplication.class, args);
    }

}
