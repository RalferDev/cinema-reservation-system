package com.cinema.catalog_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Oggetto di richiesta per l'inserimento di un nuovo film nel catalogo")
public class MovieRequestDto {

    @Schema(description = "Titolo ufficiale del film", example = "Inception")
    private String title;

    @Schema(description = "Genere cinematografico principale", example = "Sci-Fi")
    private String genre;

    @Schema(description = "Durata complessiva del film espressa in minuti", example = "148")
    private Integer durationMinutes;

    @Schema(description = "Trama o breve riassunto delle vicende del film", example = "Un ladro che ruba segreti corporativi attraverso l'uso della tecnologia di condivisione dei sogni...")
    private String description;

    @Schema(description = "URL pubblico o percorso relativo dell'immagine di locandina", example = "https://example.com/posters/inception.jpg")
    private String posterUrl;
}
