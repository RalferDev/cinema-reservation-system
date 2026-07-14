package com.cinema.booking_service.dto;

import com.cinema.booking_service.entity.Reservation;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponseDto {
    private Long id;
    private Long showtimeId;
    private Long seatId;
    private String customerName;
    private String customerEmail;
    private BigDecimal price;
    private LocalDateTime createdAt;

    public static ReservationResponseDto fromEntity(Reservation reservation) {
        return ReservationResponseDto.builder()
                .id(reservation.getId())
                .showtimeId(reservation.getShowtimeId())
                .seatId(reservation.getSeatId())
                .customerName(reservation.getCustomerName())
                .customerEmail(reservation.getCustomerEmail())
                .price(reservation.getPrice())
                .createdAt(reservation.getCreatedAt())
                .build();
    }
}
