package com.example.inventory.controller;

import com.example.inventory.dto.request.ReservationRequestDto;
import com.example.inventory.dto.response.ReservationResponseDto;
import com.example.inventory.service.ReservationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(@Valid @RequestBody ReservationRequestDto reservationRequestDto) {
        log.info("Received request to create reservation for itemId: {} quantity: {}",
                reservationRequestDto.getItemId(), reservationRequestDto.getQuantity());

        ReservationResponseDto dto = reservationService.createReservation(reservationRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/cancel/{reservationId}")
    public ResponseEntity<ReservationResponseDto> cancelReservation(@PathVariable UUID reservationId) {
        log.info("Received request to cancel reservationId: {}", reservationId);

        ReservationResponseDto dto = reservationService.cancelReservation(reservationId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationResponseDto> getReservationById(@PathVariable UUID reservationId) {
        log.info("Received request to fetch reservation with reservationId: {}", reservationId);

        ReservationResponseDto dto = reservationService.getReservationById(reservationId);
        return ResponseEntity.ok(dto);
    }
}
