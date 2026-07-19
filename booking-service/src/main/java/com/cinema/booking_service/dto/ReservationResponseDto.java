package com.cinema.booking_service.dto;

import com.cinema.booking_service.entity.Reservation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Dati restituiti a conferma dell'avvenuta prenotazione o emissione del biglietto")
public class ReservationResponseDto {
    @Schema(description = "Codice ID univoco della prenotazione generato dal sistema", example = "1")
    private Long id;

    @Schema(description = "ID dello spettacolo per cui è stato emesso il biglietto", example = "1")
    private Long showtimeId;

    @Schema(description = "Numero della poltrona assegnata all'interno della sala (scelta manualmente o in automatico)", example = "15")
    private Long seatId;

    @Schema(description = "Nome e cognome dell'acquirente", example = "Mario Rossi")
    private String customerName;

    @Schema(description = "Indirizzo email di riferimento per la prenotazione", example = "mario.rossi@example.com")
    private String customerEmail;

    @Schema(description = "Prezzo del singolo biglietto addebitato al cliente in euro", example = "12.50")
    private BigDecimal price;

    @Schema(description = "Timestamp esatto del momento in cui il biglietto è stato emesso e salvato a sistema", example = "2026-07-19T15:45:00")
    private LocalDateTime createdAt;

    public static ReservationResponseDto fromEntity(Reservation reservation) {
        return ReservationResponseDto.builder()
                .id(reservation.getId())
                .showtimeId(reservation.getShowtimeId())
                .seatId(reservation.getSeatId())
                .customerName(reservation.getCustomerName())
                .customerEmail(reservation.getCustomerEmail())
                .price(reservation.getPrice())
                .createdAt(reservation.getCreatedAt())
                .build();
    }
}
