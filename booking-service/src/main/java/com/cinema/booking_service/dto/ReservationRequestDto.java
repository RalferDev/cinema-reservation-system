package com.cinema.booking_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequestDto {
    private Long showtimeId;
    private Long seatId;
    private String customerName;
    private String customerEmail;
}