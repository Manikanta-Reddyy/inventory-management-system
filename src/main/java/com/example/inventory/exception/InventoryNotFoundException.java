package com.example.inventory.exception;

import java.util.UUID;

public class InventoryNotFoundException extends RuntimeException{

    public InventoryNotFoundException(UUID itemId) {
        super("Inventory not found for itemId: " + itemId);
    }
}
