package com.cinema.booking_service.controller;

import com.cinema.booking_service.dto.ReservationRequestDto;
import com.cinema.booking_service.dto.ReservationResponseDto;
import com.cinema.booking_service.entity.Reservation;
import com.cinema.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final BookingService bookingService;

    // POST /api/reservations - Acquista un biglietto (con o senza posto specificato)
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
    @GetMapping("/showtime/{showtimeId}")
    public ResponseEntity<List<ReservationResponseDto>> getReservationsByShowtime(@PathVariable Long showtimeId) {
        List<ReservationResponseDto> reservations = bookingService.getReservationsByShowtime(showtimeId).stream()
                .map(ReservationResponseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservations);
    }

    // GET /api/reservations/{id} - Restituisce i dettagli di una singola prenotazione
    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> getReservationById(@PathVariable Long id) {
        Reservation reservation = bookingService.getReservationById(id);
        return ResponseEntity.ok(ReservationResponseDto.fromEntity(reservation));
    }

    // GET /api/reservations/availability?showtimeId=1&seatId=5 - Verifica disponibilità posto
    @GetMapping("/availability")
    public ResponseEntity<Boolean> checkAvailability(@RequestParam Long showtimeId, @RequestParam Long seatId) {
        boolean available = bookingService.isSeatAvailable(showtimeId, seatId);
        return ResponseEntity.ok(available);
    }

    // DELETE /api/reservations/{id} - Cancella una prenotazione
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        bookingService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

}
