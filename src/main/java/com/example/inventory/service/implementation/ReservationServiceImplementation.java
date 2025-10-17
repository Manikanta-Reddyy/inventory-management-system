package com.example.inventory.service.implementation;

import com.example.inventory.dto.request.ReservationRequestDto;
import com.example.inventory.dto.response.ReservationResponseDto;
import com.example.inventory.enums.ReservationStatus;
import com.example.inventory.exception.ItemNotFoundException;
import com.example.inventory.exception.ReservationCancellationException;
import com.example.inventory.exception.ReservationNotFoundException;
import com.example.inventory.mapper.ReservationMapper;
import com.example.inventory.model.Item;
import com.example.inventory.model.Reservation;
import com.example.inventory.repository.ItemRepository;
import com.example.inventory.repository.ReservationRepository;
import com.example.inventory.service.InventoryService;
import com.example.inventory.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class ReservationServiceImplementation implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private InventoryService inventoryService;

    @Override
    @Transactional
    public ReservationResponseDto createReservation(ReservationRequestDto reservationRequestDto) {

            Item item = itemRepository.findById(reservationRequestDto.getItemId())
                    .orElseThrow(() -> {
                        log.warn("Item not found with itemId: {}", reservationRequestDto.getItemId());
                        return new ItemNotFoundException(reservationRequestDto.getItemId());
                    });
            inventoryService.reserveStock(item.getId(), reservationRequestDto.getQuantity());

            Reservation reservation = Reservation.builder()
                    .item(item)
                    .customerId(UUID.randomUUID())
                    .quantity(reservationRequestDto.getQuantity())
                    .status(ReservationStatus.RESERVED)
                    .createdAt(LocalDateTime.now())
                    .build();

            Reservation saved = reservationRepository.save(reservation);
            log.info("Reservation created successfully reservationId: {} itemId: {} quantity: {}",
                saved.getId(), item.getId(), saved.getQuantity());
            return reservationMapper.toDto(saved);

    }

    @Transactional
    @Override
    public ReservationResponseDto cancelReservation(UUID reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> {
                    log.warn("Reservation not found with reservationId: {}", reservationId);
                    return new ReservationNotFoundException(reservationId);
                });

        if (reservation.getStatus() != ReservationStatus.RESERVED) {
            log.warn("Cannot cancel reservation with reservationId: {} as it is in {} state", reservationId, reservation.getStatus());
            throw new ReservationCancellationException(reservationId, reservation.getStatus().name());
        }

        inventoryService.releaseReservedStock(reservation.getItem().getId(), reservation.getQuantity());
        reservation.setStatus(ReservationStatus.CANCELLED);
        Reservation updated = reservationRepository.save(reservation);
        log.info("Reservation cancelled successfully reservationId: {} itemId: {} quantity: {}",
                updated.getId(), reservation.getItem().getId(), updated.getQuantity());

        return reservationMapper.toDto(updated);
    }

    @Override
    public ReservationResponseDto getReservationById(UUID reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));

        return reservationMapper.toDto(reservation);
    }
}
