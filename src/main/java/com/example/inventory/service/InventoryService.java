package com.example.inventory.service;

import com.example.inventory.dto.response.InventoryStatusDto;
import com.example.inventory.model.InventoryCounter;

import java.util.UUID;

public interface InventoryService {

    InventoryCounter getOrCreateByItemId(UUID itemId);
    void addStock(UUID itemId, int quantity);
    void reserveStock(UUID itemId, int quantity);
    void releaseReservedStock(UUID itemId, int quantity);

    InventoryStatusDto getInventoryByItemId(UUID itemId);
}
