package com.cinema.catalog_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieRequestDto {
    private String title;
    private String genre;
    private Integer durationMinutes;
    private String description;
    private String posterUrl;
}
