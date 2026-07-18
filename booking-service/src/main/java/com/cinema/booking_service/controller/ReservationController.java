package com.cinema.booking_service.controller;

import com.cinema.booking_service.dto.ReservationRequestDto;
import com.cinema.booking_service.dto.ReservationResponseDto;
import com.cinema.booking_service.entity.Reservation;
import com.cinema.booking_service.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Tag(name = "Prenotazioni", description = "API per l'acquisto di biglietti, verifica disponibilità, acquisti multipli e cancellazioni")
public class ReservationController {

    private final BookingService bookingService;

    // POST /api/reservations - Acquista un biglietto (con o senza posto specificato)
    @Operation(
            summary = "Acquista un biglietto (Manuale o Automatico)",
            description = "Crea una nuova prenotazione per uno spettacolo. Se il parametro 'seatId' viene specificato, prenota quel posto esatto (dopo averne verificato la disponibilità). Se 'seatId' viene omesso o inviato come null, il sistema assegna automaticamente il primo posto libero disponibile nella sala."
    )
    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationRequestDto request) {
        Reservation reservation;

        if (request.getSeatId() == null) {
            reservation = bookingService.createAutoReservation(
                    request.getShowtimeId(),
                    request.getCustomerName(),
                    request.getCustomerEmail()
            );
        } else {
            reservation = bookingService.createReservation(
                    request.getShowtimeId(),
                    request.getSeatId(),
                    request.getCustomerName(),
                    request.getCustomerEmail()
            );
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ReservationResponseDto.fromEntity(reservation));
    }

    // GET /api/reservations/showtime/{showtimeId} - Restituisce tutte le prenotazioni per uno spettacolo
    @Operation(summary = "Prenotazioni per spettacolo", description = "Restituisce l'elenco di tutti i biglietti già venduti per una specifica proiezione, utile per mappare la mappa dei posti occupati.")
    @GetMapping("/showtime/{showtimeId}")
    public ResponseEntity<List<ReservationResponseDto>> getReservationsByShowtime(@PathVariable Long showtimeId) {
        List<ReservationResponseDto> reservations = bookingService.getReservationsByShowtime(showtimeId).stream()
                .map(ReservationResponseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservations);
    }

    // GET /api/reservations/{id} - Restituisce i dettagli di una singola prenotazione
    @Operation(summary = "Dettaglio prenotazione", description = "Recupera i dati e lo stato di una prenotazione esistente tramite il suo codice ID unico.")
    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> getReservationById(@PathVariable Long id) {
        Reservation reservation = bookingService.getReservationById(id);
        return ResponseEntity.ok(ReservationResponseDto.fromEntity(reservation));
    }

    // GET /api/reservations/availability?showtimeId=1&seatId=5 - Verifica disponibilità posto
    @Operation(summary = "Verifica disponibilità posto", description = "Controlla in tempo reale se un determinato numero di poltrona è ancora libero e prenotabile per un target spettacolo.")
    @GetMapping("/availability")
    public ResponseEntity<Boolean> checkAvailability(@RequestParam Long showtimeId, @RequestParam Long seatId) {
        boolean available = bookingService.isSeatAvailable(showtimeId, seatId);
        return ResponseEntity.ok(available);
    }

    // DELETE /api/reservations/{id} - Cancella una prenotazione
    @Operation(summary = "Cancella prenotazione", description = "Annulla una prenotazione esistente e libera immediatamente il posto associato, rendendolo di nuovo disponibile per l'acquisto.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        bookingService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    // POST /api/reservations/batch - Acquista più biglietti/spettacoli contemporaneamente
    @Operation(
            summary = "Acquisto multiplo (Carrello transazionale)",
            description = "Consente di prenotare contemporaneamente più posti o più spettacoli in una singola transazione atomica. Se anche un solo posto richiesto risulta occupato, l'intera operazione viene annullata per evitare acquisti parziali."
    )
    @PostMapping("/batch")
    public ResponseEntity<List<ReservationResponseDto>> createBatchReservations(@RequestBody List<ReservationRequestDto> requests) {
        List<Reservation> reservations = bookingService.createBatchReservations(requests);
        List<ReservationResponseDto> response = reservations.stream()
                .map(ReservationResponseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
