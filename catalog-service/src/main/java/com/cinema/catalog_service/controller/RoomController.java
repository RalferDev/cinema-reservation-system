package com.cinema.catalog_service.controller;

import com.cinema.catalog_service.dto.RoomRequestDto;
import com.cinema.catalog_service.dto.RoomResponseDto;
import com.cinema.catalog_service.entity.Room;
import com.cinema.catalog_service.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
@Tag(name = "Sale", description = "API per la gestione e configurazione delle sale cinematografiche")
public class RoomController {

    private final RoomService roomService;

    // POST /api/rooms - Crea una nuova sala e restituisce il DTO
    @Operation(
            summary = "Crea una nuova sala",
            description = "Registra una nuova sala nel cinema specificando il nome, il numero totale di file e i posti per fila. La capienza totale viene calcolata automaticamente."
    )
    @PostMapping
    public ResponseEntity<RoomResponseDto> createRoom(@RequestBody RoomRequestDto request) {
        Room createdRoom = roomService.createRoom(
                request.getName(),
                request.getTotalRows(),
                request.getSeatsPerRow()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RoomResponseDto.fromEntity(createdRoom));
    }

    // GET /api/rooms - Restituisce la lista di tutte le sale mappate in DTO
    @Operation(
            summary = "Elenca tutte le sale",
            description = "Restituisce la lista completa delle sale cinematografiche registrate a sistema e le relative capienze."
    )
    @GetMapping
    public ResponseEntity<List<RoomResponseDto>> getAllRooms() {
        List<RoomResponseDto> rooms = roomService.getAllRooms().stream()
                .map(RoomResponseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(rooms);
    }

    // GET /api/rooms/{id} - Restituisce una singola sala mappata in DTO
    @Operation(
            summary = "Dettaglio singola sala",
            description = "Recupera i dati e la disposizione di una sala specifica tramite il suo ID identificativo."
    )
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDto> getRoomById(@PathVariable Long id) {
        Room room = roomService.getRoomById(id);
        return ResponseEntity.ok(RoomResponseDto.fromEntity(room));
    }
}
