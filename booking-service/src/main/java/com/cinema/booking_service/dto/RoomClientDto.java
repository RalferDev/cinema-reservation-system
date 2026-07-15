package com.cinema.booking_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomClientDto {
    private Long id;
    private String name;
    private Integer capacity;
}