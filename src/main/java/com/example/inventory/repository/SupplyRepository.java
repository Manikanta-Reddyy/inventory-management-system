package com.example.inventory.repository;

import com.example.inventory.model.Supply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SupplyRepository extends JpaRepository<Supply, UUID> {
    List<Supply> findByItemId(UUID itemId);
}
