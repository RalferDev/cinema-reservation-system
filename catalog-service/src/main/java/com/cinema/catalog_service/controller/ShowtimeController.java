package com.cinema.catalog_service.controller;

import com.cinema.catalog_service.dto.ShowtimeRequestDto;
import com.cinema.catalog_service.dto.ShowtimeResponseDto;
import com.cinema.catalog_service.entity.Showtime;
import com.cinema.catalog_service.service.ShowtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/showtimes")
@RequiredArgsConstructor
public class ShowtimeController {

    private final ShowtimeService showtimeService;

    // POST /api/showtimes - Programma un nuovo spettacolo
    @PostMapping
    public ResponseEntity<ShowtimeResponseDto> createShowtime(@RequestBody ShowtimeRequestDto request) {
        Showtime createdShowtime = showtimeService.createShowtime(
                request.getMovieId(),
                request.getRoomId(),
                request.getStartTime(),
                request.getPrice()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ShowtimeResponseDto.fromEntity(createdShowtime));
    }

    // GET /api/showtimes/{id} - Restituisce un singolo spettacolo per ID
    @GetMapping("/{id}")
    public ResponseEntity<ShowtimeResponseDto> getShowtimeById(@PathVariable Long id) {
        Showtime showtime = showtimeService.getShowtimeById(id);
        return ResponseEntity.ok(ShowtimeResponseDto.fromEntity(showtime));
    }

    // GET /api/showtimes/movie/{movieId} - Restituisce la programmazione di un film
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ShowtimeResponseDto>> getShowtimesByMovie(@PathVariable Long movieId) {
        List<ShowtimeResponseDto> showtimes = showtimeService.getShowtimesByMovie(movieId).stream()
                .map(ShowtimeResponseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(showtimes);
    }

    // GET /api/showtimes/room/{roomId} - Restituisce gli spettacoli futuri in una sala
    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<ShowtimeResponseDto>> getShowtimesByRoom(@PathVariable Long roomId) {
        // Cerca tutti gli spettacoli a partire dal momento attuale
        List<ShowtimeResponseDto> showtimes = showtimeService.getShowtimesByRoom(roomId, LocalDateTime.now()).stream()
                .map(ShowtimeResponseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(showtimes);
    }
}
