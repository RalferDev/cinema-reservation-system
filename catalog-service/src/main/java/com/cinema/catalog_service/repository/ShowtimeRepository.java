package com.cinema.catalog_service.repository;

import com.cinema.catalog_service.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {

    List<Showtime> findByMovieId(Long movieId);

    List<Showtime> findByRoomIdAndStartTimeAfter(Long roomId, LocalDateTime time);
}
