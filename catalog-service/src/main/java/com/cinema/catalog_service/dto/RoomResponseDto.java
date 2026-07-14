package com.cinema.catalog_service.dto;

import com.cinema.catalog_service.entity.Room;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponseDto {
    private Long id;
    private String name;
    private Integer capacity;
    private Integer totalRows;
    private Integer seatsPerRow;

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
