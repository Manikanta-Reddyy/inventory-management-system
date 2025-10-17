package com.example.inventory.repository;

import com.example.inventory.model.InventoryCounter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InventoryRepository extends JpaRepository<InventoryCounter, UUID> {
    Optional<InventoryCounter> findByItemId(UUID itemId);
}
