package com.cinema.booking_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Dati completi dello spettacolo recuperati dal servizio di catalogo")
public class ShowtimeClientDto {

    @Schema(description = "ID univoco dello spettacolo", example = "1")
    private Long id;

    @Schema(description = "Prezzo base del biglietto impostato nel catalogo", example = "12.50")
    private BigDecimal price;

    @Schema(description = "Data e ora della proiezione", example = "2026-07-20T21:30:00")
    private LocalDateTime startTime;

    @Schema(description = "Dettagli e capienza della sala assegnata")
    private RoomClientDto room;
}
