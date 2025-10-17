package com.example.inventory.exception;

import java.util.UUID;

public class ReservationNotFoundException extends RuntimeException{

    public ReservationNotFoundException(UUID reservationId) {
        super("Reservation not found for ID: " + reservationId);
    }
}
