package com.cinema.catalog_service.dto;

import com.cinema.catalog_service.entity.Showtime;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowtimeResponseDto {
    private Long id;
    private MovieResponseDto movie;
    private RoomResponseDto room;
    private LocalDateTime startTime;
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
