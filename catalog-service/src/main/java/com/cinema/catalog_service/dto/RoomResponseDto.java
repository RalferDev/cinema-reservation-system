package com.cinema.catalog_service.dto;

import com.cinema.catalog_service.entity.Room;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Dati restituiti relativi a una sala cinematografica e alla sua capienza")
public class RoomResponseDto {

    @Schema(description = "ID univoco della sala", example = "1")
    private Long id;

    @Schema(description = "Nome commerciale o identificativo della sala", example = "Sala 1 - IMAX")
    private String name;

    @Schema(description = "Numero totale di file presenti nella sala", example = "5")
    private Integer totalRows;

    @Schema(description = "Numero di poltrone presenti in ciascuna fila", example = "10")
    private Integer seatsPerRow;

    @Schema(description = "Capienza massima totale calcolata automaticamente (file x posti)", example = "50")
    private Integer capacity;

    public static RoomResponseDto fromEntity(Room room) {
        return RoomResponseDto.builder()
                .id(room.getId())
                .name(room.getName())
                .capacity(room.getCapacity())
                .totalRows(room.getTotalRows())
                .seatsPerRow(room.getSeatsPerRow())
                .build();
    }
}
