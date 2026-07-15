package com.cinema.catalog_service.controller;

import com.cinema.catalog_service.dto.MovieRequestDto;
import com.cinema.catalog_service.entity.Movie;
import com.cinema.catalog_service.service.MovieService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MovieService movieService;

    @Test
    @DisplayName("POST /api/movies - Deve creare un film e restituire Status 201 Created")
    void createMovie_ShouldReturnCreated() throws Exception {
        MovieRequestDto requestDto = MovieRequestDto.builder()
                .title("Inception")
                .genre("Sci-Fi")
                .durationMinutes(148)
                .description("Un ladro che ruba segreti corporativi attraverso i sogni...")
                .posterUrl("https://example.com/inception.jpg")
                .build();

        Movie mockSavedMovie = Movie.builder()
                .id(1L)
                .title("Inception")
                .genre("Sci-Fi")
                .durationMinutes(148)
                .description("Un ladro che ruba segreti corporativi attraverso i sogni...")
                .posterUrl("https://example.com/inception.jpg")
                .build();

        when(movieService.createMovie(any(Movie.class))).thenReturn(mockSavedMovie);

        mockMvc.perform(post("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.genre").value("Sci-Fi"))
                .andExpect(jsonPath("$.durationMinutes").value(148));
    }

    @Test
    @DisplayName("GET /api/movies - Deve restituire la lista di tutti i film e Status 200 OK")
    void getAllMovies_ShouldReturnList() throws Exception {
        Movie movie1 = Movie.builder().id(1L).title("Inception").genre("Sci-Fi").durationMinutes(148).build();
        Movie movie2 = Movie.builder().id(2L).title("Interstellar").genre("Sci-Fi").durationMinutes(169).build();

        when(movieService.getAllMovies()).thenReturn(List.of(movie1, movie2));

        mockMvc.perform(get("/api/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].title").value("Inception"))
                .andExpect(jsonPath("$[1].title").value("Interstellar"));
    }

    @Test
    @DisplayName("GET /api/movies/{id} - Deve restituire un singolo film per ID e Status 200 OK")
    void getMovieById_ShouldReturnMovie() throws Exception {
        Movie mockMovie = Movie.builder().id(1L).title("Inception").genre("Sci-Fi").durationMinutes(148).build();

        when(movieService.getMovieById(1L)).thenReturn(mockMovie);

        mockMvc.perform(get("/api/movies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.genre").value("Sci-Fi"));
    }
}
