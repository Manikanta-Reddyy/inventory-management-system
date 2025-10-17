package com.example.inventory.service;

import com.example.inventory.dto.request.SupplyRequestDto;
import com.example.inventory.dto.response.SupplyResponseDto;

import java.util.List;
import java.util.UUID;

public interface SupplyService {

    SupplyResponseDto createSupply(SupplyRequestDto supplyRequestDto);
    SupplyResponseDto getSupplyById(UUID id);
    List<SupplyResponseDto> getSuppliesByItemId(UUID itemId);
}
