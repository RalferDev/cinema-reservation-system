package com.cinema.catalog_service.dto;

import com.cinema.catalog_service.entity.Movie;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Dati restituiti relativi a un film presente nel catalogo cinematografico")
public class MovieResponseDto {

    @Schema(description = "ID univoco assegnato al film dal sistema", example = "1")
    private Long id;

    @Schema(description = "Titolo ufficiale del film", example = "Inception")
    private String title;

    @Schema(description = "Genere cinematografico principale", example = "Sci-Fi")
    private String genre;

    @Schema(description = "Durata complessiva del film espressa in minuti", example = "148")
    private Integer durationMinutes;

    @Schema(description = "Trama o breve riassunto delle vicende del film", example = "Un ladro che ruba segreti corporativi attraverso l'uso della tecnologia di condivisione dei sogni...")
    private String description;

    @Schema(description = "URL pubblico o percorso relativo dell'immagine di locandina", example = "https://example.com/posters/inception.jpg")
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
