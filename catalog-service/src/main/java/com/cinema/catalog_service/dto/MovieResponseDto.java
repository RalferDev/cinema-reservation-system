package com.cinema.catalog_service.dto;

import com.cinema.catalog_service.entity.Movie;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponseDto {
    private Long id;
    private String title;
    private String genre;
    private Integer durationMinutes;
    private String description;
    private String posterUrl;

    public static MovieResponseDto fromEntity(Movie movie) {
        return MovieResponseDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .durationMinutes(movie.getDurationMinutes())
                .description(movie.getDescription())
                .posterUrl(movie.getPosterUrl())
                .build();
    }
}
