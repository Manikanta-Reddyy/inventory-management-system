package com.example.inventory.exception;

import java.util.UUID;

public class InsufficientStockException extends RuntimeException{

    public InsufficientStockException(UUID itemId, int requested, int available) {
        super("Insufficient stock for itemId: " + itemId +
                ". Requested: " + requested + ", Available: " + available);
    }
}
