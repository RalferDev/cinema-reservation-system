package com.cinema.catalog_service.dto;

import com.cinema.catalog_service.entity.Showtime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Dati completi restituiti per uno spettacolo programmato, inclusi i dettagli di film e sala")
public class ShowtimeResponseDto {

    @Schema(description = "ID univoco assegnato allo spettacolo dal sistema", example = "1")
    private Long id;

    @Schema(description = "Oggetto contenente le informazioni del film proiettato")
    private MovieResponseDto movie;

    @Schema(description = "Oggetto contenente i dati e la capienza della sala assegnata")
    private RoomResponseDto room;

    @Schema(description = "Data e ora di inizio della proiezione in formato ISO 8601", example = "2026-07-20T21:30:00")
    private LocalDateTime startTime;

    @Schema(description = "Prezzo del biglietto per questa proiezione", example = "12.50")
    private BigDecimal price;

    public static ShowtimeResponseDto fromEntity(Showtime showtime) {
        return ShowtimeResponseDto.builder()
                .id(showtime.getId())
                .movie(MovieResponseDto.fromEntity(showtime.getMovie()))
                .room(RoomResponseDto.fromEntity(showtime.getRoom()))
                .startTime(showtime.getStartTime())
                .price(showtime.getPrice())
                .build();
    }
}
