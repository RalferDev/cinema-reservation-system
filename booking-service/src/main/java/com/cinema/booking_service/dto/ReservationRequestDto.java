package com.cinema.booking_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Oggetto di richiesta per la creazione o l'acquisto di biglietti")
public class ReservationRequestDto {

    @Schema(description = "ID identificativo dello spettacolo", example = "1")
    private Long showtimeId;

    @Schema(description = "ID del posto da prenotare. Se omesso o null, il sistema assegnerà automaticamente il primo posto libero", example = "5", nullable = true)
    private Long seatId;

    @Schema(description = "Nome e cognome dell'acquirente", example = "Mario Rossi")
    private String customerName;

    @NotBlank(message = "L'email del cliente non può essere vuota")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "Formato email non valido. Inserire un indirizzo corretto (es. nome@dominio.com)"
    )
    @Schema(description = "Indirizzo email valido per l'invio della conferma di acquisto", example = "mario.rossi@example.com")
    private String customerEmail;
}