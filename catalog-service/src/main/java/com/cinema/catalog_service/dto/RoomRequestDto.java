package com.cinema.catalog_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomRequestDto {
    private String name;
    private Integer totalRows;
    private Integer seatsPerRow;
}
