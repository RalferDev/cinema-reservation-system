package com.cinema.booking_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Dati della sala recuperati dal servizio di catalogo per verificare la capienza massima")
public class RoomClientDto {

    @Schema(description = "ID univoco della sala nel catalogo", example = "1")
    private Long id;

    @Schema(description = "Nome identificativo della sala", example = "Sala 1 - IMAX")
    private String name;

    @Schema(description = "Capienza totale (numero massimo di posti prenotabili)", example = "50")
    private Integer capacity;
}