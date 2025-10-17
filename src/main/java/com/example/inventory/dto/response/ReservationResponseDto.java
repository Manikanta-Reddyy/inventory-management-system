package com.example.inventory.dto.response;

import com.example.inventory.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponseDto {

    private UUID id;

    private UUID itemId;

    private UUID customerId;

    private String itemName;

    private int quantity;

    private ReservationStatus status;

    private LocalDateTime createdAt;
}
