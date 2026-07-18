package com.cinema.booking_service.controller;

import com.cinema.booking_service.dto.ReservationRequestDto;
import com.cinema.booking_service.entity.Reservation;
import com.cinema.booking_service.service.BookingService;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BookingService bookingService;

    @Test
    @DisplayName("POST /api/reservations - Deve creare una prenotazione e restituire Status 201 Created")
    void createReservation_ShouldReturnCreated() throws Exception {
        ReservationRequestDto requestDto = ReservationRequestDto.builder()
                .showtimeId(10L)
                .seatId(5L)
                .customerName("Mario Rossi")
                .customerEmail("mario@example.com")
                .build();

        Reservation mockSavedReservation = Reservation.builder()
                .id(1L)
                .showtimeId(10L)
                .seatId(5L)
                .customerName("Mario Rossi")
                .customerEmail("mario@example.com")
                .price(BigDecimal.valueOf(12.50))
                .createdAt(LocalDateTime.now())
                .build();

        when(bookingService.createReservation(anyLong(), anyLong(), anyString(), anyString()))
                .thenReturn(mockSavedReservation);

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.showtimeId").value(10))
                .andExpect(jsonPath("$.seatId").value(5))
                .andExpect(jsonPath("$.customerName").value("Mario Rossi"))
                .andExpect(jsonPath("$.price").value(12.5));
    }

    @Test
    @DisplayName("GET /api/reservations/showtime/{showtimeId} - Deve restituire le prenotazioni per spettacolo")
    void getReservationsByShowtime_ShouldReturnList() throws Exception {
        Reservation res1 = Reservation.builder().id(1L).showtimeId(10L).seatId(1L).price(BigDecimal.valueOf(10.0)).build();
        Reservation res2 = Reservation.builder().id(2L).showtimeId(10L).seatId(2L).price(BigDecimal.valueOf(10.0)).build();

        when(bookingService.getReservationsByShowtime(10L)).thenReturn(List.of(res1, res2));

        mockMvc.perform(get("/api/reservations/showtime/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].seatId").value(1))
                .andExpect(jsonPath("$[1].seatId").value(2));
    }

    @Test
    @DisplayName("GET /api/reservations/{id} - Deve restituire una singola prenotazione per ID")
    void getReservationById_ShouldReturnReservation() throws Exception {
        Reservation mockRes = Reservation.builder()
                .id(1L)
                .showtimeId(10L)
                .seatId(5L)
                .customerName("Luigi Verdi")
                .price(BigDecimal.valueOf(15.00))
                .build();

        when(bookingService.getReservationById(1L)).thenReturn(mockRes);

        mockMvc.perform(get("/api/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerName").value("Luigi Verdi"))
                .andExpect(jsonPath("$.price").value(15.0));
    }

    @Test
    @DisplayName("GET /api/reservations/availability - Deve restituire true se il posto è disponibile")
    void checkAvailability_ShouldReturnTrue() throws Exception {
        when(bookingService.isSeatAvailable(10L, 5L)).thenReturn(true);

        mockMvc.perform(get("/api/reservations/availability")
                        .param("showtimeId", "10")
                        .param("seatId", "5"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @DisplayName("DELETE /api/reservations/{id} - Deve cancellare la prenotazione e restituire Status 204 No Content")
    void deleteReservation_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/reservations/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("POST /api/reservations - Deve assegnare automaticamente un posto se seatId è null")
    void createReservation_WithoutSeat_ShouldAssignAutomatically() throws Exception {
        // Richiesta SENZA seatId
        ReservationRequestDto requestDto = ReservationRequestDto.builder()
                .showtimeId(10L)
                .customerName("Anna Neri")
                .customerEmail("anna@example.com")
                .build();

        // Il mock simula che il sistema abbia trovato e assegnato il posto numero 1
        Reservation mockAutoAssignedReservation = Reservation.builder()
                .id(2L)
                .showtimeId(10L)
                .seatId(1L) // Assegnato in automatico!
                .customerName("Anna Neri")
                .customerEmail("anna@example.com")
                .price(BigDecimal.valueOf(12.50))
                .createdAt(LocalDateTime.now())
                .build();

        when(bookingService.createAutoReservation(anyLong(), anyString(), anyString()))
                .thenReturn(mockAutoAssignedReservation);

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.seatId").value(1))
                .andExpect(jsonPath("$.customerName").value("Anna Neri"));
    }
}