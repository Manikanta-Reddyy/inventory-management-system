package com.example.inventory.dto.response;

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
public class InventoryStatusDto {

    private UUID id;

    private UUID itemId;

    private String itemName;

    private int availableQuantity;

    private int reservedQuantity;

    private LocalDateTime updatedAt;
}