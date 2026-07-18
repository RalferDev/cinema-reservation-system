package com.cinema.catalog_service.controller;

import com.cinema.catalog_service.dto.ShowtimeRequestDto;
import com.cinema.catalog_service.dto.ShowtimeResponseDto;
import com.cinema.catalog_service.entity.Showtime;
import com.cinema.catalog_service.service.ShowtimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Spettacoli", description = "API per la programmazione e ricerca degli orari di proiezione")
public class ShowtimeController {

    private final ShowtimeService showtimeService;

    // POST /api/showtimes - Programma un nuovo spettacolo
    @Operation(summary = "Programma uno spettacolo", description = "Collega un film del catalogo a una sala in una specifica data e ora di inizio, definendo il prezzo base del biglietto.")
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
    @Operation(summary = "Dettaglio singolo spettacolo", description = "Recupera i dati di una specifica proiezione, includendo le informazioni annidate del film e della sala associata.")
    @GetMapping("/{id}")
    public ResponseEntity<ShowtimeResponseDto> getShowtimeById(@PathVariable Long id) {
        Showtime showtime = showtimeService.getShowtimeById(id);
        return ResponseEntity.ok(ShowtimeResponseDto.fromEntity(showtime));
    }

    // GET /api/showtimes/movie/{movieId} - Restituisce la programmazione di un film
    @Operation(summary = "Spettacoli per film", description = "Filtra e restituisce tutte le proiezioni programmate per un determinato film.")
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ShowtimeResponseDto>> getShowtimesByMovie(@PathVariable Long movieId) {
        List<ShowtimeResponseDto> showtimes = showtimeService.getShowtimesByMovie(movieId).stream()
                .map(ShowtimeResponseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(showtimes);
    }

    // GET /api/showtimes/room/{roomId} - Restituisce gli spettacoli futuri in una sala
    @Operation(summary = "Spettacoli per sala", description = "Filtra e restituisce tutte le proiezioni future pianificate all'interno di una specifica sala.")
    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<ShowtimeResponseDto>> getShowtimesByRoom(@PathVariable Long roomId) {
        // Cerca tutti gli spettacoli a partire dal momento attuale
        List<ShowtimeResponseDto> showtimes = showtimeService.getShowtimesByRoom(roomId, LocalDateTime.now()).stream()
                .map(ShowtimeResponseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(showtimes);
    }

    // GET /api/showtimes/all - Restituisce la lista di tutti gli spettacoli programmati
    @Operation(summary = "Elenca tutti gli spettacoli", description = "Restituisce la programmazione generale di tutte le proiezioni pianificate.")
    @GetMapping("/all")
    public ResponseEntity<List<ShowtimeResponseDto>> getAllShowtimes() {
        List<ShowtimeResponseDto> showtimes = showtimeService.getAllShowtimes().stream()
                .map(ShowtimeResponseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(showtimes);
    }
}
