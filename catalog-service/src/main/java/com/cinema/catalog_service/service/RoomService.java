package com.cinema.catalog_service.service;

import com.cinema.catalog_service.entity.Room;
import com.cinema.catalog_service.entity.Seat;
import com.cinema.catalog_service.repository.RoomRepository;
import com.cinema.catalog_service.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public Room createRoom(String name, Integer totalRows, Integer seatsPerRow) {

        int totalCapacity = totalRows * seatsPerRow;

        Room room = Room.builder()
                .name(name)
                .totalRows(totalRows)
                .seatsPerRow(seatsPerRow)
                .capacity(totalCapacity)
                .build();

        Room savedRoom = roomRepository.save(room);

        List<Seat> seatsToCreate = new ArrayList<>();

        for (int i = 0; i < totalRows; i++) {

            String rowLabel = String.valueOf((char) ('A' + i));

            for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
                Seat seat = Seat.builder()
                        .room(savedRoom)
                        .row(rowLabel)
                        .number(seatNum)
                        .build();
                seatsToCreate.add(seat);
            }
        }

        seatRepository.saveAll(seatsToCreate);

        return savedRoom;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala non trovata con ID: " + id));
    }
}