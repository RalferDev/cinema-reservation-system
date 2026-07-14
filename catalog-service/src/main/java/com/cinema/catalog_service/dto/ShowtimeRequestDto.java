package com.cinema.catalog_service.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowtimeRequestDto {
    private Long movieId;
    private Long roomId;
    private LocalDateTime startTime;
    private BigDecimal price;
}
