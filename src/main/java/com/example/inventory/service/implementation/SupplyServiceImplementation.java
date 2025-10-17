package com.example.inventory.service.implementation;

import com.example.inventory.dto.request.SupplyRequestDto;
import com.example.inventory.dto.response.SupplyResponseDto;
import com.example.inventory.exception.ItemNotFoundException;
import com.example.inventory.exception.SupplyNotFoundException;
import com.example.inventory.mapper.SupplyMapper;
import com.example.inventory.model.Item;
import com.example.inventory.model.Supply;
import com.example.inventory.repository.ItemRepository;
import com.example.inventory.repository.SupplyRepository;
import com.example.inventory.service.InventoryService;
import com.example.inventory.service.SupplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class SupplyServiceImplementation implements SupplyService {

    @Autowired
    private SupplyRepository supplyRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private SupplyMapper supplyMapper;

    @Autowired
    private InventoryService inventoryService;

    @Override
    public SupplyResponseDto createSupply(SupplyRequestDto supplyRequestDto) {

        Item item = itemRepository.findById(supplyRequestDto.getItemId())
                .orElseThrow(() -> {
                    log.warn("Item not found with itemId: {}", supplyRequestDto.getItemId());
                    return new ItemNotFoundException(supplyRequestDto.getItemId());
                });

        Supply supply = supplyMapper.toEntity(supplyRequestDto);
        supply.setItem(item);
        Supply saved = supplyRepository.save(supply);
        log.info("Supply created with id: {} for itemId: {} and quantity: {}",
                saved.getId(), item.getId(), saved.getQuantity());

        inventoryService.addStock(item.getId(), supplyRequestDto.getQuantity());
        return supplyMapper.toDto(saved);
    }

    @Override
    public SupplyResponseDto getSupplyById(UUID supplyId) {
        log.info("Fetching supply with id: {}", supplyId);

        Supply supply = supplyRepository.findById(supplyId)
                .orElseThrow(() -> {
                    log.warn("Supply not found with id: {}", supplyId);
                    return new SupplyNotFoundException(supplyId);
                });

        SupplyResponseDto dto = supplyMapper.toDto(supply);
        log.info("Successfully fetched supply with id: {}", supplyId);
        return dto;
    }

    @Override
    public List<SupplyResponseDto> getSuppliesByItemId(UUID itemId) {
        log.info("Fetching supplies with itemId: {}", itemId);

        List<Supply> supplyList = supplyRepository.findByItemId(itemId);
        if (supplyList.isEmpty()) {
            log.warn("No supplies found for itemId: {}", itemId);
        }

        List<SupplyResponseDto> dto = supplyList.stream()
                .map(supplyMapper::toDto)
                .toList();
        log.info("Fetched {} supplies for itemId: {}", dto.size(), itemId);
        return dto;
    }
}
