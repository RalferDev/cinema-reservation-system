package com.cinema.booking_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bookingOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Booking Service API")
                        .description("Microservizio per la gestione delle prenotazioni, verifica disponibilità e acquisti singoli o multipli.")
                        .version("v1.0.0"));
    }
}