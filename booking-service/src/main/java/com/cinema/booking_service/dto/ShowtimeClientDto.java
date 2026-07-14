package com.cinema.booking_service.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowtimeClientDto {
    private Long id;
    private BigDecimal price;
    private LocalDateTime startTime;
}
