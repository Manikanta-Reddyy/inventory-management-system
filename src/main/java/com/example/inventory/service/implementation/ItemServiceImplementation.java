package com.example.inventory.service.implementation;

import com.example.inventory.cache.ItemCacheService;
import com.example.inventory.dto.request.ItemRequestDto;
import com.example.inventory.dto.response.ItemResponseDto;
import com.example.inventory.exception.ItemNotFoundException;
import com.example.inventory.mapper.ItemMapper;
import com.example.inventory.model.Item;
import com.example.inventory.repository.ItemRepository;
import com.example.inventory.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ItemServiceImplementation implements ItemService {

    @Autowired
    private ItemCacheService itemCacheService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public ItemResponseDto createItem(ItemRequestDto itemRequestDto) {
        Item entity = itemMapper.toEntity(itemRequestDto);
        Item saved = itemRepository.save(entity);
        ItemResponseDto dto = itemMapper.toDto(saved);
        itemCacheService.putItem(dto);

        log.info("Successfully created item with id: {} ", dto.getId());
        return dto;
    }

    @Override
    public ItemResponseDto getItemById(UUID itemId) {
        ItemResponseDto cached = itemCacheService.getItem(itemId);
        if (cached != null) return cached;

        log.info("Item id: {} not found in Redis cache querying db", itemId);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
        ItemResponseDto dto = itemMapper.toDto(item);
        itemCacheService.putItem(dto);

        log.info("Item id: {} cached in Redis cache", itemId);
        return dto;
    }
}
