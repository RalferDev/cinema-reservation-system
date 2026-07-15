package com.cinema.catalog_service.controller;

import com.cinema.catalog_service.dto.RoomRequestDto;
import com.cinema.catalog_service.entity.Room;
import com.cinema.catalog_service.service.RoomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest (RoomController.class)
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RoomService roomService;

    @Test
    @DisplayName("POST /api/rooms - Deve creare una sala e restituire Status 201 Created")
    void createRoom_ShouldReturnCreated() throws Exception {
        RoomRequestDto requestDto = RoomRequestDto.builder()
                .name("Sala 1 - IMAX")
                .totalRows(5)
                .seatsPerRow(10)
                .build();

        Room mockSavedRoom = Room.builder()
                .id(1L)
                .name("Sala 1 - IMAX")
                .capacity(50)
                .totalRows(5)
                .seatsPerRow(10)
                .build();

        when(roomService.createRoom(anyString(), anyInt(), anyInt())).thenReturn(mockSavedRoom);

        mockMvc.perform(post("/api/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Sala 1 - IMAX"))
                .andExpect(jsonPath("$.capacity").value(50));
    }

    @Test
    @DisplayName("GET /api/rooms - Deve restituire la lista di tutte le sale e Status 200 OK")
    void getAllRooms_ShouldReturnList() throws Exception {
        Room room1 = Room.builder().id(1L).name("Sala 1").capacity(50).build();
        Room room2 = Room.builder().id(2L).name("Sala 2").capacity(100).build();

        when(roomService.getAllRooms()).thenReturn(List.of(room1, room2));

        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Sala 1"))
                .andExpect(jsonPath("$[1].name").value("Sala 2"));
    }

    @Test
    @DisplayName("GET /api/rooms/{id} - Deve restituire una singola sala per ID e Status 200 OK")
    void getRoomById_ShouldReturnRoom() throws Exception {
        Room mockRoom = Room.builder().id(1L).name("Sala VIP").capacity(20).build();
        when(roomService.getRoomById(1L)).thenReturn(mockRoom);

        mockMvc.perform(get("/api/rooms/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Sala VIP"));
    }
}