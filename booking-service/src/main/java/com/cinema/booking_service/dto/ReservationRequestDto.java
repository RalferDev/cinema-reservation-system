package com.cinema.booking_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequestDto {
    private Long showtimeId;
    private Long seatId;
    private String customerName;
    @NotBlank(message = "L'email del cliente non può essere vuota")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "Formato email non valido. Inserire un indirizzo corretto (es. nome@dominio.com)"
    )
    private String customerEmail;
}