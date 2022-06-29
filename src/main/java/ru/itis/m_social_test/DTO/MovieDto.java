package ru.itis.m_social_test.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.m_social_test.models.Movie;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDto {
    private Long id;

    private String title;

    private String poster_path;

    public static MovieDto from(Movie movie) {
        return MovieDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .poster_path(movie.getPoster_path())
                .build();
    }

    public static List<MovieDto> from(List<Movie> movies) {
        return movies.stream().map(MovieDto::from).collect(Collectors.toList());
    }
}
