package com.example.inventory.service;

import com.example.inventory.dto.request.ReservationRequestDto;
import com.example.inventory.dto.response.ReservationResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface ReservationService {

    ReservationResponseDto createReservation(ReservationRequestDto reservationRequestDto);
    ReservationResponseDto cancelReservation(UUID reservationId);
    ReservationResponseDto getReservationById(UUID reservationId);
}
