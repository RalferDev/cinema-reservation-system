package com.cinema.booking_service.service;

import com.cinema.booking_service.client.CatalogClient;
import com.cinema.booking_service.dto.ShowtimeClientDto;
import com.cinema.booking_service.entity.Reservation;
import com.cinema.booking_service.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final ReservationRepository reservationRepository;
    private final CatalogClient catalogClient;

    @Transactional
    public Reservation createReservation(Long showtimeId, Long seatId, String customerName, String customerEmail) {

        if (reservationRepository.existsByShowtimeIdAndSeatId(showtimeId, seatId)) {
            throw new RuntimeException("Ci dispiace, questo posto è già stato prenotato per questo spettacolo!");
        }

        ShowtimeClientDto showtime;
        try {
            showtime = catalogClient.getShowtime(showtimeId);
        } catch (Exception e) {
            throw new RuntimeException("Impossibile recuperare i dettagli dello spettacolo dal Catalogo (ID: " + showtimeId + ")");
        }

        Reservation reservation = Reservation.builder()
                .showtimeId(showtimeId)
                .seatId(seatId)
                .customerName(customerName)
                .customerEmail(customerEmail)
                .price(showtime.getPrice())
                .build();

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsByShowtime(Long showtimeId) {
        return reservationRepository.findByShowtimeId(showtimeId);
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione non trovata con ID: " + id));
    }

    public boolean isSeatAvailable(Long showtimeId, Long seatId) {
        return !reservationRepository.existsByShowtimeIdAndSeatId(showtimeId, seatId);
    }

    @Transactional
    public void deleteReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new RuntimeException("Impossibile cancellare: Prenotazione non trovata con ID: " + id);
        }
        reservationRepository.deleteById(id);
    }

    @Transactional
    public Reservation createAutoReservation(Long showtimeId, String customerName, String customerEmail) {

        ShowtimeClientDto showtime;
        try {
            showtime = catalogClient.getShowtime(showtimeId);
        } catch (Exception e) {
            throw new RuntimeException("Impossibile recuperare i dettagli dello spettacolo dal Catalogo (ID: " + showtimeId + ")");
        }

        if (showtime.getRoom() == null || showtime.getRoom().getCapacity() == null) {
            throw new RuntimeException("Dati sulla capienza della sala non disponibili per questo spettacolo.");
        }

        int capacity = showtime.getRoom().getCapacity();

        List<Reservation> existingReservations = reservationRepository.findByShowtimeId(showtimeId);
        Set<Long> occupiedSeats = existingReservations.stream()
                .map(Reservation::getSeatId)
                .collect(Collectors.toSet());

        Long firstAvailableSeat = null;
        for (long i = 1; i <= capacity; i++) {
            if (!occupiedSeats.contains(i)) {
                firstAvailableSeat = i;
                break;
            }
        }

        if (firstAvailableSeat == null) {
            throw new RuntimeException("Ci dispiace, lo spettacolo è interamente sold out!");
        }

        Reservation reservation = Reservation.builder()
                .showtimeId(showtimeId)
                .seatId(firstAvailableSeat)
                .customerName(customerName)
                .customerEmail(customerEmail)
                .price(showtime.getPrice())
                .build();

        return reservationRepository.save(reservation);
    }
}
