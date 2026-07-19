package com.cinema.catalog_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Oggetto di richiesta per la creazione o configurazione di una sala cinematografica")
public class RoomRequestDto {

    @Schema(description = "Nome commerciale o identificativo della sala", example = "Sala 1 - IMAX")
    private String name;

    @Schema(description = "Numero totale di file presenti nella sala", example = "5")
    private Integer totalRows;

    @Schema(description = "Numero di poltrone presenti in ciascuna fila", example = "10")
    private Integer seatsPerRow;
}
