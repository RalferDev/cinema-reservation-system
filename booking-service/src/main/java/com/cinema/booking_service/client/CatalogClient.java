package com.cinema.booking_service.client;

import com.cinema.booking_service.dto.ShowtimeClientDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CatalogClient {

    private final RestClient restClient;

    public CatalogClient(@Value("${catalog.service.url}") String catalogUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(catalogUrl)
                .build();
    }

    public ShowtimeClientDto getShowtime(Long showtimeId) {
        return restClient.get()
                .uri("/showtimes/{id}", showtimeId)
                .retrieve()
                .body(ShowtimeClientDto.class);
    }
}
