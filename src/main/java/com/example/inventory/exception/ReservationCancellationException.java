package com.example.inventory.exception;

import java.util.UUID;

public class ReservationCancellationException extends RuntimeException{

    public ReservationCancellationException(UUID reservationId, String currentStatus) {
        super("Reservation cannot be cancelled for reservationId: " + reservationId + ", Current status: " + currentStatus);
    }
}
