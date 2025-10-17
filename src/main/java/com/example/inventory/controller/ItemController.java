package com.example.inventory.controller;

import com.example.inventory.dto.request.ItemRequestDto;
import com.example.inventory.dto.response.ItemResponseDto;
import com.example.inventory.service.ItemService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/product")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemResponseDto> createItem(@Valid @RequestBody ItemRequestDto itemRequestDto) {
        log.info("Received request to create item id: {}", itemRequestDto.getName());

        ItemResponseDto dto = itemService.createItem(itemRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponseDto> getItemById(@PathVariable UUID itemId) {
        log.info("Received request to fetch item with id: {} ", itemId);

        ItemResponseDto dto = itemService.getItemById(itemId);
        return ResponseEntity.ok(dto);
    }
}
