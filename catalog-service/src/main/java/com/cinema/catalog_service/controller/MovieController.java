package com.cinema.catalog_service.controller;

import com.cinema.catalog_service.dto.MovieRequestDto;
import com.cinema.catalog_service.dto.MovieResponseDto;
import com.cinema.catalog_service.entity.Movie;
import com.cinema.catalog_service.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    // POST /api/movies - Aggiunge un nuovo film al catalogo
    @PostMapping
    public ResponseEntity<MovieResponseDto> createMovie(@RequestBody MovieRequestDto request) {
        Movie movieToSave = Movie.builder()
                .title(request.getTitle())
                .genre(request.getGenre())
                .durationMinutes(request.getDurationMinutes())
                .description(request.getDescription())
                .posterUrl(request.getPosterUrl())
                .build();

        Movie savedMovie = movieService.createMovie(movieToSave);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MovieResponseDto.fromEntity(savedMovie));
    }

    // GET /api/movies - Restituisce tutti i film
    @GetMapping
    public ResponseEntity<List<MovieResponseDto>> getAllMovies() {
        List<MovieResponseDto> movies = movieService.getAllMovies().stream()
                .map(MovieResponseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(movies);
    }

    // GET /api/movies/{id} - Restituisce un singolo film
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDto> getMovieById(@PathVariable Long id) {
        Movie movie = movieService.getMovieById(id);
        return ResponseEntity.ok(MovieResponseDto.fromEntity(movie));
    }
}
