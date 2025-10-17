package com.example.inventory.exception;

import java.util.UUID;

public class SupplyNotFoundException extends RuntimeException{

    public SupplyNotFoundException(UUID supplyId) {
        super("Supply not found with id: " + supplyId);
    }
}
