package com.cinema.catalog_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Oggetto di richiesta per la pianificazione di un nuovo spettacolo nel calendario")
public class ShowtimeRequestDto {

    @Schema(description = "ID del film da proiettare (deve esistere nel catalogo)", example = "1")
    private Long movieId;

    @Schema(description = "ID della sala in cui si terrà la proiezione (deve esistere a sistema)", example = "1")
    private Long roomId;

    @Schema(description = "Data e ora di inizio della proiezione in formato ISO 8601", example = "2026-07-20T21:30:00")
    private LocalDateTime startTime;

    @Schema(description = "Prezzo base del biglietto per questo spettacolo in euro", example = "12.50")
    private BigDecimal price;
}
