package com.example.inventory.controller;

import com.example.inventory.dto.response.InventoryStatusDto;
import com.example.inventory.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/{itemId}")
    public ResponseEntity<InventoryStatusDto> getInventoryByItemId(@PathVariable UUID itemId) {
        log.info("Received request to fetch inventory with itemId: {}", itemId);

        InventoryStatusDto dto = inventoryService.getInventoryByItemId(itemId);
        return ResponseEntity.ok(dto);
    }
}
