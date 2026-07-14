package com.cinema.catalog_service.service;

import com.cinema.catalog_service.entity.Movie;
import com.cinema.catalog_service.entity.Room;
import com.cinema.catalog_service.entity.Showtime;
import com.cinema.catalog_service.repository.ShowtimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowtimeService {

    private final ShowtimeRepository showtimeRepository;
    private final MovieService movieService;
    private final RoomService roomService;

    @Transactional
    public Showtime createShowtime(Long movieId, Long roomId, LocalDateTime startTime, BigDecimal price) {

        Movie movie = movieService.getMovieById(movieId);
        Room room = roomService.getRoomById(roomId);

        Showtime showtime = Showtime.builder()
                .movie(movie)
                .room(room)
                .startTime(startTime)
                .price(price)
                .build();

        return showtimeRepository.save(showtime);
    }

    public List<Showtime> getShowtimesByMovie(Long movieId) {
        return showtimeRepository.findByMovieId(movieId);
    }

    public List<Showtime> getShowtimesByRoom(Long roomId, LocalDateTime fromTime) {
        return showtimeRepository.findByRoomIdAndStartTimeAfter(roomId, fromTime);
    }

    public Showtime getShowtimeById(Long id) {
        return showtimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Spettacolo non trovato con ID: " + id));
    }
}