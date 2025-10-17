package com.example.inventory.service;

import com.example.inventory.dto.request.ItemRequestDto;
import com.example.inventory.dto.response.ItemResponseDto;

import java.util.UUID;

public interface ItemService {

    ItemResponseDto createItem(ItemRequestDto dto);
    ItemResponseDto getItemById(UUID uuid);
}
