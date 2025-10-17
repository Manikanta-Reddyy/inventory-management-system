package com.example.inventory.controller;

import com.example.inventory.dto.request.SupplyRequestDto;
import com.example.inventory.dto.response.SupplyResponseDto;
import com.example.inventory.service.SupplyService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/supply")
public class SupplyController {

    @Autowired
    private SupplyService supplyService;

    @PostMapping
    public ResponseEntity<SupplyResponseDto> createSupply(@Valid @RequestBody SupplyRequestDto supplyRequestDto) {
        log.info("Received request to add supply for item id: {}", supplyRequestDto.getItemId());

        SupplyResponseDto dto = supplyService.createSupply(supplyRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{supplyId}")
    public ResponseEntity<SupplyResponseDto> getSupplyById(@PathVariable UUID supplyId)  {
        log.info("Received request to fetch supply with ID: {}", supplyId);

        SupplyResponseDto dto = supplyService.getSupplyById(supplyId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<SupplyResponseDto>> getSuppliesByItemId(@PathVariable UUID itemId) {
        log.info("Received request to fetch supplies for itemId: {}", itemId);

        List<SupplyResponseDto> supply = supplyService.getSuppliesByItemId(itemId);
        return ResponseEntity.ok(supply);
    }
}
