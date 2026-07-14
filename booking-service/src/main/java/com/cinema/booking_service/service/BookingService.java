package com.cinema.booking_service.service;

import com.cinema.booking_service.client.CatalogClient;
import com.cinema.booking_service.dto.ShowtimeClientDto;
import com.cinema.booking_service.entity.Reservation;
import com.cinema.booking_service.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
