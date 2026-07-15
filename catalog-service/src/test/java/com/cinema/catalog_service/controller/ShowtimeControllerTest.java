package com.cinema.catalog_service.controller;

import com.cinema.catalog_service.dto.ShowtimeRequestDto;
import com.cinema.catalog_service.entity.Movie;
import com.cinema.catalog_service.entity.Room;
import com.cinema.catalog_service.entity.Showtime;
import com.cinema.catalog_service.service.ShowtimeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ShowtimeController.class)
class ShowtimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ShowtimeService showtimeService;

    @Test
    @DisplayName("POST /api/showtimes - Deve programmare uno spettacolo e restituire Status 201 Created")
    void createShowtime_ShouldReturnCreated() throws Exception {
        LocalDateTime startTime = LocalDateTime.of(2026, 7, 15, 21, 30);

        ShowtimeRequestDto requestDto = ShowtimeRequestDto.builder()
                .movieId(1L)
                .roomId(1L)
                .startTime(startTime)
                .price(BigDecimal.valueOf(12.50))
                .build();


        Movie mockMovie = Movie.builder().id(1L).title("Inception").build();
        Room mockRoom = Room.builder().id(1L).name("Sala 1").build();

        Showtime mockSavedShowtime = Showtime.builder()
                .id(1L)
                .movie(mockMovie)
                .room(mockRoom)
                .startTime(startTime)
                .price(BigDecimal.valueOf(12.50))
                .build();

        when(showtimeService.createShowtime(anyLong(), anyLong(), any(LocalDateTime.class), any(BigDecimal.class)))
                .thenReturn(mockSavedShowtime);

        mockMvc.perform(post("/api/showtimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.movie.id").value(1))
                .andExpect(jsonPath("$.room.id").value(1))
                .andExpect(jsonPath("$.price").value(12.5));
    }

    @Test
    @DisplayName("GET /api/showtimes - Deve restituire tutti gli spettacoli e Status 200 OK")
    void getAllShowtimes_ShouldReturnList() throws Exception {
        Movie mockMovie = Movie.builder().id(1L).title("Inception").build();
        Room mockRoom = Room.builder().id(1L).name("Sala 1").build();

        Showtime showtime1 = Showtime.builder().id(1L).movie(mockMovie).room(mockRoom).price(BigDecimal.valueOf(10.0)).build();
        Showtime showtime2 = Showtime.builder().id(2L).movie(mockMovie).room(mockRoom).price(BigDecimal.valueOf(15.0)).build();

        when(showtimeService.getAllShowtimes()).thenReturn(List.of(showtime1, showtime2));

        mockMvc.perform(get("/api/showtimes/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    @DisplayName("GET /api/showtimes/{id} - Deve restituire un singolo spettacolo per ID e Status 200 OK")
    void getShowtimeById_ShouldReturnShowtime() throws Exception {
        Movie mockMovie = Movie.builder().id(1L).title("Inception").build();
        Room mockRoom = Room.builder().id(1L).name("Sala 1").build();

        Showtime mockShowtime = Showtime.builder().id(1L).movie(mockMovie).room(mockRoom).price(BigDecimal.valueOf(12.50)).build();

        when(showtimeService.getShowtimeById(1L)).thenReturn(mockShowtime);

        mockMvc.perform(get("/api/showtimes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.price").value(12.5));
    }
}